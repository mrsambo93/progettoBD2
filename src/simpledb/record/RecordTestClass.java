package simpledb.record;

import java.util.Map;
import java.util.Random;

import simpledb.server.SimpleDB;
import simpledb.stats.BasicFileStats;
import simpledb.stats.BasicRecordStats;
import simpledb.tx.Transaction;

public class RecordTestClass {

	private static Random randomNumberGenerator = new Random();
	private static RandomStr randomStringGenerator = new RandomStr();
	private static int MAX_STRING_LENGTH = 25;
	private static String TABLE_FIELD_MATRICOLA = "Matricola";
	private static String TABLE_FIELD_NAME = "name";
	private static String TABLE_FIELD_NUMEROCASUALE = "numero casuale";
	private static int INSERT_STUDENT_QTV = 10000;
	private static int INSERT_STUDENT_QTV_NEW = 7000;
	private static long TOTAL_BLK_READ = 0;
	private static long TOTAL_BLK_WRITTEN = 0;

	public static void main(String[] args) {
		SimpleDB.init("studentdbTest2");
		Schema schTableTest = new Schema();
		schTableTest.addIntField(TABLE_FIELD_MATRICOLA);
		schTableTest.addStringField(TABLE_FIELD_NAME,MAX_STRING_LENGTH);
		schTableTest.addIntField(TABLE_FIELD_NUMEROCASUALE);

		TableInfo tbiTableTest = new TableInfo("studenti",schTableTest);
		SimpleDB.fileMgr().resetMapStats();

		insertStudents(tbiTableTest,INSERT_STUDENT_QTV);
		SimpleDB.fileMgr().resetMapStats();

		readStudents(tbiTableTest);
		SimpleDB.fileMgr().resetMapStats();

		deleteStudents(tbiTableTest,INSERT_STUDENT_QTV/2);
		SimpleDB.fileMgr().resetMapStats();

		readStudentCalculate(tbiTableTest);
		SimpleDB.fileMgr().resetMapStats();

		insertStudents(tbiTableTest,INSERT_STUDENT_QTV_NEW);
		SimpleDB.fileMgr().resetMapStats();

		readStudents(tbiTableTest);
		SimpleDB.fileMgr().resetMapStats();

		System.out.println("**************************************************************");
		System.out.println("Totale accessi al disco: "+(TOTAL_BLK_READ+TOTAL_BLK_WRITTEN));
	}

	private static void readStudentCalculate(TableInfo table) {
		Transaction tx = new Transaction();
		RecordFile rfTable = new RecordFile(table, tx);
		long sumCasuale = 0;
		int studentCont = 0;
		rfTable.next();
		do {
			sumCasuale += rfTable.getInt(TABLE_FIELD_NUMEROCASUALE);
			studentCont += 1;
		}while(rfTable.next());
		System.out.println("AVG "+ (sumCasuale/studentCont));
		printRecordStats(rfTable);
		System.out.println("---BLOCKS---");
		printBlockStats(rfTable.getFilename());
		rfTable.close();
		tx.commit();
	}

	private static void deleteStudents(TableInfo table, int studentiQuantity) {
		Transaction tx = new Transaction();
		RecordFile rfTable = new RecordFile(table, tx);
		rfTable.next();
		do{
			if(rfTable.getInt(TABLE_FIELD_MATRICOLA)>studentiQuantity) {
				rfTable.delete();
			}
		}while(rfTable.next());
		printRecordStats(rfTable);
		System.out.println("---BLOCKS---");
		printBlockStats(rfTable.getFilename());
		rfTable.close();
		tx.commit();
	}

	private static void readStudents(TableInfo table) {
		Transaction tx = new Transaction();
		RecordFile rfTable = new RecordFile(table, tx);
		rfTable.next();
		do {
			rfTable.getInt(TABLE_FIELD_MATRICOLA);
			rfTable.getString(TABLE_FIELD_NAME);
			rfTable.getInt(TABLE_FIELD_NUMEROCASUALE);
		} while(rfTable.next());

		printRecordStats(rfTable);
		System.out.println("---BLOCKS---");
		printBlockStats(rfTable.getFilename());
		rfTable.close();
		tx.commit();
	}

