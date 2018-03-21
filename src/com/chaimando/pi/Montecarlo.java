import java.util.Scanner;

class Montecarlo {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    // Pedir al usuario el número de cifras significativas para calcular pi
    System.out.println("Ingrese el número de cifras de pi: ");
    int cifras = Integer.parseInt(scan.nextLine());

    System.out.println("Calcular por valor relativo -> 1");
    System.out.println("Calcular por valor real -> 2");
    int opc = Integer.parseInt(scan.nextLine());

    switch (opc) {
      case Pi.RELATIVO:
        Pi.getPiRelativo(cifras, Pi.MONTECARLO);
        break;
      case Pi.REAL:
        Pi.getPiReal(cifras, Pi.MONTECARLO);
        break;
      default: System.out.println("opcion invalida");
    }
    // System.out.println(Pi.montecarlo(1000000));
    System.out.println("Valor real: " + Math.PI);
  }
}
