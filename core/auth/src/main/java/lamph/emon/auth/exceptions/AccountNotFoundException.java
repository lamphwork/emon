package lamph.emon.auth.exceptions;

import lamph.emon.common.exceptions.BusinessException;

import java.util.Map;

public class AccountNotFoundException extends BusinessException {

    public AccountNotFoundException(String accountId) {
        super("ACCOUNT_NOT_FOUND", Map.of("accountId", accountId));
    }
}
