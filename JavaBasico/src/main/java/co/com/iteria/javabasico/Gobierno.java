package co.com.iteria.javabasico;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ITERIA
 */
public class Gobierno {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Gobierno gobierno = new Gobierno();
        Scanner scaner = new Scanner(System.in);
        int opcion = 0;
        do {
            System.out.println("-------------MENU-------------");
            System.out.println("1.Leer informacion de la base de datos");
            System.out.println("2.Crear ciudadano");
            System.out.println("3.Crear inmueble para ciudadano");
            System.out.println("4.Reporte de inmueble por ciudadano");
            System.out.println("5.Salir");
            System.out.print("Digite una opcion: ");
            try {
                opcion = Integer.parseInt(scaner.next());
            } catch (NumberFormatException e) {
            }
            switch (opcion) {
                case 1:
                    gobierno.leerInfo();
                    System.out.println("------------------------------");
                    break;
                case 2:
                    System.out.println("Digite Id: ");
                    String id = scaner.next();
                    System.out.println("Digite nombres: ");
                    String nombres = scaner.next();
                    System.out.println("Digite Apellidos: ");
                    String apellidos = scaner.next();
                    Ciudadano ciudadano = new Ciudadano(id, nombres, apellidos, new ArrayList<>());
                    gobierno.crearCiudadano(ciudadano);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.err.println("Escoja una opcion valida");
                    break;
            }

        } while (true);
    }

    public void leerInfo() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Gobierno.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javabasico?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                "root",
                "");) {
            String sql = "SELECT * FROM ciudadanos";
            try (Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(sql)) {
                String out = "Id = %d,\t Nombres = %s,\t Apellido = %s\n";
                while (rs.next()) {
                    System.out.printf(out, rs.getInt(1), rs.getString(2), rs.getString(3));
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void crearCiudadano(Ciudadano ciudadano) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Gobierno.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javabasico?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");) {
            String sql = "INSERT INTO ciudadanos values (?,?,?)";
            try (PreparedStatement preparedStmt = conn.prepareStatement(sql);) {
                preparedStmt.setString(1, ciudadano.getId());
                preparedStmt.setString(2, ciudadano.getNombres());
                preparedStmt.setString(3, ciudadano.getApellidos());
                preparedStmt.execute();
                System.out.println("Ciudadano guardado correctamente!");
            }
        } catch (SQLException ex) {
            System.err.println("Ocurrio un error al guardar al ciudadano, intente nuevamente");
            System.err.println("Error insertando: " + ex.getMessage());
        }
    }

    public void crearInmueble(Ciudadano ciudadano, Inmueble inmueble) {

    }

    public String reporteInmueblesPorCiudadano(Ciudadano ciudadano) {
        return null;
    }
}
