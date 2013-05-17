package Level;

import java.util.Random;

public class Noise {
    /** Source of entropy */
    private Random rand_;

    /** Amount of roughness */
    float roughness_;

    /** Plasma fractal grid */
    private float[][] grid_;


    /** Generate a noise source based upon the midpoint displacement fractal.
     * 
     * @param rand The random number generator
     * @param roughness a roughness parameter
     * @param width the width of the grid
     * @param height the height of the grid
     */
    public Noise(Random rand, float roughness, int width, int height) {
        roughness_ = roughness / width;
        setGrid_(new float[width][height]);
        rand_ = (rand == null) ? new Random() : rand;
    }


    public void initialise() {
        int xh = getGrid_().length - 1;
        int yh = getGrid_()[0].length - 1;

        // set the corner points
        getGrid_()[0][0] = rand_.nextFloat() - 0.5f;
        getGrid_()[0][yh] = rand_.nextFloat() - 0.5f;
        getGrid_()[xh][0] = rand_.nextFloat() - 0.5f;
        getGrid_()[xh][yh] = rand_.nextFloat() - 0.5f;

        // generate the fractal
        generate(0, 0, xh, yh);
    }


    // Add a suitable amount of random displacement to a point
    private float roughen(float v, int l, int h) {
        return v + roughness_ * (float) (rand_.nextGaussian() * (h - l));
    }


    // generate the fractal
    private void generate(int xl, int yl, int xh, int yh) {
        int xm = (xl + xh) / 2;
        int ym = (yl + yh) / 2;
        if ((xl == xm) && (yl == ym)) return;

        getGrid_()[xm][yl] = 0.5f * (getGrid_()[xl][yl] + getGrid_()[xh][yl]);
        getGrid_()[xm][yh] = 0.5f * (getGrid_()[xl][yh] + getGrid_()[xh][yh]);
        getGrid_()[xl][ym] = 0.5f * (getGrid_()[xl][yl] + getGrid_()[xl][yh]);
        getGrid_()[xh][ym] = 0.5f * (getGrid_()[xh][yl] + getGrid_()[xh][yh]);

        float v = roughen(0.5f * (getGrid_()[xm][yl] + getGrid_()[xm][yh]), xl + yl, yh
                + xh);
        getGrid_()[xm][ym] = v;
        getGrid_()[xm][yl] = roughen(getGrid_()[xm][yl], xl, xh);
        getGrid_()[xm][yh] = roughen(getGrid_()[xm][yh], xl, xh);
        getGrid_()[xl][ym] = roughen(getGrid_()[xl][ym], yl, yh);
        getGrid_()[xh][ym] = roughen(getGrid_()[xh][ym], yl, yh);

        generate(xl, yl, xm, ym);
        generate(xm, yl, xh, ym);
        generate(xl, ym, xm, yh);
        generate(xm, ym, xh, yh);
    }


    /**
     * Dump out as a CSV
     */
    public void printAsCSV() {
        for(int i = 0;i < getGrid_().length;i++) {
            for(int j = 0;j < getGrid_()[0].length;j++) {
                System.out.print(getGrid_()[i][j]);
                System.out.print(",");
            }
            System.out.println();
        }
    }


    /**
     * Convert to a Boolean array
     * @return the boolean array
     */
    public boolean[][] toBooleans() {
        int w = getGrid_().length;
        int h = getGrid_()[0].length;
        boolean[][] ret = new boolean[w][h];
        for(int i = 0;i < w;i++) {
            for(int j = 0;j < h;j++) {
                ret[i][j] = getGrid_()[i][j] < 0;
            }
        }
        return ret;
    }
    


	public float[][] getGrid_() {
		return grid_;
	}


	public void setGrid_(float[][] grid_) {
		this.grid_ = grid_;
	}
}
