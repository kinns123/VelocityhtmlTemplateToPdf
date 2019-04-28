package com.example.htmlToPdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.OutputStream;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@SpringBootApplication
@RestController
public class HtmlToPdfApplication {

	public static void main(String[] args) {
		SpringApplication.run(HtmlToPdfApplication.class, args);
	}

	@GetMapping("/genpdf")
	public String home() {

		/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();

		/* next, get the Template */
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		ve.init();
		Template t = ve.getTemplate("templates/helloworld.vm");
		/* create a context and add data */
		VelocityContext context = new VelocityContext();
		context.put("name", "World");
		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		System.out.println(writer.toString());

		String path = generatePdf(writer.toString());

		if (path != "") {
			return path;
		} else {
			return "Error occured while generating PDF";
		}

	}

	public String generatePdf(String html) {

		String pdfFilePath = "";
		PdfWriter pdfWriter = null;

		// create a new document
		Document document = new Document();
		try {

			document = new Document();
			// document header attributes
			document.addAuthor("Kiran Dhongade");
			document.addCreationDate();
			document.addProducer();
			document.addCreator("kinns123.github.io");
			document.addTitle("HTML to PDF using itext");
			document.setPageSize(PageSize.LETTER);

			pdfFilePath = "C:/Users/Kiran/Desktop/velocityToPdfGenerated/Test"
					+ System.currentTimeMillis() + ".pdf";
			FileOutputStream file = new FileOutputStream(new File(pdfFilePath));
			pdfWriter = PdfWriter.getInstance(document, file);

			// open document
			document.open();

			XMLWorkerHelper xmlWorkerHelper = XMLWorkerHelper.getInstance();
			xmlWorkerHelper.getDefaultCssResolver(true);
			xmlWorkerHelper.parseXHtml(pdfWriter, document, new StringReader(
					html));
			// close the document
			document.close();
			// close the writer
			pdfWriter.close();

			System.out.println("PDF generated successfully");

			return "PDF generated successfully and saved at "
					+ pdfFilePath.replace("/", "\\");
		} catch (Exception e) {
			e.printStackTrace();
			return pdfFilePath = "";
		}

	}

}
