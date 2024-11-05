package juego;

import java.awt.Color;

import entorno.Entorno;

public class Navecita {
	public double x, y;
	public double ancho;
	public double alto;
	public boolean direccion;
	public boolean quieto;
	
	public Navecita(double x, double y) {
		this.x = x; 
		this.y = y; 
		this.ancho = 60; // Ancho de la nave
		this.alto = 30; // Alto de la nave
	}
	
	public void moverseHacia(double x) {
		if(x > this.centro() && x <= 800) {
			this.x+=5;
		}
		if ((x < this.centro() && x > this.ancho) || (x < this.limiteIzquierdo() && x > 10)) {
			this.x-=5;
		}
	}
	
	public void dibujarse(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.magenta);
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
	
	public double centro() {
		return this.x + (this.ancho / 2);
	}
	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}
}