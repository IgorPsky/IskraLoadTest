
public abstract class Runner implements Runnable {
	private boolean isStop = false;
	
	public Runner() {
		super();
		isStop = false;
	}

	
	public static void GlobalStop() {
		Flags.isGlobalStop = true;
	}

	public static void GlobalStopReset() {
		Flags.isGlobalStop = false;
	}
    public void Stop() {
    	isStop=true;
    }
    public boolean isStop() {
    	return isStop || Flags.isGlobalStop;
    }
}
