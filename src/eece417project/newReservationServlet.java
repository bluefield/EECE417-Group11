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
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class newReservationServlet extends HttpServlet {

	@SuppressWarnings("deprecation")
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
             throws IOException {
		customerServlet customer = new customerServlet();
		String username = customer.getUser();

		//grab user inputs from the registration page
        String lotNumber = req.getParameter("lotnum");
        String providerName = req.getParameter("providername");
        String startTime = req.getParameter("start_time");
        String reservationLength = req.getParameter("reserveLength");
        String endTime;
        String customerUserName = username;
        String status = "Active";
        SimpleDateFormat systemTimeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat userTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        //start and end time conversion
        Date startTime_date = null;
        
		try {	
			startTime_date = systemTimeStamp.parse(startTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		startTime = userTimeFormat.format(startTime_date);
		
		//calculate end dateTime
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime_date);
		cal.add(Calendar.HOUR, Integer.valueOf(reservationLength));
		endTime = userTimeFormat.format(cal.getTime());
		
		//this query get all reservations which are made by the user 
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter customerNameFilter = new FilterPredicate("customerUserName",FilterOperator.EQUAL,customerUserName);
		Filter reservationStatus = new FilterPredicate("status",FilterOperator.EQUAL,status);
		Filter activeReservUserFilter = CompositeFilterOperator.and(customerNameFilter, reservationStatus);
		Query reservationQuery = new Query("Reservation").setFilter(activeReservUserFilter);
		PreparedQuery pq = datastore.prepare(reservationQuery);
	    
		int ActiveReservationCount = 0;
		for (Entity reservation:pq.asIterable()){
			reservation.setProperty("activeId", String.valueOf(ActiveReservationCount));
			datastore.put(reservation);
		    ActiveReservationCount++;
	    }
        Entity newReservation = new Entity("Reservation");
        newReservation.setProperty("activeId", ActiveReservationCount);
        newReservation.setProperty("lotNumber", lotNumber);
        newReservation.setProperty("providerName", providerName);
        newReservation.setProperty("startTime", startTime);
        newReservation.setProperty("reservationLength",reservationLength);
        newReservation.setProperty("endTime", endTime);
        newReservation.setProperty("customerUserName", customerUserName);
        newReservation.setProperty("status", status);
		
		datastore.put(newReservation);
		
	    resp.sendRedirect("/customer.jsp?userName="+username);
	}
}