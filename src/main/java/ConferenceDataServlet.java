package com;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

@SuppressWarnings("serial")
public class ConferenceDataServlet extends HttpServlet
{
	long time;
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException
	{
		resp.getWriter().write("hello get");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response )throws IOException
	{
		response.setContentType("text/html; charset=UTF-8");
		
		Element conferenceData = new Element("conferenceData");

		Element generalSettings = new Element("generalSettings");
		{
			Element event_type = new Element("event_type");
			event_type.setText(request.getParameter("event_type") );
			generalSettings.addContent(event_type);
			
			Element uri_id = new Element("uri_id");
			uri_id.setText(request.getParameter("uri_id") );
			generalSettings.addContent(uri_id);
			
			Element year = new Element("year");
			year.setText(request.getParameter("year") );
			generalSettings.addContent(year);
			
			Element month = new Element("month");
			month.setText(request.getParameter("month") );
			generalSettings.addContent(month);
			
			Element proceedings_label = new Element("proceedings_label");
			proceedings_label.setText(request.getParameter("proceedings_label") );
			generalSettings.addContent(proceedings_label);
		}
		conferenceData.addContent(generalSettings);
		
		
		
		Element conferenceEvent = new Element("conferenceEvent");
		{
			Element name = new Element("name");
			name.setText(request.getParameter("name") );
			conferenceEvent.addContent(name);
			
			Element startDate = new Element("startDate");
			startDate.setText(request.getParameter("startDate") );
			conferenceEvent.addContent(startDate);
			
			Element endDate = new Element("endDate");
			endDate.setText(request.getParameter("endDate") );
			conferenceEvent.addContent(endDate);
			
			Element acronym = new Element("acronym");
			acronym.setText(request.getParameter("acronym") );
			conferenceEvent.addContent(acronym);
			
			Element timeZone = new Element("timeZone");
			timeZone.setText(request.getParameter("timeZone") );
			conferenceEvent.addContent(timeZone);
			
			Element homepage = new Element("homepage");
			homepage.setText(request.getParameter("homepage") );
			conferenceEvent.addContent(homepage);
		
			if ( !request.getParameter("logo").equals("") )
			{
				Element logo = new Element("logo");
				logo.setText(request.getParameter("logo") );
				conferenceEvent.addContent(logo);
			}
			
			if ( !request.getParameter("locationDbpedia").equals("") )
			{
				Element locationDbpedia = new Element("locationDbpedia");
				locationDbpedia.setText(request.getParameter("locationDbpedia") );
				conferenceEvent.addContent(locationDbpedia);
			}
			
			if ( !request.getParameter("locationGeonames").equals("") )
			{
				Element locationGeonames = new Element("locationGeonames");
				locationGeonames.setText(request.getParameter("locationGeonames") );
				conferenceEvent.addContent(locationGeonames);
			}
			
			if ( !request.getParameter("geoLat").equals("") )
			{
				Element geoLat = new Element("geoLat");
				geoLat.setText(request.getParameter("geoLat") );
				conferenceEvent.addContent(geoLat);
			}
			
			if ( !request.getParameter("geoLong").equals("") )
			{
				Element geoLong = new Element("geoLong");
				geoLong.setText(request.getParameter("geoLong") );
				conferenceEvent.addContent(geoLong);
			}
		}
		conferenceData.addContent(conferenceEvent);
		
		
		CharArrayWriter caw = new CharArrayWriter();
		
		XMLOutputter outputter = new XMLOutputter();
		outputter.output(new Document(conferenceData), caw);	
		
		response.setHeader("Content-Disposition","attachment;filename=config.xml");
		response.getWriter().write(caw.toString());
	}
}
