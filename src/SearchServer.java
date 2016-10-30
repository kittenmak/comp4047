package www.se;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * CGI Test
 * <p/>
 * Created by YI Peipei on 8/19/2016.
 */
public class SearchServer {
	
	private static ArrayList<Item> data;
	static String keyword;
	static String url;
	
    public static void main(String[] args) {
    	
    	data = new ArrayList<Item>(10);
    	//for(int i = 0; i < query.size; i++){
    	Item item = new Item();
    	keyword = item.getTitle();
    	url = item.getUrl();
//    	item.getDesc();
//    	item.getSponsor();
//    	item.getPosition();
    	data.add(item);
    	System.out.print(data);
    	//}
        System.out.print("Content-type: text/html\n\n");
        System.out.print("<title>CGI Test from Java</title>\n");
        System.out.print("<style type=\"text/css\">");
        System.out.print("BODY {FONT-SIZE: 12px;FONT-FAMILY: Verdana, Arial, Helvetica, sans-serif;WIDTH: 60%; PADDING-LEFT: 25px;}");
        System.out.print("DIV.yahoo {PADDING-RIGHT: 3px; PADDING-LEFT: 3px; PADDING-BOTTOM: 3px; MARGIN: 3px; PADDING-TOP: 3px; TEXT-ALIGN: center}");
        System.out.print("DIV.yahoo A {BORDER-RIGHT: #fff 1px solid; PADDING-RIGHT: 5px; BORDER-TOP: #fff 1px solid; PADDING-LEFT: 5px; PADDING-BOTTOM: 2px; MARGIN: 2px; BORDER-LEFT: #fff 1px solid; COLOR: #000099; PADDING-TOP: 2px; BORDER-BOTTOM: #fff 1px solid; TEXT-DECORATION: underline}");
        System.out.print("DIV.yahoo A:hover {BORDER-RIGHT: #000099 1px solid; BORDER-TOP: #000099 1px solid; BORDER-LEFT: #000099 1px solid; COLOR: #000; BORDER-BOTTOM: #000099 1px solid}");
        System.out.print("DIV.yahoo A:active {BORDER-RIGHT: #000099 1px solid; BORDER-TOP: #000099 1px solid; BORDER-LEFT: #000099 1px solid; COLOR: #f00; BORDER-BOTTOM: #000099 1px solid}");
        System.out.print("DIV.yahoo SPAN.current {BORDER-RIGHT: #fff 1px solid; PADDING-RIGHT: 5px; BORDER-TOP: #fff 1px solid; PADDING-LEFT: 5px; FONT-WEIGHT: bold; PADDING-BOTTOM: 2px; MARGIN: 2px; BORDER-LEFT: #fff 1px solid; COLOR: #000; PADDING-TOP: 2px; BORDER-BOTTOM: #fff 1px solid; BACKGROUND-COLOR: #fff}");
        System.out.print("DIV.yahoo SPAN.disabled {BORDER-RIGHT: #eee 1px solid; PADDING-RIGHT: 5px; BORDER-TOP: #eee 1px solid; PADDING-LEFT: 5px; PADDING-BOTTOM: 2px; MARGIN: 2px; BORDER-LEFT: #eee 1px solid; COLOR: #ddd; PADDING-TOP: 2px; BORDER-BOTTOM: #eee 1px solid}");
        System.out.print("</style>");
        //for(int i = 0;i < 10;i++){
        System.out.print("<div>");
        System.out.print("<table style=\"width:100%\">\n");
        System.out.print("<tr><td><a href=\""+url+"\">"+keyword+"</a></td></tr>");
        System.out.print("<tr><td>"+url+"</td></tr>");
		System.out.print("<tr><td>Descriptions</td></tr>");
        System.out.print("</table>");
        System.out.print("</div>");
        //}
        System.out.print("<div class=\"yahoo\"><span class=\"disabled\"> < </span>");
        System.out.print("<span class=\"current\">1</span>");
        System.out.print("<a href=\"#?page=2\">2</a>");
        System.out.print("<a href=\"#?page=3\">3</a>");
        System.out.print("<a href=\"#?page=4\">4</a>");
        System.out.print("<a href=\"#?page=5\">5</a>");
        System.out.print("<a href=\"#?page=6\">6</a>");
        System.out.print("<a href=\"#?page=7\">7</a>");
        System.out.print("<a href=\"#?page=2\"> > </a></div>");
        System.out.print("Received query: " + Arrays.toString(args));
    }
}
