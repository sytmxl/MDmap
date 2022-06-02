package version1;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.util.Map.Entry;
/**
 * @author 郭晟
 * @function
 * 继承Jpanel，作为绘画面板组件，是主题类的容器
 * @time 2019-1-12
 * @value
 * 	public static RightClickMenu rightClickMenu = null; 右键菜单
 * 	private ThemeLabel rootThemeLabel = null; 根主题
 * 	private HashMap<ThemeLabel, ConnectLine> ConnectLineList = null; 主题索引的连接线哈希表
 * 	private Vector<CurveLine>CurveLineList=null; 关系线向量
 * 	JScrollPane scrollPane = null; 面板滚动条
 * @functions 
 * public PaintePanel() 构造方法，被MainWindow在初始化时创建
 * 关系线操作方法
 * 	public Vector<CurveLine> getcurveLineList()
 * 	public void setCurveLineList(Vector<CurveLine> CurveLineList)
 * 	public void addCurveLine(CurveLine curveLine)
 * 根节点操作方法
 * 	public void rootThemeaddChild(ThemeLabel themeLabel)
 * 	public ThemeLabel getrootThemeLabel()
 * 创建面板右键菜单
 * 	public void CreatBox()
 * 操作连接线
 * 	public void addConnectLine(ThemeLabel themeLabel, ConnectLine connectLine)
 * 	public ConnectLine getConnectLine(ThemeLabel themeLabel)
 * 	public void removeConnectLine(ThemeLabel themeLabel)
 * 	public void removeAllConnectLine()
 * 	public HashMap<ThemeLabel, ConnectLine> getallConnectLine()
 * 	public void setallConnectLine(HashMap<ThemeLabel, ConnectLine> ConnectLineList)
 * 重载JPanel的绘画方法，画主题、连接线和关系线
 * 	public void paint(Graphics g)
 * 实现Runnable接口，10ms刷新面板
 * 	public void run()
 * 更改面板主题
 * 	public void changestyle()
 */

/************* 单独抽出JPanel,便于画画 *************/
class PaintePanel extends JPanel implements Runnable {

	public static RightClickMenu rightClickMenu = null;
	/******** 根主题 *******/
	private ThemeLabel rootThemeLabel = null;
	/***** 主题入度与连接线绑定 *****/
	private HashMap<ThemeLabel, ConnectLine> ConnectLineList = null;
	
	private Vector<CurveLine>CurveLineList=null;

	

	JScrollPane scrollPane = null;

	public PaintePanel() {
		rootThemeLabel = new ThemeLabel(Constent.frameWidth * 2 / 5, Constent.frameHeight / 3);
		ConnectLineList = new HashMap<ThemeLabel, ConnectLine>();
		CurveLineList=new Vector<CurveLine>();
		rootThemeLabel.setRank(-1);
		rootThemeLabel.setSize(rootThemeLabel.getThemeSizeX(), rootThemeLabel.getThemeSizeY());
		rootThemeLabel.setSize(66,70);
		rootThemeLabel.updateLocation(Constent.frameWidth * 2 / 5, Constent.frameHeight / 3);
		this.add(rootThemeLabel);

		/******** 添加右键组件 ***********/
		this.rightClickMenu = new RightClickMenu();
		this.add(rightClickMenu.rightMenuForPan);

		/**************** 添加滚轴 ****************/
		this.scrollPane = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		PanMouseListener t1 = new PanMouseListener(this);
		this.addMouseListener(t1);
		this.addMouseMotionListener(t1);
		/********* 设置PaintePanel属性 ******/
		this.setBackground(Color.WHITE);// 背景颜色为白色
		this.setSize(Constent.paintPanelWidth, Constent.paintPanelHight);
		this.setLayout(null);

		/***************** 根据scrollpane设置合适大小 ************/
		this.setPreferredSize(scrollPane.getViewport().getPreferredSize());

	}

	public Vector<CurveLine> getcurveLineList() {
		return CurveLineList;
	}
	
	public  void setCurveLineList(Vector<CurveLine> CurveLineList) {
		this.CurveLineList=CurveLineList;
	}
	
