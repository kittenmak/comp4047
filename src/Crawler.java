import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * @author Group9 - Kitten & Michael & Tom
 * Crawling Algorithm
 */
public class Crawler {

	int x = 10, y = 100;
	String mPath = "C:\\";
	String mFolderName = "db";
	String mFullPath = mPath + mFolderName; // file saved to c:/db
	File mFileDir = new File(mFullPath);
	static String mUrl = "http://www.hkbu.edu.hk/eng/main/index.jsp";
	ArrayList<String> mUrlPool = new ArrayList<String>(); // URL Pool
	ArrayList<String> mProcessedURLPool = new ArrayList<String>(); // Processed
																	// variable

	public Crawler() {
		mUrlPool.add(mUrl); // the first time
		createFolder();

		// loop for extracting URL and keywords to database
		for (int i = 0; mProcessedURLPool.size() < y; i++) {
			if (mUrlPool.size() > 0) {
				System.out.println("mUrlPool.get(0) = " + mUrlPool.get(0) + " mUrlPool.size() = " + mUrlPool.size());
				crawling(mUrlPool.get(0));
				mProcessedURLPool.add(mUrlPool.get(0));
				mUrlPool.remove(0);
				System.out.println("Iteration " + mProcessedURLPool.size() + ": " + mProcessedURLPool.get(i));
			}
		}
	}

	private void crawling(String url) {

		if (!url.contains("facebook") && !url.contains("icloud") && !url.contains("apple")
				&& !url.contains("google.com/intl/") && !url.contains("google.com.hk/intl/")
				&& !url.contains("mail.google") && !url.contains("drive.google")) {
			getKeyword(url);
			getUrl(url);
		}
	}

	public void getKeyword(String url) {
		String t = "";
		Matcher tagMatch;
		Pattern tagPattern;

		String script = "(?i)<script.+?>.+?</script>"; // <script> pattern
		String scriptBegin = "(?i)<script.+?>"; // <script> pattern begins
		String scriptEnd = "(?i)</script>"; // <script> pattern end

		String style = "(?i)<style.+?>.+?</style>"; // "<style> tag pattern
		String styleBegin = "(?i)<script.+?>"; // <style> pattern begins
		String styleEnd = "(?i)</script>"; // <style> pattern end

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
			String line = br.readLine();
			while (line != null) {

				if (line.contains("<title>")) {

					int starttitlepos = line.indexOf("<title>");
					int endtitlepos = line.indexOf("</title>");
					line = line.substring(starttitlepos + 7, endtitlepos);

					t = line;

				}

				// check for "style" tag within 1 line
				tagPattern = Pattern.compile(style);
				tagMatch = tagPattern.matcher(line);
				if (tagMatch.find()) {
					line = br.readLine(); // skipping the "style" tag
					continue;
				}
				// check for "style" tags with several lines
				tagPattern = Pattern.compile(styleBegin);
				tagMatch = tagPattern.matcher(line);
				if (tagMatch.find()) // if "style" tag start finds
				{
					line = br.readLine();
					Matcher styleMatch;
					Pattern stylePattern;
					stylePattern = Pattern.compile(styleEnd);
					styleMatch = stylePattern.matcher(line);
					while (!styleMatch.find()) // skipping all lines between
												// start and end "style" tag
					{
						line = br.readLine();
						if (line != null) {
							stylePattern = Pattern.compile(styleEnd);
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
				tagPattern = Pattern.compile(script);
				tagMatch = tagPattern.matcher(line);
				if (tagMatch.find()) {
					line = br.readLine(); // skipping "script" tags
					continue;
				}
				// check for script tags with several lines
				tagPattern = Pattern.compile(scriptBegin);
				tagMatch = tagPattern.matcher(line);
				if (tagMatch.find()) // "script" tag start finds
				{
					line = br.readLine();
					Matcher scriptMatch;
					Pattern scriptPattern;
					scriptPattern = Pattern.compile(scriptEnd);
					scriptMatch = scriptPattern.matcher(line);
					while (!scriptMatch.find()) // skipping all lines between
												// "script" tag start and end
					{
						line = br.readLine();
						if (line != null) {
							scriptPattern = Pattern.compile(scriptEnd);
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
							if (stopList.contains(i.toLowerCase()) == false) {
								if (keyword.contains(i) == false) {
									Item obj = new Item();
									obj.keyword = i;
									obj.title = t;
									obj.setCount(1);
									item.add(obj);
									keyword.add(i);
								} else {
									for (int a = 0; a < item.size(); a++) {
										Item object = item.get(a);
										if (object.getKeyword().contains(i)) {
											item.remove(a);
											object.setCount(object.getCount() + 1);
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

		System.out.println("Keyword: " + item.size()); // print out total
														// keyword number in
														// this URL

		for (int i = 0; i < item.size(); i++) {
			Item obj = item.get(i);
			createFile(obj.getKeyword(), url, obj.getTitle(), obj.getCount());
			System.out.println(" keyword = " + i + " url = " + url);
		}
	}

	public void CreateStopList(ArrayList<String> stopList) {
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
		} else {

			System.out.println(mFileDir);
			mFileDir.delete();
			// }
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
		// else{
		if (!found) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(path + keyword + ".txt", true));
				bw.write("\r\n" + count + "," + title + "," + url + ",F");
				bw.close(); // You need to close it here only.
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(keyword + "added");
		}
	}

	public static void main(String[] args) {
		deleteDB ddb = new deleteDB();
		Crawler crawler2 = new Crawler();
		System.out.println("crawler2.urlPool count = " + crawler2.mUrlPool.size() + " crawler2.urlPool = "
				+ crawler2.mUrlPool + "mProcessedURLPool count = " + crawler2.mProcessedURLPool.size()
				+ " \n mProcessedURLPool = " + crawler2.mProcessedURLPool);

		sorting sort = new sorting();

	}
}
