import java.util.Scanner;

class Ramanujan {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    //Pedir al usuario el número de cifras significativas para calcular pi
    System.out.println("Ingrese el número de cifras de pi: ");
    int cifras = Integer.parseInt(scan.nextLine());

    System.out.println("Calcular por valor relativo -> 1");
    System.out.println("Calcular por valor real -> 2");
    int opc = Integer.parseInt(scan.nextLine());

    switch (opc) {
      case Main.RELATIVO: Pi.getPiRelativo(cifras, Pi.RAMANUJAN); break;
      case Main.REAL: Pi.getPiReal(cifras, Pi.RAMANUJAN); break;
      default: System.out.println("opcion invalida");
    }
    System.out.println("Valor real: " + Math.PI);
  }
}
