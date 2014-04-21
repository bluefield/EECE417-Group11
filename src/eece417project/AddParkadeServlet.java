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
public class AddParkadeServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
             throws IOException {
		
		//grab user inputs from the AddParkade page
		String parkade_lat = req.getParameter("lat");
		String parkade_lng = req.getParameter("lng");
        String parkade_name = req.getParameter("pname");
		String parkade_price = req.getParameter("price");
		String parkade_des = req.getParameter("pdes");
        Entity parkade = new Entity("Parkade");
		
        parkade.setProperty("parkade_lat", parkade_lat);
		parkade.setProperty("parkade_lng", parkade_lng);
		parkade.setProperty("parkade_name", parkade_name);
		parkade.setProperty("parkade_price", parkade_price);
		parkade.setProperty("parkade_des", parkade_des);
		
		
		System.out.println("location:" + parkade_lat + ":" + parkade_lng);
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		datastore.put(parkade);
		

        resp.sendRedirect("/host.jsp");
		
	}
	
	
}
