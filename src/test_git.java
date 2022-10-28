import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class test_git {
	
	final String TEST_SHA = "da39a3ee5e6b4b0d3255bfef95601890afd80709";
	final String TEST_PATH = "test.txt";
	final String TEST2_PATH = "test2.txt";
	final String TEST3_PATH = "test3.txt";
	final String INDEX_PATH = "index";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
//		Creating test.txt MANUALLY right now with contents "hi"
//		"hi" sha1 = "c22b5f9178342609428d6f51b2c5af4c0bde6a42"
		File f = new File("test.txt");
		FileWriter writer = new FileWriter(f);
		writer.write("content");
		f = new File("test2.txt");
		writer = new FileWriter(f);
		writer.write("content 2");
		f = new File("test3.txt");
		writer = new FileWriter(f);
		writer.write("content 3");
		writer.close();
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}
/*
 * What to test:
 * - Blob()
 * - Index.init()
 * - Index.add() 
 * - Index.remove()
 * 
 * TODO: Do we need error messages on fails?
 */
	@Test
	void fullTest() throws Exception {
		
		Commit current;
		File head = new File("HEAD");
		Index index = new Index();
		index.init();
		index.add(TEST_PATH);
		FileWriter writer = new FileWriter(head);
		writer.write("");
		writer.close();
		
		index.add(TEST_PATH);
		Commit c = new Commit("summary","author");
		index.add(TEST2_PATH);
		Commit c2 = new Commit("summary 2","author 2");
		index.add(TEST3_PATH);
		Commit c3 = new Commit("summary 3","author 3");
	}
	
	
	void blobInit() {
		try {
			ABlob.createBlob(TEST_PATH);
			
//			look for file in objects directory
			File f = new File(".\\objects\\" + TEST_SHA);
			System.out.println(f.exists());
			assertTrue(f.exists());
			
//			check contents
			Path p1 = Path.of(".\\objects\\" + TEST_SHA);
			Path p2 = Path.of(TEST_PATH);
			
			assertEquals(-1, filesCompareByLine(p1, p2));
		} catch (Exception e) {
			fail();
		}
	}
	
	
	void indexInit() {
		Index i = new Index();	
		try {
			i.init();
			
//			objects directory exists
			assertTrue(new File(".\\objects\\").exists());

//			index file exists
			assertTrue(new File(INDEX_PATH).exists());
		} catch (Exception e) {
			fail();
		}
	}
	
	
	void indexAdd() {
		Index i = new Index();
		try {
			i.init();
			i.add(TEST_PATH);
			i.add(TEST2_PATH);
//			check objects folder
			assertTrue(new File(".\\objects\\" + TEST_SHA).exists());
			
//			check index file
			HashMap<String, String> map = getMapFromFile(new File(INDEX_PATH));
			
			assertEquals(map.get(TEST_PATH), TEST_SHA);
		} catch(Exception e) {
			fail();
		}
	}
	
	public HashMap<String, String> getMapFromFile(File file) {
		HashMap<String, String> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.equals("")) continue;
                String hash = line.substring(line.length()-40);
                String filename = line.substring(0, line.length()-43);
                map.put(filename, hash);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return map;
    }
	
	void indexRemove() {
		Index i = new Index();
		try {
			i.init();
			i.add(TEST_PATH);
			i.remove(new File(TEST_PATH));
			
//			check objects folder
			assertFalse(new File(".\\objects\\" + TEST_SHA).exists());
			
//			check index file
			HashMap<String, String> map = getMapFromFile(new File(INDEX_PATH));
			
			assertNotEquals(map.get(TEST_PATH), TEST_SHA);
		} catch(Exception e) {
			fail();
		}
	}
	
	
	
	public static long filesCompareByLine(Path path1, Path path2) throws IOException {
	    try (BufferedReader bf1 = Files.newBufferedReader(path1);
	         BufferedReader bf2 = Files.newBufferedReader(path2)) {
	        
	        long lineNumber = 1;
	        String line1 = "", line2 = "";
	        while ((line1 = bf1.readLine()) != null) {
	            line2 = bf2.readLine();
	            if (line2 == null || !line1.equals(line2)) {
	                return lineNumber;
	            }
	            lineNumber++;
	        }
	        if (bf2.readLine() == null) {
	            return -1;
	        }
	        else {
	            return lineNumber;
	        }
	    }
	}

}