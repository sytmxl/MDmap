package version1;

import java.awt.*;

/***
 * @author wangxianke 2019-1-12 1.连接线类连接主题，每个主题对应唯一入度 2.连接线自动探测连接
 */

class ConnectLine implements java.io.Serializable {
	/**
	 * 属性:
	 * int StartX, startY:起点坐标 
	 * int endX,endY 终点坐标
	 * int width:线宽 
	 * Color colr:颜色
	 * boolean isLive:状态
	 */
	int startX, startY, endX, endY;
	int width;
	Color color;
	boolean isLive = true;

	/**
	 * 构造方法
	 * 
	 * @param startX 起点横坐标
	 * @param startY 起点纵坐标
	 * @param endX   终点横坐标
	 * @param endY   终点纵坐标
	 */
	public ConnectLine(int startX, int StartY, int endX, int endY) {
		this.startX = startX;
		this.startY = StartY;
		this.endX = endX;
		this.endY = endY;
		this.width = 1;
		this.color = ThemeChooser.LocalStyle.getlinecolor();

	}

	/***
	 * 设置连接线起点终点位置
	 * @param startX 起点横坐标
	 * @param startY 起点纵坐标
	 * @param endX   终点横坐标
	 * @param endY   终点纵坐标
	 */
	public void setLocation(int startX, int startY, int endX, int endY) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}

	/**
	 * 设置连接线的终点位置
	 * 
	 * @param endX 终点横坐标
	 * @param endY 终点纵坐标
	 */
	public void setLocation(int endX, int endY) {
		this.endX = endX;
		this.endY = endY;

	}

	/**
	 * 返回连接线起点横坐标
	 * 
	 * @return startX
	 */
	public int getStartX() {
		return startX;
	}

	/**
	 * 设置连接线起点横坐标
	 * 
	 * @param startX 起点横坐标
	 */
	public void setStartX(int startX) {
		this.startX = startX;
	}

	/**
	 * 返回连接线起点横坐标
	 * 
	 * @return startY
	 */
	public int getStartY() {
		return startY;
	}

	/**
	 * 设置连接线起点纵坐标
	 * 
	 * @param startY 起点纵坐标
	 */
	public void setStartY(int startY) {
		this.startY = startY;
	}

	/**
	 * 返回连接线终点横坐标
	 * 
	 * @return endX
	 */
	public int getEndX() {
		return endX;
	}

	/**
	 * 设置连接线终点横坐标
	 * 
	 * @param endX 终点横坐标
	 */
	public void setEndX(int endX) {
		this.endX = endX;
	}

	/**
	 * 返回连接线终点纵坐标
	 * 
	 * @return endY
	 */
	public int getEndY() {
		return endY;
	}

	/**
	 * 设置连接线终点纵坐标
	 * 
	 * @param endY 终点纵坐标
	 */
	public void setEndY(int endY) {
		this.endY = endY;
	}

	/**
	 * 返回连接线宽度
	 * 
	 * @return width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * 设置连接线宽度
	 * 
	 * @param width 连接线宽度
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * 返回连接线颜色
	 * 
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * 设置连接线颜色
	 * 
	 * @param color 连接线颜色
	 */
	public void setColor(Color color) {
		this.color = color;
	}

}
