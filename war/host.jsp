<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.PreparedQuery" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="java.io.IOException" %>


<%@ page import= "com.google.appengine.api.datastore.Query.Filter"%>
<%@ page import= "com.google.appengine.api.datastore.Query.FilterPredicate"%>
<%@ page import= "com.google.appengine.api.datastore.Query.FilterOperator"%>
<%@ page import= "com.google.appengine.api.datastore.Query.CompositeFilter"%>
<%@ page import= "com.google.appengine.api.datastore.Query.CompositeFilterOperator"%>
<%@ page import= "com.google.appengine.api.datastore.Query"%>









<!DOCTYPE html> 
<html>
  <head>
     <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
	 <script>
		function initialize() {		
			
		  var mapOptions = {
		    zoom: 12,
		  };
		  map = new google.maps.Map(document.getElementById('googleMap'),
		      mapOptions);
		  
		  // Try W3C Geolocation (Preferred)
		  if(navigator.geolocation) {
		    browserSupportFlag = true;
		    navigator.geolocation.getCurrentPosition(function(position) {
		      initialLocation = new google.maps.LatLng(position.coords.latitude,position.coords.longitude);
		      map.setCenter(initialLocation);
		    }, function() {
		      handleNoGeolocation(browserSupportFlag);
		    });
		  }
		  // Browser doesn't support Geolocation
		  else {
		    browserSupportFlag = false;
		    handleNoGeolocation(browserSupportFlag);
		  }

		  
		  var parkadeInfo = '<div class = "MarkerPopUp" style="width: 300px; height:300px;">'+
		  '<div class = "parkingLots" style="float:left; width: 70%;"><input type="radio" name="parkingLot" value="Lot23" checked>Lot23<br>'+
		  
		  '<input type="radio" name="parkingLot" value="Lot24" >Lot24</div>'+
		  '<div class = "AddParkingLotButton" style="float:left; width: 30%;"><button onclick="onClickAddButton()">Add a Parking Lot</button></div>'+
		  '</div>';
		  var infowindow = new google.maps.InfoWindow({
			  	content: parkadeInfo
		  });
		  
		 
		  var parkade = new google.maps.Marker({
			    position: newloc,
			    map: map
			});
		
		  
		  google.maps.event.addListener(parkade, 'click', function() {
			    infowindow.open(map,parkade);
		  });
		}
		
		function getSelectedLotValue(){
			var inputs = document.getElementsByName('parkingLot');
			for (var i = 0; i < inputs.length; i++) {
				if (inputs[i].checked) {
	                return inputs[i].value;
	            }
	        }
		}
		

		function onClickAddButton(){
			var selectedLot = getSelectedLotValue();
			document.location.href = '/AddParkingSlot.jsp?selectedLot='+ selectedLot;
		}
		/*
		function getLocations(lat, lng, name){
			//alert(lat+" "+lng+" "+ name);
			
			
			list.push({
				latitude: lat,
				longitude: lng,
				userName: name
			});
		}
		
		function addMarkers(pos, title){
			var marker = new google.maps.Marker({
			    position: pos,
			    map: map,
			    title: title
			});
		}
		
		*/
		function handleNoGeolocation(errorFlag) {
		    if (errorFlag == true) {
		      initialLocation = vancouver;
		    } else { 
		      initialLocation = vancouver;
		    }
		  }
		
			
		

  </script>
  </head>
  <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
  
 
 <%
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
   
    Query ParkadeQuery = new Query("Parkade");
    PreparedQuery pq = datastore.prepare(ParkadeQuery);
    for (Entity u1:pq.asIterable()){
        	String parkade_name = u1.getProperty("parkade_name").toString();
        	String parkade_lng = u1.getProperty("parkade_lng").toString();
        	String parkade_lat = u1.getProperty("parkade_lat").toString();
    		String parkade_des= u1.getProperty("parkade_des").toString();
    		request.setAttribute("parkade_lng", parkade_lng);		

    }	      
%>

    
<body>
	<div>
		<div id="header">
			<h1 style="margin-bottom:0;">OurParkingSpot - Host Page</h1>
			<button id="AddParkadeButton" type="button" onclick="window.location.href='AddParkade.jsp'">Add Parkade</button>	
		</div>
		<div style="float:left; width: 30%; height: 800px;">
	
    	</div>
    	<div id="googleMap" style="float:left; width:70%; height:800px;"></div>
    	
    	<script>
	    	var map;
		 	var initialLocation;
		 	//var vancouver = new google.maps.LatLng(49.260665599999996, -123.10012559999998);
		 	//var e_parkade_lng = ${fn:escapeXml(parkade_lat)};
			// var e_parkade_lat = ${fn:escapeXml(parkade_lng)};
		 	//console.log(e_parkade_lat);
		 	console.log(parkade_lng);
		 	console.log("i m coming");
		    var newloc= new google.maps.LatLng(e_parkade_lng, e_parkade_lat);
		 	var browserSupportFlag =  new Boolean();
      		google.maps.event.addDomListener(window, 'load', initialize);
    	</script>
    </div>
 


 </body>
  
</html>
