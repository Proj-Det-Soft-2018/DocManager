/**
 * 
 */
package presentation;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import business.model.Situation;
import business.service.ConcreteStatisticService;
import business.service.StatisticService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.PieChart.Data;
import persistence.exception.DatabaseException;

/**
 * @author clah
 * @since 04.20.2018
 */
public class StatisticsGraphsScreenController implements Initializable {
	
	@FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis categoryAxis;
    @FXML
    private NumberAxis numberAxis;
	
    @FXML
    private PieChart pieChart;
    
    private ObservableList<String> observableListMeses = FXCollections.observableArrayList();
	private StatisticService statisticService;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		statisticService = ConcreteStatisticService.getInstance();
		try {
			this.createBarChartQuantityProcessPerMonthYear();
			this.createQuantityProcessPerSituationPieChart();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void createBarChartQuantityProcessPerMonthYear() throws DatabaseException {
		// Obtém an array com nomes dos meses em Inglês.
        String[] arrayMeses = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        // Converte o array em uma lista e adiciona em nossa ObservableList de meses.
        observableListMeses.addAll(Arrays.asList(arrayMeses));
     // Associa os nomes de mês como categorias para o eixo horizontal.        
        categoryAxis.setCategories(observableListMeses);
       
        Map<Integer, ArrayList<Integer>> dados = statisticService.quantityProcessPerMonthYear();
		
		if(dados == null || dados.isEmpty()) {
			System.out.println("ta dando ruim!");
		}
		
		
        for (Entry<Integer, ArrayList<Integer>> dadosItem : dados.entrySet()) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(dadosItem.getKey().toString());
            for (int i = 0; i < dadosItem.getValue().size(); i = i + 2) {
                String mes;
                Number quantidade;
                mes = retornaNomeMes((int) dadosItem.getValue().get(i));
                quantidade = (Number) dadosItem.getValue().get(i + 1);
                series.getData().add(new XYChart.Data<>(mes, quantidade));
            }
            barChart.getData().add(series);
        }
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
	
	private void createQuantityProcessPerSituationPieChart() throws DatabaseException {
	//	PieChart pieChart = new PieChart();
		
		 Map<Integer, Integer> dados = statisticService.quantityProcessPerSituation();
			
			
			if(dados == null || dados.isEmpty()) {
				System.out.println("ta dando ruim!");
			}
			
			Iterator<Entry<Integer, Integer>> it = dados.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Integer, Integer> pair = (Map.Entry<Integer, Integer>)it.next();
				
				int situacaoId = Integer.parseInt( pair.getKey().toString() );
				String situationName = Situation.getSituacaoPorId(situacaoId).getStatus();
				double quantity = Double.parseDouble(pair.getValue().toString());
				
				Data slice = new PieChart.Data(situationName,quantity);
				
				System.out.println(pair.getKey() + " = " + pair.getValue());
				pieChart.getData().add(slice);
				it.remove(); // avoids a ConcurrentModificationException
			 }
					
	}

}
