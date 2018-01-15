package moneycalculator.controller;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import moneycalculator.model.CalculateExchangeRate;
import moneycalculator.model.Currency;
import moneycalculator.model.CurrencyList;
import moneycalculator.model.ExchangeRate;
import moneycalculator.model.Money;
import moneycalculator.view.Display;

public class CommandCalculate implements Command {

    private JComboBox<String> currencyFrom, currencyTo;
    private JTextField amountInput;

    public CommandCalculate(JComboBox<String> currencyFrom, JComboBox<String> currencyTo, JTextField amountInput) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.amountInput = amountInput;
    }

    @Override
    public void execute(Display display) {
        display.getShowCurrencyFrom().setText("");
        display.getShowCurrencyTo().setText("");
        try {
            String[] data = printExchangeRate(withOptions(getInputs()));
            display.getShowCurrencyFrom().setText(data[0]);
            display.getShowAmountOutput().setText(data[2]);
            display.getShowCurrencyTo().setText(data[1]);
        } catch (NumberFormatException e) {
            display.getShowAmountOutput().setText("Formato inválido");
        } catch (Exception e) {
            display.getShowAmountOutput().setText("Fallo al conectar con el servidor");
        }
    }

    @Override
    public void execute(java.awt.event.ItemEvent e) {
    }

    private String[] getInputs(){
        String[] exchangeRate = new String[3];
        exchangeRate[0] = String.valueOf(currencyFrom.getSelectedItem()).substring(0, 3);
        exchangeRate[1] = String.valueOf(currencyTo.getSelectedItem()).substring(0, 3);
        exchangeRate[2] = amountInput.getText();
        return exchangeRate;
    }

    private String[] printExchangeRate(ExchangeRate a) {
        String[] values = new String[3];
        values[0] = amountInput.getText() + " " + String.valueOf(a.getFrom().getCode() + " =");
        values[1] = String.valueOf(a.getTo().getCode());
        values[2] = a.getRate() * Double.parseDouble(amountInput.getText()) + "";
        return values;
    }

    private ExchangeRate withOptions(String[] inputs) throws NumberFormatException, Exception {
        CurrencyList currencyList = new CurrencyList();
        Currency currency = currencyList.get(inputs[1]);
        Money money = new Money(Double.parseDouble(inputs[2]), currencyList.get(inputs[0]));
        return new CalculateExchangeRate().load(money.getCurrency(), currency);
    }
}
