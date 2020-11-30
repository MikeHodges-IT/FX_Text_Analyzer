package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
* <h1>Controller</h1>
* The controller event handler methods declared as public 
* so they can be set or invoked by the loader
* 
* <p>
* <b>Note:</b> The Controller class contains the following methods:
*  public void initialize()
*  void onActionBtn(ActionEvent event)
*  void onMouseDrag(MouseEvent event)
*  void HideALl() 
*  
* @author  Mike Hodges
* @version 1.0
* @since   2020-11-15
*/

public class Controller {
	/**
	* Is a string that stores name of the button that is clicked.
	* It is then used as the switch in a case statement. 
	*/
	String str;
	/**
	*Is a string that stores the url of the web page used for the word count.  
	*/
	String urlStr = null;
	/**
	* Is a string that stores the the selected text.  
	*/
	String selection;


	@FXML
    private WebView infoWeb;
			  
    @FXML
    private SplitPane splitHelp;
    
    @FXML
    private Button btnHelp;

    @FXML
    private Button btnChart;

    @FXML
    private Button btnSelect;

    @FXML
    private Button btnStart;
    
    @FXML
    private Button btnFormSelect;

    @FXML
    private Button btnAbout;

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private TextArea textarea1;

    @FXML
    private WebView webView;

    @FXML
    private AnchorPane urlAnchor;

    @FXML
    private TextField urlTxt;


    @FXML
    private AnchorPane infoWebAnc;
 
  
 
 /**
  * Is run first and is used to set up the initial setting.
  * Set the urlStr to "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm" 
  * as the default
  * Load the initial HTML instruction to the user.
  * Set the visibility of the various elements. 
  */
  @FXML 
 public void initialize(){
 final WebEngine webEngine = infoWeb.getEngine();   	 
webEngine.loadContent(
		  "<Center><H1><b>FX Text Analyzer</b></H1></Center>"
		+ " Enter a URL or use the default URL below. <BR>"
		+ " Then click the Select button below.<BR>");
urlStr = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
urlTxt.setText(urlStr);
btnSelect.setVisible(false);
btnChart.setVisible(false);
 
 }

 
 
 /**
 * onActionBtn runs every time any of the buttons are clicked.
 * 
 * The buttons name is striped from the event and used as a switch in the case statement.
 * The case statement then show or hide various elements. 
 * The case statement calls TextAnalyzer LinkedHashMap String, Long htmlStringToFreqMap(String str)
 * and converts the output to a chart.
 * 
 * This should be cleaned up at a future date by adding a few methods.
 *   
 *   
 *   
 * @param event  Takes a button click event
 * 
 */ 
 @FXML 
     void onActionBtn(ActionEvent event) {
	    final WebEngine webEngine = infoWeb.getEngine();  
	    /**
	     * str =  the event string after spiting and separating out the button name.     
	     */
    	str = event.getSource().toString().substring(event.getSource().toString().indexOf("=")+1, event.getSource().toString().indexOf(","));
    	
    	System.out.println(str + " Clicked");
    	System.out.println(event.getSource().toString());
    	System.out.println(splitHelp.toString());


     	   
    	switch(str) {
    	case "btnStart":

    		HideAll();
    		webEngine.loadContent(
    				  "<Center><H1><b>FX Text Analyzer</b></H1></Center>"
    				+ " Enter a URL or use the default URL below. <BR>"
    				+ " Then click the select button below. <BR>");
    		urlStr = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
    		urlAnchor.setVisible(true);
    		break;
    		
    	case "btnFormSelect":
    		HideAll();
    		webEngine.loadContent(
  				  "<Center><H1><b>FX Text Analyzer</b></H1></Center>"
  				+ " Select the text to be counted.  <BR>"
  				+ " After selection is made click Select text button that will appear above. <BR>");
    			urlStr = urlTxt.getText();
    			webView.setVisible(true);
    			webView.getEngine().load(urlStr);     	    
    		break;
    	case "btnSelect":
    		HideAll();
    		webEngine.loadContent(
  				  "<Center><H1><b>FX Text Analyzer</b></H1></Center>"
  				+ " Approve your selection and click Graph Data button above.  <BR>"
  				+ " Or click select the Select URL button above to start over <BR>"
  				);
    		if (webView != null) {
            	selection = (String) webView.getEngine().executeScript("window.getSelection().toString()");
            	textarea1.setText(selection);
            }
        	   textarea1.setVisible(true);
   	     	   //tempString = selection;
   	     	   btnSelect.setVisible(false);
   	     	   btnChart.setVisible(true);
    		
    		break;
    	case "btnChart":
    		HideAll();
       		webEngine.loadContent(
  				  "<Center><H1><b>FX Text Analyzer</b></H1></Center>"
  				+ " View charted results. <BR>"
  				+ " Then click the Select URL button to start again. <BR>"
  				);
    		barChart.getData().clear();
            XYChart.Series dataSeries1 = new XYChart.Series();
            dataSeries1.setName("2014");

//            TextAnalyzer.htmlStringToFreqMap(selection)
            Javadb j = new Javadb();
//            System.out.println(j.sqlCountToHashMap(selection));
            System.out.println("hello world");
            j.sqlCountToHashMap(selection)
            .entrySet()
    		.stream().forEach(e -> { 
    				dataSeries1.getData().add(new XYChart.Data(e.getKey(),e.getValue()));    				       
    				});
    		
    		System.out.println(str + " Clicked");
    		barChart.getData().add(dataSeries1);
    		barChart.setVisible(true);

    		break;
    	case "btnHelp":
    		textarea1.appendText(str + " Clicked");
    	    if(infoWebAnc.isVisible()) {
    	    	btnHelp.setText("View Help");
    	    	infoWebAnc.setVisible(false);
    	        splitHelp.setDividerPositions(0.0);
    	    }else{
    	    	btnHelp.setText("Hide Help");
    	    	infoWebAnc.setVisible(true);
    	    	splitHelp.setDividerPositions(0.16);
    	    }
    	    
    		break;
    	case "btnAbout":
    		HideAll();
   		if(infoWebAnc.isVisible() == false) {
	    	btnHelp.setText("Hide Help");
	    	infoWebAnc.setVisible(true);
	    	splitHelp.setDividerPositions(0.16);   			
   		} 
    	webEngine.loadContent(		
		  "<Center><H1><b>FX Text Analyzer</b></H1></Center>"
		+ " Created by Mike Hodges <BR>"
		+ " For Assignment Six <BR>"
		+ " ");
		break;
    		    	
    	} 
    }
 @FXML
 /**
 * onMouseDrag runs every time the mouse is dragged across the html highlighting text.
 * 
 * The select text is confirmed by user then counted.  
 *   
 *   
 * @param event  Takes a mouse drag event
 * 
 */ 
 void onMouseDrag(MouseEvent event) {
	 btnSelect.setVisible(true);
	 final WebEngine webEngine = infoWeb.getEngine();
	 webEngine.loadContent( "<Center><H1><b>FX Text Analyzer</b></H1></Center>"
		+ " Click Select Text above.<BR>"
		+ " <BR>");
 }

 /**
 * 
 * HideAll resets everything by setting all element to not visible.
 * 
 */ 
 void HideAll() {

    barChart.setVisible(false);
	textarea1.setVisible(false);
	urlAnchor.setVisible(false);
	webView.setVisible(false);
	btnSelect.setVisible(false);
	btnChart.setVisible(false);
	
}


}