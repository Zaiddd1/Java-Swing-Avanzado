import javax.swing.*;
import java.awt.*;

// Ventana para gestionar un producto: al presionar "Actualizar Producto" los datos de los campos
// pasan al modelo Producto y la etiqueta muestra la informacion del modelo de forma ordenada.
public class VentanaProducto extends JFrame {

    private Producto producto = new Producto("Teclado mecanico", 120.0, 15, "Perifericos");

    private JTextField campoNombre = new JTextField(producto.getNombre(), 18);
    private JTextField campoPrecio = new JTextField(String.valueOf(producto.getPrecio()), 18);
    private JTextField campoStock = new JTextField(String.valueOf(producto.getCantidadStock()), 18);
    private JTextField campoCategoria = new JTextField(producto.getCategoria(), 18);
    private JLabel etiquetaInfo = new JLabel();

    public VentanaProducto() {
        super("Gestion de producto");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel formulario = new JPanel(new GridLayout(4, 2, 6, 6));
        formulario.add(new JLabel("Nombre:"));
        formulario.add(campoNombre);
        formulario.add(new JLabel("Precio (S/):"));
        formulario.add(campoPrecio);
        formulario.add(new JLabel("Cantidad en stock:"));
        formulario.add(campoStock);
        formulario.add(new JLabel("Categoria:"));
        formulario.add(campoCategoria);
        formulario.setBorder(BorderFactory.createEmptyBorder(12, 12, 6, 12));

        JButton boton = new JButton("Actualizar Producto");
        boton.addActionListener(e -> actualizarProducto());
        JPanel panelBoton = new JPanel();
        panelBoton.add(boton);

        etiquetaInfo.setBorder(BorderFactory.createTitledBorder("Producto registrado"));
        mostrarProducto();

        add(formulario, BorderLayout.NORTH);
        add(panelBoton, BorderLayout.CENTER);
        add(etiquetaInfo, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }

    private void actualizarProducto() {
        String nombre = campoNombre.getText().trim();
        String categoria = campoCategoria.getText().trim();
        if (nombre.isEmpty() || categoria.isEmpty()) {
            avisar("El nombre y la categoria son obligatorios.");
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(campoPrecio.getText().trim().replace(',', '.'));
        } catch (NumberFormatException ex) {
            avisar("El precio debe ser un numero, por ejemplo 59.90");
            return;
        }
        if (precio < 0) {
            avisar("El precio no puede ser negativo.");
            return;
        }

        int stock;
        try {
            stock = Integer.parseInt(campoStock.getText().trim());
        } catch (NumberFormatException ex) {
            avisar("La cantidad en stock debe ser un numero entero.");
            return;
        }
        if (stock < 0) {
            avisar("La cantidad en stock no puede ser negativa.");
            return;
        }

        // los datos son validos: se actualiza el modelo y luego la etiqueta
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setCantidadStock(stock);
        producto.setCategoria(categoria);
        mostrarProducto();
        System.out.println("Producto actualizado: " + nombre + " | S/ " + precio + " | stock " + stock
                + " | " + categoria);
    }

    // la etiqueta se arma siempre a partir de lo que tiene el modelo
    private void mostrarProducto() {
        etiquetaInfo.setText("<html>Nombre: <b>" + producto.getNombre() + "</b><br>"
                + "Precio: S/ " + String.format("%.2f", producto.getPrecio()) + "<br>"
                + "Stock: " + producto.getCantidadStock() + " unidades<br>"
                + "Categoria: " + producto.getCategoria() + "</html>");
    }

    private void avisar(String mensaje) {
        System.out.println("Dato no valido: " + mensaje);
        JOptionPane.showMessageDialog(this, mensaje, "Dato no valido", JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaProducto().setVisible(true));
    }
}
