package agile.actors.test.services;

import agile.actors.test.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
//@RunWith(SpringRunner.class)
//@EnableConfigurationProperties
class TransactionsTests {
    @Autowired
    private AccountsService ac;
    @Autowired
    private TransactionsService tr;
    private List<Account> accounts;

    @BeforeEach
    public void initializeDb()
    {
        accounts = new ArrayList<Account>();
        accounts.add(ac.newAccount(1000, "EUR"));
        accounts.add(ac.newAccount(500, "USD"));
        accounts.add(ac.newAccount(3, "GBP"));
        accounts.add(ac.newAccount(50, "EUR"));
    }
    @Test
    public void testTransactionsService()
    {
        Assertions.assertEquals(accounts.size(), ac.getAllAccounts().size());
        Assertions.assertEquals(1000, accounts.get(0).getBalance());
        tr.runTransaction(accounts.get(0).getId(), accounts.get(3).getId(), 500, "EUR");
        accounts.set(0, ac.getAccount(accounts.get(0).getId()));
        accounts.set(3, ac.getAccount(accounts.get(3).getId()));
        Assertions.assertEquals(500, accounts.get(0).getBalance());
        Assertions.assertEquals(550, accounts.get(3).getBalance());
        try{
            tr.runTransaction(accounts.get(2).getId(), accounts.get(1).getId(), 50, "USD");}
        catch (RuntimeException ok) {;}
        accounts.set(1, ac.getAccount(accounts.get(1).getId()));
        accounts.set(2, ac.getAccount(accounts.get(2).getId()));
        Assertions.assertEquals(500, accounts.get(1).getBalance());
        Assertions.assertEquals(3, accounts.get(2).getBalance());
        tr.runTransaction(accounts.get(1).getId(), accounts.get(2).getId(), 100, "USD");
        accounts.set(1, ac.getAccount(accounts.get(1).getId()));
        accounts.set(2, ac.getAccount(accounts.get(2).getId()));
        Assertions.assertEquals(400, accounts.get(1).getBalance());
        Assertions.assertEquals(3+100*0.79, accounts.get(2).getBalance());
    }



}

