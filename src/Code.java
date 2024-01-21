public class Code {

    // this class Deals only with C-instructions: dest = comp ; jump
    /**
     * 
     */
    public Code(){

    }

    /*
     * Returns the binary representation of the parsed dest field (string)
     */
    public int dest(String instruction){
        return -1;
    }

    /*
     * Returns the binary representation of the parsed comp field (string)
     */
    public int comp(String instruction){
        return -1;
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
