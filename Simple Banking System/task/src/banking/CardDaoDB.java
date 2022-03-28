package banking;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CardDaoDB {

    private final String fileName;

    protected CardDaoDB(String fileName) {
        this.fileName = fileName;
    }

    protected Connection connect () {
        String url = "jdbc:sqlite:" + fileName;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createDatabase(String fileName) {
        // Connection string
        String url = "jdbc:sqlite:" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTable(String fileName) {
        // Connection string
        String url = "jdbc:sqlite:" + fileName;
        // Creating table
        String sql = "CREATE TABLE IF NOT EXISTS card (" +
                "  `id` INTEGER NOT NULL PRIMARY KEY," +
                "  `number` TEXT," +
                "  `pin` TEXT," +
                "  `balance` INTEGER DEFAULT 0"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // Create new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean checkNumber (long cardNumber) {
        boolean isCard = false;
        String sql = "SELECT number FROM card WHERE number=?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Setting parameter
            pstmt.setLong(1, cardNumber);
            ResultSet rs = pstmt.executeQuery();

            isCard = rs.next();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return isCard;
    }

    public boolean checkPin (long pinNumber) {
        boolean isCard = false;
        String sql = "SELECT pin FROM card WHERE pin=?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Setting parameter
            pstmt.setLong(1, pinNumber);
            ResultSet rs = pstmt.executeQuery();

            isCard = rs.next();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return isCard;
    }

    public int returnBalance (long cardNumber) {
        String sql = "SELECT number, balance FROM card WHERE number=?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Setting the value
            pstmt.setLong(1, cardNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("balance");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public void insert (long number, int pin, int balance) {
        String sql = "INSERT INTO card (number,pin,balance) VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Setting parameters
            pstmt.setLong(1, number);
            pstmt.setInt(2, pin);
            pstmt.setInt(3, balance);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteRow(long cardNumber) {
        String sql = "DELETE FROM card WHERE number = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Setting parameter
            pstmt.setLong(1,cardNumber);

            // Executing the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(long senderNumber, long recipientNumber, int money) {
        String sql = "UPDATE card SET balance=(balance-?) WHERE number=?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Setting parameters
            pstmt.setInt(1, money);
            pstmt.setLong(2, senderNumber);

            // Updating
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String nextSql = "UPDATE card SET balance=(balance+?) WHERE number=?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(nextSql)) {

            // Setting parameters
            pstmt.setInt(1, money);
            pstmt.setLong(2, recipientNumber);

            // Updating
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addIncome(long cardNumber, int money) {
        String sql = "UPDATE card SET balance=(balance+?) WHERE number=?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Setting parameters
            pstmt.setInt(1, money);
            pstmt.setLong(2, cardNumber);

            // Updating
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}