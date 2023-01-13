package org.unibl.etf.ip.webshop.repositories;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.webshop.models.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    public boolean existsByUsername(String username);
    public UserEntity findByUsername(String username);
}