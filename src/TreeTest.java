import java.io.IOException;
import java.util.*;
public class TreeTest {
	public static void main(String[] args) throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		list.add("blob: a94a8fe5ccb19ba61c4c0873d391e987982fbbd3");
		list.add("tree : ab4d8d2a5f480a137067da17100271cd176607a1");
		Tree tree = new Tree(list);
	}
}
