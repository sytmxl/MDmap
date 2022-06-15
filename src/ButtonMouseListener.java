import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonMouseListener implements ActionListener { //��ӽڵ�
    public static ThemeLabel fatherLabel=null;//��ʼ����ɸ��ڵ�,ÿ�ε����Ҳ��ɸ��ڵ�
    public int x=0,y=0;

    public void setFatherLabel(ThemeLabel themeLabel){
        fatherLabel=themeLabel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("why");
        if (fatherLabel == MainWindow.pan.getRootThemeLabel() || fatherLabel == null) {//����Ǹ��ڵ���ӽڵ�
            x = MainWindow.pan.getRootThemeLabelRightX();
            y = MainWindow.pan.getRootThemeLabelTopY();
            ThemeLabel themeLabel = new ThemeLabel(x + 40, y);
            MainWindow.pan.add(themeLabel);
            /******** ���Ӹ��ӽڵ���� ************/
            themeLabel.setFather(MainWindow.pan.getRootThemeLabel());
            MainWindow.pan.getRootThemeLabel().addChild(themeLabel);
            MainWindow.pan.add(themeLabel);
            /******** ��������ȼ� ************/
            themeLabel.setRank(1);
            themeLabel.updateRankSize(themeLabel.getRank());
            MainWindow.pan.add(themeLabel);
            /******** �������� ************/
            ConnectLine connectLine = new ConnectLine(MainWindow.pan.getRootThemeLabelRightX(), MainWindow.pan.getRootThemeLabelMidY(), themeLabel.getThemeLeftX(), themeLabel.getThemeMidY());
            MainWindow.pan.addConnectLine(themeLabel, connectLine);//��Ⱥ�������,�ӽڵ�ֻ����һ�����

        } else {
            int distance = fatherLabel.getThemeLeftX() - MainWindow.pan.getRootThemeLabelRightX();
            if (distance < 0) {
                x = fatherLabel.getThemeLeftX();
                y = fatherLabel.getThemeTopY();
                ThemeLabel themeLabel = new ThemeLabel(x - 40, y);
                MainWindow.pan.add(themeLabel);
                /******** ���Ӹ��ӽڵ���� ************/
                themeLabel.setFather(fatherLabel);
                fatherLabel.addChild(themeLabel);
                /******** ��������ȼ� ************/
                themeLabel.setRank(fatherLabel.getRank() + 1);
                themeLabel.updateRankSize(themeLabel.getRank());
                MainWindow.pan.add(themeLabel);
                /******** ���Ӹ��ӽڵ������� ************/
                ConnectLine connectLine = new ConnectLine(fatherLabel.getThemeLeftX(), fatherLabel.getThemeMidY(), themeLabel.getThemeRightX(), themeLabel.getThemeMidY());
                MainWindow.pan.addConnectLine(themeLabel, connectLine);//��Ⱥ�������,�ӽڵ�ֻ����һ�����
            } else {
                x = fatherLabel.getThemeRightX();
                y = fatherLabel.getThemeTopY();
                ThemeLabel themeLabel = new ThemeLabel(x + 40, y);
                /******** ���Ӹ��ӽڵ���� ************/
                themeLabel.setFather(fatherLabel);
                fatherLabel.addChild(themeLabel);
                /******** ��������ȼ� ************/
                themeLabel.setRank(fatherLabel.getRank() + 1);
                themeLabel.updateRankSize(themeLabel.getRank());
                MainWindow.pan.add(themeLabel);
                /******** ���Ӹ��ӽڵ������� ************/
                ConnectLine connectLine = new ConnectLine(fatherLabel.getThemeRightX(), fatherLabel.getThemeMidY(), themeLabel.getThemeLeftX(), themeLabel.getThemeMidY());
                MainWindow.pan.addConnectLine(themeLabel, connectLine);//��Ⱥ�������,�ӽڵ�ֻ����һ�����
            }
            fatherLabel = null;
        }
    }

    static public ThemeLabel add(String text, int rank, int yPlus){
        int x,y;
        if (fatherLabel == MainWindow.pan.getRootThemeLabel() || fatherLabel == null) {//����Ǹ��ڵ���ӽڵ�
            x = MainWindow.pan.getRootThemeLabelRightX();
            y = MainWindow.pan.getRootThemeLabelTopY();
            ThemeLabel themeLabel = new ThemeLabel(x + 40, y + yPlus, text, rank);
            MainWindow.pan.add(themeLabel);
            /******** ���Ӹ��ӽڵ���� ************/
            themeLabel.setFather(MainWindow.pan.getRootThemeLabel());
            MainWindow.pan.getRootThemeLabel().addChild(themeLabel);
            MainWindow.pan.add(themeLabel);
            /******** ��������ȼ� ************/
            themeLabel.setRank(1);
            themeLabel.updateRankSize(themeLabel.getRank());
            MainWindow.pan.add(themeLabel);
            /******** �������� ************/
            ConnectLine connectLine = new ConnectLine(MainWindow.pan.getRootThemeLabelRightX(), MainWindow.pan.getRootThemeLabelMidY(), themeLabel.getThemeLeftX(), themeLabel.getThemeMidY());
            MainWindow.pan.addConnectLine(themeLabel, connectLine);//��Ⱥ�������,�ӽڵ�ֻ����һ�����
            return themeLabel;
        }
        else {
            int distance = fatherLabel.getThemeLeftX() - MainWindow.pan.getRootThemeLabelRightX();
            if (distance < 0) {
                x = fatherLabel.getThemeLeftX();
                y = fatherLabel.getThemeTopY();
                ThemeLabel themeLabel = new ThemeLabel(x - 40, y + yPlus, text, rank);
                MainWindow.pan.add(themeLabel);
                /******** ���Ӹ��ӽڵ���� ************/
                themeLabel.setFather(fatherLabel);
                fatherLabel.addChild(themeLabel);
                /******** ��������ȼ� ************/
                themeLabel.setRank(fatherLabel.getRank() + 1);
                themeLabel.updateRankSize(themeLabel.getRank());
                MainWindow.pan.add(themeLabel);
                /******** ���Ӹ��ӽڵ������� ************/
                ConnectLine connectLine = new ConnectLine(fatherLabel.getThemeLeftX(), fatherLabel.getThemeMidY(), themeLabel.getThemeRightX(), themeLabel.getThemeMidY());
                MainWindow.pan.addConnectLine(themeLabel, connectLine);//��Ⱥ�������,�ӽڵ�ֻ����һ�����
                return themeLabel;
            }
            else {
                x = fatherLabel.getThemeRightX();
                y = fatherLabel.getThemeTopY();
                ThemeLabel themeLabel = new ThemeLabel(x + 40, y + yPlus, text, rank);
                /******** ���Ӹ��ӽڵ���� ************/
                themeLabel.setFather(fatherLabel);
                fatherLabel.addChild(themeLabel);
                /******** ��������ȼ� ************/
                themeLabel.setRank(fatherLabel.getRank() + 1);
                themeLabel.updateRankSize(themeLabel.getRank());
                MainWindow.pan.add(themeLabel);
                /******** ���Ӹ��ӽڵ������� ************/
                ConnectLine connectLine = new ConnectLine(fatherLabel.getThemeRightX(), fatherLabel.getThemeMidY(), themeLabel.getThemeLeftX(), themeLabel.getThemeMidY());
                MainWindow.pan.addConnectLine(themeLabel, connectLine);//��Ⱥ�������,�ӽڵ�ֻ����һ�����
                return themeLabel;
            }
        }
    }
}
