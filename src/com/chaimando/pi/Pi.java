import  java.util.Random;

class Pi {

  static final int WALLIS = 1;
  static final int EULER = 2;
  static final int MONTECARLO = 3;
  static final int RAMANUJAN = 4;


  static final int RELATIVO = 1;
  static final int REAL = 2;

  // Método para Calcular por Sumas infinitas
  static double euler (long n) {
    double pi = 0;                          // Almacena el valor calculado de cada iteración
    for (long i = 1; i <= n ; i ++) {       // Serie que va desde i=1 hasta n
      pi += ( 6.0 / ( Math.pow(i, 2) ) );
    }
    return Math.sqrt(pi);                   // Devuelve la raiz del resultado de la serie
  }


  // Método para Calcular por Multiplicaciones infinitas
  static double wallis (long n) {
    double pi = 1;                    // Almacena el valor calculado de cada iteración,
                                      // comenzando en uno para no alterar la serie

    for (long i = 1; i <= n; i ++) { // Serie que va desde i=1 hasta n
      pi *= ( Math.pow( 2*i , 2 ) ) / ( ( 2*i-1 ) * ( 2*i+1 ) );
    }
    return pi * 2.0;                  // Devuelve el resultado de la serie por dos
  }


  // Método para Calcular por la serie de Ramanujan
  static double ramanujan (long n) {
    double pi = 0;                    // Almacena el valor calculado de cada iteración,

    double aux1 = 0, aux2 = 0;        // Almacena dividendo y divisor de la formula de la serie

    for (long i = 0; i < n; i ++) {   // Serie que va desde i=0 hasta n
      aux1 = factorial(4*i) * (1103 + (26390 * i));
      aux2 = Math.pow( ( factorial(i) ) , 4 ) * Math.pow( 396 , 4 * i);
      pi += aux1/aux2;
    }

    return Math.pow( ( ( 2 * Math.sqrt(2) ) / 9801.0) * pi , -1);
  }


  // Método para Calcular por el método de Montecarlo
  static double montecarlo (long puntosTotales){
    double x = 0, y = 0;              // Almacena las dos coordenadas del punto
    long puntosCirculo = 0;           // Contador de puntos que caen dentro del circulos
    for (long i = 0; i < puntosTotales; i ++) { // Generar tantos puntos como puntosTotales indique

      x = generateNumber();           // Generar número aleatorio dentro del rango (-1,1)
      y = generateNumber();           // Generar número aleatorio dentro del rango (-1,1)

      if (Math.hypot(x,y) <= 1)       // Calcular la magnitud del vector generado
        puntosCirculo ++;             // Si la magnitud es menor al valor del radio del circulo sumar

    }
    return 4.0 * ( (double)puntosCirculo / (double)puntosTotales ); // Calcular el área del círculo
  }

  //Generar un número aleatorio dentro del rango (-1,1)
  static double generateNumber() {
    double n = new Random().nextFloat();         //Generar el número aleatorio
    return 2 * n - 1;                 // Hacer que el número este en el rango (0,2)
                                      // Y restar uno para que quede en (-1,1)
  }


  //Calcula el factorial de un número
  static long factorial(long number) {
    long result = 1;
    for (long factor = 2; factor <= number; factor++) {
      result *= factor;
    }
    return result;
  }

  // Llama a la función que corresponde al método elegido y devuelve la aproximación a pi
  static double getPiFromMethod (int method, long n) {
    switch (method) {
      case Pi.WALLIS: return Pi.wallis(n);
      case Pi.EULER: return Pi.euler(n);
      case Pi.MONTECARLO: return Pi.montecarlo(n);
      case Pi.RAMANUJAN: return Pi.ramanujan(n);
      default: return 0; //Retorna cero para cualquier otro valor recibido
    }
  }


  static double getPiRelativo(int cifras, int method) {
    double tolerancia = getTolerancia(cifras); // Calcular tolerancia con el número de cifras dado
    System.out.println(tolerancia);
    double valorAnterior = 0;

    long i = 1;
    if (method == Pi.MONTECARLO) { // Si el método seleccionado es el de Montecarlo comienza el contador en 100
      i = 100;
    }
    // Asignar valores a valorActual y error para poder comenzar a iterar
    double valorActual = getPiFromMethod(method, i); // Calcular la aproximación de pi
    double error = getError(valorActual, valorAnterior); // Calcular el primer error
    i ++;

    while (error >= tolerancia) {                   // Mientras el error sea mayor que la tolerancia
      valorAnterior = valorActual;                  // Guardar el valor de la iteración anterior
      valorActual = getPiFromMethod(method, i);     // Calcular la aproximación de la iteración actual
      error = getError(valorActual, valorAnterior); // Calcular el error

      if (method == Pi.MONTECARLO)                  // Si método elegido es el Montecarlo
        i += 100;                                     // subir contador de 100 en 100
      else
        i *= 2;
      print(valorActual, error, i);
    }

    print(valorActual, error, i);
    return valorActual;
  }


  static double getPiReal(int cifras, int method) {
    double tolerancia = getTolerancia(cifras);    // Calcular tolerancia con el número de cifras dado
    double piAprox, error;
    long i = 1;
    if (method == Pi.MONTECARLO) { // Si el método seleccionado es el de Montecarlo comienza el contador en 100
      i = 100;
    }
    do {
      piAprox = getPiFromMethod(method, i);       // Calcular la aproximación de la iteración actual
      error = getError(Math.PI, piAprox);         // Calcular el error
      print(piAprox, error, i);
      if (method == Pi.MONTECARLO)                // Si método elegido es el Montecarlo
        i += 1000;                                // subir contador de 100 en 100
      else
        i *= 2;
    } while (error >= tolerancia);
    print(piAprox, error, i);
    return piAprox;
  }


  static double getError (double real, double calculado) {
    return Math.abs( (real - calculado) / real ) * 100;
  }


  static double getTolerancia (int cifras) {
    return 0.5 * Math.pow(10, (2-cifras));
  }


  // Muestra en pantalla el último valor aproximado, el último error y cantidad de iteraciones
  static void print(double resultado, double error, long i){
    System.out.println("Último valor aproximado; " + resultado);
    System.out.println("Error final: " + error);
    System.out.println("Cantidad de iteraciones: " + i);
  }
}
