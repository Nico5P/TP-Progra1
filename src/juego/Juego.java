package juego;


import java.awt.Color;
import java.util.Random;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	// Variables y métodos propios de cada grupo
	// ...
	Interfaz Interfaz;
	Pep pep;
	Islas[] islas;
	Tortugas[] tortugas;
	Gnomos[] gnomos;
	
	int islasPorFila;
	int anchoIsla;
	int altoIsla;
	int espacioHorizontal;
	int espacioVertical;
	int vidas = 3;
	int gnomosPerdidos=0;
	int gnomosSalvados=0;
	int gnomosEnPantalla=0;
	int numTortuga=0;
	int tortugasEliminadas = 0;
	int numIsla=0;
	int horas;
	int minutos;
	int segundos;
	int tiempoJugando = 0;
    int tiempoActual = tiempoJugando;
    boolean Jugando;
    
    
    // Método para crear a Pep
    private Pep crearPep(double x, double y) {
    	return new Pep(x, y);
    }
	
	public Juego()
	{
		
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP; Balbi, Gomez, Pereira, Pereyra", 800, 600);
		entorno.colorFondo(Color.pink);
		
		
		// Inicializar lo que haga falta para el juego
		//...
		
		//Inicia la interfaz
        this.Interfaz = new Interfaz();
        
       
        //Generación de tortugas
      	this.tortugas = new Tortugas[10];
      	for (int i = 0; i<this.tortugas.length; i++) 
      	{
      		this.tortugas[i] = new Tortugas();
      	}

      	//generar gnomos

      	gnomos=new Gnomos[6];
      	for(int i = 0; i<gnomos.length; i++) 
      	{
      		this.gnomos[i] = new Gnomos(400,83,10,20);
      	}
      		

      	// Generación de islas
      	int islasPorFila = 5;
      	int anchoIsla = 100;
      	int altoIsla = 30;
      	int espacioHorizontal = 60;
      	int espacioVertical = 104;
      	double inicioY = 110;
      	
      	// Inicialización del arreglo de islas
      	this.islas = new Islas[islasPorFila * (islasPorFila + 1) / 2];
      	double posY = inicioY; // Renombrado para claridad
      	int indiceIsla = 0;
      	
      	// Creación de islas
      	for (int fila = 0; fila < islasPorFila; fila++) {
      		double posX = 400 - (fila * 80); // Renombrado para claridad
      		for (int i = 0; i <= fila; i++) {
      			this.islas[indiceIsla] = new Islas(posX, posY, anchoIsla, altoIsla);
      			posX += anchoIsla + espacioHorizontal; 
      			indiceIsla++;
     		    }
      		posY += espacioVertical;
      	}

      	// Selección aleatoria de una isla inferior para el inicio de "pep"
      	if (this.islas.length > 0) {
      		int filaInferior = islasPorFila - 1;
      		int islasEnFilaInferior = filaInferior + 1;
      		int primerIslaInferiorIndex = (islasPorFila * (islasPorFila - 1)) / 2;

      		// Selección aleatoria de una isla inferior
      		int islaAleatoriaIndex = primerIslaInferiorIndex + (int)(Math.random() * islasEnFilaInferior); 
      		Islas islaSeleccionada = this.islas[islaAleatoriaIndex];
      		
      		// Crear pep en la posición adecuada
      		pep = crearPep(islaSeleccionada.x, entorno.alto() - inicioY);
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
	
	
	
	
	public void tick() {

        // Incrementa el tiempo transcurrido desde que se inicia el juego
		
		// Procesamiento de un instante de tiempo
		int sec = entorno.tiempo()/1000;
		segundos= sec%60;
		int min = sec/60;
		minutos= min%60;
		int hor= min/60;
		horas = hor%60;
		
		 // Muestra las estadisticas
        dibujarStats();
        
		// Menu de pausa
        if (entorno.sePresiono(entorno.TECLA_ESCAPE)) 
        {
            Interfaz.noPausado();
        }

        if (Interfaz.pausado()) {
        	Jugando = false;
            if (entorno.sePresiono(entorno.TECLA_ARRIBA)) 
            {
                Interfaz.cambiarOpcion(-1);
            } 
            else if (entorno.sePresiono(entorno.TECLA_ABAJO)) 
            {
                Interfaz.cambiarOpcion(1);
            } 
            else if (entorno.sePresiono(entorno.TECLA_ENTER)) 
            {
                if (Interfaz.opcionElegida() == 0) {
                    Interfaz.noPausado(); // Continuar
                } 
                
                else if (Interfaz.opcionElegida() == 1) 
                {
                	Interfaz.noPausado();
                    reiniciar();
                    return; // Salir del método tick() para no procesar más lógica
                }
            }
            
            Interfaz.dibujarMenu(entorno);
            return;
        }
        
        if (Jugando = true) 
		{
			tiempoJugando ++;			
		}
		else 
		{
			tiempoJugando = tiempoActual;
		}
        
        // Comprobación de estado de juego
        if (vidas < 0) 
        {
        	reiniciar();
        	
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
			
		Random random = new Random();
		for(Gnomos g: this.gnomos) {
			if(g != null) {
				g.dibujarse(entorno);
				
				boolean estaEnIsla=false;
				int enIsla=0;
				for(int j=0; j < islas.length; j++) 
				{
					if(g.limiteInferior() == islas[j].limiteSuperior() && g.limiteIzquierdo() > islas[j].limiteIzquierdo()
					- g.ancho && g.limiteDerecho() < islas[j].limiteDerecho() + g.ancho)
					{	
						estaEnIsla=true;
						enIsla++;
						break;
					}
				}
				g.apoyado=estaEnIsla;
			}
			
					
			if(g.apoyado) 
			{
				if(!g.mirar) 
				{
					g.caminar=random.nextBoolean();
					g.mirar=true;
				}
				if(g.caminar) 
				{
					g.moverDerecho();
				}
				else 
				{
					g.moverIzquierda();
				}
						
						
			}
			else 
			{
				g.caida();
				g.mirar=false;
			}
			gnomosSalvados(g,pep);
			
			for(Tortugas t:this.tortugas) {
				gnomosPerdidos(g,t);
			}
			
				
		}
		   
		
		// Generación de de islas por filas
		for (int i = 0; i < islas.length; i++) 
		{
			islas[i].generarIslas(entorno);
		}
	
		// Dibuja todo de Pep
		pep.dibujarse(entorno);

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
		    vidas -= 1;
		}


		// Control de movimiento de pep
		boolean puedeMoverse = pep.apoyado;

		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && pep.getX() > 5 && puedeMoverse) {
		    pep.moverIzquierda();
		}

		if (entorno.estaPresionada(entorno.TECLA_DERECHA) && pep.getX() < entorno.ancho() - 5 && puedeMoverse) {
		    pep.moverDerecha();
		}

		if (entorno.sePresiono(entorno.TECLA_ARRIBA) && puedeMoverse) {
		    pep.salto();

		    // Lógica para comprobar si puede subir a la isla
		    if (pep.tieneQueMoverse) {
		        for (int numIsla = 0; numIsla < islas.length; numIsla++) {
		            double limiteIzq = islas[numIsla].limiteIzquierdo();
		            double limiteDer = islas[numIsla].limiteDerecho();
		            double rangoCercaniaIzq = limiteIzq - 30;
		            double rangoCercaniaDer = limiteDer + 30;

		            if (pep.limiteInferior() >= islas[enQueIslaEsta].limiteSuperior() - 110) {
		                if (pep.centro() <= limiteIzq && pep.centro() >= rangoCercaniaIzq) {
		                    pep.y = islas[numIsla].limiteSuperior() - 110 - pep.alto; // Ajusta la posición
		                    pep.x = limiteIzq; // Ajusta la posición
		                    pep.tieneQueMoverse = false; // No se moverá más
		                    break; // Sale del bucle si ya se ha movido
		                }

		                if (pep.centro() >= limiteDer && pep.centro() <= rangoCercaniaDer) {
		                    pep.y = islas[numIsla].limiteSuperior() - 110 - pep.alto; // Ajusta la posición
		                    pep.x = limiteDer - pep.ancho; // Ajusta la posición
		                    pep.tieneQueMoverse = false; // No se moverá más
		                    break; // Sale del bucle si ya se ha movido
		                }
		            }
		        }
		    }
		}


		// Llama a caer en el caso de que no esté apoyado
		if (!pep.apoyado || pep.chocaCon) {
		    pep.caer();
		}
	}
	
	
	public void gnomosSalvados(Gnomos gn, Pep pep) {
		if(gn ==null) {
			return;
		}
		for(int i=0; i <gnomos.length; i++) {
			Gnomos g=gnomos[i];
			if(g!= null && g.colisionaCon(pep) && pep.getY() > 350) {
				g.seReinicia(400, 83,10,20);
				gnomosSalvados++;
				break;
			}
		}	
	}
	
	public void gnomosPerdidos(Gnomos gn, Tortugas t) {
		if(gn ==null) {
			return;
		}
		
		for(int i=0; i<gnomos.length; i++) {
			Gnomos g=gnomos[i];
			for(Tortugas tortugas: this.tortugas) {
				if(tortugas !=null && g.colisionTortugas(t)) {
					g.seReinicia(400, 83,10,20);
					gnomosPerdidos++;
					break;
				}
			}
			if(g.limiteSuperior() > 600) {
				g.seReinicia(400,83, 10, 20);
				gnomosPerdidos++;
			}
		}
	}
	
	
	
	
	
    private void dibujarStats() {
        entorno.cambiarFont("Arial", 20, Color.BLACK);
        entorno.escribirTexto("Vidas: " + vidas, 10, 20);
        entorno.escribirTexto("Gnomos Salvados: " + gnomosSalvados, 10, 40);
        entorno.escribirTexto("Gnomos Perdidos: " + gnomosPerdidos, 10, 60);
        entorno.escribirTexto("Gnomos en Pantalla: " + gnomosEnPantalla, 10, 80);
        entorno.escribirTexto("Tortugas Asesinadas: " + tortugasEliminadas, 10, 100);
        entorno.escribirTexto("Tiempo: " + tiempoJugando/60, 10, 120);
        entorno.cambiarFont("Arial", 20, Color.BLACK);
    }
    

	
    // Reinicia todas las variables y vuelve a iniciar el juego
    private void reiniciar() 
    {
    	vidas = 3;
    	tiempoJugando = 0;
    	gnomosSalvados = 0;
    	gnomosPerdidos=0;
    	tortugasEliminadas = 0;
    }
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
