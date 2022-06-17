
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PictureListener implements ActionListener {
    private JFileChooser fileChooser;
    public PictureListener(){fileChooser=new JFileChooser();}
    @Override
    public void actionPerformed(ActionEvent e) {
        File file =null;
        int result =0;
        fileChooser.setDialogTitle("Save Picture");
        fileChooser.setSelectedFile(new File("untitle.jpg"));
        String saveType[] = {"jpg"};
        fileChooser.setFileFilter(new FileNameExtensionFilter("jpg", saveType));
        result =fileChooser.showSaveDialog(MainWindow.frame);
        if(result == JFileChooser.APPROVE_OPTION) {//选择了确定键
            // move all to left top corner
            ThemeLabel root = MainWindow.pan.getRootThemeLabel();
            root.getRange();
            new ThemeDetect(MainWindow.pan).moveAll(-root.left + 100, -root.top + 100);

            file = fileChooser.getSelectedFile();
            System.out.println(file.getName());
            //saveFile2();
            //Dimension imageSize = MainWindow.pan.getSize();
            System.out.println(root.right-root.left + 200);
            System.out.println(root.bottom-root.top + 200);
            BufferedImage image = new BufferedImage(root.right-root.left + 200,root.bottom-root.top + 200, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = image.createGraphics();
            MainWindow.pan.paint(graphics);
            graphics.dispose();

            if (root.left > 100 && root.top > 100)//没越界就移回去
                new ThemeDetect(MainWindow.pan).moveAll(root.left - 100, root.top - 100);
            try {
                ImageIO.write(image, "jpg",file);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }else if(result == JFileChooser.CANCEL_OPTION) {//选择了取消键

        }else if(result == JFileChooser.ERROR_OPTION) {//出错了

        }
    }
}
