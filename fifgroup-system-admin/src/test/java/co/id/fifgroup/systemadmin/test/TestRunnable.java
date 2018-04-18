package co.id.fifgroup.systemadmin.test;

public class TestRunnable implements Runnable {
	
	private String a;
	
	@Override
	public void run() {
		int i = 0;
		while(i < 5) {
			System.out.println("aaaa " + a);
			try {
				Thread.sleep(2 * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

}
