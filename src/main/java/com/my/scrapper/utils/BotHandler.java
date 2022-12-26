package com.my.scrapper.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import com.my.scrapper.entities.Book;
import com.my.scrapper.entities.PdfDoc;
import com.my.scrapper.repositories.BookRepository;
import com.my.scrapper.repositories.PdfDocRepository;

@Service
public class BotHandler extends TelegramLongPollingBot {

	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private PdfDocRepository pdfRepo;
	private boolean search;

	@Override
	public String getBotUsername() {
		return "";
	}

	@Override
	public String getBotToken() {
		return "";
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage()) {
			Message msg = update.getMessage();
			if(msg.hasDocument()) {
				sendFile(msg);
			}
			
			if(msg.isCommand()) {
				commands(update);
				return;
			}
			

		}
		
	}

	public void searchByTitle(Long id, Message msg) {
		
		if (msg.hasText()) {
			List<Book> bbb = bookRepo.searchByTitle(msg.getText().substring(msg.getText().indexOf(" ")+1).trim());
				if(bbb.isEmpty()) {
					sendMsg(id, "Not found!");
				}
				String m = String.format("Total of %d entries:\n", bbb.size());
				for(Book b :bbb) {
					m += "\nID: "+b.getId()+": "+b.getBookTitle();
				}
				m+="\n\nGet content by id:   /content_id id";
				sendMsg(id, m);
				System.out.println(m);
				System.out.println((msg.getText().substring(msg.getText().indexOf(" "))).toString().trim());
				System.out.println(msg.getText());
		}else {
			sendMsg(id, "You must add something to the query");
		}

		

	}

	public void sendMsg(Long id, String msg) {
		SendMessage sm = SendMessage.builder().chatId(id.toString()).text(msg).build();

		try {
			execute(sm);
		} catch (TelegramApiException e) {
			throw new RuntimeException(e);
		}
	}

	public void copyMessage(Long id, Integer msgId) {
		CopyMessage cm = CopyMessage.builder().fromChatId(id.toString()) 
				.chatId(id.toString())
				.messageId(msgId)
				.build();
		try {
			execute(cm);
		} catch (TelegramApiException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void setTime(Message msg) {
		Random rand = new Random();
		long min = 60000;
		long halfHr = 1800000;
		
		int set = Integer.parseInt(msg.getText().substring(msg.getText().lastIndexOf(" ")+1));
		
		if(set>0&&set<=10) {
			long randNum = rand.nextLong(min*set, halfHr*set);
			Timer time = new Timer();
			time.schedule(new TimerTask() {

				@Override
				public void run() {
					generateMsg(msg);
				}
				
			}
					, (long)set*1000, randNum);
			
		}
		
		
	}
	
	public void sendFile(Message msg)   {
		if(!msg.hasDocument()) {
			sendMsg(msg.getChatId(),"No file");
		}
		GetFile gf = new GetFile();
		if(msg.getDocument().getFileSize()<26214400L&&msg.getDocument().getMimeType().equals("application/pdf")) {
			gf.setFileId(msg.getDocument().getFileId());
			System.out.println(msg.getDocument().getFileId());
			try {
				String url = "https://api.telegram.org/file/bot"+getBotToken()+"/"+execute(gf).getFilePath();
				
				BufferedInputStream input = new BufferedInputStream(new URL(url).openStream());
				
				
				
				PdfDoc pdf = new PdfDoc(msg.getDocument().getFileName());
				pdf.setContent(input.readAllBytes());
				
				pdfRepo.save(pdf);
				
				sendMsg(msg.getChatId(), "Arquivo enviado");
			} catch (TelegramApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}else {
		sendMsg(msg.getChatId(), "File exceeds the permited size(25mb), or have a "
				+ "have a non permited myme type (Permited:pdf)");
		}
			
		
	}
	public void generateMsg(Message msg) {
		String pdff = "";
		
		for(byte[] arr: pdfRepo.getAllContents()) {
			pdff += new String(arr,StandardCharsets.UTF_8);
		}
		
		String sc = bookRepo.getAllContents().toString() +" "+ pdff; ;
		try {
			String mkv = Markov.generate(sc, 2,10 );
			sendMsg(msg.getChatId(),mkv);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void getPdfContent(Message msg) {
		String pdff = "";
		
		for(byte[] arr: pdfRepo.getAllContents()) {
			pdff += new String(arr,StandardCharsets.UTF_8);
		}
		sendMsg(msg.getChatId(),pdff);
	}
	public void commands(Update up) {
		Message a = up.getMessage();	
		String aa = a.getText().indexOf(" ")<0?a.getText():a.getText().substring(0, a.getText().indexOf(" "));
		switch(aa) {
			case "/search_title":{
				searchByTitle(a.getFrom().getId(), a );
				break;
			
			}case "/generate_msg":{
				generateMsg(up.getMessage());
				break;
			}case "/set_timer":{
				setTime(a);
			
			}
		}
		
	}
	@Override
	public void clearWebhook() throws TelegramApiRequestException {
		// TODO Auto-generated method stub

	}

}

