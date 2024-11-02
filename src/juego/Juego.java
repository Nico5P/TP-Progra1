package juego;


import java.awt.Color;
import java.util.Random;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	Interfaz Interfaz;
	Pep pep;
	Islas[] islas;
	Tortugas[] tortugas;
	Gnomos[] gnomos;
	Navecita navecita;
	BoladeFuego bolaDeFuego;
	
	// Variables y métodos propios de cada grupo
	int islasPorFila;
	int anchoIsla;
	int altoIsla;
	int espacioHorizontal;
	int espacioVertical;
	int vidas;
	int gnomosPerdidos;
	int gnomosSalvados;
	int gnomosEnPantalla;
	int tortEliminadas;
	int tortEnPantalla;
	int numIsla;
	int horas;
	int minutos;
	int segundos;
	int tiempoJugando;
	int tiempoActual = tiempoJugando;
    boolean Jugando;
    boolean cooldownGnomos;
    boolean gnomoGenerado;
    int esperaGnomos;
    int enQueIslaEsta;
    int conQueIslaChoca;
    
	
    public Juego() {
    	
    	/*
    	 * Inicializacion de todas las variables para que el juego funcione
    	 */
    	
        iniciarJuego();
        iniciarInterfaz();
        generarTortugas();
        generarGnomos();
        generarIslas();
        generarPep();
        generarNavecita();

        /*
         * Inicia el juego cargando los valores iniciales
         */
        
        this.entorno.iniciar();
        this.Jugando = true; 
    	this.vidas = 3;
    	this.tiempoJugando = 0;
    	this.gnomosSalvados = 0;
    	this.gnomosPerdidos=0;
    	this.tortEliminadas = 0;

    }

    private void iniciarJuego() {
        this.entorno = new Entorno(this, "Proyecto para TP; Balbi, Gomez, Pereira, Pereyra", 800, 600);
        entorno.colorFondo(Color.pink);
    }

    private void iniciarInterfaz() {
        this.Interfaz = new Interfaz();
    }

    private void generarTortugas() {
        this.tortugas = new Tortugas[5];
        for (int i = 0; i < this.tortugas.length; i++) {
            this.tortugas[i] = new Tortugas();
        }
    }
    
    private void generarGnomos() {
    	gnomosEnPantalla = 0;
        gnomos = new Gnomos[4];
        cooldownGnomos=false;
        actualizarTiempo();
        for (int i = 0; i < gnomos.length;) 
        {
        	if(gnomosEnPantalla!=4 && cooldownGnomos) { //Condición para que pueda generarse un gnomo nuevo
        		this.gnomos[i] = new Gnomos(400, 83, 10, 20);
                gnomosEnPantalla++;
                gnomoGenerado=true; //Condición para que no se generen todos los gnomos juntos. Cambia el valor de cooldownGnomos
                break;
        	}
        }
    }
    
    private void generarIslas() {
        int islasPorFila = 5;
        int anchoIsla = 100;
        int altoIsla = 30;
        int espacioHorizontal = 60;
        int espacioVertical = 100;
        int inicioY = 110;

        // Inicia el arreglo de islas
        this.islas = new Islas[islasPorFila * (islasPorFila + 1) / 2];
        double posY = inicioY;
        int indiceIsla = 0;

        for (int fila = 0; fila < islasPorFila; fila++) {
            double posX = 400 - (fila * 80);
            for (int i = 0; i <= fila; i++) {
                this.islas[indiceIsla] = new Islas(posX, posY, anchoIsla, altoIsla);
                posX += anchoIsla + espacioHorizontal;
                indiceIsla++;
            }
            posY += espacioVertical;
        }

        for (Islas isla : islas) {
            isla.generarIslas(entorno);
        }
    }


    private void generarPep() {
        if (this.islas.length > 0) {
            int primerIslaInferiorIndex = (islasPorFila * (islasPorFila - 1)) / 2;
            int islaAleatoriaIndex = primerIslaInferiorIndex + (int)(Math.random() * islasPorFila); // Selección aleatoria
            Islas islaSeleccionada = this.islas[islaAleatoriaIndex];
            pep = generarPep(islaSeleccionada.x, 480);
        }
    }

    public Pep generarPep(double x, double y) 
    {
    	return new Pep(x, y);
    }
    
    private void generarNavecita() {
        navecita = new Navecita(370, 560);
    }

	/*
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	
    public void tick() {
        if (entorno.sePresiono(entorno.TECLA_ESCAPE)) {
            Interfaz.noPausado(); // Alterna entre pausado y no pausado
        }

        if (Interfaz.pausado()) {
            Interfaz.dibujarMenu(entorno); // Dibuja el menú de pausa
            return; // No actualiza el juego si está pausado
        }
        if (Jugando) {
            tiempoJugando++; //Aumenta el contador de tiempo en pantalla
        } else {
            tiempoJugando = tiempoActual; //Si se pausa el juego lo detiene y muestra el ultimo valor
        }

        actualizarTiempo();
        estadisticas();
        generarIslas();
        
        funcionamientoTortugas();
        funcionamientoGnomos();
        funcionamientoDePep();
        funcionamientoBolaDeFuego();
        controlarMovimientoPep();
        dibujarNave();
    }


	private void actualizarTiempo() {
	    int sec = entorno.tiempo() / 1000;
	    segundos= sec%60;
		int min = sec/60;
		minutos= min%60;
		int hor= min/60;
		horas = hor%60;
		
		if(segundos%10==0 && !gnomoGenerado) //Calculo el tiempo de esepra para que pueda generarse otro gnomo
		{
			if(!gnomoGenerado) { //Condicion para que pueda generarse otro Gnomo
				cooldownGnomos=true;
			}
		}
		else if(segundos%15==0 && gnomoGenerado){ //Condicion para cambiar el estado de gnomoGenerado a false
			cooldownGnomos=false;
			gnomoGenerado=false;
		}
		else 
		{
			cooldownGnomos=false;
		}
		
	}

	private void funcionamientoGnomos() {
	    Random random = new Random();
	    for(int i=0; i<gnomos.length;i++) {
	    	Gnomos g=gnomos[i];
	    	actualizarTiempo();
	    	if(g==null && cooldownGnomos && !gnomoGenerado) {
	    		gnomos[i]= new Gnomos(400,83,10,20);
	    		gnomoGenerado=true;
	    	}
	        if (g != null) {
	            g.dibujarse(entorno);
	            EstadoDeGnomos(g, random);
	            
	            if (pep.colisionGnomos(g)&& pep.getY() > 350) {
	                gnomos[i] = null;  
	                gnomosSalvados++;
	            }
	            
	            if(g!= null && g.colisionNavecita(navecita)) {
//					g.seReinicia(400, 83,10,20);
					gnomos[i]=null;
					gnomosSalvados++;
					
				}
	            
	            for (Tortugas t : tortugas) {
	                if (t != null && g.colisionTortugas(t)) {
	                	gnomos[i] = null; 
	                    gnomosPerdidos++;
	                }
	            }
	            
	            if (g.limiteSuperior() > entorno.alto()) {
	            	gnomos[i] = null; 
	                gnomosPerdidos++;
	            }
	        }
	    }
	}

	
	
	private void EstadoDeGnomos(Gnomos g, Random random) {
	    boolean estaEnIsla = false;
	    for (Islas isla : islas) {
	        if (g.limiteInferior() == isla.limiteSuperior() &&
	            g.limiteIzquierdo() > isla.limiteIzquierdo() - g.ancho &&
	            g.limiteDerecho() < isla.limiteDerecho() + g.ancho) {
	            estaEnIsla = true;
	            break;
	        }
	    }
	    g.apoyado = estaEnIsla;

	    if (g.apoyado) {
	        g.caminar = !g.mirar ? random.nextBoolean() : g.caminar;
	        g.mirar = true;
	        if (g.caminar) {
	            g.moverDerecho();
	        } else {
	            g.moverIzquierda();
	        }
	    } else {
	        g.caida();
	        g.mirar = false;
	    }
//	    gnomosSalvados(g, pep);
//
//	    for (Tortugas t : this.tortugas) {
//	        gnomosPerdidos(g, t);
//	    }
	}

	private void funcionamientoTortugas() {
		Random random1=new Random();
		for(int i=0; i<tortugas.length; i++) {
				Tortugas t1= tortugas[i];
				if(t1!=null) {
					t1.dibujarse(entorno, Jugando);
//					t.caer();
					estadoTortugas(t1,random1);
				}
		}
	}
	
	public void estadoTortugas(Tortugas t, Random random1) {
		boolean enIsla= false;
		boolean otro=false;
		for(Islas isla:islas) {
			//Ver si la tortuga toca la isla(?
			if(t.limiteInferior() == isla.limiteSuperior()&&
					t.limiteIzquierdo()> isla.limiteIzquierdo()-t.ancho &&
					t.limiteDerecho()< isla.limiteDerecho()+t.ancho) {
				enIsla=true;
				t.apoyado=true;
				iniciarMovHorizontal(t, random1, isla);
				otro=true;
				break;
			}
		}
		if(!enIsla) {
			t.apoyado=false;
			t.mira=false;
			t.caer();
		}
			
	}
	
	private void iniciarMovHorizontal(Tortugas t, Random random1, Islas isla) {
//

		t.mover(); // Mueve la tortuga en la dirección actual
		
	    if (t.caminar) {
	        if (t.limiteIzquierdo() >= isla.limiteDerecho() - (t.ancho / 2)) {
	            t.caminar = false; // Cambiar dirección a izquierda
	        }
	    } else {
	        if (t.limiteDerecho() <= isla.limiteIzquierdo() + (t.ancho / 2)) {
	            t.caminar = true; // Cambiar dirección a derecha
	        }
	    }
		
	}

	
	/*
	 * El metodo se encarga de dibujar a pep
	 */
	
	private void funcionamientoDePep() {
	    pep.dibujarse(entorno);
	    
	    //Compruebo si Pep esta apoyado sobre una isla

	    boolean apoyadoEnAlgunaIsla = false;

	    for (int numIsla = 0; numIsla < islas.length; numIsla++) {
	        if (pep.limiteInferior() >= islas[numIsla].limiteSuperior() &&
	            pep.limiteInferior() <= islas[numIsla].limiteSuperior() + 5 &&
	            pep.limiteIzquierdo() < islas[numIsla].limiteDerecho() &&
	            pep.limiteDerecho() > islas[numIsla].limiteIzquierdo()) {
	            apoyadoEnAlgunaIsla = true;
	            enQueIslaEsta = numIsla; //Guardo el indice de la isla sobre la que Pep esta apoyado
	            break;
	        }
	    }
	    
	    pep.apoyado = apoyadoEnAlgunaIsla;
	    
	    //Compruebo si el limite superior de Pep colisiona con el limite inferior de una isla
	    
	    boolean debajoDeUnaIsla = false;
	    
	    for (int numIsla = 0; numIsla < islas.length; numIsla++) {
	    	if (pep.limiteSuperior() >= islas[numIsla].limiteInferior() &&
		        pep.limiteIzquierdo() <= islas[numIsla].limiteDerecho() + pep.ancho + 3 &&
		        pep.limiteDerecho() >= islas[numIsla].limiteIzquierdo() - pep.ancho - 3) {
	    		conQueIslaChoca = numIsla; //Guardo el indice de la isla con la que pep colisiona
	    		if(islas[enQueIslaEsta].limiteSuperior() <= islas[conQueIslaChoca].limiteInferior()-110 &&
	    			islas[enQueIslaEsta].limiteSuperior() >= islas[conQueIslaChoca].limiteInferior()-90) {
	    			debajoDeUnaIsla = true;
	    			break;
	    		}	
	    	}
	    }
	    
	    pep.debajoDe = debajoDeUnaIsla;

	    if (!pep.apoyado) {
	        pep.caer();
	    }

	    if (pep.limiteInferior() > entorno.alto()) {
	    	generarPep();
	        pep.apoyado = true;
	        vidas--;
	        if (vidas < 0) {
		        generarPep();
		    }
	    }
	}

	
	/*
	 * Se encarga de generar la bola de fuego y a su vez verifica su colision con alguna tortuga
	 * de ser el caso eliminara la tortuga y aumentara el contador de "tortugas elimanadas"
	 * De no haber colisionado con una tortuga, si toca el borde izquierdo o derecho de la pantalla
	 * desaparecera
	 */

	private void funcionamientoBolaDeFuego() {
	    if (bolaDeFuego != null && bolaDeFuego.fueDisparada()) {
	        bolaDeFuego.disparo();

	        for (int i = 0; i < tortugas.length; i++) {
	            Tortugas t = tortugas[i];
	            if (t != null && bolaDeFuego.colisionTortugas(t)) {
	                tortugas[i] = null;
	                tortEliminadas++;
	            }
	        }

	        if (bolaDeFuego.getX() < 0 || bolaDeFuego.getX() > entorno.ancho()) {
	            bolaDeFuego = null;
	        }
	    }

	    if (bolaDeFuego != null) {
	        bolaDeFuego.dibujarse(entorno);
	    }
	}
    
    /*
     * Controla el movimiento de pep mediante las entradas del teclado
     */
    
    private void controlarMovimientoPep() {
        boolean puedeMoverse = pep.apoyado;

        if (entorno.estaPresionada(entorno.TECLA_ABAJO) && pep.getX() > 5 && pep.ultimaDireccion()) {
            if (bolaDeFuego == null || !bolaDeFuego.fueDisparada()) {
            //    bolaDeFuego = new BolaDeFuego(pep.getX(), pep.getY(), pep.ultimaDireccion());  no me funciona
            }
        }

        if (bolaDeFuego != null && bolaDeFuego.fueDisparada()) {
            bolaDeFuego.disparo();
        }

	    if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && pep.getX() > 5 && puedeMoverse) {
	        pep.moverIzquierda();
	    }

	    if (entorno.estaPresionada(entorno.TECLA_DERECHA) && pep.getX() < entorno.ancho() - 5 && puedeMoverse) {
	        pep.moverDerecha();
	    }

	    if (entorno.sePresiono(entorno.TECLA_ARRIBA) && puedeMoverse) {
	        pep.salto();
	        verificarMovimientoPep();
	    }

	    if (!pep.apoyado || pep.chocaCon) {
	        pep.caer();
	    }
	}

	/*
	 * Verifica si pep puede moverse preguntando en que isla se encuentra para poder posicionarlo
	 */
	
	private void verificarMovimientoPep() {
	    if (pep.tieneQueMoverse) {
	        for (Islas isla : islas) {
	            double limiteIzq = isla.limiteIzquierdo();
	            double limiteDer = isla.limiteDerecho();
	            double rangoCercaniaIzq = limiteIzq - 30;
	            double rangoCercaniaDer = limiteDer + 30;
	            
	          //Comprueba si Pep tiene su limite inferior por encima del limite superior de la isla a la que va a saltar
	            if (pep.limiteInferior() >= islas[enQueIslaEsta].limiteSuperior() - 110) {
	                if (pep.centro() <= limiteIzq && pep.centro() >= rangoCercaniaIzq) {
	                	pep.mirandoDerecha = true;
	                    break;
	                }

	                if (pep.centro() >= limiteDer && pep.centro() <= rangoCercaniaDer) {
	                    pep.y = isla.limiteSuperior() - 110 - pep.alto;
	                    pep.x = limiteDer - pep.ancho;
	                    pep.mirandoDerecha = false;
	                    break;
	                }
	            }
	        }
	        
	    }
	    pep.tieneQueAsomarse();
        pep.acercarse();
	}

	/*
	 * Dibuja la nave
	 * comprueba si se esta pulsando el clic izquierdo
	 * de ser el caso mueve la nave hasta la posicion X del cursor
	 */
	private void dibujarNave() {
	    navecita.dibujarse(entorno);
	    if (entorno.estaPresionado(entorno.BOTON_IZQUIERDO)) {
	        navecita.moverseHacia(entorno.mouseX());
	    }
	}

	
	/*
	 * comprueba si el gnomo es null, de serlo, termina la comprobacion
	 * 
	 * De lo contrario recorre el arreglo de gnomos y pregunta si hay una colision con pep
	 * de ser el caso reinicia la posicion del gnomo 
	 * incrementando "gnomosSalvados", saliendo del bucle.
	 * 
	 * Tambien verifica si el gnomo colisiono con la navecita
	 * de ser el caso tambien incrementa "gnomosSalvados"
	 */
	
