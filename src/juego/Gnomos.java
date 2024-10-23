//package juego;
//
//import java.awt.Color;
//import java.util.Random;
//import java.util.Timer;
//
//import entorno.Entorno;
//
//public class Gnomos {
//	double x, y;
//	double ancho, alto;
//	boolean apoyado= false;
//	boolean direccion;
//	double velocidad =2;
//	boolean derecha=false;
////	Timer timer;
//	
//	
//	
//	public Gnomos(double x, double y, double ancho, double alto)
//	{
//		this.x=x;
//		this.y=y;
//		this.ancho=ancho;
//		this.alto=alto;
//		this.direccion=false;
//		
//		direccion= (new Random().nextInt(2)==0)? -1:1;
//	}
//	
//	public void dibujarse(Entorno entorno) {
//		if(direccion==-1) {
//			entorno.dibujarRectangulo(x, y, ancho, alto,0, Color.yellow);
//		} else {
//			entorno.dibujarRectangulo(x, y, ancho, alto,0, Color.yellow);
//		}
//	}
//	
//
//	
//	public double seMueve() {
//		return this.x+=direccion *velocidad;
//	}
//	
//	public double seDetiene() {
//		return this.x= limiteDerecho();
//	}
//	
//	public double limiteSuperior() 
//	{
//		return this.y - this.alto/2;
//	}
//	
//	public double limiteInferior() 
//	{
//		return this.y + this.alto/2;
//	}
//	
//	public double limiteIzquierdo() 
//	{
//		return this.x-this.ancho/2;
//	}
//	public double limiteDerecho() 
//	{
//		return this.x+this.ancho/2;
//	}
//
//
//}