	public void addCurveLine(CurveLine curveLine) {
		this.CurveLineList.add(curveLine);
	}
	public int getRootThemeX() {
		return this.rootThemeLabel.getX();
	}

	public int getRootThemeY() {
		return this.rootThemeLabel.getY();
	}

	public int getRootThemeRightX() {
		return this.rootThemeLabel.getThemeRightX();
	}

	public int getRootThemeLeftX() {
		return this.rootThemeLabel.getThemeLeftX();
	}

	public int getRootThemeMidY() {
		return this.rootThemeLabel.getThemeMidY();
	}

	public void rootThemeaddChild(ThemeLabel themeLabel) {
		this.rootThemeLabel.addChild(themeLabel);
	}

	public ThemeLabel getrootThemeLabel() {
		return this.rootThemeLabel;
	}

	public void CreatBox() {
		this.rightClickMenu.createBoxs();
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public RightClickMenu getRightClickMenu() {
		return rightClickMenu;
	}

	public void addConnectLine(ThemeLabel themeLabel, ConnectLine connectLine) {
		this.ConnectLineList.put(themeLabel, connectLine);
	}

	public ConnectLine getConnectLine(ThemeLabel themeLabel) {
		return (ConnectLine) this.ConnectLineList.get(themeLabel);

	}

	public void removeConnectLine(ThemeLabel themeLabel) {
		this.ConnectLineList.remove(themeLabel);
	}

	public void removeAllConnectLine() {
		this.ConnectLineList.clear();
		;
	}

	public HashMap<ThemeLabel, ConnectLine> getallConnectLine() {
		return this.ConnectLineList;
	}

	public void setallConnectLine(HashMap<ThemeLabel, ConnectLine> ConnectLineList) {
		this.ConnectLineList = ConnectLineList;
	}

	public void setrootThemeLabel(ThemeLabel rootThemeLabel) {
		this.rootThemeLabel = rootThemeLabel;
	}

	public void paint(Graphics g) {
		super.paint(g);
		
		/***利用迭代器避免遍历删除异常******/
		Iterator<Entry<ThemeLabel, ConnectLine>> it = ConnectLineList.entrySet().iterator();
		while (it.hasNext()) {
			Entry<ThemeLabel, ConnectLine> entry = it.next();
			ThemeLabel key = entry.getKey();
			ConnectLine val =entry.getValue();
			
			if (key.isLive) {
				this.add(key);
				if(val != null && val.isLive) {
				new PaintHelper(g).PainteLine(val);
				}
			}
			else {
				it.remove();
				this.remove(key);
			}	
		}
		
		/********画curveLine*****/
		for(int i=0;i<this.CurveLineList.size();i++) {
			CurveLine curveLine=this.CurveLineList.get(i);
			if(curveLine.isLive) {
				new PaintHelper(g).PainteCurveLine(curveLine);
				this.add(curveLine.getNode1());
				this.add(curveLine.getNode2());
			}
			else {
				this.remove(curveLine.getNode1());
				this.remove(curveLine.getNode2());
				CurveLineList.remove(curveLine);
			}
			
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// 每隔10ms刷新一下mypanel
		while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.repaint();// 必须要用repaint函数去再次执行paint函数，否则无法更新
		}
	}

	public void changestyle() {
		//遍历主题连接线，更改颜色
		for (Entry<ThemeLabel, ConnectLine> item : MainWindow.pan.getallConnectLine().entrySet()) {
			ThemeLabel key = item.getKey();
			ConnectLine val = item.getValue();
			key.setStyle();
			key.setRank(key.getRank());

			if (val != null) {
				val.setColor(ThemeChooser.LocalStyle.getlinecolor());
			}
		}
		//遍历关系线，更改颜色
		for(int i=0;i<this.CurveLineList.size();i++) {
			CurveLine curveLine=this.CurveLineList.get(i);
			curveLine.setColor(ThemeChooser.LocalStyle.getCurvelcolor());
		}
		MainWindow.pan.getrootThemeLabel().setStyle();
		MainWindow.pan.setBackground(ThemeChooser.LocalStyle.getpanelcolor());
		// MainWindow.pan.repaint();
	}

}
