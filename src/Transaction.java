import java.util.Scanner;

abstract public class Transaction {
    int amount;
    User user;

    public Transaction(int amount, User user) {
        this.amount = amount;
        this.user = user;
    }

    abstract void doTransaction();

    static void depositMenu() {
        System.out.println("enter deposit amount");
        String amount = new Scanner(System.in).nextLine();
        try {
            int intAmount = Integer.parseInt(amount);
            DepositTransaction depositTransaction = new DepositTransaction(intAmount, Main.currentUser);
            depositTransaction.doTransaction();

        } catch (Exception e) {
            System.out.println("exception happened!!");
            System.out.println("please enter int number value");
        }
    }

    static void withdrawMenu() {
        System.out.println("enter withdraw amount");
        String amount = new Scanner(System.in).nextLine();
        try {
            int intAmount = Integer.parseInt(amount);
            WithdrawTransaction withdrawTransaction = new WithdrawTransaction(intAmount, Main.currentUser);
            withdrawTransaction.doTransaction();

        } catch (Exception e) {
            System.out.println("exception happened!!");
            System.out.println("please enter int number value");
        }
    }

    static void transferMenu() {
        System.out.println("enter user id");
        String userID = new Scanner(System.in).nextLine();
        User secondUser = User.isUserExist(userID);
        if ( secondUser != null) {
            System.out.println("enter transfer amount");
            String amount = new Scanner(System.in).nextLine();
            try {
                int intAmount = Integer.parseInt(amount);
                TransferTransaction transferTransaction = new TransferTransaction(intAmount, Main.currentUser,secondUser);
                transferTransaction.doTransaction();

            } catch (Exception e) {
                System.out.println("exception happened!!");
                System.out.println("please enter int number value");
            }
        } else {
            System.out.println("sorry user not exist");
            System.out.println("please check user id and try again");

        }
    }

}
