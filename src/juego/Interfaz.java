package juego;

import entorno.Entorno;

public class Interfaz {
    private boolean pausado;
    private int seleccion; // 0 para "Continuar", 1 para "Reiniciar"

    public Interfaz() {
        this.pausado = false;
        this.seleccion = 0;
    }

    public boolean pausado() {
        return pausado;
    }

    public void noPausado() {
        pausado = !pausado;
    }

    public int opcionElegida() {
        return seleccion;
    }

    public void cambiarOpcion(int direccion) {
        seleccion += direccion;
        if (seleccion < 0) seleccion = 0;
        if (seleccion > 1) seleccion = 1;
    }

    public void dibujarMenu(Entorno entorno) {
        int ancho = entorno.ancho();
        int alto = entorno.alto();
        entorno.cambiarFont("Arial", 30, java.awt.Color.WHITE);
        
        // Mensaje de pausa
        entorno.escribirTexto("Juego Pausado", ancho / 2 - 100, alto / 2 - 50);
        
        // Opción "Continuar"
        if (seleccion == 0) {
            entorno.cambiarFont("Arial", 30, java.awt.Color.YELLOW);
        } else {
            entorno.cambiarFont("Arial", 30, java.awt.Color.WHITE);
        }
        entorno.escribirTexto("Continuar", ancho / 2 - 50, alto / 2);

        // Opción "Reiniciar"
        if (seleccion == 1) {
            entorno.cambiarFont("Arial", 30, java.awt.Color.YELLOW);
        } else {
            entorno.cambiarFont("Arial", 30, java.awt.Color.WHITE);
        }
        entorno.escribirTexto("Reiniciar", ancho / 2 - 50, alto / 2 + 50);
    }
}