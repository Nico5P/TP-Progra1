package juego;


import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	// Variables y métodos propios de cada grupo
	// ...
	Islas[] islas;
	
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP; Balbi, Gomez, Pereira, Pereyra", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		// ...
		int islasPorFila=5;
		this.islas = new Islas[islasPorFila *(islasPorFila+1)/2];
		double ejeY=110;
		int index=0;
		
		for(int fila=0; fila<islasPorFila; fila++)
		{
			double ejeX=400-(fila*80);
			for(int i=0; i<=fila; i++)
			{
				this.islas[index] = new Islas(ejeX, ejeY, 100, 30) ;
				ejeX=ejeX+100+60; //
				index++;
			}
			ejeY=ejeY+80;
		}
		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		// Procesamiento de un instante de tiempo
		// ...
		for (int i=0;i<islas.length;i++){
			islas[i].generarIslas(entorno);	
		}
		
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
