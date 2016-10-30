import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Query {
	List<Item> result = new ArrayList<Item>(); // create return object
	
	String path = "C:\\db";

	public Query(String q) {
		String query = q;
		find(query);
		// TODO Auto-generated constructor stub
	}

	private List<Item> find(String query) {
		
		
		String word = query;
		char first = word.charAt(0);

		String fullpath = path + "\\" + first + "\\" + word + ".txt";

		try (BufferedReader br = new BufferedReader(new FileReader(fullpath))) {
			String line;
			while ((line = br.readLine()) != null) {
				
				if (!line.equals("")) {
					Item temp = new Item();
					//System.out.println(line);
					
					String [] part = line.split(",");
					
					temp.setKeyword(part[0]);
					temp.setUrl(part[1]);
					temp.setCount(Integer.parseInt(part[2]));
					result.add(temp);
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		

		return result;

	}

	public static void main(String[] args) {
		Query q1 = new Query("HKBU");
		
		System.out.println(q1.result);
	}

}
