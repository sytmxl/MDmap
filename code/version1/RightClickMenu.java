package version1;

import java.awt.*;

import java.awt.event.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.Set;


import javax.swing.*;
/*
 * ********************************************************
 * @author 郭家磊
 * ********************************************************
 * @function 
 * 主题类对象右键菜单的构造及相应监听功能实现
 * 面板对象右键菜单的构造及相应监听功能实现
 * 节点对象右键菜单的构造及相应监听功能实现
 * @time 2019-1-12
 * @value
 * public JPopupMenu rightMenuForPan 面板对象右键菜单
 * public JPopupMenu rightMenuForComponent 主题类对象右键菜单
 * public JPopupMenu rightMenuForNode 节点对象右键菜单
 * public JComboBox fontTypeBox 字体下拉框
 * public JComboBox fontColorBox 文字颜色下拉框
 * public JComboBox fontSizeBox 文字大小下拉框
 * public static MouseEvent componentE = null 保存产生右键菜单的组件位置
 * public static MouseEvent oldcomponentE = null 用于记录前一产生右键菜单的组件位置，便于连接线的两端的确认
 * public static CurveLine curveLine = null 记录要新建的连接类
 * @fuctions
 * public RightClickMenu() 初始化三个右键菜单并加上监听
 * public void creatIcons(JMenu menuIcon)  制造图标右键菜单栏
 * public void createBoxs() 制造出字体、颜色、大小的下拉框及相应事件响应
 * public Color parseColor(String color) 将选择颜色的汉字与Color一一对应
 * class ThemeStateChange 实现剪切、复制、粘贴的事件监听
 */
