import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ThemeFrame extends JFrame {
    private ThemeFrame me = null;
    private ThemeLabel owner = null;//修改的是哪个文本框
    private JTextField input = null;//主题显示内容修改文本框
    private JButton button1 = null;//确认按钮
    private JButton button2 = null;//取消按钮
    //构造主题内容修改窗口
    public ThemeFrame(ThemeLabel from) {
        me = this;
        this.owner = from;
        this.setSize(Constent.themeFrameWidth, Constent.themeFrameHeight);
        this.setLocation((Constent.frameWidth - Constent.themeFrameWidth) / 2,
                (Constent.frameHeight - Constent.themeFrameHeight) / 2);
        this.setTitle("编辑");
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(252, 248, 232));

        Font myFont = new Font("黑体", Font.BOLD, 20);
        JPanel pan1 = new JPanel();
        pan1.setBounds(50, 50, 480, Constent.themeFrameHeight/2);
        //主题显示内容修改文本框的大小、内容、边框设置
        //pan1.setBorder(BorderFactory.createTitledBorder("标签内容"));
        pan1.setLayout(new GridLayout(1, 1));
        pan1.setBackground(new Color(252, 248, 232));
        input = new JTextField("主题");
        input.setFont(new Font("微软雅黑",Font.BOLD,30));
        pan1.add(input);
        //确认按钮的大小、内容、边框设置
        button1 = new JButton("确定");
        button1.setBounds(170, 280, 100, 40);
        button1.setBorder(BorderFactory.createRaisedBevelBorder());
        button1.setFont(myFont);
        button1.setForeground(new Color(39, 60, 44));
        button1.addActionListener(new ThemeFrameActionListener(this));
        /*Border originBorder=BorderFactory.createLineBorder(new Color(236, 179, 144), 1);
        button1.setBorder(originBorder);
        button1.setBackground(new Color(236, 179, 144));
        button1.setOpaque(true);
        button1.setBorderPainted(false);*/
        //取消按钮的大小、内容、边框设置
        button2 = new JButton("取消");
        button2.setBounds(310, 280, 100, 40);
        button2.setBorder(BorderFactory.createRaisedBevelBorder());
        button2.setFont(myFont);
        button2.addActionListener(new ThemeFrameActionListener(this));
        button2.setForeground(new Color(39, 60, 44));
        /*button2.addActionListener(new ThemeFrameActionListener(this));
        button2.setBorder(originBorder);
        button2.setBackground(new Color(236, 179, 144));
        button2.setOpaque(true);
        button2.setBorderPainted(false);*/
        this.add(pan1);
        this.add(button1);
        this.add(button2);

        //this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(false);
        this.setResizable(false);
    }
    public ThemeLabel getowner() {
        return owner;
    }
    public JTextField getInput() {
        return input;
    }
    public JButton getButton1() {
        return button1;
    }

    public JButton getButton2() {
        return button2;
    }
}
