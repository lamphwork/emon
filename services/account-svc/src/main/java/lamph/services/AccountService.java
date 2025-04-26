package lamph.services;

import io.micronaut.transaction.TransactionDefinition;
import io.micronaut.transaction.TransactionOperations;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import lamph.emon.auth.repositories.AccountRepository;
import lamph.emon.auth.usecase.AccountUseCase;
import lamph.emon.auth.usecase.params.CreateAccountInput;

import java.sql.Connection;

@Singleton
@Transactional
public class AccountService extends AccountUseCase {

    final TransactionOperations<Connection> txOperations;

    public AccountService(final AccountRepository accountRepository, TransactionOperations<Connection> txOperations) {
        super(accountRepository);
        this.txOperations = txOperations;
    }


    @Override
    public String createAccount(CreateAccountInput input) {
        return txOperations.execute(
                TransactionDefinition.DEFAULT,
                status -> super.createAccount(input)
        );
    }
}
