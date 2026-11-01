import java.util.Objects;

public class BankAccount {
    private final String ownerName;
    private Balance balance;
    private final long withdrawalLimitKopecks; 
    private boolean blocked;

    public BankAccount(String ownerName, Balance initialBalance, long withdrawalLimitKopecks) {
        this.ownerName = Objects.requireNonNull(ownerName, "Owner name required");
        this.balance = Objects.requireNonNull(initialBalance, "Initial balance required");
        if (withdrawalLimitKopecks < 0) {
            throw new IllegalArgumentException("Limit cannot be negative");
        }
        this.withdrawalLimitKopecks = withdrawalLimitKopecks;
    }

    public Balance getBalance() { return balance; }
    public String getOwnerName() { return ownerName; }
    public boolean isBlocked() { return blocked; }

    public void block()   { this.blocked = true; }
    public void unblock() { this.blocked = false; }

    public void deposit(Balance amount) {
        Objects.requireNonNull(amount, "Amount required");
        if (blocked) throw new IllegalStateException("Account is blocked");
        this.balance = this.balance.add(amount);
    }

    public void withdraw(Balance amount) {
        Objects.requireNonNull(amount, "Amount required");
        if (blocked) throw new IllegalStateException("Account is blocked");
        if (amount.getAmountKopecks() > withdrawalLimitKopecks) {
            throw new IllegalArgumentException("Exceeds withdrawal limit");
        }
        this.balance = this.balance.subtract(amount);
    }

    public void transfer(BankAccount to, Balance amount) {
        Objects.requireNonNull(to, "Target account required");
        Objects.requireNonNull(amount, "Amount required");
        if (this == to) throw new IllegalArgumentException("Cannot transfer to same account");

        this.withdraw(amount); 
        to.deposit(amount.convertTo(to.getBalance().getCurrency()));
    }

    @Override
    public String toString() {
        return String.format("BankAccount{owner='%s', balance=%s, blocked=%s}", ownerName, balance, blocked);
    }
}
