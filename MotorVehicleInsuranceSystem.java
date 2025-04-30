import java.time.LocalDate;
import java.util.Scanner;

class Vehicle {
    private String type;
    private int modelYear;
    private double engineCapacity;
    private boolean safetyChecked;
    private boolean registrationValid;
    private boolean inspectionValid;
    private boolean commercial;

    public Vehicle(String type, int modelYear, double engineCapacity,
                   boolean safetyChecked, boolean registrationValid,
                   boolean inspectionValid, boolean commercial) {
        this.type = type;
        this.modelYear = modelYear;
        this.engineCapacity = engineCapacity;
        this.safetyChecked = safetyChecked;
        this.registrationValid = registrationValid;
        this.inspectionValid = inspectionValid;
        this.commercial = commercial;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getModelYear() { return modelYear; }
    public void setModelYear(int modelYear) { this.modelYear = modelYear; }

    public double getEngineCapacity() { return engineCapacity; }
    public void setEngineCapacity(double engineCapacity) { this.engineCapacity = engineCapacity; }

    public boolean isSafetyChecked() { return safetyChecked; }
    public void setSafetyChecked(boolean safetyChecked) { this.safetyChecked = safetyChecked; }

    public boolean isRegistrationValid() { return registrationValid; }
    public void setRegistrationValid(boolean registrationValid) { this.registrationValid = registrationValid; }

    public boolean isInspectionValid() { return inspectionValid; }
    public void setInspectionValid(boolean inspectionValid) { this.inspectionValid = inspectionValid; }

    public boolean isCommercial() { return commercial; }
    public void setCommercial(boolean commercial) { this.commercial = commercial; }
}

class Person {
    private String name;
    private int age;
    private boolean medicallyCleared;

    public Person(String name, int age, boolean medicallyCleared) {
        this.name = name;
        this.age = age;
        this.medicallyCleared = medicallyCleared;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public boolean isMedicallyCleared() { return medicallyCleared; }
    public void setMedicallyCleared(boolean medicallyCleared) { this.medicallyCleared = medicallyCleared; }
}

// Abstract Class: InsurancePolicy
abstract class InsurancePolicy {
    private String policyId;
    private Vehicle vehicle;
    private Person policyHolder;
    private double coverageAmount;
    private double premiumAmount;
    private LocalDate policyStartDate;
    private LocalDate policyEndDate;

    public InsurancePolicy(String policyId, Vehicle vehicle, Person policyHolder,
                           double coverageAmount, double premiumAmount,
                           LocalDate policyStartDate, LocalDate policyEndDate) {
        this.policyId = policyId;
        this.vehicle = vehicle;
        this.policyHolder = policyHolder;
        this.coverageAmount = coverageAmount;
        this.premiumAmount = premiumAmount;
        this.policyStartDate = policyStartDate;
        this.policyEndDate = policyEndDate;
    }

    public String getPolicyId() { return policyId; }
    public void setPolicyId(String policyId) { this.policyId = policyId; }
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
    public Person getPolicyHolder() { return policyHolder; }
    public void setPolicyHolder(Person policyHolder) { this.policyHolder = policyHolder; }
    public double getCoverageAmount() { return coverageAmount; }
    public void setCoverageAmount(double coverageAmount) { this.coverageAmount = coverageAmount; }
    public double getPremiumAmount() { return premiumAmount; }
    public void setPremiumAmount(double premiumAmount) { this.premiumAmount = premiumAmount; }
    public LocalDate getPolicyStartDate() { return policyStartDate; }
    public void setPolicyStartDate(LocalDate policyStartDate) { this.policyStartDate = policyStartDate; }
    public LocalDate getPolicyEndDate() { return policyEndDate; }
    public void setPolicyEndDate(LocalDate policyEndDate) { this.policyEndDate = policyEndDate; }

    public abstract void calculatePremium();
    public abstract boolean processClaim(double claimAmount);
    public abstract String generatePolicyReport();
    public abstract boolean validatePolicy();
}

// Concrete Class: ComprehensivePolicy
class ComprehensivePolicy extends InsurancePolicy {
    public ComprehensivePolicy(String policyId, Vehicle vehicle, Person policyHolder,
                               double coverageAmount, double premiumAmount,
                               LocalDate policyStartDate, LocalDate policyEndDate) {
        super(policyId, vehicle, policyHolder, coverageAmount, premiumAmount, policyStartDate, policyEndDate);
    }

