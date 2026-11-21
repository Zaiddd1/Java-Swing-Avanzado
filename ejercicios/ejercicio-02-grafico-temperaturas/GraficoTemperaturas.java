import javax.swing.*;
import java.awt.*;

// Panel donde se dibuja el grafico de lineas de la temperatura de la semana
public class GraficoTemperaturas extends JPanel {

    private static final String[] DIAS = {"Lun", "Mar", "Mie", "Jue", "Vie", "Sab", "Dom"};
    private double[] temperaturas;            // null mientras no se haya presionado "Mostrar Grafico"

    public void setTemperaturas(double[] temperaturas) {
        this.temperaturas = temperaturas;
        repaint();                            // pide que se vuelva a llamar a paintComponent
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int izquierda = 50;
        int derecha = getWidth() - 30;
        int arriba = 30;
        int abajo = getHeight() - 40;

        // ejes
        g2d.setColor(Color.BLACK);
        g2d.drawLine(izquierda, arriba, izquierda, abajo);
        g2d.drawLine(izquierda, abajo, derecha, abajo);
        g2d.drawString("Temperatura (C)", 5, 18);

        if (temperaturas == null) {
            g2d.setColor(Color.GRAY);
            g2d.drawString("Escriba las temperaturas y presione \"Mostrar Grafico\"", izquierda + 20, getHeight() / 2);
            return;
        }

        // rango del eje vertical: del minimo al maximo, con un poco de margen
        double minimo = temperaturas[0];
        double maximo = temperaturas[0];
        for (double t : temperaturas) {
            minimo = Math.min(minimo, t);
            maximo = Math.max(maximo, t);
        }
        minimo = Math.floor(minimo) - 2;
        maximo = Math.ceil(maximo) + 2;

        // marcas del eje vertical (5 marcas) y lineas guia
        for (int i = 0; i <= 4; i++) {
            double valor = minimo + (maximo - minimo) * i / 4;
            int y = convertirY(valor, minimo, maximo, arriba, abajo);
            g2d.setColor(new Color(225, 225, 225));
            g2d.drawLine(izquierda + 1, y, derecha, y);
            g2d.setColor(Color.BLACK);
            g2d.drawString(String.format("%.0f", valor), izquierda - 30, y + 5);
        }

        // posicion de cada dia en el eje horizontal
        int separacion = (derecha - izquierda) / DIAS.length;
        int[] xs = new int[DIAS.length];
        int[] ys = new int[DIAS.length];
        for (int i = 0; i < DIAS.length; i++) {
            xs[i] = izquierda + separacion / 2 + i * separacion;
            ys[i] = convertirY(temperaturas[i], minimo, maximo, arriba, abajo);
            g2d.setColor(Color.BLACK);
            g2d.drawString(DIAS[i], xs[i] - 10, abajo + 20);
        }

        // linea que une los puntos
        g2d.setColor(new Color(40, 100, 200));
        g2d.setStroke(new BasicStroke(2.5f));
        for (int i = 0; i < DIAS.length - 1; i++) {
            g2d.drawLine(xs[i], ys[i], xs[i + 1], ys[i + 1]);
        }

        // puntos y valores
        g2d.setStroke(new BasicStroke(1f));
        for (int i = 0; i < DIAS.length; i++) {
            g2d.setColor(Color.RED);
            g2d.fillOval(xs[i] - 5, ys[i] - 5, 10, 10);
            g2d.setColor(Color.BLACK);
            g2d.drawString(String.valueOf(temperaturas[i]), xs[i] - 12, ys[i] - 10);
        }
    }

    // convierte una temperatura en la posicion vertical (y) dentro del panel
    private int convertirY(double valor, double minimo, double maximo, int arriba, int abajo) {
        return (int) (abajo - (valor - minimo) / (maximo - minimo) * (abajo - arriba));
    }
}
