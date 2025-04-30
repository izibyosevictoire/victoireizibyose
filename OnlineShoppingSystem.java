import java.util.*;
import java.time.LocalDate;

abstract class ShoppingItem {
    protected String itemId, itemName, itemDescription;
    protected double price;
    protected int stockAvailable;

    public ShoppingItem(String itemId, String itemName, String itemDescription, double price, int stockAvailable) {
        if (price <= 0) throw new IllegalArgumentException("Price must be positive.");
        if (stockAvailable < 0) throw new IllegalArgumentException("Stock cannot be negative.");

        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = price;
        this.stockAvailable = stockAvailable;
    }

    public abstract void updateStock(int quantity);
    public abstract void addToCart(Customer customer);
    public abstract void generateInvoice(Customer customer);
    public abstract void validateItem();
}

class ElectronicsItem extends ShoppingItem {
    private int warrantyMonths;

    public ElectronicsItem(String itemId, String itemName, String description, double price, int stock, int warrantyMonths) {
        super(itemId, itemName, description, price, stock);
        if (warrantyMonths <= 0) throw new IllegalArgumentException("Warranty must be positive.");
        this.warrantyMonths = warrantyMonths;
    }

    public void updateStock(int quantity) {
        if (stockAvailable >= quantity) stockAvailable -= quantity;
        else System.out.println("Not enough stock.");
    }

    public void addToCart(Customer customer) {
        if (stockAvailable > 0) {
            customer.getCart().addItem(this);
            updateStock(1);
        } else {
            System.out.println("Item out of stock.");
        }
    }

    public void generateInvoice(Customer customer) {
        System.out.println("Invoice for: " + customer.getCustomerName());
        System.out.println("Item: " + itemName + " | Warranty: " + warrantyMonths + " months | Price: " + price);
    }

    public void validateItem() {
        if (stockAvailable <= 0) System.out.println(itemName + " is not available.");
    }
}

class Customer {
    private String customerId, customerName, email, address, phone;
    private ShoppingCart cart;

    public Customer(String customerId, String customerName, String email, String address, String phone) {
        if (customerName == null || customerName.isEmpty()) throw new IllegalArgumentException("Customer name cannot be empty.");
        if (!email.contains("@") || !email.contains(".")) throw new IllegalArgumentException("Invalid email.");
        if (phone.length() < 9) throw new IllegalArgumentException("Invalid phone number.");
        if (address == null || address.length() < 3) throw new IllegalArgumentException("Address is too short.");

        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.cart = new ShoppingCart(this);
    }

    public String getCustomerName() {
        return customerName;
    }

    public ShoppingCart getCart() {
        return cart;
    }
}

class ShoppingCart {
    private String cartId = UUID.randomUUID().toString();
    private List<ShoppingItem> cartItems = new ArrayList<>();
    private double totalPrice = 0.0;
    private Customer customer;

    public ShoppingCart(Customer customer) {
        this.customer = customer;
    }

    public void addItem(ShoppingItem item) {
        if (item != null) {
            cartItems.add(item);
            totalPrice += item.price;
        }
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void viewCart() {
        System.out.println("Cart for " + customer.getCustomerName());
        for (ShoppingItem item : cartItems) {
            System.out.println("- " + item.itemName + ": " + item.price);
        }
        System.out.println("Total: " + totalPrice);
    }
}

class Payment {
    private String paymentId = UUID.randomUUID().toString();
    private String paymentMethod;
    private double amountPaid;
    private LocalDate transactionDate = LocalDate.now();

    public Payment(String method, double amount) {
        if (!(method.equalsIgnoreCase("Credit Card") || method.equalsIgnoreCase("PayPal"))) {
            throw new IllegalArgumentException("Invalid payment method.");
        }
        if (amount <= 0) throw new IllegalArgumentException("Amount must be greater than 0.");

        this.paymentMethod = method;
        this.amountPaid = amount;
    }

    public void printReceipt() {
        System.out.println("\nPayment ID: " + paymentId);
        System.out.println("Method: " + paymentMethod);
        System.out.println("Amount Paid: " + amountPaid);
        System.out.println("Date: " + transactionDate);
    }
}

public class OnlineShoppingSystem {
    public static void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Welcome to the Online Shopping System ---");

        Customer customer = createCustomer(scanner);
        ElectronicsItem electronics = createElectronicsItem(scanner);

        electronics.addToCart(customer);
        customer.getCart().viewCart();

        Payment payment = createPayment(scanner, customer.getCart().getTotalPrice());
        payment.printReceipt();

        electronics.generateInvoice(customer);
        scanner.close();
    }

    public static Customer createCustomer(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Customer ID: ");
                String id = scanner.nextLine();
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("Address: ");
                String address = scanner.nextLine();
                System.out.print("Phone: ");
                String phone = scanner.nextLine();
                return new Customer(id, name, email, address, phone);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static ElectronicsItem createElectronicsItem(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Item ID: ");
                String id = scanner.nextLine();
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Description: ");
                String desc = scanner.nextLine();
                System.out.print("Price: ");
                double price = Double.parseDouble(scanner.nextLine());
                System.out.print("Stock Available: ");
                int stock = Integer.parseInt(scanner.nextLine());
                System.out.print("Warranty (months): ");
                int warranty = Integer.parseInt(scanner.nextLine());
                return new ElectronicsItem(id, name, desc, price, stock, warranty);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static Payment createPayment(Scanner scanner, double totalAmount) {
        while (true) {
            try {
                System.out.print("\nEnter payment method (Credit Card / PayPal): ");
                String method = scanner.nextLine();
                return new Payment(method, totalAmount);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
