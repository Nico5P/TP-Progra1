package juego;

import java.util.Random;
import entorno.Entorno;
import java.awt.Color;

public class Tortugas {
	Entorno[] entorno;
	boolean ubicadas;
	private double x, y;
	private double ancho, alto;	
	private double velocidad;
	
	
	public Tortugas() {
		
		Random gen = new Random();
		this.x = gen.nextInt(800) + 1;
		this.y = gen.nextInt(20) + 1;
		
		this.ancho=10;
		this.alto=10;
		
		this.velocidad = gen.nextInt(5) + 1;

		this.ubicadas=false;
	}
	
	boolean colisionInferior=false;
	public void dibujarse(Entorno entorno) {
		if (this.y < 650) {
			entorno.dibujarRectangulo(x, y, ancho, ancho, alto, Color.white);
			if(colisionInferior==false)
			{
				this.y+=5;
			}	
			else 
			{
				entorno.dibujarRectangulo(x, y, ancho, ancho, alto, Color.white);
				
			}
		}
		
	}
	
	
	public void velocidad() {
		if(colisionInferior==false)
		{
			this.y+= this.velocidad*0.001;
			this.x+=this.velocidad*0.001;
		}
	}
	
	
	
	public double getY() 
	{
		return this.y;
	}

	public double getX() 
	{
		return this.x;
	}
	
	public boolean getUbicadas() 
	{
		return this.ubicadas;
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
