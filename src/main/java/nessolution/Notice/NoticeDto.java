package nessolution.Notice;

import javax.persistence.Column;
import javax.validation.constraints.Size;

public class NoticeDto {

    @Size(min = 3, max = 100)
    private String subject;

    @Size(min = 3, max = 1000)
    private String comment;

    private String limitRole;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLimitRole() {
        return limitRole;
    }

    public void setLimitRole(String limitRole) {
        this.limitRole = limitRole;
    }

    public Notice _toNotice() {
        return new Notice(this.comment, this.subject, this.limitRole);
    }
}
