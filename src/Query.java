import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Group9 - Kitten & Michael & Tom
 * This program is for handling the query process after inputting the keyword
 */

public class Query {
	List<Item> result = new ArrayList<Item>(); // create return object
	
	String path = "C:\\db";

	public Query(String q) {
		String query = q;
		find(query);
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
					
					temp.setTitle(part[1]);
					temp.setUrl(part[2]);
					//temp.setCount(Integer.parseInt(part[0]));
					if(part[3].equals("F")){
						temp.setSponsor(false);
					}else{
						temp.setSponsor(true);
					}
					result.add(temp);
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		

		return result;

	}

	public static void main(String[] args) {
		Query q1 = new Query(args[0]);
		
		System.out.println(q1.result);
	}

}
