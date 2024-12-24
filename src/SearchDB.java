import java.sql.*;
import java.util.*;

public class SearchDB {
	public static List<Map<String, String>> getSearchHistory(){
		List<Map<String, String>> searchHistory = new ArrayList<>();
		String query = "SELECT 출발지, 도착지 FROM 이전검색내역 ORDER BY 검색일련번호";
		
		try (Connection conn = DBConn.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {
				
				ResultSet rs = stmt.executeQuery();
				
				while(rs.next()) {
					String 출발지 = rs.getString("출발지");
					String 도착지 = rs.getString("도착지");
					
					Map<String, String> record = new HashMap<>();
					record.put("출발지", 출발지);
					record.put("도착지", 도착지);
					searchHistory.add(record);
				}
				rs.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
		return searchHistory;
	}
}
