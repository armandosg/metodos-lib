package chaimando.metodos;

import java.util.ArrayList;

import static java.lang.Math.*;

public class Util {
  public static double factorial (double num) {
    double factorial = 1;
    for (double i = num; i > 1; i--) {
      factorial *= i;
    }
    return factorial;
  }

  public static double[] nFactorial (double num) {
    double factoriales[] = new double[(int)num];
    for (double i = num; i >= 0; i--) {
      factoriales[(int)i] = factorial(i);
    }
    return factoriales;
  }

  public static double horner (ArrayList<Double> polinomio, double x, double c) {
    int grado = polinomio.size() - 1;
    double suma = polinomio.get(grado);
    for (int i = grado - 1; i >= 0; i--)
      suma = ((x - c) * suma) + polinomio.get(i);
    return suma;
  }

  public static double falsaPosicion (ArrayList<Double> polinomio, double c, double tolerancia) {
    double f0, ff, fr, error;
    double x0 = 3, xf = 3.5, xr = 0, xra;
    do {
      xra = xr;
      f0 = horner(polinomio, x0, c);
      ff = horner(polinomio, xf, c);
      xr = xf - ((ff * (x0 - xf)) / (f0 - ff));
      fr = horner(polinomio, xr, c);
      error = abs( ( (xr - xra) / xr ) * 100 );
      if (fr * f0 < 0)
        xf = xr;
      else if (fr * f0 > 0)
        x0 = xr;
    } while(error >= tolerancia);
    return xr;
  }

  // Método para Calcular por Sumas infinitas
  public static double euler (long n) {
    double pi = 0;                          // Almacena el valor calculado de cada iteración
    for (long i = 1; i <= n ; i ++) {       // Serie que va desde i=1 hasta n
      pi += ( 6.0 / ( pow(i, 2) ) );
    }
    return sqrt(pi);                   // Devuelve la raiz del resultado de la serie
  }

  public static double getError (double real, double calculado) {
    return abs( (real - calculado) / real ) * 100;
  }

  public static double getTolerancia (int cifras) {
    return 0.5 * pow(10, (2-cifras));
  }
}
