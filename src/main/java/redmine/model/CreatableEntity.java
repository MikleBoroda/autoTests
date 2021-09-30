package redmine.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public abstract class CreatableEntity extends Entity {
    LocalDateTime createdOn = LocalDateTime.now();
    LocalDateTime updatedOn = LocalDateTime.now();
}
