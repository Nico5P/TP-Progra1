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
	int numIsla=0;
	int numTortuga=0;
	
	
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
		this.gnomos = new Gnomos[1];
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
		
		for(int numTortuga=0; numTortuga<tortugas.length; numTortuga++)
		{
			for(int numIsla=0; numIsla<islas.length; numIsla++)
			{
				if(islas[numIsla].limiteSuperior()==tortugas[numTortuga].limiteInferior() && (islas[numIsla].limiteIzquierdo())<tortugas[numTortuga].limiteIzquierdo() && (islas[numIsla].limiteDerecho())>tortugas[numTortuga].limiteDerecho())
				{
					if(tortugas[numTortuga].derecha==true)
					{
						tortugas[numTortuga].x++;
					}
					else
					{
						tortugas[numTortuga].x--;
					}
					tortugas[numTortuga].ubicada=true;
					if(tortugas[numTortuga].getX()==islas[numIsla].limiteIzquierdo())
					{
						tortugas[numTortuga].derecha=true;
					}
					if(tortugas[numTortuga].getX()==islas[numIsla].limiteDerecho())
					{
						tortugas[numTortuga].ubicada=false;
					}
					
				}
			}
		}
		boolean apoyadoEnAlgunaIsla = false; // Variable temporal
		for(int numIsla=0; numIsla<islas.length; numIsla++)
		{
			if(pep.limiteInferior()==islas[numIsla].limiteSuperior() && pep.limiteIzquierdo()>islas[numIsla].limiteIzquierdo()-40 && pep.limiteDerecho()<islas[numIsla].limiteDerecho()+40)
			{
				apoyadoEnAlgunaIsla = true; // Se ha encontrado una isla
		        break; // Salir del bucle, ya no es necesario seguir revisando
			}
		}
		pep.apoyado = apoyadoEnAlgunaIsla; // Asignar el resultado final
//		for(int ii=0; ii<tortugas.length; ii++)
//		{
//			for(int iii=0; iii<islas.length-1; iii++)
//			{
//				if(tortugas[ii].ubicada=true)
//				{
//						
//				}
//				
//			}
//		}
			
//		for(int ii=0; ii<tortugas.length; ii++)
//		{
//			for(int iii=0; iii<islas.length; iii++)
//			{
//				tortugas[ii].tortugaUbicada(islas[iii].limiteSuperior(), islas[iii].limiteIzquierdo(), islas[iii].limiteDerecho());
//					
//			}
//		}
		
		

		//Generación de las tortugas			
		if (segundos>0)
		{
			for (int i=0;i<tortugas.length;i++){
//				for(int iii=0; iii<islas.length; iii++)
//					{
//						tortugas[i].tortugaUbicada(islas[iii].limiteSuperior(), islas[iii].limiteIzquierdo(), islas[iii].limiteDerecho());		
//					}
				tortugas[i].dibujarse(entorno, tortugas[i].ubicada);
			}
		}

	

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
		if (entorno.sePresiono(entorno.TECLA_ARRIBA))
		{
			pep.saltando = true;
			pep.salto(entorno);
			
			pep.saltando= false;
			
        	pep.VelocidadY = -15;
		}
		pep.caer(pep.saltando, pep.apoyado);
	
	
		//dibujar gnomos
		for(int i=0;  i<gnomos.length; i++) {

			gnomos[i].dibujarse(entorno);
			
			
			
			
			if(gnomos[i].apoyado) {
				gnomos[i].seMueveDerecha();
			
			}
			//este esta bien el if pero caen asustados
			if(gnomos[i].limiteDerecho() < islas[i].limiteIzquierdo() || gnomos[i].limiteIzquierdo() > islas[i].limiteDerecho()) {
				gnomos[i].y+=5;
				gnomos[i].x=islas[i].limiteIzquierdo();
			}
			
			//este cae bien pero los if esta mal
//			if(gnomos[i].limiteIzquierdo() < islas[i].limiteIzquierdo() || gnomos[i].limiteDerecho() >islas[i].limiteDerecho()) {
//				gnomos[i].y+=5;
//				gnomos[i].x=islas[i].limiteDerecho();
//			}
			
			if (gnomos[i].limiteInferior() == islas[i].limiteSuperior()) { 
				gnomos[i].apoyado = true; 
				
			} else {
				gnomos[i].apoyado = true; 
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
