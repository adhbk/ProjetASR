package optimisation;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class OptimisationJPEG {
	
	long timeTook;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		FileWriter fileWriter;
		try {
			
			
			fileWriter = new FileWriter("data_average.csv");
			
			PrintWriter printWriter = new PrintWriter(fileWriter);
		    for(int i = 1 ; i <= 8 ; ++i) {
		    	double nbFPS = save200ImagesByThread(i,"C:\\Users\\Adrien\\Pictures\\average.jpg");
		    	printWriter.printf("%d;%f\n", i,nbFPS);
		    	System.out.println(i + " Threads = " + nbFPS + " fps (average)");
		    }
		    printWriter.close();
		    
		    
		    
			fileWriter = new FileWriter("data_average_moyenne.csv");

		    printWriter = new PrintWriter(fileWriter);
		    for(int i = 1 ; i <= 8 ; ++i) {
		    	float temps1000Images = save1000ImgWithThreads(i,"C:\\Users\\Adrien\\Pictures\\average.jpg");
		    	printWriter.printf("%d;%f\n", i,1000000/temps1000Images);
		    	System.out.println(i + " Threads = " + 1000000/temps1000Images + " fps (average)");
		    }
		    printWriter.close();
		    
			fileWriter = new FileWriter("data_small_moyenne.csv");

		     printWriter = new PrintWriter(fileWriter);
		    for(int i = 1 ; i <= 8 ; ++i) {
		    	float temps1000Images = save1000ImgWithThreads(i,"C:\\Users\\Adrien\\Pictures\\small.jpg");
		    	printWriter.printf("%d;%f\n", i,1000000/temps1000Images);
		    	System.out.println(i + " Threads = " + 1000000/temps1000Images + " fps (small)");
		    }
		    printWriter.close();
		    
			fileWriter = new FileWriter("data_large_moyenne.csv");

		     printWriter = new PrintWriter(fileWriter);
		    for(int i = 1 ; i <= 8 ; ++i) {
		    	float temps1000Images = save1000ImgWithThreads(i,"C:\\Users\\Adrien\\Pictures\\large.jpg");
		    	printWriter.printf("%d;%f\n", i,1000000/temps1000Images);
		    	System.out.println(i + " Threads = " + 1000000/temps1000Images + " fps (large)");
		    }
		    printWriter.close();
		    
		    
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
	}
	/**
	 * Saves 200 images for each thread 
	 * @param nbThreads
	 * @param pathToImage
	 * @return fpsMedian
	 */
	public static double save200ImagesByThread(int nbThreads, String pathToImage) {
		Mat img = Imgcodecs.imread(pathToImage);
		
		IntHolder k = new IntHolder(0);
		
		//Contient les threads
		List<ThreadSave200Images> threads = new ArrayList<ThreadSave200Images>();
		//Liste des fps calculés toutes les x secondes
		List<Double> listeFPS = new ArrayList<Double>();
		//Tâche calcul du fps 
		TimerTask timerTask = new CalculFPS(k,2,listeFPS);
		
		//Lance le calcul du fps toutes les 
	    Timer timer = new Timer(true);
	    timer.scheduleAtFixedRate(timerTask, 2000, 2000);
		
	    //Lance tous les threads
		for(int i = 0 ; i < nbThreads ; ++i) {
			threads.add(new ThreadSave200Images(img,nbThreads,k));
			threads.get(i).start();
		}
		//Attente de la fin des threads
		//Pendant ce temps, listeFPS se remplit
		for(Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		timer.cancel();
		
		
		Double list2[] = new Double[listeFPS.size()];
		
		return mediane(listeFPS.toArray(list2));
	}
	
	public static double mediane(Double[] numArray) {
		Arrays.sort(numArray);
		double median;
		if (numArray.length % 2 == 0)
		    median = ((double)numArray[numArray.length/2] + (double)numArray[numArray.length/2 - 1])/2;
		else
		    median = (double) numArray[numArray.length/2];
		return median;
	}
	

	public static float save1000ImgWithThreads(int nbThreads, String pathToImage) {
		Mat img = Imgcodecs.imread(pathToImage);
		long before = System.currentTimeMillis();
		List<ThreadSaveImages1000Times> threads = new ArrayList<ThreadSaveImages1000Times>();
		for(int i = 0 ; i < nbThreads ; ++i) {
			
			threads.add(new ThreadSaveImages1000Times(img,nbThreads));
			threads.get(i).start();
		}
		
		for(Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long after = System.currentTimeMillis();
		
		return after - before;
	}

}
