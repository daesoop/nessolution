package nessolution.member.domain;

public class MemberDto {

    private String username;

    private String password;

    private String checkPassword;

    private String phoneNumber;

    public MemberDto() {
    }

    public MemberDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public MemberDto(long id, String email, String password) {}

    public MemberDto(String username, String password, String checkPassword) {
        this.username = username;
        this.password = password;
        this.checkPassword = checkPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Member _toUser() {
        return new Member(this.username, this.password, this.phoneNumber);
    }
}
