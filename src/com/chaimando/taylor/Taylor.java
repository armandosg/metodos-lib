import java.util.Random;
import java.util.Scanner;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Taylor {

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

  public static ArrayList<Double> coefSen (int numCoef, double c) {
    ArrayList<Double> coeficientes = new ArrayList<Double>();
    for (int k = 0; k < numCoef; k ++)
      coeficientes.add(coefSenN(k, c));
    return coeficientes;
  }

  public static double coefSenN (int k, double c) {
    double coef = 0;
    switch ((k + 1) % 4) {
      case 1: coef = Math.sin(c)/factorial((double)k);
        break;
      case 2: coef = Math.cos(c)/factorial((double)k);
        break;
      case 3: coef = (-1 * Math.sin(c))/factorial((double)k);
        break;
      case 0: coef = (-1 * Math.cos(c))/factorial((double)k);
        break;
    }
    return coef;
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
      error = Math.abs( ( (xr - xra) / xr ) * 100 );
      if (fr * f0 < 0)
        xf = xr;
      else if (fr * f0 > 0)
        x0 = xr;
    } while(error >= tolerancia);
    return xr;
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.println("Calcula hasta 16 decimales de PI");
    System.out.print("Centrar la serie en: ");
    double c = scan.nextDouble();

    System.out.print("Sumandos de la serie: ");
    int sumandos = scan.nextInt();

    System.out.print("Cifras significativas: ");
    int cifras = scan.nextInt();
    double tolerancia = 0.5 * Math.pow(10, (2-cifras));

    double error, piaprox;
    ArrayList<Double> polinomio = coefSen(sumandos, c);

    do {
      System.out.println("Sumandos");
      for (int i = 0; i < polinomio.size(); i ++)
        System.out.println("\t" + polinomio.get(i) + "\tx^" + i);
      piaprox = falsaPosicion(polinomio, c, tolerancia);
      error = Math.abs(((Math.PI - piaprox) / Math.PI) * 100);
      System.out.println("Pi aproximado para " + polinomio.size() + " sumandos: " + piaprox);
      System.out.println("Error para " + polinomio.size() + " sumandos: " + error + "\n");
      polinomio.add(coefSenN(sumandos ++, c));
    } while (error >= tolerancia);

    System.out.print("\t\t\t1 ");
    for (int i = 2; i <= 9; i ++) System.out.print(i);
    for (int i = 0; i <= 8; i ++) System.out.print(i);
    System.out.println("\nValor aprox. de PI:\t" + String.format("%.17f",piaprox));
    System.out.println("Valor real de PI:\t" + String.format("%.17f",Math.PI));
  }
}


//Centrada en 14, necesita al menos 28 sumandos para que arroje un resultado
//Centrada en 12, necesita al menos 23 sumandos para que arroje un resultado
//Centrada en 10, necesita al menos 18 sumandos para que arroje un resultado
//Centrada en 8, necesita al menos 13 sumandos para que arroje un resultado
//Centrada en 6, necesita al menos 7 sumandos para que arroje un resultado

//Centrada en 15 necesita 51 sumandos para 12 decimales
//Centrada en 14 necesita 49 sumandos para 13 decimales
//Centrada en 13 necesita 48 sumandos para 14 decimales
//Centrada en 12 necesita 45 sumandos para 15 decimales
//Centrada en 11 necesita 41 sumandos para 13 decimales
//Centrada en 10 necesita 40 sumandos para 14 decimales
//Centrada en 9 necesita 36 sumandos para 15 decimales
//Centrada en 8 necesita 33 sumandos para 15 decimales
//Centrada en 7 necesita 31 sumandos para 16 decimales
//Centrada en 6 necesita 27 sumandos para 16 decimales
//Centrada en 5 necesita 23 sumandos para 16 decimales
//Centrada en 4 necesita 17 sumandos para 16 decimales
//Centrada en 3 necesita 11 sumandos para 16 decimales
//Centrada en 2 necesita 19 sumandos para 16 decimales
//Centrada en 1 necesita 24 sumandos para 16 decimales
//Centrada en 0 necesita 28 sumandos para 16 decimales
