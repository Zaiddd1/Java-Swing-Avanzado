import javax.swing.*;
import java.awt.*;

public class AppGraficos extends JPanel {

    // Sobrescribe el metodo paintComponent para realizar dibujos
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g); // Llama al metodo paintComponent de la superclase

        // Establece el color de dibujo
        g.setColor(Color.BLUE);

        // Dibuja una linea
        g.drawLine(10, 10, 100, 100);

        // Dibuja un rectangulo
        g.setColor(Color.RED);
        g.drawRect(50, 50, 100, 50);

        // Dibuja un ovalo
        g.setColor(Color.GREEN);
        g.drawOval(150, 50, 100, 50);

        // Dibuja un circulo lleno
        g.setColor(Color.ORANGE);
        g.fillOval(200, 150, 50, 50);

        // Dibuja un texto
        g.setColor(Color.BLACK);
        g.drawString("Ejemplo de Graficos en Swing", 10, 200);
    }

    // Metodo principal para ejecutar el ejemplo
    public static void main(String[] args) {

        JFrame frame = new JFrame("Ejemplo de Graficos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Crea una instancia de nuestro panel personalizado y la agrega al marco
        AppGraficos panel = new AppGraficos();
        frame.add(panel);

        frame.setVisible(true);
    }
}
