package Programa;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Flecha {
    private int x, y; // Coordenadas de la flecha
    private final int velocidad = 100; // Velocidad de la flecha
    private final int ancho = 20; // Ancho de la flecha
    private final int alto = 5; // Alto de la flecha

    public Flecha(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Método para mover la flecha horizontalmente hacia la izquierda
    public void mover() {
        x -= velocidad;
    }

    // Método para dibujar la flecha en pantalla
    public void dibujar(Graphics g, Image flechaImg) {
        g.drawImage(flechaImg, x, y, ancho, alto, null);
    }

    // Obtener el rectángulo de colisión de la flecha
    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);
    }

    // Métodos para obtener las coordenadas de la flecha
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Método para verificar colisiones con lobos
    public boolean colisionaCon(Lobo lobo) {
        return getBounds().intersects(lobo.getBounds());
    }
}