import java.io.*;
import java.util.*;

public class Inventario {
    private List<Producto> productos;

    // Constructor
    public Inventario() {
        productos = new ArrayList<>();
        cargarDesdeArchivo();
    }

    // Cargar productos desde archivo
    public void cargarDesdeArchivo() {
        try (BufferedReader br = new BufferedReader(new FileReader("D:/Fundamentos de Programacion/Documentos_Prueba/productos.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Separar por comas
                String[] datos = line.split(",");

                // Verifica si la línea tiene los datos correctos
                if (datos.length == 5) {
                    try {
                        int idProducto = Integer.parseInt(datos[0].trim());
                        String nombreProducto = datos[1].trim(); // Nombre del producto
                        String categoria = datos[2].trim(); // Categoría del producto
                        double precio = Double.parseDouble(datos[3].trim()); // Precio
                        int cantidadDisponible = Integer.parseInt(datos[4].trim()); // Cantidad disponible

                        // Crear el producto y agregarlo a la lista
                        productos.add(new Producto(idProducto, nombreProducto, categoria, precio, cantidadDisponible));
                    } catch (NumberFormatException e) {
                        System.out.println("Error de formato en los datos: " + Arrays.toString(datos));
                    }
                } else {
                    System.out.println("Línea no válida: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // Guardar productos en archivo
    public void guardarEnArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:/Fundamentos de Programacion/Documentos_Prueba/productos.txt"))) {
            for (Producto p : productos) {
                bw.write(p.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    // Agregar un nuevo producto
    public void agregarProducto(Producto producto) {
        productos.add(producto);
        guardarEnArchivo();
    }

    // Actualizar un producto existente
    public boolean actualizarProducto(int idProducto, Producto nuevoProducto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getIdProducto() == idProducto) {
                productos.set(i, nuevoProducto);
                guardarEnArchivo();
                return true;
            }
        }
        return false;
    }

    // Eliminar un producto
    public boolean eliminarProducto(int idProducto) {
        Iterator<Producto> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Producto p = iterator.next();
            if (p.getIdProducto() == idProducto) {
                iterator.remove();
                guardarEnArchivo();
                return true;
            }
        }
        return false;
    }

    // Buscar productos por categoría
    public List<Producto> buscarPorCategoria(String categoria) {
        List<Producto> encontrados = new ArrayList<>();
        for (Producto p : productos) {
            if (p.getCategoria().equalsIgnoreCase(categoria)) {
                encontrados.add(p);
            }
        }
        return encontrados;
    }

    // Producto más caro
    public Producto productoMasCaro() {
        if (productos.isEmpty()) return null;
        Producto caro = productos.get(0);
        for (Producto p : productos) {
            if (p.getPrecio() > caro.getPrecio()) {
                caro = p;
            }
        }
        return caro;
    }

    // Calcular la cantidad total de productos por categoría
    public Map<String, Integer> cantidadPorCategoria() {
        Map<String, Integer> categorias = new HashMap<>();
        for (Producto p : productos) {
            categorias.put(p.getCategoria(), categorias.getOrDefault(p.getCategoria(), 0) + p.getCantidadDisponible());
        }
        return categorias;
    }

    // Generar reporte
    public void generarReporte() {
        double valorTotal = 0;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:/Fundamentos de Programacion/Documentos_Prueba/reporte_inventario.txt"))) {
            for (Producto p : productos) {
                double valorProducto = p.getPrecio() * p.getCantidadDisponible();
                valorTotal += valorProducto;
                bw.write(p.toString() + ", Valor: " + valorProducto);
                bw.newLine();
            }
            bw.write("Valor total del inventario: " + valorTotal);
        } catch (IOException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
    }
}
