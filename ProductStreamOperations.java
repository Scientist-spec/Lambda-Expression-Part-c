import java.util.*;
import java.util.stream.Collectors;

public class ProductStreamOperations {
    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
            new Product("Laptop", 1200.0, "Electronics"),
            new Product("Phone", 800.0, "Electronics"),
            new Product("Shirt", 30.0, "Clothing"),
            new Product("Book", 20.0, "Books"),
            new Product("Monitor", 300.0, "Electronics"),
            new Product("Shoes", 80.0, "Clothing")
        );
        
        System.out.println("=== CONCISE STREAM OPERATIONS DEMO ===");
        
        System.out.println("1. Grouping by category:");
        products.stream()
            .collect(Collectors.groupingBy(Product::getCategory))
            .forEach((cat, prods) -> {
                System.out.println(cat + ": " + 
                    prods.stream().map(Product::getName).collect(Collectors.joining(", ")));
            });
        
        System.out.println("\n2. Most expensive by category:");
        products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.maxBy(Comparator.comparingDouble(Product::getPrice))
            ))
            .forEach((cat, optProd) -> 
                optProd.ifPresent(p -> System.out.println(cat + ": " + p.getName() + " - $" + p.getPrice()))
            );
        
        System.out.println("\n3. Average price: $" + 
            String.format("%.2f", products.stream()
                .collect(Collectors.averagingDouble(Product::getPrice)))
        );
        
        System.out.println("\n4. Average price by category:");
        products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.averagingDouble(Product::getPrice)
            ))
            .forEach((cat, avg) -> System.out.println(cat + ": $" + String.format("%.2f", avg)));
    }
}
