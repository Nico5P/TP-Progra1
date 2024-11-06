package juego;

import java.util.Random;
import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;
//import java.awt.Color; se usaba para generar el color del rectangulo

public class Tortugas {
	Entorno[] entorno;
	Islas[] islas;
	double x, y;
	double ancho, alto;	
	double velocidad = 2;
	boolean apoyado;
	boolean derecha;
	boolean caminar;
	boolean mira;
	boolean mirandoDerecha;
	Image imagen;
	
	
	/*
	 * Dentro del método Tortugas tenemos dos posibles intervalos de aparición, xSpawn1 y xSpawn2. El número aleatorio
	 * "dondeSpawnea" puede ser >1 o <1 y decide en cuál de los dos intervalos se genera el eje X de la tortuga.
	 */
	public Tortugas() {
		Random gen = new Random();
		double xSpawn1 = gen.nextInt(250) + 1; //intervalo de generacion en eje X desde 0 hasta 251
		double xSpawn2 = gen.nextInt(795) + 490; //intervalo de 490 a 794
		int dondeSpawnea= gen.nextInt(2) + 1;
		if(dondeSpawnea>1) {
			this.x = xSpawn2;
			this.derecha=true;
		}
		else {
			this.x = xSpawn1;
			this.derecha=false;
		}
		this.ancho=18;
		this.alto=18;
		this.apoyado=false;
		this.caminar=true;
	}
	
	public void dibujarse(Entorno entorno) { //Cambia la imagen dependiendo de la dirección de la tortuga
        if ( this.y < 600) {
            if (!apoyado) {
                imagen = Herramientas.cargarImagen("juego/imagenes/tortCaer.png");
            }
            else if (mirandoDerecha) {
                imagen = Herramientas.cargarImagen("juego/imagenes/tortDer.png");
            }
            else {
                imagen = Herramientas.cargarImagen("juego/imagenes/tortIzq.png");
            }
//            entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.white); hitbox
        }
        entorno.dibujarImagen(imagen, x-5, y-12, 0, 1);
    }

    public void caer() { //Incrementa el valor de Y si no se encuentra sobre una isla
        if(!this.apoyado) { 
            this.y += 2;
        }
    }

    public void mover() { //Controla el movimiento de izquierda a derecha
        if (caminar) {
            this.x += 1; // Mover a la derecha
            mirandoDerecha = true;
        } else {
            this.x -= 1; // Mover a la izquierda
            mirandoDerecha = false;
        }
    }

    public boolean colisionIslas(Islas i) {
    	return (this.limiteIzquierdo() < i.limiteDerecho() &&
    			this.limiteDerecho() > i.limiteIzquierdo() &&
    			this.limiteSuperior() < i.limiteInferior() &&
    			this.limiteInferior() > i.limiteSuperior());
    }
    
    public boolean colisionFuego(BoladeFuego b) {
        return (this.limiteIzquierdo() < b.limiteDerecho() &&
                this.limiteDerecho() > b.limiteIzquierdo() &&
                this.limiteSuperior() < b.limiteInferior() &&
                this.limiteInferior() > b.limiteSuperior());
    }
	
  //Limites y valores de la hitbox
    
	public double getY() {
		return this.y;
	}

	public double getX() {
		return this.x;
	}
	
	public boolean getapoyado() {
		return this.apoyado;
	}
	
	public double limiteSuperior() {
		return this.y - this.alto/2;
	}
	
	public double limiteInferior() {
		return this.y + this.alto/2;
	}
	
	public double limiteIzquierdo() {
		return this.x-this.ancho/2;
	}
	
	public double limiteDerecho() {
		return this.x+this.ancho/2;
	}
}