package com.my.scrapper.utils;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.my.scrapper.entities.Book;
import com.my.scrapper.entities.Section;


public class Scraps {

	public static Book getInfo(String index, String base) {
		Book nuDTO = new Book(index);

		Document doc = null;
		try {
			doc = Jsoup.connect(index).userAgent("Mozilla/5.0 (compatible; Yahoo! Slurp; http://help.yahoo.com/help/us/ysearch/slurp)").get();
			
			String title = "";
			Elements links = null;
			
			if(doc.select("h1").size()>0) {
				title=doc.getElementsByTag("h1").first().text();
			}else if (doc.select("h3").size()>0){
				title = doc.getElementsByTag("h3").first().text();
			}else if(doc.select("h4").size()>0) {
				title = doc.getElementsByTag("h4").first().text();
			}else if(doc.select("p").size()>0){
				title = doc.getElementsByTag("p").first().text();
			}else {
				title = doc.getElementsByTag("pre").first().text().substring(0,17);
			}
			
			if(doc.select("h3").size()>0) {
				links = doc.selectFirst("h3").nextElementSiblings();
			}else if(doc.select("h4").size()>0){
				links = doc.selectFirst("h4").nextElementSiblings();
			}else if(doc.select("h2").size()>0){
				links = doc.selectFirst("h2").nextElementSiblings();
			}else if(doc.select("h1").size()>0){
				links = doc.selectFirst("h1").nextElementSiblings();
			}else if(doc.select("hr").size()>0){
				links = doc.selectFirst("hr").nextElementSiblings();
			}else {
				links=null;
			}
			
			if(links != null) {
				System.out.println(title);
				nuDTO.setBookTitle(title);
	
				for (Element link : links) {
					String lHref = link.attr("href");
					if(lHref.contains("../")) {
						
						nuDTO.setChapterUrls(lHref.substring(3));
					}else {
						nuDTO.setChapterUrls(lHref);
					}
				}
				for (String j :nuDTO.getChapterUrls()) {
					System.out.println(base);
					System.out.println(j);
					System.out.println(base  + j);
					Document nDoc = null;
					if(base.endsWith("/")) {
						nDoc = Jsoup.connect(base + j).get();
					}else {
						nDoc = Jsoup.connect(base + "/" + j).get();
					}
					String texts = "";
					if(nDoc.select("h3").size() > 0) {
						texts = nDoc.selectFirst("h3").nextElementSiblings().text();
					}else if(nDoc.select("h1").size()>0){
						texts = nDoc.selectFirst("h1").nextElementSiblings().text();
						
					}else if(nDoc.select("p").size()>0){
						texts = nDoc.getElementsByTag("p").text();
					}else {
						texts = nDoc.getElementsByTag("pre").text();
					}
				nuDTO.setContent(texts);
				System.out.println(texts);
				}
				String texts = "";
				if(doc.select("h3").size() > 0) {
					texts = doc.selectFirst("h3").nextElementSiblings().text();
				}else if(doc.select("h1").size()>0){
					texts = doc.selectFirst("h1").nextElementSiblings().text();
					
				}else if(doc.select("p").size()>0){
					texts = doc.getElementsByTag("p").text();
				}else {
					texts = doc.getElementsByTag("pre").text();
				}
			nuDTO.setContent(texts);
			System.out.println(texts);

			}
			
			

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return nuDTO;
	}

	public static Section getSectionInfo(String sectionUrl, String baseUrl) {
		Section nuSec = new Section(sectionUrl);

		Document doc = null;
		try {
			doc = Jsoup.connect(sectionUrl).get();

			Element title = doc.getElementsByClass("splashtitle").first();
			nuSec.setSectionTitle(title.text());

			Elements spans = doc.getElementsByClass("c_t");
			for (Element span : spans) {
				String link = span.getElementsByTag("a").first().attr("href");

				if (link.startsWith("../gno/") ) {
					nuSec.setSectionUrls(baseUrl + link.substring(5));

				} else  if(link.contains("../")){
					nuSec.setSectionUrls(baseUrl +link.substring(2));

				}else {
					nuSec.setSectionUrls(baseUrl +"/gno/"+link);
				}

			}

		} catch (IOException e) {

			System.out.println(e.getMessage());
		}

		return nuSec;
	}

}
