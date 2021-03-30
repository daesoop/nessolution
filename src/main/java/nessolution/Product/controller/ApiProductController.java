package nessolution.Product.controller;

import nessolution.Product.dao.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nessolution.Product.ProductCRUD;
import nessolution.Product.domain.Product;
import java.util.List;

@RestController
@RequestMapping("/page")
public class ApiProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/select")
    public ResponseEntity selectAll() {
        List<Product> productList = ProductCRUD.selectAll(productRepository);

        String returnMsg = "";

        for (Product product : productList) {
            returnMsg += String.format("UserID : %s   productName : %s   Desc : %s Img : %s</br>", product.getUserid(),
                    product.getProductname(), product.getDescription(), product.getProductimg());
        }

        return new ResponseEntity(productList, HttpStatus.OK);
    }

    @GetMapping("/selectById/{id}")
    public ResponseEntity selectById(@PathVariable long id) {
        Product product = ProductCRUD.selectById(id, productRepository);

//        String returnMsg = "";

//        for(Product product : productList) {
//        returnMsg += String.format("UserID : %s   productName : %s   Desc : %s Img : %s</br>", product.getUserid(),
//                product.getProductname(), product.getDescription(), product.getProductimg());
//        }
        return new ResponseEntity(product, HttpStatus.OK);
    }

}
