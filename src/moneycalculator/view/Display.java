package moneycalculator.view;

import moneycalculator.controller.CommandSetCurrency;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import moneycalculator.controller.Command;
import moneycalculator.controller.CommandCalculate;
import moneycalculator.controller.CommandInvert;
import moneycalculator.controller.Controller;
import moneycalculator.model.Currency;
import moneycalculator.model.CurrencyList;

public class Display extends JFrame {

    private final Map<String, Currency> currencyList = new CurrencyList().getCurrencies();
    private final Map<String, Command> commands = new HashMap<>();
    private JTextPane showCurrencyFrom, showAmountOutput, showCurrencyTo;
    private JComboBox<String> currencyFrom, currencyTo;
    private JButton calculate, invert;
    private JTextField amountInput;
    private Controller controller;
    private Display display;

    public Display() {
        super("Money Calculator");
        setFrameOptions();
        initComponents();
        putCommands();
        addComponents();
        display = this;
    }

    private void setFrameOptions() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPermanentSize(550, 150);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
    }

    private void setPermanentSize(int width, int height) {
        setMinimumSize(new Dimension(width, height));
        setResizable(false);
    }

    private void initComponents() {
        initTextPane();
        initComboBox();
        initButton();
        initTextField();
    }

    private void initTextPane() {
        showCurrencyFrom = createTextPane(null);
        showAmountOutput = createTextPane(new Font("Tahoma", 0, 24));
        showCurrencyTo = createTextPane(null);
    }

    private void initComboBox() {
        Dimension size = new Dimension(190, 25);
        currencyFrom = createComboBox("selectCurrencyFrom", size);
        currencyTo = createComboBox("SelectCurrencyTo", size);
    }

    private void initButton() {
        Dimension size = new Dimension(25, 25);
        calculate = createButton("calculate", "→", size);
        invert = createButton("invert", "↔", size);
    }

    private void initTextField() {
        Dimension size = new Dimension(80, 25);
        amountInput = createTextField(size);
    }

    private JTextPane createTextPane(Font font) {
        JTextPane textPane = new JTextPane();
        if(font != null) textPane.setFont(font);
        textPane.setBorder(null);
        textPane.setBackground(null);
        textPane.setEditable(false);
        return textPane;
    }

    private JComboBox<String> createComboBox(String name, Dimension size) {
        JComboBox jComboBox = new JComboBox();
        jComboBox.setName(name);
        jComboBox.addItemListener(executeSwapAction());
        jComboBox.setPreferredSize(size);
        return jComboBox;
    }

    private JButton createButton(String name, String text, Dimension size) {
        JButton button = new JButton(text);
        if(text.equals("→")) button.setNextFocusableComponent(amountInput);
        button.setName(name);
        button.setBorder(null);
        button.setPreferredSize(size);
        button.addActionListener(executeClickAction());
        return button;
    }

    public JTextPane getShowCurrencyFrom() {
        return showCurrencyFrom;
    }

    public JTextPane getShowAmountOutput() {
        return showAmountOutput;
    }

    public JTextPane getShowCurrencyTo() {
        return showCurrencyTo;
    }

    private JTextField createTextField(Dimension size) {
        JTextField textField = new JTextField();
        textField.setPreferredSize(size);
        textField.addKeyListener(executeKeyAction());

        return textField;
    }

    private void putCommands() {
        commands.put(calculate.getName(), new CommandCalculate(currencyFrom, currencyTo, amountInput));
        commands.put(invert.getName(), new CommandInvert(currencyFrom, currencyTo));
        commands.put(currencyFrom.getName(), new CommandSetCurrency(currencyFrom, currencyTo));
        commands.put(currencyTo.getName(), new CommandSetCurrency(currencyTo, currencyFrom));
    }

    private void addComponents() {
        JPanel result = new JPanel(new FlowLayout());
        result.add(showCurrencyFrom);
        result.add(showAmountOutput);
        result.add(showCurrencyTo);
        add(result, 0);

        JPanel options = new JPanel(new FlowLayout());
        options.add(amountInput);
        options.add(currencyFrom);
        options.add(invert);
        options.add(currencyTo);
        options.add(calculate);
        add(options, 1);
    }

    private void add(JComponent component, int y) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = y;
        constraints.weighty = 1.0;
        add(component, constraints);
    }

    public void setItemsComboBox() {
        currencyFrom.setModel(new DefaultComboBoxModel<>(controller.getItems(-1)));
        currencyTo.setModel(new DefaultComboBoxModel<>(controller.getItems(currencyFrom.getSelectedIndex())));
        currencyTo.setSelectedIndex(1);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private ActionListener executeClickAction() {
        return (ActionEvent e) -> {
            commands.get(((JComponent) e.getSource()).getName()).execute(display);
        };
    }

    private KeyAdapter executeKeyAction() {
        return new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10) commands.get(calculate.getName()).execute(display);
            }
        };
    }

    private ItemListener executeSwapAction() {
        return (ItemEvent e) -> {
            commands.get(((JComboBox) e.getSource()).getName()).execute(e);

        };
    }
}
