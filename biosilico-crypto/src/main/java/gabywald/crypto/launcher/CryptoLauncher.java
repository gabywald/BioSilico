package gabywald.crypto.launcher;

// import org.apache.commons.lang3.StringUtils;
import picocli.CommandLine;
import static picocli.CommandLine.Command;
import static picocli.CommandLine.Option;
import static picocli.CommandLine.Parameters;

/**
 * 
 * @author Gabriel Chandesris (2025)
 */
public class CryptoLauncher {

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}
	
    public static void main(String[] args) {
        int exitCode = new CommandLine(new HelloCommand()).execute(args);
        System.exit(exitCode);
    }

    @Command(name = "hello", description = "Prints hello to somebody")
    static class HelloCommand implements Runnable {
        @Parameters(index = "0", defaultValue = "stranger", description = "The name for greeting")
        private String name;

        @Option(names = {"-c", "--capitalize"}, defaultValue = "false", description = "Capitalize the name")
        private boolean isCapitalizeEnabled;

        @Override
        public void run() {
            // System.out.println("Hello, " + (isCapitalizeEnabled ? StringUtils.capitalize(name) : name));
        	System.out.println("Hello, " + (isCapitalizeEnabled ? name.toUpperCase() : name));
        }
    }

}
