package me.khmdev.Freezee.lang;
import java.util.*;

public class txt_es_ES extends ListResourceBundle 
{ 
   public Object[][] getContents() 
   {
      return contenido;
   }
   private Object[][] contenido = { 
		   {"partida.noFreezee","%player% no esta congelado"},
		   {"partida.freezeeYet","%player% ya esta congelado"},
		   {"partida.freezee","%from% a congelado a %to%"},
		   {"partida.deFreezee","%from% a descongelado a %to%"},
		   {"AzadaTNT.reload","Aun no se ha recargado el item"},
		   {"AzadaTNT.name","Lanza TNT"},
		   {"AzadaSnow.name","Lanza bolas"},
		   {"AzadaY.name","Lanza yunques"},
		   {"setterEquipo.noFreezee","Ya no puedes ser congelador"},
		   {"Congelador.name","Congelar"},
		   {"Descongelador.name","Descongelar"},
		  
		   {"BoardFreezee.SinCongeladar","&CSin congelar: "},
		   {"BoardFreezee.Congelados","&BCongelados: "},
		   {"BoardFreezee.finish","&AFinaliza en:"},
		   
   };
}