import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

 


//import comp4047.deleteDB;

/**
 *
 */

/**
 * @author Kitten
 */
public class Crawler2 {

	int x = 10, y = 20;
	String mPath = "C:\\";
	String mFolderName = "db";
	String mFullPath = mPath + mFolderName;
	File mFileDir = new File(mFullPath);
	static String mUrl = "http://www.hkbu.edu.hk/eng/main/index.jsp";
	ArrayList<String> mUrlPool = new ArrayList<String>(); 
	ArrayList<String> mProcessedURLPool = new ArrayList<String>(); 

	public Crawler2() {

		
		
		
		mUrlPool.add(mUrl); 
		createFolder();

		for (int i = 0; mProcessedURLPool.size() < y; i++) 
		{
			if (mUrlPool.size() > 0) {
				System.out.println("mUrlPool.get(0) = " + mUrlPool.get(0) + " mUrlPool.size() = " + mUrlPool.size() );
				crawling(mUrlPool.get(0));
				mProcessedURLPool.add(mUrlPool.get(0));
				mUrlPool.remove(0);
				System.out.println("Iteration " + mProcessedURLPool.size() + ": " + mProcessedURLPool.get(i));
			}
		}
	}

	private void crawling(String url) {
		if(!url.contains("facebook") && !url.contains("icloud") && !url.contains("apple") && !url.contains("google.com/intl/") && !url.contains("google.com.hk/intl/") && !url.contains("mail.google") && !url.contains("drive.google")) {
			getKeyword(url);
			getUrl(url);
		}
	}

	public void getKeyword(String url) {
		String title ="";
		Matcher tagMatcher;
		Pattern tagPattern;
		String styleTag = "(?i)<style.+?>.+?</style>"; // "style" tag pattern
															// within a line
		String styleTagHead = "(?i)<script.+?>"; // "style" tag pattern
														// start
		String styleTagTail = "(?i)</script>"; // "style" tag pattern end

		String scriptTag = "(?i)<script.+?>.+?</script>"; // "script" tag
																// pattern
																// within a line
		String scriptTagHead = "(?i)<script.+?>"; // "script" tag pattern
														// start
		String scriptTagTail = "(?i)</script>"; // "script" tag pattern end

		ArrayList<Item> item = new ArrayList<Item>();
		ArrayList<String> keyword = new ArrayList<String>();

		 ArrayList<String> stopList = new ArrayList<String>();
		 CreateStopList(stopList);
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			InputStream is = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
			Boolean foundtitle = true;
			String line = br.readLine();
			while (line != null) {
				
				if(line.contains("<title>")){

					int starttitlepos = line.indexOf("<title>");
					int endtitlepos = line.indexOf("</title>");
					line = line.substring(starttitlepos+7, endtitlepos);

					title = line;
					
				}
				
				tagPattern = Pattern.compile(styleTag);
				tagMatcher = tagPattern.matcher(line);
				if (tagMatcher.find()) {
					line = br.readLine(); 
					continue;
				}

				tagPattern = Pattern.compile(styleTagHead);
				tagMatcher = tagPattern.matcher(line);
				if (tagMatcher.find()) 
				{
					line = br.readLine();
					Matcher styleMatch;
					Pattern stylePattern;
					stylePattern = Pattern.compile(styleTagTail);
					styleMatch = stylePattern.matcher(line);
					while (!styleMatch.find()) 
					{
						line = br.readLine();
						if (line != null) {
							stylePattern = Pattern.compile(styleTagTail);
							styleMatch = stylePattern.matcher(line);
						} else
							break;
					}
					if (line != null) {
						line = br.readLine();
						continue;
					} else
						break;
				}

				tagPattern = Pattern.compile(scriptTag);
				tagMatcher = tagPattern.matcher(line);
				if (tagMatcher.find()) {
					line = br.readLine();
					continue;
				}

				tagPattern = Pattern.compile(scriptTagHead);
				tagMatcher = tagPattern.matcher(line);
				if (tagMatcher.find()) 
				{
					line = br.readLine();
					Matcher scriptMatch;
					Pattern scriptPattern;
					scriptPattern = Pattern.compile(scriptTagTail);
					scriptMatch = scriptPattern.matcher(line);
					while (!scriptMatch.find()) 
					{
						line = br.readLine();
						if (line != null) {
							scriptPattern = Pattern.compile(scriptTagTail);
							scriptMatch = scriptPattern.matcher(line);
						} else
							break;
					}
					if (line != null) {
						line = br.readLine();
						continue;
					} else
						break;
				}

				String newContent = line.replaceAll("<[^>].+?>", " ");
				String tokens[] = newContent.split(" ");
				for (String i : tokens) {
					if (i.matches("[a-zA-Z]+")) 
					{
						if (i.length() >= 3) 
						{
							 if (stopList.contains(i.toLowerCase()) == false)
							 {
								 if(keyword.contains(i) == false){
									 Item obj = new Item();
									 obj.keyword = i;
									 obj.title = title;
									 obj.setCount(1);
									 item.add(obj);
									 keyword.add(i);
								 }
								 else{
									 for(int a=0; a<item.size(); a++){
										 Item object = item.get(a);
										 if(object.getKeyword().contains(i)){
											 item.remove(a);
											 object.setCount(object.getCount()+1);
											 item.add(object);
										 }
									 }
								 }

							 }
						}
					}
				}
				line = br.readLine();
			}
		} catch (IOException e) {
			System.out.println("Error - Cannot access URL " + url);
		}

