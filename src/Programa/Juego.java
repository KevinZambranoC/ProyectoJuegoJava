package Programa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Juego extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private int puntos = 0;
    private int tiempoRestante = 120; // 2 minutos en segundos
    private Personaje personaje;
    private Flecha[] flechas = new Flecha[30]; // Arreglo estático de flechas
    private Lobo[] lobos = new Lobo[20]; // Aumentamos el arreglo de lobos para gestionar más lobos
    private int indiceFlecha = 0; // Índice para manejar flechas
    private int contadorTiempo = 0; // Contador para el tiempo de aparición de nuevos lobos
    private int contadorGeneracion = 0; // Contador para controlar generación de lobos
    private final int intervaloGeneracionLobos = 25; // Intervalo para generar lobos (en segundos)
    
    // Cargar imágenes
    private Image fondo;
    private Image ascensorImg;
    private Image personajeImg;
    private Image[] loboImgs = new Image[4]; // Imágenes para lobos de diferentes colores
    private Image flechaImg;

    public Juego() {
        setPreferredSize(new Dimension(800, 600)); // Tamaño del escenario
    setBackground(Color.WHITE);
    personaje = new Personaje(650, 175); // Posición inicial del personaje en el ascensor
    timer = new Timer(1000, this); // Timer para el tiempo del juego
    timer.start();
    addKeyListener(this);
    setFocusable(true);
    cargarImagenes();
    // Eliminar o comentar esta línea
    // iniciarLobos(); // No iniciar lobos al principio
    }

    private void cargarImagenes() {
        // Cargar imágenes desde la carpeta del proyecto
        fondo = new ImageIcon(getClass().getResource("/Programa/Images/escenario1.png")).getImage();
        ascensorImg = new ImageIcon(getClass().getResource("/Programa/Images/ascensor.png")).getImage();
        personajeImg = new ImageIcon(getClass().getResource("/Programa/Images/personaje.png")).getImage();
        loboImgs[0] = new ImageIcon(getClass().getResource("/Programa/Images/globo1.png")).getImage(); // Globo amarillo
        loboImgs[1] = new ImageIcon(getClass().getResource("/Programa/Images/globo2.png")).getImage(); // Globo rosa
        loboImgs[2] = new ImageIcon(getClass().getResource("/Programa/Images/globo3.png")).getImage(); // Globo verde
        loboImgs[3] = new ImageIcon(getClass().getResource("/Programa/Images/globo4.png")).getImage(); // Globo naranja (bonus)
        flechaImg = new ImageIcon(getClass().getResource("/Programa/Images/flecha1.png")).getImage();
    }

    private void iniciarLobos() {
    Random rand = new Random();
    for (int i = 0; i < lobos.length; i++) {
        if (i < 3) { // Solo para los primeros 3 lobos iniciales
            int columna = rand.nextInt(3) * 200 + 100; // Posición aleatoria en 3 columnas
            int tipoGlobo = rand.nextInt(loboImgs.length); // Seleccionar un globo aleatorio
            Image globoImg = loboImgs[tipoGlobo];
            int puntosGlobo = determinarPuntos(tipoGlobo); // Obtener los puntos basados en el tipo de globo
            String colorGlobo = determinarColor(tipoGlobo); // Obtener el color del globo
            int alturaAleatoria = rand.nextInt(300); // Altura aleatoria para la posición inicial
            lobos[i] = new Lobo(columna, alturaAleatoria, globoImg, puntosGlobo, colorGlobo, rand.nextInt(3) + 3); // Asignar velocidad aleatoria
        }
    }
    }

    private String determinarColor(int tipoGlobo) {
        switch (tipoGlobo) {
            case 0:
                return "Amarillo";
            case 1:
                return "Rosado";
            case 2:
                return "Verde";
            case 3:
                return "Naranja";
            default:
                return "Desconocido";
        }
    }

    private int determinarPuntos(int tipoGlobo) {
        switch (tipoGlobo) {
            case 0:
                return 100; // Amarillo
            case 1:
                return 300; // Rosado
            case 2:
                return 200; // Verde
            case 3:
                return 400; // Naranja (bonus)
            default:
                return 0;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar fondo
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        // Dibujar personaje y ascensor
        personaje.dibujar(g, personajeImg, ascensorImg);
        // Dibujar flechas
        for (Flecha flecha : flechas) {
            if (flecha != null) {
                flecha.dibujar(g, flechaImg);
            }
        }
        // Dibujar lobos
        for (Lobo lobo : lobos) {
            if (lobo != null) {
                lobo.dibujar(g);
                // Dibujar rectángulo de colisión para depuración
                g.setColor(Color.RED);
                g.drawRect(lobo.getX(), lobo.getY(), lobo.getBounds().width, lobo.getBounds().height);
            }
        }
        // Dibujar puntos y tiempo
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(Color.WHITE);
        g.drawString("Puntos: " + puntos, 10, 20);
        g.drawString("Tiempo: " + tiempoRestante, 650, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tiempoRestante--;
        contadorGeneracion++;
    
        // Finalizar el juego al alcanzar 2000 puntos
        if (puntos >= 2000) {
            timer.stop();
            mostrarResultado();
            System.exit(0); // Cierra el juego
        }
    
        if (tiempoRestante <= 0) {
            timer.stop();
            mostrarResultado();
            System.exit(0); // Cierra el juego
        }

    // Generar lobos cada 25 segundos
        if (contadorGeneracion >= intervaloGeneracionLobos) {
           contadorGeneracion = 0; // Reiniciar contador
           generarLobo();
        }

        moverFlechas();
        moverLobos();
        verificarColisiones();
        repaint();
    }
    
        private void generarLobo() {
        Random rand = new Random();
    for (int k = 0; k < 3; k++) { // Generar 3 lobos cada vez que se llame a este método
        int columna = rand.nextInt(3) * 200 + 100; // Distribuir los lobos en 3 columnas aleatoriamente
        int tipoGlobo = rand.nextInt(loboImgs.length); // Seleccionar un globo aleatorio
        Image globoImg = loboImgs[tipoGlobo];
        int puntosGlobo = determinarPuntos(tipoGlobo); // Obtener los puntos basados en el tipo de globo
        String colorGlobo = determinarColor(tipoGlobo); // Obtener el color del globo
        int alturaAleatoria = rand.nextInt(300); // Posición de altura aleatoria

        // Buscar un espacio disponible para el nuevo lobo
        for (int i = 0; i < lobos.length; i++) {
            if (lobos[i] == null) {
                int velocidadAleatoria = rand.nextInt(3) + 3; // Velocidad aleatoria entre 3 y 5
                lobos[i] = new Lobo(columna, alturaAleatoria, globoImg, puntosGlobo, colorGlobo, velocidadAleatoria);
                break; // Agrega un lobo y sale del bucle para agregar el siguiente en el siguiente espacio disponible
            }
        }
    }
    }

    private void moverFlechas() {
        for (int i = 0; i < flechas.length; i++) {
            if (flechas[i] != null) {
                flechas[i].mover();
                if (flechas[i].getX() < 0 || flechas[i].getX() > getWidth()) {
                    flechas[i] = null; // Eliminar flechas que salen de la pantalla
                }
            }
        }
    }

    private void moverLobos() {
        for (int i = 0; i < lobos.length; i++) {
            if (lobos[i] != null) {
                lobos[i].mover();
                if (lobos[i].getY() > getHeight()) {
                    lobos[i] = null; // Eliminar lobos que tocan el suelo
                }
            }
        }
    }

    private void verificarColisiones() {
        for (int i = 0; i < flechas.length; i++) {
            if (flechas[i] != null) {
                for (int j = 0; j < lobos.length; j++) {
                    if (lobos[j] != null && flechas[i].getBounds().intersects(lobos[j].getBounds())) {
                        puntos += lobos[j].getPuntos();
                        String colorGlobo = lobos[j].getColorGlobo(); // Obtener el color del globo
                        System.out.println("Colisión detectada entre flecha y lobo. Puntos: " + puntos + ". Color del globo: " + colorGlobo);
                        flechas[i] = null; // Eliminar la flecha
                        lobos[j] = null; // Eliminar el lobo
                        break; // Salir del bucle una vez se detecta la colisión
                    }
                }
            }
        }
    }

    private void mostrarResultado() {
        String mensaje = puntos >= 2000 ? "¡Ganaste!" : "Perdiste. Puntos: " + puntos;
        JOptionPane.showMessageDialog(this, mensaje);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            personaje.moverArriba();
        } else if (key == KeyEvent.VK_DOWN) {
            personaje.moverAbajo();
        } else if (key == KeyEvent.VK_ENTER) {
            dispararFlecha();
        }
    }

    private void dispararFlecha() {
        if (indiceFlecha < flechas.length) {
            flechas[indiceFlecha] = personaje.disparar(); // Disparar flecha horizontalmente
            indiceFlecha++;
        } else {
            // Reiniciar el arreglo de flechas si se llena
            indiceFlecha = 0;
            flechas[indiceFlecha] = personaje.disparar();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Juego del Ascensor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Juego());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}