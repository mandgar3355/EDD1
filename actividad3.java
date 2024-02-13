import java.io.*;
import java.util.*;

class Contacto implements Serializable {
    private String nombre;
    private String telefono;

    public Contacto(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Teléfono: " + telefono;
    }
}

public class actividad3 {
    private static final String FILE_NAME = "agenda.dat";
    private static List<Contacto> agenda = new ArrayList<>();

    public static void main(String[] args) {
        cargarAgenda();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenú:");
            System.out.println("1. Agregar nuevo contacto");
            System.out.println("2. Eliminar contacto por nombre");
            System.out.println("3. Mostrar agenda");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    agregarContacto(scanner);
                    break;
                case 2:
                    eliminarContacto(scanner);
                    break;
                case 3:
                    mostrarAgenda();
                    break;
                case 4:
                    guardarAgenda();
                    System.out.println("Saliendo del programa...");
                    System.exit(0);
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    private static void cargarAgenda() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            agenda = (List<Contacto>) inputStream.readObject();
            System.out.println("Agenda cargada correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de agenda. Se creará uno nuevo.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar la agenda: " + e.getMessage());
        }
    }

    private static void guardarAgenda() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            outputStream.writeObject(agenda);
            System.out.println("Agenda guardada correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar la agenda: " + e.getMessage());
        }
    }

    private static void agregarContacto(Scanner scanner) {
        System.out.print("Ingrese el nombre del contacto: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el teléfono del contacto: ");
        String telefono = scanner.nextLine();
        Contacto contacto = new Contacto(nombre, telefono);
        agenda.add(contacto);
        System.out.println("Contacto agregado correctamente.");
    }

    private static void eliminarContacto(Scanner scanner) {
        System.out.print("Ingrese el nombre del contacto a eliminar: ");
        String nombre = scanner.nextLine();
        boolean eliminado = false;
        Iterator<Contacto> iterator = agenda.iterator();
        while (iterator.hasNext()) {
            Contacto contacto = iterator.next();
            if (contacto.getNombre().equalsIgnoreCase(nombre)) {
                iterator.remove();
                eliminado = true;
                System.out.println("Contacto eliminado correctamente.");
                break;
            }
        }
        if (!eliminado) {
            System.out.println("No se encontró un contacto con ese nombre.");
        }
    }

    private static void mostrarAgenda() {
        if (agenda.isEmpty()) {
            System.out.println("La agenda está vacía.");
        } else {
            System.out.println("Agenda:");
            for (Contacto contacto : agenda) {
                System.out.println(contacto);
            }
        }
    }
}
