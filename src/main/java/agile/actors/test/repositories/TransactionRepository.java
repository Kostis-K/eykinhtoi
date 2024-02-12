package agile.actors.test.repositories;

import agile.actors.test.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long>
{
    Transaction save(Transaction trans);
}
