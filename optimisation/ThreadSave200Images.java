package optimisation;

import java.io.File;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.*;

public class ThreadSave200Images extends Thread {
	
	Mat img;
	int nbThreads;
	IntHolder cpt;
	
	ThreadSave200Images(Mat img,int nbthreads,IntHolder compteur){
        this.img = img;
        this.nbThreads = nbthreads;
        this.cpt = compteur;
	}
	
	public void run() {
		for(int i = 0; i < 500 ; i++) {
			Imgcodecs.imwrite("test"+ Thread.currentThread().getId() +".jpg",img);
			++cpt.value;
		}
		 File file = new File("test"+ Thread.currentThread().getId() +".jpg");
         
	     file.delete();
	}
}
