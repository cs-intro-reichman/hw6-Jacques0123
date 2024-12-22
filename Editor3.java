import java.awt.Color;

/**
 * Demonstrates the morphing operation featured by Runigram.java. 
 * The program receives three command-line arguments: a string representing the name
 * of the PPM file of a source image, a string representing the name of the PPM file
 * of a target image, and the number of morphing steps (an int). 
 * For example, to morph the cake into ironman in 50 steps, use:
 * java Editor3 cake.ppm ironman.ppm 50
 * Note: There is no need to scale the target image to the size of the source
 * image, since Runigram.morph performs this action.
 */
public class Editor3 {

    public static void main(String[] args) {
        // Validate command-line arguments
        if (args.length != 3) {
            System.err.println("Usage: java Editor3 <sourceFile> <targetFile> <steps>");
            return;
        }

        try {
            // Parse arguments
            String source = args[0];
            String target = args[1];
            int n = Integer.parseInt(args[2]);

            if (n <= 0) {
                System.err.println("Error: Number of morphing steps must be a positive integer.");
                return;
            }

            // Read source and target images
            Color[][] sourceImage = Runigram.read(source);
            Color[][] targetImage = Runigram.read(target);

            if (sourceImage == null || targetImage == null) {
                System.err.println("Error: Could not read one or both image files.");
                return;
            }

            // Create a canvas and perform morphing
            Runigram.setCanvas(sourceImage);
            Runigram.morph(sourceImage, targetImage, n);

        } catch (NumberFormatException e) {
            System.err.println("Error: Number of steps must be an integer.");
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
