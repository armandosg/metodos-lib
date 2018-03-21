/*
* Machine Epsilon is the smallest number of EPS (epsilon)
* such that 1 + EPS not equal to 1.
*
* Machine Epsilon is a machine-dependent floating point value that provides an upper bound
* on relative error due to rounding in floating point arithmetic.
*
* Mathematically, for each floating point type, it is equivalent to the difference between 1.0
* and the smallest representable value that is greater than 1.0.
*/

class MachineEpsilon {
  static void calcEpsilon(double EPS) {
    double prevEpsilon = 0.0;
    while ((1 + EPS) != 1) {
      prevEpsilon = EPS;
      EPS /= 2;
    }
    System.out.println("Machine Epsilon is: " + prevEpsilon);
  }
  public static void main(String[] args) {
    calcEpsilon(0.5);
    System.out.println(Math.ulp(0.5));
  }
}
