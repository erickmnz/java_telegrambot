package com.my.scrapper.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name="tb_section")
public class Section implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="section_id")
	private Integer id;
	@Column(name="section_url")
	private String sectionBaseUrl;
	@Column(name="section_title")
	private String sectionTitle;
	@Column(name="section_all_urls")
	private Set<String> sectionUrls = new HashSet<String>();
	
	@OneToMany(mappedBy="section")
	private List<Book> bookList = new ArrayList<Book>();
	
	
	
	
	public Section() {

	}




	public Section( String sectionBaseUrl) {
		super();
		this.sectionBaseUrl = sectionBaseUrl;
	}




	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public String getSectionBaseUrl() {
		return sectionBaseUrl;
	}




	public void setSectionBaseUrl(String sectionBaseUrl) {
		this.sectionBaseUrl = sectionBaseUrl;
	}




	public String getSectionTitle() {
		return sectionTitle;
	}




	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}




	public Set<String> getSectionUrls() {
		return sectionUrls;
	}




	public void setSectionUrls(String url) {
		sectionUrls.add(url);
	}




	public List<Book> getBookList() {
		return bookList;
	}




	public void setBookList(Book book) {
		bookList.add(book);
	}
	
	
	
	


}
