import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonMouseListener implements ActionListener { //��ӽڵ�
    public static ThemeLabel fatherLabel=null;//��ʼ����ɸ��ڵ�,ÿ�ε����Ҳ��ɸ��ڵ�
    public int x=0,y=0;
    public static int nowmax=0;
    public static void setFatherLabel(ThemeLabel themeLabel){
        fatherLabel=themeLabel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        fatherLabel=Constent.fatherLabel;
        if(fatherLabel!=null){
            fatherLabel.isChoosen = true;//��¼��ѡ�е�label��ת��ʱ������Ϣ�Թ��ָ�
            System.out.println("father"+fatherLabel.LabelName);
        }else{
            fatherLabel=Constent.fatherLabel=MainWindow.pan.getRootThemeLabel();
        }
        int num=0,max=-10;
        if (fatherLabel == MainWindow.pan.getRootThemeLabel() || fatherLabel == null) {//����Ǹ��ڵ���ӽڵ�
            x = MainWindow.pan.getRootThemeLabelRightX();
            num= MainWindow.pan.getRootThemeLabel().getChildNUm();
            if(num>0){
                max=MainWindow.pan.getRootThemeLabel().getChild(0).getThemeMidY();
                for(int i=0;i<num;i++){
                    if(max<MainWindow.pan.getRootThemeLabel().getChild(i).getThemeMidY()){
                        max=MainWindow.pan.getRootThemeLabel().getChild(i).getThemeMidY();
                    }
                }
            }
            if(nowmax<max){
                nowmax=max+80;
            }
            y = nowmax;
            ThemeLabel themeLabel = new ThemeLabel(x + 40, y);
            themeLabel.setBackground(new Color(250, 245, 228));
            themeLabel.setForeground(new Color(18, 91, 80));
            Border blackLine = BorderFactory.createLineBorder(new Color(18, 91, 80),5,true);
            themeLabel.setBorder(blackLine);
            themeLabel.setFont(new Font("΢���ź�",Font.BOLD,40));
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
        }
        else {
            int distance = fatherLabel.getThemeLeftX() - MainWindow.pan.getRootThemeLabelRightX();
            if (distance < 0) {
                x = fatherLabel.getThemeLeftX();
                num= fatherLabel.getChildNUm();
                if(num>0){
                    max=fatherLabel.getChild(0).getThemeMidY();
                    for(int i=0;i<num;i++){
                        if(max<fatherLabel.getChild(i).getThemeMidY()){
                            max=fatherLabel.getChild(i).getThemeMidY();
                        }
                    }
                }
                if(nowmax<max){
                    nowmax=max+40;
                }
                y = nowmax;
                ThemeLabel themeLabel = new ThemeLabel(x - 40, y);
                themeLabel.setBackground(new Color(248, 180, 0));
                themeLabel.setForeground(Color.white);
                themeLabel.setFont(new Font("΢���ź�",Font.BOLD,25));
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
                num= fatherLabel.getChildNUm();
                if(num>0){
                    max=fatherLabel.getChild(0).getThemeMidY();
                    for(int i=0;i<num;i++){
                        if(max<fatherLabel.getChild(i).getThemeMidY()){
                            max=fatherLabel.getChild(i).getThemeMidY();
                        }
                    }
                }
                if(nowmax<max){
                    nowmax=max+40;
                }
                y = nowmax;
                ThemeLabel themeLabel = new ThemeLabel(x + 40, y);
                themeLabel.setBackground(new Color(248, 180, 0));
                themeLabel.setForeground(Color.white);
                themeLabel.setFont(new Font("΢���ź�",Font.BOLD,25));
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
            //fatherLabel = null;
        }
        //�Զ��Ű�
        Texts texts = new Texts();
        MainWindow.pan.getRootThemeLabel().toTexts(texts, 0);
        MainWindow.pan.clearConnectLine();
        ThemeLabel chosenLabel = texts.toThemes();
        if (chosenLabel == null) {//���ڵ�ʱΪnull
            chosenLabel = MainWindow.pan.getRootThemeLabel();
        }
        Constent.fatherLabel = fatherLabel = ComponentMouseListener.lastChooseLabel = chosenLabel;
        ComponentMouseListener.addBorder(chosenLabel);
        chosenLabel.isChoosen = false;
    }

    static public ThemeLabel add(String text, int tabs, int yPlus, boolean left, boolean isChoosen){
        int x,y;
        int xShift = 100;
        if (fatherLabel == MainWindow.pan.getRootThemeLabel() || fatherLabel == null) {//����Ǹ��ڵ���ӽڵ�
            if (left) {
                x = MainWindow.pan.getRootThemeLabelLeftX();
                y = MainWindow.pan.getRootThemeLabelTopY();
                ThemeLabel themeLabel = new ThemeLabel(x - xShift * 3, y + yPlus, text, tabs, isChoosen);//����xShift
                themeLabel.setBackground(new Color(250, 245, 228));
                themeLabel.setForeground(new Color(18, 91, 80));
                Border blackLine = BorderFactory.createLineBorder(new Color(18, 91, 80),5,true);
                themeLabel.setBorder(blackLine);
                themeLabel.setFont(new Font("΢���ź�",Font.BOLD,40));
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
                themeLabel.updateSize();
                ConnectLine connectLine = new ConnectLine(MainWindow.pan.getRootThemeLabelLeftX(), MainWindow.pan.getRootThemeLabelMidY(), themeLabel.getThemeRightX(), themeLabel.getThemeMidY());
                MainWindow.pan.addConnectLine(themeLabel, connectLine);//��Ⱥ�������,�ӽڵ�ֻ����һ�����
                return themeLabel;
            }
            else {
                x = MainWindow.pan.getRootThemeLabelRightX();
                y = MainWindow.pan.getRootThemeLabelTopY();
                ThemeLabel themeLabel = new ThemeLabel(x + xShift, y + yPlus, text, tabs, isChoosen);
                themeLabel.setBackground(new Color(250, 245, 228));
                themeLabel.setForeground(new Color(18, 91, 80));
                Border blackLine = BorderFactory.createLineBorder(new Color(18, 91, 80),5,true);
                themeLabel.setBorder(blackLine);
                themeLabel.setFont(new Font("΢���ź�",Font.BOLD,40));
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
                themeLabel.updateSize();
                ConnectLine connectLine = new ConnectLine(MainWindow.pan.getRootThemeLabelRightX(), MainWindow.pan.getRootThemeLabelMidY(), themeLabel.getThemeLeftX(), themeLabel.getThemeMidY());
                MainWindow.pan.addConnectLine(themeLabel, connectLine);//��Ⱥ�������,�ӽڵ�ֻ����һ�����
                return themeLabel;
            }
        }
        else {
            int distance = fatherLabel.getThemeLeftX() - MainWindow.pan.getRootThemeLabelRightX();
            if (distance < 0 || left) {
                x = fatherLabel.getThemeLeftX();
                y = fatherLabel.getThemeTopY();
                ThemeLabel themeLabel = new ThemeLabel(x - xShift*2, y + yPlus, text, tabs, isChoosen);
                themeLabel.setBackground(new Color(248, 180, 0));
                themeLabel.setForeground(Color.white);
                themeLabel.setFont(new Font("΢���ź�",Font.BOLD,25));
                MainWindow.pan.add(themeLabel);
                /******** ���Ӹ��ӽڵ���� ************/
                themeLabel.setFather(fatherLabel);
                fatherLabel.addChild(themeLabel);
                /******** ��������ȼ� ************/
                themeLabel.setRank(fatherLabel.getRank() + 1);
                themeLabel.updateRankSize(themeLabel.getRank());
                MainWindow.pan.add(themeLabel);
                /******** ���Ӹ��ӽڵ������� ************/
                themeLabel.updateSize();
                ConnectLine connectLine = new ConnectLine(fatherLabel.getThemeLeftX(), fatherLabel.getThemeMidY(), themeLabel.getThemeRightX(), themeLabel.getThemeMidY());
                MainWindow.pan.addConnectLine(themeLabel, connectLine);//��Ⱥ�������,�ӽڵ�ֻ����һ�����
                return themeLabel;
            }
            else {
                x = fatherLabel.getThemeRightX();
                y = fatherLabel.getThemeTopY();
                ThemeLabel themeLabel = new ThemeLabel(x + xShift, y + yPlus, text, tabs, isChoosen);
                themeLabel.setBackground(new Color(248, 180, 0));
                themeLabel.setForeground(Color.white);
                themeLabel.setFont(new Font("΢���ź�",Font.BOLD,25));

                /******** ���Ӹ��ӽڵ���� ************/
                themeLabel.setFather(fatherLabel);
                fatherLabel.addChild(themeLabel);
                /******** ��������ȼ� ************/
                themeLabel.setRank(fatherLabel.getRank() + 1);
                themeLabel.updateRankSize(themeLabel.getRank());
                MainWindow.pan.add(themeLabel);
                /******** ���Ӹ��ӽڵ������� ************/
                themeLabel.updateSize();
                ConnectLine connectLine = new ConnectLine(fatherLabel.getThemeRightX(), fatherLabel.getThemeMidY(), themeLabel.getThemeLeftX(), themeLabel.getThemeMidY());
                MainWindow.pan.addConnectLine(themeLabel, connectLine);//��Ⱥ�������,�ӽڵ�ֻ����һ�����
                return themeLabel;
            }
        }
    }
}
