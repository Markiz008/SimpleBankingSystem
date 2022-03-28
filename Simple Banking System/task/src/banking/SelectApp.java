package banking;

import java.sql.*;

public class SelectApp {

    private final String fileName;

    protected SelectApp(String fileName) {
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
}
