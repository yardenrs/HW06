import java.io.File;
import java.io.IOException;

public class Main {

    public static void main (String[] args) throws IOException{
        
        if(args.length < 1)
            return;
        String path = args[0];
        File file = new File(path);
        if (file.isFile() && file.getName().endsWith(".asm")) {  // If it's a single .asm file, process it
            HackAssembler assembler = new HackAssembler(path);
            assembler.run();
        }
        else if (file.isDirectory()) { // If it's a directory, process all .asm files in the directory
            File[] asmFiles = file.listFiles((dir, name) -> name.endsWith(".asm"));
            if (asmFiles != null && asmFiles.length > 0) {    // Process each .asm file in the directory
                for (File asmFile : asmFiles) {
                    HackAssembler assembler = new HackAssembler(asmFile.getAbsolutePath());
                    assembler.run();
                }
            }
        }
        else {
            System.out.println("Invalid path or file type.");
        }
    }
    }
      
}
