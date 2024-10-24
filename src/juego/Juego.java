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
	
	//todo lo relacionado y necesario para "islas"
	int numIsla=0;
	Islas[] islas;
	
	//todo lo relacionado y necesario para "pep"
	Pep pep;

	//todo lo relacionado y necesario para "tortugas"
	Tortugas[] tortugas;
	int numTortuga=0;
	
	//todo lo relacionado y necesario para "gnomos"
//	Gnomos[] gnomos;
	
	//las variables de tiempo
	int horas;
	int minutos;
	int segundos;
	
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP; Balbi, Gomez, Pereira, Pereyra", 800, 600);
		entorno.colorFondo(Color.pink);
		
		// Inicializar lo que haga falta para el juego
		
		
		// Generación de islas 
		int islasPorFila = 5;
		this.islas = new Islas[islasPorFila * (islasPorFila + 1) / 2];
		double ejeY = 110;
		int index = 0;

		for (int fila = 0; fila < islasPorFila; fila++) 
		{
		    double ejeX = 400 - (fila * 80);
		    for (int i = 0; i <= fila; i++) 
		    {
		        this.islas[index] = new Islas(ejeX, ejeY, 100, 20);
		        ejeX += 100 + 60; 
		        index++;
		    }
		    ejeY += 104;
		}

		
		if (this.islas.length > 0)
		{
			//este condicional se asegura de que pep solo pueda aparecer en las islas del arreglo "inferior" para evitar que elija una superior y termine cayendose
		    int filaInferior = islasPorFila - 1;
		    int islasEnFilaInferior = filaInferior + 1;
		    int primerIslaInferiorIndex = (islasPorFila * (islasPorFila - 1)) / 2;
		    //mientras que este condicional elige una isla del arreglo anteriormente filtrado, al azar, y verifica su x para que pep pueda aparecer encima de una de ellas al iniciar el juego
		    int islaAleatoriaIndex = primerIslaInferiorIndex + (int)(Math.random() * islasEnFilaInferior); 
		    Islas islaSeleccionada = this.islas[islaAleatoriaIndex];
		    pep = new Pep(islaSeleccionada.XdeIsla(), entorno.alto() - 110);
		}


		//Generación de tortugas
		this.tortugas = new Tortugas[10];
		for (int i = 0; i<this.tortugas.length; i++) {
			this.tortugas[i] = new Tortugas();
		}
		
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
		
		
		// Generación de de islas por filas
		for (int i = 0; i < islas.length; i++) {
			islas[i].generarIslas(entorno);
		}
	
	
		

		// Generación y colisiones de Pep, este codigo se encarga de comprobar si pep esta sobre una isla y en cual
		boolean apoyadoEnAlgunaIsla = false; // Variable temporal
		int enQueIslaEsta = 0;

		for (int numIsla = 0; numIsla < islas.length; numIsla++) 
		{
		    // 
		    if (pep.limiteInferior() >= islas[numIsla].limiteSuperior() && 
		        pep.limiteInferior() <= islas[numIsla].limiteSuperior() + 5 && // Esto es el margen, por decirlo asi
		        pep.limiteIzquierdo() < islas[numIsla].limiteDerecho() && 
		        pep.limiteDerecho() > islas[numIsla].limiteIzquierdo()) 
		    {
		        
		        apoyadoEnAlgunaIsla = true; // Si se encuentra una isla la guarda y sale del bucle
		        enQueIslaEsta = numIsla;
		        break;
		    }
		}

		pep.apoyado = apoyadoEnAlgunaIsla; // Asigna una isla a pep

		// Este código comprueba si Pep al saltar llega al limite de salto 
		// inferior o los límites laterales de alguna de las islas
		boolean chocaConAlgunaIsla = false;
		for (int numIsla = 0; numIsla < islas.length; numIsla++) 
		{
		    if (!pep.apoyado) 
		    {
		        if (pep.limiteSuperior() >= islas[numIsla].limiteInferior() && 
		            (pep.limiteIzquierdo() <= islas[numIsla].limiteDerecho() && 
		             pep.limiteDerecho() >= islas[numIsla].limiteIzquierdo())) 
		        {
		            chocaConAlgunaIsla = true; // Pep choca con la isla y sale del bucle si hay colision
		            break; 
		        }
		    }
		}
		
		pep.chocaCon = chocaConAlgunaIsla;

		// Dibuja todo de Pep
		pep.dibujarse(entorno);

		if (!pep.apoyado) // Si pep no esta apoyado se cae 
		{
		    pep.caer(); 
		}

		if (pep.limiteInferior() > entorno.alto()) // Si cae al vacío resta una vida y pep vuelve a aparecer en la isla de donde cayo
		{
		    pep.contador--;
		    pep.x = 400;
		    pep.y = entorno.alto() - 110;
		    pep.apoyado = true;
		}

		// Control de movimiento de pep
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && pep.getX() > 5 && pep.apoyado)
		    pep.moverIzquierda();

		if (entorno.estaPresionada(entorno.TECLA_DERECHA) && pep.getX() < entorno.ancho() - 5 && pep.apoyado)
		    pep.moverDerecha();

		if (entorno.estaPresionada(entorno.TECLA_ARRIBA) && pep.apoyado) {
		    pep.salto();

		    // Lógica para comprobar si puede subir a la isla
		    if (pep.tieneQueMoverse) {
		        for (int numIsla = 0; numIsla < islas.length; numIsla++) {
		            double rangoDeCercaniaIzq = islas[numIsla].limiteIzquierdo() - 30;
		            double rangoDeCercaniaDer = islas[numIsla].limiteDerecho() + 30;
		            
		            if (pep.centro() <= islas[numIsla].limiteIzquierdo() && 
		                pep.centro() >= rangoDeCercaniaIzq && 
		                pep.limiteInferior() >= islas[enQueIslaEsta].limiteSuperior() - 110) {
		                
		                pep.y = islas[numIsla].limiteSuperior() - 110 - pep.alto; // Ajusta la posición
		                pep.x = islas[numIsla].limiteIzquierdo(); // Ajusta la posición
		                pep.tieneQueMoverse = false; // No se moverá más
		            }
		            
		            if (pep.centro() >= islas[numIsla].limiteDerecho() && 
		                pep.centro() <= rangoDeCercaniaDer && 
		                pep.limiteInferior() >= islas[enQueIslaEsta].limiteSuperior() - 110) {
		                
		                pep.y = islas[numIsla].limiteSuperior() - 110 - pep.alto; // Ajusta la posición
		                pep.x = islas[numIsla].limiteDerecho() - pep.ancho; // Ajusta la posición
		                pep.tieneQueMoverse = false; // No se moverá más
		            }
		        }
		    }
		}

		// Llama a caer en el caso de que no esté apoyado
		if (!pep.apoyado || pep.chocaCon) {
		    pep.caer();
		}
	
		
	

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
