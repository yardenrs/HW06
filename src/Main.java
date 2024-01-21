//package org.example;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

class Main {
    public static InputStream file = null; //setting a file and a map we can reach to from everywhere
    public static Reader reader = null;
    public static BufferedReader bufferReader = null;
    public static Map<String, Integer> SymbolMap = new HashMap<>();

    /**
     * gets a String filename and initializing the file and the map
     * @param fileName
     * @throws IOException
     */
    public static void init (String fileName) throws IOException {
        try {
            file =new FileInputStream(fileName);
            reader =new InputStreamReader(file);
            bufferReader =new BufferedReader(reader);
        }
        catch (IOException e){
            // No errors in the file
        }
        //init table:
        for (int i = 0; i < 16; i++)
            SymbolMap.put("R" + i, i); //R0,R1...,R15

        SymbolMap.put("SCREEN", 16384);
        SymbolMap.put("KBD", 24576);
        SymbolMap.put("SP", 0);
        SymbolMap.put("LCL", 1);
        SymbolMap.put("ARG", 2);
        SymbolMap.put("THIS", 3);
        SymbolMap.put("THAT", 4);
        //end of table


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

    /**
     * Gets a syntax instruction and returns the instruction bit presentation
     * @param line
     * @return String str (16bit String)
     */
    public static String syntax(String line) {
        String str = "111";
        String comp;
        String dest;
        String jmp;
        String a = "0";
        int index = line.indexOf('=');
        int index2 = line.indexOf (';');
        int start = 0; //The first index in the string that is not white space
        for (int i = 0; i < line.length(); i++)
            if (line.charAt(i) != ' ') {
                start = i;
                break;
            }
        if (index != -1) {              //line has '=' mark
            dest = line.substring(start, index);
            if (index2 != -1) {         //line has ';' mark
                comp = line.substring(index + 1, index2);
                jmp = line.substring(index2 + 1);
            }
            else {
                comp = line.substring(index + 1);
                jmp = null;
            }
        }
        else {
            dest = null;
            comp = line.substring(start, index2);
            jmp = line.substring(index2 + 1);
        }


        switch (comp) {
            case "0" -> comp = "101010";
            case "1" -> comp = "111111";
            case "-1" -> comp = "111010";
            case "D" -> comp = "001100";
            case "A" -> comp = "110000";
            case "!D" -> comp = "001101";
            case "!A" -> comp = "110001";
            case "-D" -> comp = "001111";
            case "-A" -> comp = "110011";
            case "D+1" -> comp = "011111";
            case "A+1" -> comp = "110111";
            case "D-1" -> comp = "001110";
            case "A-1" -> comp = "110010";
            case "D+A" -> comp = "000010";
            case "D-A" -> comp = "010011";
            case "A-D" -> comp = "000111";
            case "D&A" -> comp = "000000";
            case "D|A" -> comp = "010101";
            case "M" -> {
                comp = "110000";
                a = "1";
            }
            case "!M" -> {
                comp = "110001";
                a = "1";
            }
            case "-M" -> {
                comp = "110011";
                a = "1";
            }
            case "M+1" -> {
                comp = "110111";
                a = "1";
            }
            case "M-1" -> {
                comp = "110010";
                a = "1";
            }
            case "D+M" -> {
                comp = "000010";
                a = "1";
            }
            case "D-M" -> {
                comp = "010011";
                a = "1";
            }
            case "M-D" -> {
                comp = "000111";
                a = "1";
            }
            case "D&M" -> {
                comp = "000000";
                a = "1";
            }
            case "D|M" -> {
                comp = "010101";
                a = "1";
            }
        }

        dest = switch (dest) {
            case "M" -> "001";
            case "D" -> "010";
            case "DM", "MD" -> "011";//For the bug
            case "A" -> "100";
            case "AM" -> "101";
            case "AD" -> "110";
            case "ADM", "AMD" -> "111";// For the bug
            case null -> "000";
            default -> throw new IllegalStateException("Unexpected value: " + dest);
        };

        jmp = switch (jmp) {
            case null -> "000";
            case "JGT" -> "001";
            case "JEQ" -> "010";
            case "JGE" -> "011";
            case "JLT" -> "100";
            case "JNE" -> "101";
            case "JLE" -> "110";
            case "JMP" -> "111";
            default -> throw new IllegalStateException("Unexpected value: " + jmp);
        };

        return str + a + comp + dest + jmp;
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

    public static void main(String[] args) throws IOException {
        String fileName = args[0]; // filename = first argument
        init(fileName);
        String line;
        String str = "";
        int lineCount = 0;
        int index;
        int varSymbols = 16; //The variables symbols start from 16 (as we've seen in class)
        line = bufferReader.readLine();
        while (line != null){ //first parse
            if (line.indexOf('/') != -1 || emptyLine(line) ) { // skip line (Empty or comment)
                line = bufferReader.readLine();
                continue;
            }
            index = line.indexOf('(');
            if(index != -1){        // There's () in the line
                line = line.substring(index+1, line.length()-1);
                SymbolMap.put(line,lineCount); // We will insert the new var to the Map
            }
            line = bufferReader.readLine();
            lineCount++;
        }// end of first pass


        lineCount = 0; //resetting the buffer and loading the file again
        bufferReader.mark(0);
        bufferReader.reset();
        file =new FileInputStream(fileName);
        reader =new InputStreamReader(file);
        bufferReader =new BufferedReader(reader);

        line = bufferReader.readLine();
        while (line != null){
            if (line.indexOf('/') != -1 || emptyLine(line) ){ // skip line
                line = bufferReader.readLine();
                continue;
            }
            index = line.indexOf('@'); //if line has symbol handling
            if(index != -1){
                line = line.substring(index+1); // line = line - @
                if (!SymbolMap.containsKey(line)){
                    try {
                        int mem = Integer.parseInt(line); //converting the string to int if possible
                        str = str + decTo16(mem) +"/n";
                    }
                    catch (Exception e){
                        SymbolMap.put(line,varSymbols); //Inserting new Variable to the HashMap
                        str = str + decTo16(varSymbols++) + "/n";
                    }
                }
                else {
                    str = str + decTo16(SymbolMap.get(line)) + "/n";
                }
            }
            else {
                index = line.indexOf("("); // if line has no '()'
                if (index == -1)
                    str = str + syntax(line) + "/n"; //concatenating syntax var
            }
            lineCount++;
            line = bufferReader.readLine();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("prog.hack")); //Writing str to file
        writer.write(str);
        writer.close();
        System.out.println(str); //For tests, should be deleted
    }
}
