package version1;
import java.awt.*;
import java.awt.geom.GeneralPath;
/**
 * @author wangxianke
 */
/**
 * 1.传入画板中的画笔，实现底层绘画功能； 2.实现绘制连接线和绘制曲线
 */
class PaintHelper {
	/**
	 * Graphics g:传入的一纬画笔
	 *  Graphics2D g2:二纬画笔
	 */
	private Graphics g = null;
	private Graphics2D g2 = null;

	/**
	 * 初始化，传入画笔
	 * 
	 * @param g:从外部JPanel传入的画笔
	 */
	public PaintHelper(Graphics g) {
		this.g = g;
		g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	/**
	 * 绘制连接线
	 * 
	 * @param connectLine:传入的连接线对象
	 */
	public void PainteLine(ConnectLine connectLine) {
		/***** 设置线宽 **********/
		g2.setStroke(new BasicStroke(3.0f));
		/***** 设置线颜色 **********/
		g2.setColor(connectLine.color);
		/***** 绘制连接线 ********/
		this.drawALLine(connectLine.startX, connectLine.startY, connectLine.endX, connectLine.endY, g2);
	}

	/**
	 * 绘制贝塞尔曲线
	 * 
	 * @param curveLine:传入的曲线对象
	 */
	public void PainteCurveLine(CurveLine curveLine) {
		// TODO Auto-generated method stub
		/***** 设置线颜色 **********/
		g2.setColor(curveLine.getColor());
		/***** 设置线宽、虚线样式 ********/
		Stroke dash = new BasicStroke(2.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 3.5f, new float[] { 15, 10, },
				0f);
		g2.setStroke(dash);
		/***** 传入贝塞尔曲线4点坐标 *********/
		Point[] points = curveLine.getPoints();

		/******** 计算贝塞尔曲线关键点坐标并记录到path ************/
		GeneralPath path = new GeneralPath();
		path.moveTo(points[0].x, points[0].y);
		for (int i = 0; i < points.length - 1; ++i) {
			Point sp = points[i];
			Point ep = points[i + 1];
			Point c1 = new Point((sp.x + ep.x) / 2, sp.y);
			Point c2 = new Point((sp.x + ep.x) / 2, ep.y);

			path.curveTo(c1.x, c1.y, c2.x, c2.y, ep.x, ep.y);
		}
		
		g2.draw(path);

	}

	/******** 底层绘制连接线函数 **********/
	private void drawALLine(int sx, int sy, int ex, int ey, Graphics2D g2) {
		// 箭头高度
		double H = 10;
		// 底边的一半
		double L = 4;
		int x3 = 0;
		int y3 = 0;
		int x4 = 0;
		int y4 = 0;
		double awrad = Math.atan(L / H); // 箭头角度
		double arraow_len = Math.sqrt(L * L + H * H); // 箭头的长度
		double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);
		double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);
		double x_3 = ex - arrXY_1[0]; // (x3,y3)是第一端点
		double y_3 = ey - arrXY_1[1];
		double x_4 = ex - arrXY_2[0]; // (x4,y4)是第二端点
		double y_4 = ey - arrXY_2[1];

		Double X3 = new Double(x_3);
		x3 = X3.intValue();
		Double Y3 = new Double(y_3);
		y3 = Y3.intValue();
		Double X4 = new Double(x_4);
		x4 = X4.intValue();
		Double Y4 = new Double(y_4);
		y4 = Y4.intValue();
		// 画线
		g2.drawLine(sx, sy, ex, ey);

		GeneralPath triangle = new GeneralPath();
		triangle.moveTo(ex, ey);
		triangle.lineTo(x3, y3);
		triangle.lineTo(x4, y4);
		triangle.closePath();
		// 实心箭头
		g2.fill(triangle);
	}

	// 计算
	private double[] rotateVec(int px, int py, double ang, boolean isChLen, double newLen) {

		double mathstr[] = new double[2];
		// 矢量旋转函数，参数含义分别是x分量、y分量、旋转角、是否改变长度、新长度
		double vx = px * Math.cos(ang) - py * Math.sin(ang);
		double vy = px * Math.sin(ang) + py * Math.cos(ang);
		if (isChLen) {
			double d = Math.sqrt(vx * vx + vy * vy);
			vx = vx / d * newLen;
			vy = vy / d * newLen;
			mathstr[0] = vx;
			mathstr[1] = vy;
		}
		return mathstr;
	}

	public static void PainteTheme() {
	}
}
