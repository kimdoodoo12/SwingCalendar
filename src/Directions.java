import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Directions extends JFrame {

    private JTextField startField;
    private JTextField endField;

    public Directions() {
        // 1. 프레임 기본 설정
        setTitle("길찾기 - J 스케줄");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // 화면 중앙에 배치
        setLayout(new BorderLayout());

        // 2. 타이틀 레이블 설정
        JLabel titleLabel = new JLabel("J 스케줄", JLabel.CENTER);
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30));

        // 3. 타이틀 패널 생성
        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.setPreferredSize(new Dimension(800, 50));
        titlePanel.setMaximumSize(new Dimension(800, 50));  // 최대 크기 고정

        // 4. 하단 버튼 패널 설정
        JPanel bottomButtonPanel = new JPanel();
        bottomButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomButtonPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        JButton homeButton = new JButton("홈");
        JButton backButton = new JButton("뒤로가기");

        bottomButtonPanel.add(homeButton);
        bottomButtonPanel.add(backButton);

        // 5. 중앙 패널 설정 (상단은 BoxLayout으로 배치, 하단은 BorderLayout.SOUTH)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // 출발지와 목적지 입력 필드 및 검색 버튼 추가
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel startLabel = new JLabel("출발지:");
        startField = new JTextField(20);

        JLabel endLabel = new JLabel("목적지:");
        endField = new JTextField(20);

        JButton searchButton = new JButton("길찾기 검색");
        JButton searchHistory = new JButton("이전 검색");
        
        searchPanel.add(startLabel);
        searchPanel.add(startField);
        searchPanel.add(endLabel);
        searchPanel.add(endField);

        searchPanel.add(searchHistory);
        searchPanel.add(searchButton);
        
        // 중앙 컴포넌트 추가
        centerPanel.add(titlePanel);
        centerPanel.add(searchPanel);

        add(centerPanel, BorderLayout.CENTER);
        add(bottomButtonPanel, BorderLayout.SOUTH);

        // 6. 버튼 이벤트 리스너 설정
        homeButton.addActionListener(e -> {
             // 다른 화면으로 이동 (Main 클래스 필요)
            setVisible(false);
        });

        backButton.addActionListener(e -> setVisible(false));

        // 검색 버튼 클릭 시 searchResult에서의 경로 화면 가기
        searchButton.addActionListener(e -> {
            String startLocation = startField.getText();
            String endLocation = endField.getText();
            // 출발지와 목적지 정보로 경로 찾기
            if (!startLocation.isEmpty() && !endLocation.isEmpty()) {
                // API 호출 및 경로 찾기 (SubwayPathFinder)
                try {
                    SubwayPathFinder.findPath(startLocation, endLocation);
                    SubwayPathFinder.findSchedule(startLocation);
                    SubwayPathFinder.saveSearchToDB(startLocation, endLocation);
                    new searchResult();
                    setVisible(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "출발지와 목적지를 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
            }
            
        });
        searchHistory.addActionListener(e -> {
        	new SearchHistoryPage();
        	dispose();
        });

        // 창 크기 설정
        setSize(400, 600);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Directions();
    }
}
