import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Parser{
    private  InputStream inputFile;
    private  Reader reader;
    private  BufferedReader bufferReader;
    private String currentInstruction;
    //private instruction instructionType;
    
    
    // c'tor
    public Parser(String fileName) throws IOException{

        try {
            inputFile =new FileInputStream(fileName);
            reader =new InputStreamReader(inputFile);
            bufferReader =new BufferedReader(reader);
        }
        catch (IOException e){
            // No errors in the file
        }

        currentInstruction = advance();

    }

    // return if there are more lines in the input
    public boolean hasMoreLines(){

        return (bufferReader != null);
    }


    /*
     * Get the next instruction and makes it the current instruction (string)
     */
    public String advance() throws IOException {
        return bufferReader.readLine();
    }


    
    /** Return the current instruction type, as a constant:
        A_INSTRUCTION for @ xxx, where xxx is either a decimal number or a symbol
        C_INSTRUCTION for dest = comp ; jump
        L_INSTRUCTION for (label)
     * @return the instruction type (as a constant?);
     * @throws IOException
     */
    public instruction intstructionType() throws IOException{

        // skip empty lines until reaching a text line
        if (currentInstruction.indexOf('/') != -1 || emptyLine(currentInstruction) ){ // skip line
            currentInstruction = bufferReader.readLine();
        }
        // check if currentInstruction is an 'A_instruction'
        if(currentInstruction.indexOf('@') != -1){
            return instruction.A_INSTRUCTION;
        } 
        // check if currentInstruction is an 'L_instruction'
        else if(currentInstruction.indexOf('(') != -1){
            return instruction.L_INSTRUCTION;
        }
        // if currentInstruction is nither an empty line, nor an A_instruction, or an L_instruction,
        // it is a C_insturction 
        else {
            return instruction.C_INSTRUCTION;
        }
    } 

    /** Used only if the current instruction is
        '@symbol' or (symbol)
     * @return Returns the instruction’s symbol (string)
     */
    public String sybmol(){ 
        int index = currentInstruction.indexOf('@');
        return  currentInstruction.substring(index+1);
    }

    
    /** Used only if the current instruction is
        dest =comp ; jump
     * @return Return the instruction’s dest field
     */
    public String dest(){
        int equalsIndex = currentInstruction.indexOf('=');
        int start = 0; // start is the first index in the string that is not white space
        for (int i = 0; i < currentInstruction.length(); i++)
            if (currentInstruction.charAt(i) != ' ') {
                start = i;
                break;
            }
        return currentInstruction.substring(start, equalsIndex);

    }

    /** Used only if the current instruction is
        dest =comp ; jump
     * @return Return the instruction’s comp field
     */
    public String comp(){
        int equalsIndex = currentInstruction.indexOf('=');
        int semiCommaIndex = currentInstruction.indexOf (';');
        return currentInstruction.substring(equalsIndex + 1, semiCommaIndex);
    }


    /** Used only if the current instruction is
        dest =comp ; jump
     * @return Return the instruction’s jump field
     */
    public String jump(){
        int semiCommaIndex = currentInstruction.indexOf (';');
        return currentInstruction.substring(semiCommaIndex + 1);
    }


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
    
}
