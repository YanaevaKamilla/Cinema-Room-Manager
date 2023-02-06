package cinema;

import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

/**
 * This is a simulation of cinema theatre.
 * At start user sets parameters of the hall - the number of rows and seats in a row.
 * Then user can choose point from menu:
 * 1. shows hall scheme - number of rows and seats in row, including free (marked as S) / occupied (marked as B) seats.
 * 2. offers to user to select place, shows ticket price. If place is busy or out of hall user gets notifications and
 * should choose another place.
 * 3. show statistics for cinema theater owner: (1) current income, (2) total income, (3) number of available seats,
 * and (4) % of occupancy.
 * 4. finishes the program.
 *
 * @author Kamilla Burmistrova
 */
public class Cinema {
    /**
     * scanner object and specified data source for it (string with text)
     */
    private static final Scanner scanner = new Scanner(in);
    /**
     * selected ticket row
     */
    private static int bookedRowNumber;
    /**
     * selected ticket seats in row
     */
    private static int bookedSeatNumber;
    /**
     * capacity of cinema hall - result of numberOfRowsInHall * numberOfSeatsInHall
     */
    private static int capacity;
    /**
     * array of strings with size numberOfRowsInHall * numberOfSeatsInHall
     */
    private static String[][] cinemaHall;
    /**
     * value of income on purchased tickets
     */
    private static int currentIncome;
    /**
     * indicator of correctness of selected ticket
     */
    private static boolean isTicketCorrect;
    /**
     * low ticket price for rows after 1/2 of rows in cinema hall
     */
    private static int lowPrice;
    /**
     * total number of purchased tickets
     */
    private static int numberOfPurchasedTickets;
    /**
     * normal ticket price for rows before 1/2 of rows in cinema hall or for hall with capacity <=60
     */
    private static int normalPrice;
    /**
     * number of rows in cinema hall
     */
    private static int numberOfRowsInHall;
    /**
     * number of seats in row
     */
    private static int numberOfSeatsInHall;
    /**
     * selected point of menu
     */
    private static int selectedMenuItem;
    /**
     * % of purchased tickets in % of cinema hall capacity
     */
    private static float percentage;
    /**
     * total value of income based on cinema hall capacity
     */
    private static int totalIncome;

    /**
     * main method
     */
    public static void main(String[] args) {
        numberOfRowsInHall = requestCapacityRows();
        numberOfSeatsInHall = requestCapacitySeats();
        cinemaHall = createHall(numberOfRowsInHall, numberOfSeatsInHall);
        capacity = capacity(numberOfRowsInHall, numberOfSeatsInHall);
        lowPrice = setLowPrice();
        normalPrice = setNormalPrice();

        do {
            printMenu();
            selectedMenuItem = readUserChoice();

            switch (selectedMenuItem) {
                case 1 -> {
                    printCinemaLine();
                    showHeaders(numberOfSeatsInHall);
                    printSeats(cinemaHall);
                }
                case 2 -> {
                    do {
                        getBookedRowNumber();
                        getBookedSeatNumber();
                        if (bookedRowNumber > numberOfRowsInHall || bookedSeatNumber > numberOfSeatsInHall ||
                                bookedRowNumber < 1 || bookedSeatNumber < 1) {
                            out.println("Wrong input!");
                            isTicketCorrect = false;
                        } else if (Objects.equals(cinemaHall[bookedRowNumber - 1][bookedSeatNumber - 1], " B")) {
                            out.println("That ticket has already been purchased!");
                            isTicketCorrect = false;
                        } else {
                            break;
                        }
                    } while (true);
                    showTicketPrice(numberOfRowsInHall);
                    updateSeats(cinemaHall);
                }
                case 3 -> {
                    showNumberOfPurchasedTickets();
                    showPercentage();
                    showCurrentIncome();
                    showTotalIncome();
                }
            }
        } while (selectedMenuItem != 0);
    }

    /**
     * method requests number seats in row
     *
     * @return numberOfRowsInHall - number of rows in cinema hall
     */
    private static int requestCapacityRows() {
        out.println("Enter the number of rows:");
        numberOfRowsInHall = scanner.nextInt();
        return numberOfRowsInHall;
    }

    /**
     * method requests number seats in row
     *
     * @return numberOfSeatsInHall - number of seats in row
     */
    private static int requestCapacitySeats() {
        out.println("Enter the number of seats in each row:");
        numberOfSeatsInHall = scanner.nextInt();
        return numberOfSeatsInHall;
    }

    /**
     * method creates array cinemaHall with parameters defined by user.
     * Is calculated as numberOfRowsInHall * numberOfSeatsInHall.
     * Each free seat marked as S.
     *
     * @param numberOfRowsInHall  - number of rows in cinema hall
     * @param numberOfSeatsInHall - number of seats in row
     * @return array cinemaHall
     */
    private static String[][] createHall(int numberOfRowsInHall, int numberOfSeatsInHall) {
        cinemaHall = new String[numberOfRowsInHall][numberOfSeatsInHall];
        for (int i = 0; i < numberOfRowsInHall; i++) {
            for (int j = 0; j < numberOfSeatsInHall; j++) {
                cinemaHall[i][j] = " " + "S";
            }
        }
        return cinemaHall;
    }

