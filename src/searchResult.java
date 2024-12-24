import javax.swing.*;

import java.awt.*;

public class searchResult extends JFrame {

    private JPanel Result = new JPanel();

    // String 값을 받는 생성자 추가
    public searchResult() {
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
        
        // 지하철 경로 패널
        
        for (int i =0; i< SubwayPathFinder.startNameArray.size(); i++) {
        	JLabel StartName = new JLabel(SubwayPathFinder.startNameArray.get(i) + "역");
        	Result.add(Box.createVerticalStrut(20));
        	StartName.setFont(new Font("NanumGothic", Font.BOLD, 20));
            StartName.setAlignmentX(CENTER_ALIGNMENT);
            StartName.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            JLabel arrowLabel = new JLabel("↓", JLabel.CENTER);
            arrowLabel.setFont(new Font("Arial", Font.PLAIN, 30));
            arrowLabel.setAlignmentX(CENTER_ALIGNMENT);
            Result.setLayout(new BoxLayout(Result, BoxLayout.Y_AXIS));
            Result.add(StartName);
            Result.add(arrowLabel);
        }
        Result.add(Box.createVerticalStrut(20));
        JLabel EndName = new JLabel(SubwayPathFinder.endLocation + "역");
        EndName.setFont(new Font("NanumGothic", Font.BOLD, 20));
        EndName.setAlignmentX(CENTER_ALIGNMENT);
        EndName.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        Result.add(EndName);
       
        // 지하철 막차 패널
        Result.add(Box.createVerticalStrut(30));
        JLabel EndTime = new JLabel(SubwayPathFinder.endTrainTime + " 출발");
        JLabel startStation = new JLabel(SubwayPathFinder.startNameArray.get(0) +"역의 막차");
        startStation.setFont(new Font("NanumGothic", Font.BOLD, 20));
        startStation.setAlignmentX(CENTER_ALIGNMENT);
        EndTime.setFont(new Font("NanumGothic", Font.BOLD, 20));
        EndTime.setAlignmentX(CENTER_ALIGNMENT);
        Result.add(startStation);
        Result.add(EndTime);

        // 6. 중앙 패널 설정
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        centerPanel.add(titlePanel);
        centerPanel.add(Result);  // 스크롤 가능하게 JScrollPane 추가

        add(centerPanel, BorderLayout.CENTER);
        add(bottomButtonPanel, BorderLayout.SOUTH);

        // 버튼 이벤트 리스너 설정
        homeButton.addActionListener(e -> {
              // 홈 화면으로 이동 (Main 클래스 필요)
            dispose();
            SubwayPathFinder.resetData();
        });

        backButton.addActionListener(e -> setVisible(false));
        
        // 창 크기 설정
        setSize(400, 600);
        setVisible(true);
    }

    public static void main(String[] args) {
        // 예시로 서울역을 startName으로 전달
        new searchResult();
    }
}
