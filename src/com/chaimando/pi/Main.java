import java.util.Scanner;

class Main {

  static final int RELATIVO = 1;
  static final int REAL = 2;

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    //Pedir al usuario escoger el método para calcular pi
    System.out.println("Serie Producto -> 1\nEuler -> 2\nMontecarlo -> 3");
    System.out.println("\nCual metodo?: ");
    int method = Integer.parseInt(scan.nextLine());

    //Pedir al usuario el número de cifras significativas para calcular pi
    System.out.println("Ingrese el número de cifras de pi: ");
    int cifras = Integer.parseInt(scan.nextLine());

    System.out.println("Calcular por valor relativo -> 1");
    System.out.println("Calcular por valor real -> 2");
    int opc = Integer.parseInt(scan.nextLine());

    switch (opc) {
      case RELATIVO: Pi.getPiRelativo(cifras, method); break;
      case REAL: Pi.getPiReal(cifras, method); break;
      default: System.out.println("opcion invalida");
    }
    System.out.println("Valor real: " + Math.PI);
  }

}
