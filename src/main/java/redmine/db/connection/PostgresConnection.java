package redmine.db.connection;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import org.postgresql.util.PSQLException;

import static redmine.property.Property.getIntegerProperty;
import static redmine.property.Property.getStringProperty;


public class PostgresConnection implements DatabaseConnection {
    public final static DatabaseConnection INSTANCE = new PostgresConnection();

    private String host = getStringProperty("db.host");
    private Integer port = getIntegerProperty("db.port");
    private String database = getStringProperty("db.database");
    private String user = getStringProperty("db.user");
    private String password = getStringProperty("db.password");
    private Connection connection;

    public PostgresConnection() {
        connect();
    }

    @SneakyThrows
    private void connect() {
        Class.forName("org.postgresql.Driver"); //загрузка драйвера

        //подключени к бд
        String url = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
        Properties connectionProperties = new Properties();
        connectionProperties.setProperty("user", user);
        connectionProperties.setProperty("password", password);
        //создаем обьект типа connection
        connection = DriverManager.getConnection(url, connectionProperties);

    }

    @Override
    @SneakyThrows
    @Step("Выполнение SQL запроса")
    public synchronized List<Map<String, Object>> executeQuery(String query, Object... parameters) {
        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            for (int i = 0; i < parameters.length; i++) {
                stmt.setObject(i + 1, parameters[i]);

            }
            Allure.addAttachment("SQL-запрос", stmt.toString()); // логирование SQL запроса
            ResultSet rs = stmt.executeQuery();

            List<Map<String, Object>> result = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> oneLineResult = new HashMap<>();
                int columnCount = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String key = rs.getMetaData().getColumnName(i);
                    Object value = rs.getObject(i);
                    oneLineResult.put(key, value);
                }
                result.add(oneLineResult);
            }
            Allure.addAttachment("SQL-ответ", result.toString()); // логирование SQL ответа
            return result;

        } catch (PSQLException exception) {
            if (exception.getMessage().equals("Запрос не вернул результатов.")) {
                return null;
            } else {
                throw exception;
            }
        }
    }
}
