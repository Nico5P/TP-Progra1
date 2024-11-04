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
	
	// Variables y métodos propios de cada grupo
	Image fondo;
	Image casa;
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
    boolean cooldownGnomos;
    boolean gnomoGenerado;
    boolean tortugaGenerada;
    boolean cooldownTortugas;
    int tEnPantalla;
    int enQueIslaEsta;
    int conQueIslaChoca;
    double ultimaPosX;
    double ultimaPosY;
    boolean disparo;
    
    
	
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
//        if(disparo) {
//        	generarBolaDeFuego();
//        }

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
        fondo = Herramientas.cargarImagen("juego/imagenes/fondo.jpg");
    }

    private void iniciarInterfaz() {
        this.Interfaz = new Interfaz();
    }

    private void generarTortugas() {
    	tEnPantalla=0;
        this.tortugas = new Tortugas[5];
        cooldownTortugas=false;
        for (int i = 0; i < this.tortugas.length; i++) {
        	if(tEnPantalla !=4 && cooldownTortugas && !tortugaGenerada) {
        		this.tortugas[i] = new Tortugas();
        		tEnPantalla++;
        	}
            
        }
    }
    
    private void generarGnomos() {
    	gnomosEnPantalla = 0;
        gnomos = new Gnomos[6];
        cooldownGnomos=false;
        actualizarTiempo();
        for (Gnomos g : gnomos){
        	if(gnomosEnPantalla!=4 && cooldownGnomos && !gnomoGenerado) {
        		g = new Gnomos(400, 83, 10, 20);
                gnomosEnPantalla++;
                 
        	}
        	gnomoGenerado=true; 
        	break;
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
            isla.dibujarIslas(entorno);
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
        	entorno.dibujarImagen(fondo, 400, 300, 0, 0.3); // Fondo del juego
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
        funcionamientoTortugas();
    }


	private void actualizarTiempo() {
	    int sec = entorno.tiempo() / 1000;
	    segundos= sec%60;
		int min = sec/60;
		minutos= min%60;
		int hor= min/60;
		horas = hor%60;
		
		if(segundos%5==0 && !gnomoGenerado) //Calculo el tiempo de esepra para que pueda generarse otro gnomo
		{
			cooldownGnomos=true;
		}
		else if(segundos%15==0 && gnomoGenerado) { //Condicion para cambiar el estado de gnomoGenerado a false
			cooldownGnomos=false;
			gnomoGenerado=false;
		}
		else 
		{
			cooldownGnomos=false;
		}
		
		if(segundos%5==0 && !tortugaGenerada) //Calculo el tiempo de esepra para que pueda generarse otro gnomo
		{
			cooldownTortugas=true;
		}
		else if(segundos%15==0 && tortugaGenerada) { //Condicion para cambiar el estado de gnomoGenerado a false
			cooldownTortugas=false;
			tortugaGenerada=false;
		}
		else 
		{
			cooldownTortugas=false;
		}
	}

	private void comprobarGameOver() {
	    if (vidas < 0) {
	        reiniciar();
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

	}
	
	/*
	 * El metodo se encarga de dibujar a pep
	 */
	
	private void funcionamientoTortugas() {
		Random random1=new Random();
		for(int i=0; i<tortugas.length; i++) {
				Tortugas t1= tortugas[i];
				
				if(t1==null && cooldownTortugas && !tortugaGenerada) {
					tortugas[i]=new Tortugas();
					tortugaGenerada=true;
				}
				
				
				if(t1!=null) {
					t1.dibujarse(entorno, Jugando);

					estadoTortugas(t1,random1);
					
					if(tortugas[i].limiteSuperior() > entorno.alto()) {
						tortugas[i]= null;
						tortugasEliminadas++;
						break;
					}
		
					if (bolaDeFuego !=null && bolaDeFuego.colisionTortugas(t1)) {
						tortugas[i]= null;
			                
						tortugasEliminadas++;
						break;
					}
				}        
		}
	}
	
	public void estadoTortugas(Tortugas t, Random random1) {
		boolean enIsla= false;
		for(Islas isla:islas) {
			//Ver si la tortuga toca la isla(?
			if(t.limiteInferior() == isla.limiteSuperior()&&
					t.limiteIzquierdo()> isla.limiteIzquierdo()-t.ancho &&
					t.limiteDerecho()< isla.limiteDerecho()+t.ancho) {
				enIsla=true;
				t.apoyado=true;
				iniciarMovHorizontal(t, random1, isla);
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
		t.mover();
		
		if(!t.mira) {
			t.caminar=random1.nextBoolean();
			t.mira=true;
		}

		if(t.caminar) {
			if(t.limiteIzquierdo()>= isla.limiteDerecho() - (t.ancho /2)) {
				t.caminar=false;
			}
		}
		else {
			
			if(t.limiteDerecho()<= isla.limiteIzquierdo() + (t.ancho /2)) {
				t.caminar=true;
				}
			}
		
	}
	
	
	
	
	private void funcionamientoDePep() {
	    pep.dibujarse(entorno);
	    
	    // Guardar la posicion actual para que al morir pep aparezca en la ultima isla donde estuvo
	    ultimaPosX = pep.getX();
	    ultimaPosY = pep.getY();
	    
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
	    
	    for (Islas isla : islas) {
	    	if (pep.limiteSuperior() >= isla.limiteInferior() &&
	    		pep.limiteInferior() <= isla.limiteInferior()+100 &&
		        pep.limiteIzquierdo() <= isla.limiteDerecho() + pep.ancho + 3 &&
		        pep.limiteDerecho() >= isla.limiteIzquierdo() - pep.ancho - 3) {
//	    		if(islas[enQueIslaEsta].limiteSuperior() <= isla.limiteInferior()-110 &&
//	    			islas[enQueIslaEsta].limiteSuperior() >= isla.limiteInferior()-90) {
	    			debajoDeUnaIsla = true;
	    			break;
//	    		}	
	    	}
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
	}
	

	/*
	 * Verifica si pep puede moverse preguntando en que isla se encuentra para poder posicionarlo
	 */
	
	private void verificarMovimientoPep() {
	    if (pep.moviendose) {
	        for (Islas isla : islas) {
	            double limiteIzq = isla.limiteIzquierdo();
	            double limiteDer = isla.limiteDerecho();
	            double rangoCercaniaIzq = limiteIzq - 20;
	            double rangoCercaniaDer = limiteDer + 20;
	            
	          //Comprueba si Pep tiene su limite inferior por encima del limite superior de la isla a la que va a saltar
	            if (isla.limiteSuperior() >= islas[enQueIslaEsta].limiteSuperior() - 110 &&
	            	isla.limiteSuperior() != islas[enQueIslaEsta].limiteSuperior()) {
	                if (pep.centro() <= limiteIzq && pep.centro() >= rangoCercaniaIzq) {
	                	pep.mirandoDerecha = true;
	                	pep.islaCercana = true;
	                    break;
	                }

	                if (pep.centro() >= limiteDer && pep.centro() <= rangoCercaniaDer) {
	                    pep.mirandoDerecha = false;
	                    pep.islaCercana = true;
	                    break;
	                }
	                else {
	                	pep.islaCercana = false;
	                }
	            }
	        }
	        
	    }
//	    if(pep.islaCercana && !pep.debajoDe) {
	    	pep.tieneQueAsomarse();
	        pep.acercarse();
//	    }
	    
	}

	
	/*
	 * Se encarga de generar la bola de fuego y a su vez verifica su colision con alguna tortuga
	 * de ser el caso eliminara la tortuga y aumentara el contador de "tortugas elimanadas"
	 * De no haber colisionado con una tortuga, si toca el borde izquierdo o derecho de la pantalla
	 * desaparecera
	 */

	
    /*
     * Controla el movimiento de pep mediante las entradas del teclado
     */
    
    private void controlarMovimientoPep() {
        boolean puedeMoverse = pep.apoyado;
      
        if (entorno.estaPresionada(entorno.TECLA_ABAJO) && puedeMoverse && bolaDeFuego==null) {  //Pep solo puede disparar si esta apoyado y si no hay ninguna bola de fuego en pantalla
        	if(pep.mirandoDerecha) {  //Si pep mira hacia la derecha dispara hacia la derecha. Caso contrario, dispara hacia la izquierda.
        		bolaDeFuego = new BoladeFuego(pep.limiteDerecho()+8, pep.y-5, pep.mirandoDerecha); //numero 8=mitad del ancho de la bola de fuego
        	}
        	else {
        		bolaDeFuego = new BoladeFuego(pep.limiteIzquierdo()-8, pep.y-5, pep.mirandoDerecha);
        	} 
        }

	    if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && pep.getX() > 5 && puedeMoverse) {
	        pep.moverIzquierda();
	    }

	    if (entorno.estaPresionada(entorno.TECLA_DERECHA) && pep.getX() < entorno.ancho() - 5 && puedeMoverse) {
	        pep.moverDerecha();
	    }

	    if (entorno.sePresiono(entorno.TECLA_ARRIBA) && puedeMoverse) {
	        pep.salto();
//	        pep.caer();
//	        verificarMovimientoPep();
	    }

	    if (!pep.apoyado || pep.chocaCon) {
	        pep.caer();
	        verificarMovimientoPep();
	    }
	}
    
    private void funcionamientoBolaDeFuego() {
    	if (bolaDeFuego != null) {
	        bolaDeFuego.dibujarse(entorno);
	    }
	    if (bolaDeFuego != null && bolaDeFuego.disparada) {
	        bolaDeFuego.disparo();
	        
	        
	        if (bolaDeFuego.getX() < 0 || bolaDeFuego.getX() > entorno.ancho()) {
	            bolaDeFuego = null;
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
	 * Muestra todas las estadisticas en pantalla
	 * a su vez que otro valores para ver si las colisiones
	 * estan funcionando correctamente
	 */
	
    private void estadisticas() {      
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
