import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Vector;
public class ThemeLabel extends JLabel{
    int depth=0;
    int leaves=0;
    int left = 100000;//在根结点中保存左边界
    int top = 100000;//在根结点中保存上边界
    int right = -100000;
    int bottom = -100000;
    public boolean currentChoose = false;
    public boolean isChoosen = false;
    public int tabs;
    public float from;
    public float leftFrom;
    /******* 父亲孩子 *****/
    private ThemeLabel father=null;
    private Vector<ThemeLabel> child = null;

    /******* 节点等级 *****/
    private int rank=0;

    /********* 测试获取label ********/
    public static int names=0;
    public int LabelName;

    /********* 定位尺寸 ********/
    private int ThemeSizeX = 80;
    private int ThemeSizeY = 60;
    private int ThemeLeftX, ThemeRightX, ThemeMidY, ThemeTopY;

    /********* 节点删除 ********/
    public boolean isLive = true;
    /********* 修改内容窗口 ********/
    private ThemeFrame myThemeFrame = null;

    public ThemeLabel(int x,int y){
        super("",JLabel.CENTER);
        this.child = new Vector<ThemeLabel>();

        this.ThemeLeftX = x;
        this.ThemeRightX = x + ThemeSizeX;

        this.ThemeTopY=y;
        this.ThemeMidY = y + ThemeSizeY / 2;

        this.LabelName=names;
        names++;
        this.setFont(Font.getFont("微软雅黑"));
        this.setText("text");
        this.setBounds(x, y, this.ThemeSizeX, this.ThemeSizeY);

        this.setOpaque(true);
        this.setVisible(true);
        //初始化修改窗口
        this.myThemeFrame = new ThemeFrame(this);
        /******** 设置鼠标的监听 ************/
        ComponentMouseListener componentMouseListener = new ComponentMouseListener(this);
        this.addMouseListener( componentMouseListener);// 加入鼠标事件监听
        this.addMouseMotionListener(componentMouseListener);
    }

