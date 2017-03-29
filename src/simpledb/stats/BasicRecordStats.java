package simpledb.stats;

public class BasicRecordStats {

	private int readRecord = 0;
	private int writtenRecord = 0;
	private int readFieldRecord = 0;
	private int writtenFieldRecord = 0;
	
	public int getReadRecord() {
		return readRecord;
	}
	
	public void setReadRecord(int numReadRecord) {
		readRecord = numReadRecord;
	}

	public int getWrittenRecord() {
		return writtenRecord;
	}

	public void setWrittenRecord(int writtenRecord) {
		this.writtenRecord = writtenRecord;
	}

	public int getReadFieldRecord() {
		return readFieldRecord;
	}

	public void setReadFieldRecord(int readFieldRecord) {
		this.readFieldRecord = readFieldRecord;
	}

	public int getWrittenFieldRecord() {
		return writtenFieldRecord;
	}

	public void setWrittenFieldRecord(int writtenFieldRecord) {
		this.writtenFieldRecord = writtenFieldRecord;
	}
	
	public void incrementalReadRecord() {
		this.readRecord += 1;
	}
	
	public void incrementalReadFieldRecord() {
		this.readFieldRecord += 1;
	}
	
	public void incrementalWrittenRecord() {
		this.writtenRecord += 1;
	}

	public void incrementalWrittenFieldRecord() {
		this.writtenFieldRecord += 1;
	}
}
