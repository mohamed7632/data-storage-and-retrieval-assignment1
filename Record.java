
public class Record {
	private int key, offset;
	
	public Record(int key, int offset) {
		this.key = key;
		this.offset = offset;
	}
	
	public int getKey() {
		return key;
	}
	
	public void setKey(int key) {
		this.key = key;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public void print() {
		System.out.println(this.key + "   " + this.offset);
	}
}
