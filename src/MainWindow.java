import javafx.scene.layout.BorderPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class MainWindow {
    public static JFrame frame;//�����ڣ���ʹ��
    public static Container frameContainer;//����������������ʹ��
    public static  Box toolBar;
    public static PaintePanel pan;
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //��������ó�����ʹ�õ�ƽ̨�����,
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        javax.swing.SwingUtilities.invokeLater(new Runnable() { //ȷ�����¼����������ܴ��е�ִ�У����һ滭���̲��ᱻ�¼����
            public void run() {
                MainWindow mainWindow = new MainWindow();
            }
        });
    }

    public  MainWindow(){
        pan=new PaintePanel();
        Thread tr=new Thread(pan);
        tr.start();

        frame=new JFrame("MyMind");
        MainWindow.frame.setSize(Constent.frameWidth,Constent.frameHeight);
        MainWindow.frame.setLocation(Constent.frameLocationX,Constent.frameLOcationY);

        frameContainer=frame.getContentPane();

        //createMenu();
        createScrollPanel();
        createToolBar();

        frame.setVisible(true);
    }
    public void createMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("�ļ�");

        menuBar.add(menuFile);
        frame.setJMenuBar(menuBar);
    }
    public void createToolBar(){
        JToolBar toolBar = new JToolBar();
        Box line2 = Box.createHorizontalBox();
        JButton i_open = toolButton("open.png", "Open", "���ļ�");
        i_open.addActionListener(addlistener);
        JButton i_save = toolButton("md.png", "Save", "����Ϊmd�ļ�");
        i_save.addActionListener(savelistener);
        JButton i_saveXmind = toolButton("xmind.png", "Save", "����Ϊxmind�ļ�");
        i_saveXmind.addActionListener(xmindlistener);
        JButton i_add = toolButton("add.png", "New", "�½�");
        i_add.addActionListener(buttonMouseListener);
        JButton i_delete = toolButton("delete.png", "Delete", "ɾ��");
        i_delete.addActionListener(deleteThemeListener);
        JButton i_edit = toolButton("edit.png", "Edit", "�༭�ı�");
        i_edit.addActionListener(editTextListener);
        JButton i_clear = toolButton("clear.png", "Clear", "���");
        i_clear.addActionListener(clearListener);
        JButton i_picture = toolButton("picture.png", "Picture", "����ͼƬ");
        i_picture.addActionListener(pictureListener);

        toolBar.add(i_open);
        toolBar.add(i_save);
        toolBar.add(i_saveXmind);
        toolBar.add(i_add);
        toolBar.add(i_delete);
        toolBar.add(i_edit);
        toolBar.add(i_clear);
        toolBar.add(i_picture);
        line2.add(Box.createHorizontalStrut(0));
        line2.add(toolBar);
        toolBar.setMaximumSize(new Dimension(9999, 60));
        line2.setMaximumSize(new Dimension(9999, 60));
        line2.setPreferredSize(new Dimension(9999, 60));
        line2.setBackground(Color.white);
        toolBar.setBackground(Color.white);
        toolBar.setMinimumSize(new Dimension(999, 60));
        MainWindow.toolBar=line2;
        frame.getContentPane().add(line2, BorderLayout.NORTH);

    }
    public void createScrollPanel(){
        frame.getContentPane().add(pan.getScrollPane(), BorderLayout.CENTER);
    }
    protected JButton toolButton(String filePath, String action, String toolText)
    {
        String fileName = filePath;
        //��ȡ��Դ�ļ�
        System.out.println(fileName);
        URL url = getClass().getResource(fileName);

        JButton button = new JButton();
        button.setActionCommand(action);
        button.setToolTipText(toolText);
        button.setFocusable(false);
        button.setBackground(Color.white);
       // button.setSize(30,30);
        //����ͼ��
        int width = 40,height = 40;
        ImageIcon image = new ImageIcon(url);

        image.setImage(image.getImage().getScaledInstance(width, height,Image.SCALE_DEFAULT ));
      //  button.setIcon(new ImageIcon(url));
        button.setIcon(image);
        button.setSize(width, height);
        //���ü����¼�
       // button.addActionListener(listener);
        return button;
    }
    AddFileChooser addlistener=new AddFileChooser();
    SaveFileChooser savelistener=new SaveFileChooser();
    ButtonMouseListener buttonMouseListener = new ButtonMouseListener();
    DeleteThemeListener deleteThemeListener=new DeleteThemeListener();
    EditTextListener editTextListener=new EditTextListener();
    ClearListener clearListener=new ClearListener();
    PictureListener pictureListener=new PictureListener();
    XmindListener xmindlistener=new XmindListener();
    ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            //��ȡ�ÿؼ�������
            String action = e.getActionCommand();
            //ͨ���Ի����ӡ
            JOptionPane.showMessageDialog(frame, action);

        }

    };


}
