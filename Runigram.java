import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

    public static void main(String[] args) {
        // Tests the reading and printing of an image:    
        Color[][] tinypic = read("tinypic.ppm");
        print(tinypic);

        // Creates an image which will be the result of various 
        // image processing operations:
        Color[][] image;

        // Tests the horizontal flipping of an image:
        image = flippedHorizontally(tinypic);
        System.out.println();
        print(image);
    }

    public static Color[][] read(String fileName) {
        In in = new In(fileName);
        // Reads the file header, ignoring the first and third lines.
        in.readString(); // "P3"
        int numCols = in.readInt();
        int numRows = in.readInt();
        in.readInt(); // Maximum color value (255)
        
        // Create the image array
        Color[][] image = new Color[numRows][numCols];
        
        // Read the RGB values from the file and fill the array
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int r = in.readInt();
                int g = in.readInt();
                int b = in.readInt();
                image[i][j] = new Color(r, g, b);
            }
        }
        return image;
    }

    private static void print(Color[][] image) {
        for (Color[] row : image) {
            for (Color pixel : row) {
                print(pixel);
            }
            System.out.println();
        }
    }

    public static Color[][] flippedHorizontally(Color[][] image) {
        int rows = image.length;
        int cols = image[0].length;
        Color[][] flipped = new Color[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                flipped[i][j] = image[i][cols - j - 1];
            }
        }
        return flipped;
    }

    public static Color[][] flippedVertically(Color[][] image) {
        int rows = image.length;
        int cols = image[0].length;
        Color[][] flipped = new Color[rows][cols];
        for (int i = 0; i < rows; i++) {
            flipped[i] = image[rows - i - 1];
        }
        return flipped;
    }

    private static Color luminance(Color pixel) {
        int lum = (int) (0.299 * pixel.getRed() + 0.587 * pixel.getGreen() + 0.114 * pixel.getBlue());
        return new Color(lum, lum, lum);
    }

    public static Color[][] grayScaled(Color[][] image) {
        int rows = image.length;
        int cols = image[0].length;
        Color[][] grayImage = new Color[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grayImage[i][j] = luminance(image[i][j]);
            }
        }
        return grayImage;
    }

    public static Color[][] scaled(Color[][] image, int width, int height) {
        int originalHeight = image.length;
        int originalWidth = image[0].length;
        Color[][] scaledImage = new Color[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int srcRow = (i * originalHeight) / height;
                int srcCol = (j * originalWidth) / width;
                scaledImage[i][j] = image[srcRow][srcCol];
            }
        }
        return scaledImage;
    }

    public static Color blend(Color c1, Color c2, double alpha) {
        int r = (int) (alpha * c1.getRed() + (1 - alpha) * c2.getRed());
        int g = (int) (alpha * c1.getGreen() + (1 - alpha) * c2.getGreen());
        int b = (int) (alpha * c1.getBlue() + (1 - alpha) * c2.getBlue());
        return new Color(r, g, b);
    }

    public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
        int rows = image1.length;
        int cols = image1[0].length;
        Color[][] blendedImage = new Color[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                blendedImage[i][j] = blend(image1[i][j], image2[i][j], alpha);
            }
        }
        return blendedImage;
    }

    public static void morph(Color[][] source, Color[][] target, int n) {
        int rows = source.length;
        int cols = source[0].length;

        Color[][] scaledTarget = scaled(target, cols, rows);

        for (int step = 0; step <= n; step++) {
            double alpha = (double) (n - step) / n;
            Color[][] intermediate = blend(source, scaledTarget, alpha);
            display(intermediate);
            StdDraw.pause(500);
        }
    }
}
