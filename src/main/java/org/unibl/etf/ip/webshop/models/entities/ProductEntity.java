package org.unibl.etf.ip.webshop.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.unibl.etf.ip.webshop.models.enums.ProductStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "product")
public class ProductEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "title", nullable = false, length = 50)
    private String title;
    @Basic
    @Column(name = "description", nullable = false, length = 250)
    private String description;
    @Basic
    @Column(name = "price", nullable = false, precision = 2)
    private BigDecimal price;
    @Basic
    @Column(name = "new", nullable = false)
    private boolean newProduct;
    @Basic
    @Column(name = "location", nullable = false, length = 50)
    private String location;
    @Basic
    @Column(name = "status", nullable = false)
    private ProductStatus status;
    @Basic
    @Column(name = "publishDate", nullable = false)
    private Timestamp publishDate;
    @Basic
    @Column(name = "endDate", nullable = true)
    private Timestamp endDate;
    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List<CommentEntity> comments;
    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List<PictureEntity> pictures;
    @ManyToOne
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    private UserEntity buyer;
    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id", nullable = false)
    private UserEntity seller;
    @ManyToMany
    @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    @ToString.Exclude
    private  List<CategoryEntity> categories;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductEntity that = (ProductEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
