package view;

import controller.Features;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleBasedView {
    private Scanner scan;
    private Appendable out;
    public static final String RETURN_MESSAGE = "Returning to the main menu..";
    public static final String MAIN_WELCOME = "Welcome to the how to invest for dummies!";
    public static final String MAIN_MENU = "Main Menu\n" +
            "What do you want to do?\n" +
            "Please enter the index of a command or enter q or quit to quit:\n" +
            "1 Add a new product.\n" +
            "2 Examine all product.\n" +
            "3 Add an inventory record.\n" +
            "4 Examine all inventory records.\n" +
            "5 Add a customer.\n" +
            "6 Examine all customers.\n" +
            "7 Place an order all customers.\n" +
            "8 Examine an order.\n";
    public static final String EXIT_MESSAGE = "Bye.";

    /**
     * Constructor of KernelConsoleBasedView.
     *
     * @param in  Readable object that will be used as in.
     * @param out Appendable object that will be used as out.
     */
    public ConsoleBasedView(Readable in, Appendable out) {
        scan = new Scanner(in);
        this.out = out;
    }

    public void showMessage(String s) {
        try {
            this.out.append(s + "\n");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void showReturnToMain() {
        showMessage(RETURN_MESSAGE);
        showMainMenu();
    }

    public void showWelcome() {
        showMessage(MAIN_WELCOME);
    }

    public void showMainMenu() {
        showMessage(MAIN_MENU);
    }

    public void showExit() {
        showMessage(EXIT_MESSAGE);
    }

    public void showErrorMessage(String error) {
        showMessage("ERROR: " + error);
    }

    public void setFeatures(Features f) {
        System.out.println("Features has been set.");
    }

    public String getInputString() {
        String input = "";
        if (scan.hasNextLine()) {
            input = scan.nextLine();
        }
        return input;
    }
}
