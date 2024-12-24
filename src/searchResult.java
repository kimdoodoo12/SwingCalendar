import javax.swing.*;

import java.awt.*;

public class searchResult extends JFrame {

    private JPanel Result = new JPanel();

    // String ���� �޴� ������ �߰�
    public searchResult() {
        // 1. ������ �⺻ ����
        setTitle("��ã�� - J ������");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // ȭ�� �߾ӿ� ��ġ
        setLayout(new BorderLayout());

        // 2. Ÿ��Ʋ ���̺� ����
        JLabel titleLabel = new JLabel("J ������", JLabel.CENTER);
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30));

        // 3. Ÿ��Ʋ �г� ����
        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.setPreferredSize(new Dimension(800, 50));
        titlePanel.setMaximumSize(new Dimension(800, 50));  // �ִ� ũ�� ����

        // 4. �ϴ� ��ư �г� ����
        JPanel bottomButtonPanel = new JPanel();
        bottomButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomButtonPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        JButton homeButton = new JButton("Ȩ");
        JButton backButton = new JButton("�ڷΰ���");

        bottomButtonPanel.add(homeButton);
        bottomButtonPanel.add(backButton);
        
        // ����ö ��� �г�
        
        for (int i =0; i< SubwayPathFinder.startNameArray.size(); i++) {
        	JLabel StartName = new JLabel(SubwayPathFinder.startNameArray.get(i) + "��");
        	Result.add(Box.createVerticalStrut(20));
        	StartName.setFont(new Font("NanumGothic", Font.BOLD, 20));
            StartName.setAlignmentX(CENTER_ALIGNMENT);
            StartName.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            JLabel arrowLabel = new JLabel("��", JLabel.CENTER);
            arrowLabel.setFont(new Font("Arial", Font.PLAIN, 30));
            arrowLabel.setAlignmentX(CENTER_ALIGNMENT);
            Result.setLayout(new BoxLayout(Result, BoxLayout.Y_AXIS));
            Result.add(StartName);
            Result.add(arrowLabel);
        }
        Result.add(Box.createVerticalStrut(20));
        JLabel EndName = new JLabel(SubwayPathFinder.endLocation + "��");
        EndName.setFont(new Font("NanumGothic", Font.BOLD, 20));
        EndName.setAlignmentX(CENTER_ALIGNMENT);
        EndName.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        Result.add(EndName);
       
        // ����ö ���� �г�
        Result.add(Box.createVerticalStrut(30));
        JLabel EndTime = new JLabel(SubwayPathFinder.endTrainTime + " ���");
        JLabel startStation = new JLabel(SubwayPathFinder.startNameArray.get(0) +"���� ����");
        startStation.setFont(new Font("NanumGothic", Font.BOLD, 20));
        startStation.setAlignmentX(CENTER_ALIGNMENT);
        EndTime.setFont(new Font("NanumGothic", Font.BOLD, 20));
        EndTime.setAlignmentX(CENTER_ALIGNMENT);
        Result.add(startStation);
        Result.add(EndTime);

        // 6. �߾� �г� ����
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        centerPanel.add(titlePanel);
        centerPanel.add(Result);  // ��ũ�� �����ϰ� JScrollPane �߰�

        add(centerPanel, BorderLayout.CENTER);
        add(bottomButtonPanel, BorderLayout.SOUTH);

        // ��ư �̺�Ʈ ������ ����
        homeButton.addActionListener(e -> {
              // Ȩ ȭ������ �̵� (Main Ŭ���� �ʿ�)
            dispose();
            SubwayPathFinder.resetData();
        });

        backButton.addActionListener(e -> setVisible(false));
        
        // â ũ�� ����
        setSize(400, 600);
        setVisible(true);
    }

    public static void main(String[] args) {
        // ���÷� ���￪�� startName���� ����
        new searchResult();
    }
}
