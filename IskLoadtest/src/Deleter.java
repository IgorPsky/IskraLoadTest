import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Deleter extends Runner implements Runnable {

	PreparedStatement stmt = null;
	Connection conn;
	String deleteSql = "delete from cdr where call_date < (select min(call_date) from (SELECT call_date from cdr order by call_date desc limit ?) q);";
	private String dbUrl;
	
	@Override
	public void run() {
		try {
			stmt = conn.prepareStatement(deleteSql);
			while(!isStop()){
				Thread.yield();
				stmt.setInt(1, 10000);
				stmt.executeUpdate();
				Thread.sleep(3000);;
			}
		} catch (SQLException e) {
			Stop();
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public Deleter(String connectString, String user, String pass) throws SQLException {
		super();
		dbUrl = connectString;
		conn = DriverManager.getConnection(dbUrl, user, pass);
	}
	

}
