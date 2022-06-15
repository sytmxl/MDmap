import java.util.HashMap;
import java.util.Map.Entry;

public class ThemeDetect {
    private int distancex, distancey;
    private ThemeLabel themeLabel = null;
    private PaintePanel pan = null;

    public ThemeDetect(PaintePanel pan) {
        this.pan = pan;
    }
    /***** 移动所有节点 *******/
    public void moveAll(int deltax, int deltay) {

        HashMap<ThemeLabel, ConnectLine> ConnectLineList = this.pan.getallConnectLine();
        /********* 遍历移动hashmap内的连接 *******/
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
        /********* 根主题 *********/
        ThemeLabel root = this.pan.getRootThemeLabel();
        root.updateLocation(deltax + root.getX(), deltay + root.getY());
    }

    public void updateConnect(ThemeLabel themeLabel, int deltax, int deltay) {

        /*****遍历树更新主题后所有连接关系*********/
        this.updateConnectHelper(themeLabel, deltax, deltay, true);
        if (themeLabel.getallChild().size() > 0) {
            ConnectLine connectLine = this.pan.getConnectLine(themeLabel.getChild(0));
            /******* 需要从右连枝镜像到左连枝 ****/
            boolean mirrorLeft = connectLine.getStartX() < connectLine.getEndX()
                    && connectLine.getStartX() < this.pan.getRootThemeLabelLeftX();
            /******* 需要从左连枝镜像到右连枝 ****/
            boolean mirrorRight = connectLine.getStartX() > connectLine.getEndX()
                    && connectLine.getStartX() > this.pan.getRootThemeLabelRightX();
            if (mirrorLeft || mirrorRight) {
                /*********左右镜像********/
                this.themeLabelMirror(themeLabel, (themeLabel.getThemeLeftX() + themeLabel.getThemeRightX()) / 2, true);
            }
        }
    }

    /**
     * 标签在根节点左右从左到右或从右到左需要镜像
     * @param themeLabel:被拖动主题
     * @param x0:对称中心
     * @param isRoot:是否为拖动主题
     */
    private void themeLabelMirror(ThemeLabel themeLabel, int x0, boolean isRoot) {
        if (themeLabel != null) {
            /****镜像孩子主题****/
            for (int i = 0; i < themeLabel.getallChild().size(); i++) {
                ThemeLabel temp = themeLabel.getallChild().get(i);
                themeLabelMirror(temp, x0, false);
            }

            if (!isRoot) {

                /****镜像当前主题和连接线*******/
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
    /********** 更新主题以及连接线的位置 **********/
    /**
     * 更新主题和连接线位置
     * @param themeLabel:被拖动主题
     * @param deltax:x方向变化量
     * @param deltay:y方向变化量
     */
    private void updateConnectHelper(ThemeLabel themeLabel, int deltax, int deltay, boolean isRoot) {

        if (themeLabel != null) {
            /*****更新孩子主题*******/
            for (int i = 0; i < themeLabel.getallChild().size(); i++) {
                ThemeLabel temp = themeLabel.getallChild().get(i);
                updateConnectHelper(temp, deltax, deltay, false);
            }
            /****更新当前主题,连接线位置**********/
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
                    /****检查与父节点的连线要不要镜像**********/
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
     * @param themeLabel:大小变化的主题
     * @param deltaSizex:宽度变化量
     */
    public void themesizeChangeMove(ThemeLabel themeLabel, int deltaSizex) {

        int deltaX = deltaSizex;
        if (themeLabel.getallChild().size() > 0) {
            /**** 在根主题右边 ******/
            if (themeLabel.getThemeLeftX() > this.pan.getRootThemeLabelRightX()) {
                deltaX = deltaSizex;
            }
            /**** 在根主题左边 ******/
            else if (themeLabel.getThemeRightX() < this.pan.getRootThemeLabelLeftX()) {
                deltaX = -deltaSizex;
            }
        }
        moveChildConnectionHelper(themeLabel, deltaX, true);
    }
    /**
     * @param themeLabel:长度改变的主题
     * @param deltax:长度变化量
     * @param isRoot:当前主题是否为长度改变的主题
     */
    private void moveChildConnectionHelper(ThemeLabel themeLabel, int deltax, boolean isRoot) {
        if (themeLabel != null) {

            if (!isRoot) {

                /****移动连接线和主题位置****/
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

            /****移动孩子主题及连接线****/
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
     * 删除主题
     * @param themeLabel:单击 按delete或者backspace可以删除节点
     */
    public void deleteConnect(ThemeLabel themeLabel){
        deleteHelper(themeLabel);
    }
    /**
     * 删除主题
     * @param themeLabel:具体执行方法
     */
    private void deleteHelper(ThemeLabel themeLabel) {
        if (themeLabel != null) {
            if (themeLabel.getFather() != null) {
                /****从父主题列表中删除当前主题******/
                themeLabel.getFather().removeChild(themeLabel);
            }
            ThemeLabel temp = null;
            /****删除孩子****/
            while (themeLabel.getallChild().size() > 0) {
                temp = themeLabel.getallChild().get(0);
                deleteHelper(temp);
            }
            /***设置当前主题状态为已删除****/
            themeLabel.isLive = false;
        } else {
            return;
        }

    }
}
