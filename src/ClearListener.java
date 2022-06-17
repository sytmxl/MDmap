
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClearListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        MainWindow.pan.clearConnectLine();
    }
}
