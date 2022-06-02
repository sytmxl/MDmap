package version1;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
/*
 * ********************************************************
 * @author 郭家磊
 * ********************************************************
 * @function 
 * 负责构造文本选择框
 * 实现文本保存和打开的事件监听
 * 本类 在主窗口菜单 创建“文件”菜单项时实例化
 * @time 2019-1-12
 * @value
 * private JMenuItem openFile 主菜单栏“打开文件”菜单项
 * private JMenuItem saveFile 主菜单栏“保存文件”菜单项
 * private JFileChooser fileChooser java.swing包里提供的文件选择框
 * @fuctions
 * public static void loadFile(File file)  打开文件
 * void saveFile(File file)  			     保存文件
 */


public class AddFileChooser implements ActionListener{
	private JMenuItem openFile;//主菜单栏“打开文件”菜单项
	private JMenuItem saveFile;//主菜单栏“保存文件”菜单项
	private JFileChooser fileChooser;//java.swing包里提供的文件选择框
//构造函数传递两个子菜单项，便于对其加上监听
	public AddFileChooser(JMenuItem openFile,JMenuItem saveFile) {
		this.openFile=openFile;
		this.saveFile=saveFile;
		this.fileChooser =new JFileChooser();
		//两个子菜单项加上监听
		openFile.addActionListener(this);
		saveFile.addActionListener(this);
	}
	
	//实现文本保存和打开的事件监听
	public void actionPerformed(ActionEvent e) {
		File file =null;
		int result =0;
		if(e.getSource()==this.openFile) {//设置“打开文件”选项的文件选择框内容，实现打开操作
			fileChooser.setApproveButtonText("确定");
			fileChooser.setDialogTitle("打开文件");
			result =fileChooser.showOpenDialog(MainWindow.frame);
			if(result == JFileChooser.APPROVE_OPTION) {//选择了确定键
				file = fileChooser.getSelectedFile();
				if((file!=null)&&file.getName().endsWith(".umind")) {
					loadFile(file);//为加载文件的功能函数
				}
			}else if(result == JFileChooser.CANCEL_OPTION) {//选择了取消键
				
			}else if(result == JFileChooser.ERROR_OPTION) {//出错了
				
			}
		}else if(e.getSource()==this.saveFile) {//设置“保存文件”选项的文件选择框内容，实现保存操作
			result =fileChooser.showSaveDialog(MainWindow.frame);
			if(result == JFileChooser.APPROVE_OPTION) {//选择了确定键
				file = fileChooser.getSelectedFile();
				System.out.println(file.getName());
				saveFile(file);//调用保存文件的功能函数
			}else if(result == JFileChooser.CANCEL_OPTION) {//选择了取消键
				
			}else if(result == JFileChooser.ERROR_OPTION) {//出错了
				
			}
		}
		
	}
	
