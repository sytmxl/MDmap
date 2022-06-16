import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Map;

import org.xmind.core.Core;
import org.xmind.core.CoreException;
import org.xmind.core.ISheet;
import org.xmind.core.ITopic;
import org.xmind.core.IWorkbook;
import org.xmind.core.IWorkbookBuilder;

public class SaveFileChooser implements ActionListener {
    private JFileChooser fileChooser;
    public SaveFileChooser(){
        this.fileChooser =new JFileChooser();
    }
    public void actionPerformed(ActionEvent e){
        File file =null;
        int result =0;
        result =fileChooser.showSaveDialog(MainWindow.frame);
        if(result == JFileChooser.APPROVE_OPTION) {//选择了确定键
            file = fileChooser.getSelectedFile();
            System.out.println(file.getName());
            //saveFile2();
            try {
                saveFile(file);//调用保存文件的功能函数
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (CoreException ex) {
                throw new RuntimeException(ex);
            }
        }else if(result == JFileChooser.CANCEL_OPTION) {//选择了取消键

        }else if(result == JFileChooser.ERROR_OPTION) {//出错了

        }
    }

    void saveFile(File file) throws IOException, CoreException {
        if(file!=null&&file.getName().endsWith(".mm")){
            ObjectOutputStream oos=null;//创建输出流
            try {
                if((!file.exists())){
                    file.createNewFile();
                }
                //清空主题类所有内容修改窗口中不能保存的内容
                MainWindow.pan.getRootThemeLabel().getMyThemeFrame().getInput().setText(null);

                for (Map.Entry<ThemeLabel, ConnectLine> item : MainWindow.pan.getallConnectLine().entrySet()) {
                    ThemeLabel key = item.getKey();
                    key.getMyThemeFrame().getInput().setText(null);
                }
                //按顺序将对象输出保存
                oos=new ObjectOutputStream(new FileOutputStream(file));

                oos.writeObject(MainWindow.pan.getRootThemeLabel());
                oos.writeObject(MainWindow.pan.getallConnectLine());

            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } finally {
                try {
                    oos.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
        else if (file!=null&&file.getName().endsWith(".md")){
            FileWriter fw = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fw);
            MainWindow.pan.getRootThemeLabel().toText(out, "#");
            out.flush();
            out.close();
        }
        else if (file!=null&&file.getName().endsWith(".xmind")){
            Texts texts = new Texts();
            MainWindow.pan.getRootThemeLabel().toTextForXmind(texts, 0);
            // get workbook
            IWorkbookBuilder workbookBuilder = Core.getWorkbookBuilder();
            IWorkbook workbook = workbookBuilder.createWorkbook();

            // get sheet
            ISheet primarySheet = workbook.getPrimarySheet();

            // get roottopic
            ITopic rootTopic = primarySheet.getRootTopic();

            // 设置中心标题为第一行文字
            String title = texts.list.get(0).content;
            rootTopic.setTitleText(title);
            texts.list.remove(0);

            // 转换
            while (Texts.n < texts.list.size()) {
                rootTopic.add(texts.list.get(Texts.n).toTopic(workbook), ITopic.ATTACHED);
            }
            //workbook.save(file.getName());
            OutputStream outputStream = new FileOutputStream(file);
            workbook.save(outputStream);
            outputStream.close();
        }
    }
}
