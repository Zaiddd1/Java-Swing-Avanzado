<h1 align="center">🖼️ Java Swing Avanzado — Lenguajes de Programación III</h1>

<p align="center">
  <em>Binding de datos manual, gráficos con <code>Graphics</code> y <code>Graphics2D</code>, audio con
  <code>Clip</code> y video con JavaFX dentro de ventanas Swing.</em>
</p>

<p align="center">
  <img alt="Lenguaje" src="https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk&logoColor=white">
  <img alt="Tema" src="https://img.shields.io/badge/Tema-Swing-blue">
  <img alt="Curso" src="https://img.shields.io/badge/Curso-LP%20III-6f42c1">
  <img alt="Sesión" src="https://img.shields.io/badge/Sesi%C3%B3n-10-brightgreen">
  <img alt="Estado" src="https://img.shields.io/badge/Compila-OK-success">
</p>

---

## 📌 Sobre este repositorio

Reúne el código de la **Sesión N.° 10** del curso **Lenguajes de Programación III**
(Escuela Profesional de Ingeniería de Sistemas — Universidad Católica de Santa María).
Cada carpeta es un programa independiente que abre una ventana Swing. Todo compila con **JDK 21**
y cada ventana se probó de verdad: las imágenes de [`capturas/`](capturas) son capturas reales de pantalla.

---

## 🗂️ Estructura del repositorio

```
Java-Swing-Avanzado/
├── actividades/
│   ├── actividad-01-binding-manual/          Ejemplo de la guía: Persona y campos de texto
│   ├── actividad-02-graficos-simples/        Ejemplo de la guía: Graphics (línea, rectángulo, óvalos, texto)
│   ├── actividad-03-graficos-2d/             Ejemplo de la guía: Graphics2D (traslación, rotación, escala, sesgado)
│   ├── actividad-04-grafico-barras/          Ejemplo de la guía: gráfico de barras
│   ├── actividad-05-audio/                   Ejemplo de la guía: reproducir un WAV con Clip
│   ├── actividad-06-video-javafx/            Ejemplo de la guía: video MP4 con JFXPanel (JavaFX)
│   ├── actividad-07-binding-libro/           Aplicación propia: binding manual en dos direcciones (Libro)
│   ├── actividad-08-graficos-propios/        Aplicación propia: una casa (Graphics) y notas por curso (Graphics2D)
│   └── actividad-09-mini-reproductor/        Aplicación propia: reproductor con tiempo y barra de avance
├── ejercicios/
│   ├── ejercicio-01-producto-binding/        Producto: campos de texto, botón y etiqueta
│   ├── ejercicio-02-grafico-temperaturas/    Gráfico de líneas con la temperatura de la semana
│   ├── ejercicio-03-efectos-de-sonido/       Botones Aplausos, Campana y Explosión
│   └── ejercicio-04-pausar-y-reanudar/       Reproducir, Pausar y Reanudar una pista
├── recursos/
│   ├── GeneradorSonidos.java                 Programa que crea los archivos WAV
│   ├── *.wav                                 Sonidos sintéticos generados con ese programa
│   └── flower.mp4                            Video de ejemplo (dominio público, CC0)
├── capturas/                                 Capturas reales de las ventanas
└── README.md
```

---

## ▶️ Cómo compilar y ejecutar

Se compila y se ejecuta **desde dentro de la carpeta** del programa, porque los sonidos y el video se buscan
con la ruta relativa `../../recursos/`.

```bash
cd ejercicios/ejercicio-04-pausar-y-reanudar
javac -encoding UTF-8 *.java
java ReproductorMusica
```

Las clases principales son: `App`, `AppGraficos`, `AppGraficos2D`, `AppGraficoBarras`, `AppAudio`,
`VideoExample`, `VentanaLibro`, `GraficoSimple`, `GraficoNotas`, `MiniReproductor` (actividades) y
`VentanaProducto`, `VentanaTemperaturas`, `ReproductorEfectos`, `ReproductorMusica` (ejercicios).

### Actividad 6 (video con JavaFX)

Necesita las librerías de OpenJFX, que **no están en el repositorio**. Se probó con la versión 21.0.5 de
Maven Central (`org.openjfx`: `javafx-base`, `javafx-graphics`, `javafx-media` y `javafx-swing`, clasificador
`win` en Windows). Con los cuatro `.jar` en una carpeta `lib/`:

```bash
cd actividades/actividad-06-video-javafx
javac -encoding UTF-8 --module-path "../../lib/javafx-base-21.0.5-win.jar;../../lib/javafx-graphics-21.0.5-win.jar;../../lib/javafx-media-21.0.5-win.jar;../../lib/javafx-swing-21.0.5-win.jar" --add-modules javafx.swing,javafx.media,javafx.graphics VideoExample.java
java --module-path "../../lib/javafx-base-21.0.5-win.jar;../../lib/javafx-graphics-21.0.5-win.jar;../../lib/javafx-media-21.0.5-win.jar;../../lib/javafx-swing-21.0.5-win.jar" --add-modules javafx.swing,javafx.media,javafx.graphics VideoExample
```

En Linux o macOS se usan los `.jar` con el clasificador `linux` o `mac` y `:` en lugar de `;`.

### Sonidos y video

Los archivos `.wav` de `recursos/` los crea `GeneradorSonidos.java` con fórmulas (tonos, ruido y una melodía),
por eso son **sonidos sintéticos**, no grabaciones. El video `flower.mp4` es de MDN Web Docs (dominio público, CC0).

---

## 🧠 Conceptos cubiertos

`JTextField` · `JButton` · `JLabel` · `JOptionPane` · binding de datos manual · `paintComponent` · `Graphics` ·
`Graphics2D` · antialiasing · transformaciones · `javax.sound.sampled` · `Clip` · `javax.swing.Timer` · `JFXPanel`

---

<p align="center"><sub>
Material con fines educativos — Universidad Católica de Santa María · Ingeniería de Sistemas · Lenguajes de Programación III · Sesión N.° 10
</sub></p>
