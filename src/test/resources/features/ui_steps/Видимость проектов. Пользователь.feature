#language: ru

Функция: Видимость проекта. Пользователь.

  Предыстория:
    Пусть В системе создан пользователь "ПОЛЬЗОВАТЕЛЬ" со следующими параметрами:
      | Администратор | false   |
      | Статус        | Активен |
    Также В системе существует роль "РОЛЬ" с правами:
      | Просмотр задач |
    И Список ролей "СПИСОК_РОЛЕЙ_1" содержит роли:
      | РОЛЬ |
    Также Существует проект "ПРОЕКТ_1" с параметрами
      | Публичный | true |
    Также Существует проект "ПРОЕКТ_2" с параметрами
      | Публичный | false |
    Также Существует проект "ПРОЕКТ_3" с параметрами
      | Публичный | false |
    Также Пользователь "ПОЛЬЗОВАТЕЛЬ" имеет доступ к проекту "ПРОЕКТ_3" со списком ролей "СПИСОК_РОЛЕЙ_1"
    И Открыт браузер на главной странице

  @ui
  Сценарий: Вход подвержденным пользователем. Видимость проектов
    Если Нажать кнопку Войти
    И Войти в систему под пользователем "ПОЛЬЗОВАТЕЛЬ"
    Тогда На странице "Заголовок страницы" отображается элемент "Домашняя страница"
    И На странице "Заголовок страницы" нажать на элемент "Проекты"
    Тогда На странице "Проекты" отображается элемент "Проекты"
    И На странице Проекты отображается проект "ПРОЕКТ_3"
    И На странице Проекты не отображается проект "ПРОЕКТ_2"
    И На странице Проекты отображается проект "ПРОЕКТ_1"




