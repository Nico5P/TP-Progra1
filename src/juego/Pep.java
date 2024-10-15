package juego;

import java.awt.Color;

import entorno.Entorno;

public class Pep
{
	// Variables de instancia
	private int x, y;
	private int ancho;
	private int alto;
	private int VelocidadY = 0;
    private boolean saltando = false;

	public Pep(int x, int y) 
	{
		this.x = x;
		this.y = y;
		this.ancho = 40;
		this.alto = 40;
	}

	public void moverIzquierda() 
	{
		this.x -= 5;
	}
	
	public void moverDerecha() 
	{
		this.x += 5;
	}

	public void salto()
	{
        if (saltando)
        {
            y += VelocidadY;
            x += x;                   // Salta en la ultima direccion presionada
            VelocidadY += 1;          // Efecto de gravedad
            if (y >= 100)     // Prueba usando cualquier "x" como suelo
            {  
                y = 100;
                saltando = false;
                VelocidadY = 0;
                x = 0;       // Detener el movimiento horizontal al aterrizar
            }
        }
    }
	
	public void dibujarse(Entorno entorno) 
	{
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.red);
	}

	public int getX() 
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
	public int getY() 
	{
		return this.y;
	}
}