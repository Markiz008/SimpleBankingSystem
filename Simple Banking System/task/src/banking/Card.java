package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Card {

    Scanner scanner = new Scanner(System.in);

    Random random = new Random();

    private String cardNumber;
    private String cardPin;
    private List<String> cardList = new ArrayList<String>();

    public String getNewCardNumber() {
        return cardNumber;
    }

    public String getNewCardPin() {
        return cardPin;
    }

    public String newCardNumber(String cardNumber) {

        String bin = "400000";

        int lowAccNumber = 100000;
        int hiAccNumber = 999999;
        int accNumber = random.nextInt(hiAccNumber - lowAccNumber + 1) + lowAccNumber;

        int lowCheckSum = 1000;
        int hiCheckSum = 9999;
        int checkSum = random.nextInt(hiCheckSum - lowCheckSum + 1) + lowCheckSum;

        cardNumber = bin + String.valueOf(accNumber) + String.valueOf(checkSum);

        return cardNumber;
    }

    public String newCardPin(String cardPin) {

        int low = 1000;
        int hi = 9999;
        cardPin = String.valueOf(random.nextInt(hi - low + 1) + low);

        return cardPin;
    }

    public void cardList(String cardNumber, String cardPin) {

        cardList.add(cardNumber);
        cardList.add(cardPin);
    }

    public static boolean luhnCheck(String cardNumber) {

        int sum = 0;

        boolean alternate = false;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {

            int n = Integer.parseInt(cardNumber.substring(i, i + 1));

            if (alternate) {
                n *= 2;

                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }

            sum += n;

            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }


    public void pinCheck() {

        System.out.println("Enter your card number:");
        String cardNumberCheck = scanner.next();
        
        System.out.println("Enter your PIN:");
        String pinNumberCheck = scanner.next();

        for (String number : cardList) {

            if (number.equals(cardNumberCheck) && cardList.get(cardList.indexOf(number) + 1).equals(pinNumberCheck)) {
                System.out.println("You have successfully logged in!");
                accountMenu();

            } else {
                System.out.println("Wrong card number or PIN!");
            }
        }
    }

    public void accountMenu() {

        String accountMenuSelect = "";

        while (!accountMenuSelect.equals("0")) {

            System.out.println("1. Balance \n" + "2. Log out \n" + "0. Exit");

            accountMenuSelect = scanner.next();

            switch (accountMenuSelect) {

                case "1":
                    System.out.println("Balance: 0");
                    break;

                case "2":
                    System.out.println("You have successfully logged out!");
                    mainMenu();
                    break;

                case "0":
                    System.out.println("Bye!");
                    break;
            }
        }
    }

    public void mainMenu() {

        String mainMenuSelect = " ";

        while (!mainMenuSelect.equals("0")) {

            System.out.println("1. Create an account \n" + "2. Log into account \n" + "0. Exit");

            mainMenuSelect = scanner.next();

            if (mainMenuSelect.equals("1")) {

                System.out.println("Your card has been created");
                cardNumber = newCardNumber(cardNumber);

                System.out.println("Your card number:");
                System.out.println(getNewCardNumber());

                cardPin = newCardPin(cardPin);
                System.out.println("Your card PIN:");

                cardList(cardNumber, cardPin);
                System.out.println(getNewCardPin());

            } else if (mainMenuSelect.equals("2")) {
                pinCheck();
            }
        }
    }
}
