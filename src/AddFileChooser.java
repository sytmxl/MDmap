import org.xmind.core.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddFileChooser implements ActionListener {
    private JFileChooser fileChooser;

    public AddFileChooser(){
        this.fileChooser =new JFileChooser();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        File file =null;
        int result =0;
        fileChooser.setApproveButtonText("确定");
        fileChooser.setDialogTitle("打开文件");
        result =fileChooser.showOpenDialog(MainWindow.frame);
        if(result == JFileChooser.APPROVE_OPTION) {//选择了确定键
            file = fileChooser.getSelectedFile();
            if(file!=null) {
                try {
                    loadFile(file);//为加载文件的功能函数
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException | CoreException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }else if(result == JFileChooser.CANCEL_OPTION) {//选择了取消键

        }else if(result == JFileChooser.ERROR_OPTION) {//出错了

        }
    }

    public static void loadFile(File file) throws IOException, CoreException {
        MainWindow.pan.clearConnectLine();//clear panel
        MainWindow.pan.getRootThemeLabel().updateLocation(300, 100);//location

        if (file.getName().endsWith(".md")) {
            FileReader fr = new FileReader(file);
            BufferedReader in = new BufferedReader(fr);
            String content = in.readLine();

            int x=0, y=0;
            int yGap = 100;
            int xGap = 20;
            // 相对权重标准
            int last=0;
            Texts texts = new Texts();

            MainWindow.pan.clearConnectLine();
            while (content != null) {

                //md文本语法分析
                if (Pattern.compile("^\\s*$").matcher(content).matches() ||
                        Pattern.compile("^\\s*!\\[").matcher(content).find()) {
                    System.out.println("blank");
                    content = in.readLine();
                    continue;
                }

                //themeLabel = new ThemeLabel(x * xGap, y);

                Matcher matcher1 = Pattern.compile("^\\s*- ").matcher(content);
                Matcher matcher2 = Pattern.compile("^#+ ").matcher(content);
                if (matcher1.find()) {
                    System.out.println((last + matcher1.end()) + " " + content.substring(matcher1.end()));
                    x = last + matcher1.end();
                    //themeLabel.setText(content.substring(matcher1.end()));
                    texts.list.add(new TabText(last + matcher1.end(), content.substring(matcher1.end()), texts));
                }
                else if (matcher2.find()) {
                    System.out.println((matcher2.end() - 2) + " " + content.substring(matcher2.end()));
                    x = matcher2.end() - 2;
                    //themeLabel.setText(content.substring(matcher2.end()));
                    texts.list.add(new TabText(matcher2.end() - 2, content.substring(matcher2.end()), texts));
                    last = matcher2.end() - 2;
                }
                else {
                    System.out.println((last + 1) + " " + content);
                    x = last + 1;
                    //themeLabel.setText(content);
                    texts.list.add(new TabText(last + 1, content, texts));
                }

                //ConnectLine connectLine = new ConnectLine(MainWindow.pan.getRootThemeLabelRightX(), MainWindow.pan.getRootThemeLabelMidY(), themeLabel.getThemeLeftX(), themeLabel.getThemeMidY());
                //MainWindow.pan.addConnectLine(themeLabel, connectLine);//入度和连接线,子节点只能有一个入度

                //themeLabel.setRank(themeLabel.getRank());

                content = in.readLine();
                y += yGap;
            }

            // 获取每个标签所占列数
            TabText bufferTabText = null;
            Stack<TabText> tabTexts = new Stack<>();
            int chance = 1;
            for (TabText tabText : texts.list) {
                if (chance == 1) {
                    bufferTabText = tabText;
                    chance--;
                    continue;
                }
                if (bufferTabText != null) {
                    if (tabText.tabs > bufferTabText.tabs) {
                        tabTexts.push(bufferTabText);
                    }
                    else if (tabText.tabs < bufferTabText.tabs) {
                        while (tabTexts.peek().tabs >= tabText.getTabs()) {
                            tabTexts.pop();
                        }
                    }
                }
                if (tabText.tabs <= bufferTabText.tabs) {
                    for (TabText tabText1 : tabTexts) {
                        tabText1.n++;
                    }
                }

                bufferTabText = tabText;
            }

            System.out.println("n:");
            for (TabText text : texts.list){
                text.from = (float) -(text.n - 1)/2;
                System.out.println("from: "+text.from+" tabs: "+text.tabs+" content: "+text.content);
            }

            System.out.println("load md file");
            int buffergap = 1;
            int yShift;
            float from=0;
            ThemeLabel bufferThemeLabel = null;
            ButtonMouseListener.fatherLabel = null;
            Stack<ThemeLabel> fatherList = new Stack<>();
            for (TabText text : texts.list){
                    if (ButtonMouseListener.fatherLabel == null) {//第一个
                        MainWindow.pan.getRootThemeLabel().setText(text.content);
                        bufferThemeLabel = MainWindow.pan.getRootThemeLabel();
                        bufferTabText = text;

                        fatherList.add(bufferThemeLabel);
                        ButtonMouseListener.fatherLabel = bufferThemeLabel;
                        bufferThemeLabel.from = text.from;

                        System.out.println("1");
                        continue;
                    }
                    if (text.tabs > bufferTabText.tabs) {//当前级数大于上个
                        bufferThemeLabel.from = bufferTabText.from;
                        fatherList.add(bufferThemeLabel);
                        ButtonMouseListener.fatherLabel = fatherList.peek();

                        System.out.print("2 ");
                    }
                    else if (text.tabs < bufferTabText.tabs) {//当前级数小于上个
                        while (text.tabs <= fatherList.peek().getRank()){
                            fatherList.pop();
                        }

                        ButtonMouseListener.fatherLabel = fatherList.peek();

                        System.out.print("3 ");
                    }
                    else {
                        System.out.print("0 ");
                    }
                    yShift = (int) ((fatherList.peek().from + (text.n - 1)/2) * yGap);
                    bufferThemeLabel = ButtonMouseListener.add(text.content, text.getTabs(), yShift);

                    System.out.print(text.content+" ");
                    System.out.println((fatherList.peek().from + (float) (text.n - 1)/2));

                    fatherList.peek().from += text.n;
                    bufferTabText = text;
                }
        }
        else if (file.getName().endsWith(".xmind")) {
            FileInputStream fileInputStream = new FileInputStream(file);
            String path = file.getAbsolutePath();
            IWorkbookBuilder builder = Core.getWorkbookBuilder();
            IWorkbook workbook = builder.loadFromStream(fileInputStream, ".");
            ISheet sheet = workbook.getPrimarySheet();
            ITopic rootTopic = sheet.getRootTopic();

            Texts texts = new Texts();
            Topic rTopic = new Topic(rootTopic);
            rTopic.toText(texts, 0);

            System.out.println("Tablist:");
            for (TabText text : texts.list){
                System.out.println(text.tabs+text.content);
            }

            System.out.println("load xmind file");
            int x=0, y=0;
            int yGap = 100;
            int buffergap = 1;
            ThemeLabel bufferThemeLabel = null;
            ButtonMouseListener.fatherLabel = null;
            Stack<ThemeLabel> fatherList = new Stack<>();
            for (TabText text : texts.list){
                if (ButtonMouseListener.fatherLabel == null) {
                    MainWindow.pan.getRootThemeLabel().setText(text.content);
                    bufferThemeLabel = MainWindow.pan.getRootThemeLabel();
                    //MainWindow.pan.setRootThemeLabel(bufferThemeLabel);

                    fatherList.add(bufferThemeLabel);
                    ButtonMouseListener.fatherLabel = bufferThemeLabel;

                    System.out.println("1");
                    continue;
                }
                if (text.tabs - fatherList.peek().getRank() > buffergap) {
                    fatherList.add(bufferThemeLabel);
                    ButtonMouseListener.fatherLabel = fatherList.peek();

                    System.out.print("2 ");
                    //y=0;
                }
                else if (text.tabs - fatherList.peek().getRank() < buffergap) {
                    fatherList.pop();
                    ButtonMouseListener.fatherLabel = fatherList.peek();

                    System.out.print("3 ");
                    //y=0;
                }
                else {
                    y++;
                }
                bufferThemeLabel = ButtonMouseListener.add(text.content, text.getTabs(), y * yGap);
                System.out.print("sub: ");
                System.out.println(text.tabs - fatherList.peek().getRank());
            }
        }
        else {
            System.out.println("load other file");
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new FileInputStream(file));//建立对象输入流
                MainWindow.pan.setRootThemeLabel((ThemeLabel) ois.readObject());//读入根主题
                //MainWindow.pan.setallConnectLine((HashMap<ThemeLabel, ConnectLine>) (ois.readObject()));//读入HashMap

                MainWindow.pan.getRootThemeLabel().getMyThemeFrame().getButton1().addActionListener(new ThemeFrameActionListener(MainWindow.pan.getRootThemeLabel().getMyThemeFrame()));//根主题所属内容修改窗口的确认按钮加入事件监听
                ComponentMouseListener componentMouseListener1 = new ComponentMouseListener(MainWindow.pan.getRootThemeLabel());
                MainWindow.pan.getRootThemeLabel().addMouseListener(componentMouseListener1);//根主题加入鼠标事件监听
                MainWindow.pan.getRootThemeLabel().addMouseMotionListener(componentMouseListener1);//根主题加入鼠标事件监听
                /*
                 *遍历保存有 所有主题类ThemeLabel对象和以其为入度的连接线ConnectLine对 的HashMap
                 *对所有的主题类对象加入相应监听
                 */
                for (Map.Entry<ThemeLabel, ConnectLine> item : MainWindow.pan.getallConnectLine().entrySet()) {
                    ThemeLabel key = item.getKey();
                    key.getMyThemeFrame().getButton1().addActionListener(new ThemeFrameActionListener(key.getMyThemeFrame()));
                    ConnectLine val = item.getValue();
                    ComponentMouseListener componentMouseListener = new ComponentMouseListener(key);
                    key.addMouseListener(componentMouseListener);//加入鼠标事件监听
                    key.addMouseMotionListener(componentMouseListener);
                }
                MainWindow.pan.add(MainWindow.pan.getRootThemeLabel());

                //更新所有主题类对象位置
                int deltaSizex = MainWindow.pan.getRootThemeLabel().getNewLength() - MainWindow.pan.getRootThemeLabel().getThemeSizeX();
                MainWindow.pan.getRootThemeLabel().updateSize();
                new ThemeDetect(MainWindow.pan).themesizeChangeMove(MainWindow.pan.getRootThemeLabel(), deltaSizex);
            } catch (IOException | ClassNotFoundException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    ois.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}

