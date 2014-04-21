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
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class homeServlet extends HttpServlet {
	public static String s_username = null;
	public static String s_password = null;
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
              throws IOException {
        String input_username =  req.getParameter("username");
        String input_password = req.getParameter("pswrd");
        boolean finduser = false;
        s_username = input_username;
        s_password = input_password;
        System.out.println("input name:" + s_username);
        System.out.println("input pass:" + s_password);
        
 
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query userQuery = new Query("User");
        PreparedQuery pq = datastore.prepare(userQuery);
        for (Entity u1:pq.asIterable()){
        	String username = u1.getProperty("userName").toString();
        	String password = u1.getProperty("password").toString();
        	String usertype = u1.getProperty("userType").toString();
        	if(username.equals(input_username) && password.equals(input_password)){
        		finduser = true;
        		if(usertype.equals("customer")){
        			resp.sendRedirect("/customer.jsp?userName="+s_username);
        		}else{
        			resp.sendRedirect("/host.jsp?userName="+s_username);
        		}
        	}	
        }
    	if(finduser == false){
    		resp.sendRedirect("/home.jsp");
    	}

    }
}