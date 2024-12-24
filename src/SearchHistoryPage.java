import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class SearchHistoryPage extends JFrame {
	JPanel titlePanel, mainPanel, subPanel, sub2Panel, HomePanel;
	JLabel titleLabel, label1, subLabel, sub1Label, sub2Label, sub3Label;
	JButton jb1, jb2;
	JScrollPane scroll;

	SearchHistoryPage() {
		super("SearchHistoryPage");
		setLayout(new BorderLayout(0, 15));
		titleLabel = new JLabel("J ������");
		titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titlePanel = new JPanel();
		titlePanel.add(titleLabel);
		titlePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		add(titlePanel, "North");

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(0, 50));
		subPanel = new JPanel();
		label1 = new JLabel("���� �˻� ����");
		label1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		label1.setFont(new Font("NanumGothic", Font.BOLD, 30));
		subPanel.add(label1);
		mainPanel.add(subPanel, "North");

		// DB���� ����� �˻�����
		sub2Panel = new JPanel();
		sub2Panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		Box vbox = Box.createVerticalBox();
		vbox.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		List<Map<String, String>> searchHistory = SearchDB.getSearchHistory();
		for(int i=0; i < SearchDB.getSearchHistory().size(); i++) {
			Map<String, String> record = searchHistory.get(i);
			String ����� = record.get("�����");
			String ������ = record.get("������");
			
			subLabel = new JLabel(����� + "��" + "��" + ������ + "��");
			subLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			subLabel.setFont(new Font("NanumGothic", Font.BOLD, 20));
			subLabel.setHorizontalAlignment(SwingConstants.CENTER);
			subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			vbox.add(subLabel);
		}
		
		sub2Panel.add(vbox, BorderLayout.CENTER);
		mainPanel.add(sub2Panel, "Center");

		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
		scroll = new JScrollPane(sub2Panel, v, h);
		mainPanel.add(scroll, "Center");

		add(mainPanel, "Center");

		HomePanel = new JPanel();
		jb1 = new JButton("Ȩ");
		jb2 = new JButton("�ڷ�");
		HomePanel.add(jb1);
		HomePanel.add(jb2);
		add(HomePanel, "South");
		
		jb1.addActionListener(e -> {
              // �ٸ� ȭ������ �̵� (Main Ŭ���� �ʿ�)
            setVisible(false);
        });
		setSize(400, 600);
		jb1.setPreferredSize(new Dimension(150, 50));
		jb2.setPreferredSize(new Dimension(150, 50));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new SearchHistoryPage();

	}

}