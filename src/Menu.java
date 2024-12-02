import java.util.*;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    private static Inventario inventario = new Inventario();

    public static void visualizarMenuInventario() {
        while (true) {
            System.out.println("----------------------- MENÚ INVENTARIO ---------------------");
            System.out.println("|                                                           |");
            System.out.println("|           Realizado por: Andrés Felipe Algarra Reyes      |");
            System.out.println("|                                                           |");
            System.out.println("|                 Ingresa la opción deseada                 |");
            System.out.println("|                                                           |");
            System.out.println("|               1. Agregar producto                         |");
            System.out.println("|               2. Actualizar producto                      |");
            System.out.println("|               3. Eliminar producto                        |");
            System.out.println("|               4. Buscar por categoría                     |");
            System.out.println("|               5. Generar reporte                          |");
            System.out.println("|               6. Cantidad de productos por categoría      |");
            System.out.println("|               7. Producto más caro                        |");
            System.out.println("|               8. Salir                                    |");
            System.out.println("|                                                           |");
            System.out.println("-------------------------------------------------------------");

            int opcion = obtenerOpcion();
            switch (opcion) {
                case 1: agregarProducto(); break;
                case 2: actualizarProducto(); break;
                case 3: eliminarProducto(); break;
                case 4: buscarPorCategoria(); break;
                case 5: generarReporte(); break;
                case 6: cantidadPorCategoria(); break;
                case 7: productoMasCaro(); break;
                case 8: System.exit(0); break;
                default: System.out.println("La opción no es válida"); break;
            }
        }
    }

    private static int obtenerOpcion() {
        System.out.print("Ingrese un numero con la opción deseada: ");
        return scanner.nextInt();
    }

    private static void agregarProducto() {
        System.out.print("ID Producto: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nombre Producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();
        System.out.print("Precio: ");
        double precio = scanner.nextDouble();
        System.out.print("Cantidad Disponible: ");
        int cantidad = scanner.nextInt();

        Producto producto = new Producto(id, nombre, categoria, precio, cantidad);
        inventario.agregarProducto(producto);
        System.out.println("Producto agregado.");
    }

    private static void actualizarProducto() {
        System.out.print("ID Producto a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nuevo Nombre Producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Nueva Categoría: ");
        String categoria = scanner.nextLine();
        System.out.print("Nuevo Precio: ");
        double precio = scanner.nextDouble();
        System.out.print("Nueva Cantidad Disponible: ");
        int cantidad = scanner.nextInt();

        Producto producto = new Producto(id, nombre, categoria, precio, cantidad);
        if (inventario.actualizarProducto(id, producto)) {
            System.out.println("Producto actualizado.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private static void eliminarProducto() {
        System.out.print("ID Producto a eliminar: ");
        int id = scanner.nextInt();
        if (inventario.eliminarProducto(id)) {
            System.out.println("Producto eliminado.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private static void buscarPorCategoria() {
        System.out.print("Categoría: ");
        scanner.nextLine();
        String categoria = scanner.nextLine();
        List<Producto> productos = inventario.buscarPorCategoria(categoria);
        if (productos.isEmpty()) {
            System.out.println("No se encontraron productos en esta categoría.");
        } else {
            productos.forEach(System.out::println);
        }
    }

    private static void generarReporte() {
        inventario.generarReporte();
        System.out.println("Reporte generado.");
    }

    private static void cantidadPorCategoria() {
        Map<String, Integer> cantidad = inventario.cantidadPorCategoria();
        cantidad.forEach((categoria, cantidadTotal) -> System.out.println(categoria + ": " + cantidadTotal));
    }

    private static void productoMasCaro() {
        Producto caro = inventario.productoMasCaro();
        if (caro != null) {
            System.out.println("El producto más caro: " + caro);
        } else {
            System.out.println("No hay productos.");
        }
    }
}
