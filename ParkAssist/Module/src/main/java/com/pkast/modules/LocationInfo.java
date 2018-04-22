package com.pkast.modules;

public class LocationInfo {
    private String id;

    private String xiaoquName;

    private double locat_x_min;

    private double locat_x_max;

    private double locat_y_min;

    private double locat_y_max;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXiaoquName() {
        return xiaoquName;
    }

    public void setXiaoquName(String xiaoquName) {
        this.xiaoquName = xiaoquName;
    }

    public double getLocat_x_min() {
        return locat_x_min;
    }

    public void setLocat_x_min(double locat_x_min) {
        this.locat_x_min = locat_x_min;
    }

    public double getLocat_x_max() {
        return locat_x_max;
    }

    public void setLocat_x_max(double locat_x_max) {
        this.locat_x_max = locat_x_max;
    }

    public double getLocat_y_min() {
        return locat_y_min;
    }

    public void setLocat_y_min(double locat_y_min) {
        this.locat_y_min = locat_y_min;
    }

    public double getLocat_y_max() {
        return locat_y_max;
    }

    public void setLocat_y_max(double locat_y_max) {
        this.locat_y_max = locat_y_max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocationInfo)) return false;

        LocationInfo that = (LocationInfo) o;

        if (Double.compare(that.getLocat_x_min(), getLocat_x_min()) != 0) return false;
        if (Double.compare(that.getLocat_x_max(), getLocat_x_max()) != 0) return false;
        if (Double.compare(that.getLocat_y_min(), getLocat_y_min()) != 0) return false;
        if (Double.compare(that.getLocat_y_max(), getLocat_y_max()) != 0) return false;
        if (!getId().equals(that.getId())) return false;
        return getXiaoquName().equals(that.getXiaoquName());
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId().hashCode();
        result = 31 * result + getXiaoquName().hashCode();
        temp = Double.doubleToLongBits(getLocat_x_min());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getLocat_x_max());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getLocat_y_min());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getLocat_y_max());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
