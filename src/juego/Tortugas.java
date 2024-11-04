package juego;

import java.util.Random;
import entorno.Entorno;
import java.awt.Color;

public class Tortugas {
	Entorno[] entorno;
	Islas[] islas;
	double x, y;
	double ancho, alto;	
	double velocidad = 2;
	double posicionX;
	double posicionY;
	boolean apoyado;
	boolean enUnaIsla;
	boolean derecha;
	boolean salvado;
	boolean caminar;
	boolean mira;
	boolean lado;
	boolean movimientoDerecha;
	
	public Tortugas() {
		Random gen = new Random();
		double xSpawn1 = gen.nextInt(250) + 1; //rango de apariciones desde 0 hasta 251 en x
		double xSpawn2 = gen.nextInt(795) + 490; //rango de 490 a 794
		int dondeSpawnea= gen.nextInt(2) + 1;
		if(dondeSpawnea>1) {
			this.x = xSpawn2;
			this.derecha=true;
		}
		
		else {
			this.x = xSpawn1;
			this.derecha=false;
		}
		
		this.ancho=18;
		this.alto=18;
		this.apoyado=false;
		this.caminar=true;
	}
	
	public void dibujarse(Entorno entorno, boolean estaapoyado) {
		if ( this.y < 600) {
			entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.white);
		}
	}
	
	public void caer() {
		if(!this.apoyado) { 
			this.y += 2;
		}
	}
	
	public void mover() {
        if (caminar) {
        	this.x += 1; // Mover a la derecha
        } else {
        	this.x -= 1; // Mover a la izquierda
        }
	}

	

    public boolean colisionIslas(Islas i) {
    	return (this.limiteIzquierdo() < i.limiteDerecho() &&
    			this.limiteDerecho() > i.limiteIzquierdo() &&
    			this.limiteSuperior() < i.limiteInferior() &&
    			this.limiteInferior() > i.limiteSuperior());
    }
    
    public boolean colisionFuego(BoladeFuego b) {
        return (this.limiteIzquierdo() < b.limiteDerecho() &&
                this.limiteDerecho() > b.limiteIzquierdo() &&
                this.limiteSuperior() < b.limiteInferior() &&
                this.limiteInferior() > b.limiteSuperior());
    }
	
	public double getY() 
	{
		return this.y;
	}

	public double getX() 
	{
		return this.x;
	}
	
	public boolean getapoyado()
	{
		return this.apoyado;
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