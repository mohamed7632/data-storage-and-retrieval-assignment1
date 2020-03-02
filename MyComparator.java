import java.util.Comparator;

public class MyComparator implements Comparator<Record> {

	@Override
	public int compare(Record r1, Record r2) {
		return Integer.compare(r1.getKey(), r2.getKey());
	}
}
