import com.chaimando.lib.Util;
import com.chaimando.lib.NumComplex;
import com.chaimando.lib.Complex;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class Roots {
  public static void main(String[] args) {
    ArrayList<Double> polinomio = new ArrayList<Double>();
    ArrayList<Double> raices = new ArrayList<Double>();

    /*******Capturar coeficientes del polinomio*******/
    int grado = 0;
    try {
      File archivo = new File ("/home/qwerty/metodos-lib/polinomio");
      FileReader fr = new FileReader (archivo);
      BufferedReader br = new BufferedReader(fr);
      String linea = "";
      for (; (linea = br.readLine()) != null;) {
        polinomio.add(Double.parseDouble(linea));
      }
      grado = polinomio.size() - 1;
      if (polinomio.get(grado) == 0) throw new Exception();
      br.close();
    }catch (Exception e) {
      System.out.println("Hubo un error");
      return;
    }
    System.out.println("-------------------------------------------------------------------");
    System.out.println("Polinomio: " + polinomio);

    /*******Calcular probables raíces usando el teorema de las raíces racionales*******/
    ArrayList<Double> probRaices = new ArrayList<Double>();
    double an = polinomio.get(grado);
    double a0 = polinomio.get(0);
    probRaices.addAll(Util.getProbRaices((int)an, (int)a0));
    for (int i = 1; (a0 = polinomio.get(i)) == 0; i ++) {}
    probRaices.addAll(Util.getProbRaices((int)an, (int)a0));
    probRaices = Util.removeDuplicates(probRaices);

    System.out.println("-------------------------------------------------------------------");
    System.out.println("Probables raíces: " + probRaices);

    /*******Probar si las probables raíces son raices del polinomio*******/
    ArrayList<Double> residuo = new ArrayList<Double>();
    Double probRaiz;
    for (int i = 0; i < probRaices.size(); i++) {
      probRaiz = probRaices.get(i);
      residuo = Util.divisionSintetica(polinomio, probRaiz);
      if (residuo.get(0) == 0) { // Si el último elemento del residuo es cero
        raices.add(probRaiz);
        residuo.remove(0); // Quitar el elemento que quedo cero
      }
    }
    probRaices.removeAll(raices); //Borra de la lista de las probables raíces las raíces que encontró

    System.out.println("-------------------------------------------------------------------");
    System.out.println("Probables raíces irracionales: " + probRaices);

    /*******Calcular las raíces en irracionales del polinomio con las probables raíces restantes*******/
    double tolerancia = Util.getTolerancia(8);
    residuo = polinomio;
    for (int i = 0; raices.size() < grado && i < probRaices.size(); i ++) {
      probRaiz = Util.newtonRapson(probRaices.get(i), residuo, tolerancia);
      if(Util.horner(residuo, probRaiz) <= tolerancia) { // Si al evaluar te da cercano a cero
        raices.add(probRaiz);
        residuo = Util.divisionSintetica(residuo, probRaiz);
        residuo.remove(0); // Quitar el elemento que quedo cero
      }
    }
    probRaices.removeAll(raices);//Borra de la lista de las probables raíces las raíces que encontró

    raices = Util.removeDuplicates(raices);

    System.out.println("-------------------------------------------------------------------");
    System.out.println("Residuo: " + residuo);
    System.out.println("-------------------------------------------------------------------");
    System.out.println  ("Polinomio factorizado: ");

    /******Imprime factores de grado 1******/
    for (double coef : raices) {System.out.print("(x + " + (-1 * coef) + ") ");}

    /******Imprime el residuo, puede ser de grado 1 o mayor******/
    int k = 0;
    for (double coef : residuo) {
      if (k == 0) System.out.print("(");
      System.out.print("" + coef + "x^" + k);
      k ++;
      if (k != residuo.size()) System.out.print(" + ");
    }
    System.out.println(")");
    System.out.println("-------------------------------------------------------------------");
    System.out.println("Raíces enteras e irracionales: ");
    for (double e : raices) {System.out.println(e);}


    /*******Calcular las raíces complejas del residuo si este es de grado 2 o mayor*******/
    System.out.println("-------------------------------------------------------------------");
    System.out.println("Raíces complejas:");
    /*******Si es de grado calculamos por formula general******/
    if (residuo.size() == 3) {

      double a = residuo.get(2), b = residuo.get(1), c = residuo.get(0);
      double d = Math.pow(b, 2) - (4 * a * c);
      d = Math.sqrt(Math.abs(d));
      NumComplex raiz = new NumComplex((-1*b)/(2*a), d/2*a);
      System.out.println(raiz.toStringConj());

    } else if (residuo.size() > 3) { // Si es de grado mayor a 2, aproximamos las raíces con el método de newtonRapson

      tolerancia = Util.getTolerancia(15);
      NumComplex probComplex, raiz;
      ArrayList<NumComplex> raicesImag = new ArrayList<NumComplex>();
      double dif = (double) 360 / (residuo.size() - 1);
      double radio = 1;
      for (double angulo = 0; raicesImag.size() < (residuo.size() - 1); angulo += dif) {
        probComplex = new NumComplex(compX(radio, angulo), compY(radio, angulo));
        probComplex = Complex.cplx_newton(probComplex, residuo, tolerancia);
        raiz = Complex.cplx_horner(residuo, probComplex);
        if (Complex.cplx_mod(raiz) < tolerancia) {
          raicesImag.add(probComplex);
          Complex.cplx_duplicates(raicesImag); // Elimina las raíces duplicadas
        }
        if (angulo == 360) { // Completada la circunferencia, probar con una de radio mayor
          angulo = 0;
          radio ++;
        }
      }
      for (NumComplex a: raicesImag) {System.out.println(a);}
    }
    System.out.println("-------------------------------------------------------------------");
  }

  private static double compX(double radio, double angulo) {
    return radio * Math.sin(Math.toRadians(angulo));
  }

  private static double compY(double radio, double angulo) {
    return radio * Math.cos(Math.toRadians(angulo));
  }
}
