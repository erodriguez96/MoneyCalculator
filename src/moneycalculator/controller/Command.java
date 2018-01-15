package moneycalculator.controller;

import moneycalculator.view.Display;

public interface Command {

    void execute(Display display);

    void execute(java.awt.event.ItemEvent e);
}
