package banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateApp {

    private final String fileName;

    protected UpdateApp(String fileName) {
        this.fileName = fileName;
    }

    private Connection connect () {

        String url = "jdbc:sqlite:" + fileName;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;

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
