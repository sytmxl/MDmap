import java.util.HashMap;
import java.util.Map.Entry;

public class ThemeDetect {
    private int distancex, distancey;
    private ThemeLabel themeLabel = null;
    private PaintePanel pan = null;

    public ThemeDetect(PaintePanel pan) {
        this.pan = pan;
    }
    /***** �ƶ����нڵ� *******/
    public void moveAll(int deltax, int deltay) {

        HashMap<ThemeLabel, ConnectLine> ConnectLineList = this.pan.getallConnectLine();
        /********* �����ƶ�hashmap�ڵ����� *******/
        for (Entry<ThemeLabel, ConnectLine> item : ConnectLineList.entrySet()) {
            ThemeLabel key = item.getKey();
            ConnectLine val = item.getValue();
            if (key.isLive) {
                key.updateLocation(deltax + key.getX(), deltay + key.getY());

            }
            if (key.isLive && val != null && val.isLive) {
                val.setLocation(deltax + val.getStartX(), deltay + val.getStartY(), deltax + val.getEndX(),
                        deltay + val.getEndY());
            }

        }
        /********* ������ *********/
        ThemeLabel root = this.pan.getRootThemeLabel();
        root.updateLocation(deltax + root.getX(), deltay + root.getY());
    }

    public void updateConnect(ThemeLabel themeLabel, int deltax, int deltay) {

        /*****����������������������ӹ�ϵ*********/
        this.updateConnectHelper(themeLabel, deltax, deltay, true);
        if (themeLabel.getallChild().size() > 0) {
            ConnectLine connectLine = this.pan.getConnectLine(themeLabel.getChild(0));
            /******* ��Ҫ������֦��������֦ ****/
            boolean mirrorLeft = connectLine.getStartX() < connectLine.getEndX()
                    && connectLine.getStartX() < this.pan.getRootThemeLabelLeftX();
            /******* ��Ҫ������֦��������֦ ****/
            boolean mirrorRight = connectLine.getStartX() > connectLine.getEndX()
                    && connectLine.getStartX() > this.pan.getRootThemeLabelRightX();
            if (mirrorLeft || mirrorRight) {
                /*********���Ҿ���********/
                this.themeLabelMirror(themeLabel, (themeLabel.getThemeLeftX() + themeLabel.getThemeRightX()) / 2, true);
            }
        }
    }

    /**
     * ��ǩ�ڸ��ڵ����Ҵ����һ���ҵ�����Ҫ����
     * @param themeLabel:���϶�����
     * @param x0:�Գ�����
     * @param isRoot:�Ƿ�Ϊ�϶�����
     */
    private void themeLabelMirror(ThemeLabel themeLabel, int x0, boolean isRoot) {
        if (themeLabel != null) {
            /****����������****/
            for (int i = 0; i < themeLabel.getallChild().size(); i++) {
                ThemeLabel temp = themeLabel.getallChild().get(i);
                themeLabelMirror(temp, x0, false);
            }

            if (!isRoot) {

                /****����ǰ�����������*******/
                themeLabel.updateLocation(2 * x0 - themeLabel.getX() - themeLabel.getThemeSizeX(), themeLabel.getY());
                themeLabel.setVisible(true);
                ConnectLine connectLine = this.pan.getConnectLine(themeLabel);
                if (connectLine != null) {
                    connectLine.setLocation(2 * x0 - connectLine.getStartX(), connectLine.getStartY(),
                            2 * x0 - connectLine.getEndX(), connectLine.getEndY());
                }
            }

        } else {
            return;
        }
    }
    /********** ���������Լ������ߵ�λ�� **********/
    /**
     * ���������������λ��
     * @param themeLabel:���϶�����
     * @param deltax:x����仯��
     * @param deltay:y����仯��
     */
    private void updateConnectHelper(ThemeLabel themeLabel, int deltax, int deltay, boolean isRoot) {

        if (themeLabel != null) {
            /*****���º�������*******/
            for (int i = 0; i < themeLabel.getallChild().size(); i++) {
                ThemeLabel temp = themeLabel.getallChild().get(i);
                updateConnectHelper(temp, deltax, deltay, false);
            }
            /****���µ�ǰ����,������λ��**********/
            themeLabel.updateLocation(deltax + themeLabel.getX(), deltay + themeLabel.getY());

            if (!isRoot) {

                ConnectLine connectLine = this.pan.getConnectLine(themeLabel);
                if (connectLine != null) {
                    connectLine.setLocation(deltax + connectLine.getStartX(), deltay + connectLine.getStartY(),
                            deltax + connectLine.getEndX(), deltay + connectLine.getEndY());
                }
            } else {
                ConnectLine connectLine = this.pan.getConnectLine(themeLabel);
                ThemeLabel fatherLabel=themeLabel.getFather();
                int startx,endx;
                if (connectLine != null) {
                    /****����븸�ڵ������Ҫ��Ҫ����**********/
                    if(connectLine.getStartX() > themeLabel.getThemeRightX()){
                        startx= fatherLabel.getThemeLeftX();
                        endx= themeLabel.getThemeRightX();
                    }else{
                        startx= fatherLabel.getThemeRightX();
                        endx= themeLabel.getThemeLeftX();
                    }
                    connectLine.setLocation(startx, fatherLabel.getThemeMidY(),
                            endx, deltay + connectLine.getEndY());
                }
            }

        } else {
            return;
        }
    }

