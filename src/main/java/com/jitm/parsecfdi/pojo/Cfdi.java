package com.jitm.parsecfdi.pojo;

public class Cfdi {

    @Override
    public String toString() {
        return "{" +
            " emisorNombre='" + getEmisorNombre() + "'" +
            ", emisorRegimenFiscal='" + getEmisorRegimenFiscal() + "'" +
            ", emisorRfc='" + getEmisorRfc() + "'" +
            ", descripcionFactura='" + getDescripcionFactura() + "'" +
            ", importe='" + getImporte() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", formapago='" + getFormapago() + "'" +
            ", metodoPago='" + getMetodoPago() + "'" +
            ", iva='" + getIva() + "'" +
            ", xml='" + getXml() + "'" +
            ", subtotal='" + getSubtotal() + "'" +
            ", total='" + getTotal() + "'" +
            "}";
    }

    private String emisorNombre;
    private String emisorRegimenFiscal;
    private String emisorRfc;
    private String descripcionFactura;
    private String importe;
    private String fecha;
    private String formapago;
    private String metodoPago;
    private String iva;
    private String xml;
    private String subtotal;
    private String total;

    public Cfdi() {
    }

    public Cfdi(String emisorNombre, String emisorRegimenFiscal, String emisorRfc, String descripcionFactura,
            String importe, String fecha, String formapago, String metodoPago, String iva, String xml, String subtotal,
            String total) {
        this.emisorNombre = emisorNombre;
        this.emisorRegimenFiscal = emisorRegimenFiscal;
        this.emisorRfc = emisorRfc;
        this.descripcionFactura = descripcionFactura;
        this.importe = importe;
        this.fecha = fecha;
        this.formapago = formapago;
        this.metodoPago = metodoPago;
        this.iva = iva;
        this.xml = xml;
        this.subtotal = subtotal;
        this.total = total;
    }

    public String getSubtotal() {
        return this.subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getTotal() {
        return this.total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getXml() {
        return this.xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getIva() {
        return this.iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getEmisorNombre() {
        return this.emisorNombre;
    }

    public void setEmisorNombre(String emisorNombre) {
        this.emisorNombre = emisorNombre;
    }

    public String getEmisorRegimenFiscal() {
        return this.emisorRegimenFiscal;
    }

    public void setEmisorRegimenFiscal(String emisorRegimenFiscal) {
        this.emisorRegimenFiscal = emisorRegimenFiscal;
    }

    public String getEmisorRfc() {
        return this.emisorRfc;
    }

    public void setEmisorRfc(String emisorRfc) {
        this.emisorRfc = emisorRfc;
    }

    public String getDescripcionFactura() {
        return this.descripcionFactura;
    }

    public void setDescripcionFactura(String descripcionFactura) {
        this.descripcionFactura = descripcionFactura;
    }

    public String getImporte() {
        return this.importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFormapago() {
        return this.formapago;
    }

    public void setFormapago(String formapago) {
        this.formapago = formapago;
    }

    public String getMetodoPago() {
        return this.metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

}
