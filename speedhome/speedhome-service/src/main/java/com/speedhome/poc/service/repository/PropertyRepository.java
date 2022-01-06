package com.speedhome.poc.service.repository;

import com.speedhome.poc.service.entity.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, String> {
    boolean existsById(String id);
}
