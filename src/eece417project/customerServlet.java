package eece417project;


import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class customerServlet extends HttpServlet {
	String l_username = homeServlet.s_username;
	String l_password = homeServlet.s_password;
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
		
		//grab user inputs from the registration page
        String rowNumber = req.getParameter("rowCancel_name");
        String activeStatus = "Active";
        String cencelStatus = "Cancel";
      //this query get all reservations which are made by the user 
      		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      		Filter customerNameFilter = new FilterPredicate("customerUserName",FilterOperator.EQUAL,l_username);
      		Filter reservationStatus = new FilterPredicate("status",FilterOperator.EQUAL,activeStatus);
      		Filter reservationId = new FilterPredicate("activeId", FilterOperator.EQUAL,rowNumber);
      		Filter activeReservUserFilterWithId = CompositeFilterOperator.and(customerNameFilter, reservationStatus,reservationId);
      		Query reservationQuery = new Query("Reservation").setFilter(activeReservUserFilterWithId);
      		PreparedQuery pq = datastore.prepare(reservationQuery);
      	    
      		for (Entity reservation:pq.asIterable()){
      			reservation.setProperty("status", cencelStatus);
      			System.out.println(reservation.getProperty("status"));
      			datastore.put(reservation);
      	    }
             
      		//update those id
      		Filter activeReservUserFilterWithoutId = CompositeFilterOperator.and(customerNameFilter, reservationStatus);
    		reservationQuery = new Query("Reservation").setFilter(activeReservUserFilterWithoutId);
    		pq = datastore.prepare(reservationQuery);
    	    
    		int ActiveReservationCount = 0;
    		for (Entity reservation:pq.asIterable()){
    			reservation.setProperty("activeId", String.valueOf(ActiveReservationCount));
    			datastore.put(reservation);
    		    ActiveReservationCount++;
    	    }
	    resp.sendRedirect("/customer.jsp?userName="+l_username);
	}
	
	public String getUser(){
		return l_username;
	}
	
}

