package ResumeMatcher;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.helper.StringUtil;

import JobScraper.Scraper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import ResumeMatcher.AbstractPriorityQueue.Item;

public class Searcher {

	public static void scanFileToFormTST(String fileName, TST<Integer> tst, String scan) throws IOException {
		StringBuffer sb = new StringBuffer();
		FileReader file = new FileReader(fileName);
		BufferedReader br = new BufferedReader(file);
		String line = null;
		while ((line = br.readLine()) != null) {
			sb.append(line).append(" ");
		}
		br.close();
		String article = sb.toString();
		String[] str = scan.split(" ");
		String[] st;
		if (str.length != 1) {
			st = article.split(" ,(?<!" + str[0] + ") | (?!" + str[1] + ")");
		} else {
			st = article.split("[ ,\\n\\s]");
		}
		for (int i = 0; i < st.length; i++) {
			String word = st[i]; // return string until next token;
			if (!StringUtil.isBlank(word)) {
				if (tst.contains(word)) {
					int count = tst.get(word);
					tst.put(word, count + 1);
				} else {
					System.out.println(word);
					if (word.equalsIgnoreCase(scan)) {
						System.out.println("yes " + word);
					}
					tst.put(word, 1);
				}
			}
		}
		// System.out.println(fileName + " "+tst.get("google"));
	}

	public static PQ<Integer, String> occurrences(String scan) throws IOException {
		String txtPath = "/Users/nethravijayadas/Documents/";

		File txt = new File(txtPath);
		File[] Files = txt.listFiles();

		PQ<Integer, String> pq = new SortedPQ<>();
		// scan txt files
		for (int i = 0; i < Files.length; i++) {
			if (Files[i].isFile() && Files[i].getName().contains(".txt")) {
				TST<Integer> tst = new TST<Integer>();
				String path = (txtPath + Files[i].getName());
				scanFileToFormTST(path, tst, scan);// get occurrence from matching given word
				if (tst.get(scan) != null) {
					// store occurrence and web name in priority queue
					pq.insert(tst.get(scan), path);
				}
			}
		}
//        Iterator itr = pq.iterator();
//        while(itr.hasNext()) {
//            System.out.println(itr.next());        }
		return pq;
	}

	public static ResumeJobDetails[] generateResumeList(PQ<Integer, String> pq, String str) throws IOException {
		ResumeJobDetails[] queryResults = new ResumeJobDetails[pq.size()];
		Iterator<Item<Integer, String>> s = pq.iterator();
		int flag = 0;
		while (s.hasNext()) {
			Item<Integer, String> tmp = s.next();
			ResumeJobDetails resumeJobs = new ResumeJobDetails(tmp.getKey(), tmp.getValue(), str);
			queryResults[(pq.size() - 1) - flag] = resumeJobs;
			flag++;
		}
		return queryResults;
	}

	public static void search() throws IOException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please input job title ");
		String s = scan.nextLine();
		PQ<Integer, String> pq = occurrences(s);
		ResumeJobDetails[] resumeJobs = generateResumeList(pq, s);

		if (resumeJobs.length != 0) {

			Map<Integer, Object[]> resumeData = new TreeMap<Integer, Object[]>();
			resumeData.put(1, new Object[] { "ID", "Resume Link", "Rank" });
			for (Integer i = 0; i < resumeJobs.length; i++) {
				Integer id = i + 1;
				String ids = id.toString();
				System.out.println(resumeJobs[i].getResumeLink() + " " + resumeJobs[i].getRank());
				resumeData.put(i + 2, new Object[] { ids, resumeJobs[i].getResumeLink(), resumeJobs[i].getRank().toString() });

			}

			addToExcel(resumeData, s);
			Scraper scraper = new Scraper();
			scraper.scrapeLinkedInAndIndded(s);
		}

	}

	private static void addToExcel(Map<Integer, Object[]> resumeData, String jobName) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet = workbook.createSheet(jobName+ " Resumes");

		// creating a row object
		XSSFRow row;
		Set<Integer> keyid = resumeData.keySet();

		int rowid = 0;

		// writing the data into the sheets...

		for (Integer key : keyid) {

			row = spreadsheet.createRow(rowid++);
			Object[] objectArr = resumeData.get(key);
			int cellid = 0;

			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}
		FileOutputStream out = new FileOutputStream(
				new File("/Users/nethravijayadas/Study/ACC/ResumeData/" + jobName + ".xlsx"));

		workbook.write(out);
		out.close();
		
	}

	public static void main(String[] args) throws IOException {

		search();
		
	}
}
