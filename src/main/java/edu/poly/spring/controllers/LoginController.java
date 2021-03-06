package edu.poly.spring.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.poly.spring.dtos.UserLoginDTO;
import edu.poly.spring.helpers.UserLogin;
import edu.poly.spring.models.Shop;
import edu.poly.spring.models.User;
import edu.poly.spring.services.ShopService;
import edu.poly.spring.services.UserService;

@Controller
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private ShopService shopService;

	@Autowired
	private UserService userService;

	@Autowired
	private JavaMailSender emailSender;

	@RequestMapping("/login")
	public String login(ModelMap model) {
		log.info("Request to Login page!");

		model.addAttribute("userLoginDTO", new UserLoginDTO());

		return "logins/login";
	}

	@RequestMapping("/logout")
	public String logout(ModelMap model) {

		UserLogin.logoff();

		return "homes/index";
	}

	@PostMapping("/signin")
	public String signin(ModelMap model, @Validated UserLoginDTO userLoginDTO, BindingResult result) {

		log.info("Sign in!");

		// Check fields
		if (result.hasErrors()) {
			model.addAttribute("message", "Vui l??ng ??i???n ?????y ????? th??ng tin!");
			model.addAttribute("userLoginDTO", new UserLoginDTO());

			return "logins/login";
		}

		String username = userLoginDTO.getUsername();
		String password = userLoginDTO.getPassword();

		// Check user login
		List<User> listUser = (List<User>) userService.findAll();
		for (User user : listUser) {
			if (username.equals(user.getUsername())) {
				if (password.equals(user.getPassword())) {
					User userLogin = userService.findByUsername(username);
					
					if (userLogin.getStatus().equals("block")) {
						model.addAttribute("message", "T??i kho???n c???a b???n ???? b??? kh??a!");
						model.addAttribute("userLoginDTO", new UserLoginDTO());

						return "logins/login";
					}

					// set UserLogin
					UserLogin.USER = userLogin;
					model.addAttribute("userLogin", userLogin);
					model.addAttribute("user", userLogin);
					UserLogin.ROLE_USER = "user";

					log.info("Login to system by " + UserLogin.ROLE_USER + "!");

					return "homes/index";
				}
			}
		}

		// Check shop login
		List<Shop> listShop = (List<Shop>) shopService.findAll();
		for (Shop shop : listShop) {
			if (username.equals(shop.getUsername())) {
				if (password.equals(shop.getPassword())) {
					Shop shopLogin = shopService.findByUsername(username);
					
					if (shopLogin.getStatus().equals("block")) {
						model.addAttribute("message", "T??i kho???n c???a b???n ???? b??? kh??a!");
						model.addAttribute("userLoginDTO", new UserLoginDTO());

						return "logins/login";
					}

					// set UserLogin
					UserLogin.SHOP = shopLogin;
					model.addAttribute("shopLogin", shopLogin);
					model.addAttribute("user", shopLogin);
					UserLogin.ROLE_USER = "shop";

					log.info("Login to system by " + UserLogin.ROLE_USER + "!");

					return "homes/index";
				}
			}
		}

		model.addAttribute("message", "T??i kho???n ho???c m???t kh???u kh??ng ????ng!");

		return "logins/login";

	}

	@RequestMapping("/forgot-password")
	public String forgotPassword(ModelMap model) {

		UserLogin.logoff();

		return "logins/forgotPassword";
	}

	@PostMapping("/get-password")
	public String getPassword(ModelMap model, @RequestParam(value = "username") String username,
			@RequestParam(value = "email") String email) {

		String errorMessage = "T??i kho???n kh??ng ????ng. Vui l??ng nh???p l???i!";

		// Check User
		List<User> listUser = (List<User>) userService.findAll();
		for (User user : listUser) {
			if (user.getUsername().equals(username)) {
				if (user.getEmail().equals(email)) {

					// Send mail
					int strId = user.getId();
					String strEmail = user.getEmail();
					String strPhone = user.getPhone();

					String text = "Xin ch??o " + username + ",\n \n"
							+ "B???n ???? y??u c???u ?????t l???i m???t kh???u t??i kho???n tr??n Ch??? Tr???i.\n"
							+ "D?????i ????y l?? th??ng tin t??i kho???n c???a b???n:\n\t" + "- T??n ????ng nh???p: " + username + "\n\t"
							+ "- Email: " + email + "\n\t" + "- S??? ??i???n tho???i: " + strPhone
							+ "\nNh???p v??o ???????ng link ????? k??ch ho???t t??i kho???n c???a b???n. N???u trang kh??ng hi???n th???, b???n c?? th??? sao ch??p v?? d??n li??n k???t v??o tr??nh duy???t c???a m??nh: http://localhost:8080/request-set-password?id="
							+ strId + "&username=" + username + "&email=" + strEmail + "\n"
							+ "Tr?????ng h???p b???n kh??ng y??u c???u ?????t l???i m???t kh???u b???n c?? th??? li??n h??? v???i ch??ng t??i qua email: chotroi.basic@gmail.com ????? ???????c h??? tr??? nhi???u h??n.\n \n"
							+ "Th??n ??i.";

					SimpleMailMessage message = new SimpleMailMessage();
					message.setTo(strEmail);
					message.setSubject("[Ch???Tr???i] Y??u c???u ?????t l???i m???t kh???u");
					message.setText(text);
					this.emailSender.send(message);

					model.addAttribute("messageComplete", "M???t kh???u ???? ???????c g???i v??? email th??nh c??ng!");

					return "logins/forgotPassword";
				}
				errorMessage = "Email kh??ng ????ng. Vui l??ng nh???p l???i!";
			}
		}

		// Check Shop
		List<Shop> listShop = (List<Shop>) shopService.findAll();
		for (Shop shop : listShop) {
			if (shop.getUsername().equals(username)) {
				if (shop.getEmail().equals(email)) {

					// Send mail
					int strId = shop.getId();
					String strEmail = shop.getEmail();
					String strPhone = shop.getPhone();

					String text = "Xin ch??o " + username + ",\n \n"
							+ "B???n ???? y??u c???u ?????t l???i m???t kh???u t??i kho???n tr??n Ch??? Tr???i.\n"
							+ "D?????i ????y l?? th??ng tin t??i kho???n c???a b???n:\n\t" + "- T??n ????ng nh???p: " + username + "\n\t"
							+ "- Email: " + email + "\n\t" + "- S??? ??i???n tho???i: " + strPhone
							+ "\nNh???p v??o ???????ng link ????? k??ch ho???t t??i kho???n c???a b???n. N???u trang kh??ng hi???n th???, b???n c?? th??? sao ch??p v?? d??n li??n k???t v??o tr??nh duy???t c???a m??nh: http://localhost:8080/request-set-password?id="
							+ strId + "&username=" + username + "&email=" + strEmail + "\n"
							+ "Tr?????ng h???p b???n kh??ng y??u c???u ?????t l???i m???t kh???u b???n c?? th??? li??n h??? v???i ch??ng t??i qua email: chotroi.basic@gmail.com ????? ???????c h??? tr??? nhi???u h??n.\n \n"
							+ "Th??n ??i.";
					SimpleMailMessage message = new SimpleMailMessage();
					message.setTo(strEmail);
					message.setSubject("[Ch???Tr???i] Y??u c???u ?????t l???i m???t kh???u");
					message.setText(text);
					this.emailSender.send(message);

					model.addAttribute("messageComplete", "M???t kh???u ???? ???????c g???i v??? email th??nh c??ng!");

					return "logins/forgotPassword";
				}
				errorMessage = "Email kh??ng ????ng. Vui l??ng nh???p l???i!";
			}
		}

		model.addAttribute("message", errorMessage);

		return "logins/forgotPassword";
	}

	@RequestMapping("/request-set-password")
	public String requestSetPassword(ModelMap model, @RequestParam("id") String id,
			@RequestParam("username") String username, @RequestParam(name = "email") String email) {
		log.info("--Request set Password");

		model.addAttribute("id", id);
		model.addAttribute("username", username);
		model.addAttribute("email", email);

		UserLogin.logoff();

		return "logins/setPassword";
	}

	@PostMapping("/set-password")
	public String setPassword(ModelMap model, RedirectAttributes redirectAttributes,
			@RequestParam(value = "id") String id, @RequestParam(value = "username") String username,
			@RequestParam(value = "email") String email, @RequestParam(value = "newPassword") String newPassword,
			@RequestParam(value = "comfirmPassword") String confirmPassword) {

//		M???t kh???u t???i thi???u 8 k?? t???, trong ???? c?? ??t nh???t 1 k?? t??? ch??? v?? 1 k?? t??? s???.
		if (!newPassword.equalsIgnoreCase(confirmPassword)) {

			redirectAttributes.addFlashAttribute("message", "X??c nh???n m???t kh???u ch??a ch??nh x??c!");

			return "redirect:/request-set-password?id=" + id + "&username=" + username + "&email=" + email;
		}

		model.addAttribute("messageComplete", "?????t m???t kh???u th??nh c??ng!");

		return "logins/setPassword";
	}

	@RequestMapping("/signup")
	public String signup(ModelMap model) {
		log.info("Sign up!");

		model.addAttribute("userRegister", new User());
		model.addAttribute("shopRegister", new Shop());
		model.addAttribute("user", null);
		model.addAttribute("userLogin", null);
		model.addAttribute("shopLogin", null);

		return "logins/register";

	}

	@PostMapping("/save-signup-user")
	public String saveSignupUser(ModelMap model, User userDTO, Shop shopDTO) {
		String username = userDTO.getUsername();
		String email = userDTO.getEmail();

		String error = checkRegister(username, email);
		if (!error.equals("")) {

			model.addAttribute("userRegister", new User());
			model.addAttribute("shopRegister", new Shop());
			model.addAttribute("user", null);
			model.addAttribute("userLogin", null);
			model.addAttribute("shopLogin", null);
			model.addAttribute("messageError", error);

			return "logins/register";
		}
		System.out.println("4");

		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setEmail(userDTO.getEmail());
		user.setPhone(userDTO.getPhone());
		user.setPicture("choTroi.png");
		user.setStatus("not-activated");

		userService.save(user);

		model.addAttribute("message", "????ng k?? th??nh c??ng!");

		// Send mail
		Integer id = user.getId();
		String strUsername = user.getUsername();
		String strEmail = user.getEmail();
		String phone = user.getPhone();
		String text = "Xin ch??o " + username
				+ ",\n \nCh??c m???ng b???n ???? ho??n th??nh th??ng tin ????ng k?? t??i kho???n.\nB???n ???? tr??? th??nh kh??ch h??ng c???a Ch??? Tr???i.\nD?????i ????y l?? th??ng tin t??i kho???n ???? ????ng k??:\n\t- T??n ????ng nh???p: "
				+ strUsername + "\n\t- Email: " + strEmail + "\n\t- S??? ??i???n tho???i: " + phone
				+ "\nNh???p v??o ???????ng link ????? k??ch ho???t t??i kho???n c???a b???n. N???u trang kh??ng hi???n th???, b???n c?? th??? sao ch??p v?? d??n li??n k???t v??o tr??nh duy???t c???a m??nh: http://localhost:8080/active-account?id="
				+ id + "&username=" + username + "&email=" + strEmail
				+ "\nLi??n h??? v???i ch??ng t??i qua email: chotroi.basic@gmail.com ????? ???????c h??? tr??? nhi???u h??n.\n \nTh??n ??i.";

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("????NG K?? T??I KHO???N TH??NH C??NG");
		message.setText(text);
		this.emailSender.send(message);

		model.addAttribute("userRegister", new User());
		model.addAttribute("shopRegister", new Shop());
		model.addAttribute("user", null);
		model.addAttribute("userLogin", null);
		model.addAttribute("shopLogin", null);

		return "logins/register";
	}

	@PostMapping("/save-signup-shop")
	public String saveSignupShop(ModelMap model, Shop shopDTO, User userDTO) {
		String username = shopDTO.getUsername();
		String email = shopDTO.getEmail();
		String error = checkRegister(username, email);
		if (!error.equals("")) {
			model.addAttribute("messageError", error);

			return "logins/register";
		}

		Shop shop = new Shop();
		shop.setUsername(shopDTO.getUsername());
		shop.setPassword(shopDTO.getPassword());
		shop.setEmail(shopDTO.getEmail());
		shop.setPhone(shopDTO.getPhone());
		shop.setPicture("choTroi.png");
		shop.setStatus("not-activated");
		shopService.save(shop);

		// Send mail
		Integer id = shop.getId();
		String strUsername = shop.getUsername();
		String strEmail = shop.getEmail();
		String phone = shop.getPhone();
		String text = "Xin ch??o " + username
				+ ",\n \nCh??c m???ng b???n ???? ho??n th??nh th??ng tin ????ng k?? t??i kho???n.\nB???n ???? tr??? th??nh ?????i l?? ?????i t??c c???a Ch??? Tr???i.\nD?????i ????y l?? th??ng tin t??i kho???n ???? ????ng k??:\n\t- T??n ????ng nh???p: "
				+ strUsername + "\n\t- Email: " + strEmail + "\n\t- S??? ??i???n tho???i: " + phone
				+ "\nNh???p v??o ???????ng link ????? k??ch ho???t t??i kho???n c???a b???n. N???u trang kh??ng hi???n th???, b???n c?? th??? sao ch??p v?? d??n li??n k???t v??o tr??nh duy???t c???a m??nh: http://localhost:8080/active-account?id="
				+ id + "&username=" + username + "&email=" + strEmail
				+ "\nLi??n h??? v???i ch??ng t??i qua email: chotroi.basic@gmail.com ????? ???????c h??? tr??? nhi???u h??n.\n \nTh??n ??i.";

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("????NG K?? T??I KHO???N TH??NH C??NG");
		message.setText(text);
		this.emailSender.send(message);

		model.addAttribute("message", "????ng k?? th??nh c??ng!");

		model.addAttribute("userRegister", new User());
		model.addAttribute("shopRegister", new Shop());
		model.addAttribute("user", null);
		model.addAttribute("userLogin", null);
		model.addAttribute("shopLogin", null);

		return "logins/register";
	}

	@RequestMapping("/request-active-account")
	public String requestActiveAccount(ModelMap model) {

		model.addAttribute("iconSuccess", null);

		if (UserLogin.ROLE_USER == null) {
			model.addAttribute("message", "B???n ph???i ????ng nh???p ????? s??? d???ng ch???c n??ng n??y!");
			model.addAttribute("userLoginDTO", new UserLoginDTO());
			return "logins/login";
		}

		if (UserLogin.ROLE_USER.equals("shop")) {
			Shop shop = UserLogin.SHOP;
			model.addAttribute("user", shop);
			model.addAttribute("userLogin", null);
			model.addAttribute("shopLogin", shop);

			return "logins/activeAccount";
		}

		if (UserLogin.ROLE_USER.equals("user")) {
			User user = UserLogin.USER;
			model.addAttribute("user", user);
			model.addAttribute("userLogin", user);
			model.addAttribute("shopLogin", null);

			return "logins/activeAccount";
		}

		model.addAttribute("message", "B???n ph???i ????ng nh???p ????? s??? d???ng ch???c n??ng n??y!");
		model.addAttribute("userLoginDTO", new UserLoginDTO());
		return "logins/login";

	}

	@RequestMapping("/active-account")
	public String activeEmail(ModelMap model, @RequestParam(name = "id") Integer id,
			@RequestParam(name = "username") String username, @RequestParam(name = "email") String email) {

		User user = userService.findByUsername(username);
		if(user != null) {
			user.setStatus("activated");
			userService.save(user);
			
			model.addAttribute("messageComplete", "T??i kho???n c???a b???n ???? k??ch ho???t th??nh c??ng!");
			model.addAttribute("userLoginDTO", new UserLoginDTO());
			return "logins/login";
		}
		
		Shop shop = shopService.findByUsername(username);
		if(shop != null) {
			shop.setStatus("activated");
			shopService.save(shop);
			
			model.addAttribute("messageComplete", "T??i kho???n c???a b???n ???? k??ch ho???t th??nh c??ng!");
			model.addAttribute("userLoginDTO", new UserLoginDTO());
			return "logins/login";
		}

		model.addAttribute("message", "???????ng link c???a b???n kh??ng ????ng ho???c ???? b??? h???t h???n!");
		model.addAttribute("userLoginDTO", new UserLoginDTO());
		return "logins/login";
	}

	@RequestMapping("/send-active")
	public String sendActive(ModelMap model, RedirectAttributes redirectAttributes) {

		Integer id = 0;
		String username = "";
		String email = "";

		if (UserLogin.ROLE_USER.equals("shop")) {
			Shop shop = UserLogin.SHOP;

			id = shop.getId();
			username = shop.getUsername();
			email = shop.getEmail();

			model.addAttribute("user", shop);
			model.addAttribute("userLogin", null);
			model.addAttribute("shopLogin", shop);
		}

		if (UserLogin.ROLE_USER.equals("user")) {
			User user = UserLogin.USER;

			id = user.getId();
			username = user.getUsername();
			email = user.getEmail();

			model.addAttribute("user", user);
			model.addAttribute("userLogin", user);
			model.addAttribute("shopLogin", null);
		}

		// Send mail
		String text = "Xin ch??o " + username
				+ ",\n \nC???m ??n b???n ???? ????ng k?? t??i kho???n Ch??? Tr???i.\n \nNh???n v??o ???????ng link ????? k??ch ho???t t??i kho???n c???a b???n. N???u trang kh??ng hi???n th???, b???n c?? th??? sao ch??p v?? d??n li??n k???t v??o tr??nh duy???t c???a m??nh: http://localhost:8080/active-account?id="
				+ id + "&username=" + username + "&email=" + email
				+ " \nVui l??ng kh??ng ti???c l??? email n??y v?? l?? do b???o m???t. \nC?? th??? li??n h??? v???i ch??ng t??i qua email: chotroi.basic@gmail.com ????? ???????c h??? tr??? nhi???u h??n.\n \nTh??n ??i.";

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("K??CH HO???T T??I KHO???N CH??? TR???I");
		message.setText(text);
		this.emailSender.send(message);

		model.addAttribute("iconSuccess", "show");

		return "logins/activeAccount";
	}

	private String checkRegister(String username, String email) {

		// Check User
		List<User> listUser = (List<User>) userService.findAll();
		for (User user : listUser) {
			if (email.equals(user.getEmail())) {
				return "Email ???? c???a ng?????i d??ng t???n t???i!";
			}
			if (username.equals(user.getUsername())) {
				return "T??i kho???n c???a ng?????i d??ng ???? t???n t???i!";
			}
		}

		// Check Shop
		List<Shop> listShop = (List<Shop>) shopService.findAll();
		for (Shop shop : listShop) {
			if (email.equals(shop.getEmail())) {
				return "Email c???a ?????i l?? ???? t???n t???i!";
			}
			if (username.equals(shop.getUsername())) {
				return "T??i kho???n c???a ?????i l?? ???? t???n t???i!";
			}
		}

		return "";
	}

}
