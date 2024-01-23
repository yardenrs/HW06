import java.io.IOException;

public class Main {

    public static void main (String[] args) throws IOException{
        
        HackAssembler assembler = new HackAssembler(args[0]);
        assembler.run();
    }
      
}
