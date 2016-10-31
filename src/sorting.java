import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 
 */

/**
 * @author Kitten
 *
 */
public class sorting {

	/**
	 * @throws IOException
	 * 
	 */

	public sorting() {
		// TODO Auto-generated constructor stub
		normal_sorting();
		makeSponsor();
	}

	private void normal_sorting() {

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
					BufferedReader br = new BufferedReader(new FileReader(ppath));
					System.out.println("processing File: " + ppath);
					try {
						String line;

						ArrayList<String> rows = new ArrayList<String>();
						while ((line = br.readLine()) != null) {
							// printing out each line in the file
							if (line.length() > 0) {

								rows.add(line);
							}

						}
						// System.out.println(rows);
						List<Integer> temp = new ArrayList<Integer>();
						for (int k = 0; k < rows.size(); k++) {
							temp.add(Integer.valueOf(rows.get(k).split(",")[0]));
						}

						int n = rows.size();
						int k;

						for (int m = n; m >= 0; m--) {
							for (int p = 0; p < n - 1; p++) {
								k = p + 1;
								if (temp.get(p) < temp.get(k)) {
									Collections.swap(rows, p, k);
									Collections.swap(temp, p, k);
								}

							}

						}
						System.out.println(temp);
						System.out.println(rows);

						PrintWriter writer = new PrintWriter(ppath, "UTF-8");
						while (!rows.isEmpty()) {
							writer.println(rows.get(0));
							rows.remove(0);
						}

						writer.close();
						// for (int p = 1; p < temp.size(); p++) {
						// previous = temp.get(p - 1);
						// current = temp.get(p);
						// while (current > previous) {
						// Collections.swap(rows, p - 1, p);
						// Collections.swap(temp, p - 1, p);
						// System.out.println(current);
						// System.out.println(previous);
						// previous = temp.get(p - 1);
						// current = temp.get(p);
						//
						// }
						//
						// }

					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					System.out.println(e);
					e.printStackTrace();
				}

				fileList.remove(0);
			}

			System.out.println("File " + fileList);

		}

	}

	private void makeSponsor() {

		// System.out.println("processing File: " + ppat);
		ArrayList<String> sponsors = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("sponsor.txt"));
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
					BufferedReader br = new BufferedReader(new FileReader(ppath));
					System.out.println("processing File: " + ppath);
					ArrayList<String> alllines = new ArrayList<String>();
					try {
						String line;

						ArrayList<String> rows = new ArrayList<String>();
						while ((line = br.readLine()) != null) {
							// printing out each line in the file
							if (line.length() > 0) {

//								rows.add(line);
//								
//								for (String a : sponsors) {
//									System.out.println(a);
//									if (line.contains(a)) {
//										//System.out.println("Bingo!!!!");
//										int tmp = line.lastIndexOf(",");
//										// System.out.println(tmp);
//										line = line.substring(0, tmp + 1);
//										// System.out.println(line);
//										line = line.concat("T");
//										
//										//System.out.println(alllines);
//									}
//								}
								
								
								for(int p=0; p<sponsors.size();p++){
									if(line.contains(sponsors.get(p))){
										int tmp = line.lastIndexOf(",");
//										// System.out.println(tmp);
										line = line.substring(0, tmp + 1);
//										// System.out.println(line);
										line = line.concat("T");
										
									}
								}
								
								
								
								
								alllines.add(line);
								
							}

						}

						 PrintWriter writer = new PrintWriter(ppath, "UTF-8");
						 while (!alllines.isEmpty()) {
						 writer.println(alllines.get(0));
						 alllines.remove(0);
						 }
						
						 writer.close();

					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					System.out.println(e);
					e.printStackTrace();
				}

				fileList.remove(0);
			}

		}

	}

	public static void main(String[] args) {
		sorting s = new sorting();

	}

}
