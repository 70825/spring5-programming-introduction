package spring5fs.book.survey;

import java.util.List;

public class AnsweredData {

    private List<String> responses;
    private Respondent res;

    public List<String> getResponses() {
        return responses;
    }

    public void setResponses(final List<String> responses) {
        this.responses = responses;
    }

    public Respondent getRes() {
        return res;
    }

    public void setRes(final Respondent res) {
        this.res = res;
    }
}
