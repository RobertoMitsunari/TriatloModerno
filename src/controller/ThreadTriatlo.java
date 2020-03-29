package controller;



import java.util.concurrent.Semaphore;

public class ThreadTriatlo extends Thread{
	private int idAtleta;
	static private int[] placarPontos = new int[25];
	static private int[] atletaColocacao= new int[25];
	static private int indice = 0;
	static private int qntPontos = 250;
	private Semaphore semaforo;
	private Semaphore semaforoPontos;
	private int pontos;
	
	
	
	public ThreadTriatlo(int idAtleta, Semaphore semaforo, Semaphore semaforoPontos) {
		this.idAtleta = idAtleta;
		this.semaforo = semaforo;
		this.semaforoPontos = semaforoPontos;
		this.pontos = 0;
	}

	private void corrida() {
		int vel = (int) ((Math.random() * 6) + 20);
		System.out.println("Atleta: " + idAtleta + " começou a correr");
		for(int x = 0;x < 3000;x += vel) {
			try {
				sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Atleta: " + idAtleta + " terminou de correr");
	}
	
	private int tiroAoAlvo() {
		int pontos = 0;
		System.out.println("Atleta: " + idAtleta + " está no tiro ao alvo");
		for(int i = 0; i < 3;i++) {
			try {
				sleep((int) ((Math.random() * 2550) + 500));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			pontos += (int) ((Math.random() * 10));
		}
		System.out.println("Atleta: " + idAtleta + " saiu do tiro ao alvo");
		return pontos;
	}
	
	private void corridaBike() {
		int vel = (int) ((Math.random() * 11) + 30);
		System.out.println("Atleta: " + idAtleta + " começou a correr de bike");
		for(int x = 0;x < 5000;x += vel) {
			try {
				sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Atleta: " + idAtleta + " terminou de correr de bike");
	}
	
	private void mostraColocacao() {
		int tempPonto;
		int tempAtleta;
		for(int x = 0;x < 24;x++) {
			for(int y = x + 1;y < 25;y++) {
				if(placarPontos[x] <= placarPontos[y]) {
					tempPonto = placarPontos[x];
					placarPontos[x] = placarPontos[y];
					placarPontos[y] = tempPonto;
					tempAtleta = atletaColocacao[x];
					atletaColocacao[x] = atletaColocacao[y];
					atletaColocacao[y] = tempAtleta;
					
				}
			}
		}
		for(int x = 0;x < 25;x++) {
			System.out.println("Colocação: " + (x+1) + " atleta: " + atletaColocacao[x] + " pontos: " + placarPontos[x]);
		}
		
	}
	
	
	@Override
	public void run() {
		corrida();
		try {
			semaforo.acquire();
			this.pontos = tiroAoAlvo();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			semaforo.release();
		}
		corridaBike();
		
		try {
			semaforoPontos.acquire();
			pontos += qntPontos;
			qntPontos -= 10;
			placarPontos[indice] = pontos;
			atletaColocacao[indice] = this.idAtleta;
			indice++;
			if(indice == 25) {
				mostraColocacao();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			semaforoPontos.release();;
		}
		

		
	}

}
