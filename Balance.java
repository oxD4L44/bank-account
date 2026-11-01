import java.util.Objects;

public final class Balance {
    private final long amountKopecks;
    private final Currency currency;

    public Balance(long amountKopecks, Currency currency) {
        if (amountKopecks < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.currency = Objects.requireNonNull(currency, "Currency cannot be null");
        this.amountKopecks = amountKopecks;
    }

    public long getAmountKopecks() { 
        return amountKopecks; 
    }
    
    public Currency getCurrency() { 
        return currency; 
    }

    public Balance add(Balance other) {
        Objects.requireNonNull(other, "Other balance cannot be null");
        if (this.currency != other.currency) {
            throw new IllegalArgumentException("Cannot add different currencies");
        }
        return new Balance(this.amountKopecks + other.amountKopecks, this.currency);
    }

    public Balance subtract(Balance other) {
        Objects.requireNonNull(other, "Other balance cannot be null");
        if (this.currency != other.currency) {
            throw new IllegalArgumentException("Cannot subtract different currencies");
        }
        if (other.amountKopecks > this.amountKopecks) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        return new Balance(this.amountKopecks - other.amountKopecks, this.currency);
    }

    public Balance convertTo(Currency target) {
        Objects.requireNonNull(target, "Target currency cannot be null");
        if (target == this.currency) return this;

        long inRubKopecks = this.amountKopecks * this.currency.getPriceInRublesKopecks();
        long result = inRubKopecks / target.getPriceInRublesKopecks();
        return new Balance(result, target);
    }

    @Override
    public String toString() {
        long major = amountKopecks / 100;
        long minor = amountKopecks % 100;
        return String.format("%d.%02d %s", major, minor, currency.getSymbol());
    }
}