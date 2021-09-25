package redmine.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class CreatableEntity extends Entity {
    LocalDateTime createdOn = LocalDateTime.now();
    LocalDateTime updatedOn = LocalDateTime.now();
}
