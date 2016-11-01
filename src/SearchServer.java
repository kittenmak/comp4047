
/**
 * @author Group9 - Kitten & Michael & Tom
 * UI display for the search result
 */



import java.util.ArrayList;

import java.util.List;


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

		

			Item item = new Item();
			item = data.get(i);
			
			title = item.getTitle();
			url = item.getUrl();
			sponsor = item.getSponsor();


			
			if(title.equals("")){
				title="No Title";
			}
			System.out.print("<tr><td><a href=\"" + url + "\">" + title +"</a></td></tr>");
			System.out.print("<tr><td><font color = \"Green\">" + url + "</font></td></tr><br>");
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
