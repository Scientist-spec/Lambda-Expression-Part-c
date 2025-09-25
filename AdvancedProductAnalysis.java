import java.util.*;
import java.util.stream.Collectors;

public class AdvancedProductAnalysis {
    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
            new Product("MacBook Pro", 2500.0, "Electronics"),
            new Product("iPhone", 999.0, "Electronics"),
            new Product("iPad", 799.0, "Electronics"),
            new Product("Designer Dress", 299.0, "Clothing"),
            new Product("Casual Shirt", 45.0, "Clothing"),
            new Product("Winter Coat", 199.0, "Clothing"),
            new Product("Bestseller Novel", 12.0, "Books"),
            new Product("Academic Journal", 45.0, "Books"),
            new Product("Cookbook", 25.0, "Books"),
            new Product("Refrigerator", 899.0, "Home Appliances"),
            new Product("Washing Machine", 699.0, "Home Appliances"),
            new Product("Gaming Console", 499.0, "Electronics"),
            new Product("Running Shoes", 120.0, "Footwear"),
            new Product("Formal Shoes", 85.0, "Footwear")
        );
        
        System.out.println("=== ADVANCED PRODUCT ANALYSIS ===");
        
        System.out.println("\n1. Products grouped by category with counts:");
        Map<String, List<Product>> categoryGroups = products.stream()
            .collect(Collectors.groupingBy(Product::getCategory));
        
        categoryGroups.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> {
                System.out.println("\n" + entry.getKey() + " (" + entry.getValue().size() + " products):");
                entry.getValue().forEach(p -> 
                    System.out.println("   " + p.getName() + " - $" + p.getPrice())
                );
            });
        
        System.out.println("\n2. Most expensive product per category:");
        Map<String, Optional<Product>> maxPriceByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.maxBy(Comparator.comparingDouble(Product::getPrice))
            ));
        
        maxPriceByCategory.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> {
                entry.getValue().ifPresent(product -> 
                    System.out.println(entry.getKey() + ": " + product.getName() + " - $" + product.getPrice())
                );
            });
        
        System.out.println("\n3. Price statistics by category:");
        Map<String, DoubleSummaryStatistics> statsByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.summarizingDouble(Product::getPrice)
            ));
        
        statsByCategory.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> {
                DoubleSummaryStatistics stats = entry.getValue();
                System.out.println("\n" + entry.getKey() + ":");
                System.out.println("   Count: " + stats.getCount());
                System.out.println("   Average: $" + String.format("%.2f", stats.getAverage()));
                System.out.println("   Max: $" + String.format("%.2f", stats.getMax()));
                System.out.println("   Min: $" + String.format("%.2f", stats.getMin()));
                System.out.println("   Total: $" + String.format("%.2f", stats.getSum()));
            });
        
        System.out.println("\n4. Products above average price:");
        double overallAverage = products.stream()
            .mapToDouble(Product::getPrice)
            .average()
            .orElse(0.0);
        
        System.out.println("Overall average price: $" + String.format("%.2f", overallAverage));
        System.out.println("Products above average:");
        
        products.stream()
            .filter(p -> p.getPrice() > overallAverage)
            .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
            .forEach(p -> System.out.println("   " + p.getName() + " - $" + p.getPrice() + " (" + p.getCategory() + ")"));
        
        System.out.println("\n5. Category with highest average price:");
        Map<String, Double> avgPriceByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.averagingDouble(Product::getPrice)
            ));
        
        avgPriceByCategory.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .ifPresent(entry -> 
                System.out.println("Highest average price: " + entry.getKey() + " - $" + String.format("%.2f", entry.getValue()))
            );
    }
}
