package redmine.db.reaquests;

import redmine.model.Entity;

public interface Create<T extends Entity> {
    void create(T entity);

}