//	public void gnomosSalvados(Gnomos gn, Pep pep) {
//		if(gn ==null) {
//			return;
//		}
//		for(int i=0; i <gnomos.length; i++) {
//			Gnomos g=gnomos[i];
//			if(g!= null && pep.colisionGnomos(g) && pep.getY() > 350) {
//				g.seReinicia(400, 83,10,20);
//				gnomosSalvados++;
//				break;
//			}
//			if(g!= null && g.colisionNavecita(navecita)) {
//				g.seReinicia(400, 83,10,20);
//				gnomosSalvados++;
//				break;
//			}
//		}	
//	}
	
	/*
	 * Comprueba si el gnomo es null, de serlo, termina la comprobacion
	 * 
	 * De lo contrario recorre el arreglo de gnomos y el de tortugas 
	 * pregunta si hay una colision, de ser el caso reinicia la posicion del gnomo 
	 * incrementando "gnomosPerdidos", saliendo del bucle.
	 * 
	 * Tambien verifica si el gnomo salio de la pantalla
	 * de ser el caso tambien incrementa "gnomosPerdidos"
	 */
	
//	public void gnomosPerdidos(Gnomos gn, Tortugas t) {
//		if(gn ==null) {
//			return;
//		}
//		
//		for(int i=0; i<gnomos.length; i++) {
//			Gnomos g=gnomos[i];
//			for(Tortugas tortugas: this.tortugas) {
//				if(tortugas !=null && g.colisionTortugas(t)) {
//					g.seReinicia(400, 83,10,20);
//					gnomosPerdidos++;
//					break;
//				}
//			}
//			if(g.limiteSuperior() > 600) {
//				g.seReinicia(400,83, 10, 20);
//				gnomosPerdidos++;
//			}
//		}
//	}
	
	/*
	 * Muestra todas las estadisticas en pantalla
	 * a su vez que otro valores para ver si las colisiones
	 * estan funcionando correctamente
	 */
	
    private void estadisticas() {      
        entorno.cambiarFont("Arial", 20, Color.BLACK);
        entorno.escribirTexto("Vidas: " + vidas, 10, 20);
        entorno.escribirTexto("Gnomos en Pantalla: " + gnomosEnPantalla, 10, 40);
        entorno.escribirTexto("Gnomos Salvados: " + gnomosSalvados, 10, 60);
        entorno.escribirTexto("Gnomos Perdidos: " + gnomosPerdidos, 10, 80);
        entorno.escribirTexto("Tortugas en Pantalla: " + tortEnPantalla, 10, 100);
        entorno.escribirTexto("Tortugas Asesinadas: " + tortEliminadas, 10, 120);
        entorno.escribirTexto("Tiempo: " + minutos + ":" + segundos, 10, 140);
        entorno.escribirTexto("Pep Y Inf: " + pep.limiteInferior(), 10, 180);
        entorno.escribirTexto("Pep X Izq: " + pep.limiteIzquierdo(), 10, 200);
        entorno.escribirTexto("Pep X Der: " + pep.limiteDerecho(), 10, 220);
        
        }
    
    
	/*
	 * Reinicia todas las variables a su estado inicial
	 * (en proceso) reinicia el juego
	 */
    private void reiniciar() 
    {
    	vidas = 3;
    	tiempoJugando = 0;
    	gnomosSalvados = 0;
    	gnomosPerdidos=0;
    	tortEliminadas = 0;
    	pep = generarPep(400, 480);
    }
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
