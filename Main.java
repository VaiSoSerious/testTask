import java.util.Scanner;

public class Main {
    final static String[] romanDigit = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
    final static String[] romanTen = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println(calc(inputScanner.nextLine()));
    }

    public static String convert (int num) {
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

    public static String calc(String input){
        try {
            expressionCheck(input);
        } catch (InvalidExpressionException e) {
            return e.getMessage();
        }

        Scanner stringScanner = new Scanner(input);

        int num1 = 0;
        String temp;
        boolean romanNum1 = false;
        if (stringScanner.hasNextInt()) {
            num1 = stringScanner.nextInt();
        }
        else {
            temp = stringScanner.next();
            for (int i = 0; i < romanDigit.length; i++) {
                if (temp.equals(romanDigit[i])){
                    num1 = i;
                    break;
                } else if (temp.equals("X")) {
                    num1 = 10;
                    break;
                }
            }
            romanNum1 = true;
        }
        try {
            valueCheck(num1);
        } catch (InvalidValueException e) {
            return e.getMessage();
        }

        String sign = stringScanner.next();

        int num2 = 0;
        boolean romanNum2 = false;
        if (stringScanner.hasNextInt()) {
            num2 = stringScanner.nextInt();
            try {
                romanModeCheck(romanNum1,romanNum2);
            } catch (DifferentTypesValuesException e) {
                return e.getMessage();
            }
            try {
                valueCheck(num2);
            } catch (InvalidValueException e) {
                return e.getMessage();
            }
        }
        else {
            temp = stringScanner.next();
            for (int i = 0; i < romanDigit.length; i++) {
                if (temp.equals(romanDigit[i])){
                    num2 = i;
                    break;
                } else if (temp.equals("X")) {
                    num2 = 10;
                    break;
                }
            }
            try {
                valueCheck(num2);
            } catch (InvalidValueException e) {
                return e.getMessage();
            }
            romanNum2 = true;
            try {
                romanModeCheck(romanNum1,romanNum2);
            } catch (DifferentTypesValuesException e) {
                return e.getMessage();
            }
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
            return e.getMessage();
        }

        if (romanNum1 && romanNum2){
            try {
                 return convert(Integer.parseInt(answer));
            } catch (InvalidValueException e) {
                return e.getMessage();
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