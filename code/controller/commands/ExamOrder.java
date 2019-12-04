package controller.commands;

import controller.InOrderCommand;
import controller.InOrderControllerImpl;
import model.Customer;
import model.Order;
import model.OrderRecord;
import service.CustomerService;
import service.InOrderModel;
import service.OrderRecordService;
import service.OrderService;
import view.ConsoleBasedView;

import java.util.Date;
import java.util.List;

public class ExamOrder implements InOrderCommand {
    private final ConsoleBasedView view;
    public static final String WELCOME = "Show all customers:";
    public static final String INTRO_ORDER_ID = "What's the order id?";
    public static final String INTRO_ORDER = "The order contains";
    public static final String INTRO_SHIP = "The order has not been shipped, press y if you want to ship the order";
    public static final String INTRO_SHIPDATE = "What's the ship date";
    public static final String SHIP_SUCCESS = "The order has been shipped.";
    public static final String EXAM_SUCCESS =
            "\n Press any other letter to return to the main menu.";
    private boolean quit = false;

    public ExamOrder(ConsoleBasedView view) {
        this.view = view;
    }

    private void helper(InOrderModel model) {
        try {
            this.view.showMessage(INTRO_ORDER_ID);
            int id = InOrderControllerImpl.getIntPositive(this.view);
            Order order = OrderService.getById(model.getConnection(), id);
            this.view.showMessage(order.toString());
            this.view.showMessage(INTRO_ORDER);
            List<OrderRecord> orderRecordList = OrderRecordService.getByOrderId(model.getConnection(), id);
            for (OrderRecord or: orderRecordList) {
                this.view.showMessage(or.toString());
            }

            if (order.getShipDate() == null) {
                this.view.showMessage(INTRO_SHIP);
                if (this.view.getInputString().equals("y")) {
                    this.view.showMessage(INTRO_SHIPDATE);
                    Date shipDate = InOrderControllerImpl.getDate(this.view);
                    OrderService.shipOrder(model.getConnection(), id, new java.sql.Date(shipDate.getTime()));
                    this.view.showMessage(SHIP_SUCCESS);
                }
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
