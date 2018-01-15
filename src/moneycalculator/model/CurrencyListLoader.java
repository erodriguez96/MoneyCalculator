package moneycalculator.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CurrencyListLoader {

    private final String filename;

    public CurrencyListLoader(String filename) {
        this.filename = filename;
    }

    public Map getCurrencies() {
        Map<String, Currency> currencies = new LinkedHashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
            String line;
            while ((line = reader.readLine()) != null) {
                Currency currency = currencyOf(line);
                currencies.put(currency.getCode(), currency);
            }
        } catch (IOException e) {
        }
        return currencies;
    }

    private Currency currencyOf(String line) {
        String[] split = line.split(";");
        return new Currency(split[0], split[1], split[2]);
    }
}
