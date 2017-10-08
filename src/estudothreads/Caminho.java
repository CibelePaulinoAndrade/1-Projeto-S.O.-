package estudothreads;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Caminho extends Thread{
	private SemaforoNumeroCarrosFila semaforoNumeroCarrosFila;

	private Cancela cancela;
	private String caminho;
	private Integer nCarrosAtravessando;
	
	public Caminho(Cancela cancela, String caminho){
		this.nCarrosAtravessando = 0;
		this.semaforoNumeroCarrosFila = new SemaforoNumeroCarrosFila(0);
		this.cancela = cancela;
		this.caminho = caminho;
	}
	@Override
	public void run() {
		try {
			while(true){
				/*funcao do caminho eh sempre tentar ser a nova direcao*/
				semaforoNumeroCarrosFila.acquire(); //espera ate poder tentar ser a direcao da ponte, que � quando o primeiro carro chega na fila
				Ponte.ponte().getSemaforoLiberaCaminho().acquire(); //quando o primeiro carro chega, espera ate ser a nova direcao da ponte
				Ponte.ponte().setDirecao(this); //quando ele � a direcao da ponte, seta a direcao da ponte para ele mesmo
				//ponte liberada
				Log.doLog("caminho liberado");
				cancela.getSemaforoNumeroCarrosPodemAtravessar().release();
				nCarrosAtravessando ++;
				Log.doLog(this);
				cancela.getSemaforoCancelaLiberada().release();//libera a cancela para aceitar novos carros
			}
		} catch (InterruptedException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public SemaforoNumeroCarrosFila getSemaforoNumeroCarrosFila() {
		return semaforoNumeroCarrosFila;
	}
	public void setSemaforoNumeroCarrosFila(SemaforoNumeroCarrosFila semaforoNumeroCarrosFila) {
		this.semaforoNumeroCarrosFila = semaforoNumeroCarrosFila;
	}
	public Cancela getCancela() {
		return cancela;
	}
	public void setCancela(Cancela cancela) {
		this.cancela = cancela;
	}
	public String getCaminho() {
		return caminho;
	}
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	public class SemaforoNumeroCarrosFila extends Semaphore{

		public SemaforoNumeroCarrosFila(int permits) {
			super(permits);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void reducePermits(int reduction) {
			// TODO Auto-generated method stub
			super.reducePermits(reduction);
		}
	}
	public Integer getnCarrosAtravessando() {
		return nCarrosAtravessando;
	}
	public void setnCarrosAtravessando(Integer nCarrosAtravessando) {
		this.nCarrosAtravessando = nCarrosAtravessando;
	}
	
}
