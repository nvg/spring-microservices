package ca.psdev.mssc.productservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    private UUID uuid;
    private int version;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;
    private String name;
    private ProductType productType;
    private String upc;
    private BigDecimal price;
    private int quantity;
}
