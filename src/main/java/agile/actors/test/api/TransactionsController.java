package agile.actors.test.api;

import agile.actors.test.model.Account;
import agile.actors.test.services.AccountsService;
import agile.actors.test.services.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class TransactionsController
{
    @Autowired
    TransactionsService ts;
    @Autowired
    AccountsService as;

    @GetMapping("/hello")
    public String hello()
    {
        return "Hello, this is a bank.";
    }

    @GetMapping("/transaction")
    public String runTransaction(@RequestParam Long source,
                                 @RequestParam Long target,
                                 @RequestParam Double amount,
                                 @RequestParam String currency)
    {
        try {
            ts.runTransaction(source, target, amount, currency);
        }catch (RuntimeException eeaa)
        {   return eeaa.getMessage();}
        return "Transaction done just fine.";
    }

    @GetMapping("/balance")
    public String accountBalance(@PathVariable Long account)
    {
        String result="";
        try {
            result = ts.getAccountBalance(account);
        }catch (RuntimeException eelaa)
        {   return eelaa.getMessage();}
        return result;
    }

    @GetMapping("/accounts")
    public List<Account> getAccounts()
    {
        return as.getAllAccounts();
    }

    @GetMapping("/new_account")
    public Account newAccount(@RequestParam double initialB,
                           @RequestParam String currency)
    {
        return as.newAccount(initialB, currency);
    }
}
