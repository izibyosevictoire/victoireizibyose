# Use the official OpenJDK 17 image from Docker Hub
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy all Java files into the container
COPY . /app

# Compile the Java files (adjust filenames if needed)
# Compile all Java files, including Main.java
RUN javac Main.java MotorVehicleInsuranceSystem.java StockManagementSystem.java OnlineShoppingSystem.java

# Run the main Java class
CMD ["java", "Main"]


