package edu.poly.spring.controllers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.poly.spring.dtos.EdituserDto;
import edu.poly.spring.dtos.UserLoginDTO;
import edu.poly.spring.dtos.changePasswordDto;
import edu.poly.spring.helpers.UserLogin;
import edu.poly.spring.models.Shop;
import edu.poly.spring.models.User;
import edu.poly.spring.services.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	public String strImage = "";

	@Autowired
	private UserService userService;

	@RequestMapping("/profile/{id}")
	public String detailShop(Model model, @PathVariable(name = "id") Integer id) {

		// Check login
		if (!UserLogin.authenticated_shop() && !UserLogin.authenticated_user()) {
			model.addAttribute("userLoginDTO", new UserLoginDTO());
			model.addAttribute("message", "Vui l??ng ????ng nh???p ????? truy c???p!");
			return "logins/login";
		}

		// Set user login
		User user = UserLogin.USER;
		model.addAttribute("user", user);
		model.addAttribute("userLogin", user);
		model.addAttribute("shopLogin", null);

		Optional<User> optUser = userService.findById(id);
		if (optUser.isPresent()) {
			strImage = optUser.get().getPicture();
			EdituserDto dto = new EdituserDto();
			BeanUtils.copyProperties(optUser.get(), dto);
			model.addAttribute("edituserDto", dto);
			return "users/profileUser";
		}

		return "users/profileUser";
	}

	@PostMapping("/update")
	public String update(Model model, @Validated EdituserDto edituserDto, BindingResult result) {

		// Check login
		if (!UserLogin.authenticated_shop() && !UserLogin.authenticated_user()) {
			model.addAttribute("userLoginDTO", new UserLoginDTO());
			model.addAttribute("message", "Vui l??ng ????ng nh???p ????? truy c???p!");
			return "logins/login";
		}

		// check error
		if (result.hasErrors()) {
			model.addAttribute("message", "Please input or required fields!!");
			model.addAttribute("edituserDto", edituserDto);
			return "users/profileUser";
		}

		User user = new User();

		model.addAttribute("message", "C???p nh???t t??i kho???n th??nh c??ng!");

		Path path = Paths.get("images/");
		try (InputStream inputStream = edituserDto.getImage().getInputStream()) {
			Files.copy(inputStream, path.resolve(edituserDto.getImage().getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			System.out.println("Image is null");
		}

		user.setId(UserLogin.USER.getId());
		user.setUsername(edituserDto.getUsername());
		user.setPassword(UserLogin.USER.getPassword());
		if (edituserDto.getImage().getOriginalFilename().equals("")) {
			user.setPicture(strImage);
		} else {
			user.setPicture(edituserDto.getImage().getOriginalFilename());
		}

		user.setEmail(edituserDto.getEmail());
		user.setPhone(edituserDto.getPhone());
		user.setAddress(edituserDto.getAddress());
		user.setGender(edituserDto.isGender());
		user.setFullname(edituserDto.getFullname());
		user.setBirthday(edituserDto.getBirthday());
		user.setStatus(UserLogin.USER.getStatus());

		userService.save(user);

		Optional<User> optUser = userService.findById(UserLogin.USER.getId());
		strImage = optUser.get().getPicture();
		EdituserDto dto = new EdituserDto();
		BeanUtils.copyProperties(optUser.get(), dto);
		model.addAttribute("user", user);
		model.addAttribute("edituserDto", dto);
		model.addAttribute("userLogin", user);
		model.addAttribute("shopLogin", null);

		return "users/profileUser";
	}

	@RequestMapping("/change-password/{id}")
	public String changePassword(Model model, @PathVariable(name = "id") Integer id) {

//		// Check login
//		if (!UserLogin.authenticated_shop() && !UserLogin.authenticated_user()) {
//			model.addAttribute("userLoginDTO", new UserLoginDTO());
//			model.addAttribute("message", "Vui l??ng ????ng nh???p ????? truy c???p!");
//			return "logins/login";
//		}

		// Set user login
		User user = UserLogin.USER;
		model.addAttribute("user", user);
		model.addAttribute("userLogin", user);
		model.addAttribute("shopLogin", null);

		Optional<User> optUser = userService.findById(id);
		if (optUser.isPresent()) {
			strImage = optUser.get().getPicture();
			changePasswordDto dto = new changePasswordDto();
			BeanUtils.copyProperties(optUser.get(), dto);
			model.addAttribute("user", user);
			model.addAttribute("edituserDto", dto);
			model.addAttribute("name", optUser.get().getUsername());
			return "users/changePassword";
		}

		return "shops/changePassword";
	}
	@PostMapping("/changepassword")
	public String updatePassword(Model model, @Validated changePasswordDto userDto, BindingResult result) {
System.out.println("========================================");
		// Check login
		if (!UserLogin.authenticated_user()) {
			model.addAttribute("user", new User());
			model.addAttribute("message", "Please log in to access!!");
			return "homes/login";
		}

		// Set user login
		User user = UserLogin.USER;
		model.addAttribute("user", user);

		if (!user.getPassword().equals(userDto.getOldpassword())) {
			model.addAttribute("checkValid", "M???t kh???u c?? kh??ng ????ng!");
			return "users/changePassword";
		}
		if (!userDto.getNewpassword().equals(userDto.getRepassword())) {
			model.addAttribute("checkValid", "M???t kh???u x??c nh???n kh??ng ????ng!");
			return "users/changePassword";
		}

		User userUpdate = new User();

		userUpdate.setId(UserLogin.USER.getId());
		userUpdate.setUsername(user.getUsername());
		userUpdate.setEmail(user.getEmail());
		userUpdate.setPhone(user.getPhone());
		userUpdate.setAddress(user.getAddress());
		userUpdate.setFullname(user.getFullname());		
		userUpdate.setPassword(userDto.getRepassword());
		userUpdate.setPicture(user.getPicture());
		userUpdate.setStatus(UserLogin.USER.getStatus());

		userService.save(userUpdate);

		UserLogin.USER = userUpdate;

		model.addAttribute("message", "???? ?????i m???t kh???u th??nh c??ng!");
		Optional<User> optuser = userService.findById(UserLogin.USER.getId());
		strImage = optuser.get().getPicture();
		changePasswordDto dto = new changePasswordDto();
		BeanUtils.copyProperties(optuser.get(), dto);
		System.out.println(dto.getId()+"========================================");
		model.addAttribute("user", user);
		model.addAttribute("edituserDto", dto);
		model.addAttribute("user", user);
		model.addAttribute("userLogin", user);
		model.addAttribute("shopLogin", null);

		// Send mail
////		String username = shop.getUsername();
////		String name = shop.getShopname();
////		String email = shop.getEmail();
////
////		String strName = "";
////		if (name == null || name.equals("")) {
////			strName = username;
////		} else {
////			strName = name;
////		}
////
////		String text = "Xin ch??o " + strName
////				+ ",\n \nB???n v???a thay ?????i th??nh c??ng m???t kh???u t??i kho???n Ch??? Tr???i c???a b???n.\n"
////				+ "N???u b???n kh??ng th???c hi???n h??nh ?????ng n??y, b???n c?? th??? kh??i ph???c quy???n truy c???p "
////				+ "b???ng c??ch nh???p (email ng?????i d??ng) v??o bi???u m???u t???i (link ?????i m???t kh???u)\n"
////				+ "N???u b???n g???p v???n ?????, xin vui l??ng li??n h??? h??? tr??? qua email chotroi.basic@gmail.com ????? ???????c h??? tr??? nhi???u h??n."
////				+ "\nCh??ng t??i ?????c bi???t kh??ng khuy???n kh??ch b???n ti???t l??? m???t kh???u v???i b???t k??? ai\n \nTh??n ??i,";
////
////		SimpleMailMessage message = new SimpleMailMessage();
////		message.setTo(email);
////		message.setSubject("?????I M???T KH???U CH??? TR???I");
////		message.setText(text);
////		this.emailSender.send(message);
		return "users/changePassword";
	}

}