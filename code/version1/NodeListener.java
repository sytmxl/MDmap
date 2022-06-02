package version1;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;
/** 
 * @author wangxianke
 */
/**
 * 1.监听曲线上两个节点JLbel node1,node2; 2.移动node1或node2拖动曲线； 3.右键node1或node2删除曲线。
 */
public class NodeListener implements MouseInputListener {
	/**
	 * Point start:鼠标初始位置 boolean isClickLeftAndDrag：是否为拖动模式 CurveLine
	 * curveLine：监听的曲线
	 */
	Point start = null;
	boolean isClickLeftAndDrag = false;
	CurveLine curveLine = null;

	/**
	 * @param curveLine：监听的曲线
	 */
	public NodeListener(CurveLine curveLine) {
		this.curveLine = curveLine;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int clickType = e.getButton();
		if (clickType == e.BUTTON3)
			/**** 右键展开菜单 *********/
			PaintePanel.rightClickMenu.rightMenuForNode.show(MainWindow.pan, ((JLabel) e.getSource()).getX(),
					((JLabel) e.getSource()).getY());/**** 传递产生右键菜单的连接线 ***/
		PaintePanel.rightClickMenu.curveLine = this.curveLine;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		/******* 左键获取鼠标起始位置，启动拖拽模式 *********/
		if (e.getButton() == e.BUTTON1) {
			start = e.getPoint();
			isClickLeftAndDrag = true;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		/******* 拖动模式时不断更新拖动节点位置，从而改变曲线形状 ************/
		if (isClickLeftAndDrag) {
			JLabel node = (JLabel) e.getSource();
			Point now = e.getPoint();
			Point p = new Point(now.x - start.x + node.getX(), now.y - start.y + node.getY());
			if (node == this.curveLine.getNode1()) {
				this.curveLine.updateNode1(p);
			} else if (node == this.curveLine.getNode2()) {
				this.curveLine.updateNode2(p);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		/***** 左键且拖拽模式，更新曲线形状，并关闭拖动模式 **************/
		if (isClickLeftAndDrag && e.getButton() == e.BUTTON1) {
			JLabel node = (JLabel) e.getSource();
			Point now = e.getPoint();
			Point p = new Point(now.x - start.x + node.getX(), now.y - start.y + node.getY());
			if (node == this.curveLine.getNode1()) {
				this.curveLine.updateNode1(p);
			} else if (node == this.curveLine.getNode2()) {
				this.curveLine.updateNode2(p);
			}
			isClickLeftAndDrag = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
