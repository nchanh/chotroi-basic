package edu.poly.spring.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edu.poly.spring.dtos.UploadForm;
import edu.poly.spring.helpers.UserLogin;
import edu.poly.spring.models.PostingDetail;
import edu.poly.spring.models.Rate;
import edu.poly.spring.models.Shop;
import edu.poly.spring.models.User;
import edu.poly.spring.services.PostingDetailService;
import edu.poly.spring.services.RateService;
import edu.poly.spring.services.ShopService;
import edu.poly.spring.services.UserService;

@Controller
public class ImageController {

	private static final Logger log = LoggerFactory.getLogger(ImageController.class);

	@Autowired
	private ShopService shopService;

	@Autowired
	private UserService userService;

	@Autowired
	private PostingDetailService postingDetailService;

	@Autowired
	private RateService rateService;

	private static String UPLOAD_DIR = "src/main/resources/static/images/upload";

	@RequestMapping(value = "getimage/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> downloadLinkImage(@PathVariable Integer id) {

		if (UserLogin.ROLE_USER.equals("shop")) {
			Optional<Shop> shop = shopService.findById(id);
			if (shop.isPresent()) {
				Shop shop2 = shop.get();
				try {
					Path filename = Paths.get("images", shop2.getPicture());
					byte[] buffer = Files.readAllBytes(filename);
					ByteArrayResource bsr = new ByteArrayResource(buffer);
					return ResponseEntity.ok().contentLength(buffer.length)
							.contentType(MediaType.parseMediaType("image/png")).body(bsr);
				} catch (Exception e) {
					log.info("Shop Image is null!");
				}
			}
		}

		if (UserLogin.ROLE_USER.equals("user")) {
			Optional<User> user = userService.findById(id);
			if (user.isPresent()) {
				User user1 = user.get();
				try {
					Path filename = Paths.get("images", user1.getPicture());
					byte[] buffer = Files.readAllBytes(filename);
					ByteArrayResource bsr = new ByteArrayResource(buffer);
					return ResponseEntity.ok().contentLength(buffer.length)
							.contentType(MediaType.parseMediaType("image/png")).body(bsr);
				} catch (Exception e) {
					log.info("User Image is null!");
				}
			}
		}

		return ResponseEntity.badRequest().build();
	}

