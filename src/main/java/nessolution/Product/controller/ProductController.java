package nessolution.Product.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import nessolution.Product.ProductCRUD;
import nessolution.Product.dao.ProductRepository;
import nessolution.Product.domain.Product;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/productUpload")
    public String productUpload() {
        System.out.println("product 생성");
        return "/product/productCreateForm";
    }

    @GetMapping("/update/{id}")
    public String productUpdate(@PathVariable long id, Model model) {
        System.out.println("product update");
        Product product = ProductCRUD.selectById(id, productRepository);
        System.out.println("show details : " + product.getUserid() + "/////" + product.getId() + "////" + product.getDescription() +
                "////" + product.getProductname());
        model.addAttribute("product", product);
        return "/product/productUpdateForm";
    }


}
