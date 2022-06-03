package version1;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;

/**
 * @author wangxianke guojialei guosheng
 * 
 */
/***
 * 监听主题，并作相应动作 1.双击新建主题 2.单击连接曲线 3.拖动主题及连接线、曲线 4.右键面板菜单 5.鼠标进入主题蒙板
 */
class ComponentMouseListener implements MouseInputListener {
	/***
	 * int startX,startY:鼠标起始位置 ThemeLabel Label:监听主题 JLabel Mask:主题蒙板 boolean
	 * isClickLeftAndDrag:是否拖拽模式
	 */
	int startX = 0;
	int startY = 0;
	ThemeLabel Label = null;
	JLabel Mask = null;
	boolean isClickLeftAndDrag = false;

	/**
	 * 构造方法
	 * 
	 * @param themeLabel:监听主题
	 */
	public ComponentMouseListener(ThemeLabel themeLabel) {
		this.Label = themeLabel;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		/***** 鼠标左键单击,获取当前鼠标位置,并设置为拖拽模式 *********/
		if (e.getButton() == e.BUTTON1) {
			this.startX = e.getX();
			this.startY = e.getY();
			/*** 开启拖拽模式 *******/
			isClickLeftAndDrag = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		// TODO Auto-generated method stub
		/***** 关闭字体的三个下拉框 ***/
		clearBoxs();
		if (e.getButton() == e.BUTTON1) {
			/***** 关闭拖拽模式 *******/
			isClickLeftAndDrag = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if (isClickLeftAndDrag) {

			int x = e.getX();
			int y = e.getY();

			/***** 为根主题时移动所有节点 *******/
			if ((ThemeLabel) this.Label == MainWindow.pan.getrootThemeLabel()) {
				new ThemeDetect(MainWindow.pan).moveAll(x - this.startX, y - this.startY);
				/**** 光标变为手型 ******/
				this.Label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			/**** 普通主题移动自己及所有孩子 ******/
			else {
				new ThemeDetect(MainWindow.pan).updateConnect((ThemeLabel) this.Label, x - this.startX,
						y - this.startY);
			}
			/**** 更新蒙版位置 *******/
			Mask.setLocation(Label.getX() - 5, Label.getY() - 5);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		clearBoxs();
		int clickType = e.getButton();
		/****** 右键 *********/
		if (clickType == e.BUTTON3) {
			/********* 展开右键菜单栏 ******/
			PaintePanel.rightClickMenu.rightMenuForComponent.show(MainWindow.pan, e.getX() + this.Label.getX(),
					e.getY() + this.Label.getY());
			/******** 传递产生右键菜单的组件位置，确定Box的位置 **********/
			PaintePanel.rightClickMenu.componentE = e;
		}
		/****** 左键 *********/
		else if (clickType == e.BUTTON1) {
			/***** 单击 ********/
			if (e.getClickCount() == 1) {
				/********* 如果是绘制曲线模式 ******/
				if (PaintePanel.rightClickMenu.getisCureLine()) {
					/******** 获得曲线起点终点的主题ThemeLabel *********/
					PaintePanel.rightClickMenu.oldcomponentE = PaintePanel.rightClickMenu.componentE;
					PaintePanel.rightClickMenu.componentE = e;
					ThemeLabel startLabel = (ThemeLabel) (PaintePanel.rightClickMenu.oldcomponentE.getSource());
					ThemeLabel endLabel = (ThemeLabel) (PaintePanel.rightClickMenu.componentE.getSource());
					/****** 添加曲线 ******/
					new ThemeDetect(MainWindow.pan).addCurveLine(startLabel, endLabel);
					/******** 关闭绘制曲线模式 *******/
					PaintePanel.rightClickMenu.setisCureLine(false);
					/***** 光标变回箭头 *******/
					MainWindow.pan.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
			/******* 双击并且不为绘制曲线模式 ********/
			else if (e.getClickCount() == 2 && !PaintePanel.rightClickMenu.getisCureLine())// 双击
			{
				System.out.println("double click");
				/******** 关闭其它备注窗口 *******/
				for (Entry<ThemeLabel, ConnectLine> item : MainWindow.pan.getallConnectLine().entrySet()) {
					ThemeLabel key = item.getKey();
					key.getMyThemeFrame().setVisible(false);
				}
				/****** 打开本主题备注窗口 *******/
				this.Label.getMyThemeFrame().setVisible(true);
				this.Label.getMyThemeFrame().setLocation((Constent.frameWidth - Constent.themeFrameWidth) / 2,
						(Constent.frameHeight - Constent.themeFrameHeight) / 2);
				this.Label.getMyThemeFrame().setState(JFrame.NORMAL);
				this.Label.getMyThemeFrame().show();
				/***** 设置标题文字 **********/
				this.Label.getMyThemeFrame().getInput().setText(this.Label.getText());
				/***** 设置备注文字 **********/
				this.Label.getMyThemeFrame().getRemark().setText(this.Label.getMyThemeFrame().getRemarkContent());
				this.Label.getMyThemeFrame().getLink().setText(this.Label.getLinkURL());
			}
		}
	}

	/***** 鼠标进入显示蒙板 ******/
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		Mask = new JLabel();
		/******** 设置蒙板属性 ******/
		Mask.setLocation(Label.getX() - 5, Label.getY() - 5);
		Mask.setSize(Label.getSize());
		Mask.setBounds(Label.getX() - 5, Label.getY() - 5, Label.getSizeX() + 10, Label.getThemeSizeY() + 10);
		Mask.setOpaque(true);
		/******* 如果绘制曲线模式蒙版颜色加深 ******/
		if (PaintePanel.rightClickMenu.getisCureLine()) {
			Mask.setBackground(new Color(0, 0, 255, 60));
		} else {
			Mask.setBackground(Constent.Maskbkg);
		}
		Mask.setBorder(BorderFactory.createLineBorder(Constent.Maskbd));

		MainWindow.pan.add(Mask);
		Mask.setVisible(true);
	}

	/****** 鼠标退出主题设置蒙版不可见 *******/
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		Mask.setVisible(false);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		//
	}

	/****** 关闭字体的三个下拉栏 *******/
	private void clearBoxs() {
		if (PaintePanel.rightClickMenu != null) {
			PaintePanel.rightClickMenu.fontTypeBox.setVisible(false);
			PaintePanel.rightClickMenu.fontColorBox.setVisible(false);
			PaintePanel.rightClickMenu.fontSizeBox.setVisible(false);
		}
	}
}
