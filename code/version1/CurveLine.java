package version1;

import java.awt.*;
import javax.swing.*;

/**
 * @author wangxianke 2019-1-12
 *1.4个点之间有3条贝塞尔曲线，本类负责保存有关贝塞尔曲线属性
 *2.通过右键菜单实现手动连接曲线
 */
/******* 贝塞尔曲线类 ********/
public class CurveLine implements java.io.Serializable {
	/**
	 * Point[] points:贝塞尔曲线四个点坐标 
	 * Color color:曲线颜色 
	 * JLabel node1,node2:拖动节点
	 *  boolean isLive:状态
	 *   ThemeLabel startLabel,endLabel:起点、终点主题
	 */
	private Point[] points = null;
	private Color color = null;
	private JLabel node1 = null, node2 = null;
	public boolean isLive = true;
	private ThemeLabel startLabel = null;
	private ThemeLabel endLabel = null;

	/**
	 * 
	 * @param start:曲线起点
	 * @param end:曲线终点
	 * @param startLabel:起点主题
	 * @param endLabel:终点主题
	 */
	public CurveLine(Point start, Point end, ThemeLabel startLabel, ThemeLabel endLabel) {
		this.startLabel = startLabel;
		this.endLabel = endLabel;
		/********* 由风格选择类设置颜色 ********/
		color = ThemeChooser.LocalStyle.getCurvelcolor();
		/******* 曲线其它属性 ********/
		points = new Point[4];
		points[0] = new Point(start.x, start.y);
		points[1] = new Point(start.x + (end.x - start.x) / 3, start.y - 50);
		points[2] = new Point(start.x + 2 * (end.x - start.x) / 3, end.y - 50);
		points[3] = new Point(end.x, end.y);

		/******* 拖动节点注册监听 *********/

		node1 = new JLabel();
		node2 = new JLabel();

		node1.setOpaque(true);
		node1.setBounds(points[1].x, points[1].y, 12, 12);
		node1.setBorder(BorderFactory.createLineBorder(this.color));
		node1.setBackground(ThemeChooser.LocalStyle.gettxtcolor());
		node1.setVisible(true);
		NodeListener curveLineListener1 = new NodeListener(this);
		node1.addMouseListener(curveLineListener1);
		node1.addMouseMotionListener(curveLineListener1);

		node2.setOpaque(true);
		node2.setBounds(points[2].x, points[2].y, 12, 12);
		node2.setBorder(BorderFactory.createLineBorder(this.color));
		node2.setBackground(ThemeChooser.LocalStyle.gettxtcolor());
		node2.setVisible(true);
		NodeListener curveLineListener2 = new NodeListener(this);
		node2.addMouseListener(curveLineListener2);
		node2.addMouseMotionListener(curveLineListener2);
	}

	/**
	 * 更新曲线前两个点位置
	 * 
	 * @param deltax:横坐标变化量
	 * @param deltay:纵坐标变化量
	 */
	public void updateFirstTwoNode(int deltax, int deltay) {

		points[0] = new Point(points[0].x + deltax, points[0].y + deltay);
		points[1] = new Point(points[1].x + deltax, points[1].y + deltay);

		node1.setBounds(points[1].x, points[1].y, 12, 12);
	}

	/**
	 * 镜像曲线前两个点位置
	 * 
	 * @param x0:对称中心位置
	 */
	public void mirrorFirstTwoNode(int x0) {

		points[0] = new Point(2 * x0 - points[0].x, points[0].y);
		points[1] = new Point(2 * x0 - points[1].x, points[1].y);

		node1.setBounds(points[1].x, points[1].y, 12, 12);
	}

	/**
	 * 镜像曲线后两个点位置
	 * 
	 * @param x0 :对称中心位置
	 */
	public void mirrorLastTwoNode(int x0) {
		points[2] = new Point(2 * x0 - points[2].x, points[2].y);
		points[3] = new Point(2 * x0 - points[3].x, points[3].y);
		node2.setBounds(points[2].x, points[2].y, 12, 12);
	}

	/**
	 * 更新曲线后两个点位置
	 * 
	 * @param deltax:横坐标变化量
	 * @param deltay:纵坐标变化量
	 */
	public void updateLastTwoNode(int deltax, int deltay) {
		points[2] = new Point(points[2].x + deltax, points[2].y + deltay);
		points[3] = new Point(points[3].x + deltax, points[3].y + deltay);
		node2.setBounds(points[2].x, points[2].y, 12, 12);

	}

	/***
	 * 更新曲线整体位置
	 * 
	 * @param deltax:横坐标变化量
	 * @param deltay:总坐标
	 */
	public void updateCurveLine(int deltax, int deltay) {

		points[0] = new Point(points[0].x + deltax, points[0].y + deltay);
		points[1] = new Point(points[1].x + deltax, points[1].y + deltay);
		points[2] = new Point(points[2].x + deltax, points[2].y + deltay);
		points[3] = new Point(points[3].x + deltax, points[3].y + deltay);
		node1.setBounds(points[1].x, points[1].y, 12, 12);
		node2.setBounds(points[2].x, points[2].y, 12, 12);
	}

	/**
	 * 更新拖动节点1位置
	 * 
	 * @param p1:节点1的新位置
	 */
	public void updateNode1(Point p1) {
		points[1] = p1;
		node1.setBounds(p1.x, p1.y, 12, 12);

	}

	/**
	 * 更新拖动节点2位置
	 * 
	 * @param p2:节点2的新位置
	 */
	public void updateNode2(Point p2) {
		points[2] = p2;
		node2.setBounds(p2.x, p2.y, 12, 12);

	}

	/**
	 * 返回曲线的四点集
	 * 
	 * @return Point[] points
	 */
	public Point[] getPoints() {
		return points;
	}

	/**
	 * 返回曲线起点位置
	 * 
	 * @return points[0]
	 */
	public Point getStartPoint() {
		return points[0];
	}

	/**
	 * 返回曲线终点位置
	 * 
	 * @return points[3]
	 */
	public Point getEndPoint() {
		return points[3];
	}

	/**
	 * 返回拖动节点1
	 * 
	 * @return JLabel node1
	 */
	public JLabel getNode1() {
		return node1;
	}

	/**
	 * 返回拖动节点2
	 * 
	 * @return JLabel node2
	 */
	public JLabel getNode2() {
		return node2;
	}

	/**
	 * 返回曲线颜色
	 * 
	 * @return Color color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * 设置曲线颜色
	 * 
	 * @param color:曲线颜色
	 */
	public void setColor(Color color) {
		this.color = color;
		node1.setBackground(ThemeChooser.LocalStyle.gettxtcolor());
		node2.setBackground(ThemeChooser.LocalStyle.gettxtcolor());
	}

	/**
	 * 得到曲线起点主题
	 * 
	 * @return ThemeLabel startLabel
	 */
	public ThemeLabel getstartLabel() {
		return startLabel;
	}

	/**
	 * 设置曲线起点主题
	 * 
	 * @param startLabel：曲线起点主题
	 */
	public void setstartLabel(ThemeLabel startLabel) {
		this.startLabel = startLabel;
	}

	/**
	 * 返回曲线终点主题
	 * 
	 * @return ThemeLabel endLabel
	 */
	public ThemeLabel getendLabel() {
		return endLabel;
	}

	/**
	 * 设置曲线终点主题
	 * 
	 * @param endLabel：曲线终点主题
	 */
	public void setendLabel(ThemeLabel endLabel) {
		this.endLabel = endLabel;
	}

	public void updateLastNode(Point point3, Point point4) {
		System.out.println("你是魔鬼吗");
	}
}
