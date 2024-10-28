package juego;

import entorno.Entorno;
import java.awt.Color;

public class BoladeFuego {
	
	 double x, y;
	 double ancho;
	 double alto;
	 double velocidad = 10;
	 boolean disparada; //Se usa para saber si fue disparada
	 boolean direccionDerecha; //Direccion en la que se movera
	 
	 public void BolaDeFuego(double x, double y, boolean direccionDerecha) {
		 this.x = x;
		 this.y = y;
		 this.ancho = 10;
		 this.alto = 5;
		 this.disparada = true;
		 this.direccionDerecha = direccionDerecha;
	 }
	 
	 public void disparo() {
	        if (direccionDerecha) {
	            x += velocidad;  //Derecha
	        } else {
	            x -= velocidad;  //Izquierda
	        }
			 if (x < 0 || x > 800) {
				 disparada = false;
			 }
		 }
	 }

	 public boolean fueDisparada() {
	        return disparada;
	 }

	 public void dibujarse(Entorno entorno) {
		 if (disparada) {
			 entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.orange);
		 }
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
