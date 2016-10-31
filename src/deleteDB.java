package comp4047;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class deleteDB {
	String mPath = "C:\\";
	// "/Users/michaelleung/Documents/BU/Degree/Year4/group_9/src/";
	String mFolderName = "db";
	String mFullPath = mPath + mFolderName;
	File mFileDir = new File(mFullPath);

	public deleteDB() {
		//

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