public class RightClickMenu {
	public JPopupMenu rightMenuForPan;//面板对象右键菜单
	public JPopupMenu rightMenuForComponent;//主题类对象右键菜单
	public JPopupMenu rightMenuForNode;//节点对象右键菜单
	
	
	public JComboBox fontTypeBox;//字体下拉框
	public JComboBox fontColorBox;//文字颜色下拉框
	public JComboBox fontSizeBox;//文字大小下拉框
	public static MouseEvent componentE = null;// 保存产生右键菜单的组件位置
	public static MouseEvent oldcomponentE = null;//用于记录前一产生右键菜单的组件位置，便于连接线的两端的确认
	public static CurveLine curveLine = null;//记录要新建的连接类
	/***是否建立连接线****/
	boolean isCureLine=false;
	/***初始化三个右键菜单并加上监听****/
	public RightClickMenu() {
		this.rightMenuForPan = new JPopupMenu();
		this.rightMenuForComponent = new JPopupMenu();
		this.rightMenuForNode = new JPopupMenu();
		//node内右键菜单构造
		JMenuItem delteConnect = new JMenuItem("删除连接");
		/*删除连接的监听
		 *获取连接线的起始主题类对象，将连接线对象从对应主题类的属性中删除，之后从面板pan的属性中删除
		 */
		delteConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println(curveLine.getstartLabel()==null);
				curveLine.getstartLabel().getCurveLineList().remove(curveLine);
				curveLine.getendLabel().getCurveLineList().remove(curveLine);
				MainWindow.pan.getcurveLineList().remove(curveLine);
				MainWindow.pan.remove(curveLine.getNode1());
				MainWindow.pan.remove(curveLine.getNode2());
			}
		});
		
		rightMenuForNode.add(delteConnect);
		// 面板pan内右键菜单构造
		JMenuItem createTheme = new JMenuItem("新建主题");
		createTheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ThemeLabel themeLabel1 = new ThemeLabel(componentE.getX(), componentE.getY());
				new ThemeDetect(MainWindow.pan).addConnect(themeLabel1);
			}
		});
		JMenuItem paste = new JMenuItem("粘贴", new ImageIcon(Constent.imagesPath + "editMenu\\3.png"));
		ThemeStateChange forPaste = new ThemeStateChange();
		forPaste.state = 3;
		paste.addActionListener(forPaste);
		rightMenuForPan.add(createTheme);
		
		//Ctrl+V粘贴快捷键
		paste.setAccelerator(KeyStroke.getKeyStroke('V',java.awt.Event.CTRL_MASK));
		rightMenuForPan.add(paste);
		// 组件内右键菜单构造
		JMenuItem createConnect = new JMenuItem("新建连接");
		
		JMenu menuEdit = new JMenu("编辑");
		JMenu menuIcon = new JMenu("图标");
		JMenu menuFont = new JMenu("文字");
		JMenuItem link = new JMenuItem("打开链接");
		
		/*新建连接的事件监听*/
		createConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				isCureLine=true;
				//System.out.println("新建连接");
				MainWindow.pan.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				
				
			}
		});
		
		/*超链接的事件监听*/
		link.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ThemeLabel label = (ThemeLabel) (componentE.getSource());
				String linkurl = label.getLinkURL();
				if(e.getSource() == link&&linkurl!=null) {
					URI labelurl;
					try {
						labelurl = new URI(linkurl);
						Desktop dtp=Desktop.getDesktop();
						if(Desktop.isDesktopSupported()&&dtp.isSupported(Desktop.Action.BROWSE)) {
							dtp.browse(labelurl);
						}
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		// "新增"的修改
		JMenuItem remark = new JMenuItem("修改内容");
		/*
		 * 打开修改内容的事件监听：
		 * 将对应主题类的内容修改窗口设为可见，将内容修改窗口里的显示内容设置为主题类对象的相应属性内容
		 */
		remark.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==remark){
					ThemeLabel addTo = (ThemeLabel) componentE.getSource();
					//((ThemeLabel) componentE.getSource()).getMyThemeFrame().setVisible(true);
					for (Entry<ThemeLabel, ConnectLine> item : MainWindow.pan.getallConnectLine().entrySet()) {
						ThemeLabel key = item.getKey();
						key.getMyThemeFrame().setVisible(false);
					}
					addTo.getMyThemeFrame().setVisible(true);
					addTo.getMyThemeFrame().setLocation((Constent.frameWidth-Constent.themeFrameWidth)/2,(Constent.frameHeight-Constent.themeFrameHeight)/2);
					addTo.getMyThemeFrame().setState(JFrame.NORMAL);
					addTo.getMyThemeFrame().show();
					addTo.getMyThemeFrame().getInput().setText(addTo.getText());
					addTo.getMyThemeFrame().getRemark().setText(addTo.getMyThemeFrame().getRemarkContent());
				}
			}
		});
		// "编辑"的修改
		JMenuItem shear = new JMenuItem("剪切", new ImageIcon(Constent.imagesPath + "editMenu\\1.png"));
		ThemeStateChange forShear = new ThemeStateChange();
		forShear.state = 2;
		shear.addActionListener(forShear);
		JMenuItem copy = new JMenuItem("复制", new ImageIcon(Constent.imagesPath + "editMenu\\2.png"));
		ThemeStateChange forCopy = new ThemeStateChange();
		forCopy.state = 1;
		copy.addActionListener(forCopy);
		JMenuItem delete = new JMenuItem("删除", new ImageIcon(Constent.imagesPath + "editMenu\\4.png"));
		ThemeStateChange forDelete = new ThemeStateChange();
		forDelete.state = 4;
		delete.addActionListener(forDelete);
		

		menuEdit.add(shear);
		menuEdit.add(copy);
		menuEdit.add(delete);
		// "文字"的修改
		JMenuItem fontType = new JMenuItem("字体");
		JMenuItem fontColor = new JMenuItem("颜色");
		JMenuItem fontSize = new JMenuItem("大小");
		menuFont.add(fontType);
		menuFont.add(fontColor);
		menuFont.add(fontSize);
		/*字体类型的事件监听*/
		fontType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fontTypeBox.setBounds(((Component) componentE.getSource()).getX(),
						((Component) componentE.getSource()).getY()-20, 100, 20);
				fontColorBox.setVisible(false);
				fontSizeBox.setVisible(false);
				fontTypeBox.setVisible(true);
			}
		});
		/*字体颜色的事件监听*/
		fontColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fontColorBox.setBounds(((Component) componentE.getSource()).getX(),
						((Component) componentE.getSource()).getY()-20, 100, 20);
				fontSizeBox.setVisible(false);
				fontTypeBox.setVisible(false);
				fontColorBox.setVisible(true);
			}
		});
		/*字体大小的事件监听*/
		fontSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fontSizeBox.setBounds(((Component) componentE.getSource()).getX(),
						((Component) componentE.getSource()).getY()-20, 100, 20);
				fontTypeBox.setVisible(false);
				fontColorBox.setVisible(false);
				fontSizeBox.setVisible(true);
			}
		});
		// "图标"的修改
		creatIcons(menuIcon);// 图标构造代码过长，出于美观考虑单独列出构造图标的方法
		// 一级菜单的的修改
		rightMenuForComponent.add(remark);
		rightMenuForComponent.add(menuEdit);
		rightMenuForComponent.add(menuIcon);
		rightMenuForComponent.add(menuFont);
		rightMenuForComponent.add(createConnect);
		rightMenuForComponent.add(link);
	}

	/*制造图标菜单栏
	 *建立名称的二维数组与images文件夹里的各个文件夹里的图片相对应，节省代码
	 */
	public void creatIcons(JMenu menuIcon) {
		String[] menusName = { "箭头", "月份", "旗子", "完成度", "星期", "优先度" };
		String[] fileName = { "arrow", "month", "flag", "degreeofcompletion", "weekday", "priority" };
		int[] nums = { 9, 12, 9, 10, 7, 9 };
		String[][] arrays = { { "向上", "右上", "向右", "右下", "向下", "左下", "向左", "左上", "刷新" },
				{ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" },
				{ "蓝色", "灰色", "蓝绿", "黑色", "绿色", "橙色", "紫色", "红色", "黄色" },
				{ "1/8", "1/4", "3/8", "1/2", "5/8", "3/4", "7/8", "完成", "未开始", "暂停" },
				{ "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天" }, { "1", "2", "3", "4", "5", "6", "7", "8", "9" } };
		for (int j = 0; j < 6; j++) {
			JMenu temp = new JMenu(menusName[j]);
			menuIcon.add(temp);
			for (int i = 0; i < nums[j]; i++) {
				setIconsMenuItem(temp, arrays[j][i], fileName[j], i + 1);
			}
		}
		JMenuItem deleteIcon = new JMenuItem("删除图标");
		deleteIcon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ThemeLabel addTo = (ThemeLabel) componentE.getSource();
				addTo.setIcon(null);
				if(addTo.getIconNum()==1){
					addTo.setIconNum(0);
					int deltaSizex=addTo.getNewLength()-addTo.getSizeX();
					addTo.updateSize();
					new ThemeDetect(MainWindow.pan).themesizeChangeMove(addTo,deltaSizex);
				}
			}
		});
		menuIcon.add(deleteIcon);
	}

	// 用于辅助建造 菜单项 “图标”的方法
	public void setIconsMenuItem(JMenu menu, String itemName, String path, int i) {
		JMenuItem item = new JMenuItem(itemName);
		ImageIcon tempIcon = new ImageIcon(Constent.imagesPath + path + '\\' + i + ".png");
		item.setIcon(tempIcon);
		item.setPreferredSize(new Dimension(80, 40));
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ThemeLabel addTo = (ThemeLabel) componentE.getSource();
				addTo.setIcon(tempIcon);
				if(addTo.getIconNum()==0){
					addTo.setIconNum(1);
					int deltaSizex=addTo.getNewLength()-addTo.getSizeX();
					addTo.updateSize();
					new ThemeDetect(MainWindow.pan).themesizeChangeMove(addTo,deltaSizex);
				}
			}
		});
		menu.add(item);
	}

	// 制造出字体、颜色、大小的下拉框及相应事件响应
	public void createBoxs() {
		String[] fontTypes = { "黑体","楷体", "仿宋", "华文彩云", "华文新魏", "华文琥珀", "华文行楷", "微软雅黑", "方正姚体", "等线" };
		String[] fontColors = { "黑色", "蓝色", "蓝绿", "深灰", "灰色", "绿色", "淡灰", "品红", "橙色", "粉色", "红色", "白色", "黄色" };
		String[] fontSize = { "15", "20", "25", "30", "35", "40" };
		// fontTypeBox设置
		fontTypeBox = new JComboBox(fontTypes);
		fontTypeBox.setMaximumRowCount(4);// 最多显示4个选项
		fontTypeBox.setBounds(2500, 2500, 100, 20);
		fontTypeBox.setVisible(false);
		MainWindow.pan.add(fontTypeBox);
		/*********** 加入改变字体选项的事件监听 *********/
		fontTypeBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String fontType = (String) e.getItem();
					JLabel label = (JLabel) (componentE.getSource());
					Font tempFont = label.getFont();
					label.setFont(new Font(fontType, tempFont.getStyle(), tempFont.getSize()));
					fontTypeBox.setVisible(false);
				}
			}
		});
		// fontColorBox设置
		fontColorBox = new JComboBox(fontColors);
		fontColorBox.setMaximumRowCount(4);
		fontColorBox.setBounds(2500, 2500, 100, 20);
		fontColorBox.setVisible(false);
		MainWindow.pan.add(fontColorBox);
		/*********** 加入改变字体颜色的事件监听 *********/
		fontColorBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String fontColor = (String) e.getItem();
					JLabel label = (JLabel) (componentE.getSource());
					Color tempColor = parseColor(fontColor);
					if (tempColor != null) {
						label.setForeground(tempColor);
					}
					fontColorBox.setVisible(false);
				}
			}
		});
		// fontSizeBox设置
		fontSizeBox = new JComboBox(fontSize);
		fontSizeBox.setMaximumRowCount(4);
		fontSizeBox.setBounds(2500, 2500, 100, 20);
		fontSizeBox.setVisible(false);
		MainWindow.pan.add(fontSizeBox);
		/*********** 加入改变字大小选项的事件监听 *********/
		fontSizeBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String fontSize = (String) e.getItem();
					ThemeLabel label = (ThemeLabel) (componentE.getSource());
					Font tempFont = label.getFont();
					label.setFont(new Font(tempFont.getName(), tempFont.getStyle(), Integer.parseInt(fontSize)));
					fontTypeBox.setVisible(false);
					int deltaSizex=label.getNewLength()-label.getSizeX();
					label.updateSize();
					new ThemeDetect(MainWindow.pan).themesizeChangeMove(label,deltaSizex);
				}
			}
		});
	}

	// 将选择颜色的汉字与Color一一对应
	public Color parseColor(String color) {
		if ("黑色".equals(color)) {
			return Color.BLACK;
		}
		if ("蓝色".equals(color)) {
			return Color.BLUE;
		}
		if ("蓝绿".equals(color)) {
			return Color.CYAN;
		}
		if ("深灰".equals(color)) {
			return Color.DARK_GRAY;
		}
		if ("灰色".equals(color)) {
			return Color.GRAY;
		}
		if ("绿色".equals(color)) {
			return Color.GREEN;
		}
		if ("淡灰".equals(color)) {
			return Color.LIGHT_GRAY;
		}
		if ("品红".equals(color)) {
			return Color.MAGENTA;
		}
		if ("橙色".equals(color)) {
			return Color.ORANGE;
		}
		if ("粉色".equals(color)) {
			return Color.PINK;
		}
		if ("红色".equals(color)) {
			return Color.RED;
		}
		if ("白色".equals(color)) {
			return Color.WHITE;
		}
		if ("黄色".equals(color)) {
			return Color.YELLOW;
		}
		return null;
	}
	
	public boolean getisCureLine() {
		return isCureLine;
	}
	public void setisCureLine(boolean b) {
		this.isCureLine=b;
	}
}

