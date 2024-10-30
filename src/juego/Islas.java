package juego;

import java.awt.Color;
import entorno.Entorno;

public class Islas {
    public double x;
    public double y;
    public double ancho;
    public double alto;

    public Islas(double x, double y, double ancho, double alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }

    public void generarIslas(Entorno e) {
        e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.blue);
    }
    
    public double limiteSuperior() {
        return this.y - this.alto / 2;
    }

    public double limiteInferior() {
        return this.y + this.alto / 2;
    }

    public double limiteIzquierdo() {
        return this.x - this.ancho / 2;
    }

    public double limiteDerecho() {
        return this.x + this.ancho / 2;
    }

    public double getX() {
    	return this.x;
    }
    
    public double getY() {
        return this.y;
    }

    public double area() {
        return this.ancho * this.alto;
    }
}