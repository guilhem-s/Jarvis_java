package S5;


	import lejos.hardware.Sound;

	public class MusicComposer {
		
		public static void playFinalCountdown() {
	        int tempo = 120; // Réglez le tempo comme vous le souhaitez
	        int duration = 60000 / tempo; // Calculez la durée de chaque temps en millisecondes

	        int[] melody = {
	            // Utilisez les fréquences des notes pour jouer "The Final Countdown"
	            330, 330, 392, 523, 659, 784, 988, 784, 659, 523, 392, 330, 392, 523, 659, 784, 988, 784, 659, 523, 392,
	            330, 392, 784, 660, 784, 880, 988, 880, 784, 660, 784, 1109, 988, 784, 659, 988, 784, 523, 330,
	            659, 784, 880, 988, 880, 784, 659, 880, 784, 523, 659, 784, 880, 784, 659, 523, 330
	        };

	        int[] noteDurations = {
	            // Définissez les durées de chaque note
	            8, 8, 8, 8, 8, 8, 4, 8, 8, 8, 8, 8, 8, 8, 8, 4, 8, 8, 8, 8, 8,
	            8, 8, 4, 8, 8, 8, 8, 8, 8, 8, 4, 8, 8, 8, 4, 8, 8, 8, 8, 8,
	            8, 4, 8, 8, 8, 8, 8, 8, 4, 8, 8, 8, 8, 8, 8, 4
	        };

	        for (int i = 0; i < melody.length; i++) {
	            int noteDuration = duration / noteDurations[i];
	            Sound.playTone(melody[i], noteDuration);
	            Sound.pause(noteDuration / 10); // Pause entre les notes
	        }
	    }
		
	    public static void main(String[] args) {
	        playFinalCountdown();
	    }
	}

