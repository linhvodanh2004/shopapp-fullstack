package com.project.pixelstore.repositories;


import com.project.pixelstore.models.OptionValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionValueRepository extends JpaRepository<OptionValue, Long> {
}
