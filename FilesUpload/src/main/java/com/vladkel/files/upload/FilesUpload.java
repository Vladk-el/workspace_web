package com.vladkel.files.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.catalina.connector.Request;

import com.vladkel.files.upload.beans.Fichier;
import com.vladkel.files.upload.forms.UploadForm;

public class FilesUpload extends HttpServlet{

	private static final long serialVersionUID = -5946907638271746401L;

	private static final String VIEW = "/WEB-INF/jsp/upload.jsp";
	
	private static final String PATH = "path";
	
	private static final String FILE_ATT = "fichier";
	private static final String FORM_ATT = "form";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		this.getServletContext().getRequestDispatcher( VIEW ).forward(request, response);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String path = this.getServletConfig().getInitParameter(PATH);
		
		UploadForm form = new UploadForm();
		
		Fichier fichier = form.saveFile(request, path);
		
		request.setAttribute(FORM_ATT, form);
		request.setAttribute(FILE_ATT, fichier);
		
		this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
	}

	
}
