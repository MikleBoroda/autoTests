#language: ru

Функция:  Создание пользователя. Пользователь без прав администратора

  Предыстория:
    Пусть В системе создан пользователь "ПОЛЬЗОВАТЕЛЬ" со следующими параметрами:
      | Администратор | false |
      | Api-ключ      | API   |
    И Создан api-клент "КЛИЕНТ" для пользователя "ПОЛЬЗОВАТЕЛЬ"


  Сценарий: Создание пользователя без прав администратора с api-Ключом
    Тогда Отправить "POST" запрос через "КЛИЕНТ" на эндпоинт "/users.json" для создания пользователя в БД и получить "ОТВЕТ"
    И Проверить, что статус код из ответа "ОТВЕТ" = 403