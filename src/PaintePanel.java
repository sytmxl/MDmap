import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class PaintePanel extends JPanel implements Runnable{
    JScrollPane scrollPane = null;  //滚动面板

    private ThemeLabel rootThemeLabel = null;
    private HashMap<ThemeLabel, ConnectLine> ConnectLineList = null;
    public PaintePanel(){

        this.scrollPane = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);



        rootThemeLabel=new ThemeLabel(200,200);
        rootThemeLabel.updateRankSize(0);
        add(rootThemeLabel);



        this.setBackground(Color.white);// 背景颜色为白色
        this.setSize(Constent.paintPanelWidth, Constent.paintPanelHight);
        this.setLayout(null);
     //   this.setBounds(100,0,200,200);
        ConnectLineList = new HashMap<ThemeLabel, ConnectLine>();
       this.setPreferredSize(scrollPane.getViewport().getPreferredSize());

    }
    public void run() {
        // TODO Auto-generated method stub
        // 每隔10ms刷新一下mypanel
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            this.repaint();// 必须要用repaint函数去再次执行paint函数，否则无法更新
        }
    }
    public void paint(Graphics g) {
        super.paint(g);

        /***利用迭代器避免遍历删除异常******/
        if(ConnectLineList!=null){
            Iterator<Map.Entry<ThemeLabel, ConnectLine>> it = ConnectLineList.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<ThemeLabel, ConnectLine> entry = it.next();
                ThemeLabel key = entry.getKey();
                ConnectLine val =entry.getValue();

                if (key.isLive) {
                    this.add(key);
                    if(val != null && val.isLive) {
                        new PaintHelper(g).PainteLine(val);
                    }
                }
                else {
                    it.remove();
                    this.remove(key);
                }
            }
        }

    }
    public JScrollPane getScrollPane() {
        return scrollPane;
    }
    public int getRootThemeLabelLeftX(){
        return this.rootThemeLabel.getThemeLeftX();
    }
    public int getRootThemeLabelMidY() {return this.rootThemeLabel.getThemeMidY();}
    public int getRootThemeLabelTopY() {return this.rootThemeLabel.getThemeTopY();}
    public int getRootThemeLabelRightX(){return this.rootThemeLabel.getThemeRightX();}
    public int getRootThemeLabelMidX() {
        return this.rootThemeLabel.getThemeLeftX() + this.rootThemeLabel.getThemeSizeX()/2;
    }
    public ThemeLabel getRootThemeLabel(){
        return rootThemeLabel;
    }

    public void addConnectLine(ThemeLabel themeLabel,ConnectLine connectLine){
        this.ConnectLineList.put(themeLabel,connectLine);
    }

    public ConnectLine getConnectLine(ThemeLabel themeLabel) {
        return (ConnectLine) this.ConnectLineList.get(themeLabel);
    }

    public HashMap<ThemeLabel, ConnectLine> getallConnectLine() {
        return this.ConnectLineList;
    }
    public void clearConnectLine(){
        Iterator<Map.Entry<ThemeLabel, ConnectLine>> it = ConnectLineList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<ThemeLabel, ConnectLine> entry = it.next();	//下个元素
            ThemeLabel key = entry.getKey();
            ConnectLine val =entry.getValue();
                it.remove();	//移除
                this.remove(key);
            rootThemeLabel.setText("text");
        }

        System.out.println("done");
    }

    public void setRootThemeLabel(ThemeLabel themeLabel) {
        this.rootThemeLabel = themeLabel;
    }


}