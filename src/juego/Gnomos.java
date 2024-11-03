package juego;

import java.awt.Color;
import java.util.Random;
import java.util.Timer;

import entorno.Entorno;

public class Gnomos {
	double x, y;
	double ancho, alto;
	double velocidad =0.5;
	double ticks;
	double posicionX;
	double posicionY;
	boolean derecha=false;
	boolean apoyado;
	boolean direccion;
	boolean caminar=false; 
	boolean mirar=false; 
	boolean salvado;
	
	public Gnomos(double x, double y, double ancho, double alto) {
		this.x=x;
		this.y=y;
		this.ancho=ancho;
		this.alto=alto;
		this.direccion= Math.random() <0.5;
	}
	
	public void dibujarse(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho, alto,0, Color.yellow);
	}

	public void caida() {
		if(!apoyado) {
			this.y+=2;
		}
	}
	

	public void moverIzquierda() {
        	this.x+=velocidad;
        
    }
	
	public void moverDerecho() {
			this.x -=velocidad;
		
	}
	
    public void volverASalir() {
        this.posicionX = this.x;
        this.posicionY = this.y;
        salvado=false;
    }
    
    
    public boolean colisionTortugas(Tortugas t) {
        return (this.limiteIzquierdo() < t.limiteDerecho() &&
                this.limiteDerecho() > t.limiteIzquierdo() &&
                this.limiteSuperior() < t.limiteInferior() &&
                this.limiteInferior() > t.limiteSuperior());
    }

    public boolean colisionIslas(Islas i) {
        return (this.limiteIzquierdo() < i.limiteDerecho() &&
                this.limiteDerecho() > i.limiteIzquierdo() &&
                this.limiteSuperior() < i.limiteInferior() &&
                this.limiteInferior() > i.limiteSuperior());
    }
    
    public boolean colisionNavecita(Navecita n) {
    	return (this.x < n.getX() + n.ancho &&
                this.x + this.ancho > n.getX() &&
                this.y < n.getY() + n.alto &&
                this.y + this.alto > n.getY());
    }
    
    public void seReinicia(double nuevaX, double nuevaY, double ancho, double alto) {
    	this.x=nuevaX;
    	this.y=nuevaY;
    	this.ancho=ancho;
    	this.alto=alto;
    	this.salvado=false;
    }
    
    public double limiteSuperior() 
    {
    	return this.y - this.alto/2;
    }
    
    public double limiteInferior() 
    {
    	return this.y + this.alto/2;
    }
    
    public double limiteIzquierdo() 
    {
    	return this.x-this.ancho/2;
    }
    public double limiteDerecho() 
    {
    	return this.x+this.ancho/2;
    }
}