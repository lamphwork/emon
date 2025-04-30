package lamph.emon.auth.usecase.params;

import java.util.List;

public record CreateAccountInput(
        String username,
        String password,
        List<String> roles,
        List<String> permissions
) {
}
