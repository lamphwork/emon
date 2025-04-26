package lamph.emon.auth.usecase;

import lamph.emon.auth.entities.Account;
import lamph.emon.auth.exceptions.AccountNotFoundException;
import lamph.emon.auth.exceptions.DuplicateUsernameException;
import lamph.emon.auth.repositories.AccountRepository;
import lamph.emon.auth.usecase.params.BlockAccountInput;
import lamph.emon.auth.usecase.params.CreateAccountInput;
import lamph.emon.auth.usecase.params.UnBlockAccountInput;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountUseCase {

    private final AccountRepository accountRepository;

    /**
     * create new account
     *
     * @param input input
     * @return created account id
     */
    public String createAccount(CreateAccountInput input) {
        String username = input.username();
        String password = input.password();

        accountRepository.findByUsername(username).ifPresent(account -> {
            throw new DuplicateUsernameException(username);
        });

        Account newAccount = Account.newAccount(username, password);
        return accountRepository.create(newAccount);
    }

    /**
     * block account
     *
     * @param input use case input
     */
    public void blockAccount(BlockAccountInput input) {
        String accountId = input.accountId();
        String reason = input.reason();

        accountRepository.findById(accountId).ifPresentOrElse(
                account -> {
                    account.block(reason);
                    accountRepository.update(accountId, account);
                },
                () -> {
                    throw new AccountNotFoundException(accountId);
                }
        );
    }

    /**
     * unblock account
     *
     * @param input use case input
     */
    public void unBlockAccount(UnBlockAccountInput input) {
        String accountId = input.accountId();
        String reason = input.reason();

        accountRepository.findById(accountId).ifPresentOrElse(
                account -> {
                    account.unBlock(reason);
                    accountRepository.update(accountId, account);
                },
                () -> {
                    throw new AccountNotFoundException(accountId);
                }
        );
    }
}
