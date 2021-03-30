package nessolution.Product.domain;

import javax.persistence.*;

@Entity
@Table(name = "t_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String productname;

    private String description;

    private String productimg;

    private String userid;

    public Product() {
    }

    public Product(String email, String productname) {
        this.userid = email;
        this.productname = productname;
    }

    public Product(String email, String productname, String description) {
        this.userid = email;
        this.productname = productname;
        this.description = description;
    }

    public Product(String email, String productname, String description, String productimg) {
        this.userid = email;
        this.productname = productname;
        this.description = description;
        this.productimg = productimg;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productName) {
        this.productname = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProductimg(String productimg) {
        this.productimg = productimg;
    }

    public String getProductimg() {
        return productimg;
    }
}