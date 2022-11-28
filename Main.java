import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    final static String[] romanDigit = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
    final static String[] romanTen = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        try {
            System.out.println(calc(inputScanner.nextLine()));
        } catch (NoSuchElementException e) {
            System.out.println("throws Exception");
        }

    }

    public static String convert (int num){
        if (num < 1)
            try {
                throw new InvalidValueException();
            } catch (InvalidValueException e) {
                return "throws Exception";
            }

        return num != 100 ? romanTen[num % 100 / 10] + romanDigit[num % 10] : "C";
    }

    public static String calc(String input){
        int num1 = 0, num2 = 0;
        String sign, answer, temp;
        boolean romanMode = false;
        Scanner stringScanner = new Scanner(input);

        if (stringScanner.hasNextInt())
            num1 = stringScanner.nextInt();
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
            romanMode = true;
        }
        if (num1 < 1 || num1 > 10)
            try {
                throw new InvalidValueException();
            } catch (InvalidValueException e) {
                return "throws Exception";
            }

        sign = stringScanner.next();

        if (stringScanner.hasNextInt()) {
            num2 = stringScanner.nextInt();

            if (romanMode)
                try {
                    throw new DifferentTypesValuesException();
                } catch (DifferentTypesValuesException e) {
                    return "throws Exception";
                }
        }
        else {
            if (!romanMode)
                try {
                    throw new DifferentTypesValuesException();
                } catch (DifferentTypesValuesException e) {
                    return "throws Exception";
                }

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
        }
        if (num2 < 1 || num2 > 10)
            try {
                throw new InvalidValueException();
            } catch (InvalidValueException e) {
                return "throws Exception";
            }
        answer = switch (sign) {
            case "+" -> "" + (num1 + num2);
            case "-" -> "" + (num1 - num2);
            case "*" -> "" + (num1 * num2);
            case "/" -> "" + (num1 / num2);
            default -> null;
        };
        if(answer == null)
            try {
                throw new WrongSignException();
            } catch (WrongSignException e) {
                return "throws Exception";
            }
        return romanMode ? convert(Integer.parseInt(answer)) : answer;
    }
}