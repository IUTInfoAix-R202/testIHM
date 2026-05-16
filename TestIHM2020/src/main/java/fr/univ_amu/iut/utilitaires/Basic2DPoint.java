package fr.univ_amu.iut.utilitaires;

import java.util.Objects;

public class Basic2DPoint {
  private double x, y;

  public Basic2DPoint(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Basic2DPoint that = (Basic2DPoint) o;
    return Double.compare(that.getX(), getX()) == 0 &&
             Double.compare(that.getY(), getY()) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getX(), getY());
  }

}
