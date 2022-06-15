import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class EditTextListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        ThemeLabel label=ButtonMouseListener.fatherLabel;
        for (Map.Entry<ThemeLabel, ConnectLine> item : MainWindow.pan.getallConnectLine().entrySet()) {
            ThemeLabel key = item.getKey();
            key.getMyThemeFrame().setVisible(false);
        }
        label.getMyThemeFrame().setVisible(true);
        label.getMyThemeFrame().setLocation((Constent.frameWidth-Constent.themeFrameWidth)/2,(Constent.frameHeight-Constent.themeFrameHeight)/2);
        label.getMyThemeFrame().setState(JFrame.NORMAL);
        label.getMyThemeFrame().show();
        label.getMyThemeFrame().getInput().setText(label.getText());
    }
}
