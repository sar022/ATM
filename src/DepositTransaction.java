import java.util.Scanner;

public class DepositTransaction extends Transaction {
    public DepositTransaction(int amount, User user) {
        super(amount, user);
    }

    @Override
    void doTransaction() {
        user.addToAccountBalance(amount);
        user.addTransaction(this);
        //
        DBHelper.updateUser(user);
        DBHelper.addTransaction(this);
        //
        System.out.println(amount +" added to your account");
        System.out.println("your current account balance is " + user.getAccountBalance());
    }



}