    public ThemeLabel(int x,int y, String text, int tabs, boolean isChoosen){
        super("",JLabel.CENTER);
        this.child = new Vector<ThemeLabel>();

        this.isChoosen = isChoosen;

        this.ThemeLeftX = x;
        this.ThemeRightX = x + ThemeSizeX;

        this.ThemeTopY=y;
        this.ThemeMidY = y + ThemeSizeY / 2;
        /*
        this.ThemeLeftX = x - ThemeSizeX;
        this.ThemeRightX = x;

        this.ThemeTopY= y + ThemeSizeY / 2;
        this.ThemeMidY = y;

         */
        this.setFont(Font.getFont("微软雅黑"));
        this.LabelName=names;
        names++;

        this.setText(text);
        //this.updateSize();
        this.tabs = tabs;
        this.setBounds(x, y, this.ThemeSizeX, this.ThemeSizeY);

        this.setOpaque(true);
        this.setVisible(true);
        //初始化修改窗口
        this.myThemeFrame = new ThemeFrame(this);
        /******** 设置鼠标的监听 ************/
        ComponentMouseListener componentMouseListener = new ComponentMouseListener(this);
        this.addMouseListener( componentMouseListener);// 加入鼠标事件监听
        this.addMouseMotionListener(componentMouseListener);
    }
    public int getNewLength() {
        String str = this.getText();
        int x=3;//左右留余地大小
        // 计算str中中文字符个数
        int count = 0;
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            String len = Integer.toBinaryString(c[i]);
            if (len.length() > 8)
                count++;
        }
        double chineseSize = 20.0 / 15;
        double englishSize = 18.0 / 25;
        int fontSize = this.getFont().getSize();
        if (fontSize == 26) {
            chineseSize = 31.0/26;
            englishSize = 18.0 / 25;
        }
        if (fontSize == 28) {
            chineseSize = 17.5 / 15;
            englishSize = 16.0 / 25;
        }
        if (count <6) {
            chineseSize = 20.0 / 15;
        }
        if(count<=2){
            chineseSize = 30/15;
        }
        if (count <=1) {
            chineseSize = 60/15;
        }
        if (str.length() - count <=6) {
            englishSize = 25.0 / 25;
        }
        if (str.length() - count <2) {
            englishSize = 40.0 / 25;
        }
        if(this.rank==0){
            x=0;
        }else if(this.rank==1){
            x=0;
        }else{
            x=0;
        }
        return (int) ((str.length() - count) * (fontSize * englishSize)) + (int) ((count) * (fontSize * chineseSize));

    }
    /******** 因为字长更新主题长度 ************/
    public void updateSize() {
        int deltaSizeX = this.getNewLength() - this.ThemeSizeX;
        int deltaSizeY = 0;
        this.ThemeSizeX += deltaSizeX;
        this.ThemeSizeY += deltaSizeY;
        this.ThemeMidY +=deltaSizeY / 2;
        System.out.println(this.ThemeSizeX+","+this.ThemeSizeY);
        if (this.getThemeRightX() < MainWindow.pan.getRootThemeLabelLeftX()) {
            this.ThemeLeftX = this.getX() - deltaSizeX;
            this.setBounds(this.getX() - deltaSizeX, this.getY(), this.ThemeSizeX, this.ThemeSizeY);
        } else {
            this.ThemeRightX = this.getX() + this.ThemeSizeX;
            this.setBounds(this.getX(), this.getY(), this.ThemeSizeX, this.ThemeSizeY);
        }
        this.setFont(new Font("微软雅黑",Font.BOLD,this.getFont().getSize()));
    }
    /******** 因为等级更新主题长度 ************/
    public void updateRankSize(int rank){
        if(rank==0){
            this.ThemeSizeY+=25;
            this.ThemeSizeX+=60;
            this.ThemeRightX = this.getX() + ThemeSizeX;
            this.ThemeMidY = this.getY() + ThemeSizeY / 2;
        }
        else if(rank == 1) {
            this.ThemeSizeY+=10;
            this.ThemeSizeX += 60;
            this.ThemeRightX = this.getX() + ThemeSizeX;
            this.ThemeMidY = this.getY() + ThemeSizeY / 2;
        }
        //默认最小 有以上情况加大
        this.setBounds(this.getX(),this.getY(),this.ThemeSizeX,this.ThemeSizeY);
    }

    public void addChild(ThemeLabel themeLabel){
        child.add(themeLabel);
    }
    public void removeChild(ThemeLabel child) {
        this.child.remove(child);
    }
    public Vector<ThemeLabel> getallChild() {
        return this.child;
    }
    public ThemeLabel getChild(int index) {
        return this.child.get(index);
    }
    public int getChildNUm(){
        return this.child.size();
    }
    public void setFather(ThemeLabel themeLabel){
        this.father=themeLabel;
    }
    public ThemeLabel getFather(){
        return this.father;
    }
    public void updateLocation(int x, int y) {
        this.ThemeLeftX = x;
        this.ThemeRightX = x + this.ThemeSizeX;
        this.ThemeMidY = y + this.ThemeSizeY / 2;
        this.ThemeTopY = y;
        this.setBounds(x, y, this.ThemeSizeX, this.ThemeSizeY);
    }

    public int getThemeLeftX() {
        return ThemeLeftX;
    }

    public int getThemeRightX(){
        return ThemeRightX;
    }

    public int getThemeMidY() {
        return ThemeMidY;
    }
    public int getThemeTopY() {
        return ThemeTopY;
    }
    public int getThemeSizeX(){
        return ThemeSizeX;
    }
    public int getThemeSizeY(){
        return ThemeSizeY;
    }

    public ThemeFrame getMyThemeFrame(){
        return this.myThemeFrame;
    }

    public int getRank(){
        return this.rank;
    }
    public void setRank(int rank){
        this.rank=rank;
    }

    public void toText(BufferedWriter out, String level, String fatherLevel, int depth) throws IOException {
        if (this.child.size() == 0 && !fatherLevel.endsWith("- ") && !level.endsWith("##")) {//最低的
            out.write('\n');
            out.write(this.getText() + '\n');
        }
        else {
            out.write(level + " " + this.getText() + '\n');
        }

        for (ThemeLabel label : child) {
            if(level.endsWith("#")) {
                if(level.length() <= depth) {
                    label.toText(out, level+"#", level, depth);
                }
                else {
                    label.toText(out, "- ", level, depth);
                }
            }
            else { // 超过界限使用点层次
                label.toText(out, "  " + level, level, depth);
            }
        }
    }
    public void toTexts(Texts texts, int tabs) {
        texts.list.add(new TabText(tabs, this.getText(), texts, isChoosen));
        for (ThemeLabel themeLabel : child) {
            themeLabel.toTexts(texts, tabs+1);
            themeLabel.setFont(new Font("微软雅黑",Font.BOLD,themeLabel.getFont().getSize()));
        }
    }

    public int getThemeMidX() {
        return this.ThemeSizeX/2 + this.getThemeLeftX();
    }
    
    public void delChild() {
        child = new Vector<>();
    }

    public void getRange() {
        ThemeLabel root = MainWindow.pan.getRootThemeLabel();
        if (this == root) {
            top = left = 100000;
            bottom = right = -100000;
        }
        if (this.getThemeLeftX() < MainWindow.pan.getRootThemeLabel().left) {
            root.left = this.getThemeLeftX();
        }
        if (this.getThemeTopY() < root.top) {
            root.top = this.getThemeTopY();
        }
        if (this.getThemeTopY() + this.ThemeSizeY > root.bottom) {
            root.bottom = this.getThemeTopY() + this.ThemeSizeY;
        }
        if (this.getThemeLeftX() + this.ThemeSizeX > root.right) {
            root.right = this.getThemeLeftX() + this.ThemeSizeX;
        }
        for (ThemeLabel themeLabel : child) {
            themeLabel.getRange();
        }
    }

    public void getDepth(int depth) {
        this.depth = depth;
        for (ThemeLabel themeLabel : child) {
            themeLabel.getDepth(depth+1);
        }
    }

    public int getLeafDepthSum() {
        if (this == MainWindow.pan.getRootThemeLabel()) {
            leaves = 0;
        }
        if (child.size()==0) {
            MainWindow.pan.getRootThemeLabel().leaves++;
            return depth;
        }
        else {
            int sum=0;
            for (ThemeLabel themeLabel : child) {
                sum += themeLabel.getLeafDepthSum();
            }
            return sum;
        }
    }
}
