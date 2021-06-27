package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.MissingResourceException;

/**
 * Renderer class is responsible for generating pixel color map from a graphic
 * scene, using ImageWriter class
 */
public class Render {

    private static final String RESOURCE_ERROR = "Renderer resource not set";
    private static final String RENDER_CLASS = "Render";
    private static final String IMAGE_WRITER_COMPONENT = "Image writer";
    private static final String CAMERA_COMPONENT = "Camera";
    private static final String RAY_TRACER_COMPONENT = "Ray tracer";

    private int threadsCount = 0;
    private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean print = false; // printing progress percentage

    /**
     * creates the image
     */
    private ImageWriter _imageWriter;
    /**
     * camera details
     */
    private Camera _camera;
    /**
     * ray tracer
     */
    private RayTracerBase _rayTracer;
    /**
     * boolean field for depth of field
     */
    private boolean isMultyRay = false;
    /**
     * boolean field for adaptive super sampling
     */
    private boolean isAdaptiveSS = false;

    /**
     * Set multi-threading <br>
     * - if the parameter is 0 - number of cores less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)
            this.threadsCount = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            this.threadsCount = cores <= 2 ? 1 : cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        print = true;
        return this;
    }

    public Render setMultyRay(boolean isAdaptiveSS) {
        isMultyRay = true;
        this.isAdaptiveSS = isAdaptiveSS;
        return this;
    }

    /**
     * Pixel is an internal helper class whose objects are associated with a Render
     * object that they are generated in scope of. It is used for multithreading in
     * the Renderer and for follow up its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each
     * thread.
     *
     * @author Dan
     */

    private class Pixel {
        private long maxRows = 0;
        private long maxCols = 0;
        private long pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long counter = 0;
        private int percents = 0;
        private long nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            this.maxRows = maxRows;
            this.maxCols = maxCols;
            this.pixels = (long) maxRows * maxCols;
            this.nextCounter = this.pixels / 100;
            if (Render.this.print)
                System.out.printf("\r %02d%%", this.percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object
         * - this function is critical section for all the threads, and main Pixel
         * object data is the shared data of this critical section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print,
         * if it is -1 - the task is finished, any other value - the progress
         * percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++this.counter;
            if (col < this.maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            ++row;
            if (row < this.maxRows) {
                col = 0;
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percent = nextP(target);
            if (Render.this.print && percent > 0)
                synchronized (this) {
                    notifyAll();
                }
            if (percent >= 0)
                return true;
            if (Render.this.print)
                synchronized (this) {
                    notifyAll();
                }
            return false;
        }

        /**
         * Debug print of progress percentage - must be run from the main thread
         */
        public void print() {
            if (Render.this.print)
                while (this.percents < 100)
                    try {
                        synchronized (this) {
                            wait();
                        }
                        System.out.printf("\r %02d%%", this.percents);
                        System.out.flush();
                    } catch (Exception e) {
                    }
        }
    }

    /**
     * Camera setter
     *
     * @param camera to set
     * @return renderer itself - for chaining
     */
    public Render setCamera(Camera camera) {
        this._camera = camera;
        return this;
    }

    /**
     * Image writer setter
     *
     * @param imgWriter the image writer to set
     * @return renderer itself - for chaining
     */
    public Render setImageWriter(ImageWriter imgWriter) {
        this._imageWriter = imgWriter;
        return this;
    }

    /**
     * Ray tracer setter
     *
     * @param tracer to use
     * @return renderer itself - for chaining
     */
    public Render setRayTracer(RayTracerBase tracer) {
        this._rayTracer = tracer;
        return this;
    }

    /**
     * Produce a rendered image file
     */
    public void writeToImage() {
        if (_imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);
        _imageWriter.writeToImage();
    }

    /**
     * Cast ray from camera in order to color a pixel
     *
     * @param nX  resolution on X axis (number of pixels in row)
     * @param nY  resolution on Y axis (number of pixels in column)
     * @param col pixel's column number (pixel index in row)
     * @param row pixel's row number (pixel index in column)
     */
    private void castRay(int nX, int nY, int col, int row) {
        Ray ray = _camera.constructRayThroughPixel(nX, nY, col, row);
        Color colorAverage = _rayTracer.traceRay(ray);
        _imageWriter.writePixel(col, row, colorAverage);
    }

    /**
     * Cast ray from camera in order to color a pixel
     *
     * @param nX  resolution on X axis (number of pixels in row)
     * @param nY  resolution on Y axis (number of pixels in column)
     * @param col pixel's column number (pixel index in row)
     * @param row pixel's row number (pixel index in column)
     */
    private void castRays(int nX, int nY, int col, int row) {
        List<Ray> rays = _camera.constructRaysThroughPixel(nX, nY, col, row);
        Color colorAverage = Color.BLACK;
        for (Ray ray : rays)
            colorAverage = colorAverage.add(_rayTracer.traceRay(ray));
        _imageWriter.writePixel(col, row, colorAverage.reduce(rays.size()));
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object - with multi-threading
     */
    private void renderImageThreaded() {
        final int nX = _imageWriter.getNx();
        final int nY = _imageWriter.getNy();
        final Pixel thePixel = new Pixel(nY, nX);
        // Generate threads
        Thread[] threads = new Thread[threadsCount];
        for (int i = threadsCount - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel))
                    if (isMultyRay) {
                        if (!isAdaptiveSS)
                            castRays(nX, nY, pixel.col, pixel.row);
                        else
                            castRaysASS(nX, nY, pixel.col, pixel.row);
                    } else
                        castRay(nX, nY, pixel.col, pixel.row);
            });
        }
        // Start threads
        for (Thread thread : threads)
            thread.start();

