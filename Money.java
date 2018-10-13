import java.util.Currency;
import java.util.Locale;

public class Money implements Comparable<Money>{

    //Thrown each time something's wrong with currencies
    public static final class MismatchedCurrencyException extends RuntimeException {
        MismatchedCurrencyException(String message){
            super(message);
        }
    }

    //All internal calculations are done with "cents", displayed amount is in "dollars".
    public double getAmount() {
        return (double)amount / centFactor();
    }

    public Currency getCurrency(){
        return currency;
    }

    //All relevant constructors. Empty objects are forbidden, default constructor set to private.
    public Money(long amount, Currency currency) {
        this.currency = currency;
        this.amount = amount * centFactor();
    }

    public Money(double amount, Currency currency) {
        this.currency = currency;
        this.amount = Math.round(amount * centFactor());
    }

    public Money(long amount) {
        this.currency = DEFAULT_CURRENCY;
        this.amount = amount * centFactor();
    }

    public Money(double amount) {
        this.currency = DEFAULT_CURRENCY;
        this.amount = Math.round(amount * centFactor());
    }

    private Money() {
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        if (amount != money.amount) return false;
        if (currency != null? !currency.equals(money.currency): money.currency != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (int) (amount ^ (amount >>> 32));
        result = 31 * result + (currency != null? currency.hashCode(): 0);
        return result;
    }

    //Basic math operations
    public Money add(Money other) {
        checkCurrenciesMatch(other);
        return newMoney(amount + other.amount);
    }

    public Money subtract(Money other) {
        checkCurrenciesMatch(other);
        return newMoney(amount - other.amount);
    }

    public Money multiply(double arg) {
        return new Money(Math.round(getAmount() * arg), currency);
    }

    public Money divide(double arg) throws ArithmeticException {
        if (arg == 0.0) throw new ArithmeticException("Can't divide by zero!");
        return new Money(Math.round(getAmount() / arg), currency);
    }

    //For comparable<>
    public int compareTo(Money other) {
        checkCurrenciesMatch(other);
        if(amount < other.amount)
            return -1;
        if(amount == other.amount)
            return 0;
        return 1;
    }

    //Basic math relations
    //Greater than
    public boolean gt(Money other) {
        return (compareTo(other) > 0);
    }

    //Greater or equal than
    public boolean gteq(Money other) {
        return (compareTo(other) >= 0);
    }

    //Less than
    public boolean lt(Money other) {
        return (compareTo(other) < 0);
    }

    //Less or equal than
    public boolean lteq(Money other) {
        return (compareTo(other) <= 0);
    }

    /*****************************************************************************/

    private long amount;
    private Currency currency;
    private static final Currency DEFAULT_CURRENCY = Currency.getInstance(Locale.US);

    private int centFactor() {
        int f = 1;
        for(int i = 0; i < currency.getDefaultFractionDigits(); i ++)
            f*=10;
        return f;
    }

    private void checkCurrenciesMatch(Money that) {
        if (!this.currency.equals(that.getCurrency())) {
            throw new MismatchedCurrencyException(
                    that.getCurrency() + " doesn't match the expected currency : " + this.currency
            );
        }
    }

    private Money newMoney(long amount) {
        Money money = new Money();
        money.currency = this.currency;
        money.amount = amount;
        return money;
    }
}
