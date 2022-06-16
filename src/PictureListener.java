import org.xmind.core.CoreException;

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
            file = fileChooser.getSelectedFile();
            System.out.println(file.getName());
            //saveFile2();
            Dimension imageSize = MainWindow.pan.getSize();
            BufferedImage image = new BufferedImage(imageSize.width,imageSize.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = image.createGraphics();
            MainWindow.pan.paint(graphics);
            graphics.dispose();
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
