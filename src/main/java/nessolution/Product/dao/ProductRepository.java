package nessolution.Product.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import nessolution.Product.domain.Product;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByUserid(String userId);
    Product findById(long id);
    List<Product> findAll();
//    Page<Product> findAll(Pageable pageable);
}