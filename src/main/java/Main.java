public class Main {
    public static void main(String[] args) {
        System.out.println("=== Bank Account Demonstration ===");

        BankAccount acc1 = new BankAccount("ACC-001", new Balance(100000, Currency.RUB), 50000);
        BankAccount acc2 = new BankAccount("ACC-002", new Balance(5000, Currency.USD), 10000);

        System.out.println("Initial Account 1: " + acc1);
        System.out.println("Initial Account 2: " + acc2);
        System.out.println();

        System.out.println("Depositing 200 RUB to Account 1...");
        acc1 = acc1.deposit(new Balance(20000, Currency.RUB));
        System.out.println(acc1);

        System.out.println("\nConverting 10 USD to EUR...");
        Balance usdBalance = new Balance(1000, Currency.USD);
        System.out.println(usdBalance + " converted to " + usdBalance.convert(Currency.EUR));

        System.out.println("\nTransferring 50 USD from Account 2 to Account 1...");
        acc2.transfer(acc1, new Balance(5000, Currency.USD));

        System.out.println("\nWithdrawing 100 RUB from Account 1...");
        acc1 = acc1.withdraw(new Balance(10000, Currency.RUB));
        System.out.println(acc1);

        System.out.println("\nAttempting to withdraw 600 RUB (Limit is 500)...");
        try {
            acc1 = acc1.withdraw(new Balance(60000, Currency.RUB));
        } catch (IllegalArgumentException e) {
            System.out.println("Expected Error: " + e.getMessage());
        }

        System.out.println("\nAttempting to withdraw 5000 USD from Account 2 (Only 50 USD)...");
        try {
            acc2.withdraw(new Balance(500000, Currency.USD));
        } catch (IllegalArgumentException e) {
            System.out.println("Expected Error: " + e.getMessage());
        }
        
        System.out.println("\n=== End of Demonstration ===");
    }
}