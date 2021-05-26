package primitives;

/**
 * class for the geometry material
 */
public class Material {

    /**
     * for diffuse
     */
    public double kD=0;
    /**
     * for specular
     */
    public double kS=0;
    /**
     * for shininess
     */
    public int nShininess=0;
    public double kR=0;
    public double kT=0;

    /**
     * setter of the diffuse coefficient
     * @param kD kD
     * @return the object itself
     */
    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    /**
     * setter of the specular coefficient
     * @param kS kS
     * @return the object itself
     */
    public Material setKs(double kS) {
        this.kS = kS;
        return this;
    }

    /**
     * setter of the shininess coefficient
     * @param nShininess nShininess
     * @return the object itself
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    public Material setkR(double kR) {
        this.kR = kR;
        return this;
    }

    public Material setkT(double kT) {
        this.kT = kT;
        return this;
    }
}
