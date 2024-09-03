package Programa;

import java.awt.*;

public class Personaje {
    private int x, y; 
    private final int velocidad = 20; 
    private final int ancho = 50; 
    private final int alto = 70; 

    private int ascensorY = 130; 
    private final int ascensorAncho = 50; 
    private final int ascensorAlto = 140; 
    private final int ascensorX = 650; 

    public Personaje(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moverArriba() {
        if (y > 180) { 
            y -= velocidad;
            ascensorY -= velocidad; 
        }
    }

    public void moverAbajo() {
        if (y + alto < 520) { 
            y += velocidad;
            ascensorY += velocidad; 
        }
    }
 
    public void dibujar(Graphics g, Image personajeImg, Image ascensorImg) {
        g.drawImage(ascensorImg, ascensorX, ascensorY, ascensorAncho, ascensorAlto, null);
        g.drawImage(personajeImg, x, y, ancho, alto, null);
    }

    public Flecha disparar() {
        return new Flecha(x - 20, y + alto / 2);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto); 
    }

    public int getAscensorY() {
        return ascensorY;
    }
}