    /**
     * @param themeLabel:��С�仯������
     * @param deltaSizex:��ȱ仯��
     */
    public void themesizeChangeMove(ThemeLabel themeLabel, int deltaSizex) {

        int deltaX = deltaSizex;
        if (themeLabel.getallChild().size() > 0) {
            /**** �ڸ������ұ� ******/
            if (themeLabel.getThemeLeftX() > this.pan.getRootThemeLabelRightX()) {
                deltaX = deltaSizex;
            }
            /**** �ڸ�������� ******/
            else if (themeLabel.getThemeRightX() < this.pan.getRootThemeLabelLeftX()) {
                deltaX = -deltaSizex;
            }
        }
        moveChildConnectionHelper(themeLabel, deltaX, true);
    }
    /**
     * @param themeLabel:���ȸı������
     * @param deltax:���ȱ仯��
     * @param isRoot:��ǰ�����Ƿ�Ϊ���ȸı������
     */
    private void moveChildConnectionHelper(ThemeLabel themeLabel, int deltax, boolean isRoot) {
        if (themeLabel != null) {

            if (!isRoot) {

                /****�ƶ������ߺ�����λ��****/
                ConnectLine connectLine = this.pan.getConnectLine(themeLabel);
                if (connectLine != null) {
                    connectLine.setLocation(deltax + connectLine.getStartX(), connectLine.getStartY(),
                            deltax + connectLine.getEndX(), connectLine.getEndY());
                }

                themeLabel.updateLocation(themeLabel.getX() + deltax, themeLabel.getY());
            }

            else {
            }

            boolean ispanRootTheme = false;
            if (themeLabel == this.pan.getRootThemeLabel()) {
                ispanRootTheme = true;
            }

            /****�ƶ��������⼰������****/
            for (int i = 0; i < themeLabel.getallChild().size(); i++) {
                ThemeLabel temp = themeLabel.getallChild().get(i);
                if (ispanRootTheme) {
                    if (temp.getThemeRightX() > themeLabel.getThemeRightX())
                        moveChildConnectionHelper(temp, deltax, false);
                } else {
                    moveChildConnectionHelper(temp, deltax, false);
                }
            }

        } else {
            return;
        }

    }
    /**
     * ɾ������
     * @param themeLabel:���� ��delete����backspace����ɾ���ڵ�
     */
    public void deleteConnect(ThemeLabel themeLabel){
        deleteHelper(themeLabel);
    }
    /**
     * ɾ������
     * @param themeLabel:����ִ�з���
     */
    private void deleteHelper(ThemeLabel themeLabel) {
        if (themeLabel != null) {
            if (themeLabel.getFather() != null) {
                /****�Ӹ������б���ɾ����ǰ����******/
                themeLabel.getFather().removeChild(themeLabel);
            }
            ThemeLabel temp = null;
            /****ɾ������****/
            while (themeLabel.getallChild().size() > 0) {
                temp = themeLabel.getallChild().get(0);
                deleteHelper(temp);
            }
            /***���õ�ǰ����״̬Ϊ��ɾ��****/
            themeLabel.isLive = false;
        } else {
            return;
        }

    }
}
