import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

// Crea los archivos WAV que usan las actividades y los ejercicios.
// Los sonidos se calculan con formulas sencillas (senos y ruido), por eso son sinteticos.
public class GeneradorSonidos {

    static final float MUESTRAS_POR_SEGUNDO = 22050f;

    public static void main(String[] args) throws IOException {
        guardar("sample-3s.wav", tono(440, 3.0));
        guardar("campana.wav", campana());
        guardar("explosion.wav", explosion());
        guardar("aplausos.wav", aplausos());
        guardar("musica.wav", musica());
        System.out.println("Archivos WAV creados en la carpeta actual.");
    }

    // un tono puro con una subida y una bajada suaves para que no suene "click"
    static double[] tono(double frecuencia, double segundos) {
        double[] s = new double[(int) (segundos * MUESTRAS_POR_SEGUNDO)];
        for (int i = 0; i < s.length; i++) {
            double t = i / MUESTRAS_POR_SEGUNDO;
            double suavizado = Math.min(1.0, Math.min(t / 0.05, (segundos - t) / 0.05));
            s[i] = 0.6 * suavizado * Math.sin(2 * Math.PI * frecuencia * t);
        }
        return s;
    }

    // varias frecuencias que se van apagando a distinta velocidad
    static double[] campana() {
        double[] frecuencias = {523.25, 1318.5, 1569.8};
        double[] apagado = {2.5, 4.0, 6.0};
        double[] s = new double[(int) (2.0 * MUESTRAS_POR_SEGUNDO)];
        for (int i = 0; i < s.length; i++) {
            double t = i / MUESTRAS_POR_SEGUNDO;
            for (int k = 0; k < frecuencias.length; k++) {
                s[i] += Math.sin(2 * Math.PI * frecuencias[k] * t) * Math.exp(-apagado[k] * t);
            }
        }
        return normalizar(s);
    }

    // ruido que se apaga rapido mas un golpe grave de 55 Hz
    static double[] explosion() {
        Random azar = new Random(7);
        double[] s = new double[(int) (1.5 * MUESTRAS_POR_SEGUNDO)];
        double anterior = 0;
        for (int i = 0; i < s.length; i++) {
            double t = i / MUESTRAS_POR_SEGUNDO;
            double ruido = azar.nextDouble() * 2 - 1;
            anterior = anterior + 0.15 * (ruido - anterior);      // quita los agudos
            s[i] = anterior * 3 * Math.exp(-3.5 * t) + 0.8 * Math.sin(2 * Math.PI * 55 * t) * Math.exp(-5 * t);
        }
        return normalizar(s);
    }

    // muchos golpecitos de ruido de corta duracion repartidos al azar
    static double[] aplausos() {
        Random azar = new Random(11);
        double[] s = new double[(int) (2.5 * MUESTRAS_POR_SEGUNDO)];
        for (int palmada = 0; palmada < 140; palmada++) {
            int inicio = (int) ((0.1 + azar.nextDouble() * 2.0) * MUESTRAS_POR_SEGUNDO);
            double fuerza = 0.3 + azar.nextDouble() * 0.7;
            for (int j = 0; j < 600 && inicio + j < s.length; j++) {
                s[inicio + j] += fuerza * (azar.nextDouble() * 2 - 1) * Math.exp(-j / 120.0);
            }
        }
        for (int i = 0; i < s.length; i++) {
            double t = i / MUESTRAS_POR_SEGUNDO;
            s[i] *= Math.min(1.0, (2.5 - t) / 0.5);              // se apaga al final
        }
        return normalizar(s);
    }

    // melodia corta: cada nota es {frecuencia, duracion en tiempos}
    static double[] musica() {
        double do4 = 261.63, re4 = 293.66, mi4 = 329.63, fa4 = 349.23, sol4 = 392.00;
        double[][] notas = {
            {mi4, 1}, {mi4, 1}, {fa4, 1}, {sol4, 1}, {sol4, 1}, {fa4, 1}, {mi4, 1}, {re4, 1},
            {do4, 1}, {do4, 1}, {re4, 1}, {mi4, 1}, {mi4, 1.5}, {re4, 0.5}, {re4, 2},
            {mi4, 1}, {mi4, 1}, {fa4, 1}, {sol4, 1}, {sol4, 1}, {fa4, 1}, {mi4, 1}, {re4, 1},
            {do4, 1}, {do4, 1}, {re4, 1}, {mi4, 1}, {re4, 1.5}, {do4, 0.5}, {do4, 2}
        };
        double segundosPorTiempo = 0.4;
        double total = 0;
        for (double[] n : notas) {
            total += n[1] * segundosPorTiempo;
        }
        double[] s = new double[(int) (total * MUESTRAS_POR_SEGUNDO)];
        int posicion = 0;
        for (double[] n : notas) {
            int largo = (int) (n[1] * segundosPorTiempo * MUESTRAS_POR_SEGUNDO);
            for (int j = 0; j < largo && posicion + j < s.length; j++) {
                double t = j / MUESTRAS_POR_SEGUNDO;
                double duracion = largo / MUESTRAS_POR_SEGUNDO;
                double entrada = Math.min(1.0, t / 0.01);
                double salida = Math.min(1.0, (duracion - t) / 0.05);
                double onda = Math.sin(2 * Math.PI * n[0] * t) + 0.3 * Math.sin(2 * Math.PI * 2 * n[0] * t);
                s[posicion + j] = 0.5 * entrada * salida * onda * Math.exp(-0.8 * t);
            }
            posicion += largo;
        }
        return normalizar(s);
    }

    // deja el sonido mas fuerte en 0.8 para que no se corte
    static double[] normalizar(double[] s) {
        double maximo = 0;
        for (double v : s) {
            maximo = Math.max(maximo, Math.abs(v));
        }
        for (int i = 0; i < s.length; i++) {
            s[i] = s[i] / maximo * 0.8;
        }
        return s;
    }

    // convierte los valores a 16 bits y los guarda como archivo WAV
    static void guardar(String nombre, double[] muestras) throws IOException {
        byte[] datos = new byte[muestras.length * 2];
        for (int i = 0; i < muestras.length; i++) {
            short valor = (short) Math.round(muestras[i] * 32767);
            datos[2 * i] = (byte) (valor & 0xFF);
            datos[2 * i + 1] = (byte) ((valor >> 8) & 0xFF);
        }
        AudioFormat formato = new AudioFormat(MUESTRAS_POR_SEGUNDO, 16, 1, true, false);
        AudioInputStream flujo = new AudioInputStream(new ByteArrayInputStream(datos), formato, muestras.length);
        AudioSystem.write(flujo, AudioFileFormat.Type.WAVE, new File(nombre));
        System.out.println(nombre + ": " + String.format("%.1f", muestras.length / MUESTRAS_POR_SEGUNDO) + " s");
    }
}
