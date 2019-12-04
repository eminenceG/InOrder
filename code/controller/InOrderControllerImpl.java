package controller;

import controller.commands.*;
import service.InOrderModel;
import view.ConsoleBasedView;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.Function;

public class InOrderControllerImpl implements Features {
    private InOrderModel model;
    private ConsoleBasedView view;
    private final Stack<InOrderCommand> commands;
    public static final String INVALID_INPUT = "Invalid input, please input again.";
    public static final String ARGUMENT_NULL_MESSAGE = "The arguments cannot be null";
    public static final String STOCKS_WRONG_INPUT = "Invalid input.Please try again.";
    private final Map<String, Function<ConsoleBasedView, InOrderCommand>> knownCommands;

    public InOrderControllerImpl(ConsoleBasedView view, boolean init) {
        try {
            model = new InOrderModel();
            if (init) {
                model.init();
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        this.view = view;
        this.commands = new Stack<>();
        this.knownCommands = new HashMap<>();
        knownCommands.put("1", s -> new AddProduct(s));
        knownCommands.put("2", s -> new ExamProduct(s));
        knownCommands.put("3", s -> new AddInventoryRecord(s));
        knownCommands.put("4", s -> new ExamInventoryRecord(s));
        knownCommands.put("5", s -> new AddCustomer(s));
        knownCommands.put("6", s -> new ExamCustomer(s));
        knownCommands.put("7", s -> new PlaceOrder(s));
        knownCommands.put("8", s -> new ExamOrder(s));

    }

    @Override
    public void addProduct(String name, String description, String sku) {

    }

    @Override
    public void showProduct() {

    }

    public void runController() {
        if (this.model == null || this.view == null) {
            throw new IllegalArgumentException(ARGUMENT_NULL_MESSAGE);
        }
        this.view.showWelcome();
        this.view.showMainMenu();
        while (true) {
            InOrderCommand c;
            String in = this.view.getInputString();
            // if user type q or quit in the main menu, the program will terminate.
            if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
                this.view.showExit();
                return;
            }

            Function<ConsoleBasedView, InOrderCommand> cmd = knownCommands.getOrDefault(in, null);
            if (cmd == null) {
                //  throw new IllegalArgumentException();
                this.view.showErrorMessage(INVALID_INPUT);
            } else {
                c = cmd.apply(this.view);
                commands.add(c);
                c.runCommand(model);
            }
        }
    }

    /**
     * Get a positive integer element from input.
     *
     * @param view The view to execute this operation.
     * @return The integer from input.
     * @throws NumberFormatException If input is not an integer.
     */

    public static int getIntPositive(ConsoleBasedView view) {
        while (true) {
            String input = view.getInputString();
            try {
                int res = Integer.valueOf(input);
                if (res <= 0) {
                    view.showMessage(INVALID_INPUT);
                    continue;
                }
                return res;
            } catch (NumberFormatException e) {
                view.showMessage(INVALID_INPUT);
            }
        }
    }

    /**
     * Get a BigDecimal that is not negative element from input.
     *
     * @param view The view to execute this operation.
     * @return The BigDecimal from input.
     * @throws NumberFormatException If input is not a big int.
     */

    public static BigDecimal getBigDecimalNonNegative(ConsoleBasedView view) {
        while (true) {
            String input = view.getInputString();
            try {
                BigDecimal res = new BigDecimal(input);
                if (res.compareTo(BigDecimal.ZERO) >= 0) {
                    return res;
                }
                view.showMessage(INVALID_INPUT);
            } catch (NumberFormatException e) {
                view.showMessage(INVALID_INPUT);
            }
        }
    }

    /**
     * Get a Date element from input.
     *
     * @param view The view to execute this operation.
     * @return The Date from input.
     */
    public static Date getDate(ConsoleBasedView view) {
        while (true) {
            String input = view.getInputString();
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd")
                        .parse(input);
                // "2018-10-23"
                return date;
            } catch (ParseException e) {
                view.showMessage(INVALID_INPUT);
            }
        }
    }
}
