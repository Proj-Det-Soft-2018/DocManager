/**
 * 
 */
package presentation;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Stack;

import business.model.Organization;
import business.model.Situation;
import business.model.Subject;
import business.service.ConcreteStatisticService;
import business.service.StatisticService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import persistence.exception.DatabaseException;

/**
 * @author clah
 * @since 04.20.2018
 */
public class StatisticsGraphsScreenController implements Initializable {
	//First Tab
	@FXML
    private BarChart<String, Number> barChartMonthYear;
    @FXML
    private CategoryAxis categoryAxisMonthYear;
    @FXML
    private NumberAxis numberAxisMonthYear;
    //Second tab
    @FXML
    private BarChart<String, Number> barChartLastYear;
    @FXML
    private CategoryAxis categoryAxisLastYear;
    @FXML
    private NumberAxis numberAxisLastYear;
	//Third Tab
    @FXML
    private RadioButton radioBtnSubject;
    @FXML
    private RadioButton radioBtnOrganization;
    @FXML
    private RadioButton radioBtnSituation;
    
    @FXML
    private PieChart pieChart;
	
    final ToggleGroup tgCategoryGroup = new ToggleGroup();
	
    private static final String NULL_POINT_PROCESSOS = "Não existe processos cadastrados";
    private ObservableList<String> observableMonthsList = FXCollections.observableArrayList();
    private ObservableList<String> observableMonthsList2 = FXCollections.observableArrayList();
	private StatisticService statisticService;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		statisticService = ConcreteStatisticService.getInstance();
		this.radioBtnsConfigure();
		
