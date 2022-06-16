import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Texts {
    public static int n = 0;
    List<TabText> list = new ArrayList<>();

    public int rootSplit() {
        //获取父节点为根结点的TabTest列表
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
        //01背包分列表为两列，在左的属性left标ture
        int m = rootSideList.size();//连接根的数量为物品数量
        int c = (root.n)/2;//中位线为背包容积
        int [][] dp = new int[m+1][c+1];

        for(int i = 1; i <= m; i++) {//初始化
            for (int j = 0; j <= c; j++) {
                dp[i][j]=0;
            }
        }
        for(int i = 1; i <= m; i++){//01背包
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
        for(int i = m; i >= 1; i--){//判断是否选中，选中的去左边
            if(dp[i][b] > dp[i - 1][b]){
                rootSideList.get(i-1).left = true;
                b -= rootSideList.get(i-1).n;
            }
        }

        int left = 0;
        System.out.println("分离结果: ");
        for (TabText tabText : rootSideList) {
            if (tabText.left) {
                System.out.println(tabText.content);
                left+=tabText.n;
            }
        }
        System.out.println("left: "+left+" all:"+root.n);
        return left;
    }
}
