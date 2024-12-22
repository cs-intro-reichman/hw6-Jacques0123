import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

    public static void main(String[] args) {
        // Example: Test reading and printing an image
        Color[][] tinypic = read("tinypic.ppm");
        if (tinypic != null) {
            print(tinypic);
        }
    }

    /** Reads a PPM file and returns a 2D array of Color objects. */
    public static Color[][] read(String fileName) {
        In in = new In(fileName);
        in.readString(); // Skip "P3"
        int numCols = in.readInt();
        int numRows = in.readInt();
        in.readInt(); // Skip max color value (255)

        Color[][] image = new Color[numRows][numCols];

        for (int x = 0; x < numRows; x++) {
            for (int y = 0; y < numCols; y++) {
                image[x][y] = new Color(in.readInt(), in.readInt(), in.readInt());
            }
        }
        return image;
    }

    /** Prints the RGB values of a given Color. */
    private static void print(Color c) {
        System.out.printf("(%3d, %3d, %3d)  ", c.getRed(), c.getGreen(), c.getBlue());
    }

    /** Prints the entire image as RGB values for debugging purposes. */
    public static void print(Color[][] image) {
        if (image == null) {
            System.err.println("Error: Image is null.");
            return;
        }
        for (Color[] row : image) {
            for (Color pixel : row) {
                print(pixel);
            }
            System.out.println();
        }
    }

    /** Flips an image horizontally. */
    public static Color[][] flippedHorizontally(Color[][] image) {
        int rows = image.length, cols = image[0].length;
        Color[][] flipped = new Color[rows][cols];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                flipped[x][cols - 1 - y] = image[x][y];
            }
        }
        return flipped;
    }

    /** Flips an image vertically. */
    public static Color[][] flippedVertically(Color[][] image) {
        int rows = image.length, cols = image[0].length;
        Color[][] flipped = new Color[rows][cols];
        for (int x = 0; x < rows; x++) {
            flipped[rows - 1 - x] = image[x];
        }
        return flipped;
    }

    /** Computes the luminance of a Color pixel and returns a grayscale Color. */
    private static Color luminance(Color pixel) {
        int lum = (int) (0.299 * pixel.getRed() + 0.587 * pixel.getGreen() + 0.114 * pixel.getBlue());
        return new Color(lum, lum, lum);
    }

    /** Converts an image to grayscale. */
    public static Color[][] grayScaled(Color[][] image) {
        int rows = image.length, cols = image[0].length;
        Color[][] grayImage = new Color[rows][cols];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                grayImage[x][y] = luminance(image[x][y]);
            }
        }
        return grayImage;
    }

    /** Scales an image to the specified width and height. */
    public static Color[][] scaled(Color[][] image, int width, int height) {
        int originalWidth = image[0].length;
        int originalHeight = image.length;
        Color[][] scaledImage = new Color[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int srcX = x * originalWidth / width;
                int srcY = y * originalHeight / height;
                scaledImage[y][x] = image[srcY][srcX];
            }
        }
        return scaledImage;
    }

    /** Blends two colors with the given alpha. */
    public static Color blend(Color c1, Color c2, double alpha) {
        int r = (int) (alpha * c1.getRed() + (1 - alpha) * c2.getRed());
        int g = (int) (alpha * c1.getGreen() + (1 - alpha) * c2.getGreen());
        int b = (int) (alpha * c1.getBlue() + (1 - alpha) * c2.getBlue());
        return new Color(r, g, b);
    }

    /** Blends two images with the given alpha. Assumes both images have the same dimensions. */
    public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
        int rows = image1.length, cols = image1[0].length;
        Color[][] blendedImage = new Color[rows][cols];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                blendedImage[x][y] = blend(image1[x][y], image2[x][y], alpha);
            }
        }
        return blendedImage;
    }

    /** Morphs the source image into the target image in n steps. */
    public static void morph(Color[][] source, Color[][] target, int n) {
        int rows = source.length, cols = source[0].length;
        if (rows != target.length || cols != target[0].length) {
            target = scaled(target, cols, rows); // Scale target to match source
        }

        for (int step = 0; step <= n; step++) {
            double alpha = 1.0 - (double) step / n;
            Color[][] intermediate = blend(source, target, alpha);
            display(intermediate);
            StdDraw.pause(100);
        }
    }

    /** Sets the canvas for displaying an image. */
    public static void setCanvas(Color[][] image) {
        int height = image.length, width = image[0].length;
        StdDraw.setCanvasSize(width, height);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.enableDoubleBuffering();
    }

    /** Displays an image on the current canvas. */
    public static void display(Color[][] image) {
        int height = image.length, width = image[0].length;
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                StdDraw.setPenColor(image[x][y].getRed(), image[x][y].getGreen(), image[x][y].getBlue());
                StdDraw.filledSquare(y + 0.5, height - x - 0.5, 0.5);
            }
        }
        StdDraw.show();
    }
}
