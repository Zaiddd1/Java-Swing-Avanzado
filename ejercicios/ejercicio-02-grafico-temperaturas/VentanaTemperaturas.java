import javax.swing.*;
import java.awt.*;

// Ventana con siete campos (lunes a domingo), el boton "Mostrar Grafico" y el panel del grafico
public class VentanaTemperaturas extends JFrame {

    private static final String[] DIAS = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};

    private JTextField[] campos = new JTextField[7];
    private GraficoTemperaturas grafico = new GraficoTemperaturas();

    public VentanaTemperaturas() {
        super("Temperatura de la semana");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel entradas = new JPanel(new GridLayout(2, 7, 6, 2));
        for (int i = 0; i < 7; i++) {
            JLabel etiqueta = new JLabel(DIAS[i], SwingConstants.CENTER);
            entradas.add(etiqueta);
        }
        for (int i = 0; i < 7; i++) {
            campos[i] = new JTextField(5);
            campos[i].setHorizontalAlignment(SwingConstants.CENTER);
            entradas.add(campos[i]);
        }
        entradas.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        JButton boton = new JButton("Mostrar Grafico");
        boton.addActionListener(e -> mostrarGrafico());
        JPanel panelBoton = new JPanel();
        panelBoton.add(boton);

        JPanel arriba = new JPanel(new BorderLayout());
        arriba.add(entradas, BorderLayout.CENTER);
        arriba.add(panelBoton, BorderLayout.SOUTH);

        add(arriba, BorderLayout.NORTH);
        add(grafico, BorderLayout.CENTER);
        setSize(620, 440);
        setLocationRelativeTo(null);
    }

    private void mostrarGrafico() {
        double[] valores = new double[7];
        for (int i = 0; i < 7; i++) {
            String texto = campos[i].getText().trim().replace(',', '.');
            if (texto.isEmpty()) {
                avisar("Falta la temperatura del " + DIAS[i].toLowerCase() + ".");
                return;
            }
            try {
                valores[i] = Double.parseDouble(texto);
            } catch (NumberFormatException ex) {
                avisar("La temperatura del " + DIAS[i].toLowerCase() + " no es un numero valido.");
                return;
            }
        }
        grafico.setTemperaturas(valores);

        String resumen = "";
        for (int i = 0; i < 7; i++) {
            resumen = resumen + DIAS[i].substring(0, 3) + "=" + valores[i] + " ";
        }
        System.out.println("Grafico actualizado: " + resumen.trim());
    }

    private void avisar(String mensaje) {
        System.out.println("Dato no valido: " + mensaje);
        JOptionPane.showMessageDialog(this, mensaje, "Dato no valido", JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaTemperaturas().setVisible(true));
    }
}
