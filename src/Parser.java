import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Parser{
    private BufferedReader bufferedReader;
    private String currentInstruction;
        
    // c'tor
        public Parser(String fileName) throws IOException {
        bufferedReader = new BufferedReader(new FileReader(fileName));
        currentInstruction = null;
    }

    // return if there are more lines in the input
    public boolean hasMoreLines() throws IOException {
        return bufferedReader.ready();
    }


    /*
     * Get the next instruction and makes it the current instruction (string)
     */
    public void advance() throws IOException {
        do {
            currentInstruction = bufferedReader.readLine().trim();
        } while (currentInstruction != null && (currentInstruction.isEmpty() || currentInstruction.startsWith("//")));
    }


    
    /** Return the current instruction type, as a constant:
        A_INSTRUCTION for @ xxx, where xxx is either a decimal number or a symbol
        C_INSTRUCTION for dest = comp ; jump
        L_INSTRUCTION for (label)
     * @return the instruction type (as a constant?);
     * @throws IOException
     */
    public Instruction instructionType() {
        if (currentInstruction == null) {
            return Instruction.NONE;
        } else if (currentInstruction.startsWith("@")) {
            return Instruction.A_INSTRUCTION;
        } else if (currentInstruction.startsWith("(") && currentInstruction.endsWith(")")) {
            return Instruction.L_INSTRUCTION;
        } else {
            return Instruction.C_INSTRUCTION;
        }
    }

    /** Used only if the current instruction is
        '@symbol' or (symbol)
     * @return Returns the instruction’s symbol (string)
     * @throws IOException 
     */
    public String symbol() throws IOException {
        if (instructionType() == Instruction.A_INSTRUCTION) {
            return currentInstruction.substring(1);
        } else if (instructionType() == Instruction.L_INSTRUCTION) {
            return currentInstruction.substring(1, currentInstruction.length() - 1);
        }
        return null;
    }

    
    /** Used only if the current instruction is
        dest =comp ; jump
     * @return Return the instruction’s dest field
     */
    public String dest() {
        if (instructionType() == Instruction.C_INSTRUCTION) {
            int equalsIndex = currentInstruction.indexOf('=');
            return equalsIndex != -1 ? currentInstruction.substring(0, equalsIndex) : "null";
        }
        return null;
    }

    /** Used only if the current instruction is
        dest =comp ; jump
     * @return Return the instruction’s comp field
     */
    public String comp() {
        if (instructionType() == Instruction.C_INSTRUCTION) {
            int equalsIndex = currentInstruction.indexOf('=');
            int semiColonIndex = currentInstruction.indexOf(';');
            if (semiColonIndex != -1) {
                return currentInstruction.substring(equalsIndex + 1, semiColonIndex);
            } else if (equalsIndex != -1) {
                return currentInstruction.substring(equalsIndex + 1);
            }
        }
        return null;
    }


    /** Used only if the current instruction is
        dest =comp ; jump
     * @return Return the instruction’s jump field
     */
    public String jump() {
        if (instructionType() == Instruction.C_INSTRUCTION) {
            int semiColonIndex = currentInstruction.indexOf(';');
            return semiColonIndex != -1 ? currentInstruction.substring(semiColonIndex + 1) : "null";
        }
        return null;
    }

    
    public void reset() throws IOException {
        bufferedReader.reset();
    }

    public String getCurrentInstruction() {
        return currentInstruction;
    }
    
}
