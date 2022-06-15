import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonMouseListener implements ActionListener { //添加节点
    public static ThemeLabel fatherLabel=null;//初始化设成根节点,每次点击完也设成根节点
    public int x=0,y=0;

    public void setFatherLabel(ThemeLabel themeLabel){
        fatherLabel=themeLabel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("why");
        if (fatherLabel == MainWindow.pan.getRootThemeLabel() || fatherLabel == null) {//如果是根节点的子节点
            x = MainWindow.pan.getRootThemeLabelRightX();
            y = MainWindow.pan.getRootThemeLabelTopY();
            ThemeLabel themeLabel = new ThemeLabel(x + 40, y);
            MainWindow.pan.add(themeLabel);
            /******** 增加父子节点关联 ************/
            themeLabel.setFather(MainWindow.pan.getRootThemeLabel());
            MainWindow.pan.getRootThemeLabel().addChild(themeLabel);
            MainWindow.pan.add(themeLabel);
            /******** 设置主题等级 ************/
            themeLabel.setRank(1);
            themeLabel.updateRankSize(themeLabel.getRank());
            MainWindow.pan.add(themeLabel);
            /******** 增加连线 ************/
            ConnectLine connectLine = new ConnectLine(MainWindow.pan.getRootThemeLabelRightX(), MainWindow.pan.getRootThemeLabelMidY(), themeLabel.getThemeLeftX(), themeLabel.getThemeMidY());
            MainWindow.pan.addConnectLine(themeLabel, connectLine);//入度和连接线,子节点只能有一个入度

        } else {
            int distance = fatherLabel.getThemeLeftX() - MainWindow.pan.getRootThemeLabelRightX();
            if (distance < 0) {
                x = fatherLabel.getThemeLeftX();
                y = fatherLabel.getThemeTopY();
                ThemeLabel themeLabel = new ThemeLabel(x - 40, y);
                MainWindow.pan.add(themeLabel);
                /******** 增加父子节点关联 ************/
                themeLabel.setFather(fatherLabel);
                fatherLabel.addChild(themeLabel);
                /******** 设置主题等级 ************/
                themeLabel.setRank(fatherLabel.getRank() + 1);
                themeLabel.updateRankSize(themeLabel.getRank());
                MainWindow.pan.add(themeLabel);
                /******** 增加父子节点连接线 ************/
                ConnectLine connectLine = new ConnectLine(fatherLabel.getThemeLeftX(), fatherLabel.getThemeMidY(), themeLabel.getThemeRightX(), themeLabel.getThemeMidY());
                MainWindow.pan.addConnectLine(themeLabel, connectLine);//入度和连接线,子节点只能有一个入度
            } else {
                x = fatherLabel.getThemeRightX();
                y = fatherLabel.getThemeTopY();
                ThemeLabel themeLabel = new ThemeLabel(x + 40, y);
                /******** 增加父子节点关联 ************/
                themeLabel.setFather(fatherLabel);
                fatherLabel.addChild(themeLabel);
                /******** 设置主题等级 ************/
                themeLabel.setRank(fatherLabel.getRank() + 1);
                themeLabel.updateRankSize(themeLabel.getRank());
                MainWindow.pan.add(themeLabel);
                /******** 增加父子节点连接线 ************/
                ConnectLine connectLine = new ConnectLine(fatherLabel.getThemeRightX(), fatherLabel.getThemeMidY(), themeLabel.getThemeLeftX(), themeLabel.getThemeMidY());
                MainWindow.pan.addConnectLine(themeLabel, connectLine);//入度和连接线,子节点只能有一个入度
            }
            fatherLabel = null;
        }
    }

    static public ThemeLabel add(String text, int rank, int yPlus){
        int x,y;
        if (fatherLabel == MainWindow.pan.getRootThemeLabel() || fatherLabel == null) {//如果是根节点的子节点
            x = MainWindow.pan.getRootThemeLabelRightX();
            y = MainWindow.pan.getRootThemeLabelTopY();
            ThemeLabel themeLabel = new ThemeLabel(x + 40, y + yPlus, text, rank);
            MainWindow.pan.add(themeLabel);
            /******** 增加父子节点关联 ************/
            themeLabel.setFather(MainWindow.pan.getRootThemeLabel());
            MainWindow.pan.getRootThemeLabel().addChild(themeLabel);
            MainWindow.pan.add(themeLabel);
            /******** 设置主题等级 ************/
            themeLabel.setRank(1);
            themeLabel.updateRankSize(themeLabel.getRank());
            MainWindow.pan.add(themeLabel);
            /******** 增加连线 ************/
            ConnectLine connectLine = new ConnectLine(MainWindow.pan.getRootThemeLabelRightX(), MainWindow.pan.getRootThemeLabelMidY(), themeLabel.getThemeLeftX(), themeLabel.getThemeMidY());
            MainWindow.pan.addConnectLine(themeLabel, connectLine);//入度和连接线,子节点只能有一个入度
            return themeLabel;
        }
        else {
            int distance = fatherLabel.getThemeLeftX() - MainWindow.pan.getRootThemeLabelRightX();
            if (distance < 0) {
                x = fatherLabel.getThemeLeftX();
                y = fatherLabel.getThemeTopY();
                ThemeLabel themeLabel = new ThemeLabel(x - 40, y + yPlus, text, rank);
                MainWindow.pan.add(themeLabel);
                /******** 增加父子节点关联 ************/
                themeLabel.setFather(fatherLabel);
                fatherLabel.addChild(themeLabel);
                /******** 设置主题等级 ************/
                themeLabel.setRank(fatherLabel.getRank() + 1);
                themeLabel.updateRankSize(themeLabel.getRank());
                MainWindow.pan.add(themeLabel);
                /******** 增加父子节点连接线 ************/
                ConnectLine connectLine = new ConnectLine(fatherLabel.getThemeLeftX(), fatherLabel.getThemeMidY(), themeLabel.getThemeRightX(), themeLabel.getThemeMidY());
                MainWindow.pan.addConnectLine(themeLabel, connectLine);//入度和连接线,子节点只能有一个入度
                return themeLabel;
            }
            else {
                x = fatherLabel.getThemeRightX();
                y = fatherLabel.getThemeTopY();
                ThemeLabel themeLabel = new ThemeLabel(x + 40, y + yPlus, text, rank);
                /******** 增加父子节点关联 ************/
                themeLabel.setFather(fatherLabel);
                fatherLabel.addChild(themeLabel);
                /******** 设置主题等级 ************/
                themeLabel.setRank(fatherLabel.getRank() + 1);
                themeLabel.updateRankSize(themeLabel.getRank());
                MainWindow.pan.add(themeLabel);
                /******** 增加父子节点连接线 ************/
                ConnectLine connectLine = new ConnectLine(fatherLabel.getThemeRightX(), fatherLabel.getThemeMidY(), themeLabel.getThemeLeftX(), themeLabel.getThemeMidY());
                MainWindow.pan.addConnectLine(themeLabel, connectLine);//入度和连接线,子节点只能有一个入度
                return themeLabel;
            }
        }
    }
}
