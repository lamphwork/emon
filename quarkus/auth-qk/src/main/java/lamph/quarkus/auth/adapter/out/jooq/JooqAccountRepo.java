package lamph.quarkus.auth.adapter.out.jooq;

import jakarta.inject.Singleton;
import lamph.emon.auth.entities.Account;
import lamph.emon.auth.entities.Authority;
import lamph.emon.auth.entities.Permission;
import lamph.emon.auth.entities.Role;
import lamph.emon.auth.repositories.AccountRepository;
import org.jooq.*;
import lamph.quarkus.auth.tables.*;
import lamph.quarkus.auth.tables.records.AccountsRecord;
import org.jooq.Record;

import java.util.*;

import static lamph.quarkus.auth.Tables.*;


@Singleton public class JooqAccountRepo implements AccountRepository {

    private final DSLContext dsl;

    final Accounts accountTbl = ACCOUNTS.as("acc");
    final Roles roleTbl = ROLES.as("r");
    final Permissions permissionTbl = PERMISSIONS.as("p");
    final RolePermissions rolePermissions = ROLE_PERMISSIONS.as("rp");
    final AccountRoles accountRoleTbl = ACCOUNT_ROLES.as("ar");
    final AccountPermissions accountPermissionTbl = ACCOUNT_PERMISSIONS.as("ap");

    public JooqAccountRepo(DSLContext dsl) {
        this.dsl = dsl;
    }

    /**
     * write into db record
     *
     * @param account model
     * @return db record
     */
    public AccountsRecord writeAccount(Account account) {
        return new AccountsRecord(
                account.getId(),
                account.getUsername(),
                account.getPassword(),
                account.getStatus().name(),
                account.getReason(),
                account.getCreateTime(),
                account.getUpdateTime()
        );
    }

    /**
     * read db into model
     *
     * @param results db records
     * @return list model
     */
    public List<Account> readDB(Result<Record> results) {
        List<Account> accounts = new ArrayList<>();
        Map<String, Account> accountMap = new HashMap<>();
        Map<String, lamph.emon.auth.entities.Role> roleMap = new HashMap<>();

        for (Record record : results) {
            String accountId = record.getValue(ACCOUNTS.ID);
            Account account = accountMap.get(accountId);

            if (account == null) {
                account = new Account(
                        record.get(accountTbl.ID),
                        record.get(accountTbl.USERNAME),
                        record.get(accountTbl.PASSWORD),
                        Account.Status.valueOf(record.get(accountTbl.STATUS)),
                        record.get(accountTbl.REASON),
                        new HashSet<>(),
                        new HashSet<>(),
                        record.get(accountTbl.CREATE_TIME),
                        record.get(accountTbl.UPDATE_TIME)
                );
                accounts.add(account);
                accountMap.put(accountId, account);
            }

            String roleName = record.get(roleTbl.NAME);
            Role role = roleMap.get(roleName);
            if (role == null) {
                role = new Role(roleName, record.get(roleTbl.DESCRIPTION), new LinkedList<>());
                roleMap.put(roleName, role);
            }

            role.permissions().add(
                    new Permission(
                            record.get(rolePermissions.PERMISSION_NAME),
                            ""
                    )
            );
        }

        return accounts;
    }

    @Override
    public Optional<Account> findByUsername(String email) {
        Result<Record> results = queryAccount()
                .where(accountTbl.USERNAME.equalIgnoreCase(email))
                .fetch();

        return readDB(results).stream().findFirst();
    }

    /**
     * common query
     *
     * @return query
     */
    private SelectOnConditionStep<Record> queryAccount() {
        return dsl.select()
                .from(accountTbl)
                .leftJoin(accountRoleTbl).on(accountRoleTbl.ACCOUNT_ID.eq(accountTbl.ID)).and(accountRoleTbl.ACTIVE.isTrue())
                .leftJoin(roleTbl).on(roleTbl.NAME.eq(accountRoleTbl.ROLE_NAME)).and(roleTbl.ACTIVE.isTrue())
                .leftJoin(rolePermissions).on(rolePermissions.ROLE_NAME.eq(accountRoleTbl.ROLE_NAME))
                .leftJoin(accountPermissionTbl).on(accountPermissionTbl.ACCOUNT_ID.eq(accountTbl.ID)).and(accountPermissionTbl.ACTIVE.isTrue())
                .leftJoin(permissionTbl).on(permissionTbl.NAME.eq(accountPermissionTbl.PERMISSION_NAME)).and(permissionTbl.ACTIVE.isTrue());
    }

