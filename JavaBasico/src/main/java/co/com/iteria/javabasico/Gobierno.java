package co.com.iteria.javabasico;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    private Connection connection;
    private List<Ciudadano> listCiudadanos;

    public Connection getConnection() {
        return connection;
    }

    public List<Ciudadano> getListCiudadanos() {
        return new ArrayList<>(listCiudadanos);
    }

    public static void main(String[] args) {
        //instancia de gobierno
        Gobierno gobierno = new Gobierno();
        //Scanner para recibir por consola
        Scanner scaner = new Scanner(System.in);
        //Variable opcion para almacenar la opcion digitada
        int opcion = 0;
        //IF  para verificar la conexioon si no es true entonces no se ejecuta el resto del programa
        if (gobierno.crearConexion()) {
            //Menu dentro de un ciclo infinito hasta que el usuario escola la opcion salir
            do {
                System.out.println("-------------MENU-------------");
                System.out.println("1.Leer informacion de la base de datos");
                System.out.println("2.Crear ciudadano");
                System.out.println("3.Crear inmueble para ciudadano");
                System.out.println("4.Reporte de inmueble por ciudadano");
                System.out.println("5.Salir");
                System.out.print("Digite una opcion: ");

                /*Comprobamos que sea un numero y no una letra encaso de que sea una letra 
                no cambia el valor de la variable dejandola en 0 */
                try {
                    opcion = Integer.parseInt(scaner.next());
                } catch (NumberFormatException e) {
                }
                System.out.println("------------------------------");
                //Se condiciona la variable en un switch
                switch (opcion) {
                    case 1:
                        gobierno.leerInfo();
                        break;
                    case 2:
                        System.out.println("Digite Id: ");
                        String id = scaner.nextLine();
                        System.out.println("Digite nombres: ");
                        String nombres = scaner.nextLine();
                        System.out.println("Digite Apellidos: ");
                        String apellidos = scaner.nextLine();
                        //Crear objeto de ciudadano
                        Ciudadano ciudadano = new Ciudadano(id, nombres, apellidos, new ArrayList<>());
                        //Se envia el objeto creado
                        gobierno.crearCiudadano(ciudadano);
                        break;
                    case 3:
                        gobierno.leerInfo();
                        try {
                            int numero = Integer.parseInt(scaner.next());
                            //Condidicon para ver si el numero ingresado se encuentra dentro del rango del lisatdo 
                            //if (numero <= gobierno.getListCiudadanos().size() && numero < 0) {
                                
                                try {
                                        System.out.println("Que tipo de inmueble desea crear? \n 1. Casa\n 2. Apartamento\n 3. Lote");                                                                        
                                        int tipo = Integer.parseInt(scaner.next());
                                        
                                        System.out.println("Digite Codigo Nacional: ");
                                        String codigo_nacional = scaner.nextLine();                                                                
                                        System.out.println("Digite Direccion: ");
                                        String direccion = scaner.nextLine();
                                        System.out.println("Digite Are: ");
                                        String area = scaner.nextLine();
                                        System.out.println("Digite Valor Comercial: ");
                                        String valor_comercial = scaner.nextLine();
                                        System.out.println("Digite Estrato: ");
                                        String estrato;
                                        estrato = scaner.nextLine();                               
                                        
                                        switch (tipo){
                                        case 1:                                                
                                                Inmueble casa = new Casa(codigo_nacional, direccion, Double.parseDouble(area) , BigDecimal.valueOf(Double.parseDouble(valor_comercial)) , Integer.parseInt(estrato));
                                                gobierno.crearInmueble(null, casa);
                                                break;
                                        case 2:
                                                Inmueble apartamento = new Apartamento(codigo_nacional, direccion, Double.parseDouble(area) , BigDecimal.valueOf(Double.parseDouble(valor_comercial)) , Integer.parseInt(estrato));
                                                gobierno.crearInmueble(null, apartamento);
                                            break;
                                        case 3:
                                                Inmueble lote = new Lote(codigo_nacional, direccion, Double.parseDouble(area) , BigDecimal.valueOf(Double.parseDouble(valor_comercial)) , Integer.parseInt(estrato));
                                                gobierno.crearInmueble(null, lote);
                                            break;
                                        default:
                                            System.out.println("Tipo de inmueble seleccionado, no es valido");
                                            break;
                                        }
                                        
                                    } catch (NumberFormatException e) {
                                    }
                                
                               
                                
                            //} else {
                                //Si el numero ingresado esta fuera del rango arroja un excepcion
                             //   throw new IllegalArgumentException("El numero ingresado se encuentra fuera del rango, intente nuevamente");
                            //}
                        } catch (NumberFormatException e) {

                        }
                        break;
                    case 4:
                        break;
                    case 5: {
                        try {
                            gobierno.getConnection().close();
                        } catch (SQLException ex) {
                            System.err.println("Error cerrando la conexion de la base de datos");
                        }
                    }
                    System.exit(0);
                    break;
                    default:
                        System.err.println("Escoja una opcion valida");
                        break;
                }
                //opciion vuelve a tener como valor 0
                opcion = 0;
                System.out.println("------------------------------");
            } while (true);
        } else {
            System.err.println("No hay conexion con la base de datos!");
        }
    }

    public void leerInfo() {
        listCiudadanos = new ArrayList<>();
        String sql = "SELECT * FROM ciudadanos";
        try (Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(sql);) {
            String outCiudadano = "%d. Id = %d,\t Nombres = %s,\t Apellido = %s\n\n";
            String outInmueble = "Codigo Nacional = %d,\t Tipo = %s,\t Direccion = %s\t Area = %f\t Valor comercial = %f\t Estrato = %d\n";
            int cont = 0;
            while (rs.next()) {
                List<Inmueble> listInmueble = new ArrayList<>();
                //Muestra al ciudadano en pantalla                
                System.out.println("-----------------------------------------------------------------------");
                System.out.printf(outCiudadano, cont, rs.getInt(1), rs.getString(2), rs.getString(3));
                //Por cada ciudadano consulta sus inmuebles  
                String query = "SELECT * FROM inmuebles WHERE id_ciudadano = ?  ";
                try (PreparedStatement preparedStmt = connection.prepareStatement(query);) {
                    //envio parametro   
                    preparedStmt.setInt(1, rs.getInt(1));
                    //ejecuto consulta   
                    try (ResultSet rst = preparedStmt.executeQuery();) {
                        //Muestro inmuebles del ciudadano                        
                        while (rst.next()) {
                            System.out.printf(outInmueble, rst.getInt("codigo_nacional"), rst.getString("tipo"), rst.getString("direccion"), rst.getDouble("area"), rst.getDouble("valor_comercial"), rst.getInt("estrato"));
                            //declare una variable de tipo inmueble
                            Inmueble propiedad = null;
                            //Condiciono el tipo de inmueble para crearlo y añadirlo al List de inmueble 
                            switch (rst.getString("tipo")) {
                                case "CASA":
                                    propiedad = new Casa("" + rst.getInt("codigo_nacional"), rst.getString("direccion"), rst.getDouble("area"), rst.getBigDecimal("valor_comercial"), rst.getInt("estrato"));
                                    break;
                                case "APTO":
                                    propiedad = new Apartamento("" + rst.getInt("codigo_nacional"), rst.getString("direccion"), rst.getDouble("area"), rst.getBigDecimal("valor_comercial"), rst.getInt("estrato"));
                                    break;
                                case "LOTE":
                                    propiedad = new Lote("" + rst.getInt("codigo_nacional"), rst.getString("direccion"), rst.getDouble("area"), rst.getBigDecimal("valor_comercial"), rst.getInt("estrato"));
                                    break;
                                default:
                                    break;
                            }
                            //añado el inmueble al list de inmueble que sera agregado al ciudadano
                            listInmueble.add(propiedad);
                        }
                    }
                }
                listCiudadanos.add(cont, new Ciudadano("" + rs.getInt(1), rs.getString(2), rs.getString(3), listInmueble));
                cont++;
                System.out.println("\n-----------------------------------------------------------------------");
            }
        } catch (SQLException ex) {
            System.err.println("Error consultando la base de datos" + ex.getMessage());
        }
    }

    public void crearCiudadano(Ciudadano ciudadano) {
        String sql = "INSERT INTO ciudadanos values (?,?,?)";
        try (PreparedStatement preparedStmt = connection.prepareStatement(sql);) {
            //pasamos los parametros  para el preparedStmt
            preparedStmt.setString(1, ciudadano.getId());
            preparedStmt.setString(2, ciudadano.getNombres());
            preparedStmt.setString(3, ciudadano.getApellidos());
            preparedStmt.execute();
            System.out.println("Ciudadano guardado correctamente!");
        } catch (SQLException ex) {
            System.err.println("Ocurrio un error al guardar al ciudadano, intente nuevamente");
        }
    }

    public void crearInmueble(Ciudadano ciudadano, Inmueble inmueble) {
        System.out.println(inmueble.getTIPO().name());
        String sql = "INSERT INTO inmueble values (?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStmt = connection.prepareStatement(sql);) {
            //pasamos los parametros  para el preparedStmt
            preparedStmt.setString(1, inmueble.getCodigoNacional());
            //preparedStmt.setString(2, inmueble.get);
            preparedStmt.setString(3, inmueble.getTIPO().name());
            preparedStmt.setString(4, inmueble.getDireccion());
            preparedStmt.setDouble(5, inmueble.getArea());
            preparedStmt.setBigDecimal(6, inmueble.getValorComercial());
            preparedStmt.setInt(7, inmueble.getEstrato());
            preparedStmt.execute();
            System.out.println("Inmueble guardado correctamente!");
        } catch (SQLException ex) {
            System.err.println("Ocurrio un error al guardar al inmueble, intente nuevamente");
        }

    }

    public String reporteInmueblesPorCiudadano(Ciudadano ciudadano) {
        return null;
    }

    /*Metodo para crear la conexion
    Retorna TRUE si logra establecer la conexion y FLASE si falla    
     */
    public boolean crearConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //al realizar la conexion con la libreria mas reciente lanzaba error de  "Timezone" por eso se le pasan esos param a la conexion 
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javabasico?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root",
                    "");
            return true;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            return false;
        }
    }
}
