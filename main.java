package optimisation;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter("data_small.csv");

		    PrintWriter printWriter = new PrintWriter(fileWriter);
		    for(int i = 1 ; i <= 8 ; ++i)
		    	printWriter.printf("%d;%f\n", i, save1000ImgWithThreads(i,"C:\\Users\\Adrien\\Pictures\\small.jpg"));
		    printWriter.close();
		    
			fileWriter = new FileWriter("data_average.csv");

		    printWriter = new PrintWriter(fileWriter);
		    for(int i = 1 ; i <= 8 ; ++i)
		    	printWriter.printf("%d;%f\n", i, save1000ImgWithThreads(i,"C:\\Users\\Adrien\\Pictures\\average.jpg"));
		    printWriter.close();
		    
			fileWriter = new FileWriter("data_large.csv");

		    printWriter = new PrintWriter(fileWriter);
		    for(int i = 1 ; i <= 8 ; ++i)
		    	printWriter.printf("%d;%f\n", i, save1000ImgWithThreads(i,"C:\\Users\\Adrien\\Pictures\\large.jpg"));
		    printWriter.close();
		    
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
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
