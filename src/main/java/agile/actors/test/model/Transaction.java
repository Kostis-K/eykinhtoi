package agile.actors.test.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Transaction implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    private Account sourceAccount;
    @OneToOne(fetch = FetchType.EAGER)
    private Account targetAccount;
    @Column
    private Double amount;
    @Column
    private String currency;

    //@Column
    //private Date doneAt;
    public Transaction(){}

    public Transaction(Account source, Account target, double amount, String currency)
    {
        this.sourceAccount = source;
        this.targetAccount = target;
        this.amount = amount;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccountId(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(Account targetAccount) {
        this.targetAccount = targetAccount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    /*public Date getDoneAt() {
        return doneAt;
    }

    public void setDoneAt(Date doneAt) {
        this.doneAt = doneAt;
    }*/
    @Override
    public final int hashCode()
    {
        return this.id.hashCode();
    }
    @Override
    public boolean equals(Object o)
    {
        if (o == this)
            return true;
        if (!(o instanceof Transaction))
            return false;
        Transaction other = (Transaction) o;
        if (this.id.equals(other.id))
            return true;
        else return false;
    }
}
