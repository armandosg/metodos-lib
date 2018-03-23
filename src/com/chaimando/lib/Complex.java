package com.chaimando.lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.LinkedHashSet;

import static com.chaimando.lib.Util.*;
import static java.lang.Math.*;

public class Complex {

  private static int k = 0;

  public static double cplx_mod(NumComplex z){
    double modulo = 0;
    modulo = sqrt(pow(z.a, 2) + pow(z.b, 2));
    return modulo;
  }

  public static double cplx_fase(NumComplex z){
    if(z.a == 0){
      return (z.b > 0 ? (PI/2) : (-PI/2));
    }
    else
      return atan(z.b / z.a);
  }

  public static NumComplex cplx_conj(NumComplex z){
    NumComplex zconj;
    zconj = z;
    zconj.b = -zconj.b;
    return zconj;
  }

  public static NumComplex cplx_sum(NumComplex z1, NumComplex z2){
    NumComplex z = new NumComplex();
    z.a = z1.a+z2.a;
    z.b = z1.b+z2.b;
    return z;
  }

  public static NumComplex cplx_dif(NumComplex z1, NumComplex z2){
    NumComplex z = new NumComplex();
    z.a = z1.a-z2.a;
    z.b = z1.b-z2.b;
    return z;
  }

  public static NumComplex cplx_prod(NumComplex z1, NumComplex z2){
    NumComplex z = new NumComplex();
    //(a,b)(c,d)=(ac-bd,ad+bc)
    z.a = z1.a*z2.a - z1.b*z2.b;
    z.b = z1.a*z2.b + z1.b*z2.a;
    return z;
  }

  public static NumComplex cplx_div(NumComplex z1, NumComplex z2){
    NumComplex zconj;
    NumComplex z = new NumComplex();
    zconj = cplx_conj(z2);

    // z1/z2=(z1*z2')/|z2|
    z = cplx_prod(z1,zconj);
    z.a = z.a/pow(cplx_mod(z2),2);
    z.b = z.b/pow(cplx_mod(z2),2);
    return z;
  }

  //sobrecarga de funciones para potencias y raíces n-esimas
  //cplx_pow calcua la n-ésima potencia entera del número z1
  public static NumComplex cplx_pow(NumComplex z1, int n){
    NumComplex z = new NumComplex();
    z.a=pow(cplx_mod(z1),n)*cos(n*cplx_fase(z1));
    z.b=pow(cplx_mod(z1),n)*sin(n*cplx_fase(z1));
    return z;
  }
  //cplx_raiz calcua la n-ésima potencia fraccionaria del número z1. Devuelve UNA raíz
  NumComplex cplx_raiz(NumComplex z1, double n){
    NumComplex z = new NumComplex();
      z.a=pow(cplx_mod(z1),n)*cos(n*cplx_fase(z1));
      z.b=pow(cplx_mod(z1),n)*sin(n*cplx_fase(z1));

    return z;
  }

  //evaluación de polinomios

  //algoritmo usual

  public static NumComplex cplx_ev_poli(int grado, ArrayList<Double> coeficientes, NumComplex z){
    ///EVALUAR POLINOMIOS EN NÚMEROS COMPLEJOS

    int k=0;
    NumComplex polinomio = new NumComplex();
    for(k = 0; k <= grado; k++){
      polinomio.a+=cplx_pow(z,k).a*coeficientes.get(k);
      polinomio.b+=cplx_pow(z,k).b*coeficientes.get(k);
    }
    return polinomio;
  }
  //algoritmo de Horner
  public static NumComplex cplx_horner(ArrayList<Double> coeficientes, NumComplex z){
    return cplx_horner(coeficientes.size() - 1, coeficientes, z);
  }

  public static NumComplex cplx_horner(int grado, ArrayList<Double> coeficientes, NumComplex z){
    NumComplex polinomio = new NumComplex();
    NumComplex coef_complejo = new NumComplex();

    polinomio.a = coeficientes.get(grado);
    polinomio.b = 0;

    for(k=grado-1; k >= 0; k--){
      coef_complejo.a = coeficientes.get(k);
      coef_complejo.b=0;
      polinomio=cplx_sum(coef_complejo,cplx_prod(z,polinomio));
    }
    return polinomio;
  }

