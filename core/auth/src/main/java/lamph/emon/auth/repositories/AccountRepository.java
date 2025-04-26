package lamph.emon.auth.repositories;

import lamph.emon.auth.entities.Account;
import lamph.emon.common.repository.DomainRepo;

import java.util.Optional;

public interface AccountRepository extends DomainRepo<Account, String> {

    Optional<Account> findByUsername(String email);
}
