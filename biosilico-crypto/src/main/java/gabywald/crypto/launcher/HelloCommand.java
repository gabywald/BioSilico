package gabywald.crypto.launcher;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * Basic example for picocli use. 
 * <BR/>See {@link https://www.baeldung.com/java-picocli-create-command-line-program }
 * @author Gabriel Chandesris (2026)
 * @deprecated Example Picocli Command. 
 */
@Command(name = "hello", description = "Prints hello to somebody")
class HelloCommand implements Runnable {
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
