#language: ru

Функция: Создание, изменение, получение, удаление пользователя.Администратор системы

  Предыстория:
    Пусть В системе создан пользователь "ПОЛЬЗОВАТЕЛЬ" со следующими параметрами:
      | Администратор | true |
      | Api-ключ      | API  |
    И Создан api-клент "КЛИЕНТ" для пользователя "ПОЛЬЗОВАТЕЛЬ"

    @api
    Сценарий: Создание, изменение, получение, удаление пользователя.Администратор системы
      Тогда Отправить "POST" запрос через "КЛИЕНТ" на эндпоинт "/users.json" для создания пользователя в БД и получить "ОТВЕТ_1"
      И Проверить, что статус код из ответа "ОТВЕТ_1" = 201
      И Проверить, что тело ответа "ОТВЕТ_1" содержит данные пользователя в том числе его id
      И Проверить, что статус пользователя "ПОЛЬЗОВАТЕЛЬ_ИЗ_ОТВЕТА" из ответа "ОТВЕТ_1" такойже как в БД status = 2
      Затем Повторить запрос "POST" через api-client "КЛИЕНТ" на создание пользователя с тем же телом запроса проверить ответ "ОТВЕТ_2"
      И Проверить, что статус код из ответа "ОТВЕТ_2" = 422
      И Проверить, что тело ответа "ОТВЕТ_2" содержит error, в теле содржится что "Email уже существует" и "Пользователь уже существует"
      Затем Повторить запрос POST через api-client "КЛИЕНТ" на создание пользователя с тем же телом запроса,указать невалидный E-mail и password и получить ответ "ОТВЕТ_3"
      И Проверить, что статус код из ответа "ОТВЕТ_3" = 422
      И Проверить, что тело ответа "ОТВЕТ_3" содержит error, в теле содржится что "Email имеет неверное значение" и "Пароль недостаточной длины (не может быть меньше 8 символа)"
      Затем Отправить запрос PUT запрос через api-Client "КЛИЕНТ" на эндпоинт "/users/%d.json" используя ответ "ОТВЕТ_1" из шага №1,изменить status = 1, проверить ответ "ОТВЕТ_4"
      И Проверить, что статус код из ответа "ОТВЕТ_4" = 204
      И Проверить, что статус пользователя "ПОЛЬЗОВАТЕЛЬ_ИЗ_ОТВЕТА" из ответа "ОТВЕТ_1" такойже как в БД status = 1
      Затем Отправить запрос GET через api-client "КЛИЕНТ" пользователем "ПОЛЬЗОВАТЕЛЬ_ИЗ_ОТВЕТА" на эндпоинт "/users/%d.json", получить ответ = "ОТВЕТ_5"
      И Проверить, что статус код из ответа "ОТВЕТ_5" = 200
      И Проверить, что статус пользователя "ПОЛЬЗОВАТЕЛЬ_ИЗ_ОТВЕТА" из ответа "ОТВЕТ_5" такойже как в БД status = 1
      Затем Отправить запрос DELETE через api-client "КЛИЕНТ" на удаление  пользователя "ПОЛЬЗОВАТЕЛЬ_ИЗ_ОТВЕТА" на эндпоинт "/users/%d.json", получить ответ = "ОТВЕТ_6"
      И Проверить, что статус код из ответа "ОТВЕТ_6" = 204
      И Проверить, что пользователь "ПОЛЬЗОТВАТЕЛЬ" отсутствует в БД
      Также Отправить запрос DELETE через api-client "КЛИЕНТ" на удаление  пользователя "ПОЛЬЗОВАТЕЛЬ_ИЗ_ОТВЕТА" на эндпоинт "/users/%d.json", получить ответ = "ОТВЕТ_7"
      И Проверить, что статус код из ответа "ОТВЕТ_7" = 404


