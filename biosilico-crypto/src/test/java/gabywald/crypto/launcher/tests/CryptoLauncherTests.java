package gabywald.crypto.launcher.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

import gabywald.crypto.launcher.CryptoLauncher;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
class CryptoLauncherTests {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	// @Test
	void testMain() {
		
		// Assertions.assertNotNull(CryptoLauncher.main(new String[]{}));
		
		// CryptoLauncher.main(new String[]{});
		// CryptoLauncher.main(new String[]{"--DATA=toto"});
		// CryptoLauncher.main(new String[]{"--DATA=toto", "-s"});
		// CryptoLauncher.main(new String[]{"--DATA=toto", "-s", "-c"});
		// CryptoLauncher.main(new String[]{"--DATA=\"toto\"", "-s", "-c"});
		// CryptoLauncher.main(new String[]{"--DATA=toto", "-s", "-c", "-y=3"});
		// CryptoLauncher.main(new String[]{"--DATA=toto", "-s", "-c", "encode"});
		// CryptoLauncher.main(new String[]{"--DATA=toto", "-s", "-c", "encode", "--DATA=toto"});
		// CryptoLauncher.main(new String[]{"--DATA=toto", "-s", "-c", "encode", "--DATA=toto", "-s"});
		CryptoLauncher.main(new String[]{"--DATA=toto", "-s", "-c", "encode", "--DATA=toto", "-s", "-c"});
		// // CryptoLauncher.main(new String[]{"encode", "--DATA=toto", "-s", "-c"});
		
		// Assertions.fail("Not yet implemented");
	}

}