		System.out.println("Keyword: " + item.size());

		for(int i = 0; i<item.size(); i++){
			Item obj = item.get(i);
			createFile(obj.getKeyword(), url, obj.getTitle(), obj.getCount());
			System.out.println(" keyword = " + i + " url = " + url);
		}
	}

	 public void CreateStopList(ArrayList<String> stopList)
	 {
	 stopList.add("and");
	 stopList.add("the");
	 stopList.add("for");
	 stopList.add("did");
	 stopList.add("does");
	 stopList.add("are");
	 stopList.add("was");
	 stopList.add("were");
	 stopList.add("has");
	 stopList.add("have");
	 stopList.add("had");
	 stopList.add("that");
	 stopList.add("this");
	 stopList.add("these");
	 stopList.add("which");
	 stopList.add("whose");
	 stopList.add("who");
	 stopList.add("whom");
	 stopList.add("what");
	 stopList.add("why");
	 stopList.add("she");
	 stopList.add("they");
	 stopList.add("them");
	 }

	private void getUrl(String url) {

		Matcher matchATag, matchHrefTag;
		Pattern patternATag, atternHrefTag;

		String getATag = "(?i)<a([^>]+)>(.+?)</a>";
		String getHrefTag = "\\s*(?i)href\\s*=\\s*\"(([^\"]*)|'[^']*'|([^'\">\\s]+))\"";

		patternATag = Pattern.compile(getATag);
		atternHrefTag = Pattern.compile(getHrefTag);

		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			InputStream is = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));

			String line = br.readLine();
			while (line != null) {
				matchATag = patternATag.matcher(line);
				while (matchATag.find()) {
					String hrefText = matchATag.group(1);

					matchHrefTag = atternHrefTag.matcher(hrefText);
					while (matchHrefTag.find()) {
						String link = matchHrefTag.group(1);
						if (link.startsWith("http://") || link.startsWith("https://")) {
							if (mUrlPool.size() < x && mUrlPool.contains(link) == false
									&& mProcessedURLPool.contains(link) == false) {
								mUrlPool.add(link);
							}
						}
					}
				}
				line = br.readLine();
			}
		} catch (IOException e) {
			System.out.println("Error - Cannot access URL " + mUrl);
		}

	}


	private void createFolder() {
		// if the directory does not exist, create it
		if (!mFileDir.exists()) {
			System.out.println("creating directory: " + mFullPath);
			boolean result = false;
			try {
				result = mFileDir.mkdir();
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
				List<String> alphabet = new ArrayList<String>();
				for (int i = 0; i < 26; i++) {
					alphabet.add(String.valueOf((char) ('a' + i)));
				}
				final int depth = 1;
				mkDirs(mFileDir, alphabet, depth);
			}
		}
		else{
				System.out.println(mFileDir);
			    mFileDir.delete();
		}
	}

	public void mkDirs(File root, List<String> dirs, int depth) {
		if (depth == 0)
			return;
		for (String s : dirs) {
			File subdir = new File(root, s);
			subdir.mkdir();
			mkDirs(subdir, dirs, depth - 1);
		}
	}

	public void createFile(String keyword, String url, String title, int count) {
		char c = keyword.charAt(0);
		String path = mFullPath + "/" + c + "/";
		String line;
		int linecount = 0;
		File file = new File(path + keyword + ".txt");
		boolean found = false;
		if (file.exists() && !file.isDirectory()) {
			BufferedReader bf = null;
			try {
				bf = new BufferedReader(new FileReader(path + keyword + ".txt"));
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
			}
			try {
				while ((line = bf.readLine()) != null) {

					linecount++;
					int indexfound = line.indexOf(count + "," + title + "," + url + ",F");
					if (indexfound > -1) {
						System.out.println("keyword exist on line " + linecount);
						found = true;
						break;
					}
				}
				bf.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (!found) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(path + keyword + ".txt", true));
				bw.write("\r\n" + count + "," + title + "," + url + ",F");
				bw.close(); 
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(keyword + "added");
		}
	}


	public static void main(String[] args) {
		//deleteDB ddb = new deleteDB();
		Crawler2 crawler2 = new Crawler2();
		System.out.println("crawler2.urlPool count = " + crawler2.mUrlPool.size() + " crawler2.urlPool = "
				+ crawler2.mUrlPool + "mProcessedURLPool count = " + crawler2.mProcessedURLPool.size()
				+ " \n mProcessedURLPool = " + crawler2.mProcessedURLPool);

		sorting sort = new sorting();
		
		
	}
}
