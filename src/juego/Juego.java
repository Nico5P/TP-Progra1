package juego;


import java.awt.Color;
import java.util.Random;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
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
	Bombas bombas;
	
	// Variables y métodos propios de cada grupo
	Image fondo;
	Image casa;
	Image logo;
	Image titleScreen;
	Image winScreen;
	Image gameOverScreen;
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
	int minsJugando;
	int tEnPantalla;
	int enQueIslaEsta;
	int conQueIslaChoca;
	boolean inicio;
	boolean victoria;
	boolean gameOver;
    boolean Jugando;
    boolean cooldownGnomos;
    boolean gnomoGenerado;
    boolean tortugaGenerada;
    boolean cooldownTortugas;
    boolean disparo;
	
    public Juego() {

    	//Inicializacion de todas las variables para que el juego funcione
    	
        this.entorno = new Entorno(this, "Proyecto para TP; Balbi, Gomez, Pereira, Pereyra", 800, 600);
        iniciarJuego();
        this.entorno.iniciar();
    }

    private void iniciarJuego() {
    	cargarRecursos(); //Carga todos los recursos (imagenes, sonidos)
    	generarTortugas(); //Logica que genera a las tortugas
    	generarGnomos(); //Logica que genera a los gnomos
    	generarIslas(); //Logica que genera las islas
    	generarPep(); //Logica que genera a Pep
    	generarNave(); //Logica que genera la navecita
//    	generarBombas();
        iniciarVariables();
    }
    
    private void iniciarVariables() {
    	
        this.Jugando = true;
        this.inicio = true;
        this.victoria = false;
        this.gameOver = false;
        this.cooldownGnomos = false;
        this.gnomoGenerado = false;
        this.tortugaGenerada = false;
        this.cooldownTortugas = false;
        this.disparo = false;
        
        this.vidas = 3;
        this.gnomosSalvados = 0;
        this.gnomosPerdidos = 0;
        this.tortugasEliminadas = 0;
        this.gnomosEnPantalla = 0;
        
        this.horas = 0;
        this.minutos = 0;
        this.segundos = 0;
        this.minsJugando = 0;
        this.tiempoJugando = 0;
    }
    
    private void cargarRecursos() {
    	iniciarInterfaz();
    	fondo = Herramientas.cargarImagen("juego/imagenes/fondo.png");
    	casa = Herramientas.cargarImagen("juego/imagenes/casa.png");
    	logo = Herramientas.cargarImagen("juego/imagenes/logo.png");
    	titleScreen = Herramientas.cargarImagen("juego/imagenes/titleScreen.png");
    	winScreen = Herramientas.cargarImagen("juego/imagenes/winScreen.png");
    	gameOverScreen = Herramientas.cargarImagen("juego/imagenes/gameOverScreen.png");
    }

    private void iniciarInterfaz() {
        this.Interfaz = new Interfaz();
    }
   
    private void generarTortugas() {
    	tEnPantalla = 0;
        this.tortugas = new Tortugas[5];
        for (int i = 0; i < this.tortugas.length; i++) {
        	if(tEnPantalla !=4 && cooldownTortugas && !tortugaGenerada) {
        		this.tortugas[i] = new Tortugas();
        		tEnPantalla++;
        	}
        }
    }
    
    private void generarGnomos() {
        gnomosEnPantalla = 0; //Valor inicial de gnomos en pantalla
        gnomos = new Gnomos[4];
        gnomoGenerado = false;
        actualizarTiempo(); //Actualiza el valor del boolean cooldownGnomos y gnomoGenerado
        for (Gnomos g : gnomos) {
            if (g == null && gnomos.length < 4 && cooldownGnomos) {
                g = new Gnomos(400, 83, 10, 20);
                gnomosEnPantalla++;  
                gnomoGenerado = true; // Marca que se ha generado un gnomo
            }
            break;
        }
    }

    //Este método genera un arreglo de 15 islas (0-14) divididas en 6 filas (0-5) ubicadas de manera que dan la ilusion de formar una pirámide.
    
    private void generarIslas() {
        int islasPorFila = 5;
        int anchoIsla = 100;
        int altoIsla = 30;
        int espacioHorizontal = 60;
        int espacioVertical = 100;
        int inicioY = 110; //Eje Y de la primer fila (0)
        double posY = inicioY;
        int indiceIsla = 0; //Guarda la posicion de las islas dentro del arreglo
        this.islas = new Islas[islasPorFila * (islasPorFila + 1) / 2]; // Inicia el arreglo de islas

        for (int fila = 0; fila < islasPorFila; fila++) { //Recorro la cantidad de islas que tiene cada fila
            double posX = 400 - (fila * 80); //Cuando incrementa el índice fila, la x de la primer isla de cada fila se genera más a la izquierda
            for (int i = 0; i <= fila; i++) {//Genera las islas desde el último valor guardado en indiceIsla hasta la cantidad de islas de esa fila
                this.islas[indiceIsla] = new Islas(posX, posY, anchoIsla, altoIsla);
                posX += anchoIsla + espacioHorizontal; //Incremento el valor de X de la siguiente isla
                indiceIsla++; //Incremento la posición de la siguiente isla que se genere
            }
            posY += espacioVertical; //Incremento el valor de Y para todas las islas de la siguiente fila
        }

        for (Islas isla : islas) {
            isla.dibujarIslas(entorno);
        }
    }

    private void generarPep() {
    	pep = new Pep(400, 480);
    }
    
