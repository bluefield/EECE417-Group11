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

		//grab user inputs from the registration page
        String lotNumber = req.getParameter("lotnum");
        String providerName = req.getParameter("providername");
        String startTime = req.getParameter("start_time");
        String reservationLength = req.getParameter("reserveLength");
        String endTime;
        String customerUserName = req.getParameter("customer_username_name");
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
		
        System.out.println(endTime);
        
        Entity reservation = new Entity("Reservation");
        reservation.setProperty("lotNumber", lotNumber);
        reservation.setProperty("providerName", providerName);
        reservation.setProperty("startTime", startTime);
        reservation.setProperty("reservationLength",reservationLength);
        reservation.setProperty("endTime", endTime);
        reservation.setProperty("customerUserName", customerUserName);
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		datastore.put(reservation);
		
	    resp.sendRedirect("/customer.jsp");
	}
}