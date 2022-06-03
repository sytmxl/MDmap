package version1;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.io.File;
import javax.swing.*;

/*
 * 用于构造主窗口，菜单栏
 * 更新日志：
 * MWv1.01 新增了 Panel的鼠标事件处理类 的 右键点击 的事件监听 ————118行
 * 		     新增了 组件的鼠标事件处理类 的 右键点击 的事件监听 ————129行
 *         新增了右键菜单栏的构建
 */
public class MainWindow {
	public static JFrame frame;//主窗口，不使用
	public static Container frameContainer;//，主窗口容器，不使用
	public static PaintePanel pan;//所有组件均加入此容器，使用绝对布局
	public static void main(String args[]) {
		try {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		  javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	MainWindow mainWindow = new MainWindow();
	        	//	mainWindow.test();
	            }
	        });
		
		
	}
	
	/**
	 * addFilechooser
        MainWindow
        RightClickMenu
        ThemeFrame
		constent 
		paintpanel
		panmouselistener
		themechooser
		themelabel


		ComponentMouseListener
		ConnectLine
		Constent
		CurveLine
		nodeListener
		PaintHelper
		ThemeDetect
		
	 */
	/*
	 * 创建窗口、主菜单、放置组件的pan、及右键菜单
	 */
	public  MainWindow() {
		/*********获取图片路径**********/
		int length=((new File("xxx.txt")).getAbsolutePath()).toString().length();
		Constent.imagesPath=((new File("xxx.txt")).getAbsolutePath()).toString().substring(0,length-8)+"\\images\\";
		//System.out.print(Constent.imagesPath);
		
		/******初始化pan*********/
		MainWindow.pan=new PaintePanel();
		MainWindow.pan.CreatBox();
		Thread tr=new Thread(MainWindow.pan);
		tr.start();
		
		/*****frame初始化*************/
		MainWindow.frame =new JFrame("UMind");
		MainWindow.frame.setSize(Constent.frameWidth,Constent.frameHeight);
		MainWindow.frame.setLocation(Constent.frameLocationX,Constent.frameLOcationY);
		MainWindow.frame.setIconImage(new ImageIcon(Constent.imagesPath+"\\frame\\umind.png").getImage());
		/******contanier初始化*********/
		MainWindow.frameContainer=frame.getContentPane();
		/*********加入菜单与滚轴*******/
		createMenu();
		createScrollPanel();
		/*********加入监听*******/
		frame.addWindowListener(new MyWindowListener());
		/*********主窗口可见*******/
		frame.setVisible(true);
		MainWindow.pan.changestyle();
		DropTargetListener listener = new DropTargetListenerImpl();

        // 在 textArea 上注册拖拽目标监听器
		DropTarget dropTarget = new DropTarget(MainWindow.pan, DnDConstants.ACTION_COPY_OR_MOVE, listener, true);

        // 如果要移除监听器, 可以调用下面代码
        // dropTarget.removeDropTargetListener(listener);


	}
	/*
	 * 创建菜单并加入到窗口上端,设置助记符和快捷打开方式,
	 * 及加入相应事件监听（未完成）
	 */
	public void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("文件");
		JMenu menuEdit = new JMenu("编辑");
		JMenu menuTool = new JMenu("工具");
		JMenu menuStyle = new JMenu("风格");
		//“文件”的设置
		JMenuItem openFile =new JMenuItem("打开",'O');
		openFile.setIcon(new ImageIcon(Constent.imagesPath+"fileMenu\\open.png"));
		JMenuItem saveFile =new JMenuItem("保存",'S');
		saveFile.setIcon(new ImageIcon(Constent.imagesPath+"fileMenu\\save.png"));
		openFile.setAccelerator(KeyStroke.getKeyStroke('O',java.awt.Event.CTRL_MASK));
		saveFile.setAccelerator(KeyStroke.getKeyStroke('S',java.awt.Event.CTRL_MASK));
		JMenuItem clearAll =new JMenuItem("清除所有");
		//清除所有的动作监听
		clearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AddFileChooser.loadFile(new File(Constent.imagesPath+"other\\origin.umind"));
				} catch (FileNotFoundException ex) {
					throw new RuntimeException(ex);
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
		});
		menuFile.add(openFile);
		menuFile.add(saveFile);
		menuFile.add(clearAll);
		new AddFileChooser(openFile,saveFile);//构造文本选择框
		//“编辑”的设置，与右键菜单重复且实现更为复杂，暂时废弃
		JMenuItem shear = new JMenuItem("剪切",new ImageIcon(Constent.imagesPath+"editMenu\\1.png"));
		JMenuItem copy = new JMenuItem("复制",new ImageIcon(Constent.imagesPath+"editMenu\\2.png"));
		JMenuItem paste = new JMenuItem("粘贴",new ImageIcon(Constent.imagesPath+"editMenu\\3.png"));
		JMenuItem delete = new JMenuItem("删除",new ImageIcon(Constent.imagesPath+"editMenu\\4.png"));
		menuEdit.add(shear);
		menuEdit.add(copy);
		menuEdit.add(paste);
		menuEdit.add(delete);
		//“工具”的设置
		
		//“风格”的设置
		JMenuItem Dracula =new JMenuItem("Dracula");
		Dracula.setIcon(new ImageIcon(Constent.imagesPath+"fileMenu\\Bat.png"));
		JMenuItem Sublime =new JMenuItem("Sublime");
		Sublime.setIcon(new ImageIcon(Constent.imagesPath+"fileMenu\\Kokey.png"));
		JMenuItem Classic =new JMenuItem("Classic");
		Classic.setIcon(new ImageIcon(Constent.imagesPath+"fileMenu\\Casper.png"));
		menuStyle.add(Dracula);
		menuStyle.add(Sublime);
		menuStyle.add(Classic);
		new ThemeChooser(Dracula,Sublime,Classic);
		
		//“文件”等加入JMenuBar
		menuBar.add(menuFile);
		//menuBar.add(menuEdit);废弃
		menuBar.add(menuTool);
		menuBar.add(menuStyle);
		MainWindow.frame.setJMenuBar(menuBar);
	}
	/*
	 * 创建JPanel pan并加上滚轴
	 * 向pan中加组件使用add方法
	 * 因采用绝对布局，pan中组件必须使用setBounds方法定位
	 */
	public void createScrollPanel() {
		
		MainWindow.frameContainer.add(pan.getScrollPane());
		//this.rightClickMenu.createBoxs();//建造字体类型、颜色、大小下拉框，必须已有pan后才能建立
	}
	/*
	 * 用于测试的方法,可随意更改
	 */
	public void test() {
		ThemeLabel t1 =new ThemeLabel(30, 30);
		
		t1.setText("好好好好好");
		//t1.updateSize();
		pan.add(t1);
		t1.setVisible(true);
		t1.setBackground(Color.getHSBColor(1.233f, 2.4444f, 4.2221f));
	}
	private static class DropTargetListenerImpl implements DropTargetListener {

        /** 用于显示拖拽的数据 */
//        private JTextArea textArea;

        public DropTargetListenerImpl() {
//            this.textArea = textArea;
        }

        @Override
        public void dragEnter(DropTargetDragEvent dtde) {
            System.out.println("dragEnter: 拖拽目标进入组件区域");
        }

        @Override
        public void dragOver(DropTargetDragEvent dtde) {
            System.out.println("dragOver: 拖拽目标在组件区域内移动");
        }

        @Override
        public void dragExit(DropTargetEvent dte) {
            System.out.println("dragExit: 拖拽目标离开组件区域");
        }

        @Override
        public void dropActionChanged(DropTargetDragEvent dtde) {
            System.out.println("dropActionChanged: 当前 drop 操作被修改");
        }

        @Override
        public void drop(DropTargetDropEvent dtde) {
            // 一般情况下只需要关心此方法的回调
            System.out.println("drop: 拖拽目标在组件区域内释放");

            boolean isAccept = false;

            try {
                /*
                 * 1. 文件: 判断拖拽目标是否支持文件列表数据（即拖拽的是否是文件或文件夹, 支持同时拖拽多个）
                 */
                if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    // 接收拖拽目标数据
                    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    isAccept = true;

                    // 以文件集合的形式获取数据
                    List<File> files = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                    // 把文件路径输出到文本区域
                    if (files != null && files.size() > 0) {
                        for (File file : files) {
                            //filePaths.append("文件: " + file.getAbsolutePath() + "\n");
                        	System.out.println(file.getAbsolutePath());                        	
                        	AddFileChooser.loadFile(file);
                        }
                        //textArea.append(filePaths.toString());
                    }
                }
                
                /*
                 * 2. 文本: 判断拖拽目标是否支持文本数据（即拖拽的是否是文本内容, 或者是否支持以文本的形式获取）
                 */
                if (dtde.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    // 接收拖拽目标数据
                    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    isAccept = true;

                    // 以文本的形式获取数据
                    String text = dtde.getTransferable().getTransferData(DataFlavor.stringFlavor).toString();
                    
                    // 输出到文本区域
                    //textArea.append("文本: " + text + "\n");
        			ThemeLabel themeLabel = new ThemeLabel(dtde.getLocation().x, dtde.getLocation().y);
        			themeLabel.setText(text);
        			themeLabel.updateSize();
        			/*********** 新建主题时建立连接关系 ***********/
        			new ThemeDetect(MainWindow.pan).addConnect(themeLabel);
        			themeLabel.setRank(themeLabel.getRank());
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            // 如果此次拖拽的数据是被接受的, 则必须设置拖拽完成（否则可能会看到拖拽目标返回原位置, 造成视觉上以为是不支持拖拽的错误效果）
            if (isAccept) {
                dtde.dropComplete(true);
            }
        }
	}
}
/*
 * 窗口事件处理类
 */
class MyWindowListener extends WindowAdapter{
	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		System.exit(1);
	}
}
