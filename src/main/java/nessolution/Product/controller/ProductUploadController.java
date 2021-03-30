package nessolution.Product.controller;

import nessolution.Product.ProductCRUD;
import nessolution.Product.dao.ProductRepository;
import nessolution.Product.domain.Product;
import nessolution.common.file.service.FileService;
import nessolution.member.domain.MemberRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class ProductUploadController {

    private static final Logger logger = getLogger(ProductUploadController.class);


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    //    private static String UPLOADED_FOLDER = "/Users/daeseuckseung/Downloads/product_images/";
    private static String UPLOADED_FOLDER = "C:\\Users\\kimda\\Documents\\Coding\\AP_MarKet\\src\\main\\webapp\\images";
    private final FileService fileService;


    public ProductUploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/productUpload")
    public ResponseEntity singleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("email") String email,
                                           @RequestParam("productname") String productName, @RequestParam("description") String description) {


        logger.info("product name : " + productName);
        logger.info("product email : " + email);
        logger.info("product file : " + file.getOriginalFilename());
        logger.info("product description" + description);
//        logger.info("file name : " + productVO.getFile().getOriginalFilename());
//
        long id = 0;
        Product product = null;
        if (!file.isEmpty()) {
            String strOriginalFilename = file.getOriginalFilename();
            strOriginalFilename = strOriginalFilename.substring(strOriginalFilename.lastIndexOf("."), strOriginalFilename.length());
            String strFileName = email + "_" + System.currentTimeMillis() + strOriginalFilename;
            product = new Product(email, productName, description, strFileName);
            id = ProductCRUD.insert(product, productRepository);
            fileService.store(file, strFileName);

        } else {
            product = new Product(email, productName, description);
            id = ProductCRUD.insert(product, productRepository);
        }
        String msg = "";

        if (id <= 0) {
            msg = "Product Upload failed";
        } else {
            msg = "Product successfully uploaded! ID = " + id;
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/productUploadNoimg")
    public ResponseEntity productUploadNoimg(String email, String productName, String description) {
        Product product = new Product(email, productName, description);
        long id = ProductCRUD.insert(product, productRepository);

        String msg = "";

        if (id <= 0) {
            msg = "{\"result\":\"failed\"}";
        } else {
            msg = "{\"result\":\"success\"}";
        }

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ResponseEntity(jsonObject, HttpStatus.OK);
    }

    @PostMapping("/productUpdate/{id}")
    public ResponseEntity productUpdate(@PathVariable long id, String email, String productname, String description, @RequestParam("file") MultipartFile file) {

        String fileName = file.getOriginalFilename();
        logger.info("file name : " + fileName);
//        Product product = productRepository.findById(id);
        Product product = null;
        if (!file.isEmpty()) {
            String strOriginalFilename = file.getOriginalFilename();
            strOriginalFilename = strOriginalFilename.substring(strOriginalFilename.lastIndexOf("."), strOriginalFilename.length());
            String strFileName = email + "_" + System.currentTimeMillis() + strOriginalFilename;
            product = new Product(email, productname, description, strFileName);
            fileService.store(file, strFileName);

        } else {
            product = new Product(email, productname, description);
            id = ProductCRUD.insert(product, productRepository);
        }
        product.setId(id);

        Product selectProduct = ProductCRUD.update(product, id, productRepository, email);

        return new ResponseEntity(selectProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity deleteById(@PathVariable long id, String memberEmail) {
        logger.info("email test : " + memberEmail);
        ProductCRUD.deleteById(id, productRepository, memberEmail);
        String returnMsg = "상품이 삭제 되었습니다.";
        return new ResponseEntity(returnMsg, HttpStatus.OK);
    }

    @GetMapping("/product/deleteAll")
    public ResponseEntity deleteAll() {
        ProductCRUD.deleteAll(productRepository);

        String returnMsg = "All deleted";
        return new ResponseEntity(returnMsg, HttpStatus.OK);
    }
}