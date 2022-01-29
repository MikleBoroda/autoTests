package redmine.model.role;

import lombok.AllArgsConstructor;
import lombok.Getter;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


@AllArgsConstructor
@Getter
public enum Permissions {
    EDIT_PROJECT("Создание проекта"),
    SELECT_PROJECT_MODULES("Выбор модулей проекта"),
    CLOSE_PROJECT("Закрывать / открывать проекты"),
    MANAGE_MEMBERS("Управление участниками"),
    MANAGE_VERSIONS("Управление версиями"),
    ADD_SUBPROJECTS("Создание подпроектов"),
    MANAGE_PUBLIC_QUERIES("Управление общими запросами"),
    SAVE_QUERIES("Сохранение запросов"),
    VIEW_MESSAGES("Просмотр сообщений"),
    ADD_MESSAGES("Отправка сообщений"),
    EDIT_MESSAGES("Редактирование сообщени"),
    EDIT_OWN_MESSAGES("Редактирование собственных сообщений"),
    DELETE_MESSAGES("Удаление сообщений"),
    DELETE_OWN_MESSAGES("Удаление собственных сообщений"),
    MANAGE_BOARDS("Управление форумами"),
    VIEW_CALENDAR("Просмотр календаря"),
    VIEW_DOCUMENTS("Просмотр документов"),
    ADD_DOCUMENTS("Добавить документы"),
    EDIT_DOCUMENTS("Редактировать документы"),
    DELETE_DOCUMENTS("Удалить документы"),
    VIEW_FILES("Просмотр файлов"),
    MANAGE_FILES("Управление файлами"),
    VIEW_GANTT("Просмотр диаграммы Ганта"),
    VIEW_ISSUES("Просмотр задач"),
    ADD_ISSUES("Добавление задач"),
    EDIT_ISSUES("Редактирование задач"),
    EDIT_OWN_ISSUES("Редактировать свои задачи"),
    COPY_ISSUES("Копирование задач"),
    MANAGE_ISSUE_RELATIONS("Управление связыванием задач"),
    MANAGE_SUBTASKS("Управление подзадачами"),
    SET_ISSUES_PRIVATE("Установление видимости (общая/частная) для задач"),
    SET_OWN_ISSUES_PRIVATE("Установление видимости (общая/частная) для собственных задач"),
    ADD_ISSUE_NOTES("Добавление примечаний"),
    EDIT_ISSUE_NOTES("Редактирование примечаний"),
    EDIT_OWN_ISSUE_NOTES("Редактирование собственных примечаний"),
    VIEW_PRIVATE_NOTES("Просмотр приватных комментариев"),
    SET_NOTES_PRIVATE("Размещение приватных комментариев"),
    DELETE_ISSUES("Удаление задач"),
    VIEW_ISSUE_WATCHERS("Просмотр списка наблюдателей"),
    ADD_ISSUE_WATCHERS("Добавление наблюдателей"),
    DELETE_ISSUE_WATCHERS("Удаление наблюдателей"),
    IMPORT_ISSUES("Импорт задач"),
    MANAGE_CATEGORIES("Управление категориями задач"),
    VIEW_NEWS("Просмотр новостей"),
    MANAGE_NEWS("Управление новостями"),
    COMMENT_NEWS("Комментирование новостей"),
    VIEW_CHANGESETS("Просмотр изменений хранилищ"),
    BROWSE_REPOSITORY("Просмотр хранилища"),
    COMMIT_ACCESS("Изменение файлов в хранилище"),
    MANAGE_RELATED_ISSUES("Управление связанными задачами"),
    MANAGE_REPOSITORY("Управление хранилищем"),
    VIEW_TIME_ENTRIES("Просмотр трудозатрат"),
    LOG_TIME("Учёт трудозатрат"),
    EDIT_TIME_ENTRIES("Редактирование учёта времени"),
    EDIT_OWN_TIME_ENTRIES("Редактирование собственного учёта времени"),
    MANAGE_PROJECT_ACTIVITIES("Управление типами действий для проекта"),
    LOG_TIME_FOR_OTHER_USERS("Учитывать время других пользователей"),
    IMPORT_TIME_ENTRIES("Импорт трудозатрат"),
    VIEW_WIKI_PAGES("Просмотр Wiki"),
    VIEW_WIKI_EDITS("Просмотр истории Wiki"),
    EXPORT_WIKI_PAGES("Экспорт wiki-страниц"),
    EDIT_WIKI_PAGES("Редактирование wiki-страниц"),
    RENAME_WIKI_PAGES("Переименование wiki-страниц"),
    DELETE_WIKI_PAGES("Удаление wiki-страниц"),
    DELETE_WIKI_PAGES_ATTACHMENTS("Удаление прикреплённых файлов"),
    PROTECT_WIKI_PAGES("Блокирование wiki-страниц"),
    MANAGE_WIKI("Управление Wiki");

    public final String description;

    public static Permissions off(String description1) {
        List<Permissions> permissions = Arrays.asList(Permissions.values());
        for (Permissions permission : permissions) {
            if (permission.description.equals(description1)) {
                return permission;
            }
        }
        return null;
    }
}
