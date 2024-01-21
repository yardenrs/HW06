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
    
    
    public Parser(String fileName) throws IOException{

        try {
            inputFile =new FileInputStream(fileName);
            reader =new InputStreamReader(inputFile);
            bufferReader =new BufferedReader(reader);
        }
        catch (IOException e){
            // No errors in the file
        }

    }

    //returns if there are more lines in the input
    public boolean hasMoreLines(){

        return false;
    }


    /*
     * Gets the next instruction and makes it the current instruction (string)
     */
    public String advance(){
        return "";
    }
    
}
