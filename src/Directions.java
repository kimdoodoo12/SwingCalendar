import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Directions extends JFrame {

    private JTextField startField;
    private JTextField endField;

    public Directions() {
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

        // 5. �߾� �г� ���� (����� BoxLayout���� ��ġ, �ϴ��� BorderLayout.SOUTH)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // ������� ������ �Է� �ʵ� �� �˻� ��ư �߰�
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel startLabel = new JLabel("�����:");
        startField = new JTextField(20);

        JLabel endLabel = new JLabel("������:");
        endField = new JTextField(20);

        JButton searchButton = new JButton("��ã�� �˻�");
        JButton searchHistory = new JButton("���� �˻�");
        
        searchPanel.add(startLabel);
        searchPanel.add(startField);
        searchPanel.add(endLabel);
        searchPanel.add(endField);

        searchPanel.add(searchHistory);
        searchPanel.add(searchButton);
        
        // �߾� ������Ʈ �߰�
        centerPanel.add(titlePanel);
        centerPanel.add(searchPanel);

        add(centerPanel, BorderLayout.CENTER);
        add(bottomButtonPanel, BorderLayout.SOUTH);

        // 6. ��ư �̺�Ʈ ������ ����
        homeButton.addActionListener(e -> {
             // �ٸ� ȭ������ �̵� (Main Ŭ���� �ʿ�)
            setVisible(false);
        });

        backButton.addActionListener(e -> setVisible(false));

        // �˻� ��ư Ŭ�� �� searchResult������ ��� ȭ�� ����
        searchButton.addActionListener(e -> {
            String startLocation = startField.getText();
            String endLocation = endField.getText();
            // ������� ������ ������ ��� ã��
            if (!startLocation.isEmpty() && !endLocation.isEmpty()) {
                // API ȣ�� �� ��� ã�� (SubwayPathFinder)
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
                JOptionPane.showMessageDialog(this, "������� �������� �Է��ϼ���.", "�Է� ����", JOptionPane.ERROR_MESSAGE);
            }
            
        });
        searchHistory.addActionListener(e -> {
        	new SearchHistoryPage();
        	dispose();
        });

        // â ũ�� ����
        setSize(400, 600);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Directions();
    }
}
