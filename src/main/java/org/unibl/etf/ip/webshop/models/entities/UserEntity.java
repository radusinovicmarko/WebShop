package org.unibl.etf.ip.webshop.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "firstName", nullable = false, length = 30)
    private String firstName;
    @Basic
    @Column(name = "lastName", nullable = false, length = 30)
    private String lastName;
    @Basic
    @Column(name = "username", nullable = false, length = 50)
    private String username;
    @Basic
    @Column(name = "password", nullable = false, length = 50)
    private String password;
    @Basic
    @Column(name = "avatarURL", nullable = true, length = 50)
    private String avatarUrl;
    @Basic
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Basic
    @Column(name = "activated", nullable = false)
    private boolean activated;
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private Collection<CommentEntity> comments;
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private Collection<MessageEntity> messages;
    @OneToMany(mappedBy = "buyer")
    @ToString.Exclude
    private List<ProductEntity> boughtProducts;
    @OneToMany(mappedBy = "seller")
    @ToString.Exclude
    private Collection<ProductEntity> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
