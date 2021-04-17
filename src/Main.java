import java.util.LinkedList;
import java.util.Scanner;

public class Main {
   static LinkedList<User> allUser;
   static User currentUser;
    Main (){
       allUser =  DBHelper.getAllUser();
       DBHelper.readTransactionList();
    }
    public static void main(String[] args) {
        System.out.println("welcome in our ATM APP ");
        DBHelper.connectToDatabase();

        Main mainRun = new Main();
        while (true){
            mainRun.loginMenu();
        }

    }
    void loginMenu(){
        System.out.println("please login to continue");
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter your id");
        String id = scanner.nextLine();
        System.out.println("please enter your pin");
        String pin = scanner.nextLine();
        if (isAdminCheck(id,pin)){
            System.out.println("Admin logged in");
            User.adminRun();
            return;
        }
        User user = login(id,pin);
        if (user != null){
            System.out.println("user logged in");
            System.out.println("your current account balance is " + user.getAccountBalance());

            currentUser = user;
            user.userRun();
        }
        System.out.println("cant login in");
        System.out.println("please check your id or your password and try again");
    }
    boolean isAdminCheck (String id,String password){
        return id.equals("admin") && password.equals("admin");
    }
    User login(String id,String password){
        for (User user : allUser) {
            if (user.checkUser(id,password))
                return user;
        }
        return null;
    }
  static   User getUserById (String id){
        for (User user : allUser) {
            if (user.getId().equals(id))
                return user;
        }
        return null;
    }

}
