import javafx.scene.layout.BorderPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class MainWindow {
    public static JFrame frame;//主窗口，不使用
    public static Container frameContainer;//，主窗口容器，不使用
    public static  Box toolBar;
    public static PaintePanel pan;
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //把外观设置成你所使用的平台的外观,
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        javax.swing.SwingUtilities.invokeLater(new Runnable() { //确保了事件处理器都能串行的执行，并且绘画过程不会被事件打断
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
        JMenu menuFile = new JMenu("文件");

        menuBar.add(menuFile);
        frame.setJMenuBar(menuBar);
    }
    public void createToolBar(){
        JToolBar toolBar = new JToolBar();
        Box line2 = Box.createHorizontalBox();
        JButton i_open = toolButton("open.png", "Open", "打开文件");
        i_open.addActionListener(addlistener);
        JButton i_save = toolButton("md.png", "Save", "导出为md文件");
        i_save.addActionListener(savelistener);
        JButton i_saveXmind = toolButton("xmind.png", "Save", "导出为xmind文件");
        i_saveXmind.addActionListener(xmindlistener);
        JButton i_add = toolButton("add.png", "New", "新建");
        i_add.addActionListener(buttonMouseListener);
        JButton i_delete = toolButton("delete.png", "Delete", "删除");
        i_delete.addActionListener(deleteThemeListener);
        JButton i_edit = toolButton("edit.png", "Edit", "编辑文本");
        i_edit.addActionListener(editTextListener);
        JButton i_clear = toolButton("clear.png", "Clear", "清空");
        i_clear.addActionListener(clearListener);
        JButton i_picture = toolButton("picture.png", "Picture", "导出图片");
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
        //获取资源文件
        System.out.println(fileName);
        URL url = getClass().getResource(fileName);

        JButton button = new JButton();
        button.setActionCommand(action);
        button.setToolTipText(toolText);
        button.setFocusable(false);
        button.setBackground(Color.white);
       // button.setSize(30,30);
        //设置图标
        int width = 40,height = 40;
        ImageIcon image = new ImageIcon(url);

        image.setImage(image.getImage().getScaledInstance(width, height,Image.SCALE_DEFAULT ));
      //  button.setIcon(new ImageIcon(url));
        button.setIcon(image);
        button.setSize(width, height);
        //设置监听事件
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
            //获取该控件命令码
            String action = e.getActionCommand();
            //通过对话框打印
            JOptionPane.showMessageDialog(frame, action);

        }

    };


}
