import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class NBody {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws FileNotFoundException {

		double big_t = Double.parseDouble(args[0]);
		double delta_t = Double.parseDouble(args[1]);
		double Gconst = 6.67408e-11;
		String resourceFolder = "resources/";
		String fileName = resourceFolder + args[2];
		FileInputStream fileInputStream = new FileInputStream(fileName);
		System.setIn(fileInputStream);

		// Use StdIn to read from the file.
		int numBodies = StdIn.readInt();
		double universeRadius = StdIn.readDouble();

		// Print out the values read in (remove all this from your final version)
		StdOut.println("big_t          = " + big_t);
		StdOut.println("delta_t        = " + delta_t);
		StdOut.println("numBodies      = " + numBodies);
		StdOut.println("universeRadius = " + universeRadius);

		double[] xPos = new double[numBodies];
		double[] yPos = new double[numBodies];
		double[] xVel = new double[numBodies];
		double[] yVel = new double[numBodies];
		double[] mass = new double[numBodies];
		String[] image = new String[numBodies];
    //create a for loop to read the body values into an array from the resource file you chose
		for (int i = 0; i < numBodies; i++) { 
			xPos[i] = StdIn.readDouble();
			yPos[i] = StdIn.readDouble();
			xVel[i] = StdIn.readDouble();
			yVel[i] = StdIn.readDouble();
			mass[i] = StdIn.readDouble();
			image[i] = StdIn.readString();

		}
		StdAudio.play("resources/2001.wav");  // plays the music
		StdDraw.setXscale(-universeRadius, universeRadius);
		StdDraw.setYscale(-universeRadius, universeRadius);


		double time_lapsed = 0;//  variable for time accumulation
		double[] fx = new double[numBodies];
		double[] fy = new double[numBodies];

		while (time_lapsed < big_t) {// making sure the iteration goes with in the time frame set
			//creating a for loop that resets the force values after one iteration
			for (int i = 0; i < numBodies; i++) {
				fx[i] = 0;
				fy[i] = 0;
			}
			
			for (int i = 0; i < numBodies; i++) {// start for loop 1
				for (int j = 0; j < numBodies; j++) {// Start for loop inner 2
					if (i != j) {// start if loop that makes sure to not calculate the force exerted on the body itself
						double deltaX = xPos[j] - xPos[i];
						double deltaY = yPos[j] - yPos[i];
					
						double radius = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
						
                        // using the acquired values to calculate the gravitational force between the variables
						double Gforce = (mass[i] * mass[j] * (Gconst)) / (radius * radius);
						//using the general force to divide the force into x and Y components
						double forcex = Gforce * (deltaX / radius);
						double forcey = Gforce * (deltaY / radius);
						fx[i] = fx[i] + forcex;
						fy[i] = fy[i] + forcey;

					} // end if loop
				} // end for loop2 inner
				StdDraw.picture(0, 0, "resources/starfield.jpg");
				
			} // end for loop 1
				
			for (int i = 0; i < numBodies; i++) {//a for loop that calculates the position from the force arrays and draws them
				double aX = fx[i] / mass[i];
				double aY = fy[i] / mass[i];
				xVel[i] += (aX * delta_t);
				yVel[i] += (aY * delta_t);

				xPos[i] += (xVel[i] * (delta_t));
				yPos[i] += (yVel[i] * (delta_t));
				

				StdDraw.picture(xPos[i], yPos[i], "resources/" + image[i]);
				
			}
			StdDraw.show(5);// to stop the glitching
			
			time_lapsed = time_lapsed + delta_t;// accumulating the time
		

		} // end while loop
		
		//Print the final new values after the program has been run
	      System.out.println();
	      System.out.println(numBodies);
	      System.out.println(universeRadius);

		for(int i=0; i<numBodies; i++) {
	     System.out.println(xPos[i]+ " "+yPos[i]+ " "+ xVel[i]+"  "+yVel[i]+ " "+ mass[i]+ " "+ image[i]);
	     
		}
	}
}
