import javax.swing.*;
import java.awt.*;

// Binding manual en las dos direcciones entre un Libro y los campos de la ventana:
//   "Actualizar modelo" copia lo escrito en los campos hacia el Libro (vista -> modelo)
//   "Recargar vista" vuelve a escribir en los campos lo que tiene el Libro (modelo -> vista)
public class VentanaLibro extends JFrame {

    private Libro libro = new Libro("Cien anos de soledad", "Gabriel Garcia Marquez", 1967, 471);

    private JTextField campoTitulo = new JTextField(20);
    private JTextField campoAutor = new JTextField(20);
    private JTextField campoAnio = new JTextField(20);
    private JTextField campoPaginas = new JTextField(20);
    private JLabel etiquetaModelo = new JLabel();

    public VentanaLibro() {
        super("Binding manual: Libro");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel formulario = new JPanel(new GridLayout(4, 2, 6, 6));
        formulario.add(new JLabel("Titulo:"));
        formulario.add(campoTitulo);
        formulario.add(new JLabel("Autor:"));
        formulario.add(campoAutor);
        formulario.add(new JLabel("Anio:"));
        formulario.add(campoAnio);
        formulario.add(new JLabel("Paginas:"));
        formulario.add(campoPaginas);
        formulario.setBorder(BorderFactory.createEmptyBorder(12, 12, 6, 12));

        JButton botonActualizar = new JButton("Actualizar modelo");
        JButton botonRecargar = new JButton("Recargar vista");
        botonActualizar.addActionListener(e -> vistaAModelo());
        botonRecargar.addActionListener(e -> modeloAVista());
        JPanel botones = new JPanel();
        botones.add(botonActualizar);
        botones.add(botonRecargar);

        etiquetaModelo.setBorder(BorderFactory.createTitledBorder("Estado actual del modelo"));

        add(formulario, BorderLayout.NORTH);
        add(botones, BorderLayout.CENTER);
        add(etiquetaModelo, BorderLayout.SOUTH);

        modeloAVista();
        pack();
        setLocationRelativeTo(null);
    }

    // modelo -> vista
    private void modeloAVista() {
        campoTitulo.setText(libro.getTitulo());
        campoAutor.setText(libro.getAutor());
        campoAnio.setText(String.valueOf(libro.getAnio()));
        campoPaginas.setText(String.valueOf(libro.getPaginas()));
        etiquetaModelo.setText(libro.toString());
        System.out.println("Vista cargada desde el modelo: " + libro);
    }

    // vista -> modelo: primero se revisa todo y solo si esta bien se cambia el libro
    private void vistaAModelo() {
        String titulo = campoTitulo.getText().trim();
        String autor = campoAutor.getText().trim();
        if (titulo.isEmpty() || autor.isEmpty()) {
            avisar("El titulo y el autor no pueden estar vacios.");
            return;
        }
        int anio;
        int paginas;
        try {
            anio = Integer.parseInt(campoAnio.getText().trim());
            paginas = Integer.parseInt(campoPaginas.getText().trim());
        } catch (NumberFormatException ex) {
            avisar("El anio y las paginas deben ser numeros enteros.");
            return;
        }
        if (anio < 1000 || anio > 2026) {
            avisar("El anio debe estar entre 1000 y 2026.");
            return;
        }
        if (paginas <= 0) {
            avisar("Las paginas deben ser mayores que cero.");
            return;
        }

        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setAnio(anio);
        libro.setPaginas(paginas);
        etiquetaModelo.setText(libro.toString());
        System.out.println("Modelo actualizado: " + libro);
    }

    private void avisar(String mensaje) {
        System.out.println("Dato no valido: " + mensaje);
        JOptionPane.showMessageDialog(this, mensaje, "Dato no valido", JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaLibro().setVisible(true));
    }
}
