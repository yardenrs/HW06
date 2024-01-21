import java.io.*;

public class HackAssemler {


    private Parser parsedFile;
    private SymbolTable symbolTable;

    // c'tor- Gets a String filename and initializing a parsed file and symbol map 
    public HackAssemler(String fileName) throws IOException{

        parsedFile = new Parser(fileName);
        symbolTable = new SymbolTable();

    }
        
        


    /* First pass:
     Reads the program lines, one by one
     focusing only on (label) declarations.
     Adds the found labels to the symbol table
     */ 
    public void firstPass(){

    }


    /* second pass: 
     * (starts again from the beginning of the file)
        While there are more lines to process:
        Gets the next instruction, and parses it
        If the instruction is @ symbol
        If symbol is not in the symbol table, adds it to the table
        Translates the symbol into its binary value
        If the instruction is dest =comp ; jump
        Translates each of the three fields into its binary value
        Assembles the binary values into a string of sixteen 0’s and 1’s
        Writes the string to the output file.
     */
    public void secondPass(){


    }



    
}
