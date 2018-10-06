import java.util.*;
import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;
import java.math.RoundingMode;

public final class Money implements Comparable<Money>{

    //Thrown each time something's wrong with currencies
    public static final class MismatchedCurrencyException extends RuntimeException {
        MismatchedCurrencyException(String message){
            super(message);
        }
    }

    //All relevant constructors. Empty Money objects are forbidden (default constructor is private).
    public Money(BigDecimal amount, Currency currency, RoundingMode roundingStyle) {
        this.amount = amount;
        this.currency = currency;
        this.rounding = roundingStyle;
    }

    public Money(BigDecimal amount, Currency currency){
        this(amount, currency, DEFAULT_ROUNDING);
    }

    public Money(BigDecimal amount){
        this(amount, DEFAULT_CURRENCY, DEFAULT_ROUNDING);
    }

    private Money() {}

    //Getters for all fields. Objects are immutable, thus no setters
    public BigDecimal getAmount() { return amount; }

    public Currency getCurrency() { return currency; }

    public RoundingMode getRoundingStyle() { return rounding; }

    //Checks if two Money objects have matching currencies
    public boolean isSameCurrencyAs(Money that){
        boolean result = false;
        if ( that != null ) {
            result = this.currency.equals(that.currency);
        }
        return result;
    }

    //Some basic math relations with money
    public boolean isPlus(){
        return amount.compareTo(ZERO) > 0;
    }

    public boolean isMinus(){
        return amount.compareTo(ZERO) <  0;
    }

    public boolean isZero(){
        return amount.compareTo(ZERO) ==  0;
    }

    //Basic math operations with money. Currencies must match
    public Money plus(Money that){
        checkCurrenciesMatch(that);
        return new Money(amount.add(that.amount), currency, rounding);
    }

    public Money minus(Money that){
        checkCurrenciesMatch(that);
        return new Money(amount.subtract(that.amount), currency, rounding);
    }

    //Insensitive to scale. Not the same as 'equals' method
    public boolean eq(Money that) {
        checkCurrenciesMatch(that);
        return compareAmount(that) == 0;
    }

    //Greater than
    public boolean gt(Money that) {
        checkCurrenciesMatch(that);
        return compareAmount(that) > 0;
    }

    //Greater than or equal to
    public boolean gteq(Money that) {
        checkCurrenciesMatch(that);
        return compareAmount(that) >= 0;
    }

    //Less than
    public boolean lt(Money that) {
        checkCurrenciesMatch(that);
        return compareAmount(that) < 0;
    }

    //Less than or equal to
    public boolean lteq(Money that) {
        checkCurrenciesMatch(that);
        return compareAmount(that) <= 0;
    }

    //Multiplication and division with int and double takes scale from 'this' Money
    public Money times(int aFactor){
        BigDecimal factor = new BigDecimal(aFactor);
        BigDecimal newAmount = amount.multiply(factor);
        return new Money(newAmount, currency, rounding);
    }

    public Money times(double factor){
        BigDecimal newAmount = amount.multiply(asBigDecimal(factor));
        newAmount = newAmount.setScale(getNumDecimalsForCurrency(), rounding);
        return  new Money(newAmount, currency, rounding);
    }

    public Money div(int aDivisor){
        BigDecimal divisor = new BigDecimal(aDivisor);
        BigDecimal newAmount = amount.divide(divisor, rounding);
        return new Money(newAmount, currency, rounding);
    }

    public Money div(double divisor){
        BigDecimal newAmount = amount.divide(asBigDecimal(divisor), rounding);
        return new Money(newAmount, currency, rounding);
    }

    //Absolute value
    public Money abs(){
        return isPlus() ? this : times(-1);
    }

    public Money negate(){
        return times(-1);
    }

    //Some basic string representation. Can be modified with currency codes
    public String toString(){
        return amount.toPlainString() + " " + currency.getSymbol();
    }

    //'Proper' equals method, is sensitive to scale
    public boolean equals(Object aThat){
        if (this == aThat) return true;
        if (! (aThat instanceof Money) ) return false;
        Money that = (Money)aThat;
        return this.amount.equals(that.amount) && this.currency.equals(that.currency) && this.rounding.equals(that.rounding);
    }

    //For Comparable<>
    public int compareTo(Money that) {
        final int EQUAL = 0;

        if ( this == that ) return EQUAL;

        //the object fields are never null
        int comparison = this.amount.compareTo(that.amount);
        if ( comparison != EQUAL ) return comparison;

        comparison = this.currency.getCurrencyCode().compareTo(
                that.currency.getCurrencyCode()
        );
        if ( comparison != EQUAL ) return comparison;


        comparison = this.rounding.compareTo(that.rounding);
        if ( comparison != EQUAL ) return comparison;

        return EQUAL;
    }

    //Private fields and methods

    private BigDecimal amount;
    private Currency currency;
    private RoundingMode rounding;

    //Can make an init method to set those at runtime; no need for now
    private static final Currency DEFAULT_CURRENCY = Currency.getInstance(Locale.US);
    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

    private int getNumDecimalsForCurrency(){
        return currency.getDefaultFractionDigits();
    }

    private void checkCurrenciesMatch(Money aThat){
        if (! this.currency.equals(aThat.getCurrency())) {
            throw new MismatchedCurrencyException(
                    aThat.getCurrency() + " doesn't match the expected currency : " + currency
            );
        }
    }

    // Ignores scale: 0 same as 0.00
    private int compareAmount(Money aThat){
        return this.amount.compareTo(aThat.amount);
    }

    private BigDecimal asBigDecimal(double aDouble){
        String asString = Double.toString(aDouble);
        return new BigDecimal(asString);
    }
}
