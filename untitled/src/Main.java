import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.google.gson.JsonObject;
import com.google.gson.Gson;

public class Main {
    private static final String BASE_API_URL = "https://api.exchangerate-api.com/v4/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Bienvenido al Conversor de Divisas");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Convertir moneda");
            System.out.println("2. Salir");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    convertCurrency(scanner);
                    break;
                case 2:
                    System.out.println("¡Gracias por usar el Conversor de Divisas!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione de nuevo.");
            }
        }
    }

    private static void convertCurrency(Scanner scanner) {
        System.out.println("Seleccione la moneda base:");
        System.out.println("1. Euro (EUR)");
        System.out.println("2. Dólar Estadounidense (USD)");
        System.out.println("3. Peso Dominicano (DOP)");
        System.out.println("4. Libra Esterlina (GBP)");
        System.out.println("5. Yen Japonés (JPY)");
        System.out.println("6. Dólar Canadiense (CAD)");
        System.out.println("7. Franco Suizo (CHF)");
        System.out.println("8. Corona Sueca (SEK)");
        System.out.println("9. Corona Noruega (NOK)");
        System.out.println("10. Peso Mexicano (MXN)");

        int currencyChoice = scanner.nextInt();
        scanner.nextLine();

        String baseCurrency = "";
        switch (currencyChoice) {
            case 1:
                baseCurrency = "EUR";
                break;
            case 2:
                baseCurrency = "USD";
                break;
            case 3:
                baseCurrency = "DOP";
                break;
            case 4:
                baseCurrency = "GBP";
                break;
            case 5:
                baseCurrency = "JPY";
                break;
            case 6:
                baseCurrency = "CAD";
                break;
            case 7:
                baseCurrency = "CHF";
                break;
            case 8:
                baseCurrency = "SEK";
                break;
            case 9:
                baseCurrency = "NOK";
                break;
            case 10:
                baseCurrency = "MXN";
                break;
            default:
                System.out.println("Moneda inválida. Inténtelo de nuevo.");
                return;
        }

        System.out.println("Seleccione la moneda de destino:");
        System.out.println("1. Euro (EUR)");
        System.out.println("2. Dólar Estadounidense (USD)");
        System.out.println("3. Peso Dominicano (DOP)");
        System.out.println("4. Libra Esterlina (GBP)");
        System.out.println("5. Yen Japonés (JPY)");
        System.out.println("6. Dólar Canadiense (CAD)");
        System.out.println("7. Franco Suizo (CHF)");
        System.out.println("8. Corona Sueca (SEK)");
        System.out.println("9. Corona Noruega (NOK)");
        System.out.println("10. Peso Mexicano (MXN)");

        int targetCurrencyChoice = scanner.nextInt();
        scanner.nextLine();

        String targetCurrency = "";
        switch (targetCurrencyChoice) {
            case 1:
                targetCurrency = "EUR";
                break;
            case 2:
                targetCurrency = "USD";
                break;
            case 3:
                targetCurrency = "DOP";
                break;
            case 4:
                targetCurrency = "GBP";
                break;
            case 5:
                targetCurrency = "JPY";
                break;
            case 6:
                targetCurrency = "CAD";
                break;
            case 7:
                targetCurrency = "CHF";
                break;
            case 8:
                targetCurrency = "SEK";
                break;
            case 9:
                targetCurrency = "NOK";
                break;
            case 10:
                targetCurrency = "MXN";
                break;
            default:
                System.out.println("Moneda inválida. Inténtelo de nuevo.");
                return;
        }

        System.out.println("Introduce la cantidad a convertir:");
        double amount = scanner.nextDouble();

        double conversionRate = getConversionRate(baseCurrency, targetCurrency);

        if (conversionRate != -1) {
            double convertedAmount = amount * conversionRate;
            System.out.printf("%.2f %s es igual a %.2f %s%n", amount, baseCurrency, convertedAmount, targetCurrency);
        } else {
            System.out.println("Error al obtener la tasa de conversión. Intenta nuevamente más tarde.");
        }
    }

    private static double getConversionRate(String baseCurrency, String targetCurrency) {
        try {
            URL url = new URL(BASE_API_URL + baseCurrency);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);
            JsonObject rates = jsonResponse.getAsJsonObject("rates");


            if (rates.has(targetCurrency)) {
                return rates.get(targetCurrency).getAsDouble();
            } else {
                System.out.println("No se encontró la tasa de conversión para la moneda de destino.");
                return -1;
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return -1;
        }
    }
}
