package lamph.quarkus.auth.adapter.out.jooq;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lamph.emon.auth.entities.Role;
import lamph.emon.auth.repositories.RoleRepository;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Optional;

import static lamph.quarkus.auth.Tables.*;


@Singleton
public class JooqRoleRepo implements RoleRepository {

    @Inject
    DSLContext dsl;

    @Override
    public Optional<Role> findById(String s) {
        dsl.select()
                .from(ROLES)
                .leftJoin(ROLE_PERMISSIONS).on(ROLES.NAME.eq(ROLE_PERMISSIONS.ROLE_NAME))
                .leftJoin(PERMISSIONS).on(PERMISSIONS.NAME.eq(ROLE_PERMISSIONS.PERMISSION_NAME))
                .where(ROLES.NAME.eq(s))
                .fetch();
        return null;
    }

    @Override
    public List<Role> findAllByID(List<String> strings) {
        return List.of();
    }

    @Override
    public String create(Role role) {
        return "";
    }

    @Override
    public void update(String s, Role role) {

    }

    @Override
    public void delete(String s) {

    }
}
