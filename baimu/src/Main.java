import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Calculator extends JFrame {

    private JTextField inputField;
    private double num1 = 0, num2 = 0, result = 0;
    private String operator = "";

    public Calculator() {
        super("Calculator");

        // Create GUI components
        JPanel mainPanel = new JPanel(new BorderLayout());
        inputField = new JTextField(10);
        inputField.setEditable(false);
        mainPanel.add(inputField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };
        for (String buttonLabel : buttonLabels) {
            JButton button = new JButton(buttonLabel);
            button.addActionListener(new CalculatorListener());
            buttonPanel.add(button);
        }
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Set frame properties
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private class CalculatorListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String buttonLabel = e.getActionCommand();

            if (buttonLabel.matches("[0-9]")) { // 数字按钮
                if (operator.isEmpty()) { // 如果没有操作符，说明是第一个数
                    num1 = num1 * 10 + Double.parseDouble(buttonLabel);
                } else { // 否则是第二个数
                    num2 = num2 * 10 + Double.parseDouble(buttonLabel);
                }
                inputField.setText(inputField.getText() + buttonLabel);
            } else if (buttonLabel.matches("[/,*,-,+]")) { // 操作符按钮
                if (!operator.isEmpty()) { // 如果已经有操作符了，先计算上一次的结果
                    calculate();
                }
                operator = buttonLabel;
                inputField.setText(inputField.getText() + buttonLabel);
            } else if (buttonLabel.equals(".")) { // 小数点按钮
                if (operator.isEmpty()) { // 如果没有操作符，说明是第一个数
                    if (!inputField.getText().contains(".")) {
                        inputField.setText(inputField.getText() + ".");
                    }
                } else { // 否则是第二个数
                    if (!inputField.getText().substring(inputField.getText().lastIndexOf(operator)).contains(".")) {
                        inputField.setText(inputField.getText() + ".");
                    }
                }
            } else if (buttonLabel.equals("=")) { // 等号按钮
                calculate();
                operator = "";
            }
        }

        private void calculate() {
            switch (operator) {
                case "/":
                    result = num1 / num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "+":
                    result = num1 + num2;
                    break;
            }
            inputField.setText(String.valueOf(result));
            num1 = result;
            num2 = 0;
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}