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
//	Gnomos[] gnomos;
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
		this.tortugas = new Tortugas[10];
		for (int i = 0; i<this.tortugas.length; i++) {
			this.tortugas[i] = new Tortugas();
		}
		
		// Generación de de islas por filas
		
		int islasPorFila = 5;
		this.islas = new Islas[islasPorFila * (islasPorFila + 1) / 2];
		double ejeY = 110;
		int index = 0;
		
		for(int fila = 0; fila < islasPorFila; fila++)
		{
			double ejeX = 400 - (fila*80);
			for(int i = 0; i <= fila; i++)
			{
				this.islas[index] = new Islas(ejeX, ejeY, 100, 30) ;
				
				ejeX = ejeX+100+60; //
				index++;
			}
			ejeY = ejeY+104;
		}
		
		//Generar a Pep
		pep = new Pep(400, entorno.alto()-110);
		
		//generar gnomos
//		this.gnomos = new Gnomos[1];
//		for(int i = 0; i<this.gnomos.length; i++) 
//		{
//			this.gnomos[i] = new Gnomos(400,islas[0].limiteSuperior()-10,10,20);
//		}
		
		
		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	
	public void tick() {
		
		// Procesamiento de un instante de tiempo
		int sec= entorno.tiempo()/1000;
		segundos= sec%60;
		int min = sec/60;
		minutos= min%60;
		int hor= min/60;
		horas = hor%60;
		
		
		// Generación de Pep
		//Colisiones de Pep
		//El siguiente código comprueba si Pep está apoyado sobre una isla
		boolean apoyadoEnAlgunaIsla = false; // Variable temporal
		int enQueIslaEsta=0;
		for (int numIsla=0; numIsla < islas.length; numIsla++) {
			if (pep.limiteInferior() == islas[numIsla].limiteSuperior() 
				&& pep.limiteIzquierdo() > islas[numIsla].limiteIzquierdo() - pep.ancho 
				&& pep.limiteDerecho() < islas[numIsla].limiteDerecho() + pep.ancho) {
				apoyadoEnAlgunaIsla = true; // Se ha encontrado una isla
				enQueIslaEsta = numIsla;
				break; // Salir del bucle, ya no es necesario seguir revisando
			}
		}
		pep.apoyado = apoyadoEnAlgunaIsla; // Asignar el resultado final a pep
				
				
		//Este código comprueba si Pep al saltar choca con el limite
		//inferior o los límites laterales de aluna de las islas
		boolean chocaConAlgunaIsla = false;
		for (int numIsla=0; numIsla < islas.length; numIsla++) {
			if (!pep.apoyado) {
				// Comprobar colisiones
				if (pep.limiteSuperior() == islas[numIsla].limiteInferior() || 
						pep.limiteIzquierdo() == islas[numIsla].limiteDerecho() || 
				        pep.limiteDerecho() == islas[numIsla].limiteIzquierdo()) {
				        chocaConAlgunaIsla = true;
				        break; // Salir del bucle si hay colisión
				}
			}
		}
		pep.chocaCon = chocaConAlgunaIsla;
		
		// Dibuja y toma el movimiento de Pep
		
		pep.dibujarse(entorno);
		
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && pep.getX() > 5 && pep.apoyado == true)
			pep.moverIzquierda();
		if (entorno.estaPresionada(entorno.TECLA_DERECHA) && pep.getX() < entorno.ancho() - 5 && pep.apoyado == true)
			pep.moverDerecha();
		if (entorno.estaPresionada(entorno.TECLA_ARRIBA) && pep.apoyado == true)
		{
			pep.saltando = true;
			pep.salto(entorno);
			if(pep.tieneQueMoverse) {
				for (int numIsla=0; numIsla < islas.length; numIsla++) {
					double rangoDeCercaniaIzq = islas[numIsla].limiteIzquierdo()-30;
					double rangoDeCercaniaDer = islas[numIsla].limiteDerecho()+30;
					if(pep.centro() <= islas[numIsla].limiteIzquierdo() && pep.centro() >= rangoDeCercaniaIzq && pep.limiteInferior() >= islas[enQueIslaEsta].limiteSuperior()-110) {
						pep.y=islas[enQueIslaEsta].limiteSuperior()-110-pep.alto;
						pep.x=islas[numIsla].limiteIzquierdo();
					}
					if(pep.centro() >= islas[numIsla].limiteDerecho() && pep.centro() <= rangoDeCercaniaDer && pep.limiteInferior() >= islas[enQueIslaEsta].limiteSuperior()-110) {
						pep.y=islas[enQueIslaEsta].limiteSuperior()-110-pep.alto;
						pep.x=islas[numIsla].limiteDerecho()-pep.ancho;
					}
					pep.tieneQueMoverse=false;
				}
			}
			if(!pep.apoyado || pep.chocaCon) {
				pep.caer();
			}
		}
		pep.caer();	
