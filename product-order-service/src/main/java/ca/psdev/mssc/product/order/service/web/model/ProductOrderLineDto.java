package ca.psdev.mssc.product.order.service.web.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductOrderLineDto extends BaseItem {

    @Builder
    public ProductOrderLineDto(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate,
                            String upc, String productName, UUID productId, Integer orderQuantity) {
        super(id, version, createdDate, lastModifiedDate);
        this.upc = upc;
        this.productName = productName;
        this.productId = productId;
        this.orderQuantity = orderQuantity;
    }

    private String upc;
    private String productName;
    private UUID productId;
    private Integer orderQuantity = 0;
    private ProductDto productDto;
}
