package redmine.db.reauests;

import redmine.model.Entity;

import java.util.List;

public interface ReadAll <T extends Entity> {
    List<T> readAll();
}
