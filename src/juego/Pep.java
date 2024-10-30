package juego;

import java.awt.Color;

import entorno.Entorno;

public class Pep {
	public double x, y; 
	public double ancho; 
	public double alto;
	public double VelocidadY = 0; // Velocidad a la que salta
    public boolean saltando = false; // Para saber si pep esta saltando o no
    public boolean mirandoDerecha = true; // Dirección de Pep
    public boolean apoyado;
    public boolean chocaCon;
    public boolean tieneQueMoverse;

	public Pep(double x, double y) {
		this.x = x; 
		this.y = y; 
		this.ancho = 20; // Ancho de pep
		this.alto = 30; // Alto de pep
	}


	public void moverIzquierda() { //Mueve a pep a la izquierda
		if (!saltando && apoyado) { 
			this.x -= 5; 
			mirandoDerecha = false; 
		}
	}
	
	public void moverDerecha() {  //Mueve a pep a la derecha
		if (!saltando && apoyado) 
		{
			this.x += 5; 
			mirandoDerecha = true;
		}
	}

	public void salto() { // Método para saltar
		if (apoyado) { 
			saltando = true;
			VelocidadY = 16; 
			apoyado = false; 
		}
	}

	public void caer() { // Maneja la caida de pep
		if (saltando) {
			this.y -= VelocidadY; // "Sube" a pep
			VelocidadY -= 1; //
			if (VelocidadY <= 0) { // Cambia el estado al llegar al "limite" de alto permitido en su salto 
				saltando = false;
			}
		} 
		
		else if (!apoyado) {
			this.y += 2; // sino esta apoyado aumenta "Y" para simular la "caida"
		}
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.red);
	}
	
	public boolean colisionGnomos(Gnomos g) {
	    return (this.limiteIzquierdo() < g.limiteDerecho() &&
	            this.limiteDerecho() > g.limiteIzquierdo() &&
	            this.limiteSuperior() < g.limiteInferior() &&
	            this.limiteInferior() > g.limiteSuperior());
	}

	public boolean colisionTortugas(Tortugas t) {
	    return (this.limiteIzquierdo() < t.limiteDerecho() &&
	            this.limiteDerecho() > t.limiteIzquierdo() &&
	            this.limiteSuperior() < t.limiteInferior() &&
	            this.limiteInferior() > t.limiteSuperior());
	}

	public boolean colisionIslas(Islas i) {
	    return (this.limiteIzquierdo() < i.limiteDerecho() &&
	            this.limiteDerecho() > i.limiteIzquierdo() &&
	            this.limiteSuperior() < i.limiteInferior() &&
	            this.limiteInferior() > i.limiteSuperior());
	}
	
	public boolean ultimaDireccion(){
		return this.mirandoDerecha;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}
	
	public boolean getSaltando() {
		return this.saltando;
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