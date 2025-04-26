package lamph.emon.auth.exceptions;

import lamph.emon.common.exceptions.BusinessException;

import java.util.Map;

public class DuplicateUsernameException extends BusinessException {

    public DuplicateUsernameException(String username) {
        super("USERNAME_EXISTED", Map.of("username", username));
    }
}
