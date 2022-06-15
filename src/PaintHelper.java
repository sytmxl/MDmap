import java.awt.*;
import java.awt.geom.GeneralPath;

public class PaintHelper {
    /**
     * Graphics g:�����һά����
     *  Graphics2D g2:��ά����
     */
    private Graphics g = null;
    private Graphics2D g2 = null;

    /**
     * ��ʼ�������뻭��
     *
     * @param g:���ⲿJPanel����Ļ���
     */
    public PaintHelper(Graphics g) {
        this.g = g;
        g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    /**
     * ����������
     *
     * @param connectLine:����������߶���
     */
    public void PainteLine(ConnectLine connectLine) {
        /***** �����߿� **********/
        g2.setStroke(new BasicStroke(3.0f));
        /***** ���������� ********/
        this.drawALLine(connectLine.getStartX(), connectLine.getStartY(), connectLine.getEndX(), connectLine.getEndY(), g2);
    }

    private void drawALLine(int sx, int sy, int ex, int ey, Graphics2D g2) {
        // ��ͷ�߶�
        double H = 10;
        // �ױߵ�һ��
        double L = 4;
        int x3 = 0;
        int y3 = 0;
        int x4 = 0;
        int y4 = 0;
        double awrad = Math.atan(L / H); // ��ͷ�Ƕ�
        double arraow_len = Math.sqrt(L * L + H * H); // ��ͷ�ĳ���
        double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);
        double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);
        double x_3 = ex - arrXY_1[0]; // (x3,y3)�ǵ�һ�˵�
        double y_3 = ey - arrXY_1[1];
        double x_4 = ex - arrXY_2[0]; // (x4,y4)�ǵڶ��˵�
        double y_4 = ey - arrXY_2[1];

        Double X3 = new Double(x_3);
        x3 = X3.intValue();
        Double Y3 = new Double(y_3);
        y3 = Y3.intValue();
        Double X4 = new Double(x_4);
        x4 = X4.intValue();
        Double Y4 = new Double(y_4);
        y4 = Y4.intValue();
        // ����
        g2.drawLine(sx, sy, ex, ey);

        GeneralPath triangle = new GeneralPath();
        triangle.moveTo(ex, ey);
        triangle.lineTo(x3, y3);
        triangle.lineTo(x4, y4);
        triangle.closePath();
        // ʵ�ļ�ͷ
        g2.fill(triangle);
    }

    /**
     * ��ת�Ƕ�
     *
     */
    private double[] rotateVec(int px, int py, double ang, boolean isChLen, double newLen) {

        double[] mathstr = new double[2];
        // ʸ����ת��������������ֱ���x������y��������ת�ǡ��Ƿ�ı䳤�ȡ��³���
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
