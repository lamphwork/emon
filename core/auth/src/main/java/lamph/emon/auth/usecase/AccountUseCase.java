package lamph.emon.auth.usecase;

import lamph.emon.auth.entities.Account;
import lamph.emon.auth.entities.Permission;
import lamph.emon.auth.entities.Role;
import lamph.emon.auth.exceptions.AccountNotFoundException;
import lamph.emon.auth.exceptions.DuplicateUsernameException;
import lamph.emon.auth.repositories.AccountRepository;
import lamph.emon.auth.repositories.PermissionRepository;
import lamph.emon.auth.repositories.RoleRepository;
import lamph.emon.auth.usecase.params.BlockAccountInput;
import lamph.emon.auth.usecase.params.CreateAccountInput;
import lamph.emon.auth.usecase.params.UnBlockAccountInput;
import lamph.emon.auth.usecase.params.UpdateAuthoritiesInput;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class AccountUseCase {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
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

        List<Role> roles = roleRepository.findAllByID(input.roles());
        List<Permission> permissions = permissionRepository.findAllByID(input.permissions());

        accountRepository.findByUsername(username).ifPresent(account -> {
            throw new DuplicateUsernameException(username);
        });

        Account newAccount = Account.newAccount(
                username,
                password,
                roles,
                permissions
        );
        return accountRepository.create(newAccount);
    }

    /**
     * update authorities for account
     *
     * @param input new authorities info
     * @return updated account id
     */
    public String updateAuthorities(UpdateAuthoritiesInput input) {
        String accountId = input.getAccountId();

        List<Role> roles = roleRepository.findAllByID(input.getRoles());
        List<Permission> permissions = permissionRepository.findAllByID(input.getPermissions());

        accountRepository.findById(accountId).ifPresentOrElse(
                account -> {
                    account.updateRoles(roles);
                    account.updatePermissions(permissions);
                    accountRepository.update(accountId, account);
                },
                () -> {
                    throw new AccountNotFoundException(accountId);
                }
        );

        return accountId;
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
