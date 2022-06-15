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
        ButtonMouseListener.fatherLabel=this.label;
        System.out.println("click label "+ ButtonMouseListener.fatherLabel.LabelName);
        /***** 单击左键选中，单击右键取消选中主题，双击左键修改主题，双击右键删除主题*********/
        if (e.getButton() == e.BUTTON1){
            if(e.getClickCount() == 1){
                if(lastChooseLabel!=null){
                    Border noneLine = BorderFactory.createLineBorder(Color.black,0,true);
                    lastChooseLabel.setBorder(noneLine);
                }
                Border blackLine = BorderFactory.createLineBorder(Color.black,2,true);
                label.setBorder(blackLine);
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
                Border noneLine = BorderFactory.createLineBorder(Color.black,0,true);
                label.setBorder(noneLine);
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
}
