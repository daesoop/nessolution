//package nessolution.Product;
//
//import nessolution.Product.dao.ProductRepository;
//import nessolution.Product.domain.Product;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/page")
//public class ProductManagerController {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @GetMapping("/getAllProduct")
//    public ResponseEntity getAllProduct() {
//        List<Product> selectedList = ProductCRUD.selectAll(productRepository);
//
//        return new ResponseEntity(selectedList, HttpStatus.OK);
//    }
//
//    //getPagedProduct?page=1
//    @GetMapping("/getPagedProduct")
//    public ResponseEntity getPagedProduct(Pageable pageable) {
//        Page<Product> selectedList = ProductCRUD.getPagedProductList(productRepository, pageable);
//
//        return new ResponseEntity(selectedList, HttpStatus.OK);
//    }
//
//    @GetMapping("/product/{id}")
//    public String showDetails(@PathVariable long id, Model model) {
//        Product product = ProductCRUD.selectById(id, productRepository);
//        System.out.println("show details : " + product.getUserid() + "/////" + product.getId() + "////" + product.getDescription() +
//                "////" + product.getProductname());
//        String img ="/images/" + product.getProductimg();
//        model.addAttribute("img", img);
//        model.addAttribute("product", product);
//        return "/product/productShowDetail";
//    }
//}
