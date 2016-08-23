package clasificaciondetextos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

public class Clasificacion {

  private ArrayList<ArrayList<String>> tweets;
  private TreeMap<String, ArrayList<Double>> relevante;
  private TreeMap<String, ArrayList<Double>> norelevante;
  private int numDocs1;
  private int numDocs2;
  
  public Clasificacion (String corpus, String aprendizaje1, String aprendizaje2) throws FileNotFoundException, IOException {
    tweets = new ArrayList<ArrayList<String>> ();
    relevante = new TreeMap<String, ArrayList<Double>> ();
    norelevante = new TreeMap<String, ArrayList<Double>> ();
    
    String datos = new String();
    BufferedReader reader = new BufferedReader(new FileReader(corpus));
    
    while (reader.ready()) {
      ArrayList<String> tweet = new ArrayList<String> ();
      datos = reader.readLine();
      String [] valores = datos.split("@([A-Za-z0-9_]+)|\\d|\\W");     // Quita usuarios, y cadenas que no sean palabras
      
      int tokensEnlace = 0;
      
      for(int k = 1; k < valores.length; k++) {
        if(valores[k].length() != 0) {
          if(tokensEnlace != 0) {
            tokensEnlace--;
          } else {
            String key = valores[k].toLowerCase();
            if(key.equals("http") || key.equals("https") || key.equals("www")) {
              tokensEnlace = 3;
            } else {
              tweet.add(valores[k]);
            }
          }
        }
      }
      //System.out.println(tweet);
      tweets.add(tweet);
    }
    reader.close();
    
    BufferedReader readerA1 = new BufferedReader (new FileReader (aprendizaje1));
    
    datos = readerA1.readLine();
    String[] v = datos.split("Numero de documentos del corpus:");
    for(int i = 0; i < v.length; i++){
      if(!v[i].isEmpty())
        numDocs1 = Integer.parseInt(v[i]);
    }
    
    datos = readerA1.readLine();
    
    while (readerA1.ready()) {
      ArrayList<Double> frecprob = new ArrayList<Double> ();
      datos = readerA1.readLine();
      String [] valores = datos.split("Palabra:|Frec:|LogProb:|\\s+");
      //System.out.println(valores.length);
      int contador = 0;
      String key = new String ();
      for(int i = 0; i < valores.length; i++) {
        if (!valores[i].isEmpty()) {
          if(contador == 0) {
            key = valores[i];
            contador++;
          } else {
            frecprob.add(Double.parseDouble(valores[i]));
          }
        }
        //System.out.print(valores[i] + "+");
      }
      //System.out.print(key + frecprob);
      relevante.put(key, frecprob);
    }
    
    BufferedReader readerA2 = new BufferedReader (new FileReader (aprendizaje2));
    
    datos = readerA2.readLine();
    String[] v2 = datos.split("Numero de documentos del corpus:");
    for(int i = 0; i < v2.length; i++){
      if(!v2[i].isEmpty())
        numDocs2 = Integer.parseInt(v2[i]);
    }
    
    datos = readerA2.readLine();
    
    while (readerA2.ready()) {
      ArrayList<Double> frecprob = new ArrayList<Double> ();
      datos = readerA2.readLine();
      String [] valores = datos.split("Palabra:|Frec:|LogProb:|\\s+");
      //System.out.println(valores.length);
      int contador = 0;
      String key = new String ();
      for(int i = 0; i < valores.length; i++) {
        if (!valores[i].isEmpty()) {
          if(contador == 0) {
            key = valores[i];
            contador++;
          } else {
            frecprob.add(Double.parseDouble(valores[i]));
          }
        }
        //System.out.print(valores[i] + "+");
      }
      //System.out.print(key + frecprob);
      norelevante.put(key, frecprob);
    }
  }
  
  public void mostrar () {
    System.out.println("Almacenado en arboles: ");
    
    Iterator<String> iter = relevante.keySet().iterator();
    
    while( iter.hasNext() ) {
      String key = iter.next();
      System.out.println(key + " " + relevante.get(key));
    }
    
    System.out.println("\n");
    
    Iterator<String> iter2 = norelevante.keySet().iterator();
    
    while( iter2.hasNext() ) {
      String key = iter2.next();
      System.out.println(key + " " + norelevante.get(key));
    }
  }
  
  public void exportar () throws IOException {
    FileWriter fichero = null;
    fichero = new FileWriter("src/clasificaciondetextos/clasificacion.txt");
    
    for(int i = 0; i < tweets.size(); i++) {
      double probRel = 0;
      double probNrel = 0;
      
      String clase = new String ();
      String documento = "";
      ArrayList<String> tweet = tweets.get(i);
      
      for(int k = 0; k < tweet.size(); k++) {
        String palabra = tweet.get(k);
        
        documento += palabra;
        if(k < (tweet.size() - 1))
          documento += " ";
        
        if(relevante.get(palabra) != null) {
          probRel += relevante.get(palabra).get(1);
        } else {
          probRel += relevante.get("<unknown>").get(1);
        }
        if(norelevante.get(palabra) != null) {
          probNrel += norelevante.get(palabra).get(1);
        } else {
          probNrel += norelevante.get("<unknown>").get(1);
        }
      }
      
      //probRel += log2((double)numDocs1 / (double)(numDocs1 + numDocs2));
      //probNrel += log2((double)numDocs2 / (double)(numDocs1 + numDocs2));
      
      if(probRel > probNrel) {
        clase = "rel";
      } else {
        clase = "nrel";
      }
      
      fichero.write("Clase:<" + clase + "> Texto:<" + documento + ">\n");
    }
    
    fichero.close();
  }
  
  public double log2 (double n) {
    return (Math.log(n) / Math.log(2));
  }
}
