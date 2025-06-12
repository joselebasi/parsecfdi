package com.jitm.parsecfdi.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cfdi {
    private String emisorNombre;
    private String emisorRegimenFiscal;
    private String emisorRfc;
    private String receptorNombre;
    private String receptorRegimenFiscal;
    private String receptorRfc;
    private String descripcionFactura;
    private String importe;
    private String fecha;
    private String formapago;
    private String metodoPago;
    private String iva;
    private String xml;
    private String subtotal;
    private String total;
    private String ivaRetenido;
    private String usoCFDI;
    
    @Override
    public String toString() {
        return "Cfdi [emisorNombre=" + emisorNombre + ", emisorRegimenFiscal=" + emisorRegimenFiscal + ", emisorRfc="
                + emisorRfc + ", receptorNombre=" + receptorNombre + ", receptorRegimenFiscal=" + receptorRegimenFiscal
                + ", receptorRfc=" + receptorRfc + ", descripcionFactura=" + descripcionFactura + ", importe=" + importe
                + ", fecha=" + fecha + ", formapago=" + formapago + ", metodoPago=" + metodoPago + ", iva=" + iva
                + ", xml=" + xml + ", subtotal=" + subtotal + ", total=" + total + ", ivaRetenido=" + ivaRetenido
                + ", usoCFDI=" + usoCFDI + "]";
    }

    

}
