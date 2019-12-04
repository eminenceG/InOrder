package controller.commands;

import controller.InOrderCommand;
import controller.InOrderControllerImpl;
import model.InventoryRecord;
import service.InOrderModel;
import service.InventoryRecordService;
import service.ProductService;
import view.ConsoleBasedView;

import java.math.BigDecimal;

public class AddInventoryRecord implements InOrderCommand {
    private final ConsoleBasedView view;
    public static final String WELCOME = "Add inventory record:";
    public static final String INTRO_SKU = "What's the SKU of the product?";
    public static final String INTRO_QUANTITY = "What's the quantity of the product?";
    public static final String INTRO_PRICE = "What's the unit price of the product?";
    public static final String ADD_SUCCESS
            = "This inventory record been added successfully.\nIf you want to buy another one, please enter y. " +
            "Or press any other letter to return to the main menu.";
    public static final String ADD_FAIL
            = "This inventory record is not able to add.\nIf you want to buy another one, please enter y. " +
            "Or press any other letter to return to the main menu.";
    private boolean quit = false;

    public AddInventoryRecord(ConsoleBasedView view) {
       this.view = view;
    }

    private void helper(InOrderModel model) {
        try {
            this.view.showMessage(this.INTRO_SKU);
            String sku = this.view.getInputString();
            this.view.showMessage(this.INTRO_QUANTITY);
            int quantity = InOrderControllerImpl.getIntPositive(view);
            this.view.showMessage(this.INTRO_PRICE);
            BigDecimal price = InOrderControllerImpl.getBigDecimalNonNegative(view);

            InventoryRecord ir = new InventoryRecord(quantity, price.doubleValue(), sku);

            boolean inserted = InventoryRecordService.insert(model.getConnection(), ir);
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
