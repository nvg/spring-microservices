package ca.psdev.mssc.productservice.web.model;


import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {

    private UUID uuid;
    private long version;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;
    private String name;
    private String productType;
    private String upc;
    private BigDecimal price;
    private int quantity;
}