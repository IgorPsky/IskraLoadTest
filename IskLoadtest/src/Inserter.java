import java.sql.*;
import java.time.LocalDateTime;
import java.util.Random;

public class Inserter implements Runnable {
	Connection conn;
	PreparedStatement stmt = null;
	
	String insertSql = "insert into cdr(call_date, number_from, number_to, duration, comment) values (now(), ?, ?, ?, ?)";
	
	private static int insertersCount = 0;
	private static boolean isGlobalStop = false;
	
	public static void GlobalStop() {
		isGlobalStop = true;
	}

	public static void GlobalStopReset() {
		isGlobalStop = false;
	}

	private String dbUrl = "";
	
	private boolean isStop = false;
	
	private int inserterNo;
	
	public static String getRandomNumber() {
		return "+" + Integer.valueOf(rnd.nextInt(10000000)).toString();
	}
	
	static Random rnd = new Random();
	
    public Inserter(String connectString, String user, String pass) throws SQLException {
		super();	
		dbUrl = connectString;
		isStop = false;
		conn = DriverManager.getConnection(dbUrl, user, pass);
		inserterNo = ++insertersCount;
	}
    
    public void Stop() {
    	isStop=true;
    }

	@Override
	public void run() {
		try {
			stmt = conn.prepareStatement(insertSql);
			while(!isStop&&!isGlobalStop){
				Thread.yield();
				stmt.setString(1,getRandomNumber());
				stmt.setString(2,getRandomNumber());
				stmt.setInt(3, 1);
				stmt.setString(4, "Inserted by Inserter#"+inserterNo+" at "+LocalDateTime.now());
				stmt.executeUpdate();
				// conn.commit();
			}
		} catch (SQLException e) {
			isStop = true;
			e.printStackTrace();
		} 
	}


}
