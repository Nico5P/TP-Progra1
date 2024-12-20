package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
//import java.awt.Color; se usaba para generar el color del rectangulo

public class Navecita {
	public double x, y;
	public double ancho;
	public double alto;
	public boolean mirandoDerecha;
	public boolean encendida;
	Image imagen;
	Image fuego;
	
	public Navecita(double x, double y) {
		this.x = x; 
		this.y = y; 
		this.ancho = 60; // Ancho de la nave
		this.alto = 30; // Alto de la nave
	}
	
	/*
	 * Este método se encarga de tomar el valor de X del mouse cuando su tecla izquierda está presionada. Este valor
	 * lo comparamos con la x del centro de la navecita para saber en qué dirección el usuario quiere moverse
	 */
	public void moverseHacia(double x) {
		if(x > this.centro() && x <= 800) {
			mirandoDerecha = true;
			this.x+=5;
		}
		if ((x < this.centro() && x > this.ancho) || (x < this.limiteIzquierdo() && x > 10)) {
			mirandoDerecha = false;
			this.x-=5;
		}
	}
	
	//Método que dibuja la imagen de la navecita
	public void dibujarse(Entorno entorno) {
//		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.magenta); //Esta línea dibuja la hitbox de la nave
		if (mirandoDerecha) {
			this.fuego = Herramientas.cargarImagen("juego/imagenes/bolaDeFuegoDerecha.png");
			this.imagen = Herramientas.cargarImagen("juego/imagenes/navecitaDerecha.png");
		}
		else {
			this.fuego = Herramientas.cargarImagen("juego/imagenes/bolaDeFuegoIzquierda.png");
			this.imagen = Herramientas.cargarImagen("juego/imagenes/navecitaIzquierda.png");
		}
		
		if (encendida) {
			if (mirandoDerecha) {
				entorno.dibujarImagen(fuego, x-35, y, 0, 0.25);				
			}
			else {
				entorno.dibujarImagen(fuego, x+35, y, 0, 0.25);			
			}
		}
		entorno.dibujarImagen(imagen, x, y, 0, 0.1);
	}
	
	
	//Limites y valores de la hitbox
	
	public double limiteSuperior() {
		return this.y - this.alto / 2;
	}
	
	public double limiteInferior() {
		return this.y + this.alto / 2;
	}
	
	public double limiteIzquierdo() {
		return this.x - this.ancho / 2;
	}
	
	public double limiteDerecho() {
		return this.x + this.ancho / 2;
	}
	
	public double centro() {
		return this.x + (this.ancho / 2);
	}
	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}
}