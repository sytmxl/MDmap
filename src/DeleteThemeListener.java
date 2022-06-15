import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;

public class DeleteThemeListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        new ThemeDetect(MainWindow.pan).deleteConnect(ButtonMouseListener.fatherLabel);
        ButtonMouseListener.fatherLabel=MainWindow.pan.getRootThemeLabel();
    }
}
