public class Main {

    public statin main (String[] args){
        try {
            HackAssembler assembler = new HackAssembler(args[0]);
            assembler.firstPass();
            assembler.secondPass();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
