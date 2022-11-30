import java.util.Objects;
import java.util.Scanner;

public class Main {
    final static String[] romanDigit = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
    final static String[] romanTen = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println(calc(inputScanner.nextLine()));
    }

    public static String convertToRoman (int num) {
        if (num < 1) {
            throw new InvalidValueException("throws InvalidValueException: " +
                    "Римские числа могут быть только положительными");
        }
        return num != 100 ? romanTen[num / 10] + romanDigit[num % 10] : "C";
    }

    public static void valueCheck (int number) {
        if (number < 1 || number > 10){
            throw new InvalidValueException("throws InvalidValueException: Введены неверные значения операндов");
        }
    }

    public static void romanModeCheck (boolean romanNum1, boolean romanNum2) {
        if (!((romanNum1 && romanNum2) || (!romanNum1 && !romanNum2))){
            throw new DifferentTypesValuesException("throws DifferentTypesValuesException: " +
                    "Введены разные типы операндов");
        }
    }

    public static void signCheck (String answer) {
        if(answer == null){
            throw new WrongSignException("throws WrongSignException: Введен неверный знак");
        }
    }

    public static void expressionCheck (String input) {
        String[] inputArray = input.split(" ");
        if (inputArray.length != 3){
            throw new InvalidExpressionException("throws InvalidExpressionException: Введено неверное выражение");
        }
    }
    public static int convertToArab(String romanNumber){
        int arabNumber = 0;
        for (int i = 0; i < romanDigit.length; i++) {
            if (romanNumber.equals(romanDigit[i])){
                arabNumber = i;
                break;
            } else if (romanNumber.equals("X")) {
                arabNumber = 10;
                break;
            }
        }
        return arabNumber;
    }

    public static void operandCheck(int number, boolean romanNum1, boolean romanNum2){
        if (romanNum2){
            try {
                valueCheck(number);
            } catch (InvalidValueException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
            try {
                romanModeCheck(romanNum1,romanNum2);
            } catch (DifferentTypesValuesException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
        } else {
            try {
                romanModeCheck(romanNum1,romanNum2);
            } catch (DifferentTypesValuesException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
            try {
                valueCheck(number);
            } catch (InvalidValueException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }
    }

    public static String calc(String input){
        try {
            expressionCheck(input);
        } catch (InvalidExpressionException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        Scanner stringScanner = new Scanner(input);

        int num1;
        boolean romanNum1 = false;
        if (stringScanner.hasNextInt()) {
            num1 = stringScanner.nextInt();
        }
        else {
            num1 = convertToArab(stringScanner.next());
            romanNum1 = true;
        }
        try {
            valueCheck(num1);
        } catch (InvalidValueException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        String sign = stringScanner.next();

        int num2;
        boolean romanNum2 = false;
        if (stringScanner.hasNextInt()) {
            num2 = stringScanner.nextInt();
            operandCheck(num2,romanNum1,romanNum2);
        }
        else {
            romanNum2 = true;
            num2 = convertToArab(stringScanner.next());
            operandCheck(num2,romanNum1,romanNum2);
        }

        String answer;
        answer = switch (sign) {
            case "+" -> "" + (num1 + num2);
            case "-" -> "" + (num1 - num2);
            case "*" -> "" + (num1 * num2);
            case "/" -> "" + (num1 / num2);
            default -> null;
        };
        try {
            signCheck(answer);
        } catch (WrongSignException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        if (romanNum1 && romanNum2){
            try {
                 return convertToRoman(Integer.parseInt(answer));
            } catch (InvalidValueException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }
        return answer;
    }
}

class InvalidExpressionException extends RuntimeException {
    public InvalidExpressionException(String description){
        super(description);
    }
}

class WrongSignException extends RuntimeException{
    public WrongSignException(String description){
        super(description);
    }
}

class DifferentTypesValuesException extends RuntimeException{
    public DifferentTypesValuesException(String description){
        super(description);
    }
}

class InvalidValueException extends RuntimeException{
    public InvalidValueException(String description){
        super(description);
    }
}