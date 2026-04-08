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
import gabywald.global.data.File;
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
		CryptoLauncher.main(new String[]{});
		Assertions.assertTrue(bosOUT.toString().endsWith(""));
		Assertions.assertEquals(attemptedErrors[0], bosERR.toString().split("\n")[0]);
		this.flushBOSes();
	}
	
	@Test
	void testMain02a() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=toto"});
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		Assertions.assertTrue(this.attemptedErrorMRS(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
	
	@Test
	void testMain02b() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=toto", "-s"});
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		Assertions.assertTrue(this.attemptedErrorCFD(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
	
	@Test
	void testMain02c() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=toto", "-s", "-c"});
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		Assertions.assertTrue(this.attemptedErrorXorE(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
	
	@Test
	void testMain02d() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\"toto\"", "-s", "-c"});
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		Assertions.assertTrue(this.attemptedErrorXorE(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
	
	@Test
	void testMain02e() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=toto", "-s", "-c", "-i=3"});
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		Assertions.assertTrue(this.attemptedErrorXorE(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
		
	@Test
	void testMain03a() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=toto", "-x"});
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		Assertions.assertTrue(this.attemptedErrorMRS(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
	
	@Test
	void testMain03b() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c"});
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		Assertions.assertTrue(this.attemptedErrorMRS(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
	
	@Test
	void testMain03c() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\"toto\"", "-x", "-c"});
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
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
		Assertions.assertTrue(bosOUT.toString().contains("ERROR GTFILE NOT DEFINED !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=true, methodMore=false, methodRand=false\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("1\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain03j() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-r", "-i=42"});
		Assertions.assertTrue(bosOUT.toString().contains("ERROR BAD CRYPTO FILE INDEX {42} !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("ERROR GTFILE NOT DEFINED !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=false, methodMore=false, methodRand=true\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("1\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain03k() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-m", "-i=42"});
		Assertions.assertTrue(bosOUT.toString().contains("ERROR BAD CRYPTO FILE INDEX {42} !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("ERROR GTFILE NOT DEFINED !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=false, methodMore=true, methodRand=false\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("1\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain03l() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-s", "-i=-1"});
		Assertions.assertTrue(bosOUT.toString().contains("ERROR BAD CRYPTO FILE INDEX {-1} !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("ERROR GTFILE NOT DEFINED !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=true, methodMore=false, methodRand=false\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("1\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain03m() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-r", "-i=-1"});
		Assertions.assertTrue(bosOUT.toString().contains("ERROR BAD CRYPTO FILE INDEX {-1} !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("ERROR GTFILE NOT DEFINED !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=false, methodMore=false, methodRand=true\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("1\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain03n() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-m", "-i=-1"});
		Assertions.assertTrue(bosOUT.toString().contains("ERROR BAD CRYPTO FILE INDEX {-1} !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("ERROR GTFILE NOT DEFINED !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=false, methodMore=true, methodRand=false\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("1\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain03o() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-s", "-j=42"});
		Assertions.assertTrue(bosOUT.toString().contains("ERROR BAD CRYPTO PATH INDEX {42} !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("ERROR GTPATH NOT DEFINED !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=true, methodMore=false, methodRand=false\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("1\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain03p() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-r", "-j=42"});
		Assertions.assertTrue(bosOUT.toString().contains("ERROR BAD CRYPTO PATH INDEX {42} !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("ERROR GTPATH NOT DEFINED !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=false, methodMore=false, methodRand=true\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("1\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain03q() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-m", "-j=42"});
		Assertions.assertTrue(bosOUT.toString().contains("ERROR BAD CRYPTO PATH INDEX {42} !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("ERROR GTPATH NOT DEFINED !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=false, methodMore=true, methodRand=false\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("1\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain03r() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-s", "-j=-1"});
		Assertions.assertTrue(bosOUT.toString().contains("ERROR BAD CRYPTO PATH INDEX {-1} !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("ERROR GTPATH NOT DEFINED !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=true, methodMore=false, methodRand=false\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("1\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain03s() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-r", "-j=-1"});
		Assertions.assertTrue(bosOUT.toString().contains("ERROR BAD CRYPTO PATH INDEX {-1} !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("ERROR GTPATH NOT DEFINED !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=false, methodMore=false, methodRand=true\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("1\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain03t() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-c", "-m", "-j=-1"});
		Assertions.assertTrue(bosOUT.toString().contains("ERROR BAD CRYPTO PATH INDEX {-1} !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("ERROR GTPATH NOT DEFINED !!\n"));
		Assertions.assertTrue(bosOUT.toString().contains("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=:@@@@@cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:,subcommand=decode, \n"
				+ "\t\tmethodSimple=false, methodMore=true, methodRand=false\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("1\n"));
		this.flushBOSes();
	}
	
	@Test
	void testMain04a() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-s"});
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		Assertions.assertTrue(this.attemptedErrorCFD(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
	
	@Test
	void testMain04b() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\"toto\"", "-x", "-s"});
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		Assertions.assertTrue(this.attemptedErrorCFD(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
	}
	
	@Test
	void testMain04c() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=\":" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:\"", "-x", "-s", "-i=3"});
		Assertions.assertTrue(bosOUT.toString().endsWith("2\n"));
		Assertions.assertTrue(this.attemptedErrorCFD(bosERR.toString().split("\n")[0]));
		this.flushBOSes();
		
	}
	
	@Test
	void testMain05a() {
		CryptoLauncher.main(new String[]{"--info", "--DATA=:" + "@@@@@"
				+ "cgatctcacgatcgaccgatcgctctcactcacgatctcacgatcgaccgatcgctctcactca:", "-s", "-c", "-x"});
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
		Assertions.assertTrue(bosOUT.toString().startsWith("XXcodeCommand:\n"
				+ "\t\tcontent=true, filePath=false, directoryPath=false, \n"
				+ "\t\tdataToTranscript=toto,subcommand=encode, \n"
				+ "\t\tmethodSimple=true, methodMore=false, methodRand=false\n"));
		Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		Assertions.assertEquals("", bosERR.toString().split("\n")[0]);
		this.flushBOSes();
	}
	
	@Test
	void testOperationLikeTheWindDirectEncode() {
		CryptoLauncher.main(new String[]{"-e", "-f", "-s", "--DATA=src/test/resources/OperationLeiaLikeTheWind/DiscoursAuSenatLeiaOrgana.txt"});
		// Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		try {
			String toReadContent = File.readFile("src/test/resources/OperationLeiaLikeTheWind/OperationLikeTheWind.direct");
			Assertions.assertEquals(toReadContent, bosOUT.toString());
		} catch (IOException e) { e.printStackTrace();Assertions.fail(e); }
		Assertions.assertEquals("", bosERR.toString().split("\n")[0]);
		this.flushBOSes();
	}
	
	@Test
	void testOperationLikeTheWindFASTAEncode() {
		CryptoLauncher.main(new String[]{"-e", "-f", "-s", "--fasta", "--DATA=src/test/resources/OperationLeiaLikeTheWind/DiscoursAuSenatLeiaOrgana.txt"});
		// Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		try {
			String toReadContent = File.readFile("src/test/resources/OperationLeiaLikeTheWind/OperationLikeTheWind.fasta");
			int trcIndex = toReadContent.indexOf( "|18984 bp|" );
			int bosIndex = bosOUT.toString().indexOf( "|18984 bp|" );
			Assertions.assertEquals(toReadContent.substring(trcIndex), bosOUT.toString().substring(bosIndex));
		} catch (IOException e) { e.printStackTrace();Assertions.fail(e); }
		Assertions.assertEquals("", bosERR.toString().split("\n")[0]);
		this.flushBOSes();
	}
	
	@Test
	void testOperationLikeTheWindEMBLEncode() {
		CryptoLauncher.main(new String[]{"-e", "-f", "-s", "--embl", "--DATA=src/test/resources/OperationLeiaLikeTheWind/DiscoursAuSenatLeiaOrgana.txt"});
		// Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		try {
			String toReadContent = File.readFile("src/test/resources/OperationLeiaLikeTheWind/OperationLikeTheWind.embl");
			// Assertions.assertEquals(toReadContent, bosOUT.toString());
			int trcIndex = toReadContent.indexOf( "SQ   Sequence " );
			int bosIndex = bosOUT.toString().indexOf( "SQ   Sequence " );
			Assertions.assertEquals(toReadContent.substring(trcIndex), bosOUT.toString().substring(bosIndex));
		} catch (IOException e) { e.printStackTrace();Assertions.fail(e);  }
		Assertions.assertEquals("", bosERR.toString().split("\n")[0]);
		this.flushBOSes();
	}
	
	@Test
	void testOperationLikeTheWindGENBANKEncode() {
		CryptoLauncher.main(new String[]{"-e", "-f", "-s", "--genbank", "--DATA=src/test/resources/OperationLeiaLikeTheWind/DiscoursAuSenatLeiaOrgana.txt"});
		// Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		try {
			String toReadContent = File.readFile("src/test/resources/OperationLeiaLikeTheWind/OperationLikeTheWind.genbank");
			// Assertions.assertEquals(toReadContent, bosOUT.toString());
			int trcIndex = toReadContent.indexOf( "BASE COUNT" );
			int bosIndex = bosOUT.toString().indexOf( "BASE COUNT" );
			Assertions.assertEquals(toReadContent.substring(trcIndex), bosOUT.toString().substring(bosIndex));
		} catch (IOException e) { e.printStackTrace();Assertions.fail(e); }
		Assertions.assertEquals("", bosERR.toString().split("\n")[0]);
		this.flushBOSes();
	}
	
	@Test
	void testOperationLikeTheWindDirectDecode() {
		CryptoLauncher.main(new String[]{"-x", "-f", "-s", "--DATA=src/test/resources/OperationLeiaLikeTheWind/OperationLikeTheWind.direct"});
		// Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		try {
			String originalPath = "src/test/resources/OperationLeiaLikeTheWind/DiscoursAuSenatLeiaOrgana.txt";
			String toReadContent = originalPath + "\n" 
					+ File.readFile("src/test/resources/OperationLeiaLikeTheWind/DiscoursAuSenatLeiaOrgana.txt")
					+ "\n" ;
			Assertions.assertEquals(toReadContent, bosOUT.toString());
		} catch (IOException e) { e.printStackTrace();Assertions.fail(e); }
		Assertions.assertEquals("", bosERR.toString().split("\n")[0]);
		this.flushBOSes();
	}
	
	@Test
	void testOperationLikeTheWindFASTADecode() {
		CryptoLauncher.main(new String[]{"-x", "-f", "-s", "--fasta", "--DATA=src/test/resources/OperationLeiaLikeTheWind/OperationLikeTheWind.fasta"});
		// Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		try {
			String originalPath = "src/test/resources/OperationLeiaLikeTheWind/DiscoursAuSenatLeiaOrgana.txt";
			String toReadContent = originalPath + "\n" 
					+ File.readFile("src/test/resources/OperationLeiaLikeTheWind/DiscoursAuSenatLeiaOrgana.txt")
					+ "\n" ;
			Assertions.assertEquals(toReadContent, bosOUT.toString());
		} catch (IOException e) { e.printStackTrace();Assertions.fail(e); }
		Assertions.assertEquals("", bosERR.toString().split("\n")[0]);
		this.flushBOSes();
	}
	
	@Test
	void testOperationLikeTheWindEMBLDecode() {
		CryptoLauncher.main(new String[]{"-x", "-f", "-s", "--embl", "--DATA=src/test/resources/OperationLeiaLikeTheWind/OperationLikeTheWind.embl"});
		// Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		try {
			String originalPath = "src/test/resources/OperationLeiaLikeTheWind/DiscoursAuSenatLeiaOrgana.txt";
			String toReadContent = originalPath + "\n" 
					+ File.readFile("src/test/resources/OperationLeiaLikeTheWind/DiscoursAuSenatLeiaOrgana.txt")
					+ "\n" ;
			Assertions.assertEquals(toReadContent, bosOUT.toString());
		} catch (IOException e) { e.printStackTrace();Assertions.fail(e); }
		Assertions.assertEquals("", bosERR.toString().split("\n")[0]);
		this.flushBOSes();
	}
	
	@Test
	void testOperationLikeTheWindGENBANKDecode() {
		CryptoLauncher.main(new String[]{"-x", "-f", "-s", "--genbank", "--DATA=src/test/resources/OperationLeiaLikeTheWind/OperationLikeTheWind.genbank"});
		// Assertions.assertTrue(bosOUT.toString().endsWith("0\n"));
		try {
			String originalPath = "src/test/resources/OperationLeiaLikeTheWind/DiscoursAuSenatLeiaOrgana.txt";
			String toReadContent = originalPath + "\n" 
					+ File.readFile("src/test/resources/OperationLeiaLikeTheWind/DiscoursAuSenatLeiaOrgana.txt")
					+ "\n" ;
			Assertions.assertEquals(toReadContent, bosOUT.toString());
		} catch (IOException e) { e.printStackTrace();Assertions.fail(e); }
		Assertions.assertEquals("", bosERR.toString().split("\n")[0]);
		this.flushBOSes();
	}

}