    @Override
    public void calculatePremium() {
        int vehicleAge = LocalDate.now().getYear() - getVehicle().getModelYear();
        setPremiumAmount(getCoverageAmount() * 0.02 + vehicleAge * 50);
    }

    @Override
    public boolean processClaim(double claimAmount) {
        return claimAmount <= getCoverageAmount();
    }

    @Override
    public String generatePolicyReport() {
        return "Comprehensive Policy Report for Policy ID: " + getPolicyId();
    }

    @Override
    public boolean validatePolicy() {
        return getVehicle().getType().equalsIgnoreCase("Car") && getVehicle().getModelYear() > 2000;
    }
}

// Concrete Class: ThirdPartyPolicy
class ThirdPartyPolicy extends InsurancePolicy {
    public ThirdPartyPolicy(String policyId, Vehicle vehicle, Person policyHolder,
                            double coverageAmount, double premiumAmount,
                            LocalDate policyStartDate, LocalDate policyEndDate) {
        super(policyId, vehicle, policyHolder, coverageAmount, premiumAmount, policyStartDate, policyEndDate);
    }

    @Override
    public void calculatePremium() {
        setPremiumAmount(getVehicle().getEngineCapacity() * 0.01);
    }

    @Override
    public boolean processClaim(double claimAmount) {
        return claimAmount <= getCoverageAmount();
    }

    @Override
    public String generatePolicyReport() {
        return "Third Party Policy Report for Policy ID: " + getPolicyId();
    }

    @Override
    public boolean validatePolicy() {
        return true;
    }
}

// Concrete Class: CollisionPolicy
class CollisionPolicy extends InsurancePolicy {
    public CollisionPolicy(String policyId, Vehicle vehicle, Person policyHolder,
                           double coverageAmount, double premiumAmount,
                           LocalDate policyStartDate, LocalDate policyEndDate) {
        super(policyId, vehicle, policyHolder, coverageAmount, premiumAmount, policyStartDate, policyEndDate);
    }

    @Override
    public void calculatePremium() {
        setPremiumAmount(getCoverageAmount() * 0.03);
    }

    @Override
    public boolean processClaim(double claimAmount) {
        return claimAmount <= getCoverageAmount();
    }

    @Override
    public String generatePolicyReport() {
        return "Collision Policy Report for Policy ID: " + getPolicyId();
    }

    @Override
    public boolean validatePolicy() {
        return getVehicle().isSafetyChecked();
    }
}

// Concrete Class: LiabilityPolicy
class LiabilityPolicy extends InsurancePolicy {
    public LiabilityPolicy(String policyId, Vehicle vehicle, Person policyHolder,
                           double coverageAmount, double premiumAmount,
                           LocalDate policyStartDate, LocalDate policyEndDate) {
        super(policyId, vehicle, policyHolder, coverageAmount, premiumAmount, policyStartDate, policyEndDate);
    }

    @Override
    public void calculatePremium() {
        setPremiumAmount(getCoverageAmount() * 0.015);
    }

    @Override
    public boolean processClaim(double claimAmount) {
        return claimAmount <= getCoverageAmount();
    }

    @Override
    public String generatePolicyReport() {
        return "Liability Policy Report for Policy ID: " + getPolicyId();
    }

    @Override
    public boolean validatePolicy() {
        return getPolicyHolder().isMedicallyCleared();
    }
}

// Concrete Class: RoadsideAssistancePolicy
class RoadsideAssistancePolicy extends InsurancePolicy {
    public RoadsideAssistancePolicy(String policyId, Vehicle vehicle, Person policyHolder,
                                    double coverageAmount, double premiumAmount,
                                    LocalDate policyStartDate, LocalDate policyEndDate) {
        super(policyId, vehicle, policyHolder, coverageAmount, premiumAmount, policyStartDate, policyEndDate);
    }

    @Override
    public void calculatePremium() {
        setPremiumAmount(100 + (getVehicle().isCommercial() ? 50 : 0));
    }

    @Override
    public boolean processClaim(double claimAmount) {
        return claimAmount <= getCoverageAmount();
    }

    @Override
    public String generatePolicyReport() {
        return "Roadside Assistance Policy Report for Policy ID: " + getPolicyId();
    }

    @Override
    public boolean validatePolicy() {
        return getVehicle().isRegistrationValid() && getVehicle().isInspectionValid()
               && getPolicyHolder().getAge() >= 18;
    }

