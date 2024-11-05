package juego;

import entorno.Entorno;
import java.awt.Color;

public class Interfaz {
    private boolean pausado;
    private int seleccion; // 0 para "Continuar", 1 para "Reiniciar"

    public Interfaz() {
        this.pausado = false;
        this.seleccion = 0;
    }

    public void noPausado() {
        pausado = !pausado;
    }

    public void cambiarOpcion(int direccion) {
        seleccion += direccion;
        if (seleccion < 0) seleccion = 0;
        if (seleccion > 1) seleccion = 1;
    }

    public void dibujarMenu(Entorno entorno) {
        int ancho = entorno.ancho();
        int alto = entorno.alto();
        entorno.cambiarFont("Arial", 30, Color.WHITE);
        
        /*
         * Dibuja "Pausa" en el menu de pausa
         */
        
        entorno.escribirTexto("Pausa", ancho / 2 - 100, alto / 2 - 50);
        
        /*
         * Dibuja la opcion "continuar" y "reiniciar" y le cambia el color a la seleccionada
         * para que el usuario sepa cual esta por escoger
         */
        if (seleccion == 0) {
            entorno.cambiarFont("Arial", 30, Color.YELLOW);
        } else {
            entorno.cambiarFont("Arial", 30, Color.WHITE);
        }
        entorno.escribirTexto("Continuar", ancho / 2 - 50, alto / 2);
        
        if (seleccion == 1) {
            entorno.cambiarFont("Arial", 30, Color.YELLOW);
        } else {
            entorno.cambiarFont("Arial", 30, Color.WHITE);
        }
        entorno.escribirTexto("Reiniciar", ancho / 2 - 50, alto / 2 + 50);
    }
    
    public boolean pausado() {
    	return pausado;
    }
    
    public int opcionElegida() {
    	return seleccion;
    }
}