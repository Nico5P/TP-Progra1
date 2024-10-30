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
	int numTortuga;
	int tortugasEliminadas;
	int numIsla;
	int horas;
	int minutos;
	int segundos;
	int tiempoJugando;
	int tiempoActual = tiempoJugando;
    boolean Jugando;
    double ultimaPosX;
    double ultimaPosY;
    
    
	
    public Juego() {
    	
    	/*
    	 * Inicializacion de todas las variables para que el juego funcione
    	 */
    	
        iniciarJuego();
        iniciarInterfaz();
        generarTortugas();
        generarGnomos();
        generarIslas();
        crearPep();
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
    	this.tortugasEliminadas = 0;

    }

    private void iniciarJuego() {
        this.entorno = new Entorno(this, "Proyecto para TP; Balbi, Gomez, Pereira, Pereyra", 800, 600);
        entorno.colorFondo(Color.pink);
    }

    private void iniciarInterfaz() {
        this.Interfaz = new Interfaz();
    }

    private void generarTortugas() {
        this.tortugas = new Tortugas[10];
        for (int i = 0; i < this.tortugas.length; i++) {
            this.tortugas[i] = new Tortugas();
        }
    }

    private void generarGnomos() {
    	gnomosEnPantalla=0;
        gnomos = new Gnomos[4];
        if(gnomosEnPantalla != 4)
        for (int i = 0; i < gnomos.length; i++) {
            this.gnomos[i] = new Gnomos(400, 83, 10, 20);
            if(gnomosEnPantalla ==4) {
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


    private void crearPep() {
        if (this.islas.length > 0) {
            int primerIslaInferiorIndex = (islasPorFila * (islasPorFila - 1)) / 2;
            int islaAleatoriaIndex = primerIslaInferiorIndex + (int)(Math.random() * islasPorFila); // Selección aleatoria
            Islas islaSeleccionada = this.islas[islaAleatoriaIndex];
            pep = crearPep(islaSeleccionada.x, entorno.alto() - 110 - 40);
        }
    }

    public Pep crearPep(double x, double y) 
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
        comprobarGameOver();
        funcionamientoGnomos();
        generarIslas();
        funcionamientoDePep();
        controlarMovimientoPep();
        dibujarNave();
        funcionamientoBolaDeFuego();
    }


	private void actualizarTiempo() {
	    int sec = entorno.tiempo() / 1000;
	    segundos = sec % 60;
	    int min = sec / 60;
	    minutos = min % 60;
	    horas = min / 60 % 60;
	}

	private void comprobarGameOver() {
	    if (vidas < 0) {
	        reiniciar();
	    }
	}

	private void funcionamientoGnomos() {
	    Random random = new Random();
//	    for (Gnomos g : this.gnomos) {
	    for(int i=0; i<gnomos.length;i++) {
	    	Gnomos g=gnomos[i];
	    	if(g==null) {
	    		gnomos[i]= new Gnomos(400,83,10,20);
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

	
	/*
	 * El metodo se encarga de dibujar a pep
	 */
	
	private void funcionamientoDePep() {
	    pep.dibujarse(entorno);
	    
	    // Guardar la posicion actual para que al morir pep aparezca en la ultima isla donde estuvo
	    ultimaPosX = pep.getX();
	    ultimaPosY = pep.getY();

	    boolean apoyadoEnAlgunaIsla = false;

	    for (int numIsla = 0; numIsla < islas.length; numIsla++) {
	        if (pep.limiteInferior() >= islas[numIsla].limiteSuperior() &&
	            pep.limiteInferior() <= islas[numIsla].limiteSuperior() + 5 &&
	            pep.limiteIzquierdo() < islas[numIsla].limiteDerecho() &&
	            pep.limiteDerecho() > islas[numIsla].limiteIzquierdo()) {
	            apoyadoEnAlgunaIsla = true;
	            break;
	        }
	    }

	    pep.apoyado = apoyadoEnAlgunaIsla;
	    pep.chocaCon = !apoyadoEnAlgunaIsla;

	    if (!pep.apoyado) {
	        pep.caer();
	    }

	    if (pep.limiteInferior() > entorno.alto()) {
	        pep.x = ultimaPosX;
	        pep.y = ultimaPosY;
	        pep.apoyado = true;
	        vidas--;
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
	                tortugasEliminadas++;
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

	            if (pep.limiteInferior() >= isla.limiteSuperior() - 110) {
	                if (pep.centro() <= limiteIzq && pep.centro() >= rangoCercaniaIzq) {
	                    pep.y = isla.limiteSuperior() - 110 - pep.alto;
	                    pep.x = limiteIzq;
	                    pep.tieneQueMoverse = false;
	                    break;
	                }

	                if (pep.centro() >= limiteDer && pep.centro() <= rangoCercaniaDer) {
	                    pep.y = isla.limiteSuperior() - 110 - pep.alto;
	                    pep.x = limiteDer - pep.ancho;
	                    pep.tieneQueMoverse = false;
	                    break;
	                }
	            }
	        }
	    }
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
////				g.seReinicia(400, 83,10,20);
//				gnomos[i]=null;
//				gnomosSalvados++;
//				break;
//			}
//			if(g!= null && g.colisionNavecita(navecita)) {
////				g.seReinicia(400, 83,10,20);
//				gnomos[i]=null;
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
////					g.seReinicia(400, 83,10,20);
//					gnomos[i]=null;
//					gnomosPerdidos++;
//					break;
//				}
//			}
//			if(g.limiteSuperior() > 600) {
////				g.seReinicia(400,83, 10, 20);
//				gnomos[i]=null;
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
    	
        int minutos = (tiempoJugando / 60) / 60;
        int segundos = tiempoJugando / 60;
        
        entorno.cambiarFont("Arial", 20, Color.BLACK);
        entorno.escribirTexto("Vidas: " + vidas, 10, 20);
        entorno.escribirTexto("Gnomos Salvados: " + gnomosSalvados, 10, 40);
        entorno.escribirTexto("Gnomos Perdidos: " + gnomosPerdidos, 10, 60);
        entorno.escribirTexto("Gnomos en Pantalla: " + gnomosEnPantalla, 10, 80);
        entorno.escribirTexto("Tortugas Asesinadas: " + tortugasEliminadas, 10, 100);
        entorno.escribirTexto("Tiempo: " + minutos + ":" + segundos, 10, 120);
        entorno.escribirTexto("Pep Y Inf: " + pep.limiteInferior(), 10, 140);
        entorno.escribirTexto("Pep X Izq: " + pep.limiteIzquierdo(), 10, 160);
        entorno.escribirTexto("Pep X Der: " + pep.limiteDerecho(), 10, 180);
        
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
    	tortugasEliminadas = 0;
    	pep = crearPep(400, entorno.alto() - 150);
    }
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
