import java.util.*;
import java.sql.*;

public class Contactos {
    // FUNCIONES GLOBALES

    // Funcion del banner
    public static void banner() {
        String banner = """
                    ******                      **                       **                  \s
                   **////**                    /**                      /**                  \s
                  **    //   ******  *******  ******  ******    *****  ******  ******   ******
                 /**        **////**//**///**///**/  //////**  **///**///**/  **////** **////\s
                 /**       /**   /** /**  /**  /**    ******* /**  //   /**  /**   /**//*****\s
                 //**    **/**   /** /**  /**  /**   **////** /**   **  /**  /**   /** /////**
                  //****** //******  ***  /**  //** //********//*****   //** //******  ******\s
                   //////   //////  ///   //    //   ////////  /////     //   //////  ////// \s
                ______________________________By_Felix_Sanchez_________________________________
                """;
        System.out.println(banner);
    }

    // Funcion para limpiar consola, solo funciona si ejecutas el programa atraves de cnsola (java Main)
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // URL de la base de datos
    private static final String DB_URL = "jdbc:sqlite:contactos.db";

    // Función para ejecutar una consulta SQL y mostrar los resultados si existen
    public static void sqlQuery(String query) {
        try (Connection conexion = DriverManager.getConnection(DB_URL);
             Statement statement = conexion.createStatement()) {

            // Ejecutar la consulta
            boolean tieneResultados = statement.execute(query);

            // Si la consulta devuelve resultados (por ejemplo, SELECT)
            if (tieneResultados) {
                ResultSet resultados = statement.getResultSet();
                ResultSetMetaData metaDatos = resultados.getMetaData();
                int columnas = metaDatos.getColumnCount();

                // Imprimir encabezados
                System.out.println("=".repeat(70));
                for (int i = 1; i <= columnas; i++) {
                    System.out.printf("| %-12s ", metaDatos.getColumnLabel(i)); // Centrado
                }
                System.out.println("|");
                System.out.println("=".repeat(70));

                // Imprimir filas de resultados
                while (resultados.next()) {
                    for (int i = 1; i <= columnas; i++) {
                        System.out.printf("| %-12s ", resultados.getString(i)); // Centrado
                    }
                    System.out.println("|");
                }
                System.out.println("=".repeat(70));
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }


    // FUNCIONES UNICAS

    //Funcion buscar contactos
    public static void buscarContacto() {
        clear();
        banner();
        Scanner sc = new Scanner(System.in);

        System.out.print("Elije una opcion para buscar {1 = Por nombre / 2 = Por telefono / 3 = Por email}: ");
        int opcion = sc.nextInt();

        sc.nextLine();

        String busqueda = null;
        String query = null;

        switch (opcion) {
            case 1:
                System.out.print("Introduce una parte del nombre del contacto: ");
                busqueda = sc.nextLine().trim();
                query = "SELECT * FROM contactos WHERE LOWER(nombre) LIKE LOWER('%" + busqueda + "%')";
                sqlQuery(query);
                System.out.print("Presiona ENTER para volver al menú...");
                sc.nextLine();
                break;

            case 2:
                System.out.print("Introduce una parte del teléfono del contacto: ");
                busqueda = sc.nextLine().trim();
                query = "SELECT * FROM contactos WHERE telefono LIKE '%" + busqueda + "%'";
                sqlQuery(query);
                System.out.print("Presiona ENTER para volver al menú...");
                sc.nextLine();
                break;

            case 3:
                System.out.print("Introduce una parte del email del contacto: ");
                busqueda = sc.nextLine().trim();
                query = "SELECT * FROM contactos WHERE LOWER(email) LIKE LOWER('%" + busqueda + "%')";
                sqlQuery(query);
                System.out.print("Presiona ENTER para volver al menú...");
                sc.nextLine();
                break;

            default:
                System.out.println("Error: Opción no válida.");
                System.out.print("Presiona ENTER para volver al menú...");
                sc.nextLine();
        }
    }

    // Funcion para listar todos los contactos de la base de datos
    public static void listarContactos() {
        clear();
        banner();
        Scanner sc = new Scanner(System.in);
        sqlQuery("SELECT * from contactos;");

        System.out.print("Presiona ENTER para volver al menu...");
        sc.nextLine();

    }

    // Funcion eliminar un contacto
    public static void eliminarContacto() {
        clear();
        banner();
        Scanner sc = new Scanner(System.in);
        sqlQuery("SELECT * from contactos;");
        System.out.print("ID del contacto que quieras eliminar: ");
        int id = sc.nextInt();

        sc.nextLine();

        String query = "DELETE FROM contactos WHERE id = " + id;
        sqlQuery(query);
        System.out.println("Contacto eliminado con exito");
        System.out.print("Presiona ENTER para volver al menú...");
        sc.nextLine();


    }

    // Funcion para agregar un nuevo contacto
    public static void agragarContacto() {
        clear();
        banner();
        Scanner sc = new Scanner(System.in);
        System.out.print("Agrega el contacto en el siguiente formato (Nombre-telefono-email): ");
        String stringContacto = sc.nextLine();
        String[] contactoArray = stringContacto.split("-");
        if (contactoArray.length != 3) {
            System.out.println("Error: Formato inválido. Asegúrate de usar el formato Nombre-Telefono-Email.");
            System.out.print("Presiona ENTER para volver al menú...");
            sc.nextLine();
            return;
        }
        String nombre = contactoArray[0].trim();
        String telefono = contactoArray[1].trim();
        String email = contactoArray[2].trim();

        String query = "INSERT INTO contactos (nombre, telefono, email) VALUES ('"+nombre+"', '"+telefono+"', '"+email+"');";
        sqlQuery(query);
        System.out.println("Contacto agregado con exito");
        System.out.print("Presiona ENTER para volver al menú...");
        sc.nextLine();

    }


    // Funcion encargada de ejecutar las otras funciones
    public static void funcSelect(int opcion) {
        switch (opcion) {
            case 1:
                agragarContacto();
                break;
            case 2:
                eliminarContacto();
                break;
            case 3:
                listarContactos();
                break;
            case 4:
                    buscarContacto();
                break;
            case 5:
                System.out.println("Saliendo...");
                System.exit(0);
                break;
            default:
                System.out.println("Error: Opcion no valida.");
        }
    }

    // Funcion del menu
    public static void menu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            clear();
            banner();
            System.out.println("1. Agregar contacto");
            System.out.println("2. Eliminar contacto");
            System.out.println("3. Listar contactos existentes");
            System.out.println("4. Buscar contacto");
            System.out.println("5. Salir");
            System.out.println("");
            System.out.print("Elige una opcion con el numero: ");
            int opcion = sc.nextInt();
            funcSelect(opcion);
        }
    }

    // Funcion main
    public static void main(String[] args) {
        menu();
    }
}