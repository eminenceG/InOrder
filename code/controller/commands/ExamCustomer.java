package controller.commands;

import controller.InOrderCommand;
import model.Customer;
import model.Product;
import service.CustomerService;
import service.InOrderModel;
import service.ProductService;
import view.ConsoleBasedView;

import java.util.List;

public class ExamCustomer implements InOrderCommand {
    private final ConsoleBasedView view;
    public static final String WELCOME = "Show all customers:";
    public static final String EXAM_SUCCESS =
            "\n Press any other letter to return to the main menu.";
    private boolean quit = false;

    public ExamCustomer(ConsoleBasedView view) {
        this.view = view;
    }

    private void helper(InOrderModel model) {
        try {
            List<Customer> customers = CustomerService.getAll(model.getConnection());
            for (Customer customer: customers) {
                this.view.showMessage(customer.toString());
            }
            this.view.showMessage(EXAM_SUCCESS);
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
