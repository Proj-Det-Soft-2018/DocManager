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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
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
        
    private static final String NULL_POINT_PROCESSOS = "Não existe processos cadastrados";
    private ObservableList<String> observableMonthsList = FXCollections.observableArrayList();
    private ObservableList<String> observableMonthsList2 = FXCollections.observableArrayList();
	private StatisticService statisticService;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		statisticService = ConcreteStatisticService.getInstance();
		this.radioBtnsConfigure();
		try {
			this.createBarChartQuantityProcessPerMonthYear();
			this.createBarChartQuantityProcessFromLastYear();
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void radioBtnsConfigure() {
		final ToggleGroup tgGroup = new ToggleGroup();
		
		radioBtnOrganization.setToggleGroup(tgGroup);
		radioBtnOrganization.setUserData("org");
		
		radioBtnSubject.setToggleGroup(tgGroup);
		radioBtnSubject.setUserData("sub");
		
		radioBtnSituation.setToggleGroup(tgGroup);
		radioBtnSituation.setSelected(true);
		radioBtnSituation.setUserData("sit");
		
		
		
		tgGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
		        Toggle old_toggle, Toggle new_toggle) {
		            if (tgGroup.getSelectedToggle() != null) {
		            	this.radioBtnAction(tgGroup.getSelectedToggle().getUserData().toString());
		            	
		               
		            }                
		        }

			private void radioBtnAction(String btnName) {
				if(btnName.compareTo("sit")==0) {
					System.out.println("situação!");
				}else if (btnName.compareTo("org")==0) {
					System.out.println("orgaaao");
				} else if(btnName.compareTo("sub")==0){
					System.out.println("subjeeect");
				}else {
					System.out.println("deu ruim");
				}
				
			}
		});
		
	}

	private void createBarChartQuantityProcessPerMonthYear() throws DatabaseException {
		// Obtém an array com nomes dos meses em Inglês.
        String[] arrayMeses = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        // Converte o array em uma lista e adiciona em nossa ObservableList de meses.
        observableMonthsList.addAll(Arrays.asList(arrayMeses));
        // Associa os nomes de mês como categorias para o eixo horizontal.        
        categoryAxisMonthYear.setCategories(observableMonthsList);
       
        Map<Integer, ArrayList<Integer>> dados = statisticService.quantityProcessPerMonthYear();
		
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
	
	private void createBarChartQuantityProcessFromLastYear() throws DatabaseException {
		
        // Converte o array em uma lista e adiciona em nossa ObservableList de meses.
        observableMonthsList2.addAll(this.getMonthList(Calendar.getInstance()));
        // Associa os nomes de mês como categorias para o eixo horizontal.        
        categoryAxisLastYear.setCategories(observableMonthsList2);
       
        Map<Integer, ArrayList<Integer>> dados = statisticService.quantityProcessFromLastYear();
		
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
		
	}

	private String getCategoryNameById(int categoryId, String category) {
		if(category.compareTo("Situação")==0){
			return Situation.getSituacaoPorId(categoryId).getStatus();
		}else if(category.compareTo("Órgão")==0){
			return Organization.getOrgaoPorId(categoryId).name();
		}else if(category.compareTo("Assunto")==0) {
			return Subject.getAssuntoPorId(categoryId).getShortText();
		}else {
			return null;
		}
	}

	private void createQuantityProcessPerSituationPieChart() throws DatabaseException {
		
		 Map<Integer, Integer> dados = statisticService.quantityProcessPerSituation();
			
			
			if(dados == null || dados.isEmpty()) {
				throw new NullPointerException(NULL_POINT_PROCESSOS);
			}
			
			Iterator<Entry<Integer, Integer>> it = dados.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Integer, Integer> pair = it.next();
				
				int situacaoId = Integer.parseInt( pair.getKey().toString() );
				String situationName = Situation.getSituacaoPorId(situacaoId).getStatus();
				double quantity = Double.parseDouble(pair.getValue().toString());
				
				Data slice = new PieChart.Data(situationName,quantity);
				pieChart.getData().add(slice);
				it.remove(); // avoids a ConcurrentModificationException
			 }
					
	}

}
