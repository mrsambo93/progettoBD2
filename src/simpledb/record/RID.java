package simpledb.record;

/**
 * An identifier for a record within a file.
 * A RID consists of the block number in the file,
 * and the ID of the record in that block.
 * @author Edward Sciore
 */
public class RID {
	private int blknum;
	private int id;

	/**
	 * Creates a RID for the record having the
	 * specified ID in the specified block.
	 * @param blknum the block number where the record lives
	 * @param id the record's ID
	 */
	public RID(int blknum, int id) {
		this.blknum = blknum;
		this.id     = id;
	}

	/**
	 * Returns the block number associated with this RID.
	 * @return the block number
	 */
	public int blockNumber() {
		return blknum;
	}

	/**
	 * Returns the ID associated with this RID.
	 * @return the ID
	 */
	public int id() {
		return id;
	}

	public boolean equals(Object obj) {
		if(obj==null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RID r = (RID) obj;
		return blknum == r.blknum && id==r.id;
	}

	public static RID NULL = new RID(-1,-1);
	
	public String toString() {
		return "[" + blknum + ", " + id + "]";
	}
	
	public int hashCode() {
		final int primo = 31;
		int result = 1;
		result = primo + result + blknum;
		result = primo + result + id;
		return result;
	}
}
