package redmine.db.reaquests;

import redmine.model.Entity;

public interface Read<T extends Entity> {
    T read(Integer id);
}
