package ca.psdev.mssc.product.order.service.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ProductOrderLine extends BaseEntity {

    @Builder
    public ProductOrderLine(UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate,
                         ProductOrder productOrder, UUID productId, Integer orderQuantity,
                         Integer quantityAllocated) {
        super(id, version, createdDate, lastModifiedDate);
        this.productOrder = productOrder;
        this.productId = productId;
        this.orderQuantity = orderQuantity;
        this.quantityAllocated = quantityAllocated;
    }

    @ManyToOne
    private ProductOrder productOrder;

    private UUID productId;
    private Integer orderQuantity = 0;
    private Integer quantityAllocated = 0;
}
