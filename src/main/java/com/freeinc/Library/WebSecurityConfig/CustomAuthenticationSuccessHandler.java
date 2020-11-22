package com.freeinc.Library.WebSecurityConfig;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.freeinc.Library.Business.IUserService;
import com.freeinc.Library.Entites.User;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private IUserService userService;

	@Autowired
	public CustomAuthenticationSuccessHandler(IUserService userService) {
		this.userService = userService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		System.out.println("\n\nIn customAuthenticationSuccessHandler\n\n");

		String userName = authentication.getName();

		System.out.println("userName=" + userName);

		User theUser = userService.findByUserName(userName);

		// now place in the session
		HttpSession session = request.getSession();
		session.setAttribute("user", theUser);

		String role = authentication.getAuthorities().toString();
		System.out.println(role);
		if (role.contains("ROLE_USERS")) {
			response.sendRedirect(request.getContextPath() + "/dashboard");
		} else if (role.contains("ROLE_ADMIN")) {
			response.sendRedirect(request.getContextPath() + "/admin");
		} else if (role.contains("ROLE_MODERATOR")) {
			System.out.println("in else");
			response.sendRedirect(request.getContextPath() + "/moderator");
		}

	}

}