    @Override
    public Optional<Account> findById(String s) {
        Result<Record> results = queryAccount()
                .where(accountTbl.ID.eq(s))
                .fetch();

        return readDB(results).stream().findFirst();
    }

    @Override
    public List<Account> findAllByID(List<String> strings) {
        Result<Record> results = queryAccount()
                .where(accountTbl.ID.in(strings))
                .fetch();

        return readDB(results);
    }

    @Override
    public String create(Account account) {
        List<Query> queries = new LinkedList<>();
        queries.add(dsl.insertInto(ACCOUNTS).set(writeAccount(account)));

        List<String> roles = new LinkedList<>();
        for (Authority role : account.getRoles()) {
            roles.add(role.getValue());
            queries.add(
                    dsl.insertInto(accountRoleTbl)
                            .set(accountRoleTbl.ID, UUID.randomUUID().toString())
                            .set(accountRoleTbl.ACCOUNT_ID, account.getId())
                            .set(accountRoleTbl.ROLE_NAME, role.getValue())
                            .set(accountRoleTbl.ACTIVE, true)
                            .onDuplicateKeyUpdate()
                            .set(accountRoleTbl.ACTIVE, true)
                            .where(accountRoleTbl.ID.eq(account.getId()))
                            .and(accountRoleTbl.ROLE_NAME.eq(role.getValue()))
            );
        }

        queries.add(
                dsl.update(accountRoleTbl)
                        .set(accountRoleTbl.ACTIVE, false)
                        .where(accountRoleTbl.ACCOUNT_ID.eq(account.getId()))
                        .and(accountRoleTbl.ROLE_NAME.notIn(roles))
        );

        List<String> permissions = new LinkedList<>();
        for (Authority permission : account.getPermissions()) {
            permissions.add(permission.getValue());
            queries.add(
                    dsl.insertInto(accountPermissionTbl)
                            .set(accountPermissionTbl.ID, UUID.randomUUID().toString())
                            .set(accountPermissionTbl.ACCOUNT_ID, account.getId())
                            .set(accountPermissionTbl.PERMISSION_NAME, permission.getValue())
                            .set(accountPermissionTbl.ACTIVE, true)
                            .onDuplicateKeyUpdate()
                            .set(accountPermissionTbl.ACTIVE, true)
                            .where(accountPermissionTbl.ID.eq(account.getId()))
                            .and(accountPermissionTbl.PERMISSION_NAME.eq(permission.getValue()))
            );
        }
        queries.add(
                dsl.update(accountPermissionTbl)
                        .set(accountPermissionTbl.ACTIVE, false)
                        .where(accountPermissionTbl.ACCOUNT_ID.eq(account.getId()))
                        .and(accountPermissionTbl.PERMISSION_NAME.notIn(permissions))
        );


        dsl.batch(queries).execute();
        return account.getId();
    }

    @Override
    public void update(String s, Account account) {

    }

    @Override
    public void delete(String s) {
        List<Query> queries = new ArrayList<>();
        queries.add(dsl.deleteFrom(ACCOUNTS).where(ACCOUNTS.ID.eq(s)));
        queries.add(dsl.deleteFrom(ACCOUNT_ROLES).where(ACCOUNT_ROLES.ACCOUNT_ID.eq(s)));
        queries.add(dsl.deleteFrom(ACCOUNT_PERMISSIONS).where(ACCOUNT_PERMISSIONS.ACCOUNT_ID.eq(s)));

        dsl.batch(queries).execute();
    }
}
