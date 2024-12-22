import java.awt.Color;

/**
 * Demonstrates the scaling (resizing) operation featured by Runigram.java. 
 * The program receives three command-line arguments: a string representing the name
 * of the PPM file of a source image, and two integers that specify the width and the
 * height of the scaled, output image. For example, to scale/resize ironman.ppm to a width
 * of 100 pixels and a height of 900 pixels, use: java Editor2 ironman.ppm 100 900
 */
public class Editor2 {

    public static void main(String[] args) {
        // Validate command-line arguments
        if (args.length != 3) {
            System.err.println("Usage: java Editor2 <fileName> <width> <height>");
            return;
        }

        try {
            // Parse input arguments
            String fileName = args[0];
            int width = Integer.parseInt(args[1]);
            int height = Integer.parseInt(args[2]);

            if (width <= 0 || height <= 0) {
                System.err.println("Error: Width and height must be positive integers.");
                return;
            }

            // Read the input image
            Color[][] imageIn = Runigram.read(fileName);
            if (imageIn == null) {
                System.err.println("Error: Could not read the image file.");
                return;
            }

            // Apply the scaling operation
            Color[][] imageOut = Runigram.scaled(imageIn, width, height);

            if (imageOut == null) {
                System.err.println("Error: Image scaling failed.");
                return;
            }

            // Display the original image on its canvas
            Runigram.setCanvas(imageIn);
            Runigram.display(imageIn);
            StdDraw.pause(3000);

            // Display the scaled image on a new canvas
            Runigram.setCanvas(imageOut);
            Runigram.display(imageOut);

        } catch (NumberFormatException e) {
            System.err.println("Error: Width and height must be integers.");
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
