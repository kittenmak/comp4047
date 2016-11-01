import java.io.File;
import java.util.ArrayList;

/**
 * @author Group9 - Kitten & Michael & Tom
 * this class is for database clearance
 */

public class deleteDB {
	String mPath = "C:\\";
	String mFolderName = "db";
	String mFullPath = mPath + mFolderName; //database location
	File mFileDir = new File(mFullPath);

	public deleteDB() {


		for (int i = 97; i <= 122; i++) {
			char c = (char) i;
			String path = "C:\\db\\";
			String fullpath = path + c;
			System.out.println(fullpath);
			File folder = new File(fullpath);
			File[] listOfFiles = folder.listFiles();
			ArrayList<String> fileList = new ArrayList<String>();

			if (listOfFiles != null) {
				for (int j = 0; j < listOfFiles.length; j++) {
					if (listOfFiles[j].isFile()) {
						fileList.add(listOfFiles[j].getName());
					}
				}
			}

			while (!fileList.isEmpty()) {

				String ppath = fullpath + "\\" + fileList.get(0);

				File currentFile = new File(ppath);
				currentFile.delete();
				fileList.remove(0);
			}

			// System.out.println("File " + fileList);

		}
	}

	public static void main(String[] args) {
		deleteDB ddb = new deleteDB();

	}
}
