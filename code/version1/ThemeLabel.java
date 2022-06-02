/**
 * 
 */
package version1;

import java.awt.*;

import java.util.*;
import javax.swing.*;
/**
 * @author 郭晟
 * @function 
 * 主题类，放置于软件面板，实现基本思维导图绘画功能
 * @time 2019-1-12
 * @value
 * private int iconNum = 0; 图标编号
 * private ThemeLabel father = null;指向父亲主题
 * private Vector<ThemeLabel> child = null;孩子主题向量
 * private Vector<CurveLine>CurveLineList=null;连接线向量
 * private ThemeFrame myThemeFrame = null;主题修改窗口
 * 主题尺寸
 * 	private int ThemeSizeX;
 * 	private int ThemeSizeY;
 * 端点坐标
 * 	private int ThemeLeftX, ThemeRightX, ThemeMidY;
 * private String linkURL =null;超链接地址
 * public boolean isLive = true;是否存活
 * @functions
 * public ThemeLabel(int x, int y)构造函数，将默认主题放置于面板指定坐标
 * 连接线列表操作
 * public boolean isInTree()
 * public boolean isChild(ThemeLabel themeLabel)
 * public void addChild(ThemeLabel child)
 * public void removeChild(ThemeLabel child)
 * public void setFather(ThemeLabel father)
 * public ThemeLabel getFather()
 * public Vector<ThemeLabel> getallChild()
 * public ThemeLabel getChild(int index) {
 * 关系线列表操作
 * public Vector<CurveLine> getCurveLineList()
 * public void addCurveLine(CurveLine curveLine)
 * public void removeCurveLine(CurveLine curveLine) 
 * 更新位置大小文字长度
 * public void updateLocation(int x, int y)
 * public void updateSize()
 * public int getNewLength()
 * 多级主题显示
 * public int getRank()
 * public void setRank(int Rank)
 * 更新风格
 * public void setStyle() {
 * 超链接
 * public String getLinkURL()
 * public void setLinkURL(String linkURL)
 * 修改窗口
 * public ThemeFrame getMyThemeFrame()
 * public void setMyThemeFrame(ThemeFrame myThemeFrame)
 */
public class ThemeLabel extends JLabel {

	/******** 图标，继承JL Abel ********/
	private int iconNum = 0;
	/******* 父亲孩子 *****/
	private ThemeLabel father = null;
	private Vector<ThemeLabel> child = null;
	/*********连接线******/
	private Vector<CurveLine>CurveLineList=null;
	/******* 修改及备注类 *****/
	private ThemeFrame myThemeFrame = null;
	/********* 颜色 ********/
	private Color bdcolor = Color.BLUE;
	private Color bkgcolor = Color.GREEN;
	private Color txtcolor = Color.BLACK;
	/********* 尺寸 ********/
	private int ThemeSizeX = Constent.ThemeSizeX;
	private int ThemeSizeY = Constent.ThemeSizeY;
	private int ThemeLeftX, ThemeRightX, ThemeMidY;
	//超链接
	private String linkURL =null;

	public boolean isLive = true;

	public ThemeLabel(int x, int y) {
		
		super("",JLabel.CENTER);
		//初始化主题属性，关联连接线和关系线列表
		this.child = new Vector<ThemeLabel>();
		this.CurveLineList=new Vector<CurveLine>();
		this.ThemeLeftX = x;
		this.ThemeRightX = x + Constent.ThemeSizeX;
		this.ThemeMidY = y + Constent.ThemeSizeY / 2;

		this.setFont(Constent.themeFont);
		this.setText("主题");
		this.setBounds(x, y, this.ThemeSizeX, this.ThemeSizeY);
		
		this.setStyle();
		
		this.setOpaque(true);
		this.setVisible(true);
		//初始化修改窗口
		this.myThemeFrame = new ThemeFrame(this);
		/******** 设置鼠标的监听 ************/
		ComponentMouseListener componentMouseListener = new ComponentMouseListener(this);
		this.addMouseListener(componentMouseListener);// 加入鼠标事件监听
		this.addMouseMotionListener(componentMouseListener);
	}
	
	public boolean isInTree() {
		ThemeLabel temp=this;
		while(temp!=null&&temp.getFather()!=null) {
			temp=temp.getFather();
		}
		return temp==MainWindow.pan.getrootThemeLabel();
	}
	public Vector<CurveLine> getCurveLineList(){
		return this.CurveLineList;
	}
	public void addCurveLine(CurveLine curveLine) {
		this.CurveLineList.add(curveLine);
	}
	public void removeCurveLine(CurveLine curveLine) {
		this.CurveLineList.remove(curveLine);
		curveLine.isLive=false;
	}
	public void updateLocation(int x, int y) {
		this.ThemeLeftX = x;
		this.ThemeRightX = x + this.ThemeSizeX;
		this.ThemeMidY = y + this.ThemeSizeY / 2;
		this.setBounds(x, y, this.ThemeSizeX, this.ThemeSizeY);
	}


