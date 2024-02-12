package agile.actors.test.services;

import agile.actors.test.model.Account;
import agile.actors.test.model.Transaction;
import agile.actors.test.repositories.AccountRepository;
import agile.actors.test.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionsService
{
    @Autowired
    private TransactionRepository tr;
    @Autowired
    private AccountRepository ar;

    private Map<String, Map<String, Double>> convertRatios;

    @PostConstruct
    private void initializeRatios()
    {
        convertRatios = new HashMap<>();
        convertRatios.put("EUR", new HashMap<>());
        convertRatios.put("GBP", new HashMap<>());
        convertRatios.put("USD", new HashMap<>());
        convertRatios.get("EUR").put("USD", 1.08);
        convertRatios.get("EUR").put("GBP", 0.85);
        convertRatios.get("GBP").put("EUR", 1.17);
        convertRatios.get("GBP").put("USD", 1.26);
        convertRatios.get("USD").put("EUR", 0.93);
        convertRatios.get("USD").put("GBP", 0.79);
    }

    /**
     * Convert number from currency fromC to toC
     * @param fromC
     * @param toC
     * @param number
     * @return
     */
    private double calculate(String fromC, String toC, double number)
    {
        return number*convertRatios.get(fromC).get(toC);
    }

    /**
     * Do a transaction -if possible-
     * If smthing goes wrong (not just our throws) it will rollback
     * @param id_a account to take money from
     * @param id_b account to put money in
     * @param amount
     * @throws RuntimeException
     */
    @Transactional
    public void runTransaction(Long id_a, Long id_b, double amount, String currency) throws RuntimeException
    {
         Optional<Account> a = ar.findById(id_a);
        Optional<Account> b = ar.findById(id_b);
        if (!a.isPresent() && !b.isPresent())
            throw new RuntimeException("There are no "+id_a+" and "+id_b+ " accounts...!!");
        if (!a.isPresent())
             throw new RuntimeException("There is no "+id_a+" account...!!");
        if (!b.isPresent())
            throw new RuntimeException("There is no "+id_b+" account...!!");
        if (a.equals(b))
            throw new RuntimeException("No point... Source and target are the same account...");
        double current_balance_a;
        if (currency != a.get().getCurrency())
            current_balance_a = calculate(a.get().getCurrency(), currency, a.get().getBalance());
        else
            current_balance_a = a.get().getBalance();
        if (current_balance_a < amount)
            throw new RuntimeException("There ain't enough money in it...");
        //let's continue to the actual transaction alright...
        if (currency != a.get().getCurrency())
            a.get().setBalance(a.get().getBalance()-calculate(currency, a.get().getCurrency(), amount));
        else
            a.get().setBalance(a.get().getBalance() - amount);
        if (currency != b.get().getCurrency())
            b.get().setBalance(b.get().getBalance()+calculate(currency, b.get().getCurrency(), amount));
        else
            b.get().setBalance(b.get().getBalance() + amount);
        ar.save(a.get());
        ar.save(b.get());
        tr.save(new Transaction(a.get(), b.get(), amount, currency));
    }

    public String getAccountBalance(Long accountId) throws RuntimeException
    {
        Optional<Account> a = ar.findById(accountId);
        if (!a.isPresent())
            throw new RuntimeException("There is no "+accountId+" account...!!");
        return a.get().getBalance()+a.get().getCurrency();
    }



}
