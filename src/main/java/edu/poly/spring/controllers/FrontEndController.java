package edu.poly.spring.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.poly.spring.dtos.ShowRate;
import edu.poly.spring.models.Posting;
import edu.poly.spring.models.PostingDetail;
import edu.poly.spring.models.PostingSaved;
import edu.poly.spring.models.Product;
import edu.poly.spring.models.ProductType;
import edu.poly.spring.models.Shop;
import edu.poly.spring.models.User;
import edu.poly.spring.services.PostingDetailService;
import edu.poly.spring.services.PostingSavedService;
import edu.poly.spring.services.PostingService;
import edu.poly.spring.services.ProductService;
import edu.poly.spring.services.ProductTypeService;
import edu.poly.spring.services.ShopService;
import edu.poly.spring.services.UserService;

@RestController
public class FrontEndController {

	@Autowired
	private UserService userService;

	@Autowired
	private ShopService shopService;

	@Autowired
	private ProductTypeService productTypeService;

	@Autowired
	private ProductService productService;

	@Autowired
	private PostingService postingService;

	@Autowired
	private PostingDetailService postingDetailService;

	@Autowired
	private PostingSavedService postingSavedService;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	// -------------------------
	// ------ LIBS MANAGER -----
	// -------------------------

	@RequestMapping("/libs/angular.min.js")
	public String readFileAngular() {
		return "libs/angular/angular.min.js";
	}

	@RequestMapping("/libs/angular.route.js")
	public String readFileAngularRoute() {
		return "libs/angular-route/angular.route.js";
	}

	// -------------------------
	// ------ USER MANAGER -----
	// -------------------------

	@RequestMapping("/users/find-all")
	public List<User> findAllUsers() {
		return (List<User>) userService.findAll();
	}

	@PostMapping("/users/insert-all-users")
	public List<User> insertAllUsers(@RequestBody List<User> users) {
		return (List<User>) userService.saveAll(users);
	}

	@RequestMapping("/users/list-activated")
	public List<User> listUsersActivated() {
		return (List<User>) userService.findUsersByStatus("activated");
	}

	@RequestMapping("/users/list-not-activated")
	public List<User> listUsersNotActivated() {
		return (List<User>) userService.findUsersByStatus("not-activated");
	}

	@RequestMapping("/users/list-block")
	public List<User> listUsersNot() {
		return (List<User>) userService.findUsersByStatus("block");
	}

	@GetMapping("/users/{name}/find")
	public List<User> findByName(@PathVariable("name") String name) {
		return userService.findByUsernameLikeOrderByFullname(name);
	}

	@GetMapping("/users/{id}/get")
	public Optional<User> getUser(@PathVariable("id") Integer id) {
		return userService.findById(id);
	}

	@PutMapping("/users/{id}/update")
	public User update(@PathVariable Integer id, @RequestBody User user) {
		return userService.save(user);
	}

