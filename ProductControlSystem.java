import java.util.*;
import java.io.*;

/**
 * The ProductControlSystem class manages a collection of products in inventory 
 * and processes various transactions such as creating, deleting, buying, and selling products.
 */
public class ProductControlSystem {

   /**
    * A map storing product names as keys and corresponding Product objects as values.
    */
   private Map<String, Product> inventory;

   /**
    * A buffer used to store transaction logs and system messages.
    */
   private StringBuffer mainBuffer;

   public ProductControlSystem() {
      inventory = new HashMap<String, Product>();
      mainBuffer = new StringBuffer();
      Product.newInventory();
   }

   public String processTransactions(String dataFile) {
      try {
         Scanner scan = new Scanner(new File(dataFile));
         String lineScan = scan.nextLine();
         while (!lineScan.equals("*")) {
            String[] inLine = lineScan.split(" ");
            String transCode = inLine[0];
            switch (transCode) {
               case "new":
               create(inLine);
               break;

            case "delete":
               delete(inLine);
               break;

            case "sell":
               sell(inLine);
               break;

            case "buy":
               buy(inLine);
               break;

            case "report":
               mainBuffer.append(
                     String.format("Total cost: $%.2f, Total profit: $%.2f\n", Product.getCost(), Product.getProfit()));
               break;
               
            }
            lineScan = scan.nextLine();
         }
         scan.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
      return this.mainBuffer.toString().trim();
   }

   public void create(String inLine[]){
      String name = inLine[1];
      String costPrice = inLine[2];
      String sellingPrice = inLine[3];
      Product newItem = this.inventory.put(name, new Product(Double.parseDouble(costPrice),
                     Double.parseDouble(sellingPrice)));
      if (!(newItem == null)) {
         this.mainBuffer.append(String.format("ERROR: %s already in inventory\n", name));
         this.inventory.put(name, newItem);
      }

      else {
         mainBuffer.append(String.format("%s added to inventory\n", name));
      }
   }

   public void delete(String inLine[]){
      String name = inLine[1];
      Product oldItem = this.inventory.remove(name);
      if (!(oldItem == null)) {
         this.mainBuffer.append(String.format("%s removed from inventory for a total loss of $%.2f\n", name,
               oldItem.loss()));
      }

      else {
         mainBuffer.append(String.format("ERROR: %s not in inventory\n", name));
      }
   }

   public void sell(String inLine[]){
      String name = inLine[1];
      String quantity = inLine[2];
      if (!this.inventory.containsKey(name)) {
         mainBuffer.append(String.format("ERROR: %s not in inventory\n", name));
      }

      else {
         double sales = this.inventory.get(inLine[1]).sell(Integer.parseInt(quantity));
         if (sales == -1) {
            mainBuffer.append(
                  String.format("ERROR: %s exceeds units of %s in inventory\n", quantity, name));
         }

         else {
            mainBuffer.append(String.format(
                  "%s units of %s sold at a total price of $%.2f for a profit of $%.2f\n",
                  quantity, name, sales,
                  inventory.get(name).profit(Integer.parseInt(quantity))));
         }
      }
   }

   public void buy(String inLine[]){
      String name = inLine[1];
      String quantity = inLine[2];
      if (!this.inventory.containsKey(name)) {
         mainBuffer.append(String.format("ERROR: %s not in inventory\n", name));
      } else {
         mainBuffer.append(
               String.format("%s units of %s added to inventory at a total cost of $%.2f\n",
               quantity, name, this.inventory.get(name).buy(Integer.parseInt(quantity))));
      }
   }
}