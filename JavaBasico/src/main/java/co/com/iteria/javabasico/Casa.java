/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.iteria.javabasico;

import java.math.BigDecimal;

/**
 *
 * @author ITERIA
 */
public class Casa extends Inmueble {

    public Casa(String codigoNal, String dir, double area, BigDecimal valor, int estrato) {
        this.codigoNacional = codigoNal;
        this.direccion = dir;
        this.area = area;
        this.valorComercial = valor;
        this.estrato = estrato;
        this.TIPO = TipoInmmuebleEnum.CASA;
    }

    @Override
    public BigDecimal calcularImpuesto() {
        return valorComercial.multiply(BigDecimal.valueOf(area)).multiply(BigDecimal.valueOf(0, 9));
    }

}
