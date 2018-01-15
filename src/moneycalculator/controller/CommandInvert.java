package moneycalculator.controller;

import java.awt.event.ItemEvent;
import javax.swing.JComboBox;
import moneycalculator.view.Display;

public class CommandInvert implements Command {

    JComboBox<String> currencyFrom, currencyTo;

    public CommandInvert(JComboBox<String> currencyFrom, JComboBox<String> currencyTo) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
    }

    @Override
    public void execute(Display display) {
        Object aux = currencyFrom.getSelectedItem();
        currencyFrom.setSelectedItem(currencyTo.getSelectedItem());
        currencyTo.setSelectedItem(aux);
    }

    @Override
    public void execute(ItemEvent e) {
    }
}
