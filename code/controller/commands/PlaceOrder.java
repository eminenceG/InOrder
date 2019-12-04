package controller.commands;

import controller.InOrderCommand;
import controller.InOrderControllerImpl;
import model.*;
import service.InOrderModel;
import service.InventoryRecordService;
import service.OrderService;
import service.ProductService;
import view.ConsoleBasedView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlaceOrder implements InOrderCommand {
    private final ConsoleBasedView view;
    public static final String WELCOME = "Place an order:";
    public static final String INTRO_CUSTOMER_ID = "What's the customer id?";
    public static final String INTRO_ORDER_ID = "What's the order id?";
    public static final String INTRO_DATE = "What's the date? yyyy-MM-dd";
    public static final String INTRO_PRODUCT = "Add one product, what's the SKU?";
    public static final String INTRO_NUM = "How many do you want to buy?";
    public static final String ANOTHER_PRODUCT = "If you want to buy another one, please enter y. " +
            "Or press any other letter to place the order.";
    public static final String ADD_SUCCESS
            = "This order has been added successfully.\nIf you want to buy another one, please enter y. " +
            "Or press any other letter to return to the main menu.";
    public static final String ADD_FAIL
            = "This order is not able to add.\nIf you want to buy another one, please enter y. " +
            "Or press any other letter to return to the main menu.";
    private boolean quit = false;

    public PlaceOrder(ConsoleBasedView view) {
        this.view = view;
    }

    private void helper(InOrderModel model) {
        try {
            this.view.showMessage(this.INTRO_CUSTOMER_ID);
            int customerId = InOrderControllerImpl.getIntPositive(this.view);
            this.view.showMessage(this.INTRO_ORDER_ID);
            int orderId = InOrderControllerImpl.getIntPositive(this.view);
            this.view.showMessage(this.INTRO_DATE);
            Date date = InOrderControllerImpl.getDate(this.view);

            List<OrderRecord> orderRecordList = new ArrayList<>();
            while (true) {
                this.view.showMessage(this.INTRO_PRODUCT);
                String sku = this.view.getInputString();
                InventoryRecord ir = InventoryRecordService.getById(model.getConnection(), sku);
                double price = ir.getUnitPrice();
                this.view.showMessage(this.INTRO_NUM);
                int num = InOrderControllerImpl.getIntPositive(this.view);

                orderRecordList.add(new OrderRecord(orderId, num, price, sku));
                this.view.showMessage(this.ANOTHER_PRODUCT);
                String in = this.view.getInputString();
                if (!in.equals("y")) {
                    break;
                }
            }

            boolean inserted = OrderService.insert(model.getConnection(), new Order(customerId, orderId,
                            new java.sql.Date(date.getTime())), orderRecordList);
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
