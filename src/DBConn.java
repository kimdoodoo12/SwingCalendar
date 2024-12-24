import java.sql.*;

public class DBConn {
	private static String jdbcUrl = "jdbc:mysql://localhost:3306/example?useUnicode=true&characterEncoding=UTF8&serverTimezone=UTC";
    private static String user = "root";
    private static String password = "1234";
    
    public static Connection getConnection() throws SQLException {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		return DriverManager.getConnection(jdbcUrl, user, password);
    	} catch (ClassNotFoundException | SQLException e) {
    		throw new SQLException("DB연결 실패", e);
    	}
    }
}
