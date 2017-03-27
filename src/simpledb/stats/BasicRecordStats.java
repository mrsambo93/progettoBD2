package simpledb.stats;

public class BasicRecordStats {

	private int readRecord = 0;
	private int writtenRecord = 0;
	private int readFieldsRecord = 0;
	private int writtenFieldsRecord = 0;
	
	public BasicRecordStats() {}

	public int getReadRecord() {
		return readRecord;
	}

	public void setReadRecord(int readRecord) {
		this.readRecord = readRecord;
	}

	public int getWrittenRecord() {
		return writtenRecord;
	}

	public void setWrittenRecord(int writtenRecord) {
		this.writtenRecord = writtenRecord;
	}

	public int getReadFieldsRecord() {
		return readFieldsRecord;
	}

	public void setReadFieldsRecord(int readFieldsRecord) {
		this.readFieldsRecord = readFieldsRecord;
	}

	public int getWrittenFieldsRecord() {
		return writtenFieldsRecord;
	}

	public void setWrittenFieldsRecord(int writtenFieldsRecord) {
		this.writtenFieldsRecord = writtenFieldsRecord;
	}
	
	public void incrementReadRecord() {
		this.setReadRecord(this.getReadRecord() + 1);
	}

	public void incrementWrittenRecord() {
		this.setWrittenRecord(this.getWrittenRecord() + 1);
	}
	
	public void incrementReadFieldsRecord() {
		this.setReadFieldsRecord(this.getReadFieldsRecord() + 1);
	}

	public void incrementWrittenFieldsRecord() {
		this.setWrittenFieldsRecord(this.getWrittenFieldsRecord() + 1);
	}
	
}
