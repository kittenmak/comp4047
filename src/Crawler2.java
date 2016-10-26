import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 */

/**
 * @author Kitten
 *
 */
public class Crawler2 {

	ArrayList<String> urlPool = new ArrayList<String>();
	int x, y;
	static String mUrl =  "http://www.hkbu.edu.hk/eng/main/index.jsp";
	public Crawler2(String start_url, int X, int Y) {
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
		//break_page(page);
		getUrl();

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
	
	private void getUrl() {
		
        Matcher aMatch, hrefMatch;
        Pattern aPattern, hrefPattern;
        
        String aString = "(?i)<a([^>]+)>(.+?)</a>"; // "a" tag pattern
        String hrefString = "\\s*(?i)href\\s*=\\s*\"(([^\"]*)|'[^']*'|([^'\">\\s]+))\""; //"a href" pattern
        
        aPattern = Pattern.compile(aString);
        hrefPattern = Pattern.compile(hrefString);
        
        try
        {
            // create URL connection
            HttpURLConnection conn = (HttpURLConnection) new URL(mUrl).openConnection();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            // reading URL
            String line = br.readLine();
            while (line != null)
            {
                aMatch = aPattern.matcher(line); // match for "a" tag
                while (aMatch.find())
                {
                    String hrefText = aMatch.group(1); // the whole "a" string

                    hrefMatch = hrefPattern.matcher(hrefText);
                    while (hrefMatch.find())
                    {
                        String link = hrefMatch.group(1); // the value stored in "href"
                        if (link.startsWith("http://")) // extract it only if the URL starts with http://
                        {
                            // check if this URL exists in URL Pool or Processed URL Pool
                          //  if (URLPool.size() < x && URLPool.contains(link) == false && ProcessedURLPool.contains(link) == false)
                           // {
                                urlPool.add(link);
                           // }
                        }
                    }
                }
                line = br.readLine();
            }
        }
        catch (IOException e)
        {
            System.out.println("Error - Cannot access URL " + mUrl);
        }

	}

	
	
	private String remove_htmltag(String str){
		String line = str;
		while(line.contains("<") && line.contains(">")){
			int a = line.indexOf("<");
			int b = line.indexOf(">");
			//TODO
			StringBuffer s1 = new StringBuffer(line);
			line = s1.delete(a, b+1).toString();
			
		
		}
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
		//String web = "http://www.hkbu.edu.hk/eng/main/index.jsp";
		//String s = "http://ge.hkbu.edu.hk/ge-programme";
		Crawler2 attempt = new Crawler2(mUrl, 10, 100);
		System.out.println(attempt.urlPool);
		
	}

}
