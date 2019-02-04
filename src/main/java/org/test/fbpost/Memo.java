package org.test.fbpost;

import java.util.Date;

public class Memo {
    String title;
    String content;
    Date createDate;

    public Memo(String title, String content, Date createDate) {
        this.title = title;
        this.content = content;
        this.createDate = createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