  public static NumComplex cplx_newton(NumComplex x0, ArrayList<Double> coeficientes, double tolerancia) {
    ArrayList<Double> coefDerivada;
    NumComplex f, df, x1, error;
    coefDerivada = derivaPolinomio(coeficientes);
    int i = 0;
    do {
      f = cplx_horner(coeficientes, x0);
      df = cplx_horner(coefDerivada, x0);
      //x1 = x0 - (f/df);
      x1 = cplx_dif(x0, cplx_div(f,df));
      error = cplx_error(x1,x0);
      x0 = x1;
      i ++;
    } while (cplx_mod(error) >= tolerancia && i < Math.pow(tolerancia, -1));
    return x1;
  }

  public static NumComplex cplx_error (NumComplex real, NumComplex calculado) {
    return cplx_prod(cplx_div(cplx_dif(real, calculado), real), new NumComplex(100.0, 100.0));
  }

  //Las n potencias n-ésimas del número z. Devuelve un arreglo de tipo NumComplex
  ArrayList<NumComplex>  raices_nesimas(NumComplex z1, int n){
    ArrayList<NumComplex> arreglo = new ArrayList<NumComplex>();
    int k=0;
    for(k = 0; k < n; k++){
      arreglo.get(k).a=pow(cplx_mod(z1),1/(double)n)*cos((cplx_fase(z1)+2*k*PI)/n);
      arreglo.get(k).b=pow(cplx_mod(z1),1/(double)n)*sin((cplx_fase(z1)+2*k*PI)/n);
    }
    return arreglo;
  }

  //funciones trascendentes
  NumComplex cplx_sen(NumComplex z, double tolerancia){
    //implementa la serie de Taylor con siete cifras por default

    ArrayList<Double> coeficientes = new ArrayList<Double>();
    double c = 0;

    for(k=0; k<=20; k++){
      //La derivada es cíclica:
      if(k%4==0)
        coeficientes.add(sin(c)/factorial(k));
      if(k%4==1)
        coeficientes.add(cos(c)/factorial(k));
      if(k%4==2)
        coeficientes.add(-sin(c)/factorial(k));
      if(k%4==3)
        coeficientes.add(-cos(c)/factorial(k));
      }

    return cplx_horner(20,coeficientes,z);

  }

  NumComplex cplx_cos(NumComplex z, double tolerancia){
    //implementa la serie de Taylor con siete cifras por default
    ArrayList<Double> coeficientes = new ArrayList<Double>();
    double c = 0;

    for(k=0; k<=20; k++){
      //La derivada es cíclica:
      if(k%4==0)
      coeficientes.add(cos(c)/factorial(k));
      if(k%4==1)
      coeficientes.add(-sin(c)/factorial(k));
      if(k%4==2)
      coeficientes.add(-cos(c)/factorial(k));
      if(k%4==3)
      coeficientes.add(sin(c)/factorial(k));
    }

    return cplx_horner(20,coeficientes,z);
  }

  NumComplex cplx_exp(NumComplex z, double tolerancia){
    ArrayList<Double> coeficientes = new ArrayList<Double>();
    double c = 0;

    for(k=0; k<=20; k++)
      //La derivada es siempre la misma:
      coeficientes.add(exp(c)/factorial(k));

    return cplx_horner(20,coeficientes,z);
  }

  public static ArrayList<NumComplex> cplx_duplicates(ArrayList<NumComplex> lista) {
    ArrayList<NumComplex> repetidos = new ArrayList<NumComplex>();
    for (int i = 0; i < lista.size(); i ++) {
      for (int j = i + 1; j < lista.size(); j ++) {
        if(lista.get(i).a == lista.get(j).a && lista.get(i).b == lista.get(j).b)
          repetidos.add(lista.get(j));
      }
    }
    lista.removeAll(repetidos);
    return lista;
  }

  // public static ArrayList<Double> toDouble (ArrayList<NumComplex> lista) {
  //   ArrayList<Double> real = new ArrayList<Double>();
  //   for (NumComplex complex : lista) {
  //     real.add(complex.a);
  //   }
  //   return real;
  // }
  //
  // public static ArrayList<NumComplex> divisionComplex (ArrayList<NumComplex> polinomio, NumComplex x) {
  //   ArrayList<NumComplex> factorizado =  new ArrayList<NumComplex>();
  //   int grado = polinomio.size() - 1;
  //
  //   factorizado.add(polinomio.get(grado));
  //   NumComplex coeficiente, suma;
  //   for (int i = 1; i <= grado; i ++) {
  //     coeficiente = polinomio.get(grado - i);
  //     suma = factorizado.get(i - 1);
  //     factorizado.add(Complex.cplx_sum(Complex.cplx_prod(suma, x), coeficiente));
  //   }
  //
  //   Collections.reverse(factorizado);
  //   return factorizado;
  // }
}
