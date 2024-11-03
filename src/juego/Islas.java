package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Islas {
    public double x;
    public double y;
    public double ancho;
    public double alto;
    Image imagen;

    public Islas(double x, double y, double ancho, double alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.imagen = Herramientas.cargarImagen("juego/imagenes/plataforma1.png");
    }

    public void generarIslas(Entorno e) {
        e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.blue);
        
    }
    
    public void dibujarIslas(Entorno e) {
    	e.dibujarImagen(this.imagen, this.x, this.y-3, 0, 0.47);
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