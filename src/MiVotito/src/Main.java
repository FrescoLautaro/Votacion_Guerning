
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Bienvenido al sistema electoral");

        System.out.println("Seleccione una opción:");
        System.out.println("1. Votar");
        System.out.println("2. Ver resultados (sólo admin)");

        int opcion = sc.nextInt();
        sc.nextLine();  // Consumir el salto de línea que queda después de nextInt()

        switch (opcion) {
            case 1:
                procesoVotacion(sc);
                break;
            case 2:
                System.out.println("Ingrese su idVoto para verificar si es administrador:");
                String idVoto = sc.nextLine();
                if (esAdmin(idVoto)) {
                    mostrarResultados();
                } else {
                    System.out.println("Acceso no autorizado.");
                }
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    public static void procesoVotacion(Scanner sc) {
        //Solicita al usuario que ingrese sus datos: idVoto, DNI y CUIL.
        //Llama a la función validarElector() para verificar si los datos ingresados son válidos y si el usuario aún no ha votado.
        //Si la validación es exitosa, muestra la ventana de votación usando mostrarVentanaVotacion().
        System.out.println("Ingrese su idVoto:");
        String idVoto = sc.nextLine();
        System.out.println("Ingrese su DNI:");
        String dni = sc.nextLine();
        System.out.println("Ingrese su CUIL:");
        String cuil = sc.nextLine();

        if (validarElector(idVoto, dni, cuil)) {
            mostrarVentanaVotacion(idVoto);
        } else {
            System.out.println("Datos inválidos o ya ha votado.");
        }
    }

    public static void mostrarVentanaVotacion(String idVoto) {
        
        //Crea y muestra una ventana gráfica (usando Swing) donde el elector puede seleccionar a su candidato preferido.
        //Al hacer clic en un candidato, se llama a registrarVotoConConfirmacion() para confirmar y registrar el voto.
        JFrame frame = new JFrame("Elecciones");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(3, 1));

        JButton opcionA = new JButton("Sergio Massa");
        JButton opcionB = new JButton("Javier Milei");
        JButton opcionC = new JButton("Damian Olaso");

        opcionA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarVotoConConfirmacion("Sergio Massa", idVoto);
            }
        });

        opcionB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarVotoConConfirmacion("Javier Milei", idVoto);
            }
        });

        opcionC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarVotoConConfirmacion("Damian Olaso", idVoto);
            }
        });

        frame.add(opcionA);
        frame.add(opcionB);
        frame.add(opcionC);

        frame.setVisible(true);
    }

    public static void marcarComoVotado(String idVoto) {
        //Marca en la base de datos que el elector con el idVoto dado ha votado.
        try (Connection conn = Conexion.getConexion()) {
            String query = "UPDATE electores SET haVotado=TRUE WHERE idVoto=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(idVoto));
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void registrarVotoConConfirmacion(String eleccion, String idVoto) {
        //Muestra un cuadro de diálogo pidiendo al usuario que confirme su elección.
        //Si el usuario confirma, registra el voto usando registrarVoto() y marca al elector como "ha votado" usando marcarComoVotado().
        
        int dialogResult = JOptionPane.showConfirmDialog(null, "¿Estás seguro de tu elección?", "Confirmar voto", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            registrarVoto(eleccion);
            marcarComoVotado(idVoto);
            System.out.println("Gracias por votar.");
            System.exit(0);  // Cierra la aplicación
        }
    }

    public static boolean validarElector(String idVoto, String dni, String cuil) {
        //Verifica si el elector con los datos ingresados (idVoto, DNI y CUIL) está en la base de datos y no ha votado aún.
        //Devuelve true si el elector es válido y aún no ha votado, false en caso contrario.
       
        try (Connection conn = Conexion.getConexion()) {
            String query = "SELECT * FROM electores WHERE idVoto=? AND dni=? AND cuil=? AND haVotado=FALSE";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(idVoto));
            ps.setString(2, dni);
            ps.setString(3, cuil);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean esAdmin(String idVoto) {
        // Verifica si el elector con el idVoto proporcionado es administrador.
        //Devuelve true si es administrador, false en caso contrario.
        
        try (Connection conn = Conexion.getConexion()) {
            String query = "SELECT isAdmin FROM electores WHERE idVoto=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(idVoto));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("isAdmin");
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void registrarVoto(String eleccion) {
        // Registra el voto del elector en la base de datos de manera anónima
        
        try (Connection conn = Conexion.getConexion()) {
            String query = "INSERT INTO votos (eleccion) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, eleccion);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void mostrarResultados() {
        //Muestra los resultados de las elecciones, es decir, cuántos votos ha recibido cada candidato.
        
        try (Connection conn = Conexion.getConexion()) {
            String query = "SELECT eleccion, COUNT(eleccion) AS votos FROM votos GROUP BY eleccion";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            System.out.println("Resultados de las elecciones:");
            while (rs.next()) {
                String candidato = rs.getString("eleccion");
                int votos = rs.getInt("votos");
                System.out.println("Candidato: " + candidato + " - Votos: " + votos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
