import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;

public class DeleteThemeListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        new ThemeDetect(MainWindow.pan).deleteConnect(ComponentMouseListener.lastChooseLabel);
        ComponentMouseListener.lastChooseLabel=MainWindow.pan.getRootThemeLabel();
        //自动排版
        Texts texts = new Texts();
        MainWindow.pan.getRootThemeLabel().toTexts(texts, 0);
        MainWindow.pan.clearConnectLine();
        texts.toThemes();
    }
}
