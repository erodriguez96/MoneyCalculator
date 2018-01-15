package moneycalculator.controller;

import java.awt.event.ItemEvent;
import javax.swing.JComboBox;
import moneycalculator.view.Display;

public class CommandSetCurrency implements Command {

    private final JComboBox mainCurrency, otherCurrency;
    private Object lastMainItem;

    public CommandSetCurrency(JComboBox<String> mainCurrency, JComboBox<String> otherCurrency) {
        this.mainCurrency = mainCurrency;
        this.otherCurrency = otherCurrency;
        lastMainItem = String.valueOf(mainCurrency.getSelectedItem());
    }

    @Override
    public void execute(Display display) {
    }

    @Override
    public void execute(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.DESELECTED) saveLastItem(e);
        if (e.getStateChange() == ItemEvent.SELECTED) checkPossibleSwap(e);
    }

    private void saveLastItem(ItemEvent e) {
        lastMainItem = e.getItem();
    }

    private void checkPossibleSwap(ItemEvent e) {
        if (e.getItem().equals(otherCurrency.getSelectedItem())) swap();
    }

    private void swap() {
        mainCurrency.setSelectedItem(otherCurrency.getSelectedItem());
        otherCurrency.setSelectedItem(lastMainItem);
    }
}
