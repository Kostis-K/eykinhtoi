package agile.actors.test.model;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class Account implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    private Double balance;
    @Column
    private String currency;
    @Column
    private Date createdAt;

    public Account(){}
    public Account(double initialB, String currency)
    {
        this.balance=initialB;
        this.currency=currency;
        this.createdAt = new Date(new java.util.Date().getTime());
    }
    public Long getId() {
        return id;
    }

    public Double getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

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
        if (!(o instanceof Account))
            return false;
        Account other = (Account) o;
        if (this.id.equals(other.id))
            return true;
        else return false;
    }
}
