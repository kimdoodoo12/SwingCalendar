import java.sql.*;
import java.util.*;

public class SearchDB {
	public static List<Map<String, String>> getSearchHistory(){
		List<Map<String, String>> searchHistory = new ArrayList<>();
		String query = "SELECT �����, ������ FROM �����˻����� ORDER BY �˻��Ϸù�ȣ";
		
		try (Connection conn = DBConn.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {
				
				ResultSet rs = stmt.executeQuery();
				
				while(rs.next()) {
					String ����� = rs.getString("�����");
					String ������ = rs.getString("������");
					
					Map<String, String> record = new HashMap<>();
					record.put("�����", �����);
					record.put("������", ������);
					searchHistory.add(record);
				}
				rs.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
		return searchHistory;
	}
}
