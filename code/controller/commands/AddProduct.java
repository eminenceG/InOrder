package controller.commands;

import controller.InOrderCommand;
import model.Product;
import service.InOrderModel;
import service.ProductService;
import view.ConsoleBasedView;


public class AddProduct implements InOrderCommand {
    private final ConsoleBasedView view;
    public static final String WELCOME = "Add product:";
    public static final String INTRO_NAME = "What's the name of the product do you want to add?";
    public static final String INTRO_DESC = "What's the product description?";
    public static final String INTRO_SKU = "What's the SKU of the product?";
    public static final String ADD_SUCCESS
            = " has been added successfully.\nIf you want to buy another one, please enter y. " +
            "Or press any other letter to return to the main menu.";
    public static final String ADD_FAIL
            = " is not able to add.\nIf you want to buy another one, please enter y. " +
            "Or press any other letter to return to the main menu.";
    private boolean quit = false;

    public AddProduct(ConsoleBasedView view) {
        this.view = view;
    }

    private void helper(InOrderModel model) {
        try {
            this.view.showMessage(this.INTRO_NAME);
            String name = this.view.getInputString();
            this.view.showMessage(this.INTRO_DESC);
            String desc = this.view.getInputString();
            this.view.showMessage(this.INTRO_SKU);
            String sku = this.view.getInputString();

            Product product = new Product(name, desc, sku);
            boolean inserted = ProductService.insert(model.getConnection(), product);
            if (inserted) {
                this.view.showMessage(name + this.ADD_SUCCESS);
            } else {
                this.view.showMessage(name + this.ADD_FAIL);
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
