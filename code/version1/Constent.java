package version1;

import java.awt.*;

/**
 * ********************************************************
 * @author 郭晟
 * ********************************************************
 * @function 
 * 定义了程序使用到的所有常量和风格枚举变量
 * @time 2019-1-12
 * @value
 * paintPanel参数
 * 	paintPanelWidth paintPanelHight 面板的宽高
 * frame参数
 * 	frameHeight frameWidth 窗口的宽高
 * ThemeLabel参数
 * 	ThemeSizeX ThemeSizeY 主题默认宽高
 * 	LabelThickness 主题边框厚度
 * 	themeFont 主题文字字体
 * ThemeFrame参数
 * 	themeFrameHeight themeFrameWidth 修改主题窗口宽高
 * imagesPath图片路径
 * 连接线参数
 * minDistancex minDistancey 检测连接线范围
 * addRight addLeft 左右翻转
 * 颜色与主题
 * Maskbkg Maskbd 选中边框和高亮
 * LightGreen Pink DarkPurpule Purpule LightRed 风格Dracula主题色
 * Orange Lime LightGery DarkGrey 风格Sublime主题色
 * public enum StyleEnum 风格枚举型变量
 * 
 * private StyleEnum(Color bdcolor,Color bkgcolor,Color txtcolor,
	    		Color linecolor,Color panelcolor,Color curvelcolor,Color btncolor) 构造函数
 */

public class Constent {
	
	/*******paintPanel参数******/
	public static int paintPanelWidth=3000;
	public static int paintPanelHight=3000;
	
	/*****frame参数*******/
	public static int frameHeight=Toolkit.getDefaultToolkit().getScreenSize().height*2/3;
	public static int frameWidth=Toolkit.getDefaultToolkit().getScreenSize().width*2/3;
	
	public static int frameLocationX=Toolkit.getDefaultToolkit().getScreenSize().width/2;
	public static int frameLOcationY=Toolkit.getDefaultToolkit().getScreenSize().height/2;
	
	/*******ThemeLabel参数*******/
	public static int ThemeSizeX=70;
	public static int ThemeSizeY=50;
	public static float LabelThickness=3.5f;
	public static Font themeFont=new Font("黑体",Font.BOLD,25);
	/*******ThemeFrame参数*******/
	public static int themeFrameHeight=400;
	public static int themeFrameWidth=600;
	

	/***********图片路径*********/
	public static String imagesPath;
	/*********连接参数*********/
	public static int minDistancex=180;
	public static int minDistancey=200; 
	public static int addRight=1;
	public static int addLeft=-1;
	/********颜色与主题*******/
	//选中的边框和高亮色
	public final static Color Maskbkg     = new Color(0,0,255,20);
	public final static Color Maskbd     = new Color(0,0,0,60);	
	//Dracula主题色
	public final static Color LightGreen     = new Color(80, 250, 123);	
	public final static Color Pink     = new Color(255, 121, 198);	
	public final static Color DarkPurpule     = new Color(40, 42, 54);	
	public final static Color Purpule     = new Color(189,147,249);
	public final static Color LightRed     = new Color(255,85,85);
	//Sublime主题色
	public final static Color Orange     = new Color(204, 108, 29);	
	public final static Color Lime     = new Color(151, 236, 34);	
	public final static Color LightGery     = new Color(128,128,128);	
	public final static Color DarkGrey     = new Color(47, 47, 47);	
	//风格枚举
	public enum StyleEnum {
		//三种主题配色
		Dracula(Purpule,DarkPurpule,Pink,
				LightRed,DarkPurpule,LightGreen,Color.WHITE),
		Sublime(Orange,DarkGrey,Lime,
				LightGery,DarkGrey,new Color(204, 129, 167),Color.WHITE),
		Classic(new Color(85, 142, 213),Color.WHITE,new Color(55, 96, 146),
				new Color(133, 174, 255),Color.WHITE,new Color(0, 0, 255,40),Color.BLACK);

		//组件颜色属性
	    private final Color bdcolor;//主题边框
	    private final Color bkgcolor;//主题底色
	    private final Color txtcolor;//主题文字
	    private final Color linecolor;//连接线
	    private final Color panelcolor;//面板底色
	    private final Color curvelcolor;//关系线
	    private final Color btncolor;//三级主题下划线
  
	    //风格枚举构造方法
	    private StyleEnum(Color bdcolor,Color bkgcolor,Color txtcolor,
	    		Color linecolor,Color panelcolor,Color curvelcolor,Color btncolor){
	        this.bdcolor = bdcolor;
	        this.bkgcolor = bkgcolor;
	        this.txtcolor = txtcolor;
	        this.linecolor = linecolor;
	        this.panelcolor = panelcolor;
	        this.curvelcolor = curvelcolor;
	        this.btncolor = btncolor;
	    }
	    //获取方法
	    public Color getbdcolor() {
	        return this.bdcolor;
	    }
	    public Color getbkgcolor() {
	        return this.bkgcolor;
	    }
	    public Color gettxtcolor() {
	        return this.txtcolor;
	    }
	    public Color getlinecolor() {
	        return this.linecolor;
	    }	    
	    public Color getpanelcolor() {
	        return this.panelcolor;
	    }
		public Color getCurvelcolor() {
			return curvelcolor;
		}
		public Color getBtncolor() {
			return btncolor;
		}	
	    
	    
	}
}