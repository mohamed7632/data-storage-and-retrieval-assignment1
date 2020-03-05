
public class Pair<T1, T2> {
	private T1 var1;
	private T2 var2;
	
	public Pair(T1 obj1, T2 obj2) {
		this.var1 = obj1;
		this.var2 = obj2;
	}
	
	public void Set_T1(T1 value) {
		this.var1 = value;
	}
	
	public void Set_T2(T2 value) {
		this.var2 = value;
	}
	
	public T1 Get_T1() {
		return this.var1;
	}
	public T2 Get_T2() {
		return this.var2;
	}
}
