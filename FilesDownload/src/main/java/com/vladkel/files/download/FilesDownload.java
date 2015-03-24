package com.vladkel.files.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilesDownload extends HttpServlet{

	private static final long serialVersionUID = 765706579897436267L;
	
	private static final Logger log = LoggerFactory.getLogger(FilesDownload.class);
	
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10 ko

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path = this.getServletConfig().getInitParameter("path");
		
		String askedFile = request.getPathInfo();
		// Vérification de l'existance du nom du file dans l'url
		if(askedFile == null || "/".equals(askedFile)){
			log.error("No file specified in the url.");
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		//Vérification de l'existance du file
		askedFile = URLDecoder.decode(askedFile, "UTF-8");
		File file = new File(path, askedFile);
		if(!file.exists()){
			log.error("File not found on the server.");
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		// Détermination du type du file
		String type = getServletContext().getMimeType(file.getName());
		if(type == null){
			type = "application/octet-stream";
		}
		
		/* Initialise la réponse HTTP */
		response.reset();
		response.setBufferSize(DEFAULT_BUFFER_SIZE);
		response.setContentType(type);
		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
		
		/* Prépare les flux */
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
			out = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
			
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while((length = in.read(buffer)) > 0){
				out.write(buffer, 0, length);
			}
		} finally {
			try {
				out.close();
			} catch (IOException ignore){}
			try {
				in.close();
			} catch (IOException ignore) {}
		}
		
		//super.doGet(request, response);
	}

}