    /**
     * method calculates capacity of cinema hall
     *
     * @param numberOfRowsInHall  - number of rows in cinema hall
     * @param numberOfSeatsInHall - number of seats in row
     * @return capacity
     */
    private static int capacity(int numberOfRowsInHall, int numberOfSeatsInHall) {
        return capacity = numberOfRowsInHall * numberOfSeatsInHall;
    }

    /**
     * method sets low ticket price for rows after 1/2 of rows in cinema hall
     *
     * @return lowPrice - value of low price of ticket
     */
    private static int setLowPrice() {
        return lowPrice = 8;
    }

    /**
     * method sets normal ticket price for rows before 1/2 of rows in cinema hall or for hall with capacity <=60
     *
     * @return normalPrice - value of normal price of ticket
     */
    private static int setNormalPrice() {
        return normalPrice = 10;
    }

    /**
     * method prints menu for user choice
     */
    private static void printMenu() { // вывод меню
        out.println("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit\n");
    }

    /**
     * method reads user's choice - menu item
     *
     * @return point of user choice (0,1,2,3)
     */
    private static int readUserChoice() {
        return scanner.nextInt();
    }

    /**
     * method prints Cinema header
     */
    private static void printCinemaLine() {
        out.println('\n' + "Cinema:");
    }

    /**
     * method prints headers for seats in cinema hall
     *
     * @param numberOfSeatsInHall - number of rows in cinema hall
     */
    private static void showHeaders(int numberOfSeatsInHall) {
        out.print("  ");
        for (int i = 1; i <= numberOfSeatsInHall; i++) {
            out.print(i + " ");
        }
        out.print('\n');
    }

    /**
     * method prints row number and cinema hall scheme
     *
     * @param cinemaHall - array of strings with size numberOfRowsInHall * numberOfSeatsInHall
     */
    public static void printSeats(String[][] cinemaHall) {
        for (int i = 0; i < numberOfRowsInHall; i++) {
            out.print(i + 1);
            for (int j = 0; j < numberOfSeatsInHall; j++) {
                out.print(cinemaHall[i][j]);
            }
            out.println();
        }
    }

    /**
     * method requests to book row number
     */
    private static void getBookedRowNumber() {
        out.println('\n' + "Enter a row number:");
        bookedRowNumber = scanner.nextInt();
    }

    /**
     * method requests to book seat number
     */
    private static void getBookedSeatNumber() {
        out.println("Enter a seat number in that row:");
        bookedSeatNumber = scanner.nextInt();
    }

    /**
     * method calculates and shows selected ticket price,
     * calculates currentIncome - value of income on purchased tickets
     *
     * @param numberOfRowsInHall - number of rows in cinema hall
     */
    private static void showTicketPrice(int numberOfRowsInHall) {
        if (capacity <= 60) {
            System.out.println("Ticket price: $" + normalPrice);
            currentIncome += normalPrice;
        } else {
            if (bookedRowNumber > numberOfRowsInHall / 2) {
                out.println("Ticket price: $" + lowPrice);
                currentIncome += lowPrice;
            } else {
                out.println("Ticket price: $" + normalPrice);
                currentIncome += normalPrice;
            }
        }
    }

    /**
     * method updates cinema hall scheme with busy seats
     *
     * @param cinemaHall - array of strings with size numberOfRowsInHall * numberOfSeatsInHall
     */
    private static void updateSeats(String[][] cinemaHall) {
        for (int i = 0; i < numberOfRowsInHall; i++) {
            for (int j = 0; j < numberOfSeatsInHall; j++) {
                if (i == bookedRowNumber - 1 && j == bookedSeatNumber - 1) {
                    cinemaHall[i][j] = " " + "B";
                    numberOfPurchasedTickets++;
                    percentage = (float) (numberOfPurchasedTickets * 100) / capacity;
                }
            }
        }
    }

    /**
     * method calculates and shows value of Number of unique purchased tickets
     */
    private static void showNumberOfPurchasedTickets() {
        out.println("Number of purchased tickets: " + numberOfPurchasedTickets);
    }

    /**
     * method calculates and shows value of Percentage - % of purchased tickets in % of cinema hall capacity
     */
    private static void showPercentage() {
        out.println("Percentage: " + String.format("%.02f", percentage) + "%");
    }

    /**
     * method calculates and shows value of current income on purchased tickets
     */
    private static void showCurrentIncome() {
        out.println("Current income: $" + currentIncome);
    }

    /**
     * method calculates and shows value of total income based on cinema hall capacity and ticket price.
     */
    private static void showTotalIncome() {
        if (capacity <= 60) {
            totalIncome = capacity * normalPrice;
        } else {
            totalIncome = capacity / 2 * normalPrice + capacity / 2 * lowPrice;
        }
        out.println("Total income: $" + totalIncome);
    }
}