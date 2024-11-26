import java.io.*;
import java.util.*;

public class TestFramework {
    public static void main(String[] args) {
        // Define test cases
        List<TestCase> testCases = Arrays.asList(
            new TestCase(
                "Test Case 1: Add New Products",
                "new apple 5.0 10.0\nnew banana 3.0 6.0\n*",
                "apple added to inventory\nbanana added to inventory"
            ),
            new TestCase(
                "Test Case 2: Add Duplicate Product",
                "new apple 5.0 10.0\nnew apple 6.0 12.0\n*",
                "apple added to inventory\nERROR: apple already in inventory"
            ),
            new TestCase(
                "Test Case 3: Buy Product",
                "new apple 5.0 10.0\nbuy apple 50\n*",
                "apple added to inventory\n50 units of apple added to inventory at a total cost of $250.00"
            ),
            new TestCase(
                "Test Case 4: Sell Product",
                "new apple 5.0 10.0\nbuy apple 50\nsell apple 30\n*",
                "apple added to inventory\n50 units of apple added to inventory at a total cost of $250.00\n30 units of apple sold at a total price of $300.00 for a profit of $150.00"
            ),
            new TestCase(
                "Test Case 5: Sell More Than Available",
                "new apple 5.0 10.0\nbuy apple 50\nsell apple 60\n*",
                "apple added to inventory\n50 units of apple added to inventory at a total cost of $250.00\nERROR: 60 exceeds units of apple in inventory"
            ),

            // Test Case 7
            new TestCase(
                "Test Case 7: Delete Non-Existent Product",
                "delete orange\n*",
                "ERROR: orange not in inventory"
            ),

            // Test Case 8
            new TestCase(
                "Test Case 8: Buy for a Non-Existent Product",
                "buy orange 10\n*",
                "ERROR: orange not in inventory"
            ),

            // Test Case 9
            new TestCase(
                "Test Case 9: Sell Non-Existent Product",
                "sell orange 5\n*",
                "ERROR: orange not in inventory"
            ),

            // Test Case 10
            new TestCase(
                "Test Case 10: Report Without Any Transactions",
                "report\n*",
                "Total cost: $0.00, Total profit: $0.00"
            ),

            // Test Case 11
            new TestCase(
                "Test Case 11: Combine Buy, Sell, Delete, and Report",
                "new apple 2.0 5.0\nnew banana 1.0 2.0\nbuy apple 20\nbuy banana 50\nsell apple 15\ndelete banana\nreport\n*",
                "apple added to inventory\n" +
                "banana added to inventory\n" +
                "20 units of apple added to inventory at a total cost of $40.00\n" +
                "50 units of banana added to inventory at a total cost of $50.00\n" +
                "15 units of apple sold at a total price of $75.00 for a profit of $45.00\n" +
                "banana removed from inventory for a total loss of $50.00\n" +
                "Total cost: $10.00, Total profit: $-5.00"
            ),

            // Test Case 12
            new TestCase(
                "Test Case 12: Sell All Inventory",
                "new apple 2.0 5.0\nbuy apple 50\nsell apple 50\nreport\n*",
                "apple added to inventory\n" +
                "50 units of apple added to inventory at a total cost of $100.00\n" +
                "50 units of apple sold at a total price of $250.00 for a profit of $150.00\n" +
                "Total cost: $0.00, Total profit: $150.00"
            ),

            // Test Case 13
            new TestCase(
                "Test Case 13: Edge Case – Buy and Delete Without Selling",
                "new apple 3.0 7.0\nbuy apple 30\ndelete apple\nreport\n*",
                "apple added to inventory\n" +
                "30 units of apple added to inventory at a total cost of $90.00\n" +
                "apple removed from inventory for a total loss of $90.00\n" +
                "Total cost: $0.00, Total profit: $-90.00"
            ),

            // Test Case 14
            new TestCase(
                "Test Case 14: Multiple Products with Complex Transactions",
                "new apple 1.0 2.0\nnew banana 2.0 4.0\nbuy apple 100\nbuy banana 50\nsell apple 75\nsell banana 20\ndelete apple\nreport\n*",
                "apple added to inventory\n" +
                "banana added to inventory\n" +
                "100 units of apple added to inventory at a total cost of $100.00\n" +
                "50 units of banana added to inventory at a total cost of $100.00\n" +
                "75 units of apple sold at a total price of $150.00 for a profit of $75.00\n" +
                "20 units of banana sold at a total price of $80.00 for a profit of $40.00\n" +
                "apple removed from inventory for a total loss of $25.00\n" +
                "Total cost: $60.00, Total profit: $90.00"
            ),

            // Test Case 15
            new TestCase(
                "Test Case 15: Mixed Invalid Commands",
                "new apple 3.0 5.0\nsell orange 10\nbuy orange 5\ndelete orange\nreport\n*",
                "apple added to inventory\n" +
                "ERROR: orange not in inventory\n" +
                "ERROR: orange not in inventory\n" +
                "ERROR: orange not in inventory\n" +
                "Total cost: $0.00, Total profit: $0.00"
            ),

            new TestCase(
                "Test Case: All Transactions in a Single Test Case (Fully Corrected)",
                "new apple 1.0 2.0\nnew banana 2.0 3.0\nnew orange 1.5 2.5\nnew apple 1.0 2.0\nbuy apple 50\nbuy banana 30\nsell apple 20\nsell banana 40\ndelete orange\nsell apple 30\nreport\n*",
                "apple added to inventory\n" +
                "banana added to inventory\n" +
                "orange added to inventory\n" +
                "ERROR: apple already in inventory\n" +
                "50 units of apple added to inventory at a total cost of $50.00\n" +
                "30 units of banana added to inventory at a total cost of $60.00\n" +
                "20 units of apple sold at a total price of $40.00 for a profit of $20.00\n" +
                "ERROR: 40 exceeds units of banana in inventory\n" +
                "orange removed from inventory for a total loss of $0.00\n" +
                "30 units of apple sold at a total price of $60.00 for a profit of $30.00\n" +
                "Total cost: $60.00, Total profit: $50.00"
            ),

            new TestCase(
            "Edge Case 1: Attempting to Sell More Than Available Inventory",
            "new apple 1.0 2.0\nbuy apple 10\nsell apple 15\nreport\n*",
            "apple added to inventory\n" +
            "10 units of apple added to inventory at a total cost of $10.00\n" +
            "ERROR: 15 exceeds units of apple in inventory\n" +
            "Total cost: $10.00, Total profit: $0.00"
        ),

        // Edge Case 2: Attempting to Delete a Product Not in Inventory
        new TestCase(
            "Edge Case 2: Attempting to Delete a Product Not in Inventory",
            "new apple 1.0 2.0\ndelete banana\nreport\n*",
            "apple added to inventory\n" +
            "ERROR: banana not in inventory\n" +
            "Total cost: $0.00, Total profit: $0.00"
        ),

        // Edge Case 3: Deleting All Items
        new TestCase(
            "Edge Case 3: Deleting All Items",
            "new apple 1.0 2.0\nnew banana 2.0 3.0\nbuy apple 10\nbuy banana 5\ndelete apple\ndelete banana\nreport\n*",
            "apple added to inventory\n" +
            "banana added to inventory\n" +
            "10 units of apple added to inventory at a total cost of $10.00\n" +
            "5 units of banana added to inventory at a total cost of $10.00\n" +
            "apple removed from inventory for a total loss of $10.00\n" +
            "banana removed from inventory for a total loss of $10.00\n" +
            "Total cost: $0.00, Total profit: $-20.00"
        ),

        // Edge Case 4: Selling and Reporting Without Any Inventory
        new TestCase(
            "Edge Case 4: Selling and Reporting Without Any Inventory",
            "new apple 1.0 2.0\nsell apple 5\nreport\n*",
            "apple added to inventory\n" +
            "ERROR: apple not in inventory\n" +
            "Total cost: $0.00, Total profit: $0.00"
        ),

        // Edge Case 5: Adding Multiple Items and Generating Profit from Multiple Sales
        new TestCase(
            "Edge Case 5: Adding Multiple Items and Generating Profit from Multiple Sales",
            "new apple 1.0 2.0\nnew banana 2.0 3.0\nbuy apple 10\nbuy banana 5\nsell apple 5\nsell banana 5\nreport\n*",
            "apple added to inventory\n" +
            "banana added to inventory\n" +
            "10 units of apple added to inventory at a total cost of $10.00\n" +
            "5 units of banana added to inventory at a total cost of $10.00\n" +
            "5 units of apple sold at a total price of $10.00 for a profit of $5.00\n" +
            "5 units of banana sold at a total price of $15.00 for a profit of $5.00\n" +
            "Total cost: $10.00, Total profit: $10.00"
        ),

        // Edge Case 6: Selling More Than Purchased and Reporting
        new TestCase(
            "Edge Case 6: Selling More Than Purchased and Reporting",
            "new apple 1.0 2.0\nbuy apple 10\nsell apple 20\nreport\n*",
            "apple added to inventory\n" +
            "10 units of apple added to inventory at a total cost of $10.00\n" +
            "ERROR: 20 exceeds units of apple in inventory\n" +
            "Total cost: $10.00, Total profit: $0.00"
        ),

        // Edge Case 7: Buying Multiple Units, Selling Part of Them, and Reporting
        new TestCase(
            "Edge Case 7: Buying Multiple Units, Selling Part of Them, and Reporting",
            "new apple 1.0 2.0\nbuy apple 10\nbuy apple 5\nsell apple 8\nreport\n*",
            "apple added to inventory\n" +
            "10 units of apple added to inventory at a total cost of $10.00\n" +
            "5 units of apple added to inventory at a total cost of $5.00\n" +
            "8 units of apple sold at a total price of $16.00 for a profit of $8.00\n" +
            "Total cost: $15.00, Total profit: $8.00"
        ),

        // Edge Case 8: Reporting Without Any Transactions
        new TestCase(
            "Edge Case 8: Reporting Without Any Transactions",
            "report\n*",
            "Total cost: $0.00, Total profit: $0.00"
        ),

        // Edge Case 9: Handling Floating Point Precision in Cost and Profit
        new TestCase(
            "Edge Case 9: Handling Floating Point Precision in Cost and Profit",
            "new apple 1.99 3.49\nbuy apple 100\nsell apple 50\nreport\n*",
            "apple added to inventory\n" +
            "100 units of apple added to inventory at a total cost of $199.00\n" +
            "50 units of apple sold at a total price of $174.50 for a profit of $75.50\n" +
            "Total cost: $199.00, Total profit: $75.50"
        ),

        // Edge Case 10: No Profit (Cost Equals Revenue)
        new TestCase(
            "Edge Case 10: No Profit (Cost Equals Revenue)",
            "new apple 5.0 5.0\nbuy apple 10\nsell apple 10\nreport\n*",
            "apple added to inventory\n" +
            "10 units of apple added to inventory at a total cost of $50.00\n" +
            "10 units of apple sold at a total price of $50.00 for a profit of $0.00\n" +
            "Total cost: $50.00, Total profit: $0.00"
        )
);


        // Run all test cases
        for (TestCase testCase : testCases) {
            System.out.println("Running: " + testCase.getName());
            runTestCase(testCase);
        }
    }

    private static void runTestCase(TestCase testCase) {
        try {
            // Step 1: Create the input file
            File inputFile = new File("test_input.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
                writer.write(testCase.getInputData());
            }

            // Step 2: Run the ProductControlSystem
            ProductControlSystem pcs = new ProductControlSystem();
            String output = pcs.processTransactions("test_input.txt");

            // Step 3: Compare output
            if (output.equals(testCase.getExpectedOutput())) {
                System.out.println("Result: PASS");
            } else {
                System.out.println("Result: FAIL");
                System.out.println("Expected Output:");
                System.out.println(testCase.getExpectedOutput());
                System.out.println("Actual Output:");
                System.out.println(output);
            }

            // Cleanup
            inputFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Helper class to define test cases
class TestCase {
    private String name;
    private String inputData;
    private String expectedOutput;

    public TestCase(String name, String inputData, String expectedOutput) {
        this.name = name;
        this.inputData = inputData;
        this.expectedOutput = expectedOutput;
    }

    public String getName() {
        return name;
    }

    public String getInputData() {
        return inputData;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }
}
