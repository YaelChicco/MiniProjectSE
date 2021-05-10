package primitives;

import java.util.List;
import java.util.Objects;
    public class Ray {
        /**
         * starting point of the ray
         */
        Point3D p0;
        /**
         * direction vector
         */
        Vector dir;

        /**
         * constructor that normalizes the given vector
         *
         * @param p0  point of the ray beginning
         * @param dir direction vector
         */
        public Ray(Point3D p0, Vector dir) {
            this.p0 = p0;
            this.dir = dir.normalized();
        }

        /**
         * getter of the ray beginning point
         *
         * @return p0
         */
        public Point3D getP0() {
            return p0;
        }

        /**
         * getter of the direction vector
         *
         * @return dir
         */
        public Vector getDir() {
            return dir;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Ray ray = (Ray) o;
            return p0.equals(ray.p0) && dir.equals(ray.dir);
        }

        @Override
        public String toString() {
            return "Ray{" +
                    "p0=" + p0 +
                    ", dir=" + dir +
                    '}';
        }

        public Point3D getPoint(double t1) {
            return p0.add(dir.scale(t1));
        }

        public Point3D getClosestPoint(List<Point3D> intersections) {
            Point3D result = null;
            if (intersections == null) {
                return null;
            }

            double distance = Double.MAX_VALUE;
            for (Point3D p : intersections) {
                double dist = p0.distance(p);
                if (dist < distance) {
                    distance = dist;
                    result = p;
                }
            }
            return result;
        }
    }
