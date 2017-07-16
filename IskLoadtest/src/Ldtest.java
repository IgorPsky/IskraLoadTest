import java.sql.*;

public class Ldtest {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	// static final String DB_URL = "jdbc:mysql://192.168.5.91,192.168.5.92,192.168.5.93?secondsBeforeRetryMaster=16";
	
	static final String DB_URL = "jdbc:mysql://192.168.5.91:3306/apigw";
	
	
	public static void loadTest(int insertersCount) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		for (int i=0; i<insertersCount; i++) {
			Inserter ins = new Inserter(DB_URL, "iskratel", "iskratel");
			Thread tIns = new Thread(ins);
			tIns.start();
		}
		try {
			Thread.sleep(10000);
			Inserter.GlobalStop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		try {
			loadTest(5);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
