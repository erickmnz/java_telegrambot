package com.my.scrapper.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.my.scrapper.entities.PdfDoc;

public interface PdfDocRepository extends JpaRepository<PdfDoc, Integer>{
	@Query("Select u.content from PdfDoc u")
	List<byte[]> getAllContents();
}