	@DeleteMapping("/users/{id}/delete")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
		userService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PutMapping("/users/{id}/block")
	public ResponseEntity<Void> blockUser(@PathVariable("id") Integer id) {
		Optional<User> user = userService.findById(id);
		user.get().setStatus("block");
		userService.save(user.get());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PutMapping("/users/{id}/activated")
	public ResponseEntity<Void> activatedUser(@PathVariable("id") Integer id) {
		Optional<User> user = userService.findById(id);
		user.get().setStatus("activated");
		userService.save(user.get());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PutMapping("/users/{id}/not-activated")
	public ResponseEntity<Void> notActivatedUser(@PathVariable("id") Integer id) {
		Optional<User> user = userService.findById(id);
		user.get().setStatus("not-activated");
		userService.save(user.get());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	// -------------------------
	// ------ SHOP MANAGER -----
	// -------------------------

	@RequestMapping("/shops/find-all")
	public List<Shop> findAllShops() {
		return (List<Shop>) shopService.findAll();
	}

	@PostMapping("/shops/insert-all-shops")
	public List<Shop> insertAllShops(@RequestBody List<Shop> users) {
		return (List<Shop>) shopService.saveAll(users);
	}

	@RequestMapping("/shops/list-activated")
	public List<Shop> listShopsActivated() {
		return (List<Shop>) shopService.findShopsByStatus("activated");
	}

	@RequestMapping("/shops/list-not-activated")
	public List<Shop> listShopsNotActivated() {
		return (List<Shop>) shopService.findShopsByStatus("not-activated");
	}

	@RequestMapping("/shops/list-block")
	public List<Shop> listShopsNot() {
		return (List<Shop>) shopService.findShopsByStatus("block");
	}

	@GetMapping("/shops/{id}/get")
	public Optional<Shop> getShop(@PathVariable("id") Integer id) {
		return shopService.findById(id);
	}

	@PutMapping("/shops/{id}/update")
	public Shop update(@PathVariable Integer id, @RequestBody Shop shop) {
		return shopService.save(shop);
	}

	@DeleteMapping("/shops/{id}/delete")
	public ResponseEntity<Void> deleteShop(@PathVariable("id") Integer id) {
		shopService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PutMapping("/shops/{id}/block")
	public ResponseEntity<Void> blockShop(@PathVariable("id") Integer id) {
		Optional<Shop> shop = shopService.findById(id);
		shop.get().setStatus("block");
		shopService.save(shop.get());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PutMapping("/shops/{id}/activated")
	public ResponseEntity<Void> activatedShop(@PathVariable("id") Integer id) {
		Optional<Shop> shop = shopService.findById(id);
		shop.get().setStatus("activated");
		shopService.save(shop.get());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PutMapping("/shops/{id}/not-activated")
	public ResponseEntity<Void> notActivatedShop(@PathVariable("id") Integer id) {
		Optional<Shop> shop = shopService.findById(id);
		shop.get().setStatus("not-activated");
		shopService.save(shop.get());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	// -------------------------
	// ------ PRODUCT MANAGER -----
	// -------------------------

	@RequestMapping("/products/find-all")
	public List<Product> findAllProducts() {
		return (List<Product>) productService.findAll();
	}

	@RequestMapping("/products-type/find-all")
	public List<ProductType> findAllProductsType() {
		return (List<ProductType>) productTypeService.findAll();
	}

	@PostMapping("/products-type/insert-all")
	public List<ProductType> insertAllProductType(@RequestBody List<ProductType> productTypes) {
		return (List<ProductType>) productTypeService.saveAll(productTypes);
	}

	@PostMapping("/products/insert-all")
	public List<Product> insertAllProduct(@RequestBody List<Product> productTypes) {
		return (List<Product>) productService.saveAll(productTypes);
	}

	// -------------------------
	// ------ POSTING MANAGER -----
	// -------------------------

	@RequestMapping("/postings/find-all")
	public List<Posting> findAllPostings() {
		return (List<Posting>) postingService.findAll();
	}

	@RequestMapping("/postings/list-unapproved")
	public List<Posting> findStatusUnapproved() {
		return postingService.findPostingsByStatus("unapproved");
	}

	@RequestMapping("/postings/list-approved")
	public List<Posting> findStatusApproved() {
		return postingService.findPostingsByStatus("approved");
	}

	@RequestMapping("/postings/list-sold")
	public List<Posting> findStatusSold() {
		return postingService.findPostingsByStatus("sold");
	}

	@RequestMapping("/postings/list-block")
	public List<Posting> findStatusBlock() {
		return postingService.findPostingsByStatus("block");
	}

	@RequestMapping("/postings/list-sold/{id}")
	public List<Posting> findStatusSoldByIdUser(@PathVariable("id") Integer id) {

		List<Posting> listPostings = new ArrayList<Posting>();

		Optional<User> user = userService.findById(id);
		Optional<Shop> shop = shopService.findById(id);

		if (user.isPresent()) {
			listPostings = postingService.findPostingsByStatusAndUser("sold", user.get());
		}

		if (shop.isPresent()) {
			listPostings = postingService.findPostingsByStatusAndShop("sold", shop.get());
		}

		return listPostings;
	}

	@RequestMapping("/postings/list-approved/{id}")
	public List<Posting> findStatusApprovedByIdUser(@PathVariable("id") Integer id) {

		List<Posting> listPostings = new ArrayList<Posting>();

		Optional<User> user = userService.findById(id);
		Optional<Shop> shop = shopService.findById(id);

		if (user.isPresent()) {
			listPostings = postingService.findPostingsByStatusAndUser("approved", user.get());
		}

		if (shop.isPresent()) {
			listPostings = postingService.findPostingsByStatusAndShop("approved", shop.get());
		}

		return listPostings;
	}

	@RequestMapping("/postings/list-unapproved/{id}")
	public List<Posting> findStatusUnapprovedByIdUser(@PathVariable("id") Integer id) {

		List<Posting> listPostings = new ArrayList<Posting>();

		Optional<User> user = userService.findById(id);
		Optional<Shop> shop = shopService.findById(id);

		if (user.isPresent()) {
			listPostings = postingService.findPostingsByStatusAndUser("unapproved", user.get());
		}

		if (shop.isPresent()) {
			listPostings = postingService.findPostingsByStatusAndShop("unapproved", shop.get());
		}

		return listPostings;
	}

	@RequestMapping("/postings/list-block/{id}")
	public List<Posting> findStatusBlockByIdUser(@PathVariable("id") Integer id) {

		List<Posting> listPostings = new ArrayList<Posting>();

		Optional<User> user = userService.findById(id);
		Optional<Shop> shop = shopService.findById(id);

		if (user.isPresent()) {
			listPostings = postingService.findPostingsByStatusAndUser("block", user.get());
		}

		if (shop.isPresent()) {
			listPostings = postingService.findPostingsByStatusAndShop("block", shop.get());
		}

		return listPostings;
	}

	@GetMapping("/postings/{id}/get")
	public Optional<Posting> getPosting(@PathVariable("id") Integer id) {
		return postingService.findById(id);
	}

	@DeleteMapping("/postings/{id}/delete")
	public ResponseEntity<Void> deletePosting(@PathVariable("id") Integer id) {
		postingService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PutMapping("/postings/{id}/set-unapproved")
	public ResponseEntity<Void> unapprovedPosting(@PathVariable("id") Integer id) {
		Optional<Posting> posting = postingService.findById(id);
		posting.get().setStatus("unapproved");
		postingService.save(posting.get());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PutMapping("/postings/{id}/set-approved")
	public ResponseEntity<Void> approvedPosting(@PathVariable("id") Integer id) {
		Optional<Posting> posting = postingService.findById(id);
		posting.get().setStatus("approved");
		postingService.save(posting.get());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PutMapping("/postings/{id}/set-sold")
	public ResponseEntity<Void> soldPosting(@PathVariable("id") Integer id) {
		Optional<Posting> posting = postingService.findById(id);
		posting.get().setStatus("sold");
		postingService.save(posting.get());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PutMapping("/postings/{id}/set-block")
	public ResponseEntity<Void> blockPosting(@PathVariable("id") Integer id) {
		Optional<Posting> posting = postingService.findById(id);
		posting.get().setStatus("block");
		postingService.save(posting.get());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("postings/{id}/find-by-product")
	public List<Posting> getPostingsByProduct(@PathVariable("id") Integer id) {
		List<Posting> postings = postingService.findPostingsByProductId(id);
		Collections.sort(postings);
		return postings;
	}

	@GetMapping("postings/{id}/find-by-user")
	public List<Posting> getPostingsByUser(@PathVariable("id") Integer id) {
		List<Posting> listPosting = new ArrayList<Posting>();

		Optional<User> user = userService.findById(id);
		Optional<Shop> shop = shopService.findById(id);

		if (user.isPresent()) {
			listPosting = postingService.findByUser(user.get());
			if (listPosting.size() == 0) {
				listPosting.add(new Posting(null, false, null, null, user.get(), null, null, null));
			}
			return listPosting;
		}

		if (shop.isPresent()) {
			listPosting = postingService.findByShop(shop.get());
			if (listPosting.size() == 0) {
				listPosting.add(new Posting(null, false, null, null, null, shop.get(), null, null));
			}
			return listPosting;

		}

		return new ArrayList<Posting>();
	}

	@GetMapping("postings/find-top16-sort-date")
	public List<Posting> getPostingTop16SortDate() {
		return postingService.findTop16ByOrderByDateDesc();
	}

	// -------------------------
	// ------ POSTING DETAILS MANAGER -----
	// -------------------------

	@GetMapping("{id}/get-postingdetail")
	public Optional<PostingDetail> getPostingDetailById(@PathVariable("id") Integer id) {
		return postingDetailService.findById(id);
	}

	@GetMapping("postingdetails/{title}/find-by-title")
	public List<PostingDetail> getPostingDetailsByTitle(@PathVariable("title") String title) {
		List<PostingDetail> postingDetails = postingDetailService.findPostingDetailByTitleContaining(title);
		Collections.sort(postingDetails);
		return postingDetails;
	}

	// -------------------------
	// ------ SEARCH MANAGER -----
	// -------------------------

	@GetMapping("postingdetails/search")
	public List<PostingDetail> getPostingsByKeyword(@RequestParam(name = "keyword") String keyword) {
		List<PostingDetail> postingDetails = postingDetailService.findPostingDetailByTitleContaining(keyword);
		Collections.sort(postingDetails);
		return postingDetails;
	}

	@GetMapping("postings-detail-by-product-type/search")
	public List<PostingDetail> getPostingByProductsType(@RequestParam(name = "id") Integer id) {

		List<PostingDetail> postingDetails = new ArrayList<PostingDetail>();

		Optional<ProductType> productType = productTypeService.findById(id);
		List<Product> products = productService.findByProducttype(productType.get());

		for (Product product : products) {
			List<Posting> postings = postingService.findPostingsByProductId(product.getId());
			for (Posting posting : postings) {
				PostingDetail pd = postingDetailService.findPostingDetailByPostingId(posting.getId());
				System.out.println(posting.getId());
				System.out.println(pd.getId());
				postingDetails.add(pd);
			}
		}

		Collections.sort(postingDetails);

		return postingDetails;
	}

	@GetMapping("postings-detail-by-product/search")
	public List<PostingDetail> getPostingByProducts(@RequestParam(name = "id") Integer id) {
		List<PostingDetail> postingDetails = new ArrayList<PostingDetail>();
		List<Posting> postings = postingService.findPostingsByProductId(id);
		for (Posting posting : postings) {
			PostingDetail pd = postingDetailService.findPostingDetailByPostingId(posting.getId());
			postingDetails.add(pd);
		}

		Collections.sort(postingDetails);

		return postingDetails;
	}

	@GetMapping("postings/{address}")
	public List<PostingDetail> getPostings(
			@RequestParam(value = "addressPhuongXa", required = false) String addressPhuongXa,
			@RequestParam(value = "addressQuanHuyen", required = false) String addressQuanHuyen,
			@RequestParam(value = "addressTinhThanhPho", required = false) String addressTinhThanhPho) {
		List<PostingDetail> postingDetails = new ArrayList<PostingDetail>();

		String address = "Phuong abc, Huyện Kiến Thuỵ, Thành phố Hải Phòng";
		String[] parts = address.split(",");
		String part1 = parts[0];
		String part2 = parts[1].substring(1);
		String part3 = parts[2].substring(1);

		System.out.println();

		if (!part1.equals("")) {
			List<PostingDetail> listPD = (List<PostingDetail>) postingDetailService.findAll();
			for (int i = 0; i < listPD.size(); i++) {
				System.out.println("|" + listPD.get(i).getAddress());
				if ((listPD.get(i).getAddress()).contains(part1)) {
					postingDetails.add(listPD.get(i));
				}
			}

		}

		return postingDetails;
	}

	// -------------------------
	// ------ POSTING SAVED MANAGER -----
	// -------------------------

	@GetMapping("posting-saved/find-all")
	public List<PostingSaved> savePostingSaved() {
		return (List<PostingSaved>) postingSavedService.findAll();
	}	

}
