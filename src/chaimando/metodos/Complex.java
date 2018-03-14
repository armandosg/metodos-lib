package chaimando.metodos;
import java.util.ArrayList;

public class Complex {

  private int k = 0;

  public double cplx_mod(NumComplex z){
    double modulo = 0;
    modulo = Math.sqrt(Math.pow(z.a, 2) + Math.pow(z.b, 2));
    return modulo;
  }

  public double cplx_fase(NumComplex z){
    if(z.a == 0){
      return (z.b > 0 ? (Math.PI/2) : (-Math.PI/2));
    }
    else
      return Math.atan(z.b / z.a);
  }

  public NumComplex cplx_conj(NumComplex z){
    NumComplex zconj;
    zconj = z;
    zconj.b = -zconj.b;
    return zconj;
  }

  public NumComplex cplx_sum(NumComplex z1, NumComplex z2){
    NumComplex z = new NumComplex();
    z.a = z1.a+z2.a;
    z.b = z1.b+z2.b;
    return z;
  }

  public NumComplex cplx_dif(NumComplex z1, NumComplex z2){
    NumComplex z = new NumComplex();
    z.a = z1.a-z2.a;
    z.b = z1.b-z2.b;
    return z;
  }

  NumComplex cplx_prod(NumComplex z1, NumComplex z2){
    NumComplex z = new NumComplex();
    //(a,b)(c,d)=(ac-bd,ad+bc)
    z.a = z1.a*z2.a - z1.b*z2.b;
    z.b = z1.a*z2.b + z1.b*z2.a;
    return z;
  }

  NumComplex cplx_div(NumComplex z1, NumComplex z2){
    NumComplex zconj;
    NumComplex z = new NumComplex();
    zconj = cplx_conj(z2);

    // z1/z2=(z1*z2')/|z2|
    z = cplx_prod(z1,zconj);
    z.a = z.a/Math.pow(cplx_mod(z2),2);
    z.b = z.b/Math.pow(cplx_mod(z2),2);
    return z;
  }

  //sobrecarga de funciones para potencias y raíces n-esimas
  //cplx_pow calcua la n-ésima potencia entera del número z1
  NumComplex cplx_pow(NumComplex z1, int n){
    NumComplex z = new NumComplex();
    z.a=Math.pow(cplx_mod(z1),n)*Math.cos(n*cplx_fase(z1));
    z.b=Math.pow(cplx_mod(z1),n)*Math.sin(n*cplx_fase(z1));
    return z;
  }
  //cplx_raiz calcua la n-ésima potencia fraccionaria del número z1. Devuelve UNA raíz
  NumComplex cplx_raiz(NumComplex z1, double n){
    NumComplex z = new NumComplex();
      z.a=Math.pow(cplx_mod(z1),n)*Math.cos(n*cplx_fase(z1));
      z.b=Math.pow(cplx_mod(z1),n)*Math.sin(n*cplx_fase(z1));

    return z;
  }

  //evaluación de polinomios

  //algoritmo usual

  NumComplex cplx_ev_poli(int grado, double coeficientes[], NumComplex z){
    ///EVALUAR POLINOMIOS EN NÚMEROS COMPLEJOS

    int k=0;
    NumComplex polinomio = new NumComplex();
    for(k = 0; k <= grado; k++){
      polinomio.a+=cplx_pow(z,k).a*coeficientes[k];
      polinomio.b+=cplx_pow(z,k).b*coeficientes[k];
    }
    return polinomio;
  }
  //algoritmo de Horner
  NumComplex cplx_horner(int grado, double coeficientes[], NumComplex z){
    NumComplex polinomio = new NumComplex();
    NumComplex coef_complejo = new NumComplex();

    polinomio.a = coeficientes[grado];
    polinomio.b = 0;

    for(k=grado-1; k >= 0; k--){
      coef_complejo.a = coeficientes[k];
      coef_complejo.b=0;
      polinomio=cplx_sum(coef_complejo,cplx_prod(z,polinomio));
    }
    return polinomio;
  }
  //Las n potencias n-ésimas del número z. Devuelve un arreglo de tipo NumComplex
  NumComplex[]  raices_nesimas(NumComplex z1, int n){
    NumComplex[] arreglo = new NumComplex[100];
    int k=0;
    for(k = 0; k < n; k++){
      arreglo[k].a=Math.pow(cplx_mod(z1),1/(double)n)*Math.cos((cplx_fase(z1)+2*k*Math.PI)/n);
      arreglo[k].b=Math.pow(cplx_mod(z1),1/(double)n)*Math.sin((cplx_fase(z1)+2*k*Math.PI)/n);
    }
    return arreglo;
  }

  //funciones trascendentes
  NumComplex cplx_sen(NumComplex z, double tolerancia){
    //implementa la serie de Taylor con siete cifras por default

    double[] coeficientes = new double[21];
    double c = 0;

    for(k=0; k<=20; k++){
      //La derivada es cíclica:
      if(k%4==0)
        coeficientes[k]=Math.sin(c)/Util.factorial(k);
      if(k%4==1)
        coeficientes[k]=Math.cos(c)/Util.factorial(k);
      if(k%4==2)
        coeficientes[k]=-Math.sin(c)/Util.factorial(k);
      if(k%4==3)
        coeficientes[k]=-Math.cos(c)/Util.factorial(k);
      }

    return cplx_horner(20,coeficientes,z);

  }
  NumComplex cplx_cos(NumComplex z, double tolerancia){
    //implementa la serie de Taylor con siete cifras por default
    double[] coeficientes = new double[21];
    double c = 0;

    for(k=0; k<=20; k++){
      //La derivada es cíclica:
      if(k%4==0)
      coeficientes[k]=Math.cos(c)/Util.factorial(k);
      if(k%4==1)
      coeficientes[k]=-Math.sin(c)/Util.factorial(k);
      if(k%4==2)
      coeficientes[k]=-Math.cos(c)/Util.factorial(k);
      if(k%4==3)
      coeficientes[k]=Math.sin(c)/Util.factorial(k);
    }

    return cplx_horner(20,coeficientes,z);
  }
  NumComplex cplx_exp(NumComplex z, double tolerancia){
    double[] coeficientes = new double[21];
    double c = 0;

    for(k=0; k<=20; k++)
      //La derivada es siempre la misma:
      coeficientes[k]=Math.exp(c)/Util.factorial(k);

    return cplx_horner(20,coeficientes,z);
  }
}
