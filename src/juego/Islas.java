package juego;

import java.awt.Color;

import entorno.Entorno;

public class Islas 
{
	double x;
	double y;
	double ancho;
	double alto;
	
	public Islas(double x, double y, double ancho, double alto)
	{
		this.x=x;
		this.y=y;
		this.ancho=ancho;
		this.alto=alto;
	}
	
	public void generarIslas(Entorno e)
	{
		e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.blue);
	}
	
}
