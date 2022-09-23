import java.io.IOException;
import java.util.ArrayList;
import java.io.*;

public class ABlobTester {
	public static void main(String[] args) throws Exception, IOException{
//		Index i = new Index();
//		i.init();
//		File testFile = new File("test.txt");
//		testFile.createNewFile();
//		ABlob.createBlob(testFile.getName());
//		System.out.println(testFile.getName());
//		i.add(testFile.getName());
////		i.remove(testFile);
		ArrayList<String> list = new ArrayList<>();
		list.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f");
		list.add("blob : 01d82591292494afd1602d175e165f94992f6f5f");
		list.add("blob : f1d82236ab908c86ed095023b1d2e6ddf78a6d83");
		list.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
		list.add("tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976");
		Tree t = new Tree(list);
	}
}

