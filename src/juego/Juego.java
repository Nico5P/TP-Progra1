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
	Pep pep;
	Tortugas[] tortugas;
	Gnomos[] gnomos;
	int horas;
	int minutos;
	int segundos;
	
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP; Balbi, Gomez, Pereira, Pereyra", 800, 600);
		entorno.colorFondo(Color.pink);
		// Inicializar lo que haga falta para el juego
		
		//Generación de tortugas
		this.tortugas = new Tortugas[30];
		for(int i = 0; i<this.tortugas.length; i++) 
		{
			this.tortugas[i] = new Tortugas();
		}
		// Generación de de islas por filas
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
			ejeY=ejeY+104;
		}
		
		//Generar a Pep
		pep = new Pep(400, entorno.alto()-110);
		
		//generar gnomos
		
		this.gnomos = new Gnomos[2];
		for(int i = 0; i<this.gnomos.length; i++) 
		{
			this.gnomos[i] = new Gnomos(400,83,10,20);
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
		int sec= entorno.tiempo()/1000;
		segundos= sec%60;
		int min = sec/60;
		minutos= min%60;
		int hor= min/60;
		horas = hor%60;
		
		for(int i=0; i<entorno.tiempo()+1; i++)
		{
			for(int ii=0; ii<tortugas.length; ii++)
			{
				for(int iii=0; iii<islas.length; iii++)
				{
					if(islas[iii].limiteSuperior()==tortugas[ii].limiteInferior() && islas[iii].limiteIzquierdo()<tortugas[ii].limiteIzquierdo() && islas[iii].limiteDerecho()>tortugas[ii].limiteDerecho())
					{
						tortugas[ii].colisionInferior=true;
						
					}
				}
			}
		}
		//Generación de las tortugas			
		if (segundos>5)
		{
			for (int i=0;i<tortugas.length;i++){
				tortugas[i].dibujarse(entorno);
			}
		}

	
		//velocidad de las torugas
	
//		if (!entorno.estaPresionada('P'))
//		{
//			for (int i=0;i<tortugas.length;i++)
//			{
//				if (!tortugas[i].getUbicadas() && tortugas[i].getY() <= 500)
//				{
//					tortugas[i].velocidad();
//				}
//			}
//		}
		// Generación de de islas por filas
		for (int i=0;i<islas.length;i++){
			islas[i].generarIslas(entorno);
		}
		
		// Dibuja y toma el movimiento de Pep
		pep.dibujarse(entorno);
		
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && pep.getX() > 5 && pep.saltando==false)
			pep.moverIzquierda();
		if (entorno.estaPresionada(entorno.TECLA_DERECHA) && pep.getX() < entorno.ancho() - 5 && pep.saltando==false)
			pep.moverDerecha();
		if (entorno.estaPresionada(entorno.TECLA_ARRIBA))
		{
			pep.saltando = true;
			pep.salto();
			
			pep.saltando= false;
			
        	pep.VelocidadY = -15;
		}
	
	
	
//	public boolean colisionIslas()
//	{
//		boolean colision=false;
//		for(int i=0; i<islas.length; i++)
//		{
//			if(islas[i].limiteSuperior()==tortugas[i].limiteInferior())
//			{
//				colision=true;
//			}
//			if(colision==true)
//			{
//				return tortugas[i].colisionInferior=true;
//			}
//			return false;
//		}
//		return false;
//	}
	
			//dibujar nomos
		for(int i=0; i<gnomos.length; i++) {
			gnomos[i].dibujarse(entorno);
		}
			
			
		for(int i=0;  i<gnomos.length; i++) {
			if(gnomos[i].apoyado) {
				gnomos[i].seMueveDerecha();
			}
			if(gnomos[i].limiteIzquierdo() < islas[i].limiteIzquierdo() || gnomos[i].limiteDerecho() >islas[i].limiteDerecho()) {
				gnomos[i].y+=5;
			}
			if (gnomos[i].limiteInferior() >= islas[i].limiteSuperior() && gnomos[i].limiteInferior() <= islas[i].limiteSuperior() + 5) { // Ajusta el rango según sea necesario
				gnomos[i].apoyado = false; // Establece que está apoyado
				// Detén el movimiento horizontal
			} else {
				gnomos[i].apoyado = true; // Si no está en la isla, no está apoyado
			}
		}
//			this.x+=5;
//			if(this.x > limiteIzquierdo() || this.x < limiteDerecho() ) {
//				this.y+=5;
//			}
	
	
	
	}

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
