import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    private static String[] op = { "+", "-", "*", "/" };// Operation set
    public static void main(String[] args)
    {
        String res="";
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入一个整数：");
        int num = scanner.nextInt();
        while (num>=1)
        {
            String question = MakeFormula();
            System.out.println(question);
            String ret = Solve(question);
            System.out.println(ret);
            res+=(question+"\n"+ret+"\n");
            num--;
        }

        try {
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter("subject.txt"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writer.write(res);
            writer.close();
            System.out.println("字符串已成功写入文件。");
        } catch (IOException e) {
            System.out.println("写入文件时出错：" + e.getMessage());
        }

    }

    public static String MakeFormula(){
        StringBuilder build = new StringBuilder();
        int count = (int) (Math.random() * 2) + 1; // generate random count
        int start = 0;
        int number1 = (int) (Math.random() * 99) + 2;
        build.append(number1);
        while (start <= count){
            int operation = (int) (Math.random() * 3); // generate operator
            int number2 = (int) (Math.random() * 99) + 2;
            build.append(op[operation]).append(number2);
            start ++;
        }
        return build.toString();
    }

    public static String Solve(String formula) {
        // Check if the formula is null or empty
        if (formula == null || formula.isEmpty()) {
            throw new IllegalArgumentException("Formula cannot be null or empty");
        }
        Stack<String> tempStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();
        int len = formula.length();
        int k = 0;
        StringBuilder sb = new StringBuilder(); // Use a StringBuilder to append characters
        for (int j = -1; j < len - 1; j++) {
            char formulaChar = formula.charAt(j + 1);
            if (j == len - 2 || formulaChar == '+' || formulaChar == '-' || formulaChar == '/' || formulaChar == '*') {
                if (j == len - 2) {
                    sb.append(formulaChar); // Append the last character
                    tempStack.push(sb.toString()); // Push the last operand
                } else {
                    if (k < j) {
                        tempStack.push(sb.toString()); // Push the current operand
                        sb.setLength(0); // Clear the StringBuilder
                    }
                    if (operatorStack.empty()) {
                        operatorStack.push(formulaChar);
                    } else {
                        char stackChar = operatorStack.peek();
                        if ((stackChar == '+' || stackChar == '-')
                                && (formulaChar == '*' || formulaChar == '/')) {
                            operatorStack.push(formulaChar);
                        } else {
                            tempStack.push(operatorStack.pop().toString());
                            operatorStack.push(formulaChar);
                        }
                    }
                }
                k = j + 2;
            } else {
                // Check if the character is valid
                if (Character.isDigit(formulaChar) || formulaChar == '.') {
                    sb.append(formulaChar); // Append the character to the StringBuilder
                } else {
                    throw new IllegalArgumentException("Formula contains invalid character: " + formulaChar);
                }
            }
        }
        while (!operatorStack.empty()) {
            tempStack.push(operatorStack.pop().toString());
        }
        Stack<Long> calcStack = new Stack<>();
        for (String peekChar : tempStack) {
            switch (peekChar) { // Use a switch statement to check for different operators
                case "+":
                    calcStack.push(calcStack.pop() + calcStack.pop());
                    break;
                case "-":
                    long a1 = calcStack.pop();
                    long b1 = calcStack.pop();
                    calcStack.push(b1 - a1);
                    break;
                case "*":
                    calcStack.push(calcStack.pop() * calcStack.pop());
                    break;
                case "/":
                    long a2 = calcStack.pop();
                    long b2 = calcStack.pop();
                    // Check if the divisor is zero
                    if (a2 == 0) {
                        throw new ArithmeticException("Formula involves division by zero");
                    }
                    calcStack.push(b2 / a2);
                    break;
                default:
                    calcStack.push(Long.parseLong(peekChar));
            }
        }
        return formula+" = "+calcStack.pop().toString(); // Return the final result as a string
    }
}
