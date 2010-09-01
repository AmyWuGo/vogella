package de.vogella.gae.java.todo;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.datanucleus.sco.simple.GregorianCalendar;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import de.vogella.gae.java.todo.dao.Dao;

@SuppressWarnings("serial")
public class ServletCreateTodo extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		System.out.println("Creating new todo ");
	    User user =  (User) req.getAttribute("user");
	    if (user == null) {
	    	UserService userService = UserServiceFactory.getUserService();
	    	user = userService.getCurrentUser();
	    }
		
		String summary = checkNull(req.getParameter("summary"));
		String longDescription = checkNull(req.getParameter("description"));
		String url = checkNull(req.getParameter("url"));
		// Not yet used
		String dueDate = req.getParameter("dueDate");

		Dao.INSTANCE.add(user.getNickname(), summary, longDescription, url,
				GregorianCalendar.getInstance());

		resp.sendRedirect("/TodoApplication.jsp");
	}

	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
}
