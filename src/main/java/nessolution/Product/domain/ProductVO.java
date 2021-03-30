package nessolution.Product.domain;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class ProductVO {
    private static final Logger logger = getLogger(ProductVO.class);

    public ProductVO() {
        logger.info("productVo working");
    }

    private String email;

    private String productname;

    private String description;

    private String file;

    public String getFile() {
        return this.file;
    }

    public void setFile(String multipartFile) {
        this.file = multipartFile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
