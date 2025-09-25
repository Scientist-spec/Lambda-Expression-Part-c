import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
            new Product("Laptop", 1200.0, "Electronics"),
            new Product("Smartphone", 800.0, "Electronics"),
            new Product("Tablet", 500.0, "Electronics"),
            new Product("T-Shirt", 25.0, "Clothing"),
            new Product("Jeans", 45.0, "Clothing"),
            new Product("Jacket", 89.0, "Clothing"),
            new Product("Novel", 15.0, "Books"),
            new Product("Textbook", 95.0, "Books"),
            new Product("Mystery Book", 12.0, "Books"),
            new Product("Coffee Maker", 75.0, "Home Appliances"),
            new Product("Blender", 45.0, "Home Appliances"),
            new Product("Headphones", 150.0, "Electronics"),
            new Product("Sneakers", 65.0, "Footwear"),
            new Product("Sandals", 35.0, "Footwear")
        );
        
        System.out.println("=== ALL PRODUCTS ===");
        products.forEach(System.out::println);
        
        System.out.println("\n=== 1. GROUP PRODUCTS BY CATEGORY ===");
        Map<String, List<Product>> productsByCategory = products.stream()
            .collect(Collectors.groupingBy(Product::getCategory));
        
        productsByCategory.forEach((category, productList) -> {
            System.out.println("\nCategory: " + category);
            productList.forEach(p -> System.out.println("  - " + p.getName() + ": $" + p.getPrice()));
        });
        
        System.out.println("\n=== 2. MOST EXPENSIVE PRODUCT IN EACH CATEGORY ===");
        Map<String, Optional<Product>> mostExpensiveByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.maxBy(Comparator.comparingDouble(Product::getPrice))
            ));
        
        mostExpensiveByCategory.forEach((category, productOpt) -> {
            productOpt.ifPresent(product -> 
                System.out.println(category + ": " + product.getName() + " - $" + product.getPrice())
            );
        });
        
        System.out.println("\n=== 3. AVERAGE PRICE OF ALL PRODUCTS ===");
        double overallAveragePrice = products.stream()
            .collect(Collectors.averagingDouble(Product::getPrice));
        System.out.println("Overall Average Price: $" + String.format("%.2f", overallAveragePrice));
        
        System.out.println("\n=== 4. AVERAGE PRICE BY CATEGORY ===");
        Map<String, Double> averagePriceByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.averagingDouble(Product::getPrice)
            ));
        
        averagePriceByCategory.forEach((category, avgPrice) -> {
            System.out.println(category + ": $" + String.format("%.2f", avgPrice));
        });
        
        System.out.println("\n=== 5. ADDITIONAL STATISTICS ===");
        DoubleSummaryStatistics stats = products.stream()
            .collect(Collectors.summarizingDouble(Product::getPrice));
        
        System.out.println("Total Products: " + stats.getCount());
        System.out.println("Highest Price: $" + stats.getMax());
        System.out.println("Lowest Price: $" + stats.getMin());
        System.out.println("Average Price: $" + String.format("%.2f", stats.getAverage()));
        System.out.println("Total Value: $" + String.format("%.2f", stats.getSum()));
    }
}
