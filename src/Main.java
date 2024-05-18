import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    public static void main(String[] args) {
        String inputFile = "C:/Users/leone/Desktop/PW4/PW4_UIPRPO_RUDLS/input.txt";
        String outputFile = "C:/Users/leone/Desktop/PW4/PW4_UIPRPO_RUDLS/output.txt";
        String newCountryCode = "+1";

        try {
            processFile(inputFile, outputFile, newCountryCode);
        } catch (IOException e) {
            System.out.println("Ошибка при обработке файла: " + e.getMessage());
        }
    }


    // Обработка чтения из файла и запись в файл
    private static void processFile(String inputFile, String outputFile, String newCountryCode) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             FileWriter writer = new FileWriter(outputFile)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String modifiedLine = modifyPhoneNumbers(line, newCountryCode);
                writer.write(modifiedLine + "\n");
            }
            System.out.println("Текст успешно обработан и записан в файл: " + outputFile);
        }
    }


    // Использование регулярных выражений для поиска телефонных номеров
    private static String modifyPhoneNumbers(String text, String countryCode) {
        String regex = "(\\+?\\d{1,3}[-.\\s]?\\d{3}[-.\\s]?\\d{3}[-.\\s]?\\d{2}[-.\\s]?\\d{2})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            String phoneNumber = matcher.group();
            phoneNumber = processPhoneNumber(phoneNumber, countryCode);
            matcher.appendReplacement(stringBuffer, " " + phoneNumber);
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }


    // Удаление лишних символов, добавление кода страны
    private static String processPhoneNumber(String phoneNumber, String countryCode) {
        phoneNumber = phoneNumber.replaceAll("[\\s\\-\\(\\)]", "");
        if (phoneNumber.length() < 10) {
            throw new RuntimeException("Некорректная длина номера");
        }
        phoneNumber = countryCode + phoneNumber.substring(phoneNumber.length() - 10);
        phoneNumber = formatPhoneNumber(phoneNumber);
        return phoneNumber;
    }


    // Форматирование номера
    private static String formatPhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("(\\+\\d)(\\d{3})(\\d{3})(\\d{2})(\\d{2})", "$1 ($2) $3-$4-$5");
    }
}