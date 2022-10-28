import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.Scanner;


import java.util.ArrayList;
import java.util.Calendar;  

public class Commit {
	String parent;
	String child;
	String pTree;
	String summary;
	String author;
	String date;
	String sha;
	
	public Commit(String inputSummary, String inputAuthor) throws Exception {
		File f = new File("HEAD");
		BufferedReader reader = new BufferedReader(new FileReader(f));
		
		String temp = "";
		while (reader.ready())
			temp += (char)reader.read();
		reader.close();
		
		if(temp.length()!=0)
			parent = temp;
		else
			parent = null;
		
		summary = inputSummary;
		author = inputAuthor;
		pTree = makeTree();
		writeFile();
		FileWriter writer = new FileWriter(f);
		writer.write(createMySHA());
		
		FileWriter indexClearer = new FileWriter("index");
		indexClearer.write("");
		writer.close();
		indexClearer.close();
		
		
	}
	

	public String makeTree() throws NoSuchAlgorithmException, IOException {
		ArrayList<String> arr = new ArrayList<String>();
		String str = "";
		String temp = "";
		File f = new File("index");
		Scanner scanner = new Scanner(f);
		while(scanner.hasNext()) {
			str = scanner.nextLine();
			temp+="blob"+str.substring(str.indexOf(" : "));
			temp+=" "+str.substring(0,str.indexOf(" : "));
			arr.add(temp);
			temp = "";
			str = "";
		}
		scanner.close();
		if(parent!=null) {
			Scanner scan = new Scanner(new File(".\\objects\\"+parent));
			temp = scan.nextLine();
			arr.add("previous tree : "+temp);
			scan.close();
		}
		else {
			arr.add("No earlier tree in repo");
		}
		
		Tree returned = new Tree(arr);
		return returned.getShaString();
		
		
		//System.out.println("TEMP: " + temp);
	}
	
    public String generateSHAfromString(String input) {
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
        } catch(Exception e) {
        	System.out.println(e.toString());
        	return null;
        }
    }
    
    public String getDate() {
    	Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(date);  
        return strDate;
        
    }
    
    public String createMySHA() {
    	return generateSHAfromString(pTree + summary);
    }
    
    public void writeFile() throws Exception {
    	String sha = ".\\objects\\" + createMySHA();
    	PrintWriter writer = new PrintWriter(sha);
		writer.println(pTree);
		
		if (parent == null)
			writer.println("null");
		if(parent!=null) {
				writer.println(parent);
				File f = new File(".\\objects\\"+parent);
				Scanner scanner = new Scanner(f);
				String temp = scanner.nextLine()+"\n";
				temp+=scanner.nextLine()+"\n";
				scanner.nextLine();
				temp+=createMySHA()+"\n";
				while(scanner.hasNext()) {
					temp+=scanner.nextLine()+"\n";
				}
				scanner.close();
				PrintWriter parentWriter = new PrintWriter(f);
				parentWriter.print(temp);
				parentWriter.close();
			}
		
		if(child ==null)
			writer.println("null");
		
//		writer.println(parent == null ? "null" : parent.pTree);
//		writer.println(child == null ? "null" : child.pTree);
		
		writer.println(author);
		writer.println(getDate());
		writer.println(summary);
		
		writer.close();
    }
    
    
}