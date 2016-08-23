package clasificaciondetextos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PorcentajeAcierto {
  
  private double porcentaje;
  
  public PorcentajeAcierto (String file, int limRel, int limNrel) throws IOException {
    String datos = new String();
    BufferedReader reader = new BufferedReader(new FileReader(file));
    
    int numRelCorrectos = 0;
    
    for(int i = 0; i < limRel; i++) {
      if (reader.ready()) {
        datos = reader.readLine();
        String [] valores = datos.split("Clase:<|> Texto:<.+");
        for(int k = 0; k < valores.length; k++) {
          if(valores[k].length() != 0)
            if(valores[k].equals("rel")) {
              numRelCorrectos++;
            } 
        }
      }
    }
    
    int numNrelCorrectos = 0;
    
    for(int i = limRel; i < (limRel + limNrel); i++) {
      if (reader.ready()) {
        datos = reader.readLine();
        String [] valores = datos.split("Clase:<|> Texto:<.+");
        for(int k = 0; k < valores.length; k++) {
          if(valores[k].length() != 0) {
            if(valores[k].equals("nrel")) {
              numNrelCorrectos++;
            }
          }
        }
      }
    }
    
    porcentaje = ((((double)numRelCorrectos + (double)numNrelCorrectos) / ((double)limRel + (double)limNrel)) * 100);
    System.out.println("Porcentaje de acierto: " + porcentaje + "%");
    
    reader.close();
  }
  
  public void exportar () throws IOException {
    FileWriter fichero = null;
    fichero = new FileWriter("src/clasificaciondetextos/porcentajeexito.txt");
    
    fichero.write("Porcentaje de acierto: " + porcentaje + "%");
    
    fichero.close();
  }
}
