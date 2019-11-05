package ca.psdev.mssc.productservice.web.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ProductPage extends PageImpl<ProductDto> {

	private static final long serialVersionUID = 1L;

	public ProductPage(List<ProductDto> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public ProductPage(List<ProductDto> content) {
		super(content);
	}
}
