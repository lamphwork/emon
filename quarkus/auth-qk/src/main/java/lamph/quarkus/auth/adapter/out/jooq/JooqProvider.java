package lamph.quarkus.auth.adapter.out.jooq;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.sql.DataSource;

@Provider
public class JooqProvider {

    @Inject
    DataSource ds;

    @Singleton
    public DSLContext dslContext() {
        return DSL.using(ds, SQLDialect.POSTGRES);
    }
}
