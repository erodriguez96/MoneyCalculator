package moneycalculator.model;

import java.util.Map;

public class CurrencyList {

    private Map<String, Currency> currencies;

    public CurrencyList() {
        currencies = new CurrencyListLoader("currencies").getCurrencies();
    }

    private void add(Currency currency) {
        currencies.put(currency.getCode(), currency);
    }

    public Map<String, Currency> getCurrencies() {
        return currencies;
    }

    public Currency get(String code) {
        return currencies.get(code.toUpperCase());
    }
}
