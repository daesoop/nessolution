package nessolution.Notice;

import nessolution.member.domain.Member;
import org.hibernate.sql.Update;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "notice")
public class Notice {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 3, max = 100)
    @Column(name = "SUBJECT", nullable = false, length = 100)
    private String subject;

    @Size(min = 3, max = 1000)
    @Column(name = "COMMENT",  nullable = false, length = 1000)
    private String comment;

    @Column(name = "LIMIT_ROLE")
    private String limitRole;

    @Column(name = "DELETED")
    private boolean deleted;


    public Notice(String comment, String subject, String limitRole) {
        this(0L, subject, comment, limitRole);
    }

    public Notice(long id, @Size(min = 3, max = 100) String subject, @Size(min = 3, max = 1000) String comment, String limitRole) {
        this.id = id;
        this.subject = subject;
        this.comment = comment;
        this.limitRole = limitRole;
    }

    public void update(String comment) {
        System.out.println("update comment is : " + comment);
        this.comment = comment;
    }
}
