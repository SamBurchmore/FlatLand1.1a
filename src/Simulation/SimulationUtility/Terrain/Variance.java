package Simulation.SimulationUtility.Terrain;

public class Variance {
    private int upper;
    private int lower;
    private int changeRate;

    public Variance(int upper, int lower, int changeRate) {
        this.upper = upper;
        this.lower = lower;
        this.changeRate = changeRate;
    }

    public int getUpper() {
        return upper;
    }

    public void setUpper(int upper) {
        this.upper = upper;
    }

    public int getLower() {
        return lower;
    }

    public void setLower(int lower) {
        this.lower = lower;
    }

    public int getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(int changeRate) {
        this.changeRate = changeRate;
    }
}
