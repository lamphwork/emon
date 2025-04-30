package lamph.emon.auth.entities;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private Set<Role> roles;
    private Set<Permission> permissions;
    private OffsetDateTime createTime;
    private OffsetDateTime updateTime;

    public enum Status {
        ACTIVE, BLOCKED
    }

    /**
     * new instance of account
     *
     * @param username username
     * @param password password
     * @return new account
     */
    public static Account newAccount(String username, String password, List<Role> roles, List<Permission> permissions) {
        return new Account(
                UUID.randomUUID().toString(),
                username,
                password,
                Status.ACTIVE,
                null,
                new HashSet<>(roles),
                new HashSet<>(permissions),
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
     * assign roles for account
     *
     * @param roles roles
     */
    public void updateRoles(List<Role> roles) {
        setRoles(new HashSet<>(roles));
    }

    /**
     * assign permission for account
     *
     * @param permissions permissions
     */
    public void updatePermissions(List<Permission> permissions) {
        setPermissions(new HashSet<>(permissions));
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
