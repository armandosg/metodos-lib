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
    ArrayList<Double> raices = new ArrayList<Double>();
    ArrayList<Double> probIrracionales = new ArrayList<Double>();


    ArrayList<Double> residuo = new ArrayList<Double>();
    ArrayList<Double> probRaices = new ArrayList<Double>();
    double an, a0;
    for (int k = 0; k < coeficientes.size() - 2; k ++) {
      an = polinomio.get(polinomio.size() - 1);
      a0 = polinomio.get(0);
      probRaices = Util.getProbRaices((int)an, (int)a0);
      System.out.println(polinomio);
      System.out.println(probRaices);
      for (int i = 0; i < probRaices.size() && probIrracionales.size() < grado; i++) {
        residuo = Util.divisionSintetica(polinomio, probRaices.get(i));
        if (residuo.get(0) == 0) {
          System.out.println("entra");
          System.out.println(probRaices.get(i));
          raices.add(probRaices.get(i));
          probRaices.remove(i);
          residuo.remove(0);
          polinomio = residuo;
          break;
        } else {
          probIrracionales.add(probRaices.get(i));
        }
      }
    }


    System.out.println(probIrracionales);

    double tolerancia = Util.getTolerancia(7);
    polinomio = coeficientes;
    double x;
    for (double prueba : probIrracionales) {
      x = Util.newtonRapson(prueba, polinomio, tolerancia);
      if(Util.horner(polinomio, x, 0) <= tolerancia) {
        raices.add(x);
        polinomio = Util.divisionSintetica(polinomio, x);
        polinomio.remove(0);
        System.out.println(polinomio);
      }
    }

    Set<Double> hs = new LinkedHashSet<Double>(raices);
    raices.clear();
    raices.addAll(hs);

    System.out.println("Raíces: ");
    for (double e : raices) {
      System.out.println(e);
    }
  }
}
