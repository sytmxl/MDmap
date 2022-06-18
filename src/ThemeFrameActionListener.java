import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThemeFrameActionListener implements ActionListener {
    private ThemeFrame from = null;
    public ThemeFrameActionListener(ThemeFrame from) {
        super();
        this.from = from;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == from.getButton1()) {
            from.getowner().setText(from.getInput().getText());
            int deltaSizex=from.getowner().getNewLength()-from.getowner().getThemeSizeX();
            from.getowner().updateSize();
            new ThemeDetect(MainWindow.pan).themesizeChangeMove(from.getowner(),deltaSizex);
            from.getowner().setFont(new Font("Î¢ÈíÑÅºÚ",Font.BOLD,from.getowner().getFont().getSize()));
        }
        else if(e.getSource() ==from.getButton2()) {

        }
        from.setVisible(false);
    }
}
