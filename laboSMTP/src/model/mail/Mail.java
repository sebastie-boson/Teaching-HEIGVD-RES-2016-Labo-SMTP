package model.mail;

/**
 * Class that represents a mail with its content and subject.
 *
 * @author Mathieu Urstein and Sébastien Boson
 */
public class Mail {
    private String subject;
    private String content;

    /**
     * add a subject to the mail
     *
     * @param subject the subject to add
     */
    public void setSubject(String subject) {
        // for utf-8 header (subject)
        this.subject = subject.replaceAll(" ", "_");
    }

    /**
     * add the content to the mail
     *
     * @param content the content to add
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * get the subject of the mail
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * get the content of the mail
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }
}
