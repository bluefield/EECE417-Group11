package eece417project;

import java.io.IOException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class registerServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
             throws IOException {
		
		//grab user inputs from the registration page
        String firstName = req.getParameter("fname");
        String lastName = req.getParameter("lname");
        String userName = req.getParameter("uname");
        String password = req.getParameter("pass");
        String userType = req.getParameter("userType");
        
		Entity user = new Entity("User");
		user.setProperty("firstName", firstName);
		user.setProperty("lastName", lastName);
		user.setProperty("password", password);
		user.setProperty("userType", userType);
		user.setProperty("userName", userName);
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		datastore.put(user);
		

        resp.sendRedirect("/home.jsp");
		
	}
	
	
}
