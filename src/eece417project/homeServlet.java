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


public class homeServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
              throws IOException {
        String username =  req.getParameter("username");
        String password = req.getParameter("pswrd");
        System.out.println("test");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query userQuery = new Query("User");
        List<Entity> users = datastore.prepare(userQuery).asList(FetchOptions.Builder.withDefaults());
        for (Entity user : users) {
   		 String uname = user.getProperty("username").toString();
   		 //String markerID = user.getProperty("markerID").toString();
   		 //String date = user.getProperty("date").toString();
   		
   		 System.out.println(uname + "\n");
   		
        }
    }
}