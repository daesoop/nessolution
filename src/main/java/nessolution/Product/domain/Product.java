package nessolution.Product.domain;

import nessolution.member.domain.Member;

import javax.persistence.*;

@Entity
@Table(name = "t_product")
public class Product {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String productName;

    private String description;

    private String productTime;

    @ManyToOne
    @JoinColumn(name = "member", foreignKey = @ForeignKey(name = "FK_member"))
    private Member member;

    public Product() {
    }

    public Product(Member member, String productName) {
        this.member = member;
        this.productName = productName;
    }

    public Product(Member member, String productName, String description) {
        this.member = member;
        this.productName = productName;
        this.description = description;
    }

    public Product(Member member, String productName, String description, String productTime) {
        this.member = member;
        this.productName = productName;
        this.description = description;
        this.productTime = productTime;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProductTime(String productTime) {
        this.productTime = productTime;
    }

    public String getProductTime() {
        return productTime;
    }
}