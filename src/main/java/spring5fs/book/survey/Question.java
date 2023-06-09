package spring5fs.book.survey;

import java.util.Collections;
import java.util.List;

public class Question {

    private String title;
    private List<String> options;

    public Question(final String title, final List<String> options) {
        this.title = title;
        this.options = options;
    }

    public Question(final String title) {
        this(title, Collections.emptyList());
    }

    public String getTitle() {
        return title;
    }

    public List<String> getOptions() {
        return options;
    }

    public boolean isChoice() {
        return options != null && !options.isEmpty();
    }
}
