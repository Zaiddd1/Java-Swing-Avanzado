import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.swing.*;
import java.awt.*;
import java.io.File;

// Reproductor de efectos de sonido: cada boton reproduce un archivo WAV con la clase Clip
public class ReproductorEfectos extends JFrame {

    private JLabel estado = new JLabel("Elija un efecto de sonido", SwingConstants.CENTER);

    public ReproductorEfectos() {
        super("Efectos de sonido");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel botones = new JPanel(new GridLayout(1, 3, 10, 10));
        botones.add(crearBoton("Aplausos", "../../recursos/aplausos.wav"));
        botones.add(crearBoton("Campana", "../../recursos/campana.wav"));
        botones.add(crearBoton("Explosion", "../../recursos/explosion.wav"));
        botones.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        estado.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(botones, BorderLayout.CENTER);
        add(estado, BorderLayout.SOUTH);
        setSize(430, 160);
        setLocationRelativeTo(null);
    }

    private JButton crearBoton(String nombre, String archivo) {
        JButton boton = new JButton(nombre);
        boton.addActionListener(e -> reproducir(nombre, archivo));
        return boton;
    }

    private void reproducir(String nombre, String archivo) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(archivo));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);

            // cuando el sonido termina se libera el clip y se avisa en la ventana
            clip.addLineListener(evento -> {
                if (evento.getType() == LineEvent.Type.STOP) {
                    clip.close();
                    estado.setText("Termino: " + nombre);
                }
            });

            clip.start();
            estado.setText("Reproduciendo: " + nombre);
            System.out.println("Reproduciendo " + nombre + " (" + clip.getMicrosecondLength() / 1000000.0 + " s)");
        } catch (Exception ex) {
            estado.setText("No se pudo reproducir " + nombre);
            System.out.println("Error con " + nombre + ": " + ex);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReproductorEfectos().setVisible(true));
    }
}