    public static void generateReport(InsurancePolicy[] policies) {
        double totalPremiums = 0;
        int comprehensiveCount = 0, thirdPartyCount = 0, collisionCount = 0, liabilityCount = 0, roadsideCount = 0;

        for (InsurancePolicy policy : policies) {
            totalPremiums += policy.getPremiumAmount();
            if (policy instanceof ComprehensivePolicy) comprehensiveCount++;
            else if (policy instanceof ThirdPartyPolicy) thirdPartyCount++;
            else if (policy instanceof CollisionPolicy) collisionCount++;
            else if (policy instanceof LiabilityPolicy) liabilityCount++;
            else if (policy instanceof RoadsideAssistancePolicy) roadsideCount++;
        }

        System.out.println("Insurance Policy Report:");
        System.out.println("Total Premiums Collected: $" + totalPremiums);
        System.out.println("Coverage Breakdown by Policy Type:");
        System.out.println("Comprehensive Policies: " + comprehensiveCount);
        System.out.println("Third Party Policies: " + thirdPartyCount);
        System.out.println("Collision Policies: " + collisionCount);
        System.out.println("Liability Policies: " + liabilityCount);
        System.out.println("Roadside Assistance Policies: " + roadsideCount);
    }
}

// Main Class: Outside all others
public class MotorVehicleInsuranceSystem {
    public static void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of policies to create: ");
        int numPolicies = scanner.nextInt();
        scanner.nextLine();

        InsurancePolicy[] policies = new InsurancePolicy[numPolicies];

        for (int i = 0; i < numPolicies; i++) {
            System.out.println("Enter details for Policy " + (i + 1) + ":");
            System.out.print("Policy Type (Comprehensive/ThirdParty/Collision/Liability/RoadsideAssistance): ");
            String policyType = scanner.nextLine();

            System.out.print("Policy ID: ");
            String policyId = scanner.nextLine();

            System.out.println("Enter Vehicle Details:");
            System.out.print("Type: ");
            String type = scanner.nextLine();
            System.out.print("Model Year: ");
            int modelYear = scanner.nextInt();
            System.out.print("Engine Capacity: ");
            double engineCapacity = scanner.nextDouble();
            System.out.print("Is Safety Checked (true/false): ");
            boolean safetyChecked = scanner.nextBoolean();
            System.out.print("Is Registration Valid (true/false): ");
            boolean registrationValid = scanner.nextBoolean();
            System.out.print("Is Inspection Valid (true/false): ");
            boolean inspectionValid = scanner.nextBoolean();
            System.out.print("Is Commercial (true/false): ");
            boolean commercial = scanner.nextBoolean();
            scanner.nextLine();

            Vehicle vehicle = new Vehicle(type, modelYear, engineCapacity, safetyChecked, registrationValid, inspectionValid, commercial);

            System.out.println("Enter Policy Holder Details:");
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Age: ");
            int age = scanner.nextInt();
            System.out.print("Is Medically Cleared (true/false): ");
            boolean medicallyCleared = scanner.nextBoolean();
            scanner.nextLine();

            Person policyHolder = new Person(name, age, medicallyCleared);

            System.out.print("Coverage Amount: ");
            double coverageAmount = scanner.nextDouble();
            scanner.nextLine();

            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusYears(1);

            switch (policyType.toLowerCase()) {
                case "comprehensive":
                    policies[i] = new ComprehensivePolicy(policyId, vehicle, policyHolder, coverageAmount, 0, startDate, endDate);
                    break;
                case "thirdparty":
                    policies[i] = new ThirdPartyPolicy(policyId, vehicle, policyHolder, coverageAmount, 0, startDate, endDate);
                    break;
                case "collision":
                    policies[i] = new CollisionPolicy(policyId, vehicle, policyHolder, coverageAmount, 0, startDate, endDate);
                    break;
                case "liability":
                    policies[i] = new LiabilityPolicy(policyId, vehicle, policyHolder, coverageAmount, 0, startDate, endDate);
                    break;
                case "roadsideassistance":
                    policies[i] = new RoadsideAssistancePolicy(policyId, vehicle, policyHolder, coverageAmount, 0, startDate, endDate);
                    break;
                default:
                    System.out.println("Invalid policy type. Try again.");
                    i--;
                    continue;
            }
        }

        for (InsurancePolicy policy : policies) {
            policy.calculatePremium();
            System.out.println(policy.generatePolicyReport());
            System.out.println("Premium: $" + policy.getPremiumAmount());
            System.out.println("Valid Policy: " + policy.validatePolicy());
            System.out.println();
        }

        RoadsideAssistancePolicy.generateReport(policies);
        scanner.close();
    }
}
