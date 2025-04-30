import java.util.*;

// --- Abstract Class ---
abstract class StockItem {
    protected String itemId, itemName, category, supplier;
    protected int quantityInStock;
    protected double pricePerUnit;

    public StockItem(String itemId, String itemName, int quantityInStock, double pricePerUnit, String category,
            String supplier) {
        if (quantityInStock < 0)
            throw new IllegalArgumentException("Quantity cannot be negative.");
        if (pricePerUnit <= 0)
            throw new IllegalArgumentException("Price must be above zero.");

        this.itemId = itemId;
        this.itemName = itemName;
        this.quantityInStock = quantityInStock;
        this.pricePerUnit = pricePerUnit;
        this.category = category;
        this.supplier = supplier;
    }

    public abstract void updateStock(int quantity);

    public abstract double calculateStockValue();

    public abstract void generateStockReport();

    public abstract void validateStock();
}

// --- Subclasses ---

class ElectronicItem extends StockItem {
    private int warrantyPeriod;
    private double discount;

    public ElectronicItem(String itemId, String itemName, int quantity, double price, String supplier,
            int warrantyPeriod, double discount) {
        super(itemId, itemName, quantity, price, "Electronics", supplier);
        if (discount < 0 || discount > 50)
            throw new IllegalArgumentException("Discount must be 0-50%");
        if (warrantyPeriod < 0 || warrantyPeriod > 36)
            throw new IllegalArgumentException("Warranty must be 0-36 months");
        this.warrantyPeriod = warrantyPeriod;
        this.discount = discount;
    }

    public void applyDiscount() {
        pricePerUnit -= (pricePerUnit * discount / 100);
    }

    @Override
    public void updateStock(int quantity) {
        this.quantityInStock += quantity;
    }

    @Override
    public double calculateStockValue() {
        return quantityInStock * pricePerUnit;
    }

    @Override
    public void generateStockReport() {
        System.out.println("[Electronics] " + itemName + " | Stock: " + quantityInStock + " | Price: " + pricePerUnit
                + " | Warranty: " + warrantyPeriod + " months");
    }

    @Override
    public void validateStock() {
        if (quantityInStock <= 0)
            System.out.println(itemName + " is out of stock!");
    }
}

class ClothingItem extends StockItem {
    private List<String> sizes, colors;
    private boolean hasDiscount;

    public ClothingItem(String itemId, String itemName, int quantity, double price, String supplier, List<String> sizes,
            List<String> colors, boolean hasDiscount) {
        super(itemId, itemName, quantity, price, "Clothing", supplier);
        this.sizes = sizes;
        this.colors = colors;
        this.hasDiscount = hasDiscount;
    }

    @Override
    public void updateStock(int quantity) {
        this.quantityInStock += quantity;
    }

    @Override
    public double calculateStockValue() {
        return quantityInStock * pricePerUnit * (hasDiscount ? 0.9 : 1.0);
    }

    @Override
    public void generateStockReport() {
        System.out.println("[Clothing] " + itemName + " | Stock: " + quantityInStock + " | Sizes: " + sizes
                + " | Colors: " + colors);
    }

    @Override
    public void validateStock() {
        if (quantityInStock <= 0)
            System.out.println(itemName + " is out of stock.");
    }
}

class GroceryItem extends StockItem {
    private Date expirationDate;

    public GroceryItem(String itemId, String itemName, int quantity, double price, String supplier,
            Date expirationDate) {
        super(itemId, itemName, quantity, price, "Groceries", supplier);
        this.expirationDate = expirationDate;
    }

    @Override
    public void updateStock(int quantity) {
        this.quantityInStock += quantity;
    }

    @Override
    public double calculateStockValue() {
        return quantityInStock * pricePerUnit;
    }

    @Override
    public void generateStockReport() {
        System.out
                .println("[Grocery] " + itemName + " | Stock: " + quantityInStock + " | Expiration: " + expirationDate);
    }

    @Override
    public void validateStock() {
        Date now = new Date();
        long diff = expirationDate.getTime() - now.getTime();
        long daysLeft = diff / (1000 * 60 * 60 * 24);
        if (daysLeft <= 5)
            System.out.println(itemName + " is near expiration!");
    }
}

class FurnitureItem extends StockItem {
    private double weight;

    public FurnitureItem(String itemId, String itemName, int quantity, double price, String supplier, double weight) {
        super(itemId, itemName, quantity, price, "Furniture", supplier);
        this.weight = weight;
    }

    @Override
    public void updateStock(int quantity) {
        this.quantityInStock += quantity;
    }

    @Override
    public double calculateStockValue() {
        return quantityInStock * pricePerUnit;
    }

    @Override
    public void generateStockReport() {
        System.out.println("[Furniture] " + itemName + " | Weight: " + weight + "kg | Stock: " + quantityInStock);
    }

    @Override
    public void validateStock() {
        System.out.println(itemName + " must be packaged properly before delivery.");
    }
}

class PerishableItem extends StockItem {
    private Date expirationDate;

