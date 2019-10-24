package ca.psdev.mssc.productservice.web.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ProductPage extends PageImpl<Product> {

    public ProductPage(List<Product> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public ProductPage(List<Product> content) {
        super(content);
    }
}
