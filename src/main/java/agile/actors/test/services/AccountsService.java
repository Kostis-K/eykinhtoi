package agile.actors.test.services;

import agile.actors.test.model.Account;
import agile.actors.test.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountsService
{
    @Autowired
    private AccountRepository ar;

    public List<Account> getAllAccounts()
    {
        return ar.findAll();
    }

    public Account getAccount(Long id)
    {
        return ar.findById(id).get();
    }

    public Account newAccount(double initialB, String currency)
    {
        Account newAcc = new Account(initialB, currency);
        ar.save(newAcc);
        return newAcc;
    }
}
