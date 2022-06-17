import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Map;

public class ComponentMouseListener implements MouseInputListener {
    int startX = 0;
    int startY = 0;
    boolean isClickLeftAndDrag = false;
    ThemeLabel label = null;
    JLabel Mask = null;//蒙版，鼠标移动上标签可见
    public static ThemeLabel lastChooseLabel=null;
    public ComponentMouseListener(ThemeLabel themeLabel) {
        this.label = themeLabel;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        /***** 单击左键选中，单击右键取消选中主题，双击左键修改主题，双击右键删除主题*********/
        if (e.getButton() == e.BUTTON1){
            if(e.getClickCount() == 1){
                Constent.fatherLabel=this.label;
                if(lastChooseLabel!=null){
                    if(lastChooseLabel.getFather()== MainWindow.pan.getRootThemeLabel()){
                        Border blackLine = BorderFactory.createLineBorder(new Color(18, 91, 80),5,true);
                        lastChooseLabel.setBorder(blackLine);
                    }else{
                        Border noneLine = BorderFactory.createLineBorder(Color.black,0,true);
                        lastChooseLabel.setBorder(noneLine);
                    }
                }
                if(label.getRank()==0){
                    Border blackLine = BorderFactory.createLineBorder(new Color(248, 180, 0),5,true);
                    label.setBorder(blackLine);
                }else if(label.getRank()==1){
                    Border blackLine = BorderFactory.createLineBorder(new Color(159, 230, 160),5,true);
                    label.setBorder(blackLine);
                }else{
                    Border blackLine = BorderFactory.createLineBorder(new Color(255, 99, 99),5,true);
                    label.setBorder(blackLine);
                }

                lastChooseLabel=this.label;
            }else if(e.getClickCount() == 2){
                for (Map.Entry<ThemeLabel, ConnectLine> item : MainWindow.pan.getallConnectLine().entrySet()) {
                    ThemeLabel key = item.getKey();
                    key.getMyThemeFrame().setVisible(false);
                }
                this.label.getMyThemeFrame().setVisible(true);
                this.label.getMyThemeFrame().setLocation((Constent.frameWidth-Constent.themeFrameWidth)/2,(Constent.frameHeight-Constent.themeFrameHeight)/2);
                this.label.getMyThemeFrame().setState(JFrame.NORMAL);
                this.label.getMyThemeFrame().show();
                this.label.getMyThemeFrame().getInput().setText(this.label.getText());
            }

        }else if(e.getButton() == e.BUTTON3){
            if(e.getClickCount() == 1){
                Constent.fatherLabel=null;
                if(label.getFather()== MainWindow.pan.getRootThemeLabel()){
                    Border blackLine = BorderFactory.createLineBorder(new Color(18, 91, 80),5,true);
                    label.setBorder(blackLine);
                }else{
                    Border noneLine = BorderFactory.createLineBorder(Color.black,0,true);
                    label.setBorder(noneLine);
                }
            }
            else if(e.getClickCount() == 2){
                new ThemeDetect(MainWindow.pan).deleteConnect(this.label);
                ButtonMouseListener.fatherLabel=MainWindow.pan.getRootThemeLabel();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
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
        if (e.getButton() == e.BUTTON1) {
            /***** 关闭拖拽模式 *******/
            isClickLeftAndDrag = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isClickLeftAndDrag) {

            int x = e.getX();
            int y = e.getY();

            /***** 为根主题时移动所有节点 *******/
            if ((ThemeLabel) this.label == MainWindow.pan.getRootThemeLabel()) {
                new ThemeDetect( MainWindow.pan).moveAll(x - this.startX, y - this.startY);
                /**** 光标变为手型 ******/
                this.label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            /**** 普通主题移动自己及所有孩子 ******/
            else {
                new ThemeDetect( MainWindow.pan).updateConnect((ThemeLabel) this.label, x - this.startX,y - this.startY);
                this.label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            /**** 更新蒙版位置 *******/
            //Mask.setLocation(Label.getX() - 5, Label.getY() - 5);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public static void addBorder(ThemeLabel themeLabel) {
        if (themeLabel==null) {//根结点时情况
            Border blackLine = BorderFactory.createLineBorder(new Color(248, 180, 0),5,true);
            MainWindow.pan.getRootThemeLabel().setBorder(blackLine);
        }
        else if(themeLabel.getRank()==0){
            Border blackLine = BorderFactory.createLineBorder(new Color(248, 180, 0),5,true);
            themeLabel.setBorder(blackLine);
        }else if(themeLabel.getRank()==1){
            Border blackLine = BorderFactory.createLineBorder(new Color(159, 230, 160),5,true);
            themeLabel.setBorder(blackLine);
        }else{
            Border blackLine = BorderFactory.createLineBorder(new Color(255, 99, 99),5,true);
            themeLabel.setBorder(blackLine);
        }
    }
}
