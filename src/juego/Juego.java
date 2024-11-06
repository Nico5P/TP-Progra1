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
	Image vida;
	Image escudo;
	Image gnomo;
	Image perdidos;
	Image reloj;
	Image kills;
	Image titleScreen;
	Image tutoScreen;
	Image winScreen;
	Image gameOverScreen;
	Image titleText;
	Image winText;
	Image gameOverText;
	int islasPorFila;
	int anchoIsla;
	int altoIsla;
	int espacioHorizontal;
	int espacioVertical;
	int vidas;
	int gnomosPerdidos;
	int gnomosSalvados;
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
	boolean tutorial;
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
        this.tutorial = false;
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
    	vida = Herramientas.cargarImagen("juego/imagenes/vida.png");
    	escudo = Herramientas.cargarImagen("juego/imagenes/escudo.png");
    	gnomo = Herramientas.cargarImagen("juego/imagenes/gnomo.png");
    	kills = Herramientas.cargarImagen("juego/imagenes/kills.png");
    	perdidos = Herramientas.cargarImagen("juego/imagenes/perdidos.png");
    	reloj = Herramientas.cargarImagen("juego/imagenes/reloj.png");
    	titleScreen = Herramientas.cargarImagen("juego/imagenes/titleScreen.png");
    	tutoScreen = Herramientas.cargarImagen("juego/imagenes/tutoScreen.png");
    	winScreen = Herramientas.cargarImagen("juego/imagenes/winScreen.png");
    	gameOverScreen = Herramientas.cargarImagen("juego/imagenes/gameOverScreen.png");
    	titleText = Herramientas.cargarImagen("juego/imagenes/titleText.png");
    	winText = Herramientas.cargarImagen("juego/imagenes/winText.png");
    	gameOverText = Herramientas.cargarImagen("juego/imagenes/gameOverText.png");
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
        gnomos = new Gnomos[4];
        gnomoGenerado = false;
        actualizarTiempo(); //Actualiza el valor del boolean cooldownGnomos y gnomoGenerado
        for (Gnomos g : gnomos) {
            if (g == null && gnomos.length < 6 && cooldownGnomos) {
                g = new Gnomos(400, 83, 10, 20);
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
        } else if (tutorial) {
        	dibujarTutorial();
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
    	if (!inicio || !tutorial || !gameOver || !victoria) {
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
	        if(segundos % 5 == 0) {
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
	    
	    /*
	     * La colision de pep con bombas.
	     * Si pep colisiona con una bomba, muere, perdiendo una vida, y volviendo a aparecer.
	     * Si pep tiene el escudo activado, y este tiene al menos un impacto, entonces pep no morira, pero perdera un uso del escudo
	     * Del mismo modo, si el escudo esta activado pero no le quedan impactos, morira
	     */
	    if (bombas != null) { 
	        if (pep.colisionBombas(bombas)) {
	            if (pep.escudoActivo && pep.impactosRestantes > 0) {
	            	pep.impactosRestantes-=1;
	            	bombas = null;
	            	pep.escudoActivo = false;
	            } else {
	                // Si el escudo no está activo o no tiene impactos restantes Pep pierde vida
	                pep.x = 400;
	                pep.y = 480;
	                pep.apoyado = true;
	                bombas = null;
	                vidas--;
	            }
	        }
	        if (bolaDeFuego != null && bolaDeFuego.colisionBombas(bombas)) {
	        	bolaDeFuego = null;
	        	bombas = null;
	        	
	        }
	    }
	    
        if (vidas < 1 || gnomosPerdidos == 8) {
            gameOver = true;
        }
        
        if (gnomosSalvados == 8) {
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

	    if (entorno.sePresiono('s') && puedeMoverse) {
	        if (!pep.escudoActivo) {
	            pep.escudoActivo = true;  // Activa el escudo
	        }
	        else if (pep.escudoActivo) {
	        	pep.escudoActivo = false;
	        }
	    }
	    
	    if (!pep.apoyado) { // Si Pep no está apoyado verifica si es porque saltó o porque se está cayendo, y se ejecuta de acuerdo al caso.
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
	    entorno.cambiarFont("Arial", 30, Color.white);
	    for (int i = 0; i < vidas; i++) {
	        int x = 40 + i * 50;  // Cambia la posición X de cada imagen
	        entorno.dibujarImagen(vida, x, 40, 0, 1.5);
	    }
	    
	    for (int i = 0; i < pep.impactosRestantes; i++) {
	        int x = 40 + i * 50;  // Cambia la posición X de cada imagen
	        entorno.dibujarImagen(escudo, x, 100, 0, 1.5);
	    }
	    
//	    for (int i = 0; i < gnomosSalvados; i++) {
//	    	int y = 190 + i * 30;  // Cambia la posición X de cada imagen
//	    	entorno.dibujarImagen(gnomo, 40, y, 0, 1.5);
//	    }
	    entorno.dibujarImagen(gnomo, 40, 160, 0, 1.5);
	    entorno.escribirTexto("" + gnomosSalvados, 60, 170);
	    
//	    for (int i = 0; i < gnomosPerdidos; i++) {
//	    	int y = 190 + i * 30;  // Cambia la posición X de cada imagen
//	    	entorno.dibujarImagen(perdidos, 80, y, 0, 1.5);
//	    }
	    entorno.dibujarImagen(perdidos, 40, 220, 0, 1.5);
	    entorno.escribirTexto("" + gnomosPerdidos, 60, 230);
	    
//	    for (int i = 0; i < tortugasEliminadas; i++) {
//	        int y = 190 + i * 10;  // Cambia la posición X de cada imagen
//	        entorno.dibujarImagen(kills, 100, y, 0, 1.5);
//	    }
	    entorno.dibujarImagen(kills, 40, 260, 0, 1.5);
	    entorno.escribirTexto("" + tortugasEliminadas, 60, 270);
	    entorno.dibujarImagen(reloj, 670, 40, 0, 1.5);
	    
//	    entorno.escribirTexto(""+vidas, 60, 40);
//	    entorno.escribirTexto("Gnomos Salvados: " + gnomosSalvados, 10, 40);
//	    entorno.escribirTexto("Gnomos Perdidos: " + gnomosPerdidos, 10, 60);
//	    entorno.escribirTexto("escudo: " + pep.impactosRestantes, 10, 80);
//	    entorno.escribirTexto("Tortugas Asesinadas: " + tortugasEliminadas, 10, 100);
	    String tiempo = String.format("%02d:%02d", minsJugando , tiempoJugando/60);
	    entorno.escribirTexto("" + tiempo, 700, 60);
	}

    
	 private void dibujarInicio() {
	        // Dibuja la pantalla de inicio
		 entorno.dibujarImagen(titleScreen, 400, 300, 0, 1);
		 entorno.dibujarImagen(titleText, 400, 400, 0, 1);

		 if (entorno.sePresiono(entorno.TECLA_ENTER)) {
			 inicio = false;
			 tutorial = true;
		 }
	 }
	 
	 private void dibujarTutorial() {
	        // Dibuja la pantalla de inicio
		 entorno.dibujarImagen(tutoScreen, 400, 300, 0, 1);

		 if (entorno.sePresiono(entorno.TECLA_ENTER)) {
			 tutorial = false; // Empieza el juego si se presiona la tecla
		 }
	 }
	 
	 private void dibujarGameOver() {
		 entorno.dibujarImagen(gameOverScreen, 400, 300, 0, 1);
		 entorno.dibujarImagen(gameOverText, 400, 50, 0, 1);
		 
		 if (entorno.sePresiono(entorno.TECLA_ENTER)) {
			 iniciarJuego();
		 }
	 }

	 private void dibujarVictoria() {
		 entorno.dibujarImagen(winScreen, 400, 300, 0, 1);
		 entorno.dibujarImagen(winText, 400, 50, 0, 1);
		 
		 if (entorno.sePresiono(entorno.TECLA_ENTER)) {
			 iniciarJuego();
		 }
	 }

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}