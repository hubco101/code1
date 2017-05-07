/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a3cortes.crawler;

/**
 *
 * @author cadrian
 */

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import java.io.IOException;
import java.util.List;
import org.w3c.dom.NamedNodeMap;

public class test {
    
    
    
    public static void main(String[] args) throws IOException {
     WebClient webClient = new WebClient();
     webClient.getOptions().setThrowExceptionOnScriptError(false);
     
     //Get first page of the city
     HtmlPage pageCiudad = webClient.getPage("http://inmuebles.mercadolibre.com.mx/venta/queretaro/queretaro/venta-queretaro");
     HtmlElement mas = pageCiudad.getFirstByXPath("//*[@id=\"otherFilters\"]");
     mas.click();
     
     List<HtmlElement> colonias = (List<HtmlElement>) pageCiudad.getByXPath("//*[@id=\"id_neighborhood\"]/dd");
        
     colonias.remove(colonias.size()-1);
     colonias.addAll((List<HtmlElement>)pageCiudad.getByXPath("//*[@id=\"id_neighborhood\"]/dd[10]/dl/dd"));
       
       for (HtmlElement e :colonias ){
           DomNodeList<HtmlElement> elementsByTagName = e.getElementsByTagName("a");
           String linkText=null;
            for (HtmlElement a  : elementsByTagName) {
                linkText = a.asText();
                
            }
        /// Get first page for the colonia
        System.out.println(linkText+"----------------------");
        HtmlAnchor link = pageCiudad.getAnchorByText(linkText);
        
        HtmlPage pageColonia = link.click();
        //System.out.println(pageColonia.asText());
        /// GET all pages
        HtmlAnchor linkSiguiente = null;
        do{ 
            linkSiguiente = null;
//            try {linkSiguiente =  pageColonia.getAnchorByText("Siguiente >");
//                pageColonia=linkSiguiente.click();
//            }catch(Exception ex){System.out.println("End of pages");}
            
            
            
            DomNodeList<DomElement> casas =  pageColonia.getElementsByTagName("a");
            System.out.println("tamaño lista" + casas.size());
            for (DomElement casa :casas ){
                System.out.println("CASA"+casa.asText());
                if(casa.asText().contains("item-link")){
                
               HtmlAnchor casaImg = (HtmlAnchor) casa;
               HtmlPage pageCasa = casaImg.click();
               //HtmlElement infoCasa= pageCasa.getFirstByXPath("/html/body/main/section/div[1]/div");
               System.out.println(pageCasa.asText());
                }
               
            }
        
        
        }while(linkSiguiente != null );
            
            
            
        
       
          
           
       }
    }
    
}
