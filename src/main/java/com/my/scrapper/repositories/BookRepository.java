package com.my.scrapper.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.my.scrapper.entities.Book;
@Repository
public interface BookRepository extends JpaRepository<Book, Integer >{
	
	@Query("Select u.content from Book u where u.id =?1 ")
	List<String> getContentById(Integer id);
	@Query("Select u.content from Book u where u.bookTitle =?1 ")
	List<String> getContentByTitle(String title);
	@Query("Select u.bookTitle from Book u where u.bookTitle like %?1%")
	List<String> searchByTitle(String title);
	@Query("Select u.content from Book u")
	List<String> getAllContents();
}