class fontMouseListener extends MouseAdapter {
	int lastClickX;
	int lastClickY;

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == e.BUTTON1) {
			lastClickX = e.getX();
			lastClickY = e.getY();
		}
	}
}

//实现剪切、复制、粘贴的事件监听
class ThemeStateChange implements ActionListener {
	public static final int COPY = 1;
	public static final int SHEAR = 2;
	public static final int PASTE = 3;
	public static final int DELETE = 4;
	public static boolean canPaste = false;
	public static boolean isShear = false;
	public static ThemeLabel sourceLabel = null;
	public int state = 0;

	public void actionPerformed(ActionEvent e) {
		if (state == COPY) {//判断选中复制
			canPaste = true;
			sourceLabel = (ThemeLabel) RightClickMenu.componentE.getSource();
		} else if (state == SHEAR) {//判断选中剪切
			canPaste = true;
			sourceLabel = (ThemeLabel) RightClickMenu.componentE.getSource();
			isShear = true;
		} else if (state == PASTE) {//判断选中粘贴
			if (canPaste) {//判断是否有内容可以粘贴
				canPaste = false;
				if (isShear) {//判断可以粘贴内容来自剪切
					/*
					 * 直接按移动处理
					 */
					isShear = false;
					if (sourceLabel != MainWindow.pan.getrootThemeLabel()) {//根主题不能剪切
						int moveX=RightClickMenu.componentE.getX()-sourceLabel.getX();
						int moveY=RightClickMenu.componentE.getY()-sourceLabel.getY();
						new ThemeDetect(MainWindow.pan).updateConnect(sourceLabel, moveX, moveY);
					}
				}else if (sourceLabel != MainWindow.pan.getrootThemeLabel()) {//判断可以粘贴内容来自非根主题的复制
					//根节点的复制分类处理
					int num=0;
					
					HashMap<ThemeLabel, ConnectLine> tempConnectLineList = new HashMap<ThemeLabel, ConnectLine>();//新建HashMap
					copyThemeLabel(sourceLabel,tempConnectLineList);//调用函数将sourceLabel及其所有子主题复制一份放入新建的HashMap
					ThemeLabel root=null;
					//便利新建HashMap，找到其中的被复制的最大主题对象的父亲，用root记录
		    		Set<Entry<ThemeLabel, ConnectLine>> temp2 = tempConnectLineList.entrySet();
					for (Entry<ThemeLabel, ConnectLine> item : temp2) {
						ThemeLabel key = item.getKey();
						MainWindow.pan.remove(key);
						key.getMyThemeFrame().getInput().setText(null);
						key.getMyThemeFrame().getRemark().setText(null);
						key.getMyThemeFrame().getLink().setText(null);
						if(key==sourceLabel){
							root=key.getFather();
							key.setFather(null);
						}
					}
					tempConnectLineList = (HashMap<ThemeLabel, ConnectLine>) copyObject(tempConnectLineList);//调用复制方法，将HashMap复制
					sourceLabel.setFather(root);
					/*
					 *遍历保存有 所有复制后的主题类ThemeLabel对象和以其为入度的连接线ConnectLine对 的HashMap
					 *对所有复制后的主题类ThemeLabel对象加入相应监听
					 */
					for (Entry<ThemeLabel, ConnectLine> item : tempConnectLineList.entrySet()) {
						
						//System.out.println("加一");
						ThemeLabel key = item.getKey();
						key.getMyThemeFrame().getButton1().addActionListener(new ThemeFrameActionListener(key.getMyThemeFrame()));
						ConnectLine val = item.getValue();
						ComponentMouseListener componentMouseListener1=new ComponentMouseListener(key);
						key.addMouseListener(componentMouseListener1);//加入鼠标事件监听
						key.addMouseMotionListener(componentMouseListener1);
						MainWindow.pan.add(key);
						MainWindow.pan.getallConnectLine().put(key, val);
						if(key.getFather()==null){
							root=key;
						}
					}
					
					int moveX=RightClickMenu.componentE.getX()-sourceLabel.getX();
					int moveY=RightClickMenu.componentE.getY()-sourceLabel.getY();
					if(root==null){
						System.out.println("空的");
					}
		    		new ThemeDetect(MainWindow.pan).updateConnect(root, moveX, moveY);
				}
			}
		} else if (state == DELETE) {//判断选中删除
			sourceLabel = (ThemeLabel) RightClickMenu.componentE.getSource();
			if ((ThemeLabel) sourceLabel != MainWindow.pan.getrootThemeLabel()) {
			new ThemeDetect(MainWindow.pan).deleteConnect((ThemeLabel) sourceLabel);//调用相应的删除方法
			}
		}
	}

	//复制ThemeLabel及其所有孩子
    ThemeLabel copyThemeLabel(ThemeLabel source,HashMap<ThemeLabel, ConnectLine> tempConnectLineList){
    	if(source!=null){
    		tempConnectLineList.put(source,MainWindow.pan.getallConnectLine().get(source));
    		for(ThemeLabel temp:source.getallChild()){
    			copyThemeLabel(temp,tempConnectLineList);
    		}
    		return source;
    	}
    	return null;
    }
    //深克隆原对象
	Object copyObject(Object source) {
		Object copyTo =null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(bos);
			oos.writeObject(source);
			byte[] tb1 =bos.toByteArray();
			ByteArrayInputStream bis = new ByteArrayInputStream(tb1);
			ObjectInputStream ois =new ObjectInputStream(bis);
			copyTo =ois.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return copyTo;
	}
	
	void copyAllSon(ThemeLabel source,ThemeLabel copyTo){
		Vector<ThemeLabel> child=source.getallChild();
		if(child.size()!=0){
			for(ThemeLabel temp:child){
				
				copyAllSon(temp,copyTo);
			}
		}				
	}
	
}
