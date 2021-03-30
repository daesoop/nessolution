package nessolution.common.file.domin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TB_FILE")
@NoArgsConstructor
@AllArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userId;
    private String productName;
    private String productDesc;
    private String productImg;


    @Builder
    public void FileUpload(String userId, String productName, String description, String productImg) {
        this.userId = userId;
        this.productName = productName;
        this.productDesc = description;
        this.productImg = productImg;
    }


}