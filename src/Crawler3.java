import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler3 {

    int x = 10, y = 15;
    static String mUrl = "http://www.hkbu.edu.hk/eng/main/index.jsp";
    ArrayList<String> mUrlPool = new ArrayList<String>(); // URL Pool
    ArrayList<String> mProcessedURLPool = new ArrayList<String>(); // Processed variable

    public Crawler3() {

        mUrlPool.add(mUrl); //the first time

        for (int i = 0; mProcessedURLPool.size() < y; i++) // loop for extracting URL and keywords to database
        {
            if (mUrlPool.size() > 0) {
                ThreadDemo th = new ThreadDemo(mUrlPool.get(0));
                th.start();

                mProcessedURLPool.add(mUrlPool.get(0));
                mUrlPool.remove(0);
                System.out.println("Iteration " + mProcessedURLPool.size() + ": " + mProcessedURLPool.get(i));
            }
        }
    }

//    private void break_page(File webpage) {
//
//        String line;
//        try {
//            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(webpage), "UTF8"));
//            boolean start = false;
//            boolean end = false;
//            boolean bigcomment = false;
//            while ((line = in.readLine()) != null) {
//                if (line.equals("<body>")) {
//                    start = true;
//                    line = in.readLine();
//                }
//                if (line.equals("</body>")) {
//                    end = true;
//                }
//
//                if (start == true && end == false) {
//
//
//                    if (line.contains("<") && line.contains(">")) {
//                        line = remove_htmltag(line);
//                    }
//
//                    System.out.println(line);
//                }
//            }
//
//            in.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }

//    private String remove_htmltag(String str) {
//        String line = str;
//        while (line.contains("<") && line.contains(">")) {
//            int a = line.indexOf("<");
//            int b = line.indexOf(">");
//            //TODO
//            StringBuffer s1 = new StringBuffer(line);
//            line = s1.delete(a, b + 1).toString();
//
//
//        }
//        return line;
//    }

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

//    private File get_page() throws IOException {
//
//        String fileName = "C:\\db\\temp.txt";
//        File file = new File(fileName);
//        URL link = new URL(mUrlPool.get(0));
//        BufferedReader in = new BufferedReader(new InputStreamReader(link.openStream(), "UTF-8"));
//
//        if (file.exists()) {
//            file.delete();
//            System.out.println(fileName + "cleared the old temp");
//        }
//
//        String inputLine;
//        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
//        BufferedWriter writer = new BufferedWriter(write);
//        while ((inputLine = in.readLine()) != null) {
//            // System.out.println(inputLine);
//
//            writer.write(inputLine);
//            writer.newLine();
//
//        }
//        writer.flush();
//        writer.close();
//
//        System.out.println("File created at " + file.getAbsolutePath());
//
//        in.close();
//        return file;
//    }

    public static void main(String[] args) {
        Crawler3 crawler3 = new Crawler3();
        System.out.println("crawler3.urlPool count = " + crawler3.mUrlPool.size() + " crawler3.urlPool = " + crawler3.mUrlPool + "mProcessedURLPool count = " + crawler3.mProcessedURLPool.size() + " \n mProcessedURLPool = " + crawler3.mProcessedURLPool);

    }

    class ThreadDemo extends Thread {
        //        private Thread t;
        private String url;

        ThreadDemo(String url) {
            this.url = url;
        }

        public void run() {
            crawling(url);
        }

//        public void start() {
//            System.out.println("Starting " + threadName);
//            if (t == null) {
//                t = new Thread(this, threadName);
//                t.start();
//            }
//        }

        private void crawling(String url) {
            create_folder("db", "C:\\");
//            File page = get_page();
            //break_page(page);
            getKeyword(url);
            getUrl(url);
        }

        public void getKeyword(String url) {
            Matcher tagMatch;
            Pattern tagPattern;
            String styleString = "(?i)<style.+?>.+?</style>"; // "style" tag pattern within a line
            String styleStartString = "(?i)<script.+?>"; // "style" tag pattern start
            String styleEndString = "(?i)</script>"; // "style" tag pattern end
            String scriptString = "(?i)<script.+?>.+?</script>"; // "script" tag pattern within a line
            String scriptStartString = "(?i)<script.+?>"; // "script" tag pattern start
            String scriptEndString = "(?i)</script>"; // "script" tag pattern end
            ArrayList<String> keyword = new ArrayList<String>();
//        ArrayList<String> stopList = new ArrayList<String>();
//        CreateStopList(stopList);
            try {
                // create URL connection
                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
                // reading URL
                String line = br.readLine();
                while (line != null) {
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
                        while (!styleMatch.find()) // skipping all lines between start and end "style" tag
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
                        while (!scriptMatch.find()) // skipping all lines between "script" tag start and end
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
//                            if (stopList.contains(i.toLowerCase()) == false) // check if word in stop list
//                            {
                                if (keyword.contains(i) == false) // check for duplicate
                                {
                                    keyword.add(i);
                                }
//                            }
                            }
                        }
                    }
                    line = br.readLine();
                }
            } catch (IOException e) {
                System.out.println("Error - Cannot access URL " + url);
            }

            System.out.println("Keyword: " + keyword.size()); // print out total keyword number in this URL

            for (String i : keyword) {
                System.out.println(" keyword = " + i + " url = " + url);
            }
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
                                if (mUrlPool.size() < x && mUrlPool.contains(link) == false && mProcessedURLPool.contains(link) == false) {
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
    }

}
