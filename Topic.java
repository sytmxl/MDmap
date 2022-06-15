import org.xmind.core.ITopic;
import org.xmind.core.IWorkbook;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    String content;

    List<Topic> children;

    public Topic(ITopic iTopic) {
        this.content = iTopic.getTitleText();
        children = new ArrayList<>();
        for (ITopic child : iTopic.getAllChildren()) {
            children.add(new Topic(child));
        }
    }

    public void toText(Texts texts, int tabs) {
        texts.list.add(new TabText(tabs, content, texts));
        for (Topic topic : children) {
            topic.toText(texts, tabs+1);
        }
    }
}
