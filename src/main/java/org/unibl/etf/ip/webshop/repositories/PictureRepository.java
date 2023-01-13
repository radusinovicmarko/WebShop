package org.unibl.etf.ip.webshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.webshop.models.entities.PictureEntity;

public interface PictureRepository extends JpaRepository<PictureEntity, Integer> {
}