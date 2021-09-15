package edu.poly.spring.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.spring.helpers.AdminLogin;
import edu.poly.spring.helpers.UserLogin;
import edu.poly.spring.models.Admin;
import edu.poly.spring.models.Shop;
import edu.poly.spring.models.User;
import edu.poly.spring.services.AdminService;
import edu.poly.spring.services.ShopService;
import edu.poly.spring.services.UserService;

@Controller
public class HomeController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ShopService shopService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminService adminService;

	@RequestMapping("/")
	public String home(ModelMap model) {
		
		log.info("Request to Homepage");

		// Don't login to system
		if (!UserLogin.authenticated_user() && !UserLogin.authenticated_shop()) {
			model.addAttribute("user", null);
			model.addAttribute("userLogin", null);
			model.addAttribute("shopLogin", null);

			return "homes/index";
		}

		// Login to system
		if (UserLogin.ROLE_USER.equals("shop")) {
			Shop shop = shopService.findByUsername(UserLogin.SHOP.getUsername());
			UserLogin.SHOP = shop;
			model.addAttribute("user", UserLogin.SHOP);
			model.addAttribute("userLogin", null);
			model.addAttribute("shopLogin", UserLogin.SHOP);

			return "homes/index";
		}

		if (UserLogin.ROLE_USER.equals("user")) {
			User user = userService.findByUsername(UserLogin.USER.getUsername());
			UserLogin.USER = user;
			model.addAttribute("user", UserLogin.USER);
			model.addAttribute("userLogin", UserLogin.USER);
			model.addAttribute("shopLogin", null);

			return "homes/index";
		}

		return "homes/index";
	}

	@RequestMapping("/help")
	public String help(ModelMap model) {
		return "homes/help";
	}

	@RequestMapping("/admin")
	public String admin(ModelMap model) {

		if (!AdminLogin.authenticated_admin()) {
			model.addAttribute("admin", new Admin());
			return "homes/adminLogin.html";
		}
		
		model.addAttribute("admin", AdminLogin.ADMIN);

		return "admin";
	}

	@RequestMapping("/admin-login")
	public String login(ModelMap model, @Validated Admin admin, BindingResult result) {

		// Check fields
		if (result.hasErrors()) {
			model.addAttribute("message", "Vui lòng điền đầy đủ thông tin!");
			model.addAttribute("admin", new Admin());

			return "homes/adminLogin.html";
		}

		String username = admin.getUsername();
		String password = admin.getPassword();

		// Check user login
		List<Admin> listAdmins = (List<Admin>) adminService.findAll();
		for (Admin admins : listAdmins) {
			if (username.equals(admins.getUsername())) {
				if (password.equals(admins.getPassword())) {
					Admin adminn = adminService.findByUsername(username);

					// set UserLogin
					AdminLogin.ADMIN = adminn;
					model.addAttribute("admin", adminn);

					return "redirect:/admin";
				}
			}
		}
		
		model.addAttribute("message", "Tài khoản hoặc mật khẩu không đúng!");

		return "homes/adminLogin.html";
	}

	@RequestMapping("/logout-admin")
	public String logout(ModelMap model) {

		AdminLogin.logoff();
		
		model.addAttribute("admin", new Admin());

		return "admin";
	}
}
