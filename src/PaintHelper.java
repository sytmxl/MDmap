import java.awt.*;
import java.awt.geom.GeneralPath;

public class PaintHelper {
    /**
     * Graphics g:传入的一维画笔
     *  Graphics2D g2:二维画笔
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
        /***** 绘制连接线 ********/
        this.drawALLine(connectLine.getStartX(), connectLine.getStartY(), connectLine.getEndX(), connectLine.getEndY(), g2);
    }

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

    /**
     * 旋转角度
     *
     */
    private double[] rotateVec(int px, int py, double ang, boolean isChLen, double newLen) {

        double[] mathstr = new double[2];
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
}
