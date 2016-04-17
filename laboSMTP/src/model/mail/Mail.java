package model.mail;

/**
 * Created by sebbos on 16.04.2016.
 */
public class Mail {
    private String subject;
    private String content;

    public void setSubject(String subject) {
        // for utf-8 header (subject)
        this.subject = subject.replaceAll(" ", "_");
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }
}