//    private void generarBombas() {
//    	bombas = new Bombas[3];
//    }
    
    private void generarNave() {
        navecita = new Navecita(400, 560);
    }

	/*
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	
    public void tick() {
        if (entorno.sePresiono(entorno.TECLA_ESCAPE)) {
            Interfaz.noPausado();
        }
        if (Interfaz.pausado()) {
            Interfaz.dibujarMenu(entorno);
            if (entorno.sePresiono(entorno.TECLA_ARRIBA)) {
                Interfaz.cambiarOpcion(-1);
            }
            if (entorno.sePresiono(entorno.TECLA_ABAJO)) {
                Interfaz.cambiarOpcion(1);
            }
            if (entorno.sePresiono(entorno.TECLA_ENTER)) {
            	if (Interfaz.opcionElegida() == 0) {
            		Interfaz.noPausado();
            	}
            	else if (Interfaz.opcionElegida() == 1) {
            		iniciarJuego();
            	}
            }
            return;            	
        }
        if (inicio) {
            dibujarInicio();
        } else if (gameOver) {
            dibujarGameOver();
        } else if (victoria) {
            dibujarVictoria();
        } else {
            entorno.dibujarImagen(fondo, 400, 240, 0, 0.7);
            entorno.dibujarImagen(casa, 400, 40, 0, 0.4);
            actualizarEstadoDelJuego();
        }
    }
    
    private void actualizarEstadoDelJuego() {
    	if (!inicio || !gameOver || !victoria) {
    		tiempoJugando++;
    		if ((tiempoJugando/60) == 60){
    			tiempoJugando = 0;
    			minsJugando++;
    		}
    		actualizarTiempo();
    		estadisticas();
    		funcionamientoGnomos();
    		generarIslas();
    		funcionamientoDePep();
    		controlarMovimientoPep();
    		funcionamientoBolaDeFuego();
    		funcionamientoTortugas();
    		funcionamientoBombas();
    		funcionamientoNave();
    	}
    }

    
    private void actualizarTiempo() {
	        segundos = (entorno.tiempo() / 1000)% 60;
	        minutos = (segundos / 60) % 60;
	        horas = minutos / 3600;

	        /*
	         * Tanto en la generacion de gnomos como en tortugas, 
	         */
	        if(segundos % 10 == 0) {
	        	gnomoGenerado=false;
	        }
			if(gnomoGenerado) { //Calculo el tiempo de esepra para que pueda generarse otro gnomo
				cooldownGnomos=false;
			}
			if(!gnomoGenerado) {
				cooldownGnomos=true;	
			}
			if (segundos % 5 == 0 && !tortugaGenerada) { //Calculo el tiempo de esepra para que pueda generarse otro gnomo
				cooldownTortugas = true;
			}
			if (segundos % 10 == 0 && tortugaGenerada) { //Condicion para cambiar el estado de gnomoGenerado a false
				cooldownTortugas = false;
				tortugaGenerada = false;
			}
		}
 

	private void funcionamientoGnomos() {
	    Random random = new Random();
	    for(int i=0; i < gnomos.length; i++) {
	    	Gnomos g = gnomos[i];
	    	actualizarTiempo();
	    	if(g == null && cooldownGnomos && !gnomoGenerado) {
	    		gnomos[i] = new Gnomos(400,83,10,20);
	    		gnomoGenerado = true;
	    	}
	    	if (g != null) {
	            g.dibujarse(entorno);
	            EstadoDeGnomos(g, random);
	            
	            if (pep.colisionGnomos(g)&& pep.getY() > 350) {
	                gnomos[i] = null;
	                gnomosSalvados++;
	            }
	            
	            if(g!= null && g.colisionNavecita(navecita)) {
					gnomos[i] = null;
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
	}
	
	
	private void funcionamientoTortugas() {
		Random random1 = new Random();
		for(int i = 0; i < tortugas.length; i++) {
				Tortugas t1= tortugas[i];
				
				if(t1 == null && cooldownTortugas && !tortugaGenerada) {
					tortugas[i] = new Tortugas();
					tortugaGenerada = true;
				}	
				
				if(t1 != null) {
					t1.dibujarse(entorno);

					estadoTortugas(t1,random1);
					
					if(tortugas[i].limiteSuperior() > entorno.alto()) {
						tortugas[i] = null;
						tortugasEliminadas++;
						break;
					}
		
					if (bolaDeFuego !=null && bolaDeFuego.colisionTortugas(t1)) {
						tortugas[i] = null;
			            bolaDeFuego = null;
						tortugasEliminadas++;
						break;
					}
					
					if(t1.limiteInferior() + 15 >= pep.limiteInferior() && t1.limiteSuperior() - 15 <= pep.limiteSuperior()) {
							if (bombas==null && t1.caminar && t1.x < pep.x && pep.limiteInferior() != 495) {
								bombas = new Bombas(t1.limiteDerecho() + 5, t1.y - 5, true);
							}
							if (bombas==null && !t1.caminar && t1.x > pep.x && pep.limiteInferior() != 495) {
								bombas = new Bombas(t1.limiteIzquierdo() - 5, t1.y - 5, false);	
							}
//						}
						
					}
				}        
			}
		}
	
	public void estadoTortugas(Tortugas t, Random random1) {
		boolean enIsla = false;
		for(Islas isla:islas) {
			//Ver si la tortuga toca la isla(?
			if(t.limiteInferior() == isla.limiteSuperior()&&
					t.limiteIzquierdo()> isla.limiteIzquierdo()-t.ancho &&
					t.limiteDerecho()< isla.limiteDerecho()+t.ancho) {
				enIsla = true;
				t.apoyado = true;
				iniciarMovHorizontal(t, random1, isla);
				break;
			}
		}
		
		if(!enIsla) {
			t.apoyado = false;
			t.mira = false;
			t.caer();
		}	
	}
	
	private void iniciarMovHorizontal(Tortugas t, Random random1, Islas isla) {
		t.mover();
		
		if(!t.mira) {
			t.caminar = random1.nextBoolean();
			t.mira=true;
		}

		if(t.caminar && t.limiteIzquierdo() >= isla.limiteDerecho() - (t.ancho /2)) {
			t.caminar = false;
			t.mirandoDerecha = false;
		}
		if(!t.caminar && t.limiteDerecho() <= isla.limiteIzquierdo() + (t.ancho /2)) {
			t.caminar = true;
			t.mirandoDerecha = true;
		}
	}
	
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
	    if (pep.limiteIzquierdo() >= islas[enQueIslaEsta].limiteIzquierdo() - pep.ancho && 
	    	pep.limiteDerecho() <= islas[enQueIslaEsta].limiteIzquierdo() + pep.ancho + 8) {
	    	debajoDeUnaIsla = true;
	    }
	    
	    if (pep.limiteDerecho() <= islas[enQueIslaEsta].limiteDerecho() + pep.ancho && 
	    	pep.limiteIzquierdo() >= islas[enQueIslaEsta].limiteDerecho() - pep.ancho - 8) {
	    	debajoDeUnaIsla = true;
	    }
	    
	    pep.debajoDe = debajoDeUnaIsla;

	    if (!pep.apoyado) {
	        pep.caer();
	    }

	    if (pep.limiteInferior() > entorno.alto()) {
	        pep.x = 400;
	        pep.y = 480;
	        pep.apoyado = true;
	        vidas--;
	    }
	    
	    for(int i=0; i< tortugas.length; i++) {
	    	Tortugas t=tortugas[i];
	    	if(t!=null) {
	    		if(pep.colisionTortugas(t)) {
	    			 pep.x = 400;
	    			 pep.y = 480;
	    			 pep.apoyado = true;
	    			 vidas--;
	    		}
	    	}
	    }
	    
	    for(int i=0; i< tortugas.length; i++) {
	    	Tortugas t=tortugas[i];
	    	if(t!=null) {
	    		if(pep.colisionTortugas(t)) {
	    			 pep.x = 400;
	    			 pep.y = 480;
	    			 pep.apoyado = true;
	    			 vidas--;
	    		}
	    	}
	    }
	    
//	    for(int i=0; i< bombas.length; i++) {
//	    	Bombas b=bombas[i];
	    	if(bombas!=null) {
	    		if(pep.colisionBombas(bombas)) {
	    			bombas = null;
	    			pep.x = 400;
	    			pep.y = 480;
	    			pep.apoyado = true;
	    			vidas--;
	    		}
	    	}
//	    }
	    
        if (vidas < 0 || gnomosPerdidos == 6) {
            gameOver = true;
        }
        
        if (gnomosSalvados == 4) {
            victoria = true;
        }
	}
	

	/*
	 * La variable moviendose es verdadera una vez que Pep ya saltó y debe comprobar si esta o no cerca de una isla, para ello, definimos
	 * intervalos de cercanía. Cada objeto isla tiene asignado dos valores que representan sus intervalos de cercania con 
	 * sus límites laterales (izquierdo y derecho). Si compruebo que el X de Pep está entre un límite lateral y su intervalo de 
	 * cercanía, devuelvo pep.islaCercana = true. Además, dependiendo de si estoy más cerca de un límite derecho o izquierdo,
	 * devuelvo pep.mirandoDerecha = true / false para saber si tengo que aumentar o disminuir el X de Pep.
	 */
	
	private void verificarMovimientoPep() {
	    if (pep.moviendose) {
	        for (Islas isla : islas) {
	            double limiteIzq = isla.limiteIzquierdo(); //Variable límite izquierdo (valor de eje x)
	            double limiteDer = isla.limiteDerecho(); //Variable límite derecho (valor de eje x)
	            double rangoCercaniaIzq = limiteIzq - 30; //Intervalo de cercanía para el límite izquierdo (eje x del límite - 30)
	            double rangoCercaniaDer = limiteDer + 30; //Intervalo de cercanía para el límite derecho (eje x del límite + 30)

	            if (isla.limiteSuperior() >= islas[enQueIslaEsta].limiteSuperior() - 110 &&
	            	isla.limiteSuperior() != islas[enQueIslaEsta].limiteSuperior()) {
	                if (pep.centro() <= limiteIzq && pep.centro() >= rangoCercaniaIzq) { //Intervalo de cercanía con límite izquierdo
	                	pep.mirandoDerecha = true;
	                    break;
	                }
	                if (pep.centro() >= limiteDer && pep.centro() <= rangoCercaniaDer) { //Intervalo de cercanía con límite derecho
	                    pep.mirandoDerecha = false;
	                    break;
	                }
	            }
	        }
	    }
	    pep.tieneQueAsomarse(); 
	}
	
    /*
     * Controla el movimiento de pep mediante las entradas del teclado
     */
    
    private void controlarMovimientoPep() {
    //Pep solo puede moverse de izquierda a derecha o saltar mediante las teclas correspondientes si y solo si se encuentra apoyado sobre alguna isla
        boolean puedeMoverse = pep.apoyado;
        
      //Pep solo puede disparar si esta apoyado y si no hay ninguna bola de fuego en pantalla
        if ((entorno.sePresiono(entorno.TECLA_ABAJO) || entorno.sePresiono('c')) && puedeMoverse && bolaDeFuego == null) {  
        	if(pep.mirandoDerecha) {  //Si pep mira hacia la derecha dispara hacia la derecha. Caso contrario, dispara hacia la izquierda.
        		bolaDeFuego = new BoladeFuego(pep.limiteDerecho() + 8, pep.y - 5, pep.mirandoDerecha); 
        	}
        	else {
        		bolaDeFuego = new BoladeFuego(pep.limiteIzquierdo() - 8, pep.y - 5, pep.mirandoDerecha);
        	} 
        	//Número 8 == mitad del ancho de la bola de fuego, este valor lo tengo en cuenta para ubicar la bola de fuego más a la izquierda 
        	//o a la derecha de Pep, dependiendo de la dirección a la que apunte
        }
        
	    if ((entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('a')) && pep.getX() > 5 && puedeMoverse) {
	        pep.moverIzquierda();
	    }

	    if ((entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('d')) && pep.getX() < entorno.ancho() - 5 && puedeMoverse) {
	        pep.moverDerecha();
	    }

	    if ((entorno.sePresiono(entorno.TECLA_ARRIBA) || entorno.estaPresionada('w')) && puedeMoverse && pep.limiteInferior() > 196) {
	        pep.salto();
	    }

	    if (!pep.apoyado) { //Si Pep no está apoyado verifica si es porque saltó o porque se está cayendo, y se ejecuta de acuerdo al caso.
	        pep.caer();
	        verificarMovimientoPep();
	    }
	}
    
	/*
	 * Se encarga de generar la bola de fuego y a su vez verifica su colision con alguna tortuga
	 * de ser el caso eliminara la tortuga y aumentara el contador de "tortugas elimanadas"
	 * De no haber colisionado con una tortuga, si toca el borde izquierdo o derecho de la pantalla
	 * desaparecera
	 */
    private void funcionamientoBolaDeFuego() {
    	if (bolaDeFuego != null) {
	        bolaDeFuego.dibujarBolaDeFuego(entorno);
	    }
	    if (bolaDeFuego != null && bolaDeFuego.disparada) {
	        bolaDeFuego.disparo();
	        
	        if (bolaDeFuego.getX() < 0 || bolaDeFuego.getX() > entorno.ancho()) {
	            bolaDeFuego = null;
	        }	       
	    }
	}
    
    private void funcionamientoBombas() {
    	if (bombas != null) {
    		bombas.dibujarse(entorno);
    		bombas.dibujarBomba(entorno);
    	}
    	if (bombas != null && bombas.disparada) {
    	    bombas.disparo();
    	        
    	    if (bombas.getX() < 0 || bombas.getX() > entorno.ancho()) {
    	        bombas = null;
    	    }	       
    	}	
    }

	/*
	 * Dibuja la nave
	 * comprueba si se esta pulsando el clic izquierdo
	 * de ser el caso mueve la nave hasta la posicion X del cursor
	 */
	private void funcionamientoNave() {
	    navecita.dibujarse(entorno);
	    if (entorno.estaPresionado(entorno.BOTON_IZQUIERDO)) {
	        navecita.moverseHacia(entorno.mouseX());
	    }
	}

	/*
	 * Muestra todas las estadisticas en pantalla
	 * a su vez que otro valores para ver si las colisiones
	 * estan funcionando correctamente
	 */
	
	private void estadisticas() {      
	    entorno.cambiarFont("Arial", 20, Color.white);
	    entorno.escribirTexto("Vidas: " + vidas, 10, 20);
	    entorno.escribirTexto("Gnomos Salvados: " + gnomosSalvados, 10, 40);
	    entorno.escribirTexto("Gnomos Perdidos: " + gnomosPerdidos, 10, 60);
	    entorno.escribirTexto("Gnomos en Pantalla: " + gnomosEnPantalla, 10, 80);
	    entorno.escribirTexto("Tortugas Asesinadas: " + tortugasEliminadas, 10, 100);
	    String tiempo = String.format("%02d:%02d", minsJugando , tiempoJugando/60);
	    entorno.escribirTexto("Tiempo: " + tiempo, 10, 120);
	    entorno.escribirTexto("Pep Y Inf: " + pep.limiteInferior(), 10, 140);
	    entorno.escribirTexto("Pep X Izq: " + pep.limiteIzquierdo(), 10, 160);
	    entorno.escribirTexto("Pep X Der: " + pep.limiteDerecho(), 10, 180);
	}

    
	 private void dibujarInicio() {
	        // Dibuja la pantalla de inicio
	        entorno.dibujarImagen(titleScreen, 400, 300, 0, 1);
	        entorno.dibujarImagen(logo, 400, 150, 0, 0.2);
	        entorno.cambiarFont("Arial", 50, Color.green);
	        entorno.escribirTexto("Apreta \"ENTER\" para comenzar", (entorno.ancho()/2 )- 350, entorno.alto()/2 + 50);

	        if (entorno.sePresiono(entorno.TECLA_ENTER)) {
	            inicio = false; // Empieza el juego si se presiona la tecla
	        }
	    }

	 private void dibujarGameOver() {
		 entorno.dibujarImagen(gameOverScreen, 400, 300, 0, 1);
		 entorno.cambiarFont("Arial", 50, Color.red);
		 entorno.escribirTexto("A casa pete", (entorno.ancho()/2) - 100, entorno.alto()/2 - 100);
		 entorno.escribirTexto("Apreta \"ENTER\" para reiniciar",(entorno.ancho()/2 )- 350, entorno.alto()/2 + 50);
		 
		 if (entorno.sePresiono(entorno.TECLA_ENTER)) {
			 iniciarJuego();
		 }
	 }

	 private void dibujarVictoria() {
		 entorno.dibujarImagen(winScreen, 400, 300, 0, 1);
		 entorno.cambiarFont("Arial", 50, Color.green);
		 entorno.escribirTexto("¡GANASTE!", (entorno.ancho()/2) - 100, entorno.alto()/2 - 100);
		 entorno.escribirTexto("Apreta \"ENTER\" para continuar", (entorno.ancho()/2 )- 350, entorno.alto()/2 + 50);
		 
		 if (entorno.sePresiono(entorno.TECLA_ENTER)) {
			 iniciarJuego();
		 }
	 }

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}