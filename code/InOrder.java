import controller.InOrderControllerImpl;
import service.InOrderModel;
import view.ConsoleBasedView;

import java.io.InputStreamReader;

/**
 * The InOrder program runs on terminal. It uses System.in as readable and System.out as
 * appendable.
 */
public class InOrder {
    /**
     * The main program.
     *
     * @param args User inputs.
     */
    public static void main(String[] args) {
        runConsole();
    }

    private static void runConsole() {
        ConsoleBasedView consoleView = new ConsoleBasedView(new InputStreamReader(System.in), System.out);
        InOrderControllerImpl controller = new InOrderControllerImpl(consoleView, false);
        controller.runController();
    }
}
