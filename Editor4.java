import java.awt.Color;

/** A library of image processing functions. */
public class Editor4 {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		//image = flippedHorizontally(tinypic);
		//image = flippedVertically(tinypic);

		//image = blend(tinypic, tinypic, 0.5);

		//image = scaled(tinypic, 3, 5);

		//System.out.println();
		//print(image);
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		// Reads the RGB values from the file into the image array. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.		
		
		for (int x = 0; x < numRows; x++) {
			for (int y = 0; y < numCols; y++) {
				image[x][y] = new Color(in.readInt(), in.readInt(), in.readInt());
			}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	public static void print(Color[][] image) {
		if (image == null) {
			return;
		}
		for (int x = 0; x < image.length; x++) {
			for (int y = 0; y < image[0].length; y++) {
				print(image[x][y]);
			}
			System.out.println();
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		Color[][] newImg = new Color[image.length][image[0].length];
		for (int x = 0; x < image.length; x++) {
			for (int y = 0; y < image[0].length; y++) {
				newImg[x][image[0].length - 1 - y] = image[x][y];
			}
		}
		return newImg;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
        @SuppressWarnings("ManualArrayToCollectionCopy")
	public static Color[][] flippedVertically(Color[][] image){
		Color[][] newImg = new Color[image.length][image[0].length];
		for (int x = 0; x < image.length; x++) {
			for (int y = 0; y < image[0].length; y++) {
				newImg[image.length - 1 - x][y] = image[x][y];
			}
		}
		return newImg;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
		// calc lum and floor it so its always between 0 and 255 nahm sayin
		int lum = (int) Math.floor(0.299 * pixel.getRed() + 0.587 * pixel.getGreen() + 0.114 * pixel.getBlue());

		Color newPixel = new Color(lum, lum, lum);

		return newPixel;
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		Color[][] newImg = new Color[image.length][image[0].length];
		for (int x = 0; x < image.length; x++) {
			for (int y = 0; y <  image[0].length; y++) {
				newImg[x][y] = luminance(image[x][y]);
			}
		}
		return newImg;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		int w0 = image[0].length;
		int h0 = image.length;
		int w = width;
		int h = height;
		Color[][] newImg = new Color[h][w];

		int i,j;		
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				j = (int) Math.floor(x * w0 / w);
				i = (int) Math.floor(y * h0 / h);
				newImg[y][x] = image[i][j];
			}
		}

		return newImg;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int r = (int) (alpha*c1.getRed() + ((1-alpha)*c2.getRed()));
		int g = (int) (alpha*c1.getGreen() + ((1-alpha)*c2.getGreen()));
		int b = (int) (alpha*c1.getBlue() + ((1-alpha)*c2.getBlue()));
		return new Color(r, g, b);
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		int w = image1[0].length;
		int h = image1.length;
		Color[][] newImg = new Color[h][w];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				newImg[y][x] = blend(image1[y][x], image2[y][x], alpha);
			}
		}
		return newImg;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		int w = source[0].length;
		int h = source.length;
		if (w != target[0].length || h != target.length) { // meyk shere de demenshen ess the seyme
			target = scaled(target, w, h);
		}

		display(source);
		double alpha;
		for (int step = 0; step <= n; step++) {
			alpha = (double)(n-step)/(double)n;
			source = blend(source, target, alpha);
			display(source);
			StdDraw.pause(1);
		} 
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}
