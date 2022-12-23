package com.my.scrapper.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.scrapper.entities.Book;
import com.my.scrapper.repositories.BookRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository repo;
	
	public Book findById(Integer id) {
		return repo.findById(id).get();
	}
	
	public List<String> getContentById(Integer id) {
		return repo.getContentById(id);
	}
	
	public List<String> getContentByTitle(String title){
		return repo.getContentByTitle(title);
	}

	public List<String> searchByTitle(String title) {
		return repo.searchByTitle(title);
	}
	
	public List<String> getAllContents(){
		return repo.getAllContents();
	}
}
