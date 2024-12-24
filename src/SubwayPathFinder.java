import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.*;
public class SubwayPathFinder {

    static String apiKey = "P0NoslIcoGDGkhGrxTk444ZPMMgl7C4R+QnY7xGywJ0";
	public static ArrayList<String> startNameArray = new ArrayList<>();
	public static String endLocation;
	public static String endTrainTime;
	
	public static void resetData() {
		startNameArray.clear();
		endLocation = null;
		endTrainTime = null;
	}
	public static void saveSearchToDB(String startLocation, String endLocation) {
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			conn = DBConn.getConnection();
			String query = "INSERT INTO 이전검색내역 (출발지, 도착지) VALUES (?, ?)";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, startLocation);
			stmt.setString(2, endLocation);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null & !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
    public static void findPath(String startLocation, String endLocation) throws IOException {
    	SubwayPathFinder.endLocation = endLocation;

        // 역 이름으로 stationID를 찾는 API 호출
        String startStationID = getStationID(startLocation, apiKey);
        String endStationID = getStationID(endLocation, apiKey);

        if (startStationID == null || endStationID == null) {
            System.out.println("출발 역 또는 도착 역의 stationID를 찾을 수 없습니다.");
        } else {
            System.out.println("Start Station ID: " + startStationID);
            System.out.println("End Station ID: " + endStationID);

            // 두 역의 stationID를 이용하여 경로 검색
            findPathBetweenStations(startStationID, endStationID, apiKey, startLocation);
        }
    }
    public static void findSchedule(String startLocation) throws IOException{
    	String stationID = getStationID(startLocation, apiKey);
    	String urlInfo = "https://api.odsay.com/v1/api/searchSubwaySchedule?lang=0"
                + "&stationID=" + URLEncoder.encode(stationID, "UTF-8")
                + "&apiKey=" + URLEncoder.encode(apiKey, "UTF-8");

        URL url = new URL(urlInfo);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
    	
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // InputStreamReader에서 UTF-8 인코딩을 지정
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String responseString = response.toString();

            JSONObject jsonResponse = new JSONObject(responseString);

            // "result" 부분을 객체로 가져오기
            if (jsonResponse.has("result")) {
                JSONObject result = jsonResponse.getJSONObject("result");

                // "weekdaySchedule" 부분을 가져와서 필요한 정보만 처리
                if (result.has("weekdaySchedule")) {
                    JSONObject weekdaySchedule = result.getJSONObject("weekdaySchedule");

                    // "up" 배열을 가져와서 첫 번째 스케줄만 처리
                    JSONArray upArray = weekdaySchedule.getJSONArray("up");

                    // 첫 번째 항목만 처리 (firstLastFlag가 2인 경우)
                    for (int i = 0; i < upArray.length(); i++) {
                        JSONObject schedule = upArray.getJSONObject(i);

                        int firstLastFlag = schedule.optInt("firstLastFlag", -1);

                        // firstLastFlag가 2인 경우만 처리
                        if (firstLastFlag == 2) {
                            String departureTime = schedule.optString("departureTime", "정보 없음");
                            endTrainTime = departureTime;
                            // 첫 번째 시간만 출력
                            System.out.println(departureTime);
                            return;  // 첫 번째 데이터만 출력하고 종료
                        }
                    }
                }
            }
        }

        conn.disconnect();
    }


    private static String getStationID(String stationName, String apiKey) throws IOException {
        String urlInfo = "https://api.odsay.com/v1/api/searchStation?lang=0&stationClass=2&stationName=" 
                         + URLEncoder.encode(stationName, "UTF-8") 
                         + "&apiKey=" + URLEncoder.encode(apiKey, "UTF-8");

        // HTTP 요청 설정
        URL url = new URL(urlInfo);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {  // 200 OK
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            bufferedReader.close();
            conn.disconnect();

            String response = sb.toString();
            JSONObject jsonResponse = new JSONObject(response);

            // 응답 데이터에서 stationID가 존재하는지 체크
            if (jsonResponse.has("result")) {
                JSONObject result = jsonResponse.getJSONObject("result");
                if (result.has("station") && result.getJSONArray("station").length() > 0) {
                    JSONObject station = result.getJSONArray("station").getJSONObject(0);
                    Object stationIDObj = station.get("stationID");
                    return (stationIDObj instanceof String) ? (String) stationIDObj : String.valueOf(stationIDObj);
                } else {
                    System.out.println("해당 역을 찾을 수 없습니다: " + stationName);
                }
            }
        } else {
            System.out.println("API 호출에 실패했습니다. 응답 코드: " + responseCode);
        }
        return null;
    }


    private static void findPathBetweenStations(String startStationID, String endStationID, String apiKey, String startName) throws IOException {
        String urlInfo = "https://api.odsay.com/v1/api/subwayPath?CID=1000"
                         + "&SID=" + startStationID
                         + "&EID=" + endStationID
                         + "&apiKey=" + URLEncoder.encode(apiKey, "UTF-8");

        URL url = new URL(urlInfo);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {  // 200 OK
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            bufferedReader.close();
            conn.disconnect();

            String response = sb.toString();
            JSONObject jsonResponse = new JSONObject(response);
            

            if (jsonResponse.has("result")) {
                JSONObject result = jsonResponse.getJSONObject("result");

                if (result.has("driveInfoSet")) {
                    JSONObject driveInfoSet = result.getJSONObject("driveInfoSet");
                    if (driveInfoSet.has("driveInfo")) {
                        JSONArray driveInfoArray = driveInfoSet.getJSONArray("driveInfo");
                        for (int i = 0; i < driveInfoArray.length(); i++) {
                            JSONObject driveInfo = driveInfoArray.getJSONObject(i);
                            String startNameFromApi = driveInfo.getString("startName");
                            System.out.println("출발 역: " + startNameFromApi);
                            startNameArray.add(startNameFromApi);
                            

                            // searchResult 클래스를 호출하여 경로 정보 표시
                        }
                    }
                }
            }
        }
    }
}
