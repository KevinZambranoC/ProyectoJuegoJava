package Programa;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Personaje {
    private int x, y; // Coordenadas del personaje
    private final int velocidad = 20; // Velocidad de movimiento vertical del personaje
    private final int ancho = 50; // Ancho del personaje para la colisión
    private final int alto = 70; // Alto del personaje para la colisión

    private int ascensorY = 130; // Coordenada Y del ascensor para sincronización
    private final int ascensorAncho = 50; // Ancho del ascensor
    private final int ascensorAlto = 140; // Alto del ascensor
    private final int ascensorX = 650; // Posición X fija del ascensor

    public Personaje(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Método para mover el personaje y el ascensor hacia arriba
    public void moverArriba() {
        if (y > 180) { // Limitar movimiento a la parte superior del área permitida
            y -= velocidad;
            ascensorY -= velocidad; // Mover el ascensor en sincronía
        }
    }

    // Método para mover el personaje y el ascensor hacia abajo
    public void moverAbajo() {
        if (y + alto < 520) { // Limitar movimiento a la parte inferior del área permitida
            y += velocidad;
            ascensorY += velocidad; // Mover el ascensor en sincronía
        }
    }

    // Método para dibujar el personaje en el gráfico dado
    public void dibujar(Graphics g, Image personajeImg, Image ascensorImg) {
        // Dibujar ascensor primero
        g.drawImage(ascensorImg, ascensorX, ascensorY, ascensorAncho, ascensorAlto, null);
        // Dibujar personaje
        g.drawImage(personajeImg, x, y, ancho, alto, null);
    }

    // Método para disparar una flecha desde la posición del personaje
    public Flecha disparar() {
        return new Flecha(x - 20, y + alto / 2); // Crear una flecha en la posición adecuada
    }

    // Obtener el rectángulo de colisión del personaje
    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto); // Devuelve un rectángulo que representa la posición y tamaño del personaje
    }

    // Obtener la posición del ascensor para otros cálculos
    public int getAscensorY() {
        return ascensorY;
    }
}