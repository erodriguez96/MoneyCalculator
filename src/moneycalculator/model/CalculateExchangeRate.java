package moneycalculator.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class CalculateExchangeRate {

    public ExchangeRate load(Currency from, Currency to) throws Exception {
        return new ExchangeRate(from, to, new Date(), read(from.getCode(), to.getCode()));
    }

    private double read(String from, String to) throws Exception {
        String line = read(new URL("http://api.fixer.io/latest?base=" + from + "&symbols=" + to));
        return Double.parseDouble(line.substring(line.indexOf(to) + 5, line.indexOf("}")));

    }

    private String read(URL url) throws Exception {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStreamReader input = new InputStreamReader(connection.getInputStream());
        return new BufferedReader(input).readLine();
    }
}
