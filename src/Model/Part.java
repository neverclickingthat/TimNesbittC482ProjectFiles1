
package Model;

/**
 * "Part" abstract class per assignment UML
 * @author Tim Nesbitt
 */

public abstract class Part {
    
    //TN: part ID:
    private int partID;
    //TN: part name:
    private String name;
    //TN: part price:
    private double price;
    //TN: inventory level of part:
    private int inStock;
    //TN: part inventory minumum:
    private int min;
    //TN: part inventory maximum:
    private int max;
    
    public Part() {
    }
    
    //TN: setter to set part name:
    public void setPartName(String name) {
        this.name = name;
    }
    
    //TN: getter to return part name:
    public String getPartName() {
        return name;
    }
    
    //TN: setter to set part price:
    public void setPartPrice(double price) {
        this.price = price;
    }
    
    //TN: getter to return part price:
    public double getPartPrice() {
        return price;
    }
    
    //TN: setter to set current inventory level for part:
    public void setPartInStock(int inStock) {
        this.inStock = inStock;
    }
    
    //TN: getter to return inventory level of part:
    public int getPartInStock() {
        return inStock;
    }
    
    //TN: setter to set minimum inventory level for part:
    public void setPartMin(int min) {
        this.min = min;
    }
    
    //TN: getter to return minimum inventory level for part:
    public int getPartMin() {
        return min;
    }

    //TN: setter to set maximum inventory level for part:
    public void setPartMax(int max) {
        this.max = max;
    }
    
    //TN: getter to return maximum inventory level for part:
    public int getPartMax() {
        return max;
    }
    
    //TN: setter to set part ID for part:
    public void setPartID(int partID) {
        this.partID = partID;
    }

    //TN: getter to return part ID for part:
    public int getPartID() {
        return partID;
    }
}