	//加载文件的方法单独列出
	public static void loadFile(File file){//因拖入功能要调用此函数，故设static使其为类函数
		/*
		 *首先遍历保存有 所有主题类ThemeLabel对象和以其为入度的连接线ConnectLine对 的HashMap
		 *将可能打开的主题类对象所拥有的内容修改窗口ThemeFrame对象关闭
		 */
		for (Entry<ThemeLabel, ConnectLine> item : MainWindow.pan.getallConnectLine().entrySet()) {
			ThemeLabel key = item.getKey();
			MainWindow.pan.remove(key);
			key.getMyThemeFrame().setVisible(false);
			key.getMyThemeFrame().getInput().setText(null);
			key.setMyThemeFrame(null);
		}
		/*
		 * 将根主题类对象所拥有的内容修改窗口ThemeFrame对象关闭
		 */
		MainWindow.pan.getrootThemeLabel().getMyThemeFrame().setVisible(false);
		MainWindow.pan.getrootThemeLabel().getMyThemeFrame().getInput().setText(null);
		MainWindow.pan.getrootThemeLabel().setMyThemeFrame(null);
		
		MainWindow.pan.remove(MainWindow.pan.getrootThemeLabel());//移除根主题
		MainWindow.pan.removeAllConnectLine();//将保存有 所有主题类ThemeLabel对象和以其为入度的连接线ConnectLine对 的HashMap清空
		MainWindow.pan.setrootThemeLabel(null);
		
		
		ObjectInputStream ois=null;
		try {
			ois =new ObjectInputStream(new FileInputStream(file));//建立对象输入流
			MainWindow.pan.setrootThemeLabel((ThemeLabel) ois.readObject());//读入根主题		
			MainWindow.pan.setallConnectLine((HashMap<ThemeLabel, ConnectLine>)(ois.readObject()));//读入HashMap
			
			MainWindow.pan.getrootThemeLabel().getMyThemeFrame().getButton1().addActionListener(new ThemeFrameActionListener(MainWindow.pan.getrootThemeLabel().getMyThemeFrame()));//根主题所属内容修改窗口的确认按钮加入事件监听
			ComponentMouseListener componentMouseListener1=new ComponentMouseListener(MainWindow.pan.getrootThemeLabel());
			MainWindow.pan.getrootThemeLabel().addMouseListener(componentMouseListener1);//根主题加入鼠标事件监听
			MainWindow.pan.getrootThemeLabel().addMouseMotionListener(componentMouseListener1);//根主题加入鼠标事件监听
			/*
			 *遍历保存有 所有主题类ThemeLabel对象和以其为入度的连接线ConnectLine对 的HashMap
			 *对所有的主题类对象加入相应监听
			 */
			for (Entry<ThemeLabel, ConnectLine> item :MainWindow.pan.getallConnectLine().entrySet()) {
				ThemeLabel key = item.getKey();
				key.getMyThemeFrame().getButton1().addActionListener(new ThemeFrameActionListener(key.getMyThemeFrame()));
				ConnectLine val = item.getValue();
				ComponentMouseListener componentMouseListener=new ComponentMouseListener(key);
				key.addMouseListener(componentMouseListener);//加入鼠标事件监听
				key.addMouseMotionListener(componentMouseListener);
			}
			MainWindow.pan.add(MainWindow.pan.getrootThemeLabel());
			/*
			 *遍历保存有 所有连接线 的Vector
			 *将其节点从面板pan中移除
			 */
			for(int i=0;i<MainWindow.pan.getcurveLineList().size();i++) {
				CurveLine curveLine=MainWindow.pan.getcurveLineList().get(i);
				JLabel node1=curveLine.getNode1();
				JLabel node2=curveLine.getNode2();
				MainWindow.pan.remove(node1);
				MainWindow.pan.remove(node2);
				
			}
			Vector<CurveLine> CurveLineList=(Vector<CurveLine>)ois.readObject();//读入连接线
			MainWindow.pan.setCurveLineList(CurveLineList);
			/*
			 *遍历保存有 所有连接线 的Vector
			 *对所有的节点对象加入相应监听
			 */
			for(int i=0;i<CurveLineList.size();i++) {
				
				CurveLine curveLine=CurveLineList.get(i);
				
				JLabel node1=curveLine.getNode1();
				NodeListener curveLineListener1=new NodeListener(curveLine);
				node1.addMouseListener(curveLineListener1);
				node1.addMouseMotionListener(curveLineListener1);
				
				JLabel node2=curveLine.getNode2();
				NodeListener curveLineListener2=new NodeListener(curveLine);
				node2.addMouseListener(curveLineListener2);
				node2.addMouseMotionListener(curveLineListener2);
			}
			
			//读取保存风格，并修改风格
			ThemeChooser.LocalStyle = ((saveStyle) ois.readObject()).LocalStyle;
			MainWindow.pan.changestyle();
			//更新所有主题类对象位置
			int deltaSizex=MainWindow.pan.getrootThemeLabel().getNewLength()-MainWindow.pan.getrootThemeLabel().getSizeX();
			MainWindow.pan.getrootThemeLabel().updateSize();
			new ThemeDetect(MainWindow.pan).themesizeChangeMove(MainWindow.pan.getrootThemeLabel(),deltaSizex);
		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}finally {
			try {
				ois.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}


    void saveFile(File file){
    	if(file!=null&&file.getName().endsWith(".umind")){
			ObjectOutputStream oos=null;//创建输出流
			try {
				if((!file.exists())){
					file.createNewFile();
				}
				//清空主题类所有内容修改窗口中不能保存的内容
				MainWindow.pan.getrootThemeLabel().getMyThemeFrame().getInput().setText(null);
				MainWindow.pan.getrootThemeLabel().getMyThemeFrame().getRemark().setText(null);
				MainWindow.pan.getrootThemeLabel().getMyThemeFrame().getLink().setText(null);
				for (Entry<ThemeLabel, ConnectLine> item : MainWindow.pan.getallConnectLine().entrySet()) {
					ThemeLabel key = item.getKey();
					key.getMyThemeFrame().getInput().setText(null);
					key.getMyThemeFrame().getRemark().setText(null);
					key.getMyThemeFrame().getLink().setText(null);
				}
				//按顺序将对象输出保存
				 oos=new ObjectOutputStream(new FileOutputStream(file));
				 saveStyle style =new saveStyle(ThemeChooser.LocalStyle);
				 oos.writeObject(MainWindow.pan.getrootThemeLabel());
				 oos.writeObject(MainWindow.pan.getallConnectLine());
				 oos.writeObject(MainWindow.pan.getcurveLineList());
				 oos.writeObject(style);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				try {
					oos.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
    }
}
