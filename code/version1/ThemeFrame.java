package version1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*
 * ********************************************************
 * @author 郭家磊
 * ********************************************************
 * @function 
 * 负责构造主题内容修改窗口
 * 加入相应监听
 * @time 2019-1-12
 * @value
 * private ThemeLabel owner = null  从属于子主题类对象
 * private JTextField input = null  主题显示内容修改文本框
 * private JTextArea remark = null  主题备注内容修改多行文本框
 * private JTextField link=null     链接显示内容修改文本框
 * private JButton button1 = null   确认按钮
 * private JButton button2 = null   取消按钮
 * @fuctions
 * public ThemeFrame(ThemeLabel from) 构造主题内容修改窗口
 * class ThemeFrameActionListener implements ActionListener 确认按钮的事件监听
 */
/*********主题修改及备注实现******************/
public class ThemeFrame extends JFrame {
	private ThemeLabel owner = null;//从属于子主题类对象
	private JTextField input = null;//主题显示内容修改文本框
	private ThemeFrame me = null;
	private JTextArea remark = null;//主题备注内容修改多行文本框
	private JButton button1 = null;//确认按钮
	private JButton button2 = null;//取消按钮
	private String remarkContent = null;
	private JTextField link=null;//链接显示内容修改文本框
    //构造主题内容修改窗口
	public ThemeFrame(ThemeLabel from) {
		me = this;
		this.owner = from;
		this.setSize(Constent.themeFrameWidth, Constent.themeFrameHeight);
		this.setLocation((Constent.frameWidth - Constent.themeFrameWidth) / 2,
				(Constent.frameHeight - Constent.themeFrameHeight) / 2);
		this.setIconImage(new ImageIcon(Constent.imagesPath+"\\frame\\newfire.png").getImage());
		this.setTitle("编辑");
		this.setLayout(null);
		this.setBackground(Color.CYAN);

		Font myFont = new Font("黑体", Font.BOLD, 30);
		JPanel pan1 = new JPanel();
		pan1.setBounds(0, 0, 580, 50);
		//主题显示内容修改文本框的大小、内容、边框设置
		pan1.setBorder(BorderFactory.createTitledBorder("标签内容"));
		pan1.setLayout(new GridLayout(1, 1));
		input = new JTextField("主题");
		pan1.add(input);
		
		//主题备注内容修改多行文本框的大小、内容、边框设置
		JPanel pan2 = new JPanel();
		pan2.setBounds(0, 50, 580, 200);
		pan2.setBorder(BorderFactory.createTitledBorder("备注内容"));
		pan2.setLayout(new GridLayout(1, 1));
		remark = new JTextArea();
		remark.setLineWrap(true);
		JScrollPane scr = new JScrollPane(remark, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pan2.add(scr);
		
		//链接内容修改文本框的大小、内容、边框设置
		JPanel pan3 = new JPanel();
		pan3.setBounds(0, 250, 580, 50);
		pan3.setBorder(BorderFactory.createTitledBorder("链接地址"));
		pan3.setLayout(new GridLayout(1, 1));
		link = new JTextField("");
		pan3.add(link);
		
		//确认按钮的大小、内容、边框设置
		button1 = new JButton("确定");
		button1.setBounds(180, 300, 100, 40);
		button1.setBorder(BorderFactory.createRaisedBevelBorder());
		button1.setFont(myFont);
		button1.addActionListener(new ThemeFrameActionListener(this));
		
		//取消按钮的大小、内容、边框设置
		button2 = new JButton("取消");
		button2.setBounds(320, 300, 100, 40);
		button2.setBorder(BorderFactory.createRaisedBevelBorder());
		button2.setFont(myFont);
		button2.addActionListener(new ThemeFrameActionListener(this));

		this.add(pan1);
		this.add(pan2);
		this.add(pan3);
		this.add(button1);
		this.add(button2);
		
		//this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(false);
		this.setResizable(false);

	}

	public String getRemarkContent() {
		return remarkContent;
	}

	public void setRemarkContent(String remarkContent) {
		this.remarkContent = remarkContent;
	}

	public JTextField getInput() {
		return input;
	}

	public void setInput(JTextField input) {
		this.input = input;
	}

	public ThemeFrame getMe() {
		return me;
	}

	public void setMe(ThemeFrame me) {
		this.me = me;
	}

	public JTextArea getRemark() {
		return remark;
	}

	public void setRemark(JTextArea remark) {
		this.remark = remark;
	}

	public JButton getButton1() {
		return button1;
	}
	
	public JButton getButton2() {
		return button2;
	}

	public void setButton1(JButton button1) {
		this.button1 = button1;
	}

	public ThemeLabel getowner() {
		return owner;
	}

	public void setOwner(ThemeLabel owner) {
		this.owner = owner;
	}

	public JTextField getLink() {
		return link;
	}

	public void setLink(JTextField link) {
		this.link = link;
	}
	
}

// 确认按钮的事件监听
class ThemeFrameActionListener implements ActionListener {
	private ThemeFrame from = null;

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == from.getButton1()) {
			from.getowner().setText(from.getInput().getText());
			from.setRemarkContent(from.getRemark().getText());
			int deltaSizex=from.getowner().getNewLength()-from.getowner().getSizeX();
			from.getowner().updateSize();
			from.getowner().setLinkURL(from.getLink().getText());
			new ThemeDetect(MainWindow.pan).themesizeChangeMove(from.getowner(),deltaSizex);
			
		}
		else if(e.getSource() ==from.getButton2()) {
			
		}
		from.setVisible(false);
		
	}

	public ThemeFrameActionListener(ThemeFrame from) {
		super();
		this.from = from;
	}
}
