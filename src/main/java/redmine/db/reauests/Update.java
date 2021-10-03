package redmine.db.reauests;

import redmine.model.Entity;

public interface Update<T extends Entity> {
    void update(Integer id, T entity);
}
