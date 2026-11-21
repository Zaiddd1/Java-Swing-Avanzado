import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;

// Mini reproductor de audio: reproduce musica.wav y muestra el tiempo y una barra de avance.
public class MiniReproductor extends JFrame {

    private Clip clip;
    private JLabel estado = new JLabel("Estado: detenido", SwingConstants.CENTER);
    private JLabel tiempo = new JLabel("00:00 / 00:00", SwingConstants.CENTER);
    private JProgressBar barra = new JProgressBar(0, 100);
    private JButton botonReproducir = new JButton("Reproducir");
    private JButton botonDetener = new JButton("Detener");
    private Timer reloj;

    public MiniReproductor() {
        super("Mini reproductor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        cargarAudio("../../recursos/musica.wav");

        JLabel titulo = new JLabel("Pista: musica.wav", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));

        JPanel centro = new JPanel(new GridLayout(4, 1, 4, 8));
        centro.add(titulo);
        centro.add(estado);
        centro.add(barra);
        centro.add(tiempo);
        centro.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 20));

        JPanel botones = new JPanel();
        botones.add(botonReproducir);
        botones.add(botonDetener);
        botonDetener.setEnabled(false);

        add(centro, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        botonReproducir.addActionListener(e -> reproducir());
        botonDetener.addActionListener(e -> detener());

        // cada 200 ms se actualiza el tiempo y la barra
        reloj = new Timer(200, e -> actualizar());
        pack();
        setSize(360, 230);
        setLocationRelativeTo(null);
    }

    private void cargarAudio(String ruta) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(ruta));
            clip = AudioSystem.getClip();
            clip.open(audio);
            System.out.println("Audio cargado: " + ruta + " (" + segundosTexto(clip.getMicrosecondLength()) + ")");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo abrir el audio: " + ex.getMessage());
            System.out.println("Error al abrir el audio: " + ex);
        }
    }

    private void reproducir() {
        if (clip == null) {
            return;
        }
        clip.setFramePosition(0);
        clip.start();
        reloj.start();
        estado.setText("Estado: reproduciendo");
        botonReproducir.setEnabled(false);
        botonDetener.setEnabled(true);
        System.out.println("Reproduciendo");
    }

    private void detener() {
        clip.stop();
        clip.setFramePosition(0);
        reloj.stop();
        estado.setText("Estado: detenido");
        barra.setValue(0);
        tiempo.setText("00:00 / " + segundosTexto(clip.getMicrosecondLength()));
        botonReproducir.setEnabled(true);
        botonDetener.setEnabled(false);
        System.out.println("Detenido");
    }

    private void actualizar() {
        long actual = clip.getMicrosecondPosition();
        long total = clip.getMicrosecondLength();
        barra.setValue((int) (actual * 100 / total));
        tiempo.setText(segundosTexto(actual) + " / " + segundosTexto(total));
        if (!clip.isRunning()) {                       // la pista llego al final
            reloj.stop();
            estado.setText("Estado: terminado");
            botonReproducir.setEnabled(true);
            botonDetener.setEnabled(false);
            System.out.println("La pista termino");
        }
    }

    private String segundosTexto(long microsegundos) {
        long segundos = microsegundos / 1000000;
        return String.format("%02d:%02d", segundos / 60, segundos % 60);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MiniReproductor().setVisible(true));
    }
}
