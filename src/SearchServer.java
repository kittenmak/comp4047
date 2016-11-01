
//package www.se;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CGI Test
 * <p/>
 * Created by YI Peipei on 8/19/2016.
 */
public class SearchServer {

	private static List<Item> data = new ArrayList<Item>();
	static String title;
	static String url;
	static boolean sponsor;
	
	public SearchServer(String input){
		String s = input;
		
		html(s);
		
		
	}
	
	
	private void html(String s){
		
		String value = s;
		Query query = new Query(value);
		data = query.result;
		
//		System.out.println(data);
//		System.out.print(data);
		 
		System.out.print("Content-type: text/html\n\n");
		System.out.print("<title>CGI Test from Java</title>\n");
		System.out.print("<style type=\"text/css\">");
		System.out.print(
				"BODY {FONT-SIZE: 12px;FONT-FAMILY: Verdana, Arial, Helvetica, sans-serif;WIDTH: 60%; PADDING-LEFT: 25px;}");
		System.out.print("</style>");
		
		

		
		
		for (int i = 0; i <  data.size(); i++) {
			System.out.print("<BODY>");
			System.out.print("<div>");
			System.out.print("<table style=\"width:100%\">\n");

			
			
			//coding start

			Item item = new Item();
			item = data.get(i);
			
			title = item.getTitle();
			url = item.getUrl();
			sponsor = item.getSponsor();

			// Collections.sort(item, new Comparator<Item>() {
   //      	@Override
   //      	public int compare(Item item1, Item item2)
   //      	{

   //          	return  item1.getSponsor().compareTo(item2.getSponsor);
   //      	}
   //  		});
			//coding end
			
			
			System.out.print("<tr><td><a href=\"" + url + "\">" + title +"</a></td></tr>");
			System.out.print("<tr><td><font color = \"Green\">" + url + "</font></td></tr>");
			System.out.print("</table>");
			System.out.print("</div>");
			System.out.print("</BODY>");
		}
		System.out.print("Received query: " + s);
		
	}
	
	
	public static void main(String[] args) {

		SearchServer ss = new SearchServer(args[0]);
		
		
	}
}
