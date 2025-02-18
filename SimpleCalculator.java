import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculator extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder input;
    private int cursorPosition;

    public SimpleCalculator() {
        input = new StringBuilder();
        cursorPosition = 0;
        setTitle("Simple Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        add(buttonPanel, BorderLayout.CENTER);

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            button.addActionListener(this);
            buttonPanel.add(button);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("[0-9]") || command.equals(".")) {
            input.insert(cursorPosition, command);
            cursorPosition++;
            display.setText(input.toString());
        } else if (command.matches("[+\\-*/]")) {
            input.insert(cursorPosition, " " + command + " ");
            cursorPosition += 3;
            display.setText(input.toString());
        } else if (command.equals("=")) {
            try {
                String result = evaluate(input.toString());
                display.setText(result);
                input.setLength(0);
                input.append(result);
                cursorPosition = input.length();
            } catch (Exception ex) {
                display.setText("Error");
                input.setLength(0);
                cursorPosition = 0;
            }
        } else if (command.equals("C")) {
            if (cursorPosition > 0) {
                input.deleteCharAt(cursorPosition - 1);
                cursorPosition--;
                display.setText(input.toString());
            }
        }
    }

    private String evaluate(String expression) throws Exception {
        String[] tokens = expression.split(" ");
        double result = Double.parseDouble(tokens[0]);

        for (int i = 1; i < tokens.length; i += 2) {
            String operator = tokens[i];
            double operand = Double.parseDouble(tokens[i + 1]);

            switch (operator) {
                case "+":
                    result += operand;
                    break;
                case "-":
                    result -= operand;
                    break;
                case "*":
                    result *= operand;
                    break;
                case "/":
                    if (operand == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    result /= operand;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator");
            }
        }
        return String.valueOf(result);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleCalculator calculator = new SimpleCalculator();
            calculator.setVisible(true);
        });
    }
}