	private static void insertStudents(TableInfo table, int howMuch) {
		Transaction tx = new Transaction();
		RecordFile rfTable = new RecordFile(table,tx);
		for(int i=0;i<howMuch;i++) {
			rfTable.insert();
			rfTable.setInt(TABLE_FIELD_MATRICOLA,i);
			rfTable.setString(TABLE_FIELD_NAME,randomStringGenerator.get(MAX_STRING_LENGTH));
			rfTable.setInt(TABLE_FIELD_NUMEROCASUALE,randomNumberGenerator.nextInt(howMuch));
		}
		printRecordStats(rfTable);
		System.out.println("---BLOCKS---");
		printBlockStats(rfTable.getFilename());
		rfTable.close();
		tx.commit();
	}

	//anche lui ha il warning
	private static void allBlockStats() {
		Map<String, BasicFileStats> statsMap = SimpleDB.fileMgr().getMapStats();
		long totalBlockRead = 0;
		long totalBlockWritten = 0;
		System.out.println("**************************************************************");
		System.out.println("FILENAME\t");                            //non capisco cosa c'� scritto
		for(String fileName: statsMap.keySet()) {
			BasicFileStats bTemp = statsMap.get(fileName);
			totalBlockRead +=bTemp.getBlockRead();
			totalBlockWritten +=bTemp.getBlockWritten();
			printBlockStats(fileName,bTemp);
		}
		System.out.println("------------------------------------------------------------");
		System.out.println("TOTALE\t"+totalBlockRead +"\t"+ totalBlockWritten);
	}

	private static void printBlockStats(String filename) {
		if(SimpleDB.fileMgr().getMapStats().containsKey(filename)) {
			printBlockStats(filename,SimpleDB.fileMgr().getMapStats().get(filename));
		}
	}

	private static void printBlockStats(String filename, BasicFileStats basicFileStats) {
		System.out.println("blocchi letti "+basicFileStats.getBlockRead());
		System.out.println("blocchi scritti "+basicFileStats.getBlockWritten());
		TOTAL_BLK_READ += basicFileStats.getBlockRead();
		TOTAL_BLK_WRITTEN += basicFileStats.getBlockWritten();
	}

	private static void printRecordStats(RecordFile rfTable) {
		Map<RID, BasicRecordStats> statsMap = rfTable.getStatsRecord();
		long totalRecordRead = 0;
		long totalRecordWritten = 0;
		//long totalRecordFieldRead = 0;
		//long totalRecordFieldWritten = 0;

		System.out.println("**************************************************************");
		System.out.println("filename "+ rfTable.getFilename());
		for(RID recordStats: statsMap.keySet()) {
			BasicRecordStats currStats = statsMap.get(recordStats);
			totalRecordRead = currStats.getReadRecord();
			totalRecordWritten = currStats.getWrittenRecord();
			System.out.println("------------------------------------------------------------");
			System.out.println("Record letti "+totalRecordRead);
			System.out.println("Record scritti "+totalRecordWritten);
		}
	}
}

class RandomStr {
	private final char[] alphanumeric = alphanumeric();
	private final Random rand;

	public RandomStr() {
		this(null);
	}

	public RandomStr(Random rand) {
		this.rand = (rand!=null) ? rand : new Random();    // ? che sarebbe?
	}

	public String get(int len) {
		StringBuffer out = new StringBuffer();

		while(out.length()<len) {
			int idx = Math.abs((rand.nextInt()%alphanumeric.length));
			out.append(alphanumeric[idx]);
		}
		return out.toString();

	}

	private char[] alphanumeric() {
		StringBuffer buf = new StringBuffer(128);
		for(int i=65; i<=90; i++)
			buf.append((char) i);
		for(int i=97; i<=122; i++)
			buf.append((char) i);
		return buf.toString().toCharArray();
	}
}
