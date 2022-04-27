package core.basic.singleton;

public class StatefulService {

	private int price;
	
	public int order(String name, int price) {
		return price;
	}
	
	public int getPrice() {
		return price;
	}
}
