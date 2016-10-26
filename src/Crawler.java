import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Kitten
 *
 */
public class Crawler {

	ArrayList<String> urlPool = new ArrayList<String>();
	int x, y;

	public Crawler(String start_url, int X, int Y) {
		urlPool.add(start_url);
		this.x = X;
		this.y = Y;

		try {
			create_folder("db", "C:\\");
			crawl();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void crawl() throws IOException {
		File page = get_page();
		break_page(page);

	}

	private void break_page(File webpage) {

		String line;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(webpage), "UTF8"));
			boolean start = false;
			boolean end = false;
			boolean bigcomment = false;
			while ((line = in.readLine()) != null) {
				if (line.equals("<body>")) {
					start = true;
					line = in.readLine();
				}
				if (line.equals("</body>")) {
					end = true;
				}

				if (start == true && end == false) {

					if(bigcomment==true){
						if(line.contains("-->")){
							bigcomment=false;
						}
						line="";
					}
					if(line.contains("<!--")){
						if(!line.contains("-->")){
						bigcomment = true;
						line = "";
						}
						else{ 
							line = remove_basiccomment(line);
						}
					}
					if (line.contains("<li>") || line.contains("</li>")) {
						line = remove_li(line);
					}
					if (line.contains("<li")){
						line = remove_bigli(line);
					}
					if (line.contains("ul") || line.contains("</ul>")) {
						line = remove_basicul(line);
					}
					if(line.contains("<ul")){
						line= remove_bigul(line);
					}
					if (line.contains("</a>")){
						line = remove_basica(line);
					}
					if (line.contains("<a href")){
						//System.out.println("hi");
						//line = remove_biga(line);
					}
					if (line.contains("<div") || line.contains("</div>")) {
						line = remove_basicdiv(line);
					}
//					if (line.contains("<!--") && line.contains("-->")) {
//						line = remove_basiccomment(line);
//					}

					if(line.contains("<div")){
						line = remove_bigdiv(line);
					}
					if(line.contains("<p>")||line.contains("</p>")){
						line = remove_p(line);
					}
					if(line.contains("<p")){
						line = remove_bigp(line);
					}				
					if(line.contains("<span>")||line.contains("</span>")){
						line = remove_span(line);
					}
					
					if(line.contains("<span")){
						line = remove_bigspan(line);
					}
					
					if(line.contains("<iframe")){
						line = remove_bigiframe(line);
					}
					
					if(line.contains("<iframe>")||line.contains("</iframe>")){
						line = remove_iframe(line);
					}
					
					if(line.contains("<img")){
						line = remove_img(line);
					}
					
					if(line.contains("<")&&line.contains(">")){
						line = remove_htmltag(line);
					}
					
					System.out.println(line);
				}
			}

			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String remove_li(String str) {
		String line = str;
		line = line.replaceAll("<li>", "");
		line = line.replaceAll("</li>", "");
		return line;
	}

	private String remove_basicul(String str) {
		String line = str;
		line = line.replaceAll("<ul>", "");
		line = line.replaceAll("</ul>", "");
		return line;
	}

	private String remove_basica(String str) {
		String line = str;
		line = line.replaceAll("<a>", "");
		line = line.replaceAll("</a>", "");
		return line;
	}
	
	private String remove_biga(String str){
		String line = str;
		while(line.contains("<a href")){
		int a = line.indexOf("<a href=");
		int b = line.indexOf(">", a);
		
		StringBuffer s1 = new StringBuffer(line);
		line = s1.delete(a, b + 1).toString();
		//System.out.print("done");
		}
		
		return line;
	}
	
	private String remove_htmltag(String str){
		String line = str;
		while(line.contains("<") && line.contains(">")){
			int a = line.indexOf("<");
			int b = line.indexOf(">");
			
			StringBuffer s1 = new StringBuffer(line);
			line = s1.delete(a, b+1).toString();
			
		
		}
		return line;
	}
	
	private String remove_basicdiv(String str) {
		String line = str;
		// int a = line.indexOf("<div");
		// int b = line.indexOf(">");
		// line = line.substring(b+1);
		line = line.replaceAll("<div>", "");
		line = line.replaceAll("</div>", "");

		
		return line;
	}

	private String remove_basiccomment(String str) {
		String line = str;

		int a = line.indexOf("<!--");
		int b = line.indexOf("-->");
		StringBuffer s1 = new StringBuffer(line);
		line = s1.delete(a, b + 3).toString();
		// line = line.substring(a, b);

		return line;
	}

	private String remove_bigdiv(String str) {
		String line = str;

		int a = line.indexOf("<div");
		int b = line.indexOf(">", a);
		StringBuffer s1 = new StringBuffer(line);
		line = s1.delete(a, b + 1).toString();
		return line;
	}

	private String remove_p(String str) {
		String line = str;
		line = line.replaceAll("<p>", "");
		line = line.replaceAll("</p>", "");
		return line;
	}

	private String remove_bigp(String str) {
		String line = str;

		int a = line.indexOf("<p");
		int b = line.indexOf(">", a);
		StringBuffer s1 = new StringBuffer(line);
		line = s1.delete(a, b + 1).toString();
		// line = line.replaceAll("</p>", "");
		return line;
	}

	private String remove_bigul(String str) {
		String line = str;

		int a = line.indexOf("<ul");
		int b = line.indexOf(">", a);
		StringBuffer s1 = new StringBuffer(line);
		line = s1.delete(a, b + 1).toString();
		return line;

	}
	
	private String remove_bigli(String str) {
		String line = str;

		int a = line.indexOf("<li");
		int b = line.indexOf(">", a);
		StringBuffer s1 = new StringBuffer(line);
		line = s1.delete(a, b + 1).toString();
		return line;

	}

	private String remove_img(String str) {
		String line = str;
		int a = line.indexOf("<img");
		int b = line.indexOf(">", a);
		StringBuffer s1 = new StringBuffer(line);
		line = s1.delete(a, b + 1).toString();
		return line;
	}
	
	private String remove_span(String str) {
		String line = str;
		line = line.replaceAll("<span>", "");
		line = line.replaceAll("</span>", "");
		return line;
	}

	private String remove_bigspan(String str) {
		String line = str;

		int a = line.indexOf("<span");
		int b = line.indexOf(">", a);
		StringBuffer s1 = new StringBuffer(line);
		line = s1.delete(a, b + 1).toString();

		return line;

	}

	private String remove_iframe(String str){
		String line = str;
		line = line.replaceAll("<iframe>", "");
		line = line.replaceAll("</iframe>", "");
		return line;
	}
	private String remove_bigiframe(String str) {
		String line = str;

		int a = line.indexOf("<iframe");
		int b = line.indexOf(">", a);
		StringBuffer s1 = new StringBuffer(line);
		line = s1.delete(a, b + 1).toString();
		return line;
	}

	private void create_folder(String folderName, String path) {

		String fullpath = path + folderName;
		File theDir = new File(fullpath);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + fullpath);
			boolean result = false;

			try {
				theDir.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}

	}

	private File get_page() throws IOException {

		String fileName = "C:\\db\\temp.txt";
		File file = new File(fileName);
		URL link = new URL(urlPool.get(0));
		BufferedReader in = new BufferedReader(new InputStreamReader(link.openStream(), "UTF-8"));

		if (file.exists()) {
			file.delete();
			System.out.println(fileName + "cleared the old temp");
		}

		String inputLine;
		OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
		BufferedWriter writer = new BufferedWriter(write);
		while ((inputLine = in.readLine()) != null) {
			// System.out.println(inputLine);

			writer.write(inputLine);
			writer.newLine();

		}
		writer.flush();
		writer.close();

		System.out.println("File created at " + file.getAbsolutePath());

		in.close();
		return file;
	}

	public static void main(String[] args) {
		String web = "http://www.hkbu.edu.hk/eng/main/index.jsp";
		String s = "http://ge.hkbu.edu.hk/ge-programme";
		Crawler attempt = new Crawler(web, 10, 100);
	}

}
