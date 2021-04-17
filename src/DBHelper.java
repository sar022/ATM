import java.sql.*;
import java.util.LinkedList;

public class DBHelper {
    static Connection conn;

    static void connectToDatabase() {
        String dbURL = "jdbc:sqlserver://localhost;integratedSecurity=true;";
        try {
            conn = DriverManager.getConnection(dbURL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static LinkedList<User> getAllUser() {
        LinkedList<User> users = new LinkedList<>();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = stmt.executeQuery("select * from ATM.dbo.Users");
            while (rs.next()) {
                String id = rs.getString("id");
                String password = rs.getString("password");
                String accountBalance = rs.getString("accountBalance");
                users.add(new User(id, password, Integer.parseInt(accountBalance)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void addNewUser(String id, String pin) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {

            stmt.executeUpdate("insert into ATM.dbo.Users values (" + id + "," + "'" + pin + "'" + "," + 0 + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateUser(User user) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {

            stmt.executeUpdate("update ATM.dbo.Users set accountBalance = " + user.getAccountBalance() + "where id = " + user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addTransaction(Transaction transaction) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (transaction instanceof TransferTransaction)
                stmt.executeUpdate("insert into ATM.dbo.twoUsersTransactions values (" + transaction.amount + "," + transaction.user.getId() + "," + ((TransferTransaction) transaction).secondUser.getId() + ")");
            else if (transaction instanceof  WithdrawTransaction)
                stmt.executeUpdate("insert into ATM.dbo.oneUserTransactions values (" + -transaction.amount + "," + transaction.user.getId() + ")");
            else
                stmt.executeUpdate("insert into ATM.dbo.oneUserTransactions values (" + transaction.amount + "," + transaction.user.getId() + ")");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  static void readTransactionList (){
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = stmt.executeQuery("select * from ATM.dbo.twoUsersTransactions");
            while (rs.next()) {
                int amount = rs.getInt("amount");
                String firstUserId = rs.getString("firstUserId");
                String secondUserId = rs.getString("secondUserId");
                User firstUser = Main.getUserById(firstUserId);
                User secondUser = Main.getUserById(secondUserId);
                Transaction transaction = new TransferTransaction(amount,firstUser,secondUser);
                firstUser.addTransaction(transaction);
                secondUser.addTransaction(transaction);
            }
            ResultSet rs2 = stmt.executeQuery("select * from ATM.dbo.oneUserTransactions");
            while (rs2.next()) {
                int amount = rs2.getInt("amount");
                String firstUserId = rs2.getString("userId");
                User firstUser = Main.getUserById(firstUserId);
                Transaction transaction = null;
                if (amount > 0)
                 transaction = new DepositTransaction(amount,firstUser);
                else
                 transaction = new WithdrawTransaction(-amount,firstUser);

                firstUser.addTransaction(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
