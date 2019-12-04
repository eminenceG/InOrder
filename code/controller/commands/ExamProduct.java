package controller.commands;

import controller.InOrderCommand;
import model.Product;
import service.InOrderModel;
import service.ProductService;
import view.ConsoleBasedView;

import java.util.List;

public class ExamProduct implements InOrderCommand {
    private final ConsoleBasedView view;
    public static final String WELCOME = "Show all product:";
    public static final String EXAM_SUCCESS =
            "\n Press any other letter to return to the main menu.";
    private boolean quit = false;

    public ExamProduct(ConsoleBasedView view) {
        this.view = view;
    }

    private void helper(InOrderModel model) {
        try {
            List<Product> products = ProductService.getAllProducts(model.getConnection());
            for (Product product : products) {
                this.view.showMessage(product.toString());
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
