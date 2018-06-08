/**
 * 
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import business.model.HealthOrganization;
import business.model.HealthSituation;
import business.model.HealthSubject;
import business.service.ConcreteListService;
import business.service.ConcreteStatisticService;
import business.service.ListService;
import business.service.StatisticService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import persistence.DaoFactory;
import persistence.DaoFactoryJDBC;
import persistence.exception.DatabaseException;

/**
 * @author clah
 *
 */
public class StatisticScreenTest extends Application {

	private static final Logger LOGGER = Logger.getLogger(StatisticScreenTest.class);
	private static final int GRAFICO_ALTURA = 300;
	private static final int GRAFICO_LARGURA = 300;
	private StatisticService statisticService;
	private ListService listService;
	private ObservableList<String> observableListMeses = FXCollections.observableArrayList();
	
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage s) throws Exception {
	     DaoFactory daoFactory = new DaoFactoryJDBC();
		 statisticService = new ConcreteStatisticService(daoFactory);
		 listService = new ConcreteListService(
                HealthOrganization.getAll(),
                HealthSubject.getAll(),
                HealthSituation.getAll());
			
		s.setScene(new Scene(new FlowPane(createQuantityProcessPerSituationPieChart(),
				criarGraficoLinha(), createBarChartQuantityProcessPerMonthYear())));
		s.setTitle("Gráficos básicos do DocManager");
		s.setWidth(910);
		s.setHeight(350);
		s.show();

	}

	private Node createQuantityProcessPerSituationPieChart() {
		PieChart pieChart = new PieChart();
		
		 Map<Integer, Integer> dados = null;
			try {
				dados = statisticService.quantityProcessPerSituation();
			} catch (DatabaseException e) {
				LOGGER.error(e.getMessage(), e);
			}
			
			if(dados == null || dados.isEmpty()) {
				System.out.println("ta dando ruim!");
			}
			
			Iterator<Entry<Integer, Integer>> it = dados.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Integer, Integer> pair = (Map.Entry<Integer, Integer>)it.next();
				
				int situacaoId = Integer.parseInt( pair.getKey().toString() );
				String situationName = listService.getSituationDescritionById(situacaoId);
				double quantity = Double.parseDouble(pair.getValue().toString());
				
				Data slice = new PieChart.Data(situationName,quantity);
				
				System.out.println(pair.getKey() + " = " + pair.getValue());
				pieChart.getData().add(slice);
				it.remove(); // avoids a ConcurrentModificationException
			 }
			
			
			pieChart.setTitle("Lucros por Trimestre");
			pieChart.setPrefSize(GRAFICO_LARGURA, GRAFICO_ALTURA);
			return pieChart;
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Node criarGraficoLinha() {
		LineChart<String, Number> graficoLinha = new LineChart<>(
				new CategoryAxis(), new NumberAxis());
		final String T1 = "T1";
		final String T2 = "T2";
		final String T3 = "T3";
		final String T4 = "T4";

		XYChart.Series prod1 = new XYChart.Series();
		prod1.setName("Produto 1");

		prod1.getData().add(new XYChart.Data(T1, 5));
		prod1.getData().add(new XYChart.Data(T2, -2));
		prod1.getData().add(new XYChart.Data(T3, 3));
		prod1.getData().add(new XYChart.Data(T4, 15));

		XYChart.Series prod2 = new XYChart.Series();
		prod2.setName("Produto 2");

		prod2.getData().add(new XYChart.Data(T1, -5));
		prod2.getData().add(new XYChart.Data(T2, -1));
		prod2.getData().add(new XYChart.Data(T3, 12));
		prod2.getData().add(new XYChart.Data(T4, 8));

		XYChart.Series prod3 = new XYChart.Series();
		prod3.setName("Produto 3");

		prod3.getData().add(new XYChart.Data(T1, 12));
		prod3.getData().add(new XYChart.Data(T2, 15));
		prod3.getData().add(new XYChart.Data(T3, 12));
		prod3.getData().add(new XYChart.Data(T4, 20));
		graficoLinha.getData().addAll(prod1, prod2, prod3);
		graficoLinha.setPrefSize(GRAFICO_LARGURA, GRAFICO_ALTURA);
		return graficoLinha;
	}

	@SuppressWarnings({ })
	private Node createBarChartQuantityProcessPerMonthYear() {
		// Obtém an array com nomes dos meses em Inglês.
        String[] arrayMeses = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        // Converte o array em uma lista e adiciona em nossa ObservableList de meses.
        observableListMeses.addAll(Arrays.asList(arrayMeses));
     // Associa os nomes de mês como categorias para o eixo horizontal.
        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.setCategories(observableListMeses);
        
        BarChart<String, Number> barChart = new BarChart<>(categoryAxis, new NumberAxis());
        barChart.setTitle("Quantidade de Processos");
        Map<Integer, ArrayList<Integer>> dados = null;
		try {
			dados = statisticService.quantityProcessPerMonthYear();
		} catch (DatabaseException e) {
			LOGGER.error(e.getMessage(), e);
		}
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
   
        barChart.setPrefSize(GRAFICO_LARGURA, GRAFICO_ALTURA);
		return barChart;
	}
	
	public String retornaNomeMes(int mes) {
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
}
