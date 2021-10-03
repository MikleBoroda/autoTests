package redmine.db.reauests;

import redmine.model.Entity;

public interface Create<T extends Entity> {
    void create(T entity);

}
