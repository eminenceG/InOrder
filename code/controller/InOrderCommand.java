package controller;

import service.InOrderModel;

/**
 * The interface for all possible stock trader commands.
 */
public interface InOrderCommand {
    void runCommand(InOrderModel model);
}
