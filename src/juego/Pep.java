package juego;

import java.awt.Color;

import entorno.Entorno;

public class Pep {
	public double x, y; 
	public double ancho; 
	public double alto;
	public double VelocidadY = 0; // Velocidad a la que salta
	public double VelocidadX = 0;
    public boolean saltando = false; // Para saber si pep esta saltando o no
    public boolean mirandoDerecha = true; // Dirección de Pep
    public boolean apoyado;
    public boolean chocaCon;
    public boolean debajoDe;
    public boolean tieneQueMoverse;
    public boolean moviendose;
    public boolean salto;
    public boolean islaCercana;

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
			if(!debajoDe) {
				VelocidadY = 16;
			}
			else {
				VelocidadY = 9;
			}
			apoyado = false; 
		}
	}

	public void caer() { // Maneja la caida de pep
		if (saltando) {
			moviendose = false;
			this.y -= VelocidadY; // "Sube" a pep
			VelocidadY -= 1; //
			if (VelocidadY <= 0) { // Cambia el estado al llegar al "limite" de alto permitido en su salto 
				saltando = false;
				if(!debajoDe) {
					salto = true;
				}
			}
		} 
		
		else if (!apoyado) {
			this.y += 2; // sino esta apoyado aumenta "Y" para simular la "caida"
		}
	}
	
	/*
	 *Una vez que Pep terminó de saltar y de disminuir su valor de Y, si Pep 
	 */
	public void tieneQueAsomarse() { 
		if(salto && !saltando && !debajoDe) {
			VelocidadX = 8;
			moviendose = true;
			salto = false;
		}
		if(moviendose) 
		{
			if(mirandoDerecha) 
			{
				this.x += VelocidadX; //Suma al valor de x si Pep esta mirando a la derecha
			}
			else 
			{
				this.x -= VelocidadX; //Resta al valor de x si Pep esta mirando a la derecha
			}
			VelocidadX -= 1; //Resta gradualmente la distancia a la que Pep se debe acercar
			if (VelocidadX <= 0) 
			{
				moviendose=false;
			}
		}
	}
	
	//Una vez que tenemos la variable VelocidadX para que pep se acerque gradualmente a la isla, ejecutamos el
	//codigo que lleva a cabo este movimiento
	
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