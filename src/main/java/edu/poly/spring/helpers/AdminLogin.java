package edu.poly.spring.helpers;

import edu.poly.spring.models.Admin;

public class AdminLogin {

	public static Admin ADMIN = null;

	public static void logoff() {
		AdminLogin.ADMIN = null;
	}

	public static boolean authenticated_admin() {
		return AdminLogin.ADMIN != null;
	}

}
