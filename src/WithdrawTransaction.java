public class WithdrawTransaction  extends Transaction{

    public WithdrawTransaction(int amount, User user) {
        super(amount, user);
    }

    @Override
    void doTransaction() {
       if ( user.removeFromAccountBalance(amount)){
           user.addTransaction(this);
           DBHelper.updateUser(user);
           DBHelper.addTransaction(this);
           System.out.println(amount +" pulled from your account");
       } else {
           System.out.println("you cant do this withdraw because amount is bigger than your account balance");
       }
        System.out.println("your current account balance is " + user.getAccountBalance());
    }
}
