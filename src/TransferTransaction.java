public class TransferTransaction extends Transaction {
    User secondUser;

    public TransferTransaction(int amount, User user, User secondUser) {
        super(amount, user);
        this.secondUser = secondUser;
    }

    @Override
    void doTransaction() {
       if ( user.removeFromAccountBalance(amount)){
           secondUser.addToAccountBalance(amount);
           user.addTransaction(this);
           secondUser.addTransaction(this);
           //
           DBHelper.updateUser(user);
           DBHelper.updateUser(secondUser);
           DBHelper.addTransaction(this);
           //
       } else {
           System.out.println("you cant do this withdraw because amount is bigger than your account balance");
       }
        System.out.println("your current account balance is " + user.getAccountBalance());

    }
}
