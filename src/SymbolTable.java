import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    public Map<String, Integer> SymbolMap = new HashMap<>();

    // c'tor: creates and initializes a SymbolTable
    public SymbolTable(){

        for (int i = 0; i < 16; i++){
            SymbolMap.put("R" + i, i); //R0,R1...,R15
        }

        SymbolMap.put("SCREEN", 16384);
        SymbolMap.put("KBD", 24576);
        SymbolMap.put("SP", 0);
        SymbolMap.put("LCL", 1);
        SymbolMap.put("ARG", 2);
        SymbolMap.put("THIS", 3);
        SymbolMap.put("THAT", 4);
        //end of table

    }


    /*
     * Adds <symbol, address> to the table
     */
    public void addEntry(String symbol, int adress){

    }

    /*
     * Checks if symbol exists in the table
     */
    public boolean contains(String symbol){
        return false;

    }

    /*
     * Returns the address associated with symbol
     */
    public int getAddress(String symbol){
        return -1;

    }
    
}
