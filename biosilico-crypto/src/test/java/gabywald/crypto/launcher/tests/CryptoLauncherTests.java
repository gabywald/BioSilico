package gabywald.crypto.launcher.tests;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gabywald.crypto.launcher.CryptoLauncher;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
class CryptoLauncherTests {
	private PrintStream originalOut = System.out;
	private PrintStream originalErr = System.err;
	private ByteArrayOutputStream bosOUT = new ByteArrayOutputStream();
	private ByteArrayOutputStream bosERR = new ByteArrayOutputStream();

	@BeforeEach
	void setUp() throws Exception {
		System.setOut(new PrintStream(this.bosOUT));
		System.setErr(new PrintStream(this.bosERR));
	}

	@AfterEach
	void tearDown() throws Exception {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}
	
	void flushBOSes() {
		try { bosOUT.flush();bosOUT.reset();bosERR.flush();bosERR.reset(); } 
		catch (IOException e) { e.printStackTrace(); }
	}
	
	String[] attemptedErrors = {
			"Missing required option: '--DATA=<dataTotranscript>'", 
			"Error: Missing required argument (specify one of these): (-e | -x)", 
			"Error: Missing required argument (specify one of these): (-s | -m | -r)", 
			"Error: Missing required argument (specify one of these): (-c | -f | -d)"
	};
	boolean attemptedErrors() { return true; }
	
	String[] attemptedErrorXorE = {
			"Error: Missing required argument (specify one of these): (-e | -x)",
			"Error: Missing required argument (specify one of these): (-x | -e)"
	};
	boolean attemptedErrorXorE(String msg) { return Arrays.asList(attemptedErrorXorE).contains(msg); }

	String[] attemptedErrorSMR = {
			"Error: Missing required argument (specify one of these): (-m | -r | -s)",
			"Error: Missing required argument (specify one of these): (-s | -r | -m)",
			"Error: Missing required argument (specify one of these): (-s | -m | -r)",
			"Error: Missing required argument (specify one of these): (-r | -m | -s)",
			"Error: Missing required argument (specify one of these): (-m | -s | -r)",
			"Error: Missing required argument (specify one of these): (-r | -s | -m)"
	};
	boolean attemptedErrorMRS(String msg) { return Arrays.asList(attemptedErrorSMR).contains(msg); }
	
	String[] attemptedErrorCFD = {
			"Error: Missing required argument (specify one of these): (-c | -f | -d)",
			"Error: Missing required argument (specify one of these): (-d | -f | -c)", 
			"Error: Missing required argument (specify one of these): (-f | -c | -d)",
			"Error: Missing required argument (specify one of these): (-d | -c | -f)", 
			"Error: Missing required argument (specify one of these): (-c | -d | -f)",
			"Error: Missing required argument (specify one of these): (-f | -d | -c)"
	};
	boolean attemptedErrorCFD(String msg) { return Arrays.asList(attemptedErrorCFD).contains(msg); }
	

	@Test
	void testMain() {
		
		// Assertions.assertNotNull(CryptoLauncher.main(new String[]{}));
		
		Logger.setLogLevel(LoggerLevel.LL_WARNING);
		
		CryptoLauncher.main(new String[]{});
		Assertions.assertEquals("2\n", bosOUT.toString());
		Assertions.assertEquals(attemptedErrors[0], bosERR.toString().split("\n")[0]);
		this.flushBOSes();
		
		CryptoLauncher.main(new String[]{"--DATA=toto"});
		Assertions.assertEquals("2\n", bosOUT.toString());
		// Assertions.assertEquals(attemptedErrors[1], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorXorE(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
		CryptoLauncher.main(new String[]{"--DATA=toto", "-s"});
		Assertions.assertEquals("2\n", bosOUT.toString());
		// Assertions.assertEquals(attemptedErrors[1], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorXorE(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
		CryptoLauncher.main(new String[]{"--DATA=toto", "-s", "-c"});
		Assertions.assertEquals("2\n", bosOUT.toString());
		// Assertions.assertEquals(attemptedErrors[1], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorXorE(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
		CryptoLauncher.main(new String[]{"--DATA=\"toto\"", "-s", "-c"});
		Assertions.assertEquals("2\n", bosOUT.toString());
		// Assertions.assertEquals(attemptedErrors[1], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorXorE(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
		CryptoLauncher.main(new String[]{"--DATA=toto", "-s", "-c", "-y=3"});
		Assertions.assertEquals("2\n", bosOUT.toString());
		// Assertions.assertEquals(attemptedErrors[1], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorXorE(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
		
		CryptoLauncher.main(new String[]{"--DATA=toto", "-x"});
		Assertions.assertEquals("2\n", bosOUT.toString());
		// Assertions.assertEquals(attemptedErrors[2], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorMRS(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
		CryptoLauncher.main(new String[]{"--DATA=toto", "-x", "-c"});
		Assertions.assertEquals("2\n", bosOUT.toString());
		// Assertions.assertEquals(attemptedErrors[2], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorMRS(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
		CryptoLauncher.main(new String[]{"--DATA=\"toto\"", "-x", "-c"});
		Assertions.assertEquals("2\n", bosOUT.toString());
		// Assertions.assertEquals(attemptedErrors[2], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorMRS(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
		CryptoLauncher.main(new String[]{"--DATA=toto", "-x", "-c", "-y=3"});
		Assertions.assertEquals("2\n", bosOUT.toString());
		// Assertions.assertEquals(attemptedErrors[2], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorMRS(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
		
		CryptoLauncher.main(new String[]{"--DATA=toto", "-x", "-s"});
		Assertions.assertEquals("2\n", bosOUT.toString());
		// Assertions.assertEquals(attemptedErrors[3], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorCFD(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
		CryptoLauncher.main(new String[]{"--DATA=\"toto\"", "-x", "-s"});
		Assertions.assertEquals("2\n", bosOUT.toString());
		// Assertions.assertEquals(attemptedErrors[3], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorCFD(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
		CryptoLauncher.main(new String[]{"--DATA=toto", "-x", "-s", "-y=3"});
		Assertions.assertEquals("2\n", bosOUT.toString());
		// Assertions.assertEquals(attemptedErrors[3], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorCFD(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
		
		CryptoLauncher.main(new String[]{"--DATA=toto", "-s", "-c", "-x"});
		Assertions.assertEquals("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false\n"
				+ "dataToTranscript=toto\n"
				+ "\t\tmethodSimple=true, methodMore=false, methodRand=false\n"
				+ "0\n", bosOUT.toString());
		Assertions.assertEquals("", bosERR.toString().split("\n")[0]);
		this.flushBOSes();
		CryptoLauncher.main(new String[]{"--DATA=toto", "-s", "-c", "-e"});
		Assertions.assertEquals("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false\n"
				+ "dataToTranscript=toto\n"
				+ "\t\tmethodSimple=true, methodMore=false, methodRand=false\n"
				+ "0\n", bosOUT.toString());
		Assertions.assertEquals("", bosERR.toString().split("\n")[0]);
		this.flushBOSes();
		CryptoLauncher.main(new String[]{"--DATA=\"toto\"", "-s", "-c", "-e"});
		Assertions.assertEquals("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false\n"
				+ "dataToTranscript=\"toto\"\n"
				+ "\t\tmethodSimple=true, methodMore=false, methodRand=false\n"
				+ "0\n", bosOUT.toString());
		Assertions.assertEquals("", bosERR.toString().split("\n")[0]);
		this.flushBOSes();
		
		// TODO continue tests here !!
	}

}
