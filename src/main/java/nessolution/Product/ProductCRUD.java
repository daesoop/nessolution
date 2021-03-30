package nessolution.Product;

import nessolution.Product.dao.ProductRepository;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import nessolution.Product.domain.Product;
import nessolution.exception.CannotDeleteException;
import nessolution.exception.CannotUpdateException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class ProductCRUD {

    private static final Logger logger = getLogger(ProductCRUD.class);

    static public long insert(Product product, ProductRepository productRepository) {
        logger.info("product success : " + product.getProductname() + "///" + product.getDescription());
        product = productRepository.save(product);
        logger.info("product success2 : " + product.getProductname() + "///" + product.getDescription());
        long id = product.getId();
        return id;
    }

    static public Product selectById(long id, ProductRepository productRepository) {
        Product product = productRepository.findById(id);
        return product;
    }

    static public List<Product> selectAll(ProductRepository productRepository) {
        List<Product> selectedList = productRepository.findAll();
        return selectedList;
    }

    static public Product update(Product product, long id, ProductRepository productRepository, String email) {
        Product selectProduct = productRepository.findById(id);

        if (product.getUserid() != email) {
            throw new CannotUpdateException("비밀번호가 일치 하지 않습니다.");
        }

        productRepository.save(product);
        return selectProduct;

    }

    static public void deleteById(long id, ProductRepository productRepository, String email) {
        Product product = productRepository.findById(id);
        logger.info("product userId : " + product.getUserid() + "////" + email);
        if (!product.getUserid().equals(email)) {
            throw new CannotDeleteException("본인 상품이 아닐시 상품 정보를 삭제 할 수 없습니다.");
        }
        productRepository.deleteById(id);
        logger.info("삭제 성공 예스");
    }
    static public Page<Product> getPagedProductList(ProductRepository productRepository, Pageable pageable) {
        Page<Product> selectedList = productRepository.findAll(pageable);
        return selectedList;
    }


    static public boolean deleteAll(ProductRepository productRepository) {
        productRepository.deleteAll();

        List<Product> selectedList = selectAll(productRepository);

        if (selectedList == null || selectedList.size() == 0)
            return true;
        else
            return false;
    }
}