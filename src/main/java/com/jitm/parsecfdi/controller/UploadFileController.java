package com.jitm.parsecfdi.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jitm.parsecfdi.pojo.Cfdi;
import com.jitm.parsecfdi.pojo.CfdiExcelDto;
import com.jitm.parsecfdi.service.CfdiService;
import com.jitm.parsecfdi.service.ExcelService;

@Controller
public class UploadFileController {

	@Autowired
	CfdiService cfdiService;
	@Autowired
	ExcelService excelService;

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
	public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,HttpSession session) {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
			return "redirect:/";
		}

		Integer aTotal = 0;
		Integer aTotalError = 0;
		Integer aTotalCorrect = 0;

		ArrayList<Cfdi> cfdisFile = new ArrayList<Cfdi>();

		try (ZipInputStream zipIn = new ZipInputStream(
				new ByteArrayInputStream(file.getBytes()))) {

			for (ZipEntry entry; (entry = zipIn.getNextEntry()) != null;) {
				try{										
					System.out.println(entry.getName());
					InputStream in = new ByteArrayInputStream(zipIn.readAllBytes());
					if(entry.getName().contains(".txt")){
						cfdiService.readCfdiTxt(in);
					}else{
						cfdisFile.add(cfdiService.readCfdiTotalCheck(in,  entry.getName()));
					}
					aTotalCorrect++;
				}catch(Exception e){
					aTotalError++;
				}
				aTotal++;
			}

			System.out.println("Total rows: " + aTotal);
			redirectAttributes.addFlashAttribute("message", "Numero de total facturas " + aTotal + " numero correctas "+ aTotalCorrect+ " total error "+ aTotalError+"!");
		} catch (Exception e) {
			System.out.println("Error zip");
			redirectAttributes.addFlashAttribute("message", "Error to process de zip!");
		}
		redirectAttributes.addFlashAttribute("cfdis", cfdisFile);
		session.setAttribute("cfdis", cfdisFile);
		return "redirect:/";
	}

	@RequestMapping(value = "/filegenerator", method=RequestMethod.POST)
	public void greetingSubmit(HttpServletResponse response, HttpSession session)
	throws IOException  {

		ArrayList<Cfdi> cfdisFile = (ArrayList<Cfdi>)session.getAttribute("cfdis");
		
		response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=customers.xlsx");
        ByteArrayInputStream stream = excelService.writeExcelTotal("misfacturas",cfdisFile);
        IOUtils.copy(stream, response.getOutputStream());
	}

}
