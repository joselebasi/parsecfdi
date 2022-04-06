package com.jitm.bookkeeping.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.ParserConfigurationException;

import com.jitm.bookkeeping.web.service.InvoiceService;

@Controller
public class UploadFileController {

	@Autowired
	private InvoiceService invoiceService;
	
	@GetMapping("/")
	public String loadLoginPage(Model model) {
		model.addAttribute("name", "Baeldung Reader");
		return "index";
	}

	@GetMapping("/invoiceTable")
	public String loadMainPage() {
		return "index";
	}
	
	@PostMapping("/startSession")
	public String startSession() {
		return "redirect:/index";
	}

	@PostMapping("/uploadFile")
	public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model) {
		
		 //InputStream in = new ByteArrayInputStream(Base64.decodeBase64(request.getFile()));
		 Integer aTotal = 0;
		 
		 try (ZipInputStream zipIn = new ZipInputStream(
				 new ByteArrayInputStream(file.getBytes()))) {
					 
			 for (ZipEntry entry; (entry = zipIn.getNextEntry()) != null;) {
				 //String encodeString = encoder.encodeToString(zipIn.readAllBytes());
				 //base64Entries.put(entry.getName(), encodeString);
				 System.out.println(entry.getName());                
				 aTotal++;
			 }

			 System.out.println("Total rows: "+aTotal);
		 }catch(IOException e){
			 System.out.println("Error file");
		 }
		
		return "index";
	}

}
