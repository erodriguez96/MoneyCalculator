package moneycalculator.controller;

import java.util.Map;
import moneycalculator.model.Currency;
import moneycalculator.model.CurrencyList;
import moneycalculator.view.Display;

public class Controller {

    private final CurrencyList currencyList;
    private Display display;
    private String[] currencyListString;

    public Controller(CurrencyList currencyList) {
        this.currencyList = currencyList;
    }

    public String[] getItems(int avoidItem) {
        return getCurrencyList();
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    private String[] getCurrencyList() {
        Map<String, Currency> currencies = currencyList.getCurrencies();
        currencyListString = new String[currencies.size()];
        int i = 0;
        for (Map.Entry<String, Currency> entry : currencies.entrySet()) {
            currencyListString[i++] = entry.getKey() + " " + entry.getValue().getName();
        }
        return currencyListString;
    }
}
