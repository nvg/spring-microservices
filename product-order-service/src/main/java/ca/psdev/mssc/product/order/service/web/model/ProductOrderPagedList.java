package ca.psdev.mssc.product.order.service.web.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ProductOrderPagedList extends PageImpl<ProductOrderDto> {
    public ProductOrderPagedList(List<ProductOrderDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public ProductOrderPagedList(List<ProductOrderDto> content) {
        super(content);
    }
}
