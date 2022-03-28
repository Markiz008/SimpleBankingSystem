package banking;

import java.util.Random;

public class Card {

    private final String cardNumber;
    private final String pin;
    private final int balance;

    protected Card() {

        Random random = new Random();

        String tempNumber = "400000" + (random.nextInt(899) + 100) +
                (random.nextInt(899) + 100) + (random.nextInt(899) + 100);

        // Applying Luhn Algorithm to generate the checksum

        long numberCheck = Long.parseLong(tempNumber);
        int sum = 0;
        int checksum = 0;
        long tempSum;
        long adder = 0;
        boolean odd = true;

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

        while (sum % 10 != 0) {
            sum++;
            checksum++;
        }

        cardNumber = tempNumber + checksum;

        int tempPin = random.nextInt(9999);

        if (tempPin <= 999 && tempPin >= 100) {
            pin = "0" + tempPin;

        } else if (tempPin <= 99 && tempPin >= 10) {
            pin = "00" + tempPin;

        } else if (tempPin <= 9) {
            pin = "000" + tempPin;

        } else {
            pin = "" + tempPin;
        }

        balance = 0;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }
}