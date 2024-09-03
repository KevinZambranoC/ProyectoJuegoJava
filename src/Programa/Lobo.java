package Programa;

import java.awt.*;

public class Lobo {
    private int x, y; 
    private int velocidad; 
    private Image globoImg; 
    private int puntos; 
    private final int ancho; 
    private final int alto; 
    private String colorGlobo; 

    public Lobo(int x, int y, Image globoImg, int puntos, String colorGlobo, int velocidad) {
        this.x = x;
        this.y = y;
        this.globoImg = globoImg;
        this.puntos = puntos;
        this.ancho = globoImg.getWidth(null);
        this.alto = globoImg.getHeight(null);
        this.colorGlobo = colorGlobo;
        this.velocidad = velocidad; 
    }

    public void mover() {
        y += velocidad;
    }

    public void dibujar(Graphics g) {
        g.drawImage(globoImg, x, y, ancho, alto, null);
        
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

    public int getPuntos() {
        return puntos;
    }

    public String getColorGlobo() {
        return colorGlobo;
    }
}