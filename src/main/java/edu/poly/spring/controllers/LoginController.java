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
			model.addAttribute("message", "Vui lòng điền đầy đủ thông tin!");
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
						model.addAttribute("message", "Tài khoản của bạn đã bị khóa!");
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
						model.addAttribute("message", "Tài khoản của bạn đã bị khóa!");
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

		model.addAttribute("message", "Tài khoản hoặc mật khẩu không đúng!");

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

		String errorMessage = "Tài khoản không đúng. Vui lòng nhập lại!";

		// Check User
		List<User> listUser = (List<User>) userService.findAll();
		for (User user : listUser) {
			if (user.getUsername().equals(username)) {
				if (user.getEmail().equals(email)) {

					// Send mail
					int strId = user.getId();
					String strEmail = user.getEmail();
					String strPhone = user.getPhone();

					String text = "Xin chào " + username + ",\n \n"
							+ "Bạn đã yêu cầu đặt lại mật khẩu tài khoản trên Chợ Trời.\n"
							+ "Dưới đây là thông tin tài khoản của bạn:\n\t" + "- Tên đăng nhập: " + username + "\n\t"
							+ "- Email: " + email + "\n\t" + "- Số điện thoại: " + strPhone
							+ "\nNhập vào đường link để kích hoạt tài khoản của bạn. Nếu trang không hiển thị, bạn có thể sao chép và dán liên kết vào trình duyệt của mình: http://localhost:8080/request-set-password?id="
							+ strId + "&username=" + username + "&email=" + strEmail + "\n"
							+ "Trường hợp bạn không yêu cầu đặt lại mật khẩu bạn có thể liên hệ với chúng tôi qua email: chotroi.basic@gmail.com để được hỗ trợ nhiều hơn.\n \n"
							+ "Thân ái.";

					SimpleMailMessage message = new SimpleMailMessage();
					message.setTo(strEmail);
					message.setSubject("[ChợTrời] Yêu cầu đặt lại mật khẩu");
					message.setText(text);
					this.emailSender.send(message);

					model.addAttribute("messageComplete", "Mật khẩu đã được gửi về email thành công!");

					return "logins/forgotPassword";
				}
				errorMessage = "Email không đúng. Vui lòng nhập lại!";
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

					String text = "Xin chào " + username + ",\n \n"
							+ "Bạn đã yêu cầu đặt lại mật khẩu tài khoản trên Chợ Trời.\n"
							+ "Dưới đây là thông tin tài khoản của bạn:\n\t" + "- Tên đăng nhập: " + username + "\n\t"
							+ "- Email: " + email + "\n\t" + "- Số điện thoại: " + strPhone
							+ "\nNhập vào đường link để kích hoạt tài khoản của bạn. Nếu trang không hiển thị, bạn có thể sao chép và dán liên kết vào trình duyệt của mình: http://localhost:8080/request-set-password?id="
							+ strId + "&username=" + username + "&email=" + strEmail + "\n"
							+ "Trường hợp bạn không yêu cầu đặt lại mật khẩu bạn có thể liên hệ với chúng tôi qua email: chotroi.basic@gmail.com để được hỗ trợ nhiều hơn.\n \n"
							+ "Thân ái.";
					SimpleMailMessage message = new SimpleMailMessage();
					message.setTo(strEmail);
					message.setSubject("[ChợTrời] Yêu cầu đặt lại mật khẩu");
					message.setText(text);
					this.emailSender.send(message);

					model.addAttribute("messageComplete", "Mật khẩu đã được gửi về email thành công!");

					return "logins/forgotPassword";
				}
				errorMessage = "Email không đúng. Vui lòng nhập lại!";
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

//		Mật khẩu tối thiểu 8 ký tự, trong đó có ít nhất 1 ký tự chữ và 1 ký tự số.
		if (!newPassword.equalsIgnoreCase(confirmPassword)) {

			redirectAttributes.addFlashAttribute("message", "Xác nhận mật khẩu chưa chính xác!");

			return "redirect:/request-set-password?id=" + id + "&username=" + username + "&email=" + email;
		}

		model.addAttribute("messageComplete", "Đặt mật khẩu thành công!");

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

		model.addAttribute("message", "Đăng ký thành công!");

		// Send mail
		Integer id = user.getId();
		String strUsername = user.getUsername();
		String strEmail = user.getEmail();
		String phone = user.getPhone();
		String text = "Xin chào " + username
				+ ",\n \nChúc mừng bạn đã hoàn thành thông tin đăng ký tài khoản.\nBạn đã trở thành khách hàng của Chợ Trời.\nDưới đây là thông tin tài khoản đã đăng ký:\n\t- Tên đăng nhập: "
				+ strUsername + "\n\t- Email: " + strEmail + "\n\t- Số điện thoại: " + phone
				+ "\nNhập vào đường link để kích hoạt tài khoản của bạn. Nếu trang không hiển thị, bạn có thể sao chép và dán liên kết vào trình duyệt của mình: http://localhost:8080/active-account?id="
				+ id + "&username=" + username + "&email=" + strEmail
				+ "\nLiên hệ với chúng tôi qua email: chotroi.basic@gmail.com để được hỗ trợ nhiều hơn.\n \nThân ái.";

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("ĐĂNG KÝ TÀI KHOẢN THÀNH CÔNG");
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
		String text = "Xin chào " + username
				+ ",\n \nChúc mừng bạn đã hoàn thành thông tin đăng ký tài khoản.\nBạn đã trở thành đại lý đối tác của Chợ Trời.\nDưới đây là thông tin tài khoản đã đăng ký:\n\t- Tên đăng nhập: "
				+ strUsername + "\n\t- Email: " + strEmail + "\n\t- Số điện thoại: " + phone
				+ "\nNhập vào đường link để kích hoạt tài khoản của bạn. Nếu trang không hiển thị, bạn có thể sao chép và dán liên kết vào trình duyệt của mình: http://localhost:8080/active-account?id="
				+ id + "&username=" + username + "&email=" + strEmail
				+ "\nLiên hệ với chúng tôi qua email: chotroi.basic@gmail.com để được hỗ trợ nhiều hơn.\n \nThân ái.";

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("ĐĂNG KÝ TÀI KHOẢN THÀNH CÔNG");
		message.setText(text);
		this.emailSender.send(message);

		model.addAttribute("message", "Đăng ký thành công!");

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
			model.addAttribute("message", "Bạn phải đăng nhập để sử dụng chức năng này!");
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

		model.addAttribute("message", "Bạn phải đăng nhập để sử dụng chức năng này!");
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
			
			model.addAttribute("messageComplete", "Tài khoản của bạn đã kích hoạt thành công!");
			model.addAttribute("userLoginDTO", new UserLoginDTO());
			return "logins/login";
		}
		
		Shop shop = shopService.findByUsername(username);
		if(shop != null) {
			shop.setStatus("activated");
			shopService.save(shop);
			
			model.addAttribute("messageComplete", "Tài khoản của bạn đã kích hoạt thành công!");
			model.addAttribute("userLoginDTO", new UserLoginDTO());
			return "logins/login";
		}

		model.addAttribute("message", "Đường link của bạn không đúng hoặc đã bị hết hạn!");
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
		String text = "Xin chào " + username
				+ ",\n \nCảm ơn bạn đã đăng ký tài khoản Chợ Trời.\n \nNhấn vào đường link để kích hoạt tài khoản của bạn. Nếu trang không hiển thị, bạn có thể sao chép và dán liên kết vào trình duyệt của mình: http://localhost:8080/active-account?id="
				+ id + "&username=" + username + "&email=" + email
				+ " \nVui lòng không tiếc lộ email này vì lý do bảo mật. \nCó thể liên hệ với chúng tôi qua email: chotroi.basic@gmail.com để được hỗ trợ nhiều hơn.\n \nThân ái.";

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("KÍCH HOẠT TÀI KHOẢN CHỢ TRỜI");
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
				return "Email đã của người dùng tồn tại!";
			}
			if (username.equals(user.getUsername())) {
				return "Tài khoản của người dùng đã tồn tại!";
			}
		}

		// Check Shop
		List<Shop> listShop = (List<Shop>) shopService.findAll();
		for (Shop shop : listShop) {
			if (email.equals(shop.getEmail())) {
				return "Email của đại lý đã tồn tại!";
			}
			if (username.equals(shop.getUsername())) {
				return "Tài khoản của đại lý đã tồn tại!";
			}
		}

		return "";
	}

}
