package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bombas {
		 double x, y;
		 double ancho;
		 double alto;
		 double velocidad = 10;
		 boolean bombaEnPantalla;
		 boolean disparada; //Se usa para saber si fue disparada
		 boolean direccionDerecha; //Direccion en la que se movera
		 boolean direccion;
		 Image imagen;
		 

		public Bombas(double x, double y, boolean direccion) {
		        this.x = x;
		        this.y = y;
		        this.direccion = direccion;
		        this.velocidad = 10; // Ajusta la velocidad según sea necesario
		        this.alto=16;
		        this.ancho=16;
		        this.disparada = true;
		        if (this.direccion) {
		        	this.imagen = Herramientas.cargarImagen("juego/imagenes/bolaDeFuegoDerecha.png");
		        }
		        else {
		        	this.imagen = Herramientas.cargarImagen("juego/imagenes/bolaDeFuegoIzquierda.png");
		        }
		        
		 }
		 
		 public void disparo() {
			 if (direccion && x<810) {
				 x += velocidad; // Mover hacia la derecha
			 } else if (!direccion && x>-10){
				 x -= velocidad; // Mover hacia la izquierda
			 }
		 }

		 public void dibujarse(Entorno entorno) {
			 if (disparada) {
				 entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.orange);
			 }
		 }
		 
		 public void dibujarBomba(Entorno entorno) {
			 entorno.dibujarImagen(imagen, x, y, 0, 0.6);
		 }
		 
		 
		//Limites y valores de la hitbox
		 
		 public double getX() {
				return this.x;
		 }
		 
		 public double getY() {
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
		 
		 public double centro() {	
			 return this.x + (this.ancho / 2);
		 }
}