	public void updateSize() {
		int deltaSizeX = this.getNewLength() - this.ThemeSizeX;
		int deltaSizeY = 0;
		this.ThemeSizeX += deltaSizeX;
		this.ThemeSizeY += deltaSizeY;
		this.ThemeMidY +=deltaSizeY / 2;
		System.out.println(this.ThemeSizeX+","+this.ThemeSizeY);
		if (this.getThemeRightX() < MainWindow.pan.getRootThemeLeftX()) {
			this.ThemeLeftX = this.getX() - deltaSizeX;
			this.setBounds(this.getX() - deltaSizeX, this.getY(), this.ThemeSizeX, this.ThemeSizeY);
		} else {
			this.ThemeRightX = this.getX() + this.ThemeSizeX;
			this.setBounds(this.getX(), this.getY(), this.ThemeSizeX, this.ThemeSizeY);
		}

	}

	public int getNewLength() {
		String str = this.getText();
		// 计算str中中文字符个数
		int count = 0;
		char[] c = str.toCharArray();
		for (int i = 0; i < c.length; i++) {
			String len = Integer.toBinaryString(c[i]);
			if (len.length() > 8)
				count++;
		}
		double chineseSize = 16.5/ 15;
		double englishSize = 15.0 / 25;
		int fontSize = this.getFont().getSize();
		if (fontSize == 10) {
			chineseSize = 18 / 15;
			englishSize = 16.0 / 25;
		}
		if (fontSize == 15) {
			chineseSize = 17.5 / 15;
			englishSize = 16.0 / 25;
		}
		if (count <6) {
			chineseSize = 20.0 / 15;
		}
		if (count <2) {
			chineseSize = 25.0 / 15;
		}
		if (str.length() - count <6) {
			englishSize = 18.0 / 25;
		}
		if (str.length() - count <2) {
			englishSize = 30.0 / 25;
		}
		return (int) ((str.length() - count) * (fontSize * englishSize)) + 38 * (this.getIconNum() == 1 ? 1 : 0)
				+ (int) (count * (fontSize * chineseSize));

	}

	public int getSizeX() {
		return this.ThemeSizeX;
	}

	public boolean isChild(ThemeLabel themeLabel) {
		return this.child.contains(themeLabel);
	}

	public void addChild(ThemeLabel child) {
		this.child.add(child);
	}

	public void removeChild(ThemeLabel child) {
		this.child.remove(child);
	}

	public void setFather(ThemeLabel father) {
		this.father = father;
	}

	public ThemeLabel getFather() {
		return this.father;
	}

	public Vector<ThemeLabel> getallChild() {
		return this.child;
	}

	public ThemeLabel getChild(int index) {
		return this.child.get(index);
	}

	public void setThemeLeftX(int themeLeftX) {
		ThemeLeftX = themeLeftX;
	}

	public void setThemeRightX(int themeRightX) {
		ThemeRightX = themeRightX;
	}

	public void setThemeMidY(int themeMidY) {
		ThemeMidY = themeMidY;
	}

	public int getThemeLeftX() {
		return ThemeLeftX;
	}

	public int getThemeRightX() {
		return ThemeRightX;
	}

	public int getThemeMidY() {
		return ThemeMidY;
	}

	public ThemeFrame getMyThemeFrame() {
		return myThemeFrame;
	}

	public void setMyThemeFrame(ThemeFrame myThemeFrame) {
		this.myThemeFrame = myThemeFrame;
	}

	public int getThemeSizeX() {
		return ThemeSizeX;
	}

	public void setThemeSizeX(int themeSizeX) {
		ThemeSizeX = themeSizeX;
	}

	public int getIconNum() {
		return iconNum;
	}

	public void setIconNum(int iconNum) {
		this.iconNum = iconNum;
	}

	public int getThemeSizeY() {
		return ThemeSizeY;
	}

	public void setThemeSizeY(int themeSizeY) {
		ThemeSizeY = themeSizeY;
	}
	
	public String getLinkURL() {
		return linkURL;
	}

	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}
	public int getRank() {
		if(this == MainWindow.pan.getrootThemeLabel()) {
			return -1;
		}
		else if(this.father == null)
		{
			return 0;
		}else if(this.father.father ==null)
		{
			return 1;
		}else {
			return 2;
		}
	}
	public void setRank(int Rank)
	{
		switch(Rank) {
			case -1:
				this.ThemeSizeX += 50;
				this.ThemeSizeY += 20;
				//this.updateSize();
				break;
			case 0:
				this.setBorder(BorderFactory.createLineBorder(ThemeChooser.LocalStyle.getbdcolor(),4,true));
				break;
			case 1:
				this.setBorder(BorderFactory.createLineBorder(ThemeChooser.LocalStyle.getbdcolor(),4,true));
				break;				
			case 2:
				this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ThemeChooser.LocalStyle.getBtncolor()));
				break;
		}
	}
	public void setStyle() {
		
		this.setBorder(BorderFactory.createLineBorder(ThemeChooser.LocalStyle.getbdcolor(),4,true));
		this.setBackground(ThemeChooser.LocalStyle.getbkgcolor());	
		this.setForeground(ThemeChooser.LocalStyle.gettxtcolor());
	}
}
