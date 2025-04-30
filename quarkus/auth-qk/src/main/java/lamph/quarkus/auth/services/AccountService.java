package lamph.quarkus.auth.services;

import io.quarkus.narayana.jta.QuarkusTransaction;
import jakarta.inject.Singleton;
import lamph.emon.auth.repositories.AccountRepository;
import lamph.emon.auth.repositories.PermissionRepository;
import lamph.emon.auth.repositories.RoleRepository;
import lamph.emon.auth.usecase.AccountUseCase;
import lamph.emon.auth.usecase.params.BlockAccountInput;
import lamph.emon.auth.usecase.params.CreateAccountInput;
import lamph.emon.auth.usecase.params.UnBlockAccountInput;

@Singleton
public class AccountService extends AccountUseCase {


    /**
     * constructor inject
     *
     * @param roleRepository       role repository
     * @param permissionRepository permission repository
     * @param accountRepository    account repository
     */
    public AccountService(
            RoleRepository roleRepository,
            PermissionRepository permissionRepository,
            AccountRepository accountRepository) {
        super(roleRepository, permissionRepository, accountRepository);
    }

    @Override
    public String createAccount(CreateAccountInput input) {
        return QuarkusTransaction.joiningExisting()
                .call(() -> super.createAccount(input));
    }

    @Override
    public void blockAccount(BlockAccountInput input) {
        QuarkusTransaction.joiningExisting()
                .run(() -> super.blockAccount(input));
    }

    @Override
    public void unBlockAccount(UnBlockAccountInput input) {
        QuarkusTransaction.joiningExisting()
                .run(() -> super.unBlockAccount(input));
    }
}
