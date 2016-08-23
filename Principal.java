package clasificaciondetextos;

import java.io.*;
import java.util.ArrayList;

import javax.swing.JFrame;


/**
 * Alexis Daniel Fuentes P�rez
 * Luis David Padilla Martin
 */

public class Principal {
  
  /**
   * 
   * @param args Nombre del fichero de texto
   * @throws FileNotFoundException
   * @throws IOException
   */

  public static void main(String[] args) throws FileNotFoundException, IOException {
    System.out.println("Alexis Daniel Fuentes Pérez");
    System.out.println("Luis David Padilla Martin");
    
    System.out.println("\n> Creando vocabulario");
    Vocabulario vocabulario = new Vocabulario(args[0]);
    vocabulario.exportar();
    System.out.println("Listo");
    
    System.out.println("\n> Creando archivos de aprendizaje");
    Aprendizaje ap1 = new Aprendizaje ("src/clasificaciondetextos/corpusrel.txt", vocabulario.getVocabulario());
    ap1.exportar("aprendizajerel.txt");
    
    Aprendizaje ap2 = new Aprendizaje ("src/clasificaciondetextos/corpusnrel.txt", vocabulario.getVocabulario());
    ap2.exportar("aprendizajenrel.txt");
    System.out.println("Listo");
    
    System.out.println("\n> Clasificando");
    Clasificacion c = new Clasificacion ("src/clasificaciondetextos/corpustodo.txt", "src/clasificaciondetextos/aprendizajerel.txt", "src/clasificaciondetextos/aprendizajenrel.txt");
    c.exportar();
    System.out.println("Listo\n");
    
    PorcentajeAcierto pc = new PorcentajeAcierto ("src/clasificaciondetextos/clasificacion.txt", ap1.getNumDocs(), ap2.getNumDocs());
    pc.exportar();
  }

}
