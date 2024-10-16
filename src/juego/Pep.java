package juego;

import java.awt.Color;

import entorno.Entorno;

public class Pep
{
	// Variables de instancia
	private int x, y;
	private int ancho;
	private int alto;
	public int VelocidadY = 0;
    public boolean saltando = false;
    public boolean mirandoDerecha=true;

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
		mirandoDerecha=false;
	}
	
	public void moverDerecha() 
	{
		this.x += 5;
		mirandoDerecha=true;
	}

	public void salto()
	{
        if (saltando)
        {
        		for(int i=0; i<20; i++)
        		{
        			if(i<10)
        			{
        				this.y=this.y-1;
        			}
        			if(i>9 && i<15)
        			{
        				if(mirandoDerecha==true)
        				{
        					this.x++;
        				}
        				else
        				{
        					this.x--;
        				}
        			}
        			if(i>14)
        			{
        				if(mirandoDerecha==true)
        				{
        					this.x++;
        				}
        				else
        				{
        					this.x--;
        				}

        			}
        		}	
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
	
	public void caer(boolean saltando)
	{
		if (saltando==false)
		{
			this.y++;
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
	
	public boolean getSaltando()
	{
		return this.saltando;
	}
}