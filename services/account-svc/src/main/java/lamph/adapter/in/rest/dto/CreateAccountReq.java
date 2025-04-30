package lamph.adapter.in.rest.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Introspected
@Serdeable
public record CreateAccountReq(
        String username,
        String password,
        List<String> roles,
        List<String> permissions
) {
}
