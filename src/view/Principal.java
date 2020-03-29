package view;

import java.util.concurrent.Semaphore;

import controller.ThreadTriatlo;

public class Principal {

	public static void main(String[] args) {
		Thread triatlo;
		Semaphore semaforoTiros = new Semaphore(5);
		Semaphore semaforo = new Semaphore(1);
		for (int i = 1; i <= 25; i++) {
			triatlo = new ThreadTriatlo(i, semaforoTiros, semaforo);
			triatlo.start();
		}
	}

}
