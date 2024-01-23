import java.io.IOException;

public class Main {

    public static void main (String[] args){
        try {
            HackAssembler assembler = new HackAssembler("MaxL.asm");
            assembler.firstPass();
            assembler.secondPass();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
