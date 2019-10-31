package ca.psdev.mssc.product.inventory.service.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ProductInventory extends BaseEntity{

    @Builder
    public ProductInventory(UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate, UUID productId,
                         String upc, Integer quantityOnHand) {
        super(id, version, createdDate, lastModifiedDate);
        this.productId = productId;
        this.upc = upc;
        this.quantityOnHand = quantityOnHand;
    }

    private UUID productId;
    private String upc;
    private Integer quantityOnHand = 0;
}
