import java.io.*;

public class HackAssemler {


    private Parser parser;
    private SymbolTable symbolTable;
    private Code code; 
    private String inputFile;
    private String outputFile;
    private BufferedWriter writer;
    


    // c'tor- Gets a String filename and initializing a parsed file and symbol map 
    public HackAssemler(String inputFile) throws IOException{

        this.inputFile = inputFile;
        this.outputFile = inputFile.substring(0, inputFile.lastIndexOf('.')) + ".hack";
        this.parser = new Parser(inputFile);
        this.symbolTable = new SymbolTable();
        this.code = new Code();
        this.writer = new BufferedWriter(new FileWriter(outputFile));
    }
        
        


    /* First pass:
     Reads the program lines, one by one
     focusing only on (label) declarations.
     Adds the found labels to the symbol table
     */ 
    public void firstPass() throws IOException {
        int lineNumber = 0;
        while (parser.hasMoreLines()) {
            parser.advance();
            if (parser.instructionType() == Instruction.L_INSTRUCTION) {
                String label = parser.symbol();
                symbolTable.addEntry(label, lineNumber);
            } else if (parser.instructionType() != Instruction.NONE) {
                lineNumber++;
            }
        }
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
    public void secondPass() throws IOException{

        parsedFile = new Parser(inputFile);
        String currentInst = parsedFile.getCurrentInstruction();
        int lineCount = 0;
        String binLine = "";

        while(parsedFile.hasMoreLines()){
            if (currentInst.indexOf('/') != -1 || emptyLine(currentInst) ) { // skip line (Empty or comment)
                currentInst = parsedFile.advance();
                continue;
            }
            // If the instruction is @symbol
            if (parsedFile.intstructionType() == instruction.A_INSTRUCTION){
                String symbol = parsedFile.sybmol();
                // If symbol is not in the symbol table, add it to the table
                if (containsLetters(symbol) && !symbolTable.contains(symbol)){
                    symbolTable.addEntry(symbol, lineCount);
                }
                // Translate the symbol into a dec value
                int decSymbol = Integer.parseInt(symbol);
                // Translates the symbol into its binary value (as a string!!!)
                binLine = decTo16(decSymbol);

                // Write binLine into outputFile
                writer.write(binLine);
                lineCount++;



            }

            // If the instruction is dest =comp ; jump,
            // Translates each of the three fields into its binary value
             else if(parsedFile.intstructionType() == instruction.C_INSTRUCTION){
                String binDest = code.dest(parsedFile.dest());
                String binComp = code.comp(parsedFile.comp());
                String binJump = code.jump(parsedFile.jump());

                // Assemble the binary values into a string of sixteen 0’s and 1’s
                binLine = ("111" + binComp + binDest + binJump);

                // Write binLine into outputFile
                writer.write(binLine);
                lineCount++;
            }

                
        }// end of while(hasmorelines), which means we parsed the whole file.

        writer.close();

    }// end of second Pass



    /**
     * Gets a string and returns if empty (empty or only white spaces)
     * @param s
     * @return boolean
     */
    public static boolean emptyLine(String s){
        for(int i = 0; i < s.length(); i ++)
            if(s.charAt(i) != ' ')
                return false;
        return true;
    }

    /**
     * Checks if the given string contains any letters.
     *
     * @param str the string to check
     * @return true if the string contains at least one letter, false otherwise
     */
    public static boolean containsLetters(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetter(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }


    /**
     * Takes a decimal number and returns its 16bit presentation
     * @param dec
     * @return String str (str.length() = 16)
     */
    public static String decTo16(int dec){
        int[] bin = new int[16];
        int log;
        while (dec > 0){
            log = (int)(Math.log(dec)/Math.log(2));//log dec on base 2
            bin[log] = 1;
            dec -= Math.pow(2,log);
        }
        String str = "";
        char c;
        for (int i = 15; i>=0; i--){
            c = '0';
            if(bin[i] == 1)
                c = '1';
            str = str + c;
        }
        return str;
    }
    
}
