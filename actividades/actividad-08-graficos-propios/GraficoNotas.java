import javax.swing.*;
import java.awt.*;

// Grafico avanzado con Graphics2D: barras con las notas de cinco cursos.
// Usa antialiasing, trazos de distinto grosor, degradados, una rotacion para el titulo vertical
// y pinta de rojo las notas que no llegan al minimo aprobatorio (11).
public class GraficoNotas extends JPanel {

    private String[] cursos = {"Matematica", "Fisica", "Programacion", "Ingles", "Estadistica"};
    private int[] notas = {15, 12, 18, 9, 14};
    private int notaMinima = 11;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int izquierda = 60;
        int abajo = getHeight() - 50;
        int altoGrafico = 260;
        int anchoBarra = 60;
        int separacion = 25;
        int notaMaxima = 20;

        // lineas horizontales de guia cada 5 puntos
        g2d.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f,
                new float[]{4f, 4f}, 0f));
        for (int valor = 0; valor <= notaMaxima; valor += 5) {
            int y = abajo - valor * altoGrafico / notaMaxima;
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.drawLine(izquierda, y, getWidth() - 20, y);
            g2d.setColor(Color.BLACK);
            g2d.drawString(String.valueOf(valor), izquierda - 25, y + 5);
        }

        // barras
        g2d.setStroke(new BasicStroke(1f));
        for (int i = 0; i < cursos.length; i++) {
            int alto = notas[i] * altoGrafico / notaMaxima;
            int x = izquierda + 15 + i * (anchoBarra + separacion);
            int y = abajo - alto;

            Color arriba = notas[i] >= notaMinima ? new Color(90, 150, 220) : new Color(230, 90, 90);
            Color abajoColor = notas[i] >= notaMinima ? new Color(30, 80, 150) : new Color(150, 30, 30);
            g2d.setPaint(new GradientPaint(x, y, arriba, x, abajo, abajoColor));
            g2d.fillRect(x, y, anchoBarra, alto);

            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, anchoBarra, alto);
            g2d.drawString(String.valueOf(notas[i]), x + anchoBarra / 2 - 6, y - 6);
            g2d.drawString(cursos[i], x - 2, abajo + 18);
        }

        // linea roja de la nota minima
        int yMinima = abajo - notaMinima * altoGrafico / notaMaxima;
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawLine(izquierda, yMinima, getWidth() - 20, yMinima);

        // leyenda en la esquina superior derecha
        g2d.drawLine(getWidth() - 170, 25, getWidth() - 140, 25);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Nota minima: " + notaMinima, getWidth() - 132, 29);

        // ejes
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawLine(izquierda, abajo, izquierda, abajo - altoGrafico);
        g2d.drawLine(izquierda, abajo, getWidth() - 20, abajo);

        // titulo vertical: se gira el dibujo 90 grados hacia la izquierda
        g2d.rotate(-Math.PI / 2);
        g2d.drawString("Nota (0 a 20)", -(abajo - altoGrafico / 2 + 40), 15);
        g2d.rotate(Math.PI / 2);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Grafico avanzado: notas por curso");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(520, 420);
        frame.add(new GraficoNotas());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
