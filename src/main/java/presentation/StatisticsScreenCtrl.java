package presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import java.util.ResourceBundle;

import business.model.Organization;
import business.model.Situation;
import business.model.Subject;
import business.service.StatisticService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import persistence.exception.DatabaseException;
import presentation.utils.StringConstants;

/**
 * @author clah
 * @since 04.20.2018
 */
public class StatisticsScreenCtrl implements Initializable {

	private static final URL FXML_PATH = PdfViewerCtrl.class.getResource("/visions/statistics_screen.fxml");
	private static final Logger LOGGER = Logger.getLogger(StatisticsScreenCtrl.class);

	private StatisticService statisticService;
	
	private ObservableList<String> observableMonthsList = FXCollections.observableArrayList();
	private ObservableList<String> observableMonthsList2 = FXCollections.observableArrayList();

	@FXML
	private Node root;

	//First Tab
	@FXML
	private BarChart<String, Number> barChartMonthYear;
	@FXML
	private CategoryAxis categoryAxisMonthYear;

	//Second tab
	@FXML
	private BarChart<String, Number> barChartLastYear;
	@FXML
	private CategoryAxis categoryAxisLastYear;

	//Third tab
	@FXML
	private PieChart pieChart;
	
	public StatisticsScreenCtrl(StatisticService statisticService) {
		this.statisticService = statisticService;
	}
	
