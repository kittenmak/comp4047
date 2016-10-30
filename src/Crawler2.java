import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */

/**
 * @author Kitten
 */
public class Crawler2 {

	int x = 10, y = 20;
	String mPath = "C:\\";
	// "/Users/michaelleung/Documents/BU/Degree/Year4/group_9/src/";
	String mFolderName = "db";
	String mFullPath = mPath + mFolderName;
	File mFileDir = new File(mFullPath);
	static String mUrl = "http://www.hkbu.edu.hk/eng/main/index.jsp";
	ArrayList<String> mUrlPool = new ArrayList<String>(); // URL Pool
	ArrayList<String> mProcessedURLPool = new ArrayList<String>(); // Processed
																	// variable

	public Crawler2() {

		mUrlPool.add(mUrl); // the first time
		createFolder();

		for (int i = 0; mProcessedURLPool.size() < y; i++) // loop for
															// extracting URL
															// and keywords to
															// database
		{
			if (mUrlPool.size() > 0) {
				crawling(mUrlPool.get(0));
				mProcessedURLPool.add(mUrlPool.get(0));
				mUrlPool.remove(0);
				System.out.println("Iteration " + mProcessedURLPool.size() + ": " + mProcessedURLPool.get(i));
			}
		}
	}

	private void crawling(String url) {
		// "C:\\");
		// File page = get_page();
		// break_page(page);

		getKeyword(url);
		getUrl(url);
	}

	// private void break_page(File webpage) {
	//
	// String line;
	// try {
	// BufferedReader in = new BufferedReader(new InputStreamReader(new
	// FileInputStream(webpage), "UTF8"));
	// boolean start = false;
	// boolean end = false;
	// boolean bigcomment = false;
	// while ((line = in.readLine()) != null) {
	// if (line.equals("<body>")) {
	// start = true;
	// line = in.readLine();
	// }
	// if (line.equals("</body>")) {
	// end = true;
	// }
	//
	// if (start == true && end == false) {
	//
	//
	// if (line.contains("<") && line.contains(">")) {
	// line = remove_htmltag(line);
	// }
	//
	// System.out.println(line);
	// }
	// }
	//
	// in.close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }

