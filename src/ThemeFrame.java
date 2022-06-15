import javax.swing.*;
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
        this.setBackground(Color.CYAN);

        Font myFont = new Font("黑体", Font.BOLD, 30);
        JPanel pan1 = new JPanel();
        pan1.setBounds(0, 0, 580, 50);
        //主题显示内容修改文本框的大小、内容、边框设置
        pan1.setBorder(BorderFactory.createTitledBorder("标签内容"));
        pan1.setLayout(new GridLayout(1, 1));
        input = new JTextField("主题");
        pan1.add(input);

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
