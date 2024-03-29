package org.unibl.etf.ip.webshop.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Integer id;
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
    @Column(name = "password", nullable = false, length = 100)
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
    @Basic
    @Column(name = "contactPhone", nullable = false, length = 20)
    private String contactPhone;
    @Basic
    @Column(name = "location", nullable = false, length = 50)
    private String location;
    @Basic
    @Column(name = "deleted", nullable = false)
    private boolean deleted;
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    private Collection<CommentEntity> comments;
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    private Collection<MessageEntity> messages;
    @OneToMany(mappedBy = "buyer")
    @ToString.Exclude
    @JsonIgnore
    private List<ProductEntity> boughtProducts;
    @OneToMany(mappedBy = "seller")
    @ToString.Exclude
    @JsonIgnore
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