        // Print percents on the console
        thePixel.print();

        // Ensure all threads have finished
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }

        if (print)
            System.out.print("\r100%");
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */
    public void renderImage() {
        if (_imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);
        if (_camera == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, CAMERA_COMPONENT);
        if (_rayTracer == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, RAY_TRACER_COMPONENT);

        final int nX = _imageWriter.getNx();
        final int nY = _imageWriter.getNy();
        if (threadsCount == 0)
            for (int i = 0; i < nY; ++i)
                for (int j = 0; j < nX; ++j)
                    if (isMultyRay) {
                        if (!isAdaptiveSS)
                            castRays(nX, nY, j, i);
                        else
                            castRaysASS(nX, nY, j, i);
                    } else
                        castRay(nX, nY, j, i);
        else
            renderImageThreaded();
    }

    /**
     * Create a grid [over the picture] in the pixel color map. given the grid's
     * step and color.
     *
     * @param step  grid's step
     * @param color grid's color
     */
    public void printGrid(int step, Color color) {
        if (_imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();

        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j)
                if (j % step == 0 || i % step == 0)
                    _imageWriter.writePixel(j, i, color);
    }

    /**
     * Cast ray from camera in order to color a pixel
     *
     * @param nX  resolution on X axis (number of pixels in row)
     * @param nY  resolution on Y axis (number of pixels in column)
     * @param col pixel's column number (pixel index in row)
     * @param row pixel's row number (pixel index in column)
     */
    private void castRaysASS(int nX, int nY, int col, int row) {
        Point3D focusPoint = _camera.getFocusPoint(nX, nY, col, row);
        int n = _camera.getApertureN();
        Color[][] colors = new Color[n][n];
        Color finalColor = castRaysASSInner(focusPoint,colors, 0, 0, n - 1, n - 1);
        _imageWriter.writePixel(col, row, finalColor);
    }

    /**
     * Calculates the final color of the pixel
     * @param focusPoint- the point on the focal plane that the rays pass through
     * @param colors-the matrix of the ray's colors
     * @param x1-index of the bottom left square of the aperture
     * @param y1-index of the bottom left square of the aperture
     * @param x2-index of the top right square of the aperture
     * @param y2-index of the top right square of the aperture
     * @return the color of the pixel
     */
    private Color castRaysASSInner(Point3D focusPoint, Color[][] colors, int x1, int y1, int x2, int y2) {
        Color current = averageColor4(focusPoint, colors, x1, y1, x2, y2);
        if (current != null)
            return current;

        if (x1 + 1 == x2 && y1 + 1 == y2) {
            Color finalColor = colors[x1][y1].add(colors[x2][y1]).add(colors[x2][y2]).add(colors[x1][y2]);
            return finalColor.reduce(4);
        }

        int distance = (x2 - x1) / 2;

        Color finalColor = castRaysASSInner(focusPoint, colors, x1, y1, x1 + distance, y1 + distance);
        finalColor = finalColor.add(castRaysASSInner(focusPoint, colors, x2 - distance, y1, x2, y1 + distance));
        finalColor = finalColor.add(castRaysASSInner(focusPoint, colors, x2 - distance, y2 - distance, x2, y2));
        finalColor = finalColor.add(castRaysASSInner(focusPoint, colors, x1, y2 - distance, x1 + distance, y2));
        return finalColor.reduce(4);
    }

    /**
     * Calculates the colors of 4 rays and saves each one in a matrix
     * @param focusPoint- the point on the focal plane that the rays pass through
     * @param colors-the matrix of the ray's colors
     * @param x1-index of the bottom left square of the aperture
     * @param y1-index of the bottom left square of the aperture
     * @param x2-index of the top right square of the aperture
     * @param y2-index of the top right square of the aperture
     * @return the calculate color of the 4 rays
     */
    private Color averageColor4(Point3D focusPoint, Color[][] colors, int x1, int y1, int x2, int y2) {
        int xRight = x2;
        int yRight = y1;

        int xUp = x1;
        int yUp = y2;

        Point3D apertureDL = _camera.getPointByMat(x1, y1);
        Point3D apertureUR = _camera.getPointByMat(x2, y2);
        Point3D apertureUL = _camera.getPointByMat(xUp, yUp);
        Point3D apertureDR = _camera.getPointByMat(xRight, yRight);

        Color downLeft = colors[x1][y1];
        if (downLeft == null) {
            downLeft = _rayTracer.traceRay(new Ray(apertureDL, focusPoint.subtract(apertureDL)));
            colors[x1][y1] = downLeft;
        }

        Color downRight = colors[xRight][yRight];
        if (downRight == null) {
            downRight = _rayTracer.traceRay(new Ray(apertureDR, focusPoint.subtract(apertureDR)));
            colors[xRight][yRight] = downRight;
        }

        Color upRight = colors[x2][y2];
        if (upRight == null) {
            upRight = _rayTracer.traceRay(new Ray(apertureUR, focusPoint.subtract(apertureUR)));
            colors[x2][y2] = upRight;
        }

        Color upLeft = colors[xUp][yUp];
        if (upLeft == null) {
            upLeft = _rayTracer.traceRay(new Ray(apertureUL, focusPoint.subtract(apertureUL)));
            colors[xUp][yUp] = upLeft;
        }

        Vector right = apertureDR.subtract(apertureDL).scale(0.5);
        Vector up = apertureUL.subtract(apertureDL).scale(0.5);
        Point3D center = apertureDL.add(right).add(up);
        Color centerColor = _rayTracer.traceRay(new Ray(center, focusPoint.subtract(center)));

        List<Color> colorList = List.of(downLeft, downRight, upLeft, upRight, centerColor);
        for (Color c1 : colorList) {
            for (Color c2 : colorList) {
                if (!c1.equals(c2))
                    return null;
            }
        }

        return downLeft.add(upLeft, upRight, downRight, centerColor).reduce(5);
    }
}