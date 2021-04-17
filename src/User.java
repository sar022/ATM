import java.util.LinkedList;
import java.util.Scanner;

public class User {
   private String userID;
   private String userPin;
   private int accountBalance;
   private LinkedList<Transaction> transactionsHistoryList;

    public User(String userID, String userPin, int accountBalance) {
        this.userID = userID;
        this.userPin = userPin;
        this.accountBalance = accountBalance;
        this.transactionsHistoryList = new LinkedList<>();
    }

    void addToAccountBalance (int amount){
        accountBalance+=amount;
    }
    boolean removeFromAccountBalance (int amount){
        if (accountBalance - amount >= 0){
            accountBalance-=amount;
            return true;
        }
        return false;
    }
    public String getAccountBalance() {
        return String.valueOf(accountBalance);
    }
    void addTransaction (Transaction transaction){
        transactionsHistoryList.add(transaction);
    }
    boolean checkUser (String id,String password){
        return id.equals(userID) && password.equals(userPin);
    }

    void userRun (){
        while (true){
            Scanner scanner = new Scanner(System.in);
            System.out.println();
            System.out.println("press 1 to deposit");
            System.out.println("press 2 to withdraw");
            System.out.println("press 3 to transfer money");
            System.out.println("press 4 to print transactions history");
            System.out.println("press 5 to logout");
            System.out.println("press 6 to quit the program");
            String input = scanner.nextLine().trim();
            if (input.equals("1") )
                Transaction.depositMenu();
            if (input.equals("2") )
                Transaction.withdrawMenu();
            if (input.equals("3") )
                Transaction.transferMenu();
            if (input.equals("4") )
                this.printTransactionsHistory();
            if (input.equals("5") )
               return;
            if (input.equals("6")){
                System.out.println("thank you for using our app");
                System.exit(0);
            }


        }
    }
    void printTransactionsHistory (){
        for (Transaction transaction : transactionsHistoryList){
            if (transaction instanceof TransferTransaction)
                System.out.println("transaction from : "+transaction.user.getId() + " to : "+ ((TransferTransaction) transaction).secondUser.getId() + " amount = "+transaction.amount);
        }
        for (Transaction transaction : transactionsHistoryList){
            if (transaction instanceof DepositTransaction)
                System.out.println("Deposit Transaction amount = " +transaction.amount );
        }
        for (Transaction transaction : transactionsHistoryList){
            if (transaction instanceof WithdrawTransaction)
                System.out.println("Withdraw Transaction amount = " +transaction.amount );
        }
    }
    static void adminRun (){
        while (true){
            System.out.println();
            System.out.println("press 1 to add new user");
            System.out.println("press 2 to logout");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().trim();
            if (input.equals("1") )
                addNewUserMenu ();
            if (input.equals("2"))
                return;
        }
    }
  private static void addNewUserMenu (){
      System.out.println("please enter user id");
      Scanner scanner = new Scanner(System.in);
      String id = scanner.nextLine();
      if (!id.matches("[0-9]+")){
          System.out.println("id must be only numbers");
          System.out.println("please try again with another id");
          return;
      }
      if (isUserExist(id) != null){
          System.out.println("this id is already exist");
          System.out.println("please try again with another id");
          return;
      }
      System.out.println("please enter user pin");
      String pin = scanner.nextLine();
      DBHelper.addNewUser(id,pin);
      Main.allUser.add(new User(id,pin,0));
      System.out.println("user added successfully");

    }
   static User isUserExist(String id){
        for (User user : Main.allUser)
            if(user.userID.equals(id))
                return user;
        return null;
    }

    public String getId() {
        return userID;
    }
}
