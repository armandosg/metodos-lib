package com.chaimando.lib;

public class NumComplex {
  public double a;
  public double b;

  public NumComplex () {

  }

  public NumComplex(double a, double b) {
    this.a = a;
    this.b = b;
  }

  public String toString() {
    return "(" + a + ", " + b + ")";
  }

  public String toStringConj() {
    return "(" + a + ", +-" + Math.abs(b) + ")";
  }

}
