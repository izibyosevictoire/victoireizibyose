import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("===== System Menu =====");
            System.out.println("1. Motor Vehicle Insurance System");
            System.out.println("2. Stock Management System");
            System.out.println("3. Online Shopping System");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    new MotorVehicleInsuranceSystem().start();
                    break;
                case "2":
                    new StockManagementSystem().start();
                    break;
                case "3":
                    new OnlineShoppingSystem().start();
                    break;
                case "0":
                    running = false;
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println(); 
        }

        scanner.close();
    }
}
