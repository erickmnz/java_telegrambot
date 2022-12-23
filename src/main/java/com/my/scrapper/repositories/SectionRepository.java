package com.my.scrapper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.my.scrapper.entities.Section;
@Repository
public interface SectionRepository extends JpaRepository<Section, Integer>{

}
