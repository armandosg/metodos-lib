package com.chaimando.metodos;

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

  public static double newtonRapson(double x0, ArrayList<Double> coeficientes, double tolerancia) {
    ArrayList<Double> coefDerivada;
    double f, df, x1 = 0, error;
    coefDerivada = derivaPolinomio(coeficientes);
    do {
      x0 = x1;
      f = horner(coeficientes, x0, 0);
      df = horner(coefDerivada, x0, 0);
      x1 = x0 - (f/df);
      error = getError(x1,x0);
    } while (error >= tolerancia);
    return x1;
  }

  //división sintética
  /*
  int divSintetica(int x, ArrayList<Double> coeficientes) {
    asignar último elemento de coeficientes a variable 'residuo'
    mientras i >= 0
      hacer 'residuo' por x, mas coeficientes en i-1
      asignar el resultado de lo anterior a 'residuo'
      i--
    devolver 'residuo'
  }
  */

  //derivada de una función polinomial de grado n
  public static ArrayList<Double> derivaPolinomio(ArrayList<Double> coeficientes) {
    ArrayList<Double> coefDerivada = new ArrayList<Double>();
    for(int i = 1; i < coeficientes.size(); i ++) {
      coefDerivada.add(i - 1, coeficientes.get(i) * i);
    }
    return coefDerivada;
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
  
   public static double divisionSintetica (ArrayList<Double> polinomio, double x) {
    int grado = polinomio.size(), i;
    double[] a =  null;
    a[grado] = polinomio.get(grado);
    for (i = grado - 1; i >= 0; i--)
      a[i] = polinomio.get(i) + a[i+1]*x;
    return a[i];
    }
}
