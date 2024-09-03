package Programa;

import java.awt.*;

public class Flecha {
    private int x, y; 
    private final int velocidad = 100; 
    private final int ancho = 20; 
    private final int alto = 5; 

    public Flecha(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void mover() {
        x -= velocidad;
    }
 
    public void dibujar(Graphics g, Image flechaImg) {
        g.drawImage(flechaImg, x, y, ancho, alto, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean colisionaCon(Lobo lobo) {
        return getBounds().intersects(lobo.getBounds());
    }
}