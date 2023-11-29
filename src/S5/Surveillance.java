package S5;

import lejos.hardware.Button;

public class Surveillance {
    private Thread surveillanceBoutonThread;

    // Méthode pour lancer la surveillance du bouton
    public void lancerSurveillanceBouton() {
        surveillanceBoutonThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (Button.ESCAPE.isDown()) {
                        System.exit(0);}
                    // Pause courte
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
        }}
}