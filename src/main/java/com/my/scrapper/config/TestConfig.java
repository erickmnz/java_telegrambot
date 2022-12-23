package com.my.scrapper.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.my.scrapper.repositories.BookRepository;
import com.my.scrapper.repositories.SectionRepository;
import com.my.scrapper.utils.BotHandler;

@Configuration
@Profile("test")
@ComponentScan
public class TestConfig implements CommandLineRunner{
	@Autowired
	private BookRepository bRepo;
	@Autowired
	private SectionRepository sRepo;
	@Autowired
	private BotHandler botH;
	
	
	private final static String BASE_URL="https://www.sacred-texts.com";
	@Override
	public void run(String... args) throws IOException {
		  try {
	            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
	            botsApi.registerBot(botH);
	        } catch (TelegramApiException e) {
	            e.printStackTrace();
	        }
		/**
		String b = bRepo.getAllContents().toString();
		String res =  Markov.generate(b, 2, 200);
		System.out.println(res);

		String sectionUrl = BASE_URL + "/gno/index.htm";
		
		Section s = Scraps.getSectionInfo(sectionUrl, BASE_URL);
			for(String i: s.getSectionUrls()) {
					Book book = Scraps.getInfo(i, i.substring(0,i.lastIndexOf("/"))); 
					System.out.println(book.getBookTitle());
					s.setBookList(book);
					bRepo.save(book);
				}
			
		
		
		sRepo.save(s);
	**/
	}
	
	
	
}
