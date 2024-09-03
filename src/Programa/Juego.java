package Programa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Juego extends JPanel implements ActionListener, KeyListener {
    
    private Timer timer;
    private int puntos = 0;
    private int tiempoRestante = 120;
    private Personaje personaje;
    private Flecha[] flechas = new Flecha[30]; 
    private Lobo[] lobos = new Lobo[40]; 
    private int indiceFlecha = 0; 
    private int contadorTiempo = 0; 
    private int contadorGeneracion = 0; 
    private final int intervaloGeneracionLobos = 10;
    private final int intervaloGeneracionLobosNaranja = 25;
    private int contadorGeneracionNaranja = 0;
    
    private Image fondo;
    private Image ascensorImg;
    private Image personajeImg;
    private Image[] loboImgs = new Image[4]; 
    private Image flechaImg;

    public Juego() {
        
        setPreferredSize(new Dimension(800, 600)); 
        setBackground(Color.WHITE);
        personaje = new Personaje(650, 175);
        timer = new Timer(1000, this);
        timer.start();
        
        addKeyListener(this);
        setFocusable(true);
        cargarImagenes();
        generarLobosIniciales();
        
    }

    private void cargarImagenes() {
        fondo = new ImageIcon(getClass().getResource("/Programa/Images/escenario1.png")).getImage();
        ascensorImg = new ImageIcon(getClass().getResource("/Programa/Images/ascensor.png")).getImage();
        personajeImg = new ImageIcon(getClass().getResource("/Programa/Images/personaje.png")).getImage();
        loboImgs[0] = new ImageIcon(getClass().getResource("/Programa/Images/globo1.png")).getImage();
        loboImgs[1] = new ImageIcon(getClass().getResource("/Programa/Images/globo2.png")).getImage();
        loboImgs[2] = new ImageIcon(getClass().getResource("/Programa/Images/globo3.png")).getImage();
        loboImgs[3] = new ImageIcon(getClass().getResource("/Programa/Images/globo4.png")).getImage();
        flechaImg = new ImageIcon(getClass().getResource("/Programa/Images/flecha1.png")).getImage();
    }

    private void iniciarLobos() {
        Random rand = new Random();
            for (int i = 0; i < lobos.length; i++) {
                if (i < 3) {
                    int columna = rand.nextInt(3) * 200 + 100;
                    int tipoGlobo = rand.nextInt(loboImgs.length);
                    Image globoImg = loboImgs[tipoGlobo];
                    int puntosGlobo = determinarPuntos(tipoGlobo);
                    String colorGlobo = determinarColor(tipoGlobo);
                    int alturaAleatoria = rand.nextInt(300);
                    lobos[i] = new Lobo(columna, alturaAleatoria, globoImg, puntosGlobo, colorGlobo, rand.nextInt(3) + 3);
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
                return 100;
            case 1:
                return 300;
            case 2:
                return 200;
            case 3:
                return 400;
            default:
                return 0;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        personaje.dibujar(g, personajeImg, ascensorImg);
        
        for (Flecha flecha : flechas) {
            if (flecha != null) {
                flecha.dibujar(g, flechaImg);
            }
        }
          
        for (Lobo lobo : lobos) {
            if (lobo != null) {
                lobo.dibujar(g);
            }
        }
        
        int minutos = tiempoRestante / 60;
        int segundos = tiempoRestante % 60;
        String tiempoFormato = String.format("%02d:%02d", minutos, segundos);
        
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(Color.WHITE);
        g.drawString("Puntos: " + puntos, 10, 20);
        g.drawString("Tiempo: " + tiempoFormato, 640, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tiempoRestante--;
        contadorGeneracion++;
        contadorGeneracionNaranja++;
    
        if (puntos >= 2000) {
            timer.stop();
            mostrarResultado();
            System.exit(0);
        }
    
        if (tiempoRestante <= 0) {
            timer.stop();
            mostrarResultado();
            System.exit(0);
        }

        if (contadorGeneracion >= intervaloGeneracionLobos) {
           contadorGeneracion = 0;
           generarLobo();
        }
        
        if (contadorGeneracionNaranja >= intervaloGeneracionLobosNaranja) {
            contadorGeneracionNaranja = 0;
            generarLoboNaranja();
        }

        moverFlechas();
        moverLobos();
        verificarColisiones();
        repaint();
    }
    
    private void generarLobosIniciales() {
        Random rand = new Random();
                for (int i = 0; i < 3; i++) {
                    int columna = rand.nextInt(3) * 200 + 100;
                    int tipoGlobo = rand.nextInt(loboImgs.length - 1);
                    Image globoImg = loboImgs[tipoGlobo];
                    int puntosGlobo = determinarPuntos(tipoGlobo);
                    String colorGlobo = determinarColor(tipoGlobo);
                    int alturaInicial = rand.nextInt(100);
            

                    for (int j = 0; j < lobos.length; j++) {
                        if (lobos[j] == null) {
                            int velocidadAleatoria = rand.nextInt(3) + 15;
                            lobos[j] = new Lobo(columna, alturaInicial, globoImg, puntosGlobo, colorGlobo, velocidadAleatoria);
                            break;
                        }
                    }
                }
    }
    
    private void generarLoboNaranja() {
        Random rand = new Random();
        int columna = rand.nextInt(3) * 200 + 100;
        Image globoImg = loboImgs[3];
        int puntosGlobo = 400;
        String colorGlobo = "Naranja";


        for (int i = 0; i < lobos.length; i++) {
            if (lobos[i] == null) {
                int velocidadAleatoria = rand.nextInt(3) + 30;
                lobos[i] = new Lobo(columna, 0, globoImg, puntosGlobo, colorGlobo, velocidadAleatoria);
                break;
            }
        }
    }    
        
    private void generarLobo() {
        Random rand = new Random();
        int columna = rand.nextInt(3) * 200 + 100;
        int tipoGlobo = rand.nextInt(loboImgs.length - 1);
        Image globoImg = loboImgs[tipoGlobo];
        int puntosGlobo = determinarPuntos(tipoGlobo);
        String colorGlobo = determinarColor(tipoGlobo);

        for (int i = 0; i < lobos.length; i++) {
            if (lobos[i] == null) {
                int velocidadAleatoria = rand.nextInt(3) + 20;
                lobos[i] = new Lobo(columna, 0, globoImg, puntosGlobo, colorGlobo, velocidadAleatoria);
                break;
            }
        }
    
    }

    private void moverFlechas() {
        for (int i = 0; i < flechas.length; i++) {
            if (flechas[i] != null) {
                flechas[i].mover();
                if (flechas[i].getX() < 0 || flechas[i].getX() > getWidth()) {
                    flechas[i] = null;
                }
            }
        }
    }

    private void moverLobos() {
        for (int i = 0; i < lobos.length; i++) {
            if (lobos[i] != null) {
                lobos[i].mover();
                if (lobos[i].getY() > getHeight()) {
                    lobos[i] = null;
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
                        String colorGlobo = lobos[j].getColorGlobo();
                        System.out.println("Colisión detectada entre flecha y lobo. Puntos: " + puntos + ". Color del globo: " + colorGlobo);
                        flechas[i] = null;
                        lobos[j] = null;
                        break;
                    }
                }
            }
        }
    }

    private void mostrarResultado() {
        String mensaje = puntos >= 2000 ? "¡Ganaste!. Puntos: " + puntos : "Perdiste. Puntos: " + puntos;
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
            flechas[indiceFlecha] = personaje.disparar();
            indiceFlecha++;
        } else {
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