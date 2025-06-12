package com.jitm.parsecfdi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jitm.parsecfdi.pojo.Cfdi;
import com.jitm.parsecfdi.pojo.CfdiXmlDto;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Service
public class CfdiService {

    public Cfdi readCfdiTotal(InputStream file, String nameFile)
            throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        doc.getDocumentElement().normalize();

        NodeList lstRoot = (NodeList) doc.getDocumentElement();
        for (int i = 0; i < lstRoot.getLength(); i++) {
            System.out.println(lstRoot.item(i).getNodeName());
        }

        String fecha = "";
        String formaPago = "";
        String metodoPago = "";
        String total = "";
        String subtotal = "";

        try {
            NodeList nlComprobante = (NodeList) doc.getElementsByTagName("cfdi:Comprobante");
            for (int i = 0; i < nlComprobante.getLength(); i++) {
                fecha = nlComprobante.item(i).getAttributes().getNamedItem("Fecha").getTextContent();
                formaPago = nlComprobante.item(i).getAttributes().getNamedItem("FormaPago").getTextContent();
                metodoPago = nlComprobante.item(i).getAttributes().getNamedItem("MetodoPago").getTextContent();
                total = nlComprobante.item(i).getAttributes().getNamedItem("Total").getTextContent();
                subtotal = nlComprobante.item(i).getAttributes().getNamedItem("SubTotal").getTextContent();
            }
        } catch (NullPointerException npe) {
            fecha = "N/A";
            formaPago = "N/A";
            metodoPago = "N/A";
            total = "0.0";
            subtotal = "0.0";
        }

        String nombre = "";
        String regimenFiscal = "";
        String rfc = "";

        NodeList nlEmisor = (NodeList) doc.getElementsByTagName("cfdi:Emisor");

        for (int i = 0; i < nlEmisor.getLength(); i++) {
            nombre = nlEmisor.item(i).getAttributes().getNamedItem("Nombre").getTextContent();
            regimenFiscal = nlEmisor.item(i).getAttributes().getNamedItem("RegimenFiscal").getTextContent();
            rfc = nlEmisor.item(i).getAttributes().getNamedItem("Rfc").getTextContent();
            NamedNodeMap nnm = nlEmisor.item(i).getAttributes();
            for (int x = 0; x < nnm.getLength(); x++) {
                Node attr = nnm.item(x);
                String name = attr.getNodeName();
                String value = attr.getNodeValue();
                System.out.println("  " + name + "=\"" + value + "\"");
            }
        }

        String iva = "";
        NodeList nlImpuestos = (NodeList) doc.getElementsByTagName("cfdi:Impuestos");
        for (int i = 0; i < nlImpuestos.getLength(); i++) {
            try {
                iva = nlImpuestos.item(i).getAttributes().getNamedItem("TotalImpuestosTrasladados").getTextContent();
            } catch (NullPointerException npe) {
                iva = "0.0";
            }
        }

        Cfdi rc = new Cfdi();
        rc.setEmisorNombre(nombre);
        rc.setEmisorRfc(rfc);
        rc.setEmisorRegimenFiscal(regimenFiscal);
        rc.setFecha(fecha);
        rc.setFormapago(formaPago);
        rc.setMetodoPago(metodoPago);
        rc.setIva(iva);
        rc.setTotal(total);
        rc.setSubtotal(subtotal);
        rc.setXml(nameFile);

        return rc;
    }

    public Cfdi readCfdiTotalCheck(InputStream file, String nameFile)
        throws ParserConfigurationException, IOException, SAXException {

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setValidating(true);
    factory.setIgnoringElementContentWhitespace(true);
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(file);
    doc.getDocumentElement().normalize();

    NodeList lstRoot = (NodeList) doc.getDocumentElement();
    for (int i = 0; i < lstRoot.getLength(); i++) {
        System.out.println(lstRoot.item(i).getNodeName());
    }

    String fecha = "N/A";
    String formaPago = "N/A";
    String metodoPago = "N/A";
    String total = "N/A";
    String subtotal = "N/A";
    
    NodeList nlComprobante = (NodeList) doc.getElementsByTagName("cfdi:Comprobante");
    for (int i = 0; i < nlComprobante.getLength(); i++) {
        fecha = nlComprobante.item(i).getAttributes().getNamedItem("Fecha") != null ?
                nlComprobante.item(i).getAttributes().getNamedItem("Fecha").getTextContent() : "N/A";
        formaPago = nlComprobante.item(i).getAttributes().getNamedItem("FormaPago") != null ?
                nlComprobante.item(i).getAttributes().getNamedItem("FormaPago").getTextContent() : "N/A";
        metodoPago = nlComprobante.item(i).getAttributes().getNamedItem("MetodoPago") != null ?
                nlComprobante.item(i).getAttributes().getNamedItem("MetodoPago").getTextContent() : "N/A";
        total = nlComprobante.item(i).getAttributes().getNamedItem("Total") != null ?
                nlComprobante.item(i).getAttributes().getNamedItem("Total").getTextContent() : "0.00";
        subtotal = nlComprobante.item(i).getAttributes().getNamedItem("SubTotal") != null ?
                nlComprobante.item(i).getAttributes().getNamedItem("SubTotal").getTextContent() : "0.00";
    }

    String nombre = "N/A";
    String regimenFiscal = "N/A";
    String rfc = "N/A";

    NodeList nlEmisor = (NodeList) doc.getElementsByTagName("cfdi:Emisor");

    for (int i = 0; i < nlEmisor.getLength(); i++) {
        nombre = nlEmisor.item(i).getAttributes().getNamedItem("Nombre") != null ?
                nlEmisor.item(i).getAttributes().getNamedItem("Nombre").getTextContent() : "N/A";
        regimenFiscal = nlEmisor.item(i).getAttributes().getNamedItem("RegimenFiscal") != null ?
                nlEmisor.item(i).getAttributes().getNamedItem("RegimenFiscal").getTextContent() : "N/A";
        rfc = nlEmisor.item(i).getAttributes().getNamedItem("Rfc") != null ?
                nlEmisor.item(i).getAttributes().getNamedItem("Rfc").getTextContent() : "N/A";
        NamedNodeMap nnm = nlEmisor.item(i).getAttributes();
        for (int x = 0; x < nnm.getLength(); x++) {
            Node attr = nnm.item(x);
            String name = attr.getNodeName();
            String value = attr.getNodeValue();
            System.out.println("  " + name + "=\"" + value + "\"");
        }
    }

    String receptorNombre = "N/A";
    String receptorRegimenFiscal = "N/A";
    String receptorRfc = "N/A";
    String usoCFDI = "N/A";

    NodeList nlReceptor = (NodeList) doc.getElementsByTagName("cfdi:Receptor");

    for (int i = 0; i < nlReceptor.getLength(); i++) {
        receptorNombre = nlReceptor.item(i).getAttributes().getNamedItem("Nombre") != null ?
                nlReceptor.item(i).getAttributes().getNamedItem("Nombre").getTextContent() : "N/A";
        receptorRegimenFiscal = nlReceptor.item(i).getAttributes().getNamedItem("RegimenFiscalReceptor") != null ?
                nlReceptor.item(i).getAttributes().getNamedItem("RegimenFiscalReceptor").getTextContent() : "N/A";
        receptorRfc = nlReceptor.item(i).getAttributes().getNamedItem("Rfc") != null ?
                nlReceptor.item(i).getAttributes().getNamedItem("Rfc").getTextContent() : "N/A";
        usoCFDI = nlReceptor.item(i).getAttributes().getNamedItem("UsoCFDI") != null ?
                nlReceptor.item(i).getAttributes().getNamedItem("UsoCFDI").getTextContent() : "N/A";
        NamedNodeMap nnm = nlReceptor.item(i).getAttributes();
        for (int x = 0; x < nnm.getLength(); x++) {
            Node attr = nnm.item(x);
            String name = attr.getNodeName();
            String value = attr.getNodeValue();
            System.out.println("  " + name + "=\"" + value + "\"");
        }
    }

    String iva = "0.00";
    String ivaRetenido = "0.00";
    NodeList nlImpuestos = (NodeList) doc.getElementsByTagName("cfdi:Impuestos");
    for (int i = 0; i < nlImpuestos.getLength(); i++) {
        iva = nlImpuestos.item(i).getAttributes().getNamedItem("TotalImpuestosTrasladados") != null ?
                nlImpuestos.item(i).getAttributes().getNamedItem("TotalImpuestosTrasladados").getTextContent() : "0.00";
        ivaRetenido = nlImpuestos.item(i).getAttributes().getNamedItem("TotalImpuestosRetenidos") != null ?
                nlImpuestos.item(i).getAttributes().getNamedItem("TotalImpuestosRetenidos").getTextContent() : "0.00";
    }

    Cfdi rc = new Cfdi();
    rc.setEmisorNombre(nombre);
    rc.setEmisorRfc(rfc);
    rc.setEmisorRegimenFiscal(regimenFiscal);
    rc.setReceptorNombre(receptorNombre);
    rc.setReceptorRfc(receptorRfc);
    rc.setReceptorRegimenFiscal(receptorRegimenFiscal);
    rc.setFecha(fecha);
    rc.setFormapago(formaPago);
    rc.setMetodoPago(metodoPago);
    rc.setIva(iva);
    rc.setTotal(total);
    rc.setSubtotal(subtotal);
    rc.setXml(nameFile);
    rc.setIvaRetenido(ivaRetenido);
    rc.setUsoCFDI(usoCFDI);

    return rc;
    }

    public void readCfdiTxt (InputStream file) throws IOException{
        BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(file));
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
