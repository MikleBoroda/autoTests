#language: ru

Функция:  Авторизация подтвержденным пользователем

  Предыстория:
    Пусть В системе создан пользователь "НЕ_АДМИНИСТРАТОР" со следующими параметрами:
      | Администратор | false   |
      | Статус        | Не подтвержден |
    И Открыт браузер на главной странице

  Сценарий: Вход подтвержденным пользователем.
    Если Нажать кнопку Войти
    И Войти в систему под пользователем "НЕ_АДМИНИСТРАТОР"
    Тогда На странице "Заголовок страницы" отображается элемент "Домашняя страница"
    И На странице "Заголовок страницы" отображается элемент "Сообщение"
    И На странице "Заголовок страницы" отображается элемент "Войти"
    И На странице "Заголовок страницы" отображается элемент "Регистрация"
    Также На странице "Заголовок страницы" не отображается элемент "Моя страница"
