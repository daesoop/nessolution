package nessolution.member.domain;

import nessolution.Product.domain.Product;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ROLE")
    private String role;

    @Column(name ="PHONE_NUMBER", unique = true, nullable = false)
    public String phoneNumber;

    @Column(name = "ENABLED")
    private Boolean enabled;

    @Column(name = "LASTPASSWORDRESETDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordResetDate;

    @OneToMany(mappedBy = "member")
    private List<Product> product;

    public Member() {
    }

    public Member(String email, String password, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.role = "ROLE_USER";
        enabled = true;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean checkBCryptPassword(String checkPassword) {
        return new BCryptPasswordEncoder().matches(checkPassword, this.password);
//        return checkPassword.equals(this.password);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String encode) {
        this.password = encode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public MemberDto _toUserDto() {
        return new MemberDto(this.email, this.password);
    }

    //회원가입 패스워드 맞는지 테스트용 체크 메소드
    public boolean checkBCryptPassword(String encodePassword, String checkPassword) {
        return new BCryptPasswordEncoder().matches(checkPassword, encodePassword);
    }

    public void changePassword(String changedPassword) {
        String encode = new BCryptPasswordEncoder().encode(changedPassword);
        this.password = encode;
    }

    public String findPassword() {
        StringBuilder temp = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 20; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    // a-z
                    temp.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    // A-Z
                    temp.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    // 0-9
                    temp.append((rnd.nextInt(10)));
                    break;
            }
        }
        this.password = new BCryptPasswordEncoder().encode(temp.toString());
        return temp.toString();
    }

}
