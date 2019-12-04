package controller.commands;

import controller.InOrderCommand;
import model.InventoryRecord;
import model.Product;
import service.InOrderModel;
import service.InventoryRecordService;
import service.ProductService;
import view.ConsoleBasedView;

import java.util.List;

public class ExamInventoryRecord implements InOrderCommand {

    private final ConsoleBasedView view;
    public static final String WELCOME = "Show all inventory records:";
    public static final String EXAM_SUCCESS =
            "\n Press any other letter to return to the main menu.";
    private boolean quit = false;

    public ExamInventoryRecord(ConsoleBasedView view) {
        this.view = view;
    }


    private void helper(InOrderModel model) {
        try {
            List<InventoryRecord> inventoryRecords= InventoryRecordService.getAll(model.getConnection());
            for (InventoryRecord ir: inventoryRecords) {
                this.view.showMessage(ir.toString());
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
