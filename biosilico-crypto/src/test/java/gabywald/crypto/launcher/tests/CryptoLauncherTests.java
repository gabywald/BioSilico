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
		Logger.setLogLevel(LoggerLevel.LL_DEBUG);
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
	void testMain01() {
		Logger.setLogLevel(LoggerLevel.LL_WARNING);
		// Assertions.assertNotNull(CryptoLauncher.main(new String[]{}));
		CryptoLauncher.main(new String[]{});
		// Assertions.assertEquals("2\n", bosOUT.toString());
		Assertions.assertTrue(bosOUT.toString().endsWith(""));
		Assertions.assertEquals(attemptedErrors[0], bosERR.toString().split("\n")[0]);
		this.flushBOSes();
	}
	
	@Test
	void testMain02a() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=toto"});
		// Assertions.assertEquals("2\n", bosOUT.toString());
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		// Assertions.assertEquals(attemptedErrors[1], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorMRS(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
	
	@Test
	void testMain02b() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=toto", "-s"});
		// Assertions.assertEquals("2\n", bosOUT.toString());
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		// Assertions.assertEquals(attemptedErrors[1], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorCFD(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
	
	@Test
	void testMain02c() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=toto", "-s", "-c"});
		// Assertions.assertEquals("2\n", bosOUT.toString());
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		// Assertions.assertEquals(attemptedErrors[1], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorXorE(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
	
	@Test
	void testMain02d() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\"toto\"", "-s", "-c"});
		// Assertions.assertEquals("2\n", bosOUT.toString());
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		// Assertions.assertEquals(attemptedErrors[1], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorXorE(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
	
	@Test
	void testMain02e() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=toto", "-s", "-c", "-i=3"});
		// Assertions.assertEquals("2\n", bosOUT.toString());
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		// Assertions.assertEquals(attemptedErrors[1], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorXorE(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
		
	@Test
	void testMain03a() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=toto", "-x"});
		// Assertions.assertEquals("2\n", bosOUT.toString());
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		// Assertions.assertEquals(attemptedErrors[2], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorMRS(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
	
	@Test
	void testMain03b() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c"});
		// Assertions.assertEquals("2\n", bosOUT.toString());
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		// Assertions.assertEquals(attemptedErrors[2], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorMRS(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
	
	@Test
	void testMain03c() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\"toto\"", "-x", "-c"});
		// Assertions.assertEquals("2\n", bosOUT.toString());
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		// Assertions.assertEquals(attemptedErrors[2], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorMRS(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
	
	@Test
	void testMain03d() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-i=3"});
		// Assertions.assertEquals("2\n", bosOUT.toString());
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		// Assertions.assertEquals(attemptedErrors[2], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorMRS(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
	
	@Test
	void testMain03e() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-s", "-i=3"});
		Assertions.assertTrue(bosOUT.toString().startsWith("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=true, methodMore=false, methodRand=false\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain03f() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-r", "-i=3"});
		Assertions.assertTrue(bosOUT.toString().startsWith("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=false, methodMore=false, methodRand=true\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain03g() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-m", "-i=3"});
		Assertions.assertTrue(bosOUT.toString().startsWith("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=false, methodMore=true, methodRand=false\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain03h() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-i=42"});
		// Assertions.assertEquals("2\n", bosOUT.toString());
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		// Assertions.assertEquals(attemptedErrors[2], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorMRS(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
	
	@Test
	void testMain03i() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-s", "-i=42"});
		Assertions.assertTrue(bosOUT.toString().contains("ERROR BAD CRYPTO FILE INDEX {42} !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("ERROR GT NOT DEFINED !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=true, methodMore=false, methodRand=false\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain03j() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-r", "-i=42"});
		Assertions.assertTrue(bosOUT.toString().contains("ERROR BAD CRYPTO FILE INDEX {42} !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("ERROR GT NOT DEFINED !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=false, methodMore=false, methodRand=true\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain03k() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-m", "-i=42"});
		Assertions.assertTrue(bosOUT.toString().contains("ERROR BAD CRYPTO FILE INDEX {42} !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("ERROR GT NOT DEFINED !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=false, methodMore=true, methodRand=false\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain03l() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-s", "-i=-1"});
		Assertions.assertTrue(bosOUT.toString().contains("ERROR BAD CRYPTO FILE INDEX {-1} !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("ERROR GT NOT DEFINED !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=true, methodMore=false, methodRand=false\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain03m() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-r", "-i=-1"});
		Assertions.assertTrue(bosOUT.toString().contains("ERROR BAD CRYPTO FILE INDEX {-1} !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("ERROR GT NOT DEFINED !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=false, methodMore=false, methodRand=true\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain03n() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-m", "-i=-1"});
		Assertions.assertTrue(bosOUT.toString().contains("ERROR BAD CRYPTO FILE INDEX {-1} !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("ERROR GT NOT DEFINED !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=false, methodMore=true, methodRand=false\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain04a() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-s"});
		// Assertions.assertEquals("2\n", bosOUT.toString());
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		// Assertions.assertEquals(attemptedErrors[3], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorCFD(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
	
	@Test
	void testMain04b() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\"toto\"", "-x", "-s"});
		// Assertions.assertEquals("2\n", bosOUT.toString());
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		// Assertions.assertEquals(attemptedErrors[3], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorCFD(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
	
	@Test
	void testMain04c() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-s", "-i=3"});
		// Assertions.assertEquals("2\n", bosOUT.toString());
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		// Assertions.assertEquals(attemptedErrors[3], bosERR.toString().split("\n")[0]);
		Assertions.assertTrue(this.attemptedErrorCFD(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
		
	}
	
	@Test
	void testMain05a() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=:" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:", "-s", "-c", "-x"});
//		Assertions.assertEquals("XXcodeCommand:\n"
//				+ "\t\tcontent=true, filePath=false, directoryPath=false\n"
//				+ "dataToTranscript=toto\n"
//				+ "\t\tmethodSimple=true, methodMore=false, methodRand=false\n"
//				+ "0\n", bosOUT.toString());
		Assertions.assertTrue(bosOUT.toString().startsWith("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=true, methodMore=false, methodRand=false\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		Assertions.assertEquals("", bosERR.toString().split("\n")[0]);
		this.flushBOSes();
	}
	
	@Test
	void testMain05b() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=toto", "-s", "-c", "-e"});
//		Assertions.assertEquals("XXcodeCommand:\n"
//				+ "\t\tcontent=true, filePath=false, directoryPath=false\n"
//				+ "dataToTranscript=toto\n"
//				+ "\t\tmethodSimple=true, methodMore=false, methodRand=false\n"
//				+ "0\n", bosOUT.toString());
		Assertions.assertTrue(bosOUT.toString().startsWith("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=toto,subcommand=encode, \n"
				+ "\t\tmethodSimple=true, methodMore=false, methodRand=false\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		Assertions.assertEquals("", bosERR.toString().split("\n")[0]);
		this.flushBOSes();
	}
	
	@Test
	void testMain05c() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\"toto\"", "-s", "-c", "-e"});
//		Assertions.assertEquals("XXcodeCommand:\n"
//				+ "\t\tcontent=true, filePath=false, directoryPath=false\n"
//				+ "dataToTranscript=\"toto\"\n"
//				+ "\t\tmethodSimple=true, methodMore=false, methodRand=false\n"
//				+ "0\n", bosOUT.toString());
		Assertions.assertTrue(bosOUT.toString().startsWith("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=toto,subcommand=encode, \n"
				+ "\t\tmethodSimple=true, methodMore=false, methodRand=false\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		Assertions.assertEquals("", bosERR.toString().split("\n")[0]);
		this.flushBOSes();
	}
	
	// TODO continue tests here !!

}
