import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ThemeFrame extends JFrame {
    private ThemeFrame me = null;
    private ThemeLabel owner = null;//�޸ĵ����ĸ��ı���
    private JTextField input = null;//������ʾ�����޸��ı���
    private JButton button1 = null;//ȷ�ϰ�ť
    private JButton button2 = null;//ȡ����ť
    //�������������޸Ĵ���
    public ThemeFrame(ThemeLabel from) {
        me = this;
        this.owner = from;
        this.setSize(Constent.themeFrameWidth, Constent.themeFrameHeight);
        this.setLocation((Constent.frameWidth - Constent.themeFrameWidth) / 2,
                (Constent.frameHeight - Constent.themeFrameHeight) / 2);
        this.setTitle("�༭");
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(252, 248, 232));

        Font myFont = new Font("����", Font.BOLD, 20);
        JPanel pan1 = new JPanel();
        pan1.setBounds(50, 50, 480, Constent.themeFrameHeight/2);
        //������ʾ�����޸��ı���Ĵ�С�����ݡ��߿�����
        //pan1.setBorder(BorderFactory.createTitledBorder("��ǩ����"));
        pan1.setLayout(new GridLayout(1, 1));
        pan1.setBackground(new Color(252, 248, 232));
        input = new JTextField("����");
        input.setFont(new Font("΢���ź�",Font.BOLD,30));
        pan1.add(input);
        //ȷ�ϰ�ť�Ĵ�С�����ݡ��߿�����
        button1 = new JButton("ȷ��");
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
        //ȡ����ť�Ĵ�С�����ݡ��߿�����
        button2 = new JButton("ȡ��");
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
