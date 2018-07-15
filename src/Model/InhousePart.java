
package Model;

/**
 * "InhousePart" class per assignment UML
 * @author Tim Nesbitt
 */

public class InhousePart extends Part {

    //TN: in-house part machine ID:
    private int machineID;
        
    //TN: setter to set machine ID for in-house part:
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    //TN: getter to return machine ID for in-house part:
    public int getMachineID() {
        return machineID;
    }
    
}

