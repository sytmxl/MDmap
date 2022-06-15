import javax.swing.*;
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
        this.setBackground(Color.CYAN);

        Font myFont = new Font("����", Font.BOLD, 30);
        JPanel pan1 = new JPanel();
        pan1.setBounds(0, 0, 580, 50);
        //������ʾ�����޸��ı���Ĵ�С�����ݡ��߿�����
        pan1.setBorder(BorderFactory.createTitledBorder("��ǩ����"));
        pan1.setLayout(new GridLayout(1, 1));
        input = new JTextField("����");
        pan1.add(input);

        //ȷ�ϰ�ť�Ĵ�С�����ݡ��߿�����
        button1 = new JButton("ȷ��");
        button1.setBounds(180, 300, 100, 40);
        button1.setBorder(BorderFactory.createRaisedBevelBorder());
        button1.setFont(myFont);
        button1.addActionListener(new ThemeFrameActionListener(this));

        //ȡ����ť�Ĵ�С�����ݡ��߿�����
        button2 = new JButton("ȡ��");
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
