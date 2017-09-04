/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.iteria.javabasico;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author ITERIA
 */
public abstract class Inmueble {

    protected String codigoNacional;
    protected String direccion;
    protected double area;
    protected BigDecimal valorComercial;
    protected int estrato;
    protected TipoInmmuebleEnum tipo;

    public String getCodigoNacional() {
        return codigoNacional;
    }

    public String getDireccion() {
        return direccion;
    }

    public double getArea() {
        return area;
    }

    public BigDecimal getValorComercial() {
        return valorComercial;
    }

    public int getEstrato() {
        return estrato;
    }

    public TipoInmmuebleEnum getTipo() {
        return tipo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.codigoNacional);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Inmueble other = (Inmueble) obj;
        if (!Objects.equals(this.codigoNacional, other.codigoNacional)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Inmueble{" + "codigoNacional=" + codigoNacional + ", direccion=" + direccion + ", area=" + area + ", valorComercial=" + valorComercial + ", estrato=" + estrato + ", tipo=" + tipo + '}';
    }

    public abstract BigDecimal calcularImpuesto();
}
