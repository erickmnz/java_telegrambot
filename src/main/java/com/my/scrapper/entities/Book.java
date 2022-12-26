package com.my.scrapper.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="tb_book")
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="book_id")
	private Integer id;
	@Column(name="book_title")
	private String bookTitle;
	@Column(name="book_index")
	private String bookIndexUrl;
	@Column(name="book_chapters_url", length=2000000)

	private Set<String> chapterUrls = new HashSet<String>();	
	@Column(name="book_content", length=2000000)


	private List<String> content = new ArrayList<String>();
	
	@ManyToOne

	private Section section;
	
	
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Book( String bookIndexUrl) {
		super();
		this.bookIndexUrl = bookIndexUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getBookIndexUrl() {
		return bookIndexUrl;
	}

	public void setBookIndexUrl(String bookIndexUrl) {
		this.bookIndexUrl = bookIndexUrl;
	}

	public Set<String> getChapterUrls() {
		return chapterUrls;
	}

	public void setChapterUrls(String chapUrl) {
		chapterUrls.add(chapUrl);
	}

	public List<String> getContent() {
		return content;
	}

	public void setContent(String cont) {
		content.add(cont);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bookTitle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(bookTitle, other.bookTitle);
	}


	

}
