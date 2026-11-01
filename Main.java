public class Main {
    public static void main(String[] args) {
        System.out.println("=== 1. Creating Accounts ===");
        // Alice has 100 RUB, daily withdrawal limit 50 RUB (5000 kopecks)
        BankAccount alice = new BankAccount("Alice", new Balance(100_00, Currency.RUB), 50_00);
        // Bob has 0 RUB, daily withdrawal limit 1000 RUB
        BankAccount bob = new BankAccount("Bob", new Balance(0, Currency.RUB), 1000_00);

        System.out.println(alice);
        System.out.println(bob);

        System.out.println("\n=== 2. Deposit ===");
        alice.deposit(new Balance(20_00, Currency.RUB));
        System.out.println("After depositing 20 RUB to Alice: " + alice.getBalance());

        System.out.println("\n=== 3. Transfer Between Accounts ===");
        // Transfer 30 RUB from Alice to Bob
        alice.transfer(bob, new Balance(30_00, Currency.RUB));
        System.out.println("After transferring 30 RUB:");
        System.out.println("Alice: " + alice.getBalance());
        System.out.println("Bob: " + bob.getBalance());

        System.out.println("\n=== 4. Currency Conversion ===");
        Balance usd = new Balance(10_00, Currency.USD); // 10 USD
        System.out.println("10 USD = " + usd.convertTo(Currency.RUB) + " RUB");

        System.out.println("\n=== 5. Testing Corner Cases (Errors) ===");
        
        // Trying to withdraw more than the daily limit
        try {
            System.out.println("Trying to withdraw 100 RUB (Limit is 50)...");
            alice.withdraw(new Balance(100_00, Currency.RUB));
        } catch (IllegalArgumentException e) {
            System.out.println("Expected Error: " + e.getMessage());
        }

        // Trying to transfer more than the current balance
        try {
            System.out.println("Trying to transfer 9999 RUB from Bob (Insufficient funds)...");
            bob.transfer(alice, new Balance(9999_00, Currency.RUB));
        } catch (IllegalArgumentException e) {
            System.out.println("Expected Error: " + e.getMessage());
        }

        // Trying to deposit into a blocked account
        try {
            System.out.println("Blocking Bob's account and trying to deposit...");
            bob.block();
            bob.deposit(new Balance(10_00, Currency.RUB));
        } catch (IllegalStateException e) {
            System.out.println("Expected Error: " + e.getMessage());
        }

        System.out.println("\n=== Test Completed Successfully! ===");
    }
}