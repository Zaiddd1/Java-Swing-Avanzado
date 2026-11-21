import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;

// Reproductor con tres botones: Reproducir (desde el inicio), Pausar y Reanudar (desde donde se detuvo)
public class ReproductorMusica extends JFrame {

    private Clip clip;
    private boolean pausado = false;
    private JLabel estado = new JLabel("Estado: detenido", SwingConstants.CENTER);
    private JLabel posicion = new JLabel("Posicion: 0.0 s", SwingConstants.CENTER);
    private JButton botonReproducir = new JButton("Reproducir");
    private JButton botonPausar = new JButton("Pausar");
    private JButton botonReanudar = new JButton("Reanudar");

    public ReproductorMusica() {
        super("Reproductor de musica");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        cargarPista("../../recursos/musica.wav");

        JPanel textos = new JPanel(new GridLayout(2, 1, 4, 4));
        textos.add(estado);
        textos.add(posicion);
        textos.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JPanel botones = new JPanel();
        botones.add(botonReproducir);
        botones.add(botonPausar);
        botones.add(botonReanudar);

        add(textos, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
        botonPausar.setEnabled(false);
        botonReanudar.setEnabled(false);

        botonReproducir.addActionListener(e -> reproducir());
        botonPausar.addActionListener(e -> pausar());
        botonReanudar.addActionListener(e -> reanudar());

        // actualiza la posicion cada 200 ms mientras suena la pista
        new Timer(200, e -> actualizarPosicion()).start();

        setSize(380, 180);
        setLocationRelativeTo(null);
    }

    private void cargarPista(String ruta) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(ruta));
            clip = AudioSystem.getClip();
            clip.open(audio);
            System.out.println("Pista cargada: " + ruta + " (" + clip.getMicrosecondLength() / 1000000.0 + " s)");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo abrir la pista: " + ex.getMessage());
            System.out.println("Error al abrir la pista: " + ex);
        }
    }

    // empieza desde el inicio
    private void reproducir() {
        if (clip == null) {
            return;
        }
        pausado = false;
        clip.setFramePosition(0);
        clip.start();
        estado.setText("Estado: reproduciendo");
        botonPausar.setEnabled(true);
        botonReanudar.setEnabled(false);
        System.out.println("Reproducir: desde el inicio");
    }

    // detiene el audio sin borrar la posicion en la que estaba
    private void pausar() {
        pausado = true;
        clip.stop();
        estado.setText("Estado: en pausa");
        botonPausar.setEnabled(false);
        botonReanudar.setEnabled(true);
        System.out.println("Pausar: en " + segundos(clip.getMicrosecondPosition()) + " s");
    }

    // clip.start() continua desde la posicion donde se detuvo el clip
    private void reanudar() {
        pausado = false;
        clip.start();
        estado.setText("Estado: reproduciendo");
        botonPausar.setEnabled(true);
        botonReanudar.setEnabled(false);
        System.out.println("Reanudar: continua desde " + segundos(clip.getMicrosecondPosition()) + " s");
    }

    private void actualizarPosicion() {
        if (clip == null) {
            return;
        }
        posicion.setText("Posicion: " + segundos(clip.getMicrosecondPosition()) + " s de "
                + segundos(clip.getMicrosecondLength()) + " s");
        // si el clip ya no suena y el usuario no lo pauso, es porque la pista termino
        if (!clip.isRunning() && !pausado && botonPausar.isEnabled()) {
            estado.setText("Estado: terminado");
            botonPausar.setEnabled(false);
            System.out.println("La pista termino");
        }
    }

    private String segundos(long microsegundos) {
        return String.format("%.1f", microsegundos / 1000000.0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReproductorMusica().setVisible(true));
    }
}
