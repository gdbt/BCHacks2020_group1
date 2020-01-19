import java.io.*;
import java.sql.*;
public class conversion {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String url = "jdbc:mysql://***.***.***.***/**";   //input your own server address and logins
		String uid = "**";
		String pw = "*****";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch ( java.lang.ClassNotFoundException e) {
			System.err.println("ClassNotFoundException: " + e );
		}
		
		Connection con = null;
		//File file = new File("dataFile.csv");
		try {
			con = DriverManager.getConnection(url,uid,pw);
			Statement stmt = con.createStatement();
			ResultSet rst = stmt.executeQuery("SELECT * FROM locationMatrix");
			PrintWriter w = new PrintWriter("dataFile.csv","UTF-8"); 
			while (rst.next()) {
				w.println(rst.getInt("locationId")+ "," + rst.getDouble("xNum") + "," + rst.getDouble("yNum"));
				System.out.println( rst.getInt("locationId")+ "," + rst.getDouble("xNum") + "," + rst.getDouble("yNum") );
			}
			w.close();
		}
		catch (SQLException ex) {
			System.err.println(ex);
		}
		finally {
			if (con != null)
				try {
					con.close();
				}
				catch (SQLException ex) {
					System.err.println(ex);
				}
		}
	}

}
