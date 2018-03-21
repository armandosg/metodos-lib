import com.chaimando.lib.Util;
import java.util.Scanner;
import java.util.ArrayList;

public class MinMax {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    System.out.print("Ingresa el grado del polinomio: ");
      int grado = scan.nextInt();

    ArrayList<Double> coeficientes = new ArrayList<Double>();
    for (int i = 0; i <= grado; i ++) {
      System.out.print("Ingresa coeficiente x^" + i + ": ");
      coeficientes.add(scan.nextDouble());
    }

    System.out.print("Ingrese el valor inicial: ");
      double x0 = scan.nextDouble();

    System.out.print("Cifras significativas: ");
      double tolerancia = Util.getTolerancia(scan.nextInt());

    ArrayList<Double> derivada = Util.derivaPolinomio(coeficientes);

    double x = Util.newtonRapson(x0, derivada, tolerancia);

    System.out.print("El punto (" + x + ", " + Util.horner(coeficientes, x, 0) + "): es un ");

    ArrayList<Double> segundaDerivada = Util.derivaPolinomio(derivada);

    if(Util.horner(segundaDerivada, x, 0) < 0) {
      System.out.print("máximo\n");
    } else {
      System.out.print("mínimo\n");
    }

  }
}
