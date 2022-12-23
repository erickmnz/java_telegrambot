package com.my.scrapper.utils;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import com.my.scrapper.repositories.BookRepository;
@Service
public class BotHandler extends TelegramLongPollingBot{

	@Autowired
	private BookRepository repo;
	
	
	

	

	@Override
	public String getBotUsername() {
		return "Gnostic_bot";
	}

	@Override
	public String getBotToken() {
		return "";
	}

	@Override
	public void onUpdateReceived(Update update) {
		if(update.hasMessage()&&update.getMessage().hasText()) {
			SendMessage msg = new SendMessage();
			msg.setChatId(update.getMessage().getChatId().toString());
			
			try {
				
				String mkv = Markov.generate(repo.getAllContents().toString(), 2, 200);
				msg.setText(mkv.substring(0, mkv.lastIndexOf(".")));
				execute(msg);

			
			} catch (TelegramApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}



	@Override
	public void clearWebhook() throws TelegramApiRequestException {
		// TODO Auto-generated method stub
		
	}
	

}
