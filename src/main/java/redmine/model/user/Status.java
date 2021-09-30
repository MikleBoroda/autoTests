package redmine.model.user;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {

    UNREGISTERED(0),
    ACTIVE(1),
    UNACCEPTED(2),
    LOKED(3);

    public final int statusCode;

}
