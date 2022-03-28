package banking;

import java.util.Scanner;

public class Main {

    static String fileName;

    public static void main(String[] args) {

        fileName = "card.s3db";

        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        // Creating an SQL database using newDaoDB
        CardDaoDB newDaoDB = new CardDaoDB(fileName);
        newDaoDB.createDatabase(fileName);
        newDaoDB.createTable(fileName);

//        for (int i = 0; i < args.length - 1; i++) {
//            if (args[i].equals("-fileName")) {
//
//                fileName = args[i + 1];
//                break;
//            }

        while (choice != 0) {
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
            choice = scanner.nextInt();

            switch (choice) {

                case 0:
                    System.out.println();
                    System.out.println("Bye!");
                    break;

                case 1:
                    createAccount();
                    break;

                case 2:
                    logIn();
                    break;

                default:
                    System.out.println();
                    System.out.println("Unknown command");
                    break;
            }
        }
    }

    static void printData(Card card) {
        System.out.println();
        System.out.println("Your card has been created");

        System.out.println("Your card number:");
        System.out.println(card.getCardNumber());

        System.out.println("Your card PIN:");
        System.out.println(card.getPin());
    }

    static void createAccount() {
        CardDaoDB app = new CardDaoDB(fileName);
        Card account = new Card();
        // Inserting data into an SQL database
        app.insert(Long.parseLong(account.getCardNumber()), Integer.parseInt(account.getPin()),
                account.getBalance());

        printData(account);
    }

    static void logIn() {

        System.out.println();

        Scanner scanner = new Scanner(System.in);
        CardDaoDB app = new CardDaoDB(fileName);

        boolean loggedIn = false;

        System.out.println("Enter your card number:");
        String numberInput = scanner.nextLine();

        System.out.println("Enter your PIN:");
        String pinInput = scanner.nextLine();

        // Checking correctness of card number & PIN
        if (app.checkNumber(Long.parseLong(numberInput)) && app.checkPin(Integer.parseInt(pinInput))) {
            loggedIn = true;
            System.out.println("You have successfully logged in!");
            menu(Long.parseLong(numberInput));
        }
        if (!loggedIn) {
            System.out.println("Wrong card number or PIN!");
        }
    }

    static void menu(long cardNumber) {
        System.out.println();
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");

        Scanner scanner = new Scanner(System.in);
        CardDaoDB app = new CardDaoDB(fileName);

        int secondChoice = scanner.nextInt();

        switch (secondChoice) {

            case 5:
                // Logging out
                System.out.println();
                System.out.println("You have sucessfully logged out!");
                return;

            case 4:
                // Closing account
                CardDaoDB delete = new CardDaoDB(fileName);
                delete.deleteRow(cardNumber);
                System.out.println();
                System.out.println("The account has been closed!");
                break;

            case 3:
                // Performing a money transfer
                System.out.println();
                System.out.println("Transfer");
                System.out.println("Enter card number:");
                long cardInput = scanner.nextLong();
                boolean correct = true;

                // Checking if number passes Luhn Algorithm
                long numberCheck = cardInput;
                int sum = 0;
                long tempSum, adder = 0;
                boolean odd = false;

                while (numberCheck > 0) {
                    if (odd) {
                        tempSum = (numberCheck % 10) * 2;
                        if (tempSum > 9) {
                            while (tempSum > 0) {
                                adder += tempSum % 10;
                                tempSum /= 10;
                            }
                            tempSum = adder;
                        }
                        odd = false;
                    } else {
                        tempSum = numberCheck % 10;
                        odd = true;
                    }
                    adder = 0;
                    sum += tempSum;
                    numberCheck /= 10;
                }

                if (sum % 10 != 0) {
                    correct = false;
                }

                if (!correct) {
                    System.out.println("Probably you made mistake in the card number. Please try again!");
                    break;
                } else {
                    if (!app.checkNumber(cardInput)) {
                        System.out.println("Such a card does not exist.");
                        break;
                    } else {
                        System.out.println("Enter how much money you want to transfer:");
                        int moneyTransfer = scanner.nextInt();

                        if (app.returnBalance(cardNumber) < moneyTransfer) {
                            System.out.println("Not enough money!");
                            break;
                        } else {
                            CardDaoDB update = new CardDaoDB(fileName);
                            update.update(cardNumber, cardInput, moneyTransfer);
                            System.out.println("Success!");
                        }
                    }
                    break;
                }

            case 2:
                // Adding income
                System.out.println();
                System.out.println("Enter income:");
                int income = scanner.nextInt();

                CardDaoDB update = new CardDaoDB(fileName);
                update.addIncome(cardNumber, income);
                System.out.println("Income was added!");
                break;

            case 1:
                // Checking balance
                System.out.println();
                System.out.println("Balance: " + app.returnBalance(cardNumber));
                break;

            case 0:
                // Exiting application
                System.out.println();
                System.out.println("Bye!");
                System.exit(0);

            default:
                System.out.println();
                System.out.println("Unknown command");
                break;
        }
        menu(cardNumber);
    }

//    public static void createDatabase(String fileName) {
//        // Connection string
//        String url = "jdbc:sqlite:" + fileName;
//
//        try (Connection conn = DriverManager.getConnection(url)) {
//            if (conn != null) {
//                DatabaseMetaData meta = conn.getMetaData();
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public static void createTable(String fileName) {
//        // Connection string
//        String url = "jdbc:sqlite:" + fileName;
//        // Creating table
//        String sql = "CREATE TABLE IF NOT EXISTS card (" +
//                "  `id` INTEGER NOT NULL PRIMARY KEY," +
//                "  `number` TEXT," +
//                "  `pin` TEXT," +
//                "  `balance` INTEGER DEFAULT 0"
//                + ");";
//
//        try (Connection conn = DriverManager.getConnection(url);
//             Statement stmt = conn.createStatement()) {
//            // Create new table
//            stmt.execute(sql);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
}