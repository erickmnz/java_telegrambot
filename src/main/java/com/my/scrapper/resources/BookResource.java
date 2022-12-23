package com.my.scrapper.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.my.scrapper.entities.Book;
import com.my.scrapper.services.BookService;

@RestController
@RequestMapping(value="/books")
public class BookResource {

	@Autowired
	private BookService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<Book> findById(@PathVariable Integer id){
		Book b = service.findById(id);
		return ResponseEntity.ok().body(b);
	}
	
	@GetMapping("/{id}/content")
	public ResponseEntity<List<String>> getContentById(@PathVariable Integer id){
		return ResponseEntity.ok().body(service.getContentById(id));
	}
	@GetMapping("/title/content")
	public ResponseEntity<List<String>> getContentByTitle(@RequestParam(defaultValue="title") String title){
		title=title.replaceAll("\\+", " ");
		return ResponseEntity.ok().body(service.getContentByTitle(title));
	}

	@GetMapping("/search")
	public ResponseEntity<List<String>> searchByTitle(@RequestParam(defaultValue="title") String title){
		return ResponseEntity.ok().body(service.searchByTitle(title));
	}
	
	@GetMapping("/all")
	public ResponseEntity<String> getAllContents(){
		String c = "";
		for(String i: service.getAllContents()) {
			c+=i;
		}
		return ResponseEntity.ok().body(c);
	}
}