	public static void showStatisticsScreen(Window ownerWindow, StatisticsScreenCtrl controller) {
		try {
			FXMLLoader loader = new FXMLLoader(FXML_PATH);
			loader.setController(controller);
			Parent rootParent = loader.load();

			Stage statisticsScreen = new Stage();
			statisticsScreen.setTitle(StringConstants.TITLE_PDF_VIEWER_SCREEN.getText());
			statisticsScreen.initModality(Modality.WINDOW_MODAL);
			statisticsScreen.initOwner(ownerWindow);
			statisticsScreen.setScene(new Scene(rootParent, 940, 610));
			
			statisticsScreen.show();
		} catch (IOException e) {
			//TODO Alert Erro de geração de tela
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createChartQntPerMonthAndYear();
		createChartQntLastYear();
		createPieChartSubject();
	}

	private void createChartQntPerMonthAndYear(){
		/* Converte o array em uma lista e adiciona em nossa ObservableList de meses.*/
		observableMonthsList.addAll(Arrays.asList(Month.getAll()));
		/* Associa os nomes de mês como categorias para o eixo horizontal. */        
		categoryAxisMonthYear.setCategories(observableMonthsList);

		Map<Integer, ArrayList<Integer>> dados = null;
		try {
			dados = statisticService.quantityProcessPerMonthYear();
		} catch (DatabaseException e) {
			//TODO fazer um alert e tratar
			LOGGER.error(e.getMessage(), e);
		}

		if(dados == null || dados.isEmpty()) {
			//TODO fazer um alert e tratar
		} else {
			for (Entry<Integer, ArrayList<Integer>> itemData : dados.entrySet()) {
				XYChart.Series<String, Number> series = new XYChart.Series<>();
				series.setName(itemData.getKey().toString());
				for (int i = 0; i < itemData.getValue().size(); i = i + 2) {
					String month;
					Number quantity;
					month = Month.getName((int) itemData.getValue().get(i));
					quantity = itemData.getValue().get(i + 1);
					series.getData().add(new XYChart.Data<>(month, quantity));
				}
				barChartMonthYear.getData().add(series);
			}
		}
	}

	private void createChartQntLastYear() {

		// Converte o array em uma lista e adiciona em nossa ObservableList de meses.
		observableMonthsList2.addAll(this.getMonthList(Calendar.getInstance()));
		// Associa os nomes de mês como categorias para o eixo horizontal.        
		categoryAxisLastYear.setCategories(observableMonthsList2);

		Map<Integer, ArrayList<Integer>> dados = null;
		try {
			dados = statisticService.quantityProcessFromLastYear();
		} catch (DatabaseException e) {
			//TODO fazer um alert e tratar
			LOGGER.error(e.getMessage(), e);
		}

		if(dados == null || dados.isEmpty()) {
			//TODO fazer um alert e tratar
		} else {
	
			for (Entry<Integer, ArrayList<Integer>> itemData : dados.entrySet()) {
				XYChart.Series<String, Number> series = new XYChart.Series<>();
				series.setName(itemData.getKey().toString());
				for (int i = 0; i < itemData.getValue().size(); i = i + 2) {
					String month;
					Number quantity;
					month = Month.getName((int) itemData.getValue().get(i));
					quantity = itemData.getValue().get(i + 1);
					series.getData().add(new XYChart.Data<>(month, quantity));
				}
				barChartLastYear.getData().add(series);
			}
		}

	}

	private List<String> getMonthList(Calendar currentDate){
		List<String> monthList = new ArrayList<>();

		Deque<String> temporaryMonthDeque = new LinkedList<>();

		//Add the current month value in the stack
		int currentMonthValue = currentDate.get(Calendar.MONTH);
		temporaryMonthDeque.push(Month.getName(currentMonthValue+1));

		//Add the other 11 months
		for (int i = 0; i < 11; i++) {
			currentDate.add(Calendar.MONTH,-1);
			int monthValue = currentDate.get(Calendar.MONTH);
			temporaryMonthDeque.push(Month.getName(monthValue+1));
		}

		//Add in a list in the correct order
		for (int i = 0; i < 12; i++) {
			monthList.add(temporaryMonthDeque.pop());
		}
		
		return monthList;
	}

	@FXML
	private void createPieChartSituation() {
		try {
			pieChart.getData().clear(); //Clean data chart
			Map<Integer, Integer> dados = statisticService.quantityProcessPerSituation();
			this.createPieChart("Situação",dados);
		} catch (DatabaseException e) {
			// TODO Alert Banco de Dados
			LOGGER.error(e.getMessage(), e);
		}
	}

	@FXML
	private void createPieChartOrganization() {
		try {
			pieChart.getData().clear(); //Clean data chart
			Map<Integer, Integer> dados = statisticService.quantityProcessPerOrganization();
			this.createPieChart("Órgão",dados);
		} catch (DatabaseException e) {
			// TODO Alert Banco de Dados
			LOGGER.error(e.getMessage(), e);
		}
	}

	@FXML
	private void createPieChartSubject() {
		try {
			pieChart.getData().clear(); //Clean data chart
			Map<Integer, Integer> data = statisticService.quantityProcessPerSubject();
			this.createPieChart("Assunto",data);
		} catch (DatabaseException e) {
			// TODO Alert Banco de Dados
			LOGGER.error(e.getMessage(), e);
		}
	}

	@FXML
	private void closeWindow() {
		Stage janela = (Stage) root.getScene().getWindow();
		if (janela != null)
			janela.close();
	}


	private void createPieChart(String category, Map<Integer, Integer> data) {
		if(data == null || data.isEmpty()) {
			//TODO fazer um alert e tratar
		} else {
			pieChart.setLabelsVisible(false);
			pieChart.setTitle("Quantidade de Processos por "+category);
	
			Iterator<Entry<Integer, Integer>> it = data.entrySet().iterator();
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
	}

	private String getCategoryNameById(int categoryId, String category) {
		if(category.equalsIgnoreCase("Situação")){
			return Situation.getSituationById(categoryId).getStatus();
		}else if(category.equalsIgnoreCase("Órgão")){
			return Organization.getOrganizationById(categoryId).name();
		}else if(category.equalsIgnoreCase("Assunto")) {
			return Subject.getSubjectById(categoryId).getShortText();
		}else {
			return null;
		}
	}
	
	//http://acodigo.blogspot.com.br/2017/08/piechart-javafx.html
	public void installTooltip(PieChart.Data pcData) {

		String message = String.format("%s : %s", pcData.getName(), (int)pcData.getPieValue());

		Tooltip tolltip = new Tooltip(message);
		tolltip.setStyle("-fx-background-color: gray; -fx-text-fill: whitesmoke;");

		Tooltip.install(pcData.getNode(), tolltip);
	}

	protected static class Month {
		private static String[] names = {
				"Jan", "Fev", "Mar", "Abr", "Mai", "Jun",
				"Jul", "Ago", "Set", "Out", "Nov", "Dez"};

		private Month() {}

		protected static String getName(int order) {
			if (order > 0 && order <= 12) {
				return names[order -1];
			} else {
				return null;
			}
		}

		protected static String[] getAll() {
			return names;
		}
	}
}
