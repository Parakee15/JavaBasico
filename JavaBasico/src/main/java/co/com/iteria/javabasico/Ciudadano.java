/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.iteria.javabasico;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author ITERIA
 */
public class Ciudadano {

    private final String id;
    private final String nombres;
    private final String apellidos;
    private final List<Inmueble> inmuebles;

    public String getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public List<Inmueble> getInmuebles() {
        return new ArrayList<>(inmuebles);
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final Ciudadano other = (Ciudadano) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ciudadano{" + "id=" + id + ", nombres=" + nombres + ", apellidos=" + apellidos + ", inmuebles=" + inmuebles + '}';
    }

    public Ciudadano(String id, String nombres, String apellidos, List<Inmueble> inmuebles) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.inmuebles = new ArrayList<>(inmuebles);
    }
}
