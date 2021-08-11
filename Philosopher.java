
public class Philosopher extends Thread {

	public static final long TIME = 1000;

	private static int count = 0;
	int idPhilosopher;
	boolean isAte;

	boolean holdLeftStick;
	boolean holdRightStick;

	Main main;

	String name;
	
	public Philosopher(Main m) {
		idPhilosopher = count++;
		name = "Filozof_" + idPhilosopher;
		main = m;
	}

	@Override
	public void run() 
	{
		while (!isAte) 
		{
			think();
			try {sleep(TIME);} catch (InterruptedException e) {e.printStackTrace();}
			
			synchronized(main.sticksLock[idPhilosopher])
			{
				takeLeftStick();
			}
			
			if(isAvailable((idPhilosopher+1)%Main.numOfPhil) && holdLeftStick)
			{
				
					synchronized(main.sticksLock[(idPhilosopher+1)%Main.numOfPhil]) 
					{
						takeRightStick();
					}
					
					eat();
					
					try {sleep(TIME);} catch (InterruptedException e) {e.printStackTrace();}
					
					synchronized(main.sticksLock[idPhilosopher]) 
					{
						putLeftStick();
					}
					
					synchronized(main.sticksLock[(idPhilosopher+1)%Main.numOfPhil]) 
					{			
						putRightStick();			
					}
			}
			else 
			{
				if(holdLeftStick)
				{
					synchronized(main.sticksLock[idPhilosopher])
					{
						putLeftStick();
					}
				}
				try {sleep(TIME);} catch (InterruptedException e) {e.printStackTrace();}
				
			}
		}
	}

	 boolean isAvailable(int index) {
		synchronized (main.sticksLock[index]) {
			return main.sticks[index];
		}
	}

	synchronized void takeLeftStick() {

	
		if (main.sticks[idPhilosopher])
		{
			holdLeftStick = true;
			main.sticks[idPhilosopher] = false;
			synchronized(System.out) 
			{
				System.out.println(name + " je uzeo lijevi stapic.");
			}
		}
	}

	synchronized void takeRightStick() 
	{

		if (main.sticks[(idPhilosopher + 1) % Main.numOfPhil])
		{
			holdRightStick = true;
			main.sticks[(idPhilosopher + 1) % Main.numOfPhil] = false;
			synchronized(System.out) 
			{
				System.out.println(name + " je uzeo desni stapic.");
			}
		} 
	}

	synchronized void putLeftStick() 
	{
		holdLeftStick = false;
		main.sticks[idPhilosopher] = true;
		synchronized(System.out) {System.out.println(name + " je ostavio lijevi stapic.");}
	}

	synchronized void putRightStick() 
	{
		if(holdRightStick)
		{
			holdRightStick = false;
			main.sticks[(idPhilosopher + 1) % Main.numOfPhil] = true;
			synchronized(System.out) {
				System.out.println(name + " je ostavio desni stapic.");
			}
		}
	}

	void eat() 
	{
		if (holdRightStick && holdLeftStick) 
		{
			isAte = true;
			synchronized(System.out) {System.out.println(name + " jede...");}
		}
	}

	void think() {
		System.out.println(name + " razmislja");
	}
}
