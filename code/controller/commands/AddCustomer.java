package controller.commands;

import controller.InOrderCommand;
import controller.InOrderControllerImpl;
import model.Customer;
import service.CustomerService;
import service.InOrderModel;
import view.ConsoleBasedView;

public class AddCustomer implements InOrderCommand {
    private final ConsoleBasedView view;
    public static final String WELCOME = "Add Customer:";
    public static final String INTRO_ID = "What's the ID?";
    public static final String INTRO_NAME = "What's the name?";
    public static final String INTRO_ADDRESS = "What's the address?";
    public static final String INTRO_CITY = "What's the city?";
    public static final String INTRO_STATE = "What's the state?";
    public static final String INTRO_COUNTRY = "What's the country?";
    public static final String INTRO_POSTAL = "What's the postal code?";
    public static final String ADD_SUCCESS
            = "This customer has been added successfully.\nIf you want to buy another one, please enter y. " +
            "Or press any other letter to return to the main menu.";
    public static final String ADD_FAIL
            = "This customer is not able to add.\nIf you want to buy another one, please enter y. " +
            "Or press any other letter to return to the main menu.";
    private boolean quit = false;

    public AddCustomer(ConsoleBasedView view) {
        this.view = view;
    }

    private void helper(InOrderModel model) {
        try {
            this.view.showMessage(this.INTRO_ID);
            int id = InOrderControllerImpl.getIntPositive(this.view);
            this.view.showMessage(this.INTRO_NAME);
            String name = this.view.getInputString();
            this.view.showMessage(this.INTRO_ADDRESS);
            String address = this.view.getInputString();
            this.view.showMessage(this.INTRO_CITY);
            String city = this.view.getInputString();
            this.view.showMessage(this.INTRO_STATE);
            String state = this.view.getInputString();
            this.view.showMessage(this.INTRO_COUNTRY);
            String country = this.view.getInputString();
            this.view.showMessage(this.INTRO_POSTAL);
            String postal = this.view.getInputString();
            Customer customer = new Customer(id, name, address, city, state, country, postal);
            boolean inserted = CustomerService.insert(model.getConnection(), customer);
            if (inserted) {
                this.view.showMessage(this.ADD_SUCCESS);
            } else {
                this.view.showMessage(this.ADD_FAIL);
            }
        } catch (Exception e) {
            // get exception from model, return to main menu.
            this.view.showErrorMessage(e.getMessage());
            this.quit = true;
        }
    }

    @Override
    public void runCommand(InOrderModel model) {
        this.view.showMessage(this.WELCOME);
        helper(model);
        while (!this.quit && this.view.getInputString().equals("y")) {
            helper(model);
        }
        this.view.showReturnToMain();
    }
}
