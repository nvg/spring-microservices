package ca.psdev.mssc.productservice.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
	private UUID uuid;
	@Version
	private long version;
	@CreationTimestamp
	@Column(updatable = false)
	private OffsetDateTime createDate;
	@UpdateTimestamp
	private OffsetDateTime updateDate;
	@NotBlank
	private String name;
	private ProductType productType;
	private String upc;
	private BigDecimal price;
	private int quantity;
	private int minQuantity;
}
