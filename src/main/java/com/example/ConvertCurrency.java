package com.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConvertCurrency extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Map<String, Double> EXCHANGE_RATES = new HashMap<>();

    static {
        // Initialize exchange rates
        EXCHANGE_RATES.put("USD", 1.0);
        EXCHANGE_RATES.put("EUR", 0.85); // Euro (European Union)
        EXCHANGE_RATES.put("GBP", 0.72); // British Pound (United Kingdom)
        EXCHANGE_RATES.put("JPY", 112.26); // Japanese Yen (Japan)
        EXCHANGE_RATES.put("AUD", 1.32); // Australian Dollar (Australia)
        EXCHANGE_RATES.put("CAD", 1.28); // Canadian Dollar (Canada)
        EXCHANGE_RATES.put("INR", 74.23); // Indian Rupee (India)
        EXCHANGE_RATES.put("CNY", 6.38); // Chinese Yuan (China)
        EXCHANGE_RATES.put("BRL", 5.30); // Brazilian Real (Brazil)
        EXCHANGE_RATES.put("MXN", 20.23); // Mexican Peso (Mexico)
        EXCHANGE_RATES.put("ZAR", 16.86); // South African Rand (South Africa)
        EXCHANGE_RATES.put("RUB", 74.86); // Russian Ruble (Russia)
        EXCHANGE_RATES.put("KRW", 1142.60); // South Korean Won (South Korea)
        EXCHANGE_RATES.put("TRY", 13.16); // Turkish Lira (Turkey)
        EXCHANGE_RATES.put("AED", 3.67); // United Arab Emirates Dirham (United Arab Emirates)
        EXCHANGE_RATES.put("SGD", 1.35); // Singapore Dollar (Singapore)
        EXCHANGE_RATES.put("HKD", 7.77); // Hong Kong Dollar (Hong Kong)
        EXCHANGE_RATES.put("NZD", 1.42); // New Zealand Dollar (New Zealand)
        EXCHANGE_RATES.put("CHF", 0.92); // Swiss Franc (Switzerland)
        EXCHANGE_RATES.put("SEK", 8.86); // Swedish Krona (Sweden)
        EXCHANGE_RATES.put("NOK", 8.69); // Norwegian Krone (Norway)
        EXCHANGE_RATES.put("DKK", 6.34); // Danish Krone (Denmark)
        EXCHANGE_RATES.put("ISK", 129.83); // Icelandic Krona (Iceland)
        EXCHANGE_RATES.put("HUF", 307.63); // Hungarian Forint (Hungary)
        EXCHANGE_RATES.put("PLN", 3.94); // Polish Zloty (Poland)
        EXCHANGE_RATES.put("THB", 32.17); // Thai Baht (Thailand)
        EXCHANGE_RATES.put("PHP", 51.21); // Philippine Peso (Philippines)
        EXCHANGE_RATES.put("IDR", 14440.97); // Indonesian Rupiah (Indonesia)
        EXCHANGE_RATES.put("MYR", 4.12); // Malaysian Ringgit (Malaysia)
        EXCHANGE_RATES.put("ARS", 114.68); // Argentine Peso (Argentina)
        EXCHANGE_RATES.put("ILS", 3.25); // Israeli New Shekel (Israel)
        EXCHANGE_RATES.put("COP", 4163.90); // Colombian Peso (Colombia)
        EXCHANGE_RATES.put("EGP", 15.68); // Egyptian Pound (Egypt)
        EXCHANGE_RATES.put("CLP", 835.92); // Chilean Peso (Chile)
        EXCHANGE_RATES.put("VND", 23117.71); // Vietnamese Dong (Vietnam)
        EXCHANGE_RATES.put("UAH", 27.68); // Ukrainian Hryvnia (Ukraine)
        EXCHANGE_RATES.put("SAR", 3.75); // Saudi Riyal (Saudi Arabia)
        EXCHANGE_RATES.put("KWD", 0.30); // Kuwaiti Dinar (Kuwait)
        EXCHANGE_RATES.put("IQD", 1459.83); // Iraqi Dinar (Iraq)
        EXCHANGE_RATES.put("QAR", 3.64); // Qatari Riyal (Qatar)
        EXCHANGE_RATES.put("CRC", 617.58); // Costa Rican Colon (Costa Rica)
        EXCHANGE_RATES.put("PEN", 4.07); // Peruvian Sol (Peru)
        EXCHANGE_RATES.put("PKR", 184.68); // Pakistani Rupee (Pakistan)
        EXCHANGE_RATES.put("OMR", 0.39); // Omani Rial (Oman)
        EXCHANGE_RATES.put("TWD", 27.94); // New Taiwan Dollar (Taiwan)
        EXCHANGE_RATES.put("HRK", 6.54); // Croatian Kuna (Croatia)
        // Add more exchange rates with corresponding country comments as needed
        // Add more exchange rates as needed...
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String amountParam = request.getParameter("amount");
        String sourceCurrency = request.getParameter("sourceCurrency");
        String targetCurrency = request.getParameter("targetCurrency");

        if (amountParam == null || amountParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Amount is required.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountParam);

            if (!isValidCurrencyCode(sourceCurrency) || !isValidCurrencyCode(targetCurrency)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid currency codes.");
                return;
            }

            if (EXCHANGE_RATES.containsKey(sourceCurrency) && EXCHANGE_RATES.containsKey(targetCurrency)) {

                double convertedAmount = performCurrencyConversion(amount, sourceCurrency, targetCurrency);
                String sourceCurrencySymbol = getCurrencySymbol(sourceCurrency);
                String targetCurrencySymbol = getCurrencySymbol(targetCurrency);

                request.setAttribute("amount", amount);
                request.setAttribute("sourceCurrency", sourceCurrency);
                request.setAttribute("sourceCurrencySymbol", sourceCurrencySymbol);
                request.setAttribute("targetCurrency", targetCurrency);
                request.setAttribute("targetCurrencySymbol", targetCurrencySymbol);
                request.setAttribute("convertedAmount", convertedAmount);

                request.getRequestDispatcher("result.jsp").forward(request, response);

            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid currency codes.");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid amount. Please enter a valid numeric value.");
        }
    }

    private boolean isValidCurrencyCode(String code) {
        return code != null && EXCHANGE_RATES.containsKey(code);
    }

    private double performCurrencyConversion(double amount, String sourceCurrency, String targetCurrency) {
        double sourceRate = EXCHANGE_RATES.get(sourceCurrency);
        double targetRate = EXCHANGE_RATES.get(targetCurrency);

        return amount * (targetRate / sourceRate);
    }

    private String getCurrencySymbol(String currencyCode) {
        switch (currencyCode) {
            case "USD":
                return "$";
            case "EUR":
                return "€";
            case "GBP":
                return "£";
            case "JPY":
                return "¥";
            case "AUD":
                return "A$";
            case "CAD":
                return "C$";
            case "INR":
                return "₹";
            case "CNY":
                return "¥";
            case "BRL":
                return "R$";
            case "MXN":
                return "Mex$";
            case "ZAR":
                return "R";
            case "RUB":
                return "₽";
            case "KRW":
                return "₩";
            case "TRY":
                return "₺";
            case "AED":
                return "د.إ";
            case "SGD":
                return "S$";
            case "HKD":
                return "HK$";
            case "NZD":
                return "NZ$";
            case "CHF":
                return "CHF";
            case "SEK":
                return "kr";
            case "NOK":
                return "kr";
            case "DKK":
                return "kr";
            case "ISK":
                return "kr";
            case "HUF":
                return "Ft";
            case "PLN":
                return "zł";
            case "THB":
                return "฿";
            case "PHP":
                return "₱";
            case "IDR":
                return "Rp";
            case "MYR":
                return "RM";
            case "ARS":
                return "$";
            case "ILS":
                return "₪";
            case "COP":
                return "$";
            case "EGP":
                return "£";
            case "CLP":
                return "$";
            case "VND":
                return "₫";
            case "UAH":
                return "₴";
            case "SAR":
                return "﷼";
            case "KWD":
                return "ك";
            case "IQD":
                return "ع.د";
            case "QAR":
                return "ر.ق";
            case "CRC":
                return "₡";
            case "PEN":
                return "S/.";
            case "PKR":
                return "₨";
            case "OMR":
                return "ر.ع.";
            case "TWD":
                return "NT$";
            case "HRK":
                return "kn";
            default:
                return ""; // Return empty string if symbol not found
        }
    }
}
