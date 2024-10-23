package juego;

import java.util.Random;
import entorno.Entorno;
import java.awt.Color;

public class Tortugas {
	Entorno[] entorno;
	Islas[] islas;
	public double x, y;
	public double ancho, alto;	
	public double velocidad;
	public boolean apoyado;
	public boolean enUnaIsla;
	public boolean derecha;
	
	public Tortugas() {
		
		Random gen = new Random();
		double xSpawn1 = gen.nextInt(350) + 1;
		double xSpawn2 = gen.nextInt(800) + 470;
		int dondeSpawnea= gen.nextInt(2) + 1;
		if(dondeSpawnea>1)
		{
			this.x = xSpawn2;
			this.derecha=true;
		}
		else
		{
			this.x = xSpawn1;
			this.derecha=false;
		}

		this.y = gen.nextInt(20) + 1;
		this.ancho=10;
		this.alto=10;
		this.velocidad = gen.nextInt(5) + 1;
		this.apoyado=false;
		
	}
	
	public void dibujarse(Entorno entorno, boolean estaapoyado) {
		if (this.y < 600) {
			entorno.dibujarRectangulo(x, y, ancho, ancho, alto, Color.white);
		}
	}
	
	public void caer() {
		if(!this.apoyado) {
			this.y++;
		}
	}
	
	public void mover() {
		if(derecha) {
			this.x++;
		}
		else {
			this.x--;
		}
	}
	
//	public void moverse(double extremo1, double extremo2) {
//		boolean izquierda;
//		boolean derecha;
//		if(this.enUnaIsla) {
//			}
//			if(this.apoyado) {
//				cambiarDireccion=false;
//			}
//			if(!this.apoyado) {
//				cambiarDireccion=true;
//			}
//			if(derecha) {
//				this.x++;
//		}
//	}
//	public void velocidad() {
//		if(colisionInferior==false)
//		{
//			this.y+= this.velocidad*0.001;
//			this.x+=this.velocidad*0.001;
//		}
//	}
//	
	
	
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
