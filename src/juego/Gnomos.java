package juego;

import java.awt.Color;

import entorno.Entorno;

public class Gnomos {
	double x, y;
	double ancho, alto;
	boolean apoyado= false;
	double direccion =1;
//	Timer timer;
	
	
	
	public Gnomos(double x, double y, double ancho, double alto)
	{
		this.x=x;
		this.y=y;
		this.ancho=ancho;
		this.alto=alto;
	}
	
	public void dibujarse(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho, alto,0, Color.yellow);
//		this.x+=5;
//		if(this.x > limiteIzquierdo() || this.x < limiteDerecho() ) {
//			this.y+=5;
//		}
	}
	
	public double seMueveDerecha() {
		return this.x+=2;
		
	}
	public double seMueveIzquierda() {
		return this.x-=2;
	}
	
	public double seDetiene() {
		return this.x= limiteDerecho();
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