	@RequestMapping(value = "getimage/{username}/username", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> downloadLinkImage(@PathVariable String username) {

		Shop shop = shopService.findByUsername(username);
		if (shop != null) {
			try {
				Path filename = Paths.get("images", shop.getPicture());
				byte[] buffer = Files.readAllBytes(filename);
				ByteArrayResource bsr = new ByteArrayResource(buffer);
				return ResponseEntity.ok().contentLength(buffer.length)
						.contentType(MediaType.parseMediaType("image/png")).body(bsr);
			} catch (Exception e) {
				log.info("Shop Image is null!");
			}
		}

		User user = userService.findByUsername(username);
		if (user != null) {
			try {
				Path filename = Paths.get("images", user.getPicture());
				byte[] buffer = Files.readAllBytes(filename);
				ByteArrayResource bsr = new ByteArrayResource(buffer);
				return ResponseEntity.ok().contentLength(buffer.length)
						.contentType(MediaType.parseMediaType("image/png")).body(bsr);
			} catch (Exception e) {
				log.info("User Image is null!");
			}
		}

		return ResponseEntity.badRequest().build();
	}

	@RequestMapping(value = "getimage/{username}/user", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> downloadLinkImageUser(@PathVariable String username) {

		User user = userService.findByUsername(username);
		if (user != null) {
			try {
				Path filename = Paths.get("images", user.getPicture());
				byte[] buffer = Files.readAllBytes(filename);
				ByteArrayResource bsr = new ByteArrayResource(buffer);
				return ResponseEntity.ok().contentLength(buffer.length)
						.contentType(MediaType.parseMediaType("image/png")).body(bsr);
			} catch (Exception e) {
				log.info("User Image is null!");
			}
		}

		return ResponseEntity.badRequest().build();
	}

	@RequestMapping(value = "getimage/{username}/shop", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> downloadLinkImageShop(@PathVariable String username) {

		Optional<Shop> shop = Optional.of(shopService.findByUsername(username));

		if (shop.isPresent()) {
			Shop shop2 = shop.get();
			try {
				Path filename = Paths.get("images", shop2.getPicture());
				byte[] buffer = Files.readAllBytes(filename);
				ByteArrayResource bsr = new ByteArrayResource(buffer);
				return ResponseEntity.ok().contentLength(buffer.length)
						.contentType(MediaType.parseMediaType("image/png")).body(bsr);
			} catch (Exception e) {
				log.info("User Image is null!");
			}
		}

		return ResponseEntity.badRequest().build();
	}

	@PostMapping("/rest/uploadMultiFiles")
	public ResponseEntity<?> uploadFileMulti(@ModelAttribute UploadForm form) throws Exception {

		String result = null;
		try {

			result = this.saveUploadedFiles(form.getFiles());

		}
		// Here Catch IOException only.
		// Other Exceptions catch by RestGlobalExceptionHandler class.
		catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>("Uploaded to: " + result, HttpStatus.OK);
	}

	// Save Files
	private String saveUploadedFiles(MultipartFile[] files) throws IOException {

		// Make sure directory exists!
		File uploadDir = new File(UPLOAD_DIR);
		uploadDir.mkdirs();

		StringBuilder sb = new StringBuilder();

		for (MultipartFile file : files) {

			if (file.isEmpty()) {
				continue;
			}
			String uploadFilePath = UPLOAD_DIR + "/" + file.getOriginalFilename();

			byte[] bytes = file.getBytes();
			Path path = Paths.get(uploadFilePath);
			Files.write(path, bytes);

			sb.append(uploadFilePath).append(", ");
		}
		return sb.toString();
	}

	@RequestMapping(value = "getimage1/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> downloadLinkInage1(@PathVariable Integer id) {
		Optional<PostingDetail> sop = postingDetailService.findById(id);
		if (sop.isPresent()) {
			PostingDetail postingDetail = sop.get();
			try {
				Path filename1 = Paths.get("images", postingDetail.getPicture1());
				byte[] buffer1 = Files.readAllBytes(filename1);
				ByteArrayResource bsr1 = new ByteArrayResource(buffer1);
				return ResponseEntity.ok().contentLength(buffer1.length)
						.contentType(MediaType.parseMediaType("image/png")).body(bsr1);
			} catch (Exception e) {
				log.info("Posting Detail Image 1 is null!");
			}

		}
		return ResponseEntity.badRequest().build();
	}

	@RequestMapping(value = "getimage2/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> downloadLinkInage2(@PathVariable Integer id) {
		Optional<PostingDetail> sop = postingDetailService.findById(id);
		if (sop.isPresent()) {
			PostingDetail postingDetail = sop.get();
			try {
				Path filename2 = Paths.get("images", postingDetail.getPicture2());
				byte[] buffer2 = Files.readAllBytes(filename2);
				ByteArrayResource bsr2 = new ByteArrayResource(buffer2);
				return ResponseEntity.ok().contentLength(buffer2.length)
						.contentType(MediaType.parseMediaType("image/png")).body(bsr2);
			} catch (Exception e) {
				log.info("Posting Detail Image 2 is null!");
			}

		}
		return ResponseEntity.badRequest().build();
	}

	@RequestMapping(value = "getimage3/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> downloadLinkInage3(@PathVariable Integer id) {
		Optional<PostingDetail> sop = postingDetailService.findById(id);
		if (sop.isPresent()) {
			PostingDetail postingDetail = sop.get();
			try {
				Path filename3 = Paths.get("images", postingDetail.getPicture3());
				byte[] buffer3 = Files.readAllBytes(filename3);
				ByteArrayResource bsr3 = new ByteArrayResource(buffer3);
				return ResponseEntity.ok().contentLength(buffer3.length)
						.contentType(MediaType.parseMediaType("image/png")).body(bsr3);
			} catch (Exception e) {
				log.info("Posting Detail Image 3 is null!");
			}

		}
		return ResponseEntity.badRequest().build();
	}

	@RequestMapping(value = "getimage4/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> downloadLinkInage4(@PathVariable Integer id) {
		Optional<PostingDetail> sop = postingDetailService.findById(id);
		if (sop.isPresent()) {
			PostingDetail postingDetail = sop.get();
			try {
				Path filename4 = Paths.get("images", postingDetail.getPicture4());
				byte[] buffer4 = Files.readAllBytes(filename4);
				ByteArrayResource bsr4 = new ByteArrayResource(buffer4);
				return ResponseEntity.ok().contentLength(buffer4.length)
						.contentType(MediaType.parseMediaType("image/png")).body(bsr4);
			} catch (Exception e) {
				log.info("Posting Detail Image 4 is null!");
			}

		}
		return ResponseEntity.badRequest().build();
	}

	@RequestMapping(value = "getimage/{id}/rate", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> getImageRate(@PathVariable Integer id) {
		Optional<Rate> optRate = rateService.findById(id);
		if (optRate.isPresent()) {
			Rate rate = optRate.get();
			try {
				Path filename2 = Paths.get("images", rate.getImage());
				byte[] buffer2 = Files.readAllBytes(filename2);
				ByteArrayResource bsr2 = new ByteArrayResource(buffer2);
				return ResponseEntity.ok().contentLength(buffer2.length)
						.contentType(MediaType.parseMediaType("image/png")).body(bsr2);
			} catch (Exception e) {
				log.info("Rate Image is null!");
			}

		}
		return ResponseEntity.badRequest().build();
	}

	@RequestMapping(value = "getimage/chotroi", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> getImageChoTroi() {
		try {
			Path filename2 = Paths.get("images", "choTroi.png");
			byte[] buffer2 = Files.readAllBytes(filename2);
			ByteArrayResource bsr2 = new ByteArrayResource(buffer2);
			return ResponseEntity.ok().contentLength(buffer2.length).contentType(MediaType.parseMediaType("image/png"))
					.body(bsr2);
		} catch (Exception e) {
			log.info("Image is null!");
		}

		return ResponseEntity.badRequest().build();
	}
}
