package com.chaimando.lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.LinkedHashSet;

import static java.lang.Math.*;

public class Util {

  // Método para Calcular por Sumas infinitas
  public static double euler (long n) {
    double pi = 0;                          // Almacena el valor calculado de cada iteración
    for (long i = 1; i <= n ; i ++) {       // Serie que va desde i=1 hasta n
      pi += ( 6.0 / ( pow(i, 2) ) );
    }
    return sqrt(pi);                   // Devuelve la raiz del resultado de la serie
  }

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
    if (polinomio.size() == 0) {
      return 0;
    }
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
    int i = 0;
    do {
      f = horner(coeficientes, x0, 0);
      df = horner(coefDerivada, x0, 0);
      x1 = x0 - (f/df);
      error = getError(x1,x0);
      x0 = x1;
      i ++;
    } while (error >= tolerancia && i < Math.pow(tolerancia, -1));
    return x1;
  }

  //derivada de una función polinomial de grado n
  public static ArrayList<Double> derivaPolinomio (ArrayList<Double> coeficientes) {
    ArrayList<Double> coefDerivada = new ArrayList<Double>();
    for(int i = 1; i < coeficientes.size(); i ++) {
      coefDerivada.add(coeficientes.get(i) * i);
    }
    return coefDerivada;
  }

  public static double getError (double real, double calculado) {
    return abs( (real - calculado) / real ) * 100;
  }

  public static double getTolerancia (int cifras) {
    return 0.5 * pow(10, (2-cifras));
  }

  public static ArrayList<Double> divisionSintetica (ArrayList<Double> polinomio, double x) {
    ArrayList<Double> factorizado =  new ArrayList<Double>();
    int grado = polinomio.size() - 1;

    factorizado.add(polinomio.get(grado));
    for (int i = 1; i <= grado; i ++)
      factorizado.add(polinomio.get(grado - i) + factorizado.get(i - 1) * x);

    Collections.reverse(factorizado);
    return factorizado;
  }

  public static ArrayList<Integer> factores (int num) {
    ArrayList<Integer> factores = new ArrayList<Integer>();
    if (num < 0) num = abs(num);
    factores.add(num);
    if (num == 0 || num == 1) return factores;
    factores.add(1);
    int i = 1;
    int factor = num, factorAnterior = num;
    do {
      i ++;
      if (num % i == 0) {
        factorAnterior = factor;
        if (i == factorAnterior) break;
        factor = num / i;
        factores.add(i);
        if(i != factor) factores.add(factor);
      }
    } while (i < (num / 2));
    return factores;
  }

  public static ArrayList<Double> getProbRaices (int an, int a0) {
    ArrayList<Integer> factoresAn = Util.factores((int)an);
    ArrayList<Integer> factoresA0 = Util.factores((int)a0);
    ArrayList<Double> probRaices = new ArrayList<Double>();
    double probRaiz;
    for (int i = 0; i < factoresA0.size(); i ++) {
      for (int j = 0; j < factoresAn.size(); j ++) {
        probRaiz = (double) factoresA0.get(i) / factoresAn.get(j);
        probRaices.add( probRaiz );
        probRaices.add( -1*probRaiz );
      }
    }
    probRaices = removeDuplicates(probRaices);
    Collections.sort(probRaices);
    return probRaices;
  }

  public static ArrayList<Double> removeDuplicates(ArrayList<Double> lista) {
    Set<Double> hs = new LinkedHashSet<Double>(lista);
    lista.clear();
    lista.addAll(hs);
    return lista;
  }

}
