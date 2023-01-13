package org.unibl.etf.ip.webshop.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "product_attribute")
@IdClass(ProductAttributeKey.class)
public class ProductAttributeEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "attribute_id", referencedColumnName = "id")
    @ToString.Exclude
    private AttributeEntity attribute;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonIgnore
    private ProductEntity product;
    @Basic
    @Column(name = "value", nullable = false, length = 100)
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductAttributeEntity that = (ProductAttributeEntity) o;
        return attribute != null && Objects.equals(attribute, that.attribute);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
