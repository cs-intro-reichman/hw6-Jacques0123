import java.awt.Color;

/**
 * Demonstrates the morphing of an image into its grayscale version,
 * featured by Runigram.java.
 * The program receives two command-line arguments: a string representing the name
 * of the PPM file of a source image, and the number of morphing steps (an int).
 * For example, to morph thor.ppm into its grayscale version in 50 steps, use:
 * java Editor4 thor.ppm 50
 */
public class Editor4 {

    public static void main(String[] args) {
        // Validate command-line arguments
        if (args.length != 2) {
            System.err.println("Usage: java Editor4 <sourceFile> <steps>");
            return;
        }

        try {
            // Parse arguments
            String sourceFile = args[0];
            int steps = Integer.parseInt(args[1]);

            if (steps <= 0) {
                System.err.println("Error: Number of morphing steps must be a positive integer.");
                return;
            }

            // Read the source image
            Color[][] sourceImage = Runigram.read(sourceFile);

            if (sourceImage == null) {
                System.err.println("Error: Could not read the image file.");
                return;
            }

            // Create the grayscale version of the source image
            Color[][] grayscaleImage = Runigram.grayScaled(sourceImage);

            if (grayscaleImage == null) {
                System.err.println("Error: Failed to generate the grayscale image.");
                return;
            }

            // Create a canvas and perform the morphing
            Runigram.setCanvas(sourceImage);
            Runigram.morph(sourceImage, grayscaleImage, steps);

        } catch (NumberFormatException e) {
            System.err.println("Error: Number of steps must be an integer.");
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
