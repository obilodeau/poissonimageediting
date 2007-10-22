package test;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.PoissonPhotomontage;

/**
 * 
 * @author fproulx
 *
 */
public class PhotomontageTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Paths to all the images
		String srcImagePath = "";
		String maskImagePath = "";
		String destImagePath = "";
		
		try {
			// Load all the images
			BufferedImage srcImage = ImageIO.read(new File(srcImagePath));
			BufferedImage maskImage = ImageIO.read(new File(maskImagePath));
			BufferedImage destImage = ImageIO.read(new File(destImagePath));

			// Setup the Poisson solver
			PoissonPhotomontage photomontage = new PoissonPhotomontage(srcImage, maskImage, destImage, new Point(10, 10));
			// Do the heavy lifting
			BufferedImage output = photomontage.createPhotomontage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
