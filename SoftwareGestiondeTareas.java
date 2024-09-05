import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SoftwareGestiondeTareas {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorDeTareas gestor = new GestorDeTareas();
        
        while (true) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Crear una nueva tarea");
            System.out.println("2. Editar una tarea existente");
            System.out.println("3. Cambiar el estado de una tarea");
            System.out.println("4. Eliminar una tarea");
            System.out.println("5. Ver todas las tareas");
            System.out.println("6. Ver tareas pendientes");
            System.out.println("7. Ver tareas completadas");
            System.out.println("8. Salir");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); 
            
            switch(opcion) {
                case 1:
                    try {
                        System.out.print("Ingrese el título de la tarea: ");
                        String titulo = scanner.nextLine();
                        System.out.print("Ingrese la descripción de la tarea: ");
                        String contenido = scanner.nextLine();
                        System.out.print("Ingrese la fecha de vencimiento (yyyy-MM-dd): ");
                        String fechaVencimiento = scanner.nextLine();
                        
                        
                        Date fechaVenc = sdf.parse(fechaVencimiento);
                        
                        
                        Tarea nuevaTarea = new Tarea(gestor.obtenerProximoId(), titulo, contenido, fechaVenc);
                        gestor.añadirTarea(nuevaTarea);
                        
                        System.out.println("La tarea fue creada exitosamente.");
                    } catch (ParseException e) {
                        System.out.println("El formato de fecha es incorrecto. Use el formato yyyy-MM-dd.");
                    }
                    break;
                case 2:
                    try {
                        System.out.print("Ingrese el ID de la tarea a editar: ");
                        int idEditar = scanner.nextInt();
                        scanner.nextLine(); // Consumir la nueva línea
                        
                        Tarea tareaEditar = gestor.buscarTareaPorId(idEditar);
                        if (tareaEditar != null) {
                            System.out.print("Ingrese el nuevo título: ");
                            String nuevoTitulo = scanner.nextLine();
                            System.out.print("Ingrese la nueva descripción: ");
                            String nuevaDescripcion = scanner.nextLine();
                            System.out.print("Ingrese el nuevo estado: ");
                            String nuevoEstado = scanner.nextLine();
                            System.out.print("Ingrese la nueva fecha de vencimiento (yyyy-MM-dd): ");
                            String nuevaFechaVencimiento = scanner.nextLine();
                            
                            Date nuevaFecha = sdf.parse(nuevaFechaVencimiento);
                            tareaEditar.editarTarea(nuevoTitulo, nuevaDescripcion, nuevoEstado, nuevaFecha);
                            System.out.println("la tarea fue editada exitosamente.");
                        } else {
                            System.out.println("La tarea no fue encontrada.");
                        }
                    } catch (ParseException e) {
                        System.out.println("El formato de fecha es incorrecto. Use el formato yyyy-MM-dd.");
                    }
                    break;
                case 3:
                    System.out.print("Ingrese el ID de la tarea a cambiar de estado: ");
                    int idCambiar = scanner.nextInt();
                    scanner.nextLine(); 
                    
                    Tarea tareaCambiar = gestor.buscarTareaPorId(idCambiar);
                    if (tareaCambiar != null) {
                        System.out.print("Ingrese el nuevo estado: ");
                        String nuevoEstado = scanner.nextLine();
                        tareaCambiar.cambiarEstado(nuevoEstado);
                        System.out.println("El Estado de la tarea fue cambiado exitosamente.");
                    } else {
                        System.out.println("La Tarea no fue encontrada.");
                    }
                    break;
                case 4:
                    System.out.print("Ingrese el ID de la tarea a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    scanner.nextLine(); 
                    
                    gestor.eliminarTarea(idEliminar);
                    System.out.println("La Tarea fue eliminada exitosamente.");
                    break;
                case 5:
                    System.out.println("Todas las tareas:");
                    List<Tarea> todasLasTareas = gestor.obtenerTareas();
                    imprimirTareas(todasLasTareas);
                    break;
                case 6:
                    System.out.println("Tareas pendientes:");
                    List<Tarea> tareasPendientes = gestor.obtenerTareasPorEstado("Pendiente");
                    imprimirTareas(tareasPendientes);
                    break;
                case 7:
                    System.out.println("Tareas completadas:");
                    List<Tarea> tareasCompletadas = gestor.obtenerTareasPorEstado("Completada");
                    imprimirTareas(tareasCompletadas);
                    break;
                case 8:
                    System.out.println("Saliendo del sistema...");
                    scanner.close();
                    return;
                default:
                    System.out.println("La Opción no es válida, intente de nuevo.");
            }
        }
    }

    
    public static void imprimirTareas(List<Tarea> tareas) {
        for (Tarea tarea : tareas) {
            System.out.println("ID: " + tarea.getId());
            System.out.println("Título: " + tarea.getNombre());
            System.out.println("Descripción: " + tarea.getDescripcion());
            System.out.println("Estado: " + tarea.getEstado());
            System.out.println("Fecha de Creación: " + tarea.getFechaCreacion());
            System.out.println("Fecha de Vencimiento: " + tarea.getFechaVencimiento());
            System.out.println("-----------");
        }
    }
}


class Tarea {
    
    private int id;
    private String nombre;
    private String descripcion;
    private String estado;
    private Date fechaCreacion;
    private Date fechaVencimiento;

   
    public Tarea(int id, String nombre, String descripcion, Date fechaVencimiento) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = "Pendiente";
        this.fechaCreacion = new Date(); 
        this.fechaVencimiento = fechaVencimiento;
    }

    
    public void editarTarea(String nombre, String descripcion, String estado, Date fechaVencimiento) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaVencimiento = fechaVencimiento;
    }

    public void cambiarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

   
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }
}


class GestorDeTareas {
   
    private List<Tarea> listaTareas;

    public GestorDeTareas() {
        this.listaTareas = new ArrayList<>();
    }


    public void añadirTarea(Tarea tarea) {
        listaTareas.add(tarea);
    }

    public List<Tarea> obtenerTareas() {
        return listaTareas;
    }

    public Tarea buscarTareaPorId(int id) {
        for (Tarea tarea : listaTareas) {
            if (tarea.getId() == id) {
                return tarea;
            }
        }
        return null;
    }

    public void eliminarTarea(int id) {
        Tarea tarea = buscarTareaPorId(id);
        if (tarea != null) {
            listaTareas.remove(tarea);
        }
    }

    public List<Tarea> obtenerTareasPorEstado(String estado) {
        List<Tarea> tareasPorEstado = new ArrayList<>();
        for (Tarea tarea : listaTareas) {
            if (tarea.getEstado().equals(estado)) {
                tareasPorEstado.add(tarea);
            }
        }
        return tareasPorEstado;
    }

   
    public int obtenerProximoId() {
        return listaTareas.size() + 1;
    }
}
