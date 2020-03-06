import java.util.Comparator;
public class Comparator_1 implements Comparator<Pair<Record,Integer>> {

	@Override
	public int compare(Pair<Record, Integer> record, Pair<Record, Integer> another_record) {
		return Integer.compare(record.Get_T1().getKey(), another_record.Get_T1().getKey());
	}

}
