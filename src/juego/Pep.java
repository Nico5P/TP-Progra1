package juego;

import java.awt.Color;

import entorno.Entorno;

public class Pep
{
	
	public double x, y; 
	public double ancho; 
	public double alto;
	public double VelocidadY = 0; // Velocidad a la que salta
    public boolean saltando = false; // Para saber si pep esta saltando o no
    public boolean mirandoDerecha = true; // Dirección de Pep
    public boolean apoyado;
    public boolean chocaCon;
    public int contador = 0;
    public boolean tieneQueMoverse;    

	public Pep(double x, double y) 
	{
		this.x = x; 
		this.y = y; 
		this.ancho = 20; // Ancho de pep
		this.alto = 30; // Alto de pep
	}


	public void moverIzquierda() //Mueve a pep a la izquierda
	{ 
		if (!saltando && apoyado) 
		{ 
			this.x -= 5; 
			mirandoDerecha = false; 
		}
	}
	
	public void moverDerecha() //Mueve a pep a la derecha
	{ 
		if (!saltando && apoyado) 
		{
			this.x += 5; 
			mirandoDerecha = true;
		}
	}

	public void salto() // Método para saltar 
	{
		if (apoyado) 
		{ 
			saltando = true;
			VelocidadY = 9; 
			apoyado = false; 
		}
	}

	public void caer() // Maneja la caida de pep
	{
		if (saltando) 
		{
			this.y -= VelocidadY; // "Sube" a pep
			VelocidadY -= 0.5; //
			if (VelocidadY <= 0)  // Cambia el estado al llegar al "limite" de alto permitido en su salto 
			{
				saltando = false;
			}
		} 
		
		else if (!apoyado)
		{
			this.y += 2; // sino esta apoyado aumenta "Y" para simular la "caida"
		}

		// Aquí puedes incluir la lógica para determinar si Pep vuelve a estar apoyado
		// Ejemplo: si toca el suelo, establece apoyado a true.
		// apoyado = (this.y >= alturaDelSuelo); // Define la altura del suelo adecuadamente
	}

	public void dibujarse(Entorno entorno) 
	{
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.red);
	}

	public double getX() 
	{
		return this.x;
	}

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
		return this.y - this.alto / 2;
	}
	
	public double limiteInferior()
	{
		return this.y + this.alto / 2;
	}
	
	public double limiteIzquierdo()
	{
		return this.x - this.ancho / 2;
	}
	
	public double limiteDerecho() 
	{
		return this.x + this.ancho / 2;
	}
	
	public double centro() 
	{
		return this.x + (this.ancho / 2);
	}
}
