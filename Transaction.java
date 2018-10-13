import java.util.Objects;

public class Transaction {

    public Transaction(Account from, Account where, Money balanceChange){
        this.id = Math.round(Math.random()); //Need something less stupid
        this.from = from;
        this.where = where;
        this.balanceChange = balanceChange;
    }

    public int commitTransaction(){
        if (from == null || where == null) {
            System.out.println("Invalid transation participants.\n");
            return -1;
        }

        //I hope we can't create accounts with null money :'(

        //No checks if the from account actually has the money. Maybe a credit card?
        from.getBalance().subtract(this.balanceChange);
        where.getBalance().add(this.balanceChange);

        //Write to database here?

        return 0;
    }

    public int hashCode() {
        return Objects.hash(id, from, where, balanceChange);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Transaction transaction = (Transaction)o;

        if (this.id != transaction.id || !this.balanceChange.equals(transaction.balanceChange) ||
            !this.where.equals(transaction.where) || !this.from.equals(transaction.from)) return false;

        return true;
    }

    /************************************************************/

    private long id;
    private Money balanceChange;
    private Account from, where;
}
