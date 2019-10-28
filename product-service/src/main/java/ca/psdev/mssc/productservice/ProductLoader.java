package ca.psdev.mssc.productservice;

import ca.psdev.mssc.productservice.domain.Product;
import ca.psdev.mssc.productservice.repo.ProductRepo;
import lombok.Data;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static ca.psdev.mssc.productservice.domain.ProductType.TYPE1;

@Component
@Data
public class ProductLoader implements CommandLineRunner {

    @Autowired
    private ProductRepo repo;

    @Override
    public void run(String... args) throws Exception {
        if (repo.count() != 0)
            return;

        for (int i = 1; i < 10; i++) {
            val p = Product.builder()
                    .name(String.format("Product %02d", i))
                    .productType(TYPE1)
                    .upc("" + Math.random() * 10000)
                    .price(BigDecimal.valueOf(Math.random() * 10000))
                    .quantity((int) (Math.random() * 1000))
                    .build();
            repo.save(p);
        }
    }
}
