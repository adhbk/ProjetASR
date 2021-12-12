package optimisation;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.*;

public class ThreadSaveImages1000Times extends Thread {
	
	Mat img;
	int nbThreads;
	ThreadSaveImages1000Times(Mat img,int nbthreads){
        this.img = img;
        this.nbThreads = nbthreads;
	}
	
	public void run() {
		for(int i = 0; i < 1000/nbThreads ; i++) {
			Imgcodecs.imwrite("test"+ Thread.currentThread().getId() +".png",img);
		}
	}

	

	
}
