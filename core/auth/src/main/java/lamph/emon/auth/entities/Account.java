package lamph.emon.auth.entities;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private String id;
    private String username;
    private String password;
    private Status status;
    private String reason;
    private OffsetDateTime createTime;
    private OffsetDateTime updateTime;

    enum Status {
        ACTIVE, BLOCKED
    }

    /**
     * new instance of account
     *
     * @param username username
     * @param password password
     * @return new account
     */
    public static Account newAccount(String username, String password) {
        return new Account(
                UUID.randomUUID().toString(),
                username,
                password,
                Status.ACTIVE,
                null,
                OffsetDateTime.now(),
                null
        );
    }

    /**
     * block
     *
     * @param reason reason
     */
    public void block(String reason) {
        updateStatus(Status.BLOCKED, reason);
    }

    /**
     * unblock
     *
     * @param reason reason
     */
    public void unBlock(String reason) {
        updateStatus(Status.ACTIVE, reason);
    }

    /**
     * update status
     *
     * @param newStatus new status
     * @param reason    reason
     */
    private void updateStatus(Status newStatus, String reason) {
        setStatus(newStatus);
        setReason(reason);
    }

}
