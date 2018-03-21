import com.chaimando.lib.Util;
import com.chaimando.lib.NumComplex;
import com.chaimando.lib.Complex;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Set;
import java.util.LinkedHashSet;

class Roots {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    System.out.print("Ingresa el grado del polinomio: ");
      int grado = scan.nextInt();
    if (grado < 1) {
      return;
    }

    ArrayList<Double> coeficientes = new ArrayList<Double>();
    double a;
    double b;
    for (int i = 0; i <= grado; i ++) {
      System.out.print("Ingresa coeficiente x^" + i + ": ");
      coeficientes.add(scan.nextDouble());
    }

    ArrayList<Double> polinomio = coeficientes;
    ArrayList<Double> residuo = new ArrayList<Double>();
    ArrayList<Double> raices = new ArrayList<Double>();
    ArrayList<Double> probIrracionales = new ArrayList<Double>();
    ArrayList<Integer> factoresAn;
    ArrayList<Integer> factoresA0;
    double an, a0;
    for (int k = 0; k < coeficientes.size() - 1; k ++) {
      an = polinomio.get(polinomio.size() - 1);
      a0 = polinomio.get(0);
      factoresAn = Util.factores((int)an);
      factoresA0 = Util.factores((int)a0);

      double probRaiz, x;
      test:
        for (int i = 0; i < factoresA0.size(); i ++) {
          for (int j = 0; j < factoresAn.size(); j ++) {
            probRaiz = (double) factoresA0.get(i) / factoresAn.get(j);
            residuo = Util.divisionSintetica(polinomio, probRaiz);
            if (residuo.get(0) == 0) {
              raices.add(probRaiz);
              residuo.remove(0);
              break test;
            } else {
              probIrracionales.add(probRaiz);
            }
          }
        }
      polinomio = residuo;
    }

    Set<Double> hs = new LinkedHashSet<Double>(probIrracionales);
    probIrracionales.clear();
    probIrracionales.addAll(hs);

    double tolerancia = Util.getTolerancia(15);
    polinomio = coeficientes;
    double x;
    for (double complex : probIrracionales) {
      x = Util.newtonRapson(complex, polinomio, tolerancia);
      if(Util.horner(polinomio, x, 0) <= tolerancia) {
        raices.add(x);
        polinomio = Util.divisionSintetica(polinomio, x);
        polinomio.remove(0);
      }
    }

    System.out.println("Raíces: ");
    for (double e : raices) {
      System.out.println(e);
    }
  }
}
