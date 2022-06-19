import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SaveFileChooser implements ActionListener {
    private JFileChooser fileChooser;
    public SaveFileChooser(){
        this.fileChooser =new JFileChooser();
        fileChooser.setSelectedFile(new File("untitle.md"));
        String saveType[] = {"md"};
        fileChooser.setFileFilter(new FileNameExtensionFilter("md", saveType));
    }
    public void actionPerformed(ActionEvent e){
        File file =null;
        int result =0;
        result =fileChooser.showSaveDialog(MainWindow.frame);
        if(result == JFileChooser.APPROVE_OPTION) {//选择了确定键
            file = fileChooser.getSelectedFile();

            if (!file.getName().endsWith(".md")) {//名称修正
                String name = file.getAbsolutePath();
                String [] subs = name.split("\\.");
                for (String str :  subs) System.out.println(str);
                name = subs[0];
                file = new File(name+".md");
            }

            System.out.println(file.getName());
            try {
                saveFile(file);//调用保存文件的功能函数
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }else if(result == JFileChooser.CANCEL_OPTION) {//选择了取消键

        }else if(result == JFileChooser.ERROR_OPTION) {//出错了

        }
    }

    void saveFile(File file) throws IOException{
        FileWriter fw = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(fw);
        MainWindow.pan.getRootThemeLabel().toText(out, "#", "");
        out.flush();
        out.close();
    }
}