	public void getKeyword(String url) {
		String t ="";
		Matcher tagMatch;
		Pattern tagPattern;
		String styleString = "(?i)<style.+?>.+?</style>"; // "style" tag pattern
															// within a line
		String styleStartString = "(?i)<script.+?>"; // "style" tag pattern
														// start
		String styleEndString = "(?i)</script>"; // "style" tag pattern end

		String scriptString = "(?i)<script.+?>.+?</script>"; // "script" tag
																// pattern
																// within a line
		String scriptStartString = "(?i)<script.+?>"; // "script" tag pattern
														// start
		String scriptEndString = "(?i)</script>"; // "script" tag pattern end

		ArrayList<Item> item = new ArrayList<Item>();
		ArrayList<String> keyword = new ArrayList<String>();

		 ArrayList<String> stopList = new ArrayList<String>();
		 CreateStopList(stopList);
		try {
			// create URL connection
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			InputStream is = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
			// reading URL
			Boolean foundtitle = true;
			String line = br.readLine();
			while (line != null) {
				
				if(line.contains("<title>")){
					
					line = line.replaceAll("<title>", "");
					line = line.replaceAll("</title>", "");
					while(line.charAt(0)==' '){
						line=line.substring(1, line.length());
					}
					while(line.charAt(0)=='	'){
						line=line.substring(1, line.length());
					}
					t = line;
					
				}
				
//				System.out.println(t);
				
				
				// check for "style" tag within 1 line
				tagPattern = Pattern.compile(styleString);
				tagMatch = tagPattern.matcher(line);
				if (tagMatch.find()) {
					line = br.readLine(); // skipping the "style" tag
					continue;
				}
				// check for "style" tags with several lines
				tagPattern = Pattern.compile(styleStartString);
				tagMatch = tagPattern.matcher(line);
				if (tagMatch.find()) // if "style" tag start finds
				{
					line = br.readLine();
					Matcher styleMatch;
					Pattern stylePattern;
					stylePattern = Pattern.compile(styleEndString);
					styleMatch = stylePattern.matcher(line);
					while (!styleMatch.find()) // skipping all lines between
												// start and end "style" tag
					{
						line = br.readLine();
						if (line != null) {
							stylePattern = Pattern.compile(styleEndString);
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
				// check for script tags within a line
				tagPattern = Pattern.compile(scriptString);
				tagMatch = tagPattern.matcher(line);
				if (tagMatch.find()) {
					line = br.readLine(); // skipping "script" tags
					continue;
				}
				// check for script tags with several lines
				tagPattern = Pattern.compile(scriptStartString);
				tagMatch = tagPattern.matcher(line);
				if (tagMatch.find()) // "script" tag start finds
				{
					line = br.readLine();
					Matcher scriptMatch;
					Pattern scriptPattern;
					scriptPattern = Pattern.compile(scriptEndString);
					scriptMatch = scriptPattern.matcher(line);
					while (!scriptMatch.find()) // skipping all lines between
												// "script" tag start and end
					{
						line = br.readLine();
						if (line != null) {
							scriptPattern = Pattern.compile(scriptEndString);
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
				// check for other tags
				String newContent = line.replaceAll("<[^>].+?>", " ");
				String tokens[] = newContent.split(" ");
				for (String i : tokens) {
					if (i.matches("[a-zA-Z]+")) // check for alphabetic word
					{
						if (i.length() >= 3) // check for at least alphabets
						{
							 if (stopList.contains(i.toLowerCase()) == false)
							 {
								 if(keyword.contains(i) == false){
									 Item obj = new Item();
									 obj.keyword = i;
									 obj.title = t;
									 obj.setCount(1);
									 item.add(obj);
									 keyword.add(i);
								 }
								 else{
									 for(int a=0; a<item.size(); a++){
										 Item object = item.get(a);
										 if(object.getKeyword().contains(i)){
											 item.remove(a);
											 //									System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
											 object.setCount(object.getCount()+1);
											 item.add(object);
										 }
									 }
								 }

//							if (item.contains(i) == false) // check for duplicate
//							{
//								Item obj = new Item();
//								obj.keyword = i;
//								obj.title = t;
//								obj.setCount(1);
//								item.add(obj);
//							}




//							else{
////								for(int j=0; j<item.size(); j++){
////									Item item1 = item.get(j);
////									if(item1.getKeyword().equals(i)){
////										item1.setCount(item1.getCount() + 1);
////									}
////								}
//								System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//								System.out.println("duplicate word = "+ i);
//							}
							 }
						}
					}
				}
				line = br.readLine();
			}
		} catch (IOException e) {
			System.out.println("Error - Cannot access URL " + url);
		}

		System.out.println("Keyword: " + item.size()); // print out total
															// keyword number in
															// this URL

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
						if (link.startsWith("http://")) {
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

	// private String remove_htmltag(String str) {
	// String line = str;
	// while (line.contains("<") && line.contains(">")) {
	// int a = line.indexOf("<");
	// int b = line.indexOf(">");
	// //TODO
	// StringBuffer s1 = new StringBuffer(line);
	// line = s1.delete(a, b + 1).toString();
	//
	//
	// }
	// return line;
	// }

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
		// try{
		// BufferedWriter output = null;
		// File file = new File(path + keyword + ".txt");
		// if(file.exists() && !file.isDirectory()) {
		// output = new BufferedWriter(new FileWriter(file));
		// output.newLine();
		// output.write(keyword + "," + url);
		// output.close();
		// }
		// else{
		// output = new BufferedWriter(new FileWriter(file));
		// output.write(keyword + "," + url);
		// output.close();
		// System.out.println("File has been written");
		// }
		// }catch(Exception e){
		// System.out.println("Could not create file");
		// }

		boolean found = false;
		if (file.exists() && !file.isDirectory()) {
			BufferedReader bf = null;
			try {
				bf = new BufferedReader(new FileReader(path + keyword + ".txt"));
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				while ((line = bf.readLine()) != null) {

					linecount++;
					int indexfound = line.indexOf(title + "," + url + "," + count);
					if (indexfound > -1) {
						System.out.println("keyword exist on line " + linecount);
						found = true;
						break;
					}
				}
				bf.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// else{
		if (!found) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(path + keyword + ".txt", true));
				bw.write("\r\n" + title + "," + url +  "," + count);
				bw.close(); // You need to close it here only.
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(keyword + "added");
		}
		// }
	}

	// private File get_page() throws IOException {
	//
	// String fileName = "C:\\db\\temp.txt";
	// File file = new File(fileName);
	// URL link = new URL(mUrlPool.get(0));
	// BufferedReader in = new BufferedReader(new
	// InputStreamReader(link.openStream(), "UTF-8"));
	//
	// if (file.exists()) {
	// file.delete();
	// System.out.println(fileName + "cleared the old temp");
	// }
	//
	// String inputLine;
	// OutputStreamWriter write = new OutputStreamWriter(new
	// FileOutputStream(fileName), "UTF-8");
	// BufferedWriter writer = new BufferedWriter(write);
	// while ((inputLine = in.readLine()) != null) {
	// // System.out.println(inputLine);
	//
	// writer.write(inputLine);
	// writer.newLine();
	//
	// }
	// writer.flush();
	// writer.close();
	//
	// System.out.println("File created at " + file.getAbsolutePath());
	//
	// in.close();
	// return file;
	// }

	public static void main(String[] args) {
		Crawler2 crawler2 = new Crawler2();
		System.out.println("crawler2.urlPool count = " + crawler2.mUrlPool.size() + " crawler2.urlPool = "
				+ crawler2.mUrlPool + "mProcessedURLPool count = " + crawler2.mProcessedURLPool.size()
				+ " \n mProcessedURLPool = " + crawler2.mProcessedURLPool);

	}
}
