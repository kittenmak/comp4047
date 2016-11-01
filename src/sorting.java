import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

/**
 * @author Group9 - Kitten & Michael & Tom Ranking and Sorting Algorithm
 */

public class sorting {

	/**
	 * @throws IOException
	 * 
	 */

	public sorting() {
		makeSponsor();
		full_sorting();

	}

	private void makeSponsor() {

		ArrayList<String> sponsors = new ArrayList<String>();
		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("sponsor.txt"), "UTF8"));

			String line;
			

			while ((line = br.readLine()) != null) {
				// printing out each line in the file
				if (line.length() > 0) {
					sponsors.add(line);
				}

			}
		} catch (IOException e) {

			System.out.println("file not found");
		}

		for (int i = 97; i <= 122; i++) {
			char c = (char) i;
			String path = "C:\\db\\";
			String fullpath = path + c;
			System.out.println(fullpath);
			File folder = new File(fullpath);
			File[] listOfFiles = folder.listFiles();
			ArrayList<String> fileList = new ArrayList<String>();

			for (int j = 0; j < listOfFiles.length; j++) {
				if (listOfFiles[j].isFile()) {
					fileList.add(listOfFiles[j].getName());
				}
			}

			while (!fileList.isEmpty()) {

				String ppath = fullpath + "\\" + fileList.get(0);
				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ppath), "UTF8"));
					
					System.out.println("processing File: " + ppath);
					ArrayList<String> allLines = new ArrayList<String>();
					try {
						String line;

						while ((line = br.readLine()) != null) {
							// printing out each line in the file
							if (line.length() > 0) {
								for (int p = 0; p < sponsors.size(); p++) {
									if (line.contains(sponsors.get(p))) {
										int tmp = line.lastIndexOf(",");
										line = line.substring(0, tmp + 1);
										line = line.concat("T");

									}
								}

								allLines.add(line);
							}
						}

						PrintWriter writer = new PrintWriter(ppath, "UTF-8");
						while (!allLines.isEmpty()) {
							
							writer.println(allLines.get(0));
							allLines.remove(0);
						}

						writer.close();

					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException | UnsupportedEncodingException e) {
					System.out.println(e);
					e.printStackTrace();
				}

				fileList.remove(0);
			}

		}

	}

	private void full_sorting() {

		for (int i = 97; i <= 122; i++) {
			char c = (char) i;
			String path = "C:\\db\\";
			String fullpath = path + c;
			System.out.println(fullpath);
			File folder = new File(fullpath);
			File[] listOfFiles = folder.listFiles();
			ArrayList<String> fileList = new ArrayList<String>();

			for (int j = 0; j < listOfFiles.length; j++) {
				if (listOfFiles[j].isFile()) {
					fileList.add(listOfFiles[j].getName());
				}
			}

			while (!fileList.isEmpty()) {

				String ppath = fullpath + "\\" + fileList.get(0);
				try {
					
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ppath), "UTF8"));
					System.out.println("processing File: " + ppath);
					try {
						String line;

						ArrayList<String> trows = new ArrayList<String>();
						ArrayList<String> frows = new ArrayList<String>();

						while ((line = br.readLine()) != null) {

							if (line.length() > 0 && line.split(",")[line.split(",").length - 1].equals("T")) {
								trows.add(line);
							}
							if (line.length() > 0 && line.split(",")[line.split(",").length - 1].equals("F")) {
								frows.add(line);
							}
						}

						List<Integer> temp = new ArrayList<Integer>();
						for (int k = 0; k < trows.size(); k++) {
							temp.add(Integer.valueOf(trows.get(k).split(",")[0]));
						}

						int n = trows.size();
						int k;

						for (int m = n; m >= 0; m--) {
							for (int p = 0; p < n - 1; p++) {
								k = p + 1;
								if (temp.get(p) < temp.get(k)) {
									Collections.swap(trows, p, k);
									Collections.swap(temp, p, k);
								}

							}

						}

						List<Integer> temp2 = new ArrayList<Integer>();
						for (int k2 = 0; k2 < frows.size(); k2++) {
							temp2.add(Integer.valueOf(frows.get(k2).split(",")[0]));
						}

						int n2 = frows.size();
						int k2;

						for (int m2 = n2; m2 >= 0; m2--) {

							for (int p2 = 0; p2 < n2 - 1; p2++) {

								k2 = p2 + 1;

								if (temp2.get(p2) < temp2.get(k2)) {
									Collections.swap(frows, p2, k2);
									Collections.swap(temp2, p2, k2);
								}

							}

						}

						BufferedWriter bw = new BufferedWriter(new FileWriter(ppath, true));

						// PrintWriter writer = new PrintWriter(ppath, "BIG-5");
						// PrintWriter writer = new PrintWriter(ppath);
						while (!trows.isEmpty()) {
							bw.write(trows.get(0));
							// writer.println(trows.get(0));
							trows.remove(0);
						}
						while (!frows.isEmpty()) {
							bw.write(frows.get(0));
							frows.remove(0);
						}

						// writer.close();
						bw.close(); // You need to close it here only.
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					System.out.println(e);
					e.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				fileList.remove(0);
			}

		}

	}

	public static void main(String[] args) {
		sorting s = new sorting();
	}

}
