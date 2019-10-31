package ca.psdev.mssc.product.order.service.web.model;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
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
    @NotBlank
    private String name;
    private String productType;
    private String upc;
    private BigDecimal price;
    private int quantity;
}
