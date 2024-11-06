package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
//import java.awt.Color; se usaba para generar el color del rectangulo

public class Gnomos {
	double x, y;
	double ancho, alto;
	double velocidad = 1;
	double ticks;
	double posicionX;
	double posicionY;
	boolean derecha=false;
	boolean apoyado;
	boolean direccion;
	boolean caminar=false; 
	boolean mirar=false; 
	boolean salvado;
	Image imagen;
	
	public Gnomos(double x, double y, double ancho, double alto) {
		this.x=x;
		this.y=y;
		this.ancho=ancho;
		this.alto=alto;
		this.direccion= Math.random() <0.5;
	}
	
	public void dibujarse(Entorno entorno) {
		if (apoyado) {
			if (derecha) {
				imagen = Herramientas.cargarImagen("juego/imagenes/gIzq.png");				
			} else {				
				imagen = Herramientas.cargarImagen("juego/imagenes/gDer.png");				
			}
		}
		else if (!apoyado && derecha) {
			imagen = Herramientas.cargarImagen("juego/imagenes/gCaerIzq.png");
		} else {
			imagen = Herramientas.cargarImagen("juego/imagenes/gCaerDer.png");
		}
			
		//entorno.dibujarRectangulo(x, y, ancho, alto,0, Color.yellow);
		entorno.dibujarImagen(imagen, x, y, 0, 1);
	}
	
	public void caida() {			//Cuando los gnomos no estan en una isla, disimula la caída.
		if(!apoyado) {				//Si el gnomo no esta apoyado en una isla.
			this.y += 2;			//Aumenta la coordenada y, para que simule una caída
		}
	}
	
	public void moverIzquierda() {	//Mover a la izquierda.
        	this.x+=velocidad;
        	derecha = false;
    }
	
	public void moverDerecho() {	//Mover a la derecha.
			this.x -=velocidad;
			derecha = true;        		
	}
	
	
	/*
	 * Colisión de gnomos con distintos objetos: Tortugas y la Navecita.
	 */
    
    public boolean colisionTortugas(Tortugas t) {
        return (this.limiteIzquierdo() < t.limiteDerecho() &&
                this.limiteDerecho() > t.limiteIzquierdo() &&
                this.limiteSuperior() < t.limiteInferior() &&
                this.limiteInferior() > t.limiteSuperior());
    }


    
    public boolean colisionNavecita(Navecita n) {
    	return (this.x < n.getX() + n.ancho &&
                this.x + this.ancho > n.getX() &&
                this.y < n.getY() + n.alto &&
                this.y + this.alto > n.getY());
    }
    

    /*
     * Los limites de los gnomos.
     */
    public double limiteSuperior() 
    {
    	return this.y - this.alto/2;
    }
    
    public double limiteInferior() 
    {
    	return this.y + this.alto/2;
    }
    
    public double limiteIzquierdo() 
    {
    	return this.x-this.ancho/2;
    }
    public double limiteDerecho() 
    {
    	return this.x+this.ancho/2;
    }
}