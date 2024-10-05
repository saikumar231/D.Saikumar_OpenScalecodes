package com.bills;
import java.io.*;
import java.util.*;

public class MinimumPriceFinder {

    public static void main(String[] args) {
        String csvFile = "C:\\Users\\Lenovo\\OneDrive\\Desktop\\data.csv"; // Path to your CSV file
        Scanner scanner = new Scanner(System.in);
        String inputItemsStr = scanner.nextLine();
        String[] inputItems = inputItemsStr.split(" ");
       // String[] inputItems = {"chef_salad wine_spritzer\r\n"}; // Input items to search for
        
        findMinimumPrice(csvFile, inputItems);
    }

    public static void findMinimumPrice(String csvFile, String[] inputItems) {
        Map<Integer, Map<String, Double>> restaurantMenu = new HashMap<>();

        // Step 1: Read the CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            
            // Skip the header line
            br.readLine(); // This skips the first line (header)

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int restaurantId = Integer.parseInt(values[0].trim());  // Parse restaurant_id
                double price = Double.parseDouble(values[1].trim());    // Parse food_item_price
                String foodItem = values[2].trim();                     // Get food_item_name

                // Add data to restaurant menu map
                restaurantMenu.computeIfAbsent(restaurantId, k -> new HashMap<>()).put(foodItem, price);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Step 2: Find the restaurant with minimum total price
        int minPriceRestaurantId = -1;
        double minPrice = Double.MAX_VALUE;

        for (Map.Entry<Integer, Map<String, Double>> entry : restaurantMenu.entrySet()) {
            int restaurantId = entry.getKey();
            Map<String, Double> menu = entry.getValue();

            // Check if the restaurant has all the required food items
            boolean hasAllItems = true;
            double totalPrice = 0.0;
            for (String item : inputItems) {
                if (!menu.containsKey(item)) {
                    hasAllItems = false;
                    break;
                } else {
                    totalPrice += menu.get(item);
                }
            }

            // Update the minimum price if current restaurant meets the criteria
            if (hasAllItems && totalPrice < minPrice) {
                minPriceRestaurantId = restaurantId;
                minPrice = totalPrice;
            }
        }

        // Step 3: Print the result
        if (minPriceRestaurantId == -1) {
            System.out.println("No matching restaurant found");
        } else {
            System.out.println(minPriceRestaurantId + ", " + String.format("%.2f", minPrice));
        }
    }
}
