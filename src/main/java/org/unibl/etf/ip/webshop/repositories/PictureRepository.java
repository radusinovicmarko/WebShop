package org.unibl.etf.ip.webshop.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.unibl.etf.ip.webshop.models.entities.PictureEntity;

public interface PictureRepository extends PagingAndSortingRepository<PictureEntity, Integer> {
}