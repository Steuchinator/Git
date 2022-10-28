import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class ABlob {

	//Takes in file name and returns string of file contents
//	public static String getFileString(File file) throws IOException {
//
//		FileInputStream fis = new FileInputStream( file.getName());
//		byte[] buffer = new byte[10];
//		StringBuilder sb = new StringBuilder();
//		while (fis.read(buffer) != -1) {
//			sb.append(new String(buffer));
//			buffer = new byte[10];
//		}
//		fis.close();
//		return sb.toString();
//}
	public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

	public static String encryptThisString(String input) {
        try {
            // getInstance() method is called with algorithm SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
            // Convert message digest into hex value
            String hashtext = no.toString(16);
 
            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            // return the HashText
            return hashtext;
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
}
	public static void copyContent(File a, File b) throws Exception
	    {
	        FileInputStream in = new FileInputStream(a);
	        FileOutputStream out = new FileOutputStream(b);
	        try {
	            int n;
	            // read() function to read the
	            // byte of data
	            while ((n = in.read()) != -1) {
	                // write() function to write
	                // the byte of data
	                out.write(n);
	            }
	        }
	        finally {
	            if (in != null) {
	                // close() function to close the
	                // stream
	                in.close();
	            }
	            // close() function to close
	            // the stream
	            if (out != null) {
	                out.close();
	            }
	        }
	        System.out.println("File Copied");
	    }
	
//	public static void main (String[] args) throws IOException {
//		System.out.println(encryptThisString("Hello World"));
//		System.out.println(encryptThisString("hi"));
////		 File file = new File("TesterFile.txt");
////		    Scanner sc = new Scanner(file);
////		    // we just need to use \\Z as delimiter
////		    sc.useDelimiter("\\Z");
////		    System.out.println(sc.next());
//	}
	// no need for a main in blob
	
	public static void createBlob (String fileName) throws Exception, IOException {
		//Step 1: Get string of file contents
		File file = new File(fileName);
		String fileContents = readFile(fileName, StandardCharsets.US_ASCII);
		//Step 2: Encrypt file contents
		String fileHash = encryptThisString(fileContents);
		//Step 3: create new file with name of the hash of contents of previous file
		File newFile = new File(".\\objects\\" + fileHash);
		//Step 4: Copy contents
		copyContent(file, newFile);
	}
	
}