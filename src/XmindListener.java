import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;

import org.xmind.core.Core;
import org.xmind.core.CoreException;
import org.xmind.core.ISheet;
import org.xmind.core.ITopic;
import org.xmind.core.IWorkbook;
import org.xmind.core.IWorkbookBuilder;

public class XmindListener implements ActionListener {
    private JFileChooser fileChooser;
    public XmindListener(){
        this.fileChooser =new JFileChooser();
        fileChooser.setSelectedFile(new File("untitle.xmind"));
        String saveType[] = {"xmind"};
        fileChooser.setFileFilter(new FileNameExtensionFilter("xmind", saveType));
    }
    public void actionPerformed(ActionEvent e){
        File file =null;
        int result =0;

        result =fileChooser.showSaveDialog(MainWindow.frame);
        if(result == JFileChooser.APPROVE_OPTION) {//选择了确定键
            file = fileChooser.getSelectedFile();

            if (!file.getName().endsWith(".xmind")) {//名称修正
                String name = file.getAbsolutePath();
                String [] subs = name.split("\\.");
                for (String str :  subs) System.out.println(str);
                name = subs[0];
                file = new File(name+".xmind");
            }

            System.out.println(file.getName());
            try {
                saveFile(file);//调用保存文件的功能函数
            } catch (IOException | CoreException ex) {
                throw new RuntimeException(ex);
            }
        }else if(result == JFileChooser.CANCEL_OPTION) {//选择了取消键

        }else if(result == JFileChooser.ERROR_OPTION) {//出错了

        }
    }

    void saveFile(File file) throws IOException, CoreException {
        Texts texts = new Texts();
        MainWindow.pan.getRootThemeLabel().toTexts(texts, 0);
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

        OutputStream outputStream = Files.newOutputStream(file.toPath());
        workbook.save(outputStream);
        outputStream.close();
    }
}
