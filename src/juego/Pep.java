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
    public boolean mirandoDerecha=true;
    public boolean apoyado;

	public Pep(double x, double y) 
	{
		this.x = x;
		this.y = y;
		this.ancho = 40;
		this.alto = 40;
	}

	public void moverIzquierda() 
	{
		this.x -= 5;
		mirandoDerecha=false;
	}
	
	public void moverDerecha() 
	{
		this.x += 5;
		mirandoDerecha=true;
	}

	public void salto(Entorno entorno)
	{
        if (saltando)
        {
        	for(int i=0; i<30; i++)
        	{
        		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.red);
        		this.y--;
        	}
//        	for(int ii=0; ii<30; ii++)
//        	{
//        		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.red);
//        		this.x--;
//        	}
        	saltando=false;
        	
        	

        	
//            y += VelocidadY;
//            x += x;                   // Salta en la ultima direccion presionada
//            VelocidadY += 1;          // Efecto de gravedad
//            if (y >= 100)     // Prueba usando cualquier "x" como suelo
//            {  
//                y = 100;
//                saltando = false;
//                VelocidadY = 0;
//                x = 0;       // Detener el movimiento horizontal al aterrizar
//            }
        }
    }
	
	public void caer(boolean saltando, boolean apoyado)
	{
		if (saltando==false && apoyado==false)
		{
			this.y++;
		}
	}
	
	public void dibujarse(Entorno entorno) 
	{
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.red);
	}

	public double getX() 
	{
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
	public double getY() 
	{
		return this.y;
	}
	
	public boolean getSaltando()
	{
		return this.saltando;
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