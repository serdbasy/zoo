package program;


public class Run {

	public static void main(String[] args) {

		long startTime = System.currentTimeMillis();
		System.out.println();
		new Zoo(10, 10, 100, 100, 45);
		long endTime = System.currentTimeMillis();
		System.out.println((endTime - startTime) + "ms");
		
	}
	
}
