package com.jitm.parsecfdi.pojo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CfdiXmlDto {
    @JacksonXmlProperty(localName = "Version", isAttribute = true)
    private String version;

    @JacksonXmlProperty(localName = "Fecha", isAttribute = true)
    private String fecha;

    @JacksonXmlProperty(localName = "FormaPago", isAttribute = true)
    private String formaPago;

    @JacksonXmlProperty(localName = "MetodoPago", isAttribute = true)
    private String metodoPago;

    @JacksonXmlProperty(localName = "Total", isAttribute = true)
    private String total;

    @JacksonXmlProperty(localName = "SubTotal", isAttribute = true)
    private String subTotal;

    @JacksonXmlProperty(localName = "cfdi:Emisor")
    private Emisor emisor;

    @JacksonXmlProperty(localName = "cfdi:Receptor")
    private Receptor receptor;

    @JacksonXmlProperty(localName = "Impuestos")
    private Impuestos impuestos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Emisor {
        @JacksonXmlProperty(localName = "Rfc", isAttribute = true)
        private String rfc;
        @JacksonXmlProperty(localName = "Nombre", isAttribute = true)
        private String nombre;
        @JacksonXmlProperty(localName = "RegimenFiscal", isAttribute = true)
        private String regimenFiscal;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Receptor {
        @JacksonXmlProperty(localName = "Rfc", isAttribute = true)
        private String rfc;
        @JacksonXmlProperty(localName = "Nombre", isAttribute = true)
        private String nombre;
        @JacksonXmlProperty(localName = "RegimenFiscalReceptor", isAttribute = true)
        private String regimenFiscalReceptor;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Impuestos {
        @JacksonXmlProperty(localName = "TotalImpuestosTrasladados", isAttribute = true)
        private String totalImpuestosTrasladados;

        @JacksonXmlProperty(localName = "TotalImpuestosRetenidos", isAttribute = true)
        private String totalImpuestosRetenidos;
    }
}
