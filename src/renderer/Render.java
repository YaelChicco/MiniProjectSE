package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;

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
    private boolean isMultyRay = false;
    private Color[][] colors;

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

    public Render setMultyRay() {
        isMultyRay = true;
        return this;
    }

    /**
     * creates a picture with focus
     */
    public void renderFocusImage() {
        //one of the parameters is null
        if (_imageWriter == null)
            throw new MissingResourceException("_imageWriter is null", "Render", "ImageWriter");
        if (_camera == null)
            throw new MissingResourceException("_camera is null", "Render", "Camera");
        if (_rayTracer == null)
            throw new MissingResourceException("_rayTracer is null", "Render", "RayTracerBase");

        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                List<Ray> rays = _camera.constructRaysThroughPixel(nX, nY, j, i);
                Color colorAverage = Color.BLACK;
                for (Ray ray : rays)
                    colorAverage = colorAverage.add(_rayTracer.traceRay(ray));
                _imageWriter.writePixel(j, i, colorAverage.reduce(rays.size()));
            }
        }
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
        colors = new Color[_camera.getApertureN()][_camera.getApertureN()];
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
                    if (isMultyRay)
                        castRays(nX, nY, pixel.col, pixel.row);
                    else
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
                    if (isMultyRay)
                        castRays(nX, nY, j, i);
                    else
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
    private void castRays2(int nX, int nY, int col, int row) {
        Point3D focusPoint = _camera.getFocusPoint(nX, nY, col, row);
        Color color = castRay2Inner(focusPoint, 0, 0, _camera.getApertureN(), _camera.getApertureN());
        _imageWriter.writePixel(col, row, color);
    }

//    Color castRay2Inner(int nX, int nY, int col, int row, List<Integer> indexes) {
//        List<Ray> rays = _camera.constructRaysThroughPixel2(nX, nY, col, row);
//        Color colorRay = _rayTracer.traceRay(rays.get(0));
//        Color finalColor = Color.BLACK;
//       // previousCenter=_camera.calcCenter(index,iter,previousCenter);
//
//        /*if (iter > _camera.getApertureN() / 2) {
//            for (Ray r : rays)
//                finalColor = finalColor.add(_rayTracer.traceRay(r));
//            return finalColor.reduce(4);
//        }*/
//
//        boolean flag=true;
//        Color aparturePixel=Color.BLACK;
//        for (Ray ray : rays) {
//            if (!_rayTracer.traceRay(ray).equals(colorRay)) {
//                for(int i=1; i<=4; i++) {
//                    aparturePixel = castRay2Inner(nX, nY, col, row);
//                    //colors[]
//                    finalColor = finalColor.add(aparturePixel);
//                }
//                flag=false;
//                break;
//            }
//        }
//        if(flag)
//            return colorRay;
//        return finalColor.reduce(4);
//    }
//}

    Color castRay2Inner(Point3D focusPoint, int x1, int y1, int x2, int y2) {
        Color current=averageColor4(focusPoint, x1, y1, x2, y2);
        if (current!=null)
            return current;

        if (x1 + 1 == x2 && y1 + 1 == y2){
            Color finalColor=colors[x1][y1].add(colors[x2][y1]).add(colors[x2][y2]).add(colors[x1][y2]);
            return finalColor.reduce(4);
        }

        int distance=(x2-x1)/2;
        Color finalColor=castRay2Inner(focusPoint,x1, y1,x1+distance,y1+distance);
        finalColor=finalColor.add(castRay2Inner(focusPoint,x1+distance, y1, x2, y1+distance));
        finalColor=finalColor.add(castRay2Inner(focusPoint,x1+distance, y1+distance, x2, y2));
        finalColor=finalColor.add(castRay2Inner(focusPoint,x1, y1+distance, x1+distance, y2));
        return finalColor.reduce(4);
    }

    private Color averageColor4(Point3D focusPoint, int x1, int y1, int x2, int y2) {
        int xRight = x2;
        int yRight = y1;

        int xUp = x1;
        int yUp = y2;

        Color downLeft = colors[x1][y1];
        if (downLeft == null) {
            Point3D aperturePoint = _camera.getPointByMat(x1, y1);
            downLeft = _rayTracer.traceRay(new Ray(aperturePoint, focusPoint.subtract(aperturePoint)));
            colors[x1][y1] = downLeft;
        }

        Color downRight = colors[xRight][yRight];
        if (downRight == null) {
            Point3D aperturePoint = _camera.getPointByMat(xRight, yRight);
            downRight = _rayTracer.traceRay(new Ray(aperturePoint, focusPoint.subtract(aperturePoint)));
            colors[xRight][yRight] = downRight;
        }

        Color upRight = colors[x2][y2];
        if (upRight == null) {
            Point3D aperturePoint = _camera.getPointByMat(x2, y2);
            upRight = _rayTracer.traceRay(new Ray(aperturePoint, focusPoint.subtract(aperturePoint)));
            colors[x2][y2] = upRight;
        }

        Color upLeft = colors[xUp][yUp];
        if (upLeft == null) {
            Point3D aperturePoint = _camera.getPointByMat(xUp, yUp);
            upLeft = _rayTracer.traceRay(new Ray(aperturePoint, focusPoint.subtract(aperturePoint)));
            colors[xUp][yUp] = upLeft;
        }

        if (!downLeft.equals(downRight) || !downLeft.equals(upRight) || !downLeft.equals(upLeft))
            return null;
        return downLeft;
    }
}