import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Texts {
    public static int n = 0;
    List<TabText> list = new ArrayList<>();

    public int rootSplit() {
        //???????????????TabTest?§Ò?
        Stack<TabText> fatherList = new Stack<>();
        List<TabText> rootSideList = new ArrayList<>();
        TabText bufferTabText = null;
        TabText root = null;
        int chance = 1;
        int rootTabs = 0;
        for (TabText tabText : list) {
            if (chance == 1) {
                chance--;
                rootTabs = tabText.tabs;
                bufferTabText = tabText;
                root = tabText;
                continue;
            }

            if (tabText.tabs > bufferTabText.tabs) {
                fatherList.add(bufferTabText);
            }
            else if (tabText.tabs < bufferTabText.tabs) {
                while (tabText.tabs <= fatherList.peek().tabs){
                    fatherList.pop();
                }
            }

            if (fatherList.peek().tabs == rootTabs) {
                rootSideList.add(tabText);
            }
            bufferTabText = tabText;
        }
        //01???????§Ò?????§µ??????????left??ture
        int m = rootSideList.size();//???????????????????
        int c = (root.n)/2;//??¦Ë??????????
        int [][] dp = new int[m+1][c+1];

        for(int i = 1; i <= m; i++) {//?????
            for (int j = 0; j <= c; j++) {
                dp[i][j]=0;
            }
        }
        for(int i = 1; i <= m; i++){//01????
            for(int j = 0; j <= c; j++){
                if(rootSideList.get(i-1).n <= j) {
                    if(dp[i-1][j]>dp[i-1][j-rootSideList.get(i-1).n]+rootSideList.get(i-1).n) dp[i][j] = dp[i-1][j];
                    else dp[i][j] = dp[i-1][j-rootSideList.get(i-1).n]+rootSideList.get(i-1).n;
                }
                else {
                    dp[i][j] = dp[i - 1][j] = 0;
                }
            }
        }
        int b = c;
        for(int i = m; i >= 1; i--){//?§Ø??????§µ???§Ö?????
            if(dp[i][b] > dp[i - 1][b]){
                rootSideList.get(i-1).left = true;
                b -= rootSideList.get(i-1).n;
            }
        }

        int left = 0;
        System.out.println("??????: ");
        for (TabText tabText : rootSideList) {
            if (tabText.left) {
                System.out.println(tabText.content);
                left+=tabText.n;
            }
        }
        System.out.println("left: "+left+" all:"+root.n);
        return left;
    }

    public ThemeLabel toThemes() {
        int yGap = 100;
        int yShift = 0;
        // ????????????????
        TabText bufferTabText = null;
        Stack<TabText> tabTexts = new Stack<>();
        int chance = 1;
        for (TabText tabText : this.list) {
            if (chance == 1) {
                bufferTabText = tabText;
                chance--;
                continue;
            }
            if (bufferTabText != null) {
                if (tabText.tabs > bufferTabText.tabs) {
                    tabTexts.push(bufferTabText);
                }
                else if (tabText.tabs < bufferTabText.tabs) {
                    while (tabTexts.peek().tabs >= tabText.getTabs()) {
                        tabTexts.pop();
                    }
                }
            }
            if (tabText.tabs <= bufferTabText.tabs) {
                for (TabText tabText1 : tabTexts) {
                    tabText1.n++;
                }
            }

            bufferTabText = tabText;
        }

        System.out.println("n:");
        for (TabText text : this.list){
            text.from = (float) -(text.n - 1)/2;
            System.out.println("n: "+text.n+"from: "+text.from+" tabs: "+text.tabs+" content: "+text.content);
        }

        int left = this.rootSplit();//??????01???????????????????????????

        ThemeLabel bufferThemeLabel = null;
        ThemeLabel root = null;
        ButtonMouseListener.fatherLabel = null;
        Stack<ThemeLabel> fatherList = new Stack<>();
        ThemeLabel chosenLabel = null;
        for (TabText text : this.list){
            if (ButtonMouseListener.fatherLabel == null) {//????????????
                MainWindow.pan.getRootThemeLabel().setText(text.content);
                bufferThemeLabel = MainWindow.pan.getRootThemeLabel();
                bufferThemeLabel.updateSize();
                bufferTabText = text;

                bufferThemeLabel.leftFrom = (float) -(left - 1)/2;
                bufferThemeLabel.from = (float) - (text.n - left - 1)/2;
                ButtonMouseListener.fatherLabel = bufferThemeLabel;
                fatherList.add(bufferThemeLabel);
                root = bufferThemeLabel;

                System.out.println("---------");
                System.out.println("1");
                System.out.println("leftFrom: "+ bufferThemeLabel.leftFrom);
                System.out.println("from: "+ bufferThemeLabel.from);
                System.out.println("---------");
                continue;
            }
            if (text.tabs > bufferTabText.tabs) {//??????????????
                if (bufferThemeLabel != root) {
                    bufferThemeLabel.from = bufferTabText.from;
                    fatherList.add(bufferThemeLabel);
                    ButtonMouseListener.fatherLabel = fatherList.peek();
                }
                else {
                    System.out.println("skip for root");
                }
                //System.out.println("add:"+fatherList.peek().getText());

                System.out.print("2 ");
            }
            else if (text.tabs < bufferTabText.tabs) {//???????§³?????
                while (text.tabs <= fatherList.peek().tabs){
                    //System.out.println("pop:"+fatherList.peek().getText());
                    fatherList.pop();
                }

                ButtonMouseListener.fatherLabel = fatherList.peek();

                System.out.print("3 ");
            }
            else {
                System.out.print("0 ");
            }

            if (text.left) {//??rootSplit?§Ø????????????????????leftFrom
                yShift = (int) ((fatherList.peek().leftFrom + ((float) text.n - 1) / 2) * yGap);
            }
            else {
                yShift = (int) ((fatherList.peek().from + ((float) text.n - 1) / 2) * yGap);
            }

            if (text.left) {
                System.out.println(text.content+" leftFrom: "+fatherList.peek().leftFrom + " n:"+text.n + " yShift:"+yShift);
                fatherList.peek().leftFrom += text.n;
            }
            else {
                System.out.println(text.content+" from:" + fatherList.peek().from + " n:"+text.n + " yShift:"+yShift);
                fatherList.peek().from += text.n;
            }
            bufferThemeLabel = ButtonMouseListener.add(text.content, text.getTabs(), yShift, text.left, text.isChoosen);
            bufferTabText = text;
            if (bufferThemeLabel.isChoosen) {
                chosenLabel = bufferThemeLabel;
            }
        }

        root.getRange();// cal the range
        if (root.left < 100 || root.top < 100)
            new ThemeDetect(MainWindow.pan).moveAll(-root.left + 100, -root.top + 100);// move according to range(to left top corner)

        //Constent.paintPanelHight = root.bottom-root.top + 600;//update pan size
        //Constent.paintPanelWidth = root.right-root.left + 600;

        ButtonMouseListener.setEveryFont(MainWindow.pan.getRootThemeLabel());
        return chosenLabel;
    }
}
