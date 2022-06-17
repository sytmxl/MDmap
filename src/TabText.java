//import org.xmind.core.ITopic;
//import org.xmind.core.IWorkbook;

import org.xmind.core.ITopic;
import org.xmind.core.IWorkbook;

public class TabText {
    boolean isChoosen = false;
    int tabs;
    String content;
    Texts texts;
    boolean left;
    int n;
    float from;

    public int getTabs() {
        return tabs;
    }

    public String getContent() {
        return content;
    }

    public Texts getTexts() {
        return texts;
    }

    public TabText(int tabs, String content, Texts texts) {
        this.tabs = tabs;
        this.content = content;
        this.texts = texts;
        this.n=1;
        this.left=false;
        this.from = 0.1F;
    }

    public TabText(int tabs, String content, Texts texts, boolean isChoosen) {
        this.tabs = tabs;
        this.content = content;
        this.texts = texts;
        this.n=1;
        this.left=false;
        this.from = 0.1F;
        this.isChoosen = isChoosen;
    }

    public ITopic toTopic(IWorkbook workbook) {
        Texts.n++;
        ITopic topic = workbook.createTopic();
        topic.setTitleText(content);
        while (Texts.n < texts.list.size()) {
            TabText tt = texts.list.get(Texts.n);
            if (tt.tabs > tabs) {
                topic.add(tt.toTopic(workbook), ITopic.ATTACHED);
            }
            else {
                break;
            }
        }
        return topic;
    }
}
