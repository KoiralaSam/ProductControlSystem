/**
 * The Product class represents a product with a cost price, selling price, 
 * available stock amount, and tracks the running profit and cost.
 */
public class Product {
   /**
    * The cost price of a single unit of the product.
    */
   private final double costPrice;

   /**
    * The selling price of a single unit of the product.
    */
   private final double sellingPrice;

   /**
    * The quantity of the product currently available in inventory.
    */
   private int amount;

   /**
    * The cumulative profit earned across all product instances.
    */
   private static double runningProfit = 0;

   /**
    * The cumulative cost of inventory across all product instances.
    */
   private static double runningCost = 0;

   /**
    * Constructs a Product instance with the specified cost price and selling price.
    * Initializes the stock amount to 0.
    * 
    * @param costPrice the cost price of the product
    * @param sellingPrice the selling price of the product
    * @param amount the quantity of the product when the it is first listed in inventory
    */
   public Product(double costPrice, double sellingPrice) {
      this.costPrice = costPrice;
      this.sellingPrice = sellingPrice;
      amount = 0;
   }

   /**
    * Calculates the profit made from selling a specified amount of the product.
    * 
    * @param amount the number of units sold
    * @return the profit made from the sale
    */
   public double profit(int amount) {
      double profit = 0;
      profit = amount * this.sellingPrice - amount * this.costPrice;
      return profit;
   }

   /**
    * Handles the loss incurred from the deprication of certain product,
    * deducting the loss from running profit and running cost of inventory
    * 
    * @return the total loss incurred from the deprication of current stock
    */
   public double loss() {
      double loss =  (this.costPrice * this.amount);
      runningProfit -= loss;
      runningCost -= loss;
      amount = 0;
      return loss;
   }

   /**
    * Sells a specified amount of the product if sufficient stock is available.
    * Updates the cost of inventory by deducting the cost of the product sold and adds to
    * the running profit, if there has been any
    * 
    * @param amount the number of units to sell
    * @return the total revenue from the sale, or -1 if insufficient stock
    */
   public double sell(int amount) {
      if (this.amount >= amount) {
         this.amount -= amount;
         runningCost -= amount * this.costPrice;
         runningProfit += profit(amount);
         return amount * this.sellingPrice;
      } 
      else
         return -1;
   }

   /**
    * Purchases a specified amount of the product, adding to the stock and updating the running cost.
    * 
    * @param amount the number of units to purchase
    * @return the total cost of the purchase
    */
   public double buy(int amount) {
      double totalcost = amount * this.costPrice;
      this.amount += amount;
      runningCost += amount * this.costPrice;
      return totalcost;
   }

   /**
    * Retrieves the total profit accumulated across all products.
    * 
    * @return the total running profit
    */
   public static double getProfit(){
      return Product.runningProfit;
   }

   /**
    * Retrieves the total cost accumulated across all products.
    * 
    * @return the total running cost
    */
   public static double getCost(){
      return Product.runningCost;
   }

   /**
    * Resets the inventory by setting the cumulative profit and cost to 0.
    */
   public static void newInventory(){
      Product.runningCost = 0;
      Product.runningProfit = 0;
   }
}