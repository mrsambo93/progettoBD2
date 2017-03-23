package simpledb.stats;


public class BasicFileStats {

	private int blockRead;
	private int blockWritten;

	public BasicFileStats() {}

	public int getBlockRead() {
		return blockRead;
	}

	public void setBlockRead(int blockRead) {
		this.blockRead = blockRead;
	}

	public int getBlockWritten() {
		return blockWritten;
	}

	public void setBlockWritten(int blockWritten) {
		this.blockWritten = blockWritten;
	}

	public void incrementBlockRead() {
		this.setBlockRead(this.getBlockRead() + 1);
	}

	public void incrementBlockWritten() {
		this.setBlockWritten(this.getBlockWritten() + 1);
	}
}
