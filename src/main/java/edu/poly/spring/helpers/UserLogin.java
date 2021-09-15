package edu.poly.spring.helpers;

import org.springframework.ui.Model;

import edu.poly.spring.dtos.UserLoginDTO;
import edu.poly.spring.models.Shop;
import edu.poly.spring.models.User;

public class UserLogin {

	public static Shop SHOP = null;
	public static User USER = null;
	public static String ROLE_USER = null;

	public static void logoff() {
		UserLogin.ROLE_USER = null;
		UserLogin.SHOP = null;
		UserLogin.USER = null;
	}

	public static boolean authenticated_shop() {
		return UserLogin.SHOP != null;
	}

	public static boolean authenticated_user() {
		return UserLogin.USER != null;
	}

}
