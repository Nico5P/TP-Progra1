package juego;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.Color;
import java.awt.Image;

public class BoladeFuego {
	 double x, y;
	 double ancho;
	 double alto;
	 double velocidad = 10;
	 boolean disparada; //Se usa para saber si fue disparada
	 boolean direccionDerecha; //Direccion en la que se movera
	 boolean direccion;
	 Image imagen;
	 

	public BoladeFuego(double x, double y, boolean direccion) {
	        this.x = x;
	        this.y = y;
	        this.direccion = direccion;
	        this.velocidad = 10; // Ajusta la velocidad según sea necesario
	        this.alto=16;
	        this.ancho=16;
	        this.disparada = true;
	        this.imagen = Herramientas.cargarImagen("juego/imagenes/bolaDeFuego.webp");
	 }
	 
	 public void disparo() {
		 if (direccion && x<810) {
			 x += velocidad; // Mover hacia la derecha
		 } else if (!direccion && x>-10){
			 x -= velocidad; // Mover hacia la izquierda
		 }
	 }


	 public void dibujarse(Entorno entorno) 
	 {
		 if (disparada) 
		 {
			 entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.orange);
			 
		 }
	 }
	 
	 public boolean colisionTortugas(Tortugas t) {
		 return (this.limiteIzquierdo() < t.limiteDerecho() &&
				 this.limiteDerecho() > t.limiteIzquierdo() &&
				 this.limiteSuperior() < t.limiteInferior() &&
				 this.limiteInferior() > t.limiteSuperior());
	 }
	 
	 public double getX() 
		{
			return this.x;
		}

	 public double getY() 
	 {
		 return this.y;
	 
	 }
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
	 public double centro() 
	 {	
		 return this.x + (this.ancho / 2);
	 }
}
