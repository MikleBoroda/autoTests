package redmine.db.reauests;

import redmine.model.Entity;

public interface Read<T extends Entity> {
    T read(Integer id);
}
