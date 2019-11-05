package ca.psdev.mssc.productservice.web.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
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
