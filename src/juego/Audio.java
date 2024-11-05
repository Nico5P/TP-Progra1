package juego;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Audio {
	
	private Clip clip;
	
	public void cargarSonido(String ruta) {
	    try {
	        File archivoSonido = new File(ruta);
	        if (!archivoSonido.exists()) {
	            System.out.println("Archivo de sonido no encontrado: " + ruta);
	        }
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivoSonido);
	        clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void play(String string) {
		if(clip != null) {
			clip.setFramePosition(0);
			clip.start();
		}
	}
	
	public void stop() {
		if(clip != null && clip.isRunning()) {
			clip.stop();
		}
	}
}