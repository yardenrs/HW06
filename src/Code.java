public class Code {

    
    // this class Deals only with C-instructions: dest = comp ; jump
    public Code() {}

    /*
     * Returns the binary representation of the parsed dest field (string)
     */
    public String dest(String dest){
        // Handle null case first
        if (dest == null) {
            return "000";
        }
        
        String res;
        switch (dest) {
            case "M":
                res = "001";
                break;
            case "D":
                res = "010";
                break;
            case "DM":
            case "MD":
                res = "011"; // For the bug
                break;
            case "A":
                res = "100";
                break;
            case "AM":
                res = "101";
                break;
            case "AD":
                res = "110";
                break;
            case "ADM":
            case "AMD":
                res = "111"; // For the bug
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dest);
        }
        return res;

    }

    /*
     * Returns the binary representation of the parsed comp field (string)
     */
    public String comp(String comp){
        String res = "";
        String a = "0"; // Initialize 'a' with a default value if needed

        switch (comp) {
            case "0":
                res = "101010";
                break;
            case "1":
                res = "111111";
                break;
            case "-1":
                res = "111010";
                break;
            case "D":
                res = "001100";
                break;
            case "A":
                res = "110000";
                break;
            case "!D":
                res = "001101";
                break;
            case "!A":
                res = "110001";
                break;
            case "-D":
                res = "001111";
                break;
            case "-A":
                res = "110011";
                break;
            case "D+1":
                res = "011111";
                break;
            case "A+1":
                res = "110111";
                break;
            case "D-1":
                res = "001110";
                break;
            case "A-1":
                res = "110010";
                break;
            case "D+A":
                res = "000010";
                break;
            case "D-A":
                res = "010011";
                break;
            case "A-D":
                res = "000111";
                break;
            case "D&A":
                res = "000000";
                break;
            case "D|A":
                res = "010101";
                break;
            case "M":
                res = "110000";
                a = "1";
                break;
            case "!M":
                res = "110001";
                a = "1";
                break;
            case "-M":
                res = "110011";
                a = "1";
                break;
            case "M+1":
                res = "110111";
                a = "1";
                break;
            case "M-1":
                res = "110010";
                a = "1";
                break;
            case "D+M":
                res = "000010";
                a = "1";
                break;
            case "D-M":
                res = "010011";
                a = "1";
                break;
            case "M-D":
                res = "000111";
                a = "1";
                break;
            case "D&M":
                res = "000000";
                a = "1";
                break;
            case "D|M":
                res = "010101";
                a = "1";
                break;
        }

        // Use 'res' and 'a' here as needed
         return (a + res);
        
        
    }

    public String jump(String jmp){
        String res;
        if (jmp == null) {
            res = "000";
        } else {
        switch (jmp) {
            case "JGT":
                res = "001";
                break;
            case "JEQ":
                res = "010";
                break;
            case "JGE":
                res = "011";
                break;
            case "JLT":
                res = "100";
                break;
            case "JNE":
                res = "101";
                break;
            case "JLE":
                res = "110";
                break;
            case "JMP":
                res = "111";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + jmp);
            }
        }
        return res;
    }


    
}