			this.createBarChartQuantityProcessPerMonthYear();
			this.createBarChartQuantityProcessFromLastYear();
			try {
				this.createPieChartSubject();
			} catch (DatabaseException e) {
				//TODO tem que ver para onde vai a mensagem, sugiro log.
			}
			
		
	}
	
	private void radioBtnsConfigure() {
	
		radioBtnSubject.setToggleGroup(tgCategoryGroup);
		radioBtnSubject.setUserData("sub");
		radioBtnSubject.setSelected(true);
		
		radioBtnOrganization.setToggleGroup(tgCategoryGroup);
		radioBtnOrganization.setUserData("org");
		
		radioBtnSituation.setToggleGroup(tgCategoryGroup);
		radioBtnSituation.setUserData("sit");
		
		
	}

	private void createBarChartQuantityProcessPerMonthYear(){
		// Obtém an array com nomes dos meses em Inglês.
        String[] arrayMeses = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        // Converte o array em uma lista e adiciona em nossa ObservableList de meses.
        observableMonthsList.addAll(Arrays.asList(arrayMeses));
        // Associa os nomes de mês como categorias para o eixo horizontal.        
        categoryAxisMonthYear.setCategories(observableMonthsList);
       
        Map<Integer, ArrayList<Integer>> dados = null;
		try {
			dados = statisticService.quantityProcessPerMonthYear();
		} catch (DatabaseException e) {
			//TODO tem que ver para onde vai a mensagem, sugiro log.
		}
		
        if(dados == null || dados.isEmpty()) {
			throw new NullPointerException(NULL_POINT_PROCESSOS);
		}		
		
        for (Entry<Integer, ArrayList<Integer>> itemData : dados.entrySet()) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(itemData.getKey().toString());
            for (int i = 0; i < itemData.getValue().size(); i = i + 2) {
                String month;
                Number quantity;
                month = retornaNomeMes((int) itemData.getValue().get(i));
                quantity = itemData.getValue().get(i + 1);
                series.getData().add(new XYChart.Data<>(month, quantity));
            }
            barChartMonthYear.getData().add(series);
        }
   	}
	
	private void createBarChartQuantityProcessFromLastYear() {
		
        // Converte o array em uma lista e adiciona em nossa ObservableList de meses.
        observableMonthsList2.addAll(this.getMonthList(Calendar.getInstance()));
        // Associa os nomes de mês como categorias para o eixo horizontal.        
        categoryAxisLastYear.setCategories(observableMonthsList2);
       
        Map<Integer, ArrayList<Integer>> dados = null;
		try {
			dados = statisticService.quantityProcessFromLastYear();
		} catch (DatabaseException e) {
			//TODO tem que ver para onde vai a mensagem, sugiro log.
		}
		
        if(dados == null || dados.isEmpty()) {
			throw new NullPointerException(NULL_POINT_PROCESSOS);
		}
		
		
        for (Entry<Integer, ArrayList<Integer>> itemData : dados.entrySet()) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(itemData.getKey().toString());
            for (int i = 0; i < itemData.getValue().size(); i = i + 2) {
                String month;
                Number quantity;
                month = retornaNomeMes((int) itemData.getValue().get(i));
                quantity = itemData.getValue().get(i + 1);
                series.getData().add(new XYChart.Data<>(month, quantity));
            }
            barChartLastYear.getData().add(series);
        }
       
   	}
	
	private List<String> getMonthList(Calendar currentDate){
		List<String> monthList = new ArrayList<>();
        
		Stack<String> temporaryMonthStack = new Stack<>();
        
        //Add the current month value in the stack
		int currentMonthValue = currentDate.get(Calendar.MONTH);
        temporaryMonthStack.push(retornaNomeMes(currentMonthValue+1));
        
		//Add the other 11 months
        for (int i = 0; i < 11; i++) {
        	currentDate.add(Calendar.MONTH,-1);
        	int monthValue = currentDate.get(Calendar.MONTH);
        	temporaryMonthStack.push(retornaNomeMes(monthValue+1));
		}
        
        //Add in a list in the correct order
        for (int i = 0; i < 12; i++) {
        	monthList.add(temporaryMonthStack.pop());
		}
        
        return monthList;
	}
	
	private String retornaNomeMes(int mes) {
        switch (mes) {
            case 1:
                return "Jan";
            case 2:
                return "Fev";
            case 3:
                return "Mar";
            case 4:
                return "Abr";
            case 5:
                return "Mai";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Ago";
            case 9:
                return "Set";
            case 10:
                return "Out";
            case 11:
                return "Nov";
            case 12:
                return "Dez";
            default:
                return "";
        }
    }
	
	@FXML
	private void createPieChartSituation() throws DatabaseException{
		pieChart.getData().clear(); //Clean data chart
		Map<Integer, Integer> dados = statisticService.quantityProcessPerSituation();
		this.createPieChart("Situação",dados);
	}
	
	@FXML
	private void createPieChartOrganization() throws DatabaseException{
		pieChart.getData().clear(); //Clean data chart
		Map<Integer, Integer> dados = statisticService.quantityProcessPerOrganization();
		this.createPieChart("Órgão",dados);
	}
	
	@FXML
	private void createPieChartSubject() throws DatabaseException{
		pieChart.getData().clear(); //Clean data chart
		Map<Integer, Integer> dados = statisticService.quantityProcessPerSubject();
		this.createPieChart("Assunto",dados);
	}
	
	
	private void createPieChart(String category, Map<Integer, Integer> dataMap) {
		if(dataMap == null || dataMap.isEmpty()) {
			throw new NullPointerException(NULL_POINT_PROCESSOS);
		}
		pieChart.setLabelsVisible(false);
		pieChart.setTitle("Quantidade de Processos por "+category);
		
		Iterator<Entry<Integer, Integer>> it = dataMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Integer> pair = it.next();
			
			int categoryId = Integer.parseInt( pair.getKey().toString() );
			String categoryName = this.getCategoryNameById(categoryId, category);
			double quantity = Double.parseDouble(pair.getValue().toString());
			
			Data slice = new PieChart.Data(categoryName,quantity);
			pieChart.getData().add(slice);
			
			it.remove(); // avoids a ConcurrentModificationException
		 }
		
			pieChart.getData().forEach(this::installTooltip);
		
	}

	private String getCategoryNameById(int categoryId, String category) {
		if(category.compareTo("Situação")==0){
			return Situation.getSituationById(categoryId).getStatus();
		}else if(category.compareTo("Órgão")==0){
			return Organization.getOrganizationById(categoryId).name();
		}else if(category.compareTo("Assunto")==0) {
			return Subject.getSubjectById(categoryId).getShortText();
		}else {
			return null;
		}
	}


	//http://acodigo.blogspot.com.br/2017/08/piechart-javafx.html
	public void installTooltip(PieChart.Data d) {

	    String msg = String.format("%s : %s", d.getName(), (int)d.getPieValue());
	
	    Tooltip tt = new Tooltip(msg);
	    tt.setStyle("-fx-background-color: gray; -fx-text-fill: whitesmoke;");
	    
	    Tooltip.install(d.getNode(), tt);
	}


}
