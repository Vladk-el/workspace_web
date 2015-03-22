package com.vladkel.files.upload.forms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.vladkel.files.upload.beans.Fichier;

public class UploadForm {

	private static final String FIELD_DESCRIPTION = "description";
	private static final String FIELD_FILE = "fichier";
	private static final String HEADER_CONTENT_DISPOSITION = "content-disposition";
	private static final String HEADER_FILENAME = "filename";
	private static final int BUFFER_SIZE = 10240;
	private static final int DESCRIPTION_MIN_SIZE = 5;
	
	private String result;
	private Map<String, String> errors = new HashMap<String, String>();
	
	public String getResult(){
		return result;
	}
	
	public Map<String, String> getErrors(){
		return errors;
	}
	
	public Fichier saveFile(HttpServletRequest request, String path){
		Fichier fichier = new Fichier();
		
		String description = getFieldValue(request, FIELD_DESCRIPTION);
		
		String fileName = null;
		InputStream fileContent = null;
		
		try {
			Part part = request.getPart(FIELD_FILE);
			fileName = getFileName(part);
			
			if(fileName != null && !fileName.isEmpty()){
				/* Fix pour IE */
					if(fileName.lastIndexOf('/') != -1)
						fileName = fileName.substring(fileName.lastIndexOf('/')+1).substring(fileName.lastIndexOf('\\'+1));
				/* Récupération du contenu du fichier */
				fileContent = part.getInputStream();
			}
			
		} catch(IllegalStateException e){
			e.printStackTrace();
			setError(FIELD_FILE, "Les données envoyées sont trop volumineuses.");
		} catch(IOException e){
			e.printStackTrace();
			setError(FIELD_FILE, "Erreur de configuration du serveur.");
		} catch(ServletException e){
			e.printStackTrace();
			setError(FIELD_FILE, "Ce type de requête n'est pas supporté, merci d'utiliser le formulaire pour envoyer votre fichier.");
		}
		
		if(errors.isEmpty()){
			try {
				descriptionValidation(description);
			} catch(Exception e){
				setError(FIELD_DESCRIPTION, e.getMessage());
			}
			fichier.setDescription(description);
			
			try {
				fileValidation(fileName, fileContent);
			} catch(Exception e){
				setError(FIELD_FILE, e.getMessage());
			}
			fichier.setName(fileName);
		}
		
		if(errors.isEmpty()){
			try {
				writeFile(fileContent, fileName, path);
			} catch(Exception e){
				setError(FIELD_FILE, "Erreur lors de l'écriture du fichier sur le disque.");
			}
		}
		
		if(errors.isEmpty()){
			result = "Succès de l'envoi du fichier.";
		} else {
			result = "Echec de l'envoi du fichier.";
		}
		
		return fichier;
	}
	
	private void descriptionValidation(String description) throws Exception {
		if(description != null){
			if(description.length() < DESCRIPTION_MIN_SIZE){
				throw new Exception("La phrase de description du fichier doit contenir au minimum 5 cracatères.");
			}
		}
		else {
			throw new Exception("Merci de compléter la description du fichier.");
		}
	}
	
	private void fileValidation(String fileName, InputStream fileContent) throws Exception {
		if(fileName == null || fileContent == null){
			throw new Exception("Merci de sélectionner un fichier à envoyer.");
		}
	}
	
	private void setError(String key, String value){
		errors.put(key, value);
	}
	
	private void writeFile(InputStream content, String fileName, String path) throws IOException {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		
		try{
			in = new BufferedInputStream(content, BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(new File(path + fileName)), BUFFER_SIZE);
			
			byte[] tampon = new byte[BUFFER_SIZE];
			int size;
			while((size = in.read(tampon)) > 0){
				out.write(tampon, 0, size);
			}
		} finally {
			try{
				out.close();
			} catch(IOException ignore){}
			try{
				in.close();
			} catch(IOException ignore){}
		}
	}
	
	private static String getFieldValue(HttpServletRequest request, String fieldName){
		String value = request.getParameter(fieldName);
		if(value == null || value.trim().length() == 0){
			return null;
		}
		return value;
	}
	
	private static String getFileName(Part part){
		for(String contentDisposition : part.getHeader(HEADER_CONTENT_DISPOSITION).split(";")){
			if(contentDisposition.trim().startsWith(HEADER_FILENAME)){
				return contentDisposition.substring(contentDisposition.indexOf('=')+1).trim().replaceAll("\"", "");
			}
		}
		return null;
	}
	
	
	
	
	
}
