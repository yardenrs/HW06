import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    private Map<String, Integer> symbolMap = new HashMap<>();
    private int freeAddress = 16; // First free memory address for variables



    // c'tor: creates and initializes a SymbolTable
    public SymbolTable(){

        for (int i = 0; i < 16; i++){
            symbolMap.put("R" + i, i); //R0,R1...,R15
        }

        symbolMap.put("SCREEN", 16384);
        symbolMap.put("KBD", 24576);
        symbolMap.put("SP", 0);
        symbolMap.put("LCL", 1);
        symbolMap.put("ARG", 2);
        symbolMap.put("THIS", 3);
        symbolMap.put("THAT", 4);
        //end of table

    }


    /*
     * Adds <symbol, address> to the table, if symbol table does not contain it already.
     */
    public void addEntry(String symbol, int adress){
        if(!contains(symbol)){
            symbolMap.put(symbol, adress);
        }   
    }


    /**  
     * @param symbol
     * @return true if symbol exists in table 
     */
    public boolean contains(String symbol){
        return symbolMap.containsKey(symbol);
    }


    /**
     * @param symbol
     * @return Return the address associated with symbol
     */
    public int getAddress(String symbol){
        return symbolMap.get(symbol);

    }

    public int addVariable(String symbol) {
        symbolMap.put(symbol, freeAddress);
        return freeAddress++;
    }
    
}
