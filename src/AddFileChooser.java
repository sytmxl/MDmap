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
        fileChooser.setApproveButtonText("ȷ��");
        fileChooser.setDialogTitle("���ļ�");
        result =fileChooser.showOpenDialog(MainWindow.frame);
        if(result == JFileChooser.APPROVE_OPTION) {//ѡ����ȷ����
            file = fileChooser.getSelectedFile();
            if(file!=null) {
                try {
                    loadFile(file);//Ϊ�����ļ��Ĺ��ܺ���
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException | CoreException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }else if(result == JFileChooser.CANCEL_OPTION) {//ѡ����ȡ����

        }else if(result == JFileChooser.ERROR_OPTION) {//������

        }
    }

    public static void loadFile(File file) throws IOException, CoreException {
        MainWindow.pan.clearConnectLine();//clear panel

        if (file.getName().endsWith(".md")) {
            FileReader fr = new FileReader(file);
            BufferedReader in = new BufferedReader(fr);
            String content = in.readLine();

            // ���Ȩ�ر�׼
            int last=0;
            Texts texts = new Texts();

            MainWindow.pan.clearConnectLine();
            boolean code = false;
            while (content != null) {
                Matcher matcher5 = Pattern.compile("```").matcher(content);//���������
                if (matcher5.find()) {
                    if (code) {
                        code = false;
                        content = in.readLine();
                        continue;
                    }
                    else {
                        code = true;
                    }
                }
                //md�ı��﷨����
                if (Pattern.compile("^\\s*$").matcher(content).matches() ||
                        Pattern.compile("^\\s*!\\[").matcher(content).find() || code) {
                    //System.out.println("blank");
                    content = in.readLine();
                    continue;
                }

                Matcher matcher4 = Pattern.compile("^> ").matcher(content);//ȥ��"> "
                if (matcher4.find()) {
                    content = content.substring(2);
                }

                Matcher matcher6 = Pattern.compile("^>").matcher(content);//ȥ������">"
                if (matcher6.find()) {
                    content = in.readLine();
                    continue;
                }

                Matcher matcher1 = Pattern.compile("^\\s*- ").matcher(content);
                Matcher matcher2 = Pattern.compile("^#+ ").matcher(content);
                Matcher matcher3 = Pattern.compile("^\\s*\\* ").matcher(content);

                if (matcher1.find()) {
                    System.out.println((last + matcher1.end()) + " " + content.substring(matcher1.end()));
                    texts.list.add(new TabText(last + matcher1.end(), content.substring(matcher1.end()), texts));
                }
                else if (matcher2.find()) {
                    System.out.println((matcher2.end() - 2) + " " + content.substring(matcher2.end()));
                    texts.list.add(new TabText(matcher2.end() - 2, content.substring(matcher2.end()), texts));
                    last = matcher2.end() - 2;
                }
                else if (matcher3.find()) {
                    System.out.println((last + matcher3.end()) + " " + content.substring(matcher3.end()));
                    texts.list.add(new TabText(last + matcher3.end(), content.substring(matcher3.end()), texts));
                }
                else {
                    System.out.println((last + 1) + " " + content);
                    texts.list.add(new TabText(last + 1, content, texts));
                }
                content = in.readLine();
            }

            System.out.println("load md file");
            texts.toThemes();
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

            System.out.println("load xmind file");
            texts.toThemes();
        }
        else {
            System.out.println("load other file");
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new FileInputStream(file));//��������������
                MainWindow.pan.setRootThemeLabel((ThemeLabel) ois.readObject());//���������
                //MainWindow.pan.setallConnectLine((HashMap<ThemeLabel, ConnectLine>) (ois.readObject()));//����HashMap

                MainWindow.pan.getRootThemeLabel().getMyThemeFrame().getButton1().addActionListener(new ThemeFrameActionListener(MainWindow.pan.getRootThemeLabel().getMyThemeFrame()));//���������������޸Ĵ��ڵ�ȷ�ϰ�ť�����¼�����
                ComponentMouseListener componentMouseListener1 = new ComponentMouseListener(MainWindow.pan.getRootThemeLabel());
                MainWindow.pan.getRootThemeLabel().addMouseListener(componentMouseListener1);//�������������¼�����
                MainWindow.pan.getRootThemeLabel().addMouseMotionListener(componentMouseListener1);//�������������¼�����
                /*
                 *���������� ����������ThemeLabel���������Ϊ��ȵ�������ConnectLine�� ��HashMap
                 *�����е���������������Ӧ����
                 */
                for (Map.Entry<ThemeLabel, ConnectLine> item : MainWindow.pan.getallConnectLine().entrySet()) {
                    ThemeLabel key = item.getKey();
                    key.getMyThemeFrame().getButton1().addActionListener(new ThemeFrameActionListener(key.getMyThemeFrame()));
                    ConnectLine val = item.getValue();
                    ComponentMouseListener componentMouseListener = new ComponentMouseListener(key);
                    key.addMouseListener(componentMouseListener);//��������¼�����
                    key.addMouseMotionListener(componentMouseListener);
                }
                MainWindow.pan.add(MainWindow.pan.getRootThemeLabel());

                //�����������������λ��
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

