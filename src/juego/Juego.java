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
  //Inicializa las variables que se utilizan, ya sea como booleanos o en valores numéricos. 
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
    	tEnPantalla = 0;			//Inicializa la varibale en 0.
        this.tortugas = new Tortugas[5];;		//Genera 5 tortugas.
        for (int i = 0; i < this.tortugas.length; i++) {  //Recorre el array de tortugas de 0 hasta el length - 1. 
        	if(tEnPantalla !=4 && cooldownTortugas && !tortugaGenerada) {	//Si no hay cuatro en pantalla, el cooldwn es falso y tortugaGenerada es true:
        		this.tortugas[i] = new Tortugas();		//Genera una nueva tortuga.
        		tEnPantalla++;							//Aumenta en 1 la cantidad en pantalla.
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
            isla.dibujarIslas(entorno);	//Se dibujan las islas generadas
        }
    }

    private void generarPep() {
    	pep = new Pep(400, 480);
    }
    

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
        
        //Cuando inicia el juego | pierde | gana se muestra su pantalla correspondiente

        if (inicio) {
            dibujarInicio();
        } else if (tutorial) {
        	dibujarTutorial();
        } else if (gameOver) {
        	dibujarGameOver();
        } else if (victoria) {
            dibujarVictoria();
        } else { 	//Si no pasa ninguna se dibuja otra y se actualiza el estado del juego
            entorno.dibujarImagen(fondo, 400, 240, 0, 0.7);
            entorno.dibujarImagen(casa, 400, 40, 0, 0.4);
            actualizarEstadoDelJuego();
        }
    }
    
  //Actualiza las facetas del juego mientras el usuario este jugando. 
    
    private void actualizarEstadoDelJuego() {
    	if (!inicio || !gameOver || !victoria) {	//Si alguna de las tres opciones es verdadera, aumenta el tiempo de juego.
    		tiempoJugando++;
    		if ((tiempoJugando/60) == 60){ 		//Convierte el tiempo de segundos a minutos, y cuando el tiempo es igual a 60, vuelve a reiniciarse.
    			tiempoJugando = 0;
    			minsJugando++;
    		}
    		actualizarTiempo();				//Lógica que actualiza el tiempo del juego.
    		estadisticas();					//Actualiza las estadisticas en pantalla.
    		funcionamientoGnomos();			//Lógica del funcionamiento de los gnomos.
    		generarIslas();					//Generación de las islas
    		funcionamientoDePep();			//Lógica del funcionamiento de Pep.
    		controlarMovimientoPep();		//Lógica de como se mueve pep dentro del juego.
    		funcionamientoBolaDeFuego();	//Lógica del funcionamiento de la bola de fuego.
    		funcionamientoTortugas();		//Lógica del funcionamiento de las tortugas.
    		funcionamientoBombas();         //Lógica del funcionamiento de las bombas.
    		funcionamientoNave();			//Lógica del funcionamiento de la navecita.
    	}
    }


    
    private void actualizarTiempo() {
	        segundos = (entorno.tiempo() / 1000)% 60;
	        minutos = (segundos / 60) % 60;
	        horas = minutos / 3600;

	        /*
	         * Tanto en la generacion de gnomos como tortugas, existe un intervalo de generación llamado cooldown, que
	         * solo se activa si segundos es múltiplo de un número especificado por los ifs, y si no se ha generado ya un 
	         * gnomo/tortuga. De ser el caso contario, el juego deberá esperar a que se cumplan los segundos necesarios
	         * para cambiar el valor de gnomoGenerado o tortugaGenerada a false.
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
 
    /*
     * Verifica en cada instancia el comportamiento de los gnomos. 
     * Cuando este se vuelve NULL debido a una colision con objetos que se encuentran en el juego,
     *  vuelve a generar uno nuevo a los segundos en la casa.
     */
	private void funcionamientoGnomos() {
	    Random random = new Random();		//Generación de un random.
	    for(int i=0; i < gnomos.length; i++) {
	    	Gnomos g = gnomos[i];			//Guarda el indice de cada gnomo en g.
	    	actualizarTiempo();				//Lógica de la generación de gnomos. 
	    	if(g == null && cooldownGnomos && !gnomoGenerado) {		//Si el gnomo es null, el cooldown de Gnomos es true y hay gnomos generados
	    		gnomos[i] = new Gnomos(400,83,10,20);			//Crea un nuevo Gnomo.
	    		gnomoGenerado = true;				//Informa que se genero un gnomo.
	    	}
	    	if (g != null) {
	            g.dibujarse(entorno);
	            EstadoDeGnomos(g, random);			//llama a la función que controla la colisión de gnomos y su movimiento.
	            
	            if (pep.colisionGnomos(g)&& pep.getY() > 350) {		//Si los gnomos son salvados por pep a partir de la fila 3:
	                gnomos[i] = null;
					gnomosSalvados++;	//Aumenta el número de gnomos salvados.
					
				}
	            
	            if(g!= null && g.colisionNavecita(navecita)) {			//Si gnomo no es null y colisiona con la nave:
					gnomos[i] = null;							//Vuelve la instancia del gnomo a null.
					gnomosSalvados++;					//Aumenta el número de gnomos salvados.	
				}
	            
	            for (Tortugas t : tortugas) {			//Recorre el array de tortugas.
	            	
	                if (t != null && g.colisionTortugas(t)) { //Si la tortuga no es null y colisiona con un gnomo
	                	gnomos[i] = null; 		//Vuelve la instancia del gnomo a null.
	                    gnomosPerdidos++;			//Aumenta el número de gnomos perdidos.
	                }
	            }
	            
	            if (g.limiteSuperior() > entorno.alto()) {		//Si el gnomo sale de pantalla:
	            	gnomos[i] = null; 		//Vuelve la instancia del gnomo a null.
	                gnomosPerdidos++;		//Aumenta el número de gnomos perdidos.
	            }
	        }
	    }
	}

	
	
	/*
	 * Controla el movimiento y las colisiones de gnomos con las islas
	 */
	private void EstadoDeGnomos(Gnomos g, Random random) {
	    boolean estaEnIsla = false;			//Inicializa esta en la isla en false.
	    for (Islas isla : islas) {			//Recorre las islas
	        if (g.limiteInferior() == isla.limiteSuperior() &&
	            g.limiteIzquierdo() > isla.limiteIzquierdo() - g.ancho &&
	            g.limiteDerecho() < isla.limiteDerecho() + g.ancho) {
	            estaEnIsla = true;		//Si el gnomo colisionó con una isla, la variable 'esta en la isla' informa que es verdadera.
	            break;					//Sale del bloque.
	        }
	    }
	    
	    g.apoyado = estaEnIsla;		//la variable esta apoyado toma la condicion de 'estaEnIsla'
	    if (g.apoyado) {		//Si el gnomo esta apoyado:
	        g.caminar = !g.mirar ? random.nextBoolean() : g.caminar; 	//Cambia el estado de g.caminar de forma aleatoria.
	        g.mirar = true; 	//Vuelve g.mirar a true.
	        if (g.caminar) {		//Si el estado es verdadero
	            g.moverDerecho();	//Se mueve a la derecha
	        } else {
	            g.moverIzquierda();	//Se mueve a la Izquierda.
	        }
	    } else {		//Si no esta apoyado, el gnomo sigue cayendo y mirar se vuelve false.
	        g.caida();
	        g.mirar = false;
	    }
	}
	
	
	/*
	 * Es el comportamiento de las tortugas. 
	 * Si la tortuga es NULL, su cooldown de generacion es falso y torugasgenerada es true, se crea una nueva tortuga 
	 * en una posición aleatoria en x. 
	 */
	private void funcionamientoTortugas() {
		Random random1 = new Random();		//Generación de un random.
		for(int i = 0; i < tortugas.length; i++) {
				Tortugas t1= tortugas[i];	//Guarda el indice de cada tortuga en t1
				
				if(t1 == null && cooldownTortugas && !tortugaGenerada) {
					tortugas[i] = new Tortugas();		//Se genera una nueva tortuga en una posición random en x
					tortugaGenerada = true;	//Informa que se genero una tortuga
				}	
				
				
				if(t1 != null) {	//Si la tortuga no es null
					t1.dibujarse(entorno);

					estadoTortugas(t1,random1);	//Comprueba  si colisiona con una isla o sigue cayendo
					
					//Si la tortuga sale de la pantalla, la misma se vuelve null y aumenta las tortugas eliminadas
					if(tortugas[i].limiteSuperior() > entorno.alto()) {
						tortugas[i] = null;	//La tortuga se vuelve NULL
						break;	//Sale del bloque
					}
		
					if (bolaDeFuego !=null && bolaDeFuego.colisionTortugas(t1)) {	//Si la bola de fuego no es NULL y la bola colisiona con una tortuga:
						tortugas[i] = null;	//La tortuga se vuelve NULL
			            bolaDeFuego = null;	//La bola de fuego se vuelve NULL
						tortugasEliminadas++;	//Aumenta la cantidad de tortugas eliminadas
						break;	//Sale del bloque
					}
					
					//
					if(t1.limiteInferior() + 15 >= pep.limiteInferior() && t1.limiteSuperior() - 15 <= pep.limiteSuperior()) { //Si tortuga está en a misma fila de islas que Pep:
						if (bombas==null && t1.caminar && t1.x < pep.x && pep.limiteInferior() != 495) {    //Si la bomba no es NULL, si tortuga no está en la fila que Pep se genera y si tortuga está mirando hacia Pep:
							bombas = new Bombas(t1.limiteDerecho() + 5, t1.y - 5, true); //Se genera una bomba disparada por la tortuga hacia la dirección en la que está mirando
						}
						if (bombas==null && !t1.caminar && t1.x > pep.x && pep.limiteInferior() != 495) {
							bombas = new Bombas(t1.limiteIzquierdo() - 5, t1.y - 5, false);	
						}
					}
				}        
			}
		}
	
	
	/*
	 * Es el comportamiento de la tortuga que verifica cuando esta colisionando con alguna isla. 
	 * Si se encuentra en una isla, llama a la función 'iniciarMovHorizontal' que hace que la tortuga se quede sobre una isla.
	 * Por el contrario, si no colisiono con alguna isla sigue cayendo.
	 */
	public void estadoTortugas(Tortugas t, Random random1) {
		boolean enIsla = false;
		for(Islas isla:islas) {
			//Ver si la tortuga toca la isla
			if(t.limiteInferior() == isla.limiteSuperior()&&
					t.limiteIzquierdo()> isla.limiteIzquierdo()-t.ancho &&
					t.limiteDerecho()< isla.limiteDerecho()+t.ancho) {
				enIsla = true; //Infoma que la tortuga esta en una isla.
				t.apoyado = true;   //La tortuga esta sobre la superficie de una isla.
				iniciarMovHorizontal(t, random1, isla); 	//Inicia el movimiento horizontal
				break;		//Sale de la condición
			}
		}
		
		if(!enIsla) { 	//Si no esta en una isla, sigue cayendo.
			t.apoyado = false;
			t.mira = false;
			t.caer();
		}	
	}
	
	
	/*
	 * Monitorea el movimiento de las tortugas cuando estan en una isla.
	 * Cuando la tortuga llega al borde de la isla cambia su dirreción para no salirse. 
	 */
	private void iniciarMovHorizontal(Tortugas t, Random random1, Islas isla) {
		t.mover();			//Se mueve la tortuga hacia la derecha o izquierda
		
		if(!t.mira) {		//Si no esta mirando a que dirrecion caer:
			t.caminar = random1.nextBoolean();		//Elige para que dirreción caminar con un booleano
			t.mira=true;		//Mirar se vuelve true
		}

		if(t.caminar && t.limiteIzquierdo() >= isla.limiteDerecho() - (t.ancho /2)) {		//Si caminar es verdadero y el limite izq de tortuga es mayor al borde derecho menos el ancho dividido 2
			t.caminar = false;				//Regresa hacia el lado opuesto
			t.mirandoDerecha = false;
		}
		if(!t.caminar && t.limiteDerecho() <= isla.limiteIzquierdo() + (t.ancho /2)) { //Se mueve hacia la izquierda hasta llegar al borde de la isla para luego cambiar de dirreción.
			t.caminar = true;			//Caminar se vuelve true
			t.mirandoDerecha = true;
		}
	}
	
	
	/*
	 * Indica el comportamiento de pep. Verifica constantemente si colsiona con una Isla y cuando esta por debajo de la misma. 
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

	    //Si pep cae del limite de la pantalla pierde una vida y aparece de nuevo en la isla inicial. 
	    if (pep.limiteInferior() > entorno.alto()) {
	        pep.x = 400;
	        pep.y = 480;
	        pep.apoyado = true;
	        vidas--;
	    }
	    
	    //Recorre el array de las tortugas.
	    //Cuando pep colisione con una tortuga, pierde una vida y aparece de nueveo en la isla inicial.
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
	  //Si pep pierde todas las vidas o si los gnomos perdidos es igual a 8, la variable 'gameOver' se vuelve true.
        if (vidas < 1 || gnomosPerdidos == 8) {
            gameOver = true;
        }
        
        //Si los gnomos salvados son 9, 'victoria' es true.
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
	    pep.tieneQueAsomarse(); //Cuando pep salta verifica si esta debajo de una isla, y si no lo está, verifica de que isla esta más cerca para cambiar el valor de x. 
	}
	
    /*
     * Controla el movimiento de pep mediante las entradas del teclado
     */
    
    private void controlarMovimientoPep() {
    //Pep solo puede moverse de izquierda a derecha o saltar mediante las teclas correspondientes si y solo si se encuentra apoyado sobre alguna isla
        boolean puedeMoverse = pep.apoyado;
        
      //Pep solo puede disparar si esta apoyado y si no hay ninguna bola de fuego en pantalla
        if ((entorno.sePresiono(entorno.TECLA_CTRL) || entorno.sePresiono('c')) && puedeMoverse && bolaDeFuego == null) {  
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
	        pep.moviendose = true;
	    }

	    if ((entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('d')) && pep.getX() < entorno.ancho() - 5 && puedeMoverse) {
	        pep.moverDerecha();
	        pep.moviendose = true;
	    }

	    if ((entorno.sePresiono(entorno.TECLA_ARRIBA) || entorno.estaPresionada('w')) && puedeMoverse && pep.limiteInferior() > 196) {
	        pep.salto();
	    }

	    if ((entorno.sePresiono(entorno.TECLA_ABAJO)) || entorno.sePresiono('s') && puedeMoverse) {
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
	    if (bolaDeFuego != null && bolaDeFuego.disparada) {	//Si no es NULL  y fue disparada se activa el disparo
	        bolaDeFuego.disparo();
	        
	        if (bolaDeFuego.getX() < 0 || bolaDeFuego.getX() > entorno.ancho()) {	//Si la nave sale de pantalla se vuelve NULL
	            bolaDeFuego = null;
	        }	       
	    }
	}
    
    /*
     * Dibuja y controla el estado de las bombas disparadas por tortugas
     */
    private void funcionamientoBombas() {
    	if (bombas != null) {
    		bombas.dibujarse(entorno);
    	}
    	if (bombas != null && bombas.disparada) {
    	    bombas.disparo();
    	        
    	    if (bombas.getX() < 0 || bombas.getX() > entorno.ancho()) {	//Si la bomba sale del ancho de la pantalla vale NULL
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
	    	navecita.encendida = true;
	        navecita.moverseHacia(entorno.mouseX());
	    }
	    else {
	    	navecita.encendida = false;
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
	    

	    entorno.dibujarImagen(gnomo, 40, 170, 0, 1.5);
	    entorno.escribirTexto("" + gnomosSalvados, 60, 180);
	    

	    entorno.dibujarImagen(perdidos, 40, 230, 0, 1.5);
	    entorno.escribirTexto("" + gnomosPerdidos, 60, 240);
	    

	    entorno.dibujarImagen(kills, 40, 270, 0, 1.5);
	    entorno.escribirTexto("" + tortugasEliminadas, 60, 280);
	    entorno.dibujarImagen(reloj, 670, 40, 0, 1.5);
	    

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
	 
	//Se dibuja la pantalla cuando el usuario pierde.
	 private void dibujarGameOver() {
		 entorno.dibujarImagen(gameOverScreen, 400, 300, 0, 1);
		 entorno.dibujarImagen(gameOverText, 400, 50, 0, 1);
		 
		 if (entorno.sePresiono(entorno.TECLA_ENTER)) {
			 iniciarJuego();
		 }
	 }

//	 Se dibuja la pantalla cuando el usuario gana.
	 private void dibujarVictoria() {
		 entorno.dibujarImagen(winScreen, 400, 300, 0, 1);
		 entorno.dibujarImagen(winText, 400, 50, 0, 1);
		 
		 if (entorno.sePresiono(entorno.TECLA_ENTER)) {
			 iniciarJuego();
		 }
	 }

	 
	 /*
		 * Reinicia todas las variables a su estado inicial
		 * (en proceso) reinicia el juego
		 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}