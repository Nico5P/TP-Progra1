package juego;

import java.awt.Color;
import java.util.Random;
import java.util.Timer;

import entorno.Entorno;

public class Gnomos {
	double x, y;
	double ancho, alto;
	double velocidad =1;
	double ticks;
	double posicionX;
	double posicionY;
	boolean derecha=false;
	boolean apoyado;
	boolean direccion;
	boolean caminar=false; 
	boolean mirar=false; 
	boolean salvado;
	
	public Gnomos(double x, double y, double ancho, double alto)
	{
		this.x=x;
		this.y=y;
		this.ancho=ancho;
		this.alto=alto;
		this.direccion= Math.random() <0.5;
	}
	
	public void dibujarse(Entorno entorno)
	{
		entorno.dibujarRectangulo(x, y, ancho, alto,0, Color.yellow);
	}
	

	public void caida()
	{
		if(!apoyado) {
			this.y+=2;
		}
	}
	

	public void moverIzquierda()
	{
        	this.x+=velocidad;
        
    }
	
	public void moverDerecho()
	{
			this.x -=velocidad;
		
	}
	
//	public boolean isSalvado()
//	{
//        return salvado;
//    }
//
//    public void salvar()
//    {
//        salvado = true;
//    }

    public void volverASalir() 
    {
        this.posicionX = this.x;
        this.posicionY = this.y;
        salvado=false;
    }
    
    
    public boolean colisionaCon(Pep p) {
        return (this.x < p.getX() + p.ancho &&
                this.x + this.ancho > p.getX() &&
                this.y < p.getY() + p.alto &&
                this.y + this.alto > p.getY());
    }
    
    public boolean colisionTortugas(Tortugas t) {
        return (this.x < t.getX() + t.ancho &&
                this.x + this.ancho > t.getX() &&
                this.y < t.getY() + t.alto &&
                this.y + this.alto > t.getY());
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