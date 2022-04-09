package com.jitm.parsecfdi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.jitm.parsecfdi.pojo.Cfdi;


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