    public PerishableItem(String itemId, String itemName, int quantity, double price, String supplier,
            Date expirationDate) {
        super(itemId, itemName, quantity, price, "Perishable", supplier);
        this.expirationDate = expirationDate;
    }

    @Override
    public void updateStock(int quantity) {
        this.quantityInStock += quantity;
    }

    @Override
    public double calculateStockValue() {
        return quantityInStock * pricePerUnit;
    }

    @Override
    public void generateStockReport() {
        System.out
                .println("[Perishable] " + itemName + " | Stock: " + quantityInStock + " | Expiry: " + expirationDate);
    }

    @Override
    public void validateStock() {
        Date now = new Date();
        if (expirationDate.before(now))
            System.out.println(itemName + " has expired!");
    }
}

// --- Main Program ---
public class StockManagementSystem {
    static Scanner sc = new Scanner(System.in);
    static List<StockItem> items = new ArrayList<>();

    public static void start() {
        while (true) {
            System.out.println("\n--- STOCK MANAGEMENT MENU ---");
            System.out.println("1. Add Electronics Item");
            System.out.println("2. Add Clothing Item");
            System.out.println("3. Add Grocery Item");
            System.out.println("4. Add Furniture Item");
            System.out.println("5. Add Perishable Item");
            System.out.println("6. View Stock Report");
            System.out.println("0. Return to Main Menu");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addElectronicsItem();
                case 2:
                    addClothingItem();
                case 3:
                    addGroceryItem();
                case 4:
                    addFurnitureItem();
                case 5:
                    addPerishableItem();
                case 6:
                    showReport();
                case 7:
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addElectronicsItem() {
        System.out.print("Item ID: ");
        String id = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Quantity: ");
        int qty = sc.nextInt();
        System.out.print("Price per unit: ");
        double price = sc.nextDouble();
        sc.nextLine();
        System.out.print("Supplier: ");
        String supplier = sc.nextLine();
        System.out.print("Warranty (months): ");
        int warranty = sc.nextInt();
        System.out.print("Discount (%): ");
        double discount = sc.nextDouble();

        ElectronicItem ei = new ElectronicItem(id, name, qty, price, supplier, warranty, discount);
        ei.applyDiscount();
        items.add(ei);
        System.out.println("Electronics item added!");
    }

    private static void addClothingItem() {
        System.out.print("Item ID: ");
        String id = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Quantity: ");
        int qty = sc.nextInt();
        System.out.print("Price per unit: ");
        double price = sc.nextDouble();
        sc.nextLine();
        System.out.print("Supplier: ");
        String supplier = sc.nextLine();
        System.out.print("Sizes (comma separated): ");
        List<String> sizes = Arrays.asList(sc.nextLine().split(","));
        System.out.print("Colors (comma separated): ");
        List<String> colors = Arrays.asList(sc.nextLine().split(","));
        System.out.print("Has discount? (true/false): ");
        boolean discount = sc.nextBoolean();

        items.add(new ClothingItem(id, name, qty, price, supplier, sizes, colors, discount));
        System.out.println("Clothing item added!");
    }

    private static void addGroceryItem() {
        System.out.print("Item ID: ");
        String id = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Quantity: ");
        int qty = sc.nextInt();
        System.out.print("Price per unit: ");
        double price = sc.nextDouble();
        sc.nextLine();
        System.out.print("Supplier: ");
        String supplier = sc.nextLine();
        System.out.print("Expiration (yyyy-mm-dd): ");
        String dateStr = sc.nextLine();
        Date exp = java.sql.Date.valueOf(dateStr);

        items.add(new GroceryItem(id, name, qty, price, supplier, exp));
        System.out.println("Grocery item added!");
    }

    private static void addFurnitureItem() {
        System.out.print("Item ID: ");
        String id = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Quantity: ");
        int qty = sc.nextInt();
        System.out.print("Price per unit: ");
        double price = sc.nextDouble();
        System.out.print("Weight (kg): ");
        double weight = sc.nextDouble();
        sc.nextLine();
        System.out.print("Supplier: ");
        String supplier = sc.nextLine();

        items.add(new FurnitureItem(id, name, qty, price, supplier, weight));
        System.out.println("Furniture item added!");
    }

    private static void addPerishableItem() {
        System.out.print("Item ID: ");
        String id = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Quantity: ");
        int qty = sc.nextInt();
        System.out.print("Price per unit: ");
        double price = sc.nextDouble();
        sc.nextLine();
        System.out.print("Supplier: ");
        String supplier = sc.nextLine();
        System.out.print("Expiration (yyyy-mm-dd): ");
        String dateStr = sc.nextLine();
        Date exp = java.sql.Date.valueOf(dateStr);

        items.add(new PerishableItem(id, name, qty, price, supplier, exp));
        System.out.println("Perishable item added!");
    }

    private static void showReport() {
        System.out.println("\n--- INVENTORY REPORT ---");
        for (StockItem item : items) {
            item.generateStockReport();
            item.validateStock();
            System.out.println("Stock Value: $" + item.calculateStockValue() + "\n");
        }
    }
}