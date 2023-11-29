package S5;

import lejos.hardware.Button;

public class Surveillance {
	// Utilisation de thread pour récupérer données en parallèle de méthodes
    private Thread surveillanceBoutonThread;

    
    public void lancerSurveillanceBouton() {
        surveillanceBoutonThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (Button.ESCAPE.isDown())  System.exit(0);
                    // Pause courte pour éviter surcharge
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    	e.printStackTrace();
                    }
                }}});
        surveillanceBoutonThread.start();
    }

    // Méthode pour attendre que les threads de surveillance se terminent
    public void attendreFinSurveillance() {
        try {
            if (surveillanceBoutonThread != null) {
                surveillanceBoutonThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }}}
