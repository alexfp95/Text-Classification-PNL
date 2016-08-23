package clasificaciondetextos;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.*;
import java.util.TreeMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.Collections;
import java.util.Iterator;

/**
 * Alexis Daniel Fuentes P�rez
 * Luis David Padilla Martin
 */

public class Vocabulario {
  private TreeMap<String, Integer> tree;
  
  /**
   * Constructor. Almacena las palabras en un TreeMap. La palabra ser� la clave, y la ocurrencia el valor.
   * @param file Archivo del que se va a leer.
   * @throws FileNotFoundException No se encuentra el fichero.
   * @throws IOException Excepsion en la entrada / salida.
   */
  
  public Vocabulario (String file) throws FileNotFoundException, IOException {
    tree =  new TreeMap <String,Integer> ();
    String datos = new String();
    BufferedReader reader = new BufferedReader( new FileReader(file) );

    while ( reader.ready() ) {
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
		          if(tree.containsKey(key) == false) 
		            tree.put(key, 1);
		          else {
		            Integer count = tree.get(key);
		            tree.remove(key);
		            tree.put(key, count + 1);
		          }
	          }
        	}
        }
      }
    }
    
    reader.close();
  }
  
  /**
   * Recorre el TreeMap con un iterador, y muestra cada palabra (clave).
   */
  
  public void mostrar () {
    Iterator<String> iter = tree.keySet().iterator();
    
    while( iter.hasNext() ) {
      String key = iter.next();
      System.out.println(key);
    }
  }
  
  /**
   * Exporta en un fichero "vocabulario.txt"
   * @throws IOException
   */
  
  public void exportar () throws IOException {
  	FileWriter fichero = null;
    fichero = new FileWriter("src/clasificaciondetextos/vocabulario.txt");
    
    fichero.write("Numero de palabras:");
    fichero.write(tree.size() + "\n");
    
    Iterator<String> iter = tree.keySet().iterator();
    
    while( iter.hasNext() ) {
      String key = iter.next();
      fichero.write("Palabra:" + key + "\n");
    }
    
    fichero.close();
  }
  
  public TreeMap getVocabulario () {
    return tree;
  }
  
}
