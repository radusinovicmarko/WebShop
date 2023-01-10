package org.unibl.etf.ip.webshop.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "category")
public class CategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    private List<AttributeEntity> attributes;
    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private CategoryEntity parentCategory;
    @OneToMany(mappedBy = "parentCategory")
    @ToString.Exclude
    private List<CategoryEntity> subcategories;
    @ManyToMany(mappedBy = "categories")
    @ToString.Exclude
    private List<ProductEntity> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CategoryEntity that = (CategoryEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