//		if (pep.centro()==600) {
//			pep.x=400;
//			pep.y=(entorno.alto()-110);
//		}
		
		
		

		
		//Generación de las tortugas			
		if (segundos>0) { //Condicion para que se genere segundos despues de iniciar el juego
			for (int i = 0; i < tortugas.length; i++) {
				tortugas[i].dibujarse(entorno, tortugas[i].apoyado);
				tortugas[i].caer();
				boolean tortugaEnUnaIsla = false;
				double extremo1=0;
				double extremo2=0;
				for(int numIsla = 0; numIsla < islas.length; numIsla++) {
					if (islas[numIsla].limiteSuperior() == tortugas[numTortuga].limiteInferior() && 
						(islas[numIsla].limiteIzquierdo()) < tortugas[numTortuga].limiteIzquierdo() && 
						(islas[numIsla].limiteDerecho()) > tortugas[numTortuga].limiteDerecho()){
						tortugaEnUnaIsla = true;
						extremo1 = islas[numIsla].limiteIzquierdo();
						extremo2 = islas[numIsla].limiteDerecho();
						break;
					}
				}
				tortugas[i].apoyado = tortugaEnUnaIsla;
				if(tortugas[i].apoyado) {
					if(tortugas[i].limiteIzquierdo()!=extremo1 || tortugas[numTortuga].limiteDerecho()!=extremo2) {
						tortugas[i].mover();
					}
					else {
						if(tortugas[i].derecha==false) {
							tortugas[i].derecha=true;
						}
						else {
							tortugas[i].derecha=false;
						}
						tortugas[i].mover();
					}
				}
			}
		}

		

		// Generación de de islas por filas
		for (int i = 0; i < islas.length; i++) {
			islas[i].generarIslas(entorno);
		}
	
	
		
		
		
		
		
		//dibujar gnomos
		
//		for(int i=0;  i<gnomos.length; i++) {
//		boolean fueraIsla=false;
//		gnomos[i].dibujarse(entorno);
//		
//		
////		if (gnomos[i].limiteInferior() == islas[i].limiteSuperior()) { 
//			
//			gnomos[i].seMueve();
//		
//		
//		
//		//este cae bien pero los if esta mal
//			if(gnomos[i].limiteInferior() == islas[i].limiteSuperior() && gnomos[i].limiteIzquierdo() >islas[i].limiteDerecho()+5) {
//				gnomos[i].y++;
//				gnomos[i].x=islas[i].limiteDerecho()+5;
//				fueraIsla=true;
//			}
//			else
//			{
//				if(gnomos[i].limiteDerecho() < islas[i].limiteIzquierdo()-5) {
//					gnomos[i].y++;
//					gnomos[i].x=islas[i].limiteIzquierdo()-5;
//					fueraIsla=true;
//				}
//			}
////		}
//
//		
//	}
		
		
		
//		 /*
//		for(int i=0;  i<gnomos.length; i++) {
//			boolean fueraIsla=true;
//			gnomos[i].dibujarse(entorno);
//			
//			gnomos[i].seMueve();
//			
//			if(gnomos[i].limiteIzquierdo() >islas[i].limiteDerecho()+2) {
//				gnomos[i].x=islas[i].limiteDerecho()+2;
//				gnomos[i].y++;
//				fueraIsla=true;
//			}
//			else
//			{
//				if(gnomos[i].limiteDerecho() < islas[i].limiteIzquierdo()-2) {
//					gnomos[i].x=islas[i].limiteIzquierdo()-2;
//					gnomos[i].y++;
//					fueraIsla=true;
//				}
//			}
//			
//			if (gnomos[i].limiteInferior() == islas[i].limiteSuperior()) { 
//				gnomos[i].apoyado = false; 
//				
//			} else {
//				gnomos[i].apoyado = false; 
//			}
//			
//		}
	}
		
	
////		*/boolean colsionGnomoisla(Gnomos g , Islas[]) {
//	for (Islas isla: isla) {
//		if(islas!=null) {
//			if(g.limiteInferior()==isla.limiteSuperior() || g.limiteInferior()>isla.limiteSuperior()) && (g.limiteDerecho()>isla.limiteIzquierdo()) || g.limiteIzquierdo()<
//		
//	}
//
//	*/
	
	
	
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
