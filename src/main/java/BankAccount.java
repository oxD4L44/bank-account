public class BankAccount {
    private final String accountNumber;
    private final Balance balance;
    private final long withdrawalLimit;

    public BankAccount(String accountNumber, Balance balance, long withdrawalLimit) {
        if (accountNumber == null || accountNumber.isBlank()) throw new IllegalArgumentException("Invalid account number");
        if (balance == null) throw new IllegalArgumentException("Balance cannot be null");
        if (withdrawalLimit < 0) throw new IllegalArgumentException("Limit cannot be negative");
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.withdrawalLimit = withdrawalLimit;
    }

    public String getAccountNumber() { return accountNumber; }
    public Balance getBalance() { return balance; }
    public long getWithdrawalLimit() { return withdrawalLimit; }

    public BankAccount deposit(Balance amount) {
        Balance newBalance = this.balance.add(amount);
        return new BankAccount(accountNumber, newBalance, withdrawalLimit);
    }

    public BankAccount withdraw(Balance amount) {
        if (amount.getCurrency() != this.balance.getCurrency()) {
            amount = amount.convert(this.balance.getCurrency());
        }
        if (amount.getAmount() > withdrawalLimit) throw new IllegalArgumentException("Withdrawal exceeds limit");
        Balance newBalance = this.balance.subtract(amount);
        return new BankAccount(accountNumber, newBalance, withdrawalLimit);
    }

    public void transfer(BankAccount target, Balance amount) {
        if (target == null) throw new IllegalArgumentException("Target account cannot be null");
        BankAccount updatedSource = this.withdraw(amount);
        Balance convertedAmount = amount.convert(target.getBalance().getCurrency());
        BankAccount updatedTarget = target.deposit(convertedAmount);
        
        System.out.println("Transfer successful: " + amount + " from " + this.accountNumber + " to " + target.getAccountNumber());
        System.out.println("Source balance: " + updatedSource.getBalance());
        System.out.println("Target balance: " + updatedTarget.getBalance());
    }

    @Override
    public String toString() {
        return "Account: " + accountNumber + " | Balance: " + balance + " | Limit: " + (withdrawalLimit / 100.0) + " RUB";
    }
}