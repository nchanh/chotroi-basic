package edu.poly.spring.controllers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.multipart.MultipartFile;

import edu.poly.spring.dtos.EditshopDto;
import edu.poly.spring.dtos.UserLoginDTO;
import edu.poly.spring.dtos.changePasswordDto;
import edu.poly.spring.helpers.UserLogin;
import edu.poly.spring.models.Shop;
import edu.poly.spring.models.User;
import edu.poly.spring.services.ShopService;

@Controller
@RequestMapping("/shops")
public class ShopController {

	private static final Logger log = LoggerFactory.getLogger(ShopController.class);

	String strImage = "";

	@Autowired
	private ShopService shopService;

	@RequestMapping("/profile/{id}")
	public String detailShop(Model model, @PathVariable(name = "id") Integer id) {

		// Check login
		if (!UserLogin.authenticated_shop() && !UserLogin.authenticated_user()) {
			model.addAttribute("userLoginDTO", new UserLoginDTO());
			model.addAttribute("message", "Vui l??ng ????ng nh???p ????? truy c???p!");
			return "logins/login";
		}

		// Set shop login
		Shop shop = UserLogin.SHOP;
		model.addAttribute("user", shop);
		model.addAttribute("userLogin", null);
		model.addAttribute("shopLogin", shop);

		Optional<Shop> optShop = shopService.findById(id);
		if (optShop.isPresent()) {
			strImage = optShop.get().getPicture();

			EditshopDto dto = new EditshopDto();
			BeanUtils.copyProperties(optShop.get(), dto);
			model.addAttribute("user", shop);
			model.addAttribute("editshopDto", dto);
			model.addAttribute("user", shop);
			return "shops/profileShop";
		}

		return "shops/profileShop";
	}

	@PostMapping("/update")
	public String update(Model model, @Validated EditshopDto editshopDto, BindingResult result,
			@PathVariable(name = "image") MultipartFile image) {

		// Check login
		if (!UserLogin.authenticated_shop()) {
			model.addAttribute("shop", new Shop());
			model.addAttribute("message", "Please log in to access!!");
			return "logins/login";
		}

		// check error
		if (result.hasErrors()) {
			model.addAttribute("message", "Please input or required fields!!");
			model.addAttribute("editshopDto", editshopDto);
			return "shops/profileShop";
		}

		model.addAttribute("message", "C???p nh???t t??i kho???n th??nh c??ng!");

		editshopDto.setImage(image);

		Path path = Paths.get("images/");
		try (InputStream inputStream = editshopDto.getImage().getInputStream()) {
			Files.copy(inputStream, path.resolve(editshopDto.getImage().getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			log.info("Image is " + editshopDto.getImage());
		} catch (Exception e) {
			log.info("Image is null");
		}

		// Set user login
		Shop shop = new Shop();
		shop.setId(UserLogin.SHOP.getId());
		shop.setUsername(editshopDto.getUsername());
		shop.setPassword(UserLogin.SHOP.getPassword());

		if (editshopDto.getImage().getOriginalFilename().equals("")) {
			shop.setPicture(strImage);
		} else {
			shop.setPicture(editshopDto.getImage().getOriginalFilename());
		}
		shop.setEmail(editshopDto.getEmail());
		shop.setPhone(editshopDto.getPhone());
		shop.setAddress(editshopDto.getAddress());
		shop.setInformation(editshopDto.getInformation());
		shop.setShopname(editshopDto.getShopname());
		shop.setStatus(UserLogin.SHOP.getStatus());

		shopService.save(shop);

		Optional<Shop> optShop = shopService.findById(UserLogin.SHOP.getId());
		strImage = optShop.get().getPicture();
		EditshopDto dto = new EditshopDto();
		BeanUtils.copyProperties(optShop.get(), dto);
		model.addAttribute("user", shop);
		model.addAttribute("editshopDto", dto);
		model.addAttribute("user", shop);
		model.addAttribute("userLogin", null);
		model.addAttribute("shopLogin", shop);

		return "shops/profileShop";
	}

	@RequestMapping("/change-password/{id}")
	public String changePassword(Model model, @PathVariable(name = "id") Integer id) {

//		// Check login
//		if (!UserLogin.authenticated_shop() && !UserLogin.authenticated_user()) {
//			model.addAttribute("userLoginDTO", new UserLoginDTO());
//			model.addAttribute("message", "Vui l??ng ????ng nh???p ????? truy c???p!");
//			return "logins/login";
//		}

		// Set shop login
		Shop shop = UserLogin.SHOP;
		model.addAttribute("user", shop);
		model.addAttribute("userLogin", null);
		model.addAttribute("shopLogin", shop);

		Optional<Shop> optShop = shopService.findById(id);
		if (optShop.isPresent()) {
			strImage = optShop.get().getPicture();

			changePasswordDto dto = new changePasswordDto();
			BeanUtils.copyProperties(optShop.get(), dto);
			model.addAttribute("user", shop);
			model.addAttribute("editshopDto", dto);
			model.addAttribute("user", shop);
			model.addAttribute("name", optShop.get().getUsername());

			return "shops/changePassword";
		}

		return "shops/changePassword";
	}

//
//	HomeController hcl;
//
//	static String ima = "";
//	static int idshop;
//	static String password = "";
//
//	@Autowired
//	private ShopService shopService;
//
//	@Autowired
//	private UserService userService;
//
//	@Autowired
//	private JavaMailSender emailSender;
//
//	@RequestMapping("/detailshop/{id}")
//	public String detailShop(Model model, @PathVariable(name = "id") Integer id) {
//
//		System.out.println(hcl.sel + "==============");
//		// Check login
//		if (!UserLogin.authenticated()) {
//			model.addAttribute("shop", new Shop());
//			model.addAttribute("message", "Please log in to access!!");
//			return "homes/login";
//		}
//
//		if (hcl.sel.equals("shop")) {
//			// Set user login
//			Shop shop = UserLogin.USER;
//			model.addAttribute("user", shop);
//
//			Optional<Shop> optShop = shopService.findById(id);
//			if (optShop.isPresent()) {
//				ima = optShop.get().getPicture();
//				idshop = id;
//				password = shop.getPassword();
//				EditshopDto dto = new EditshopDto();
//				BeanUtils.copyProperties(optShop.get(), dto);
//				model.addAttribute("user", shop);
//				model.addAttribute("editshopDto", dto);
//				
//				if (dto.getStatus().contentEquals("block")) {
//					model.addAttribute("statusUser", "block");
//				}else {
//					model.addAttribute("statusUser", null);
//				}
//				model.addAttribute("user", shop);
//				return "shops/detailShop";
//			}
//		} else {
//			if (hcl.sel.equals("user")) {
//				// Set user login
//				User user = UserLogin.USER1;
//				model.addAttribute("user", user);
//				Optional<User> optUser = userService.findById(id);
//				if (optUser.isPresent()) {
//					ima = optUser.get().getPicture();
//					idshop = id;
//					System.out.println(id + "===============");
//					password = user.getPassword();
//					EdituserDto dto = new EdituserDto();
//					BeanUtils.copyProperties(optUser.get(), dto);
//					model.addAttribute("edituserDto", dto);
//					return "users/detailShop";
//				} else {
//					return "homes/admin";
//				}
//			}
//		}
//		return "shops/detailShop";
//	}
//
//	@PostMapping("/update")
//	public String update(Model model, @Validated EditshopDto editshopDto, BindingResult result,
//			@PathVariable(name = "image") MultipartFile image) {
//
//		// Check login
//		if (!UserLogin.authenticated()) {
//			model.addAttribute("shop", new Shop());
//			model.addAttribute("message", "Please log in to access!!");
//			return "homes/login";
//		}
//		// check error
//		if (result.hasErrors()) {
//			model.addAttribute("message", "Please input or required fields!!");
//			model.addAttribute("editshopDto", editshopDto);
//			System.out.println("====" + result);
//			return "shops/detailShop";
//		}
//
//		model.addAttribute("message", "C???p nh???t t??i kho???n th??nh c??ng!");
//
//		editshopDto.setImage(image);
//
//		Path path = Paths.get("images/");
//		try (InputStream inputStream = editshopDto.getImage().getInputStream()) {
//			Files.copy(inputStream, path.resolve(editshopDto.getImage().getOriginalFilename()),
//					StandardCopyOption.REPLACE_EXISTING);
//			String filename = editshopDto.getImage().getOriginalFilename();
//			System.out.println(editshopDto.getImage());
//		} catch (Exception e) {
//			System.out.println("Image is null");
//
//		}
//
//		// Set user login
//		Shop shop = new Shop();
//		shop.setId(idshop);
//		shop.setUsername(editshopDto.getUsername());
//		shop.setPassword(password);
//
//		if (editshopDto.getImage().getOriginalFilename().equals("")) {
//			shop.setPicture(ima);
//		} else {
//			shop.setPicture(editshopDto.getImage().getOriginalFilename());
//		}
//		shop.setEmail(editshopDto.getEmail());
//		shop.setPhone(editshopDto.getPhone());
//		shop.setAddress(editshopDto.getAddress());
//		shop.setInformation(editshopDto.getInformation());
//		shop.setShopname(editshopDto.getShopname());
//		shop.setStatus(editshopDto.getStatus());
//		shop.setBusinesscode(editshopDto.getBusinesscode());
//
//		shopService.save(shop);
//
//		Optional<Shop> optShop = shopService.findById(idshop);
//		if (optShop.isPresent()) {
//			ima = optShop.get().getPicture();
//			password = shop.getPassword();
//			EditshopDto dto = new EditshopDto();
//			BeanUtils.copyProperties(optShop.get(), dto);
//			model.addAttribute("user", shop);
//			model.addAttribute("editshopDto", dto);
//		}
//
//		return "shops/detailShop";
//	}
//

//
	@PostMapping("/changepassword")
	public String updatePassword(Model model, @Validated changePasswordDto shopDto, BindingResult result) {

		// Check login
		if (!UserLogin.authenticated_shop()) {
			model.addAttribute("shop", new Shop());
			model.addAttribute("message", "Please log in to access!!");
			return "homes/login";
		}

		// Set user login
		Shop shop = UserLogin.SHOP;
		model.addAttribute("user", shop);

		if (!shop.getPassword().equals(shopDto.getOldpassword())) {
			model.addAttribute("checkValid", "M???t kh???u c?? kh??ng ????ng!");
			return "shops/changePassword";
		}
		if (!shopDto.getNewpassword().equals(shopDto.getRepassword())) {
			model.addAttribute("checkValid", "M???t kh???u x??c nh???n kh??ng ????ng!");
			return "shops/changePassword";
		}

		Shop shopUpdate = new Shop();

		shopUpdate.setId(UserLogin.SHOP.getId());
		shopUpdate.setUsername(shop.getUsername());
		shopUpdate.setEmail(shop.getEmail());
		shopUpdate.setPhone(shop.getPhone());
		shopUpdate.setAddress(shop.getAddress());
		shopUpdate.setInformation(shop.getInformation());
		shopUpdate.setShopname(shop.getShopname());
		shopUpdate.setPassword(shopDto.getRepassword());
		shopUpdate.setPicture(shop.getPicture());
		shopUpdate.setStatus(UserLogin.SHOP.getStatus());

		shopService.save(shopUpdate);

		UserLogin.SHOP = shopUpdate;

		model.addAttribute("message", "???? ?????i m???t kh???u th??nh c??ng!");
		Optional<Shop> optShop = shopService.findById(UserLogin.SHOP.getId());
		strImage = optShop.get().getPicture();
		changePasswordDto dto = new changePasswordDto();
		BeanUtils.copyProperties(optShop.get(), dto);
		model.addAttribute("user", shop);
		model.addAttribute("editshopDto", dto);
		model.addAttribute("user", shop);
		model.addAttribute("userLogin", null);
		model.addAttribute("shopLogin", shop);

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
		return "shops/changePassword";
	}

//
//	@RequestMapping("/find")
//	public String find(ModelMap model, @RequestParam(defaultValue = "") String name) {
//
//		// Set user login
//		Shop shop = UserLogin.USER;
//		model.addAttribute("user", shop);
//
////		Shop shopfind = shopRepository.findByUsername(name);
//
//		List<Shop> list = shopService.findByUsernameLikeOrderByUsername(name);
//
//		model.addAttribute("shops", list);
//
//		return "shops/find";
//	}
}
