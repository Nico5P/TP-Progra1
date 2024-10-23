package juego;

import java.awt.Color;

import entorno.Entorno;

public class Pep
{
	// Variables de instancia
	private double x, y;
	private double ancho;
	private double alto;
	public double VelocidadY = 0;
    public boolean saltando = false;
    public boolean mirandoDerecha = true;
    public boolean apoyado;
    public boolean chocaCon;
    public int contador=0;

	public Pep(double x, double y) {
		this.x = x;
		this.y = y;
		this.ancho = 40;
		this.alto = 40;
	}

	public void moverIzquierda() {
		this.x -= 5;
		mirandoDerecha=false;
	}
	
	public void moverDerecha() {
		this.x += 5;
		mirandoDerecha=true;
	}

	public void salto (Entorno entorno) {
		if(apoyado) {
			if (saltando) {
	        	for (int i = 0; i < 120; i++) {
	        		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.red);
	        		if(chocaCon == false) {
	        			this.y--;
	        		}
	        	}
	        }
			saltando = false;
			apoyado = false;
		}
        
    }
	
	
	public void caer() {
		if (!this.saltando && !this.apoyado) {
			this.y++;
		}
		if(this.chocaCon && !this.apoyado) {
			this.y++;
		}
	}
	
	public void dibujarse(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.red);
	}

	public double getX() {
		return this.x;
	}
/*
	public boolean colisionaCon(Pelotita p) 
	{
		if (p.getY() >= this.y - (this.alto / 2) && p.getY() <= this.y + (this.alto / 2) &&
			p.getX() >= this.x - (this.ancho / 2) && p.getX() <= this.x + (this.ancho / 2))
		{
		
			return true;
		}
		
		return false;			
	}
*/
	public double getY() {
		return this.y;
	}
	
	public boolean getSaltando() {
		return this.saltando;
	}
	
	public double limiteSuperior() {
		return this.y - this.alto/2;
	}
	
	public double limiteInferior() {
		return this.y + this.alto/2;
	}
	
	public double limiteIzquierdo() {
		return this.x - this.ancho/2;
	}
	
	public double limiteDerecho() {
		return this.x + this.ancho/2;
	}
}