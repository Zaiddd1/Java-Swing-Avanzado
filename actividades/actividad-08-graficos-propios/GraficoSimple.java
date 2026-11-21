import javax.swing.*;
import java.awt.*;

// Grafico simple con Graphics: una casa con sol, puerta y ventana
public class GraficoSimple extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // cielo y pasto
        setBackground(new Color(200, 230, 255));
        g.setColor(new Color(110, 190, 90));
        g.fillRect(0, 230, getWidth(), getHeight() - 230);

        // sol
        g.setColor(Color.YELLOW);
        g.fillOval(300, 25, 60, 60);
        g.setColor(Color.ORANGE);
        g.drawOval(300, 25, 60, 60);
        g.drawLine(330, 10, 330, 20);
        g.drawLine(330, 90, 330, 100);

        // paredes de la casa
        g.setColor(new Color(240, 220, 170));
        g.fillRect(90, 140, 160, 120);
        g.setColor(Color.DARK_GRAY);
        g.drawRect(90, 140, 160, 120);

        // techo (un triangulo)
        int[] techoX = {75, 170, 265};
        int[] techoY = {140, 70, 140};
        g.setColor(new Color(170, 60, 50));
        g.fillPolygon(techoX, techoY, 3);

        // puerta
        g.setColor(new Color(120, 75, 40));
        g.fillRoundRect(150, 195, 40, 65, 8, 8);
        g.setColor(Color.YELLOW);
        g.fillOval(180, 228, 5, 5);

        // ventana con cruz
        g.setColor(new Color(150, 210, 240));
        g.fillRect(110, 160, 30, 30);
        g.setColor(Color.DARK_GRAY);
        g.drawRect(110, 160, 30, 30);
        g.drawLine(125, 160, 125, 190);
        g.drawLine(110, 175, 140, 175);

        // texto
        g.setColor(Color.BLACK);
        g.drawString("Mi casa dibujada con Graphics", 100, 290);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Grafico simple: una casa");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);
        frame.add(new GraficoSimple());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
