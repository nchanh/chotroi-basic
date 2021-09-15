package edu.poly.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import edu.poly.spring.dtos.UserLoginDTO;
import edu.poly.spring.helpers.UserLogin;
import edu.poly.spring.models.ChatBox;
import edu.poly.spring.models.ChatMessage;
import edu.poly.spring.models.Shop;
import edu.poly.spring.models.User;
import edu.poly.spring.services.ChatBoxService;


@Controller
public class ChatController {
	PostingController pc = new PostingController();
	@Autowired
	private ChatBoxService chatBoxService;
	@MessageMapping("/chat.register")
	@SendTo("/topic/public")
	public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		return chatMessage;
	}

	@MessageMapping("/chat.send")
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage,ModelMap model,ChatBox chatbox) {
		if (!UserLogin.authenticated_shop() && !UserLogin.authenticated_user()) {
			model.addAttribute("userLoginDTO", new UserLoginDTO());
			model.addAttribute("message", "Vui lòng đăng nhập để truy cập!");
		}
		if (UserLogin.ROLE_USER.equals("user") && PostingController.type.equals("uservsshop")) {
			
			User user = UserLogin.USER;
			PostingController.iduser = user.getId();
			model.addAttribute("user", user);
			model.addAttribute("userLogin", user);
			model.addAttribute("shopLogin", null);
			Shop shop = new Shop();
			shop.setId(PostingController.idshop);
	chatbox.setShop(shop);
	user.setId(PostingController.iduser);
	chatbox.setUser(user);
	chatbox.setTime(PostingController.sender);
	chatbox.setMessage(chatMessage.getContent());

			chatBoxService.save(chatbox);
			model.addAttribute(chatbox);
		 System.out.println(chatbox.getMessage());
			System.out.println(chatbox.getTime());
			System.out.println(chatbox.getUser().getId());
			System.out.println(chatbox.getShop().getId());
		
		} 

		if (UserLogin.ROLE_USER.equals("shop") && PostingController.type.equals("shopvsuser")) {
			Shop shop = UserLogin.SHOP;
			PostingController.iduser = shop.getId();
			model.addAttribute("user", shop);
			model.addAttribute("userLogin", null);
			model.addAttribute("shopLogin", shop);
			if(chatbox.getId() != null && chatbox.getId() > 0) {
			}
			
			Shop shop2 = new Shop();
			shop2.setId(PostingController.iduser);
	chatbox.setShop(shop2);

	User user = new User();
	user.setId(PostingController.idshop);
	chatbox.setUser(user);
	chatbox.setTime(PostingController.sender);
	chatbox.setMessage(chatMessage.getContent());

			chatBoxService.save(chatbox);
			model.addAttribute(chatbox);
		 System.out.println(chatbox.getMessage());
			System.out.println(chatbox.getTime());
			System.out.println(chatbox.getUser().getId());
			System.out.println(chatbox.getShop().getId());
		}
		if (UserLogin.ROLE_USER.equals("shop") && PostingController.type.equals("shopvsshop")) {
			Shop shop = UserLogin.SHOP;
			PostingController.iduser = shop.getId();
			model.addAttribute("user", shop);
			model.addAttribute("userLogin", null);
			model.addAttribute("shopLogin", shop);
			if(chatbox.getId() != null && chatbox.getId() > 0) {
			}
			Shop shop2 = new Shop();
			shop2.setId(PostingController.iduser);
	chatbox.setShop(shop2);
	Shop shop3 = new Shop();
	shop3.setId(PostingController.idshop);
chatbox.setShop2(shop3);

	chatbox.setTime(PostingController.sender);
	chatbox.setMessage(chatMessage.getContent());

			chatBoxService.save(chatbox);
			model.addAttribute(chatbox);
		 System.out.println(chatbox.getMessage());
			System.out.println(chatbox.getTime());
			System.out.println(chatbox.getShop2().getId());
			System.out.println(chatbox.getShop().getId());
		}
if (UserLogin.ROLE_USER.equals("user") && PostingController.type.equals("uservsuser")) {
			
			User user = UserLogin.USER;
			PostingController.iduser = user.getId();
			model.addAttribute("user", user);
			model.addAttribute("userLogin", user);
			model.addAttribute("shopLogin", null);
	user.setId(PostingController.iduser);
	chatbox.setUser(user);
	User user2 = new User();
	user2.setId(PostingController.idshop);
	chatbox.setUser2(user2);
	chatbox.setTime(PostingController.sender);
	chatbox.setMessage(chatMessage.getContent());

			chatBoxService.save(chatbox);
			model.addAttribute(chatbox);
		 System.out.println(chatbox.getMessage());
			System.out.println(chatbox.getTime());
			System.out.println(chatbox.getUser().getId());
			System.out.println(chatbox.getUser2().getId());
		
		} 
		
		
		
		return chatMessage;
	}

}
