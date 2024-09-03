package Programa;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Color;

public class Lobo {
    private int x, y; // Coordenadas del lobo
    private int velocidad; // Velocidad de movimiento del lobo, ahora modificable
    private Image globoImg; // Imagen del lobo con globo
    private int puntos; // Puntos que otorga el lobo al ser eliminado
    private final int ancho; // Ancho del lobo
    private final int alto; // Alto del lobo
    private String colorGlobo; // Atributo para almacenar el color del globo

    // Constructor actualizado para aceptar la velocidad como parámetro
    public Lobo(int x, int y, Image globoImg, int puntos, String colorGlobo, int velocidad) {
        this.x = x;
        this.y = y;
        this.globoImg = globoImg;
        this.puntos = puntos;
        this.ancho = globoImg.getWidth(null);
        this.alto = globoImg.getHeight(null);
        this.colorGlobo = colorGlobo;
        this.velocidad = velocidad; // Asignar la velocidad pasada por parámetro
    }

    // Método para mover el lobo hacia abajo usando la velocidad establecida
    public void mover() {
        y += velocidad;
    }

    public void dibujar(Graphics g) {
        g.drawImage(globoImg, x, y, ancho, alto, null);
        g.setColor(Color.RED);
        g.drawRect(x, y, ancho, alto);
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