package com;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.ByteArrayOutputStream;

@SuppressWarnings("serial")
public class WebProjectServlet extends HttpServlet
{
	DiskFileItemFactory factory;
	long time;
	private static final Logger log = Logger.getLogger(FileUpload.class.getName());
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException
	{
		resp.getWriter().write("hello get");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response )throws IOException
	{
		response.setContentType("text/html; charset=UTF-8");
		try 
		{
			HTMLParse html_doc = null;
			XLSXPrase xlsx_doc = null;
			
			ServletFileUpload upload = new ServletFileUpload();

			FileItemIterator iterator = upload.getItemIterator(request);
			while (iterator.hasNext()) 
			{
				FileItemStream item = iterator.next();
				InputStream stream = item.openStream();
				
				if (item.isFormField()) 
					log.warning("Got a form field: " + item.getFieldName());
				else 
					log.warning("Got an uploaded file: " + item.getFieldName() + ", name = " + item.getName());
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
				int len;
				byte[] buffer = new byte[8192];
				while ((len = stream.read(buffer, 0, buffer.length)) != -1) 
					baos.write(buffer, 0, len);
				
				
				
				if ( item.getName().contains(".html") )
					html_doc = new HTMLParse(baos.toString());
				
				if ( item.getName().contains(".xlsx") )
					xlsx_doc = new XLSXPrase(baos.toByteArray());	
			}
			
			response.setHeader("Content-Disposition","attachment;filename=file.xml");
			XMLCreate obj = new XMLCreate(html_doc.parse(), xlsx_doc.parse());
			response.getWriter().write(obj.Create());
			
		} catch (Exception e)
		{
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
	}
}
