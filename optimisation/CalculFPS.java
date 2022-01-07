package optimisation;

import java.util.List;
import java.util.TimerTask;

public class CalculFPS extends TimerTask {

	IntHolder k;
	int delta;
	List<Double> listeFPS;

	CalculFPS(IntHolder k,int delta, List<Double> listeFPS){
		this.k = k;
		this.delta = delta;
		this.listeFPS = listeFPS;
	}

	@Override
	public void run() {
		double fps = (double)k.value/(double)delta;
		listeFPS.add(fps);
		k.value = 0;
		System.out.println(fps + " fps");
	}
}