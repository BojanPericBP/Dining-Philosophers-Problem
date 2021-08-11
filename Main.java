import java.util.ArrayList;

public final class Main {
public static final int numOfPhil = 2;
	
	public boolean[] sticks = new boolean[Main.numOfPhil];
	public Object[] sticksLock = new Object[numOfPhil];
	
	public static void main(String[] args) {
		Main m = new Main();
		
		for (int i = 0; i < m.sticks.length; i++) {
			m.sticks[i] = true;
			m.sticksLock[i] = new Object();
		}
		
		ArrayList<Philosopher> philosophers = new ArrayList<>(numOfPhil);
		for(int i = 0; i<numOfPhil; ++i)
		{
			philosophers.add(new Philosopher(m));
		}
				
		for(int i=0; i<numOfPhil; ++i)
		{
			philosophers.get(i).start();
		}
	}
}
