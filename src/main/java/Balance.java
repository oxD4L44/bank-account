public final class Balance {
    private final long amount;
    private final Currency currency;

    public Balance(long amount, Currency currency) {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");
        if (currency == null) throw new IllegalArgumentException("Currency cannot be null");
        this.amount = amount;
        this.currency = currency;
    }

    public long getAmount() { return amount; }
    public Currency getCurrency() { return currency; }

    public Balance add(Balance other) {
        if (other == null) throw new IllegalArgumentException("Other balance cannot be null");
        if (this.currency != other.currency) throw new IllegalArgumentException("Currencies must match");
        return new Balance(this.amount + other.amount, this.currency);
    }

    public Balance subtract(Balance other) {
        if (other == null) throw new IllegalArgumentException("Other balance cannot be null");
        if (this.currency != other.currency) throw new IllegalArgumentException("Currencies must match");
        if (this.amount < other.amount) throw new IllegalArgumentException("Insufficient funds");
        return new Balance(this.amount - other.amount, this.currency);
    }

    public Balance convert(Currency targetCurrency) {
        if (targetCurrency == null) throw new IllegalArgumentException("Target currency cannot be null");
        if (this.currency == targetCurrency) return this;
        long rublesInKopecks = this.amount * this.currency.getPriceInRublesKopecks();
        long convertedAmount = rublesInKopecks / targetCurrency.getPriceInRublesKopecks();
        return new Balance(convertedAmount, targetCurrency);
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", (double) amount / 100, currency.getSymbol());
    }
}