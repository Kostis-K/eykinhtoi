package agile.actors.test.repositories;

import agile.actors.test.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>
{

}
