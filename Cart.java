package FinalPOS;



import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Cart extends Application {
	Model model = new Model();
	View view = new View();

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		model.loadData();		//this will load itemsObservableList from csv file
		Scene scene = new Scene(view.setupScene(), 1500, 900);  
		setupActions();
		primaryStage.setScene(scene);
		primaryStage.setTitle("Coffee Shop");
		primaryStage.show();


	}

	ObjectBinding<Item> itemBinding = new ObjectBinding<Item>() {
		{
			super.bind(view.itemsComboBox.valueProperty());
		}
		@Override
		protected Item computeValue() {
			Item item; 
			if (view.itemsComboBox.getSelectionModel().getSelectedIndex() >= 0) 
				item = view.itemsComboBox.getSelectionModel().selectedItemProperty().get();
			else item = new Item();
			view.quantitySlider.setValue(0);
			return item;
		}
	};


	void setupActions() {
		//bind itemsTableView with data
		view.itemsTableView.setItems(model.cartObservableList);
		view.itemsComboBox.setItems(model.itemsObservableList); 

		//bind purchase units label with slider value using Fluent API
		view.purchasedUnitsValueLabel.textProperty().bind(Bindings.format("%.0f", view.quantitySlider.valueProperty()));

		//selecting item in itemsComboBox updates unit quantity and unit price labels

		view.unitValueLabel.textProperty().bind(Bindings.format("%.2f %s", Bindings.select(itemBinding, "unitQuantity"), Bindings.select(itemBinding, "unit")));
		view.unitPriceValueLabel.textProperty().bind(Bindings.format("$ %.2f", Bindings.select(itemBinding, "unitPrice")));

		//provide cell value factory for Price column in itemsTableView
		Callback<CellDataFeatures<ItemInCart, Double>, ObservableValue<Double>> callback = new Callback<CellDataFeatures<ItemInCart, Double>, ObservableValue<Double>>() {
			@Override
			public ObservableValue<Double> call(CellDataFeatures<ItemInCart, Double> param) {
				for(Item item : model.itemsObservableList) {
					if (item.getName().equalsIgnoreCase(param.getValue().getName())) {
						return (new SimpleDoubleProperty(param.getValue().getQuantity() * item.getUnitPrice())).asObject();
					}
				}
				return null;
			}
		};

		//set rate columnn's call-back for factory method
		view.rateColumn.setCellValueFactory(callback);

		//event handler for add button creates new ItemInCart with currently selected item'name and purchase qty 
		//and adds it to cartObservableList
		//it also moves the table selection to the last item just added

		view.auLaitButton.setOnAction(event -> {
			
			Item item = new Item("Cafe au Latte","Cup",1.0,2.5);
			view.menuList.add(item);
			
			for(int i=0; i<view.menuList.size();i++) {
				Item currentItem = view.menuList.get(i);
				ItemInCart shoppingCartItem = new ItemInCart(currentItem.getName(), view.quantitySlider.getValue());
				model.cartObservableList.add(shoppingCartItem);
				
			}		
		
		});
		
		view.frenchPressButton.setOnAction((event) -> {
			Item item = new Item("Frecch Press","Cup",1.0,3.0);
			view.menuList.add(item);
			
			for(int i=0; i<view.menuList.size();i++) {
				Item currentItem = view.menuList.get(i);
				ItemInCart shoppingCartItem = new ItemInCart(currentItem.getName(), view.quantitySlider.getValue());
				model.cartObservableList.add(shoppingCartItem);
				
			}		
		});

		//totalBinding to add all cell values in the price column of table view
		
		DoubleBinding totalBinding = new DoubleBinding() {
			{
				super.bind(model.cartObservableList);
			}

			@Override
			protected double computeValue() {
				double total = 0;
				if (model.cartObservableList.size() > 0)
					for (int row = 0; row < model.cartObservableList.size(); row++)
						total += (double)view.itemsTableView.getColumns().get(2).getCellObservableValue(row).getValue();
				return total;
			}
			
		};

		//bind totalValueLabel's textProperty to totalBinding
		view.totalValueLabel.textProperty().bind(Bindings.format("%.2f", totalBinding));


		//listener for item-selection in itemsTableView updates the image, item in combo-box, and qty in quantitySlider
		//it looks for the selected item in the tableView in itemsObservableList, sets itemComboBox to that index, and
		//quantitySlider to the newValue's quantity.
		view.itemsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				int index = 0;
				for (Item item : model.itemsObservableList) {
					if (item.getName().equals(newValue.getName())) { 
						view.itemsComboBox.getSelectionModel().select(index);
						view.quantitySlider.setValue(newValue.getQuantity());
						break;
					}
					index++;
				}
			}
		});
		
		//event handler for remove button 
		//checks the current selected index 
		//if it is >= 0, it removes the item at that index from cartObservableList
		view.removeButton.setOnAction(event -> {
			int index = view.itemsTableView.getSelectionModel().getFocusedIndex();
			if (model.cartObservableList.size() > index) 
				model.cartObservableList.remove(index);
				view.menuList.remove(index);
			if (model.cartObservableList.size() == 0) view.itemsComboBox.getSelectionModel().clearSelection();
		});
		
		view.coffeeButton.setOnAction(event -> {
			
			view.brewedButton.setVisible(true);
			view.auLaitButton.setVisible(true);
			view.frenchPressButton.setVisible(true);
			view.icedCoffeeButton.setVisible(true);

			
			view.blackTeaButton.setVisible(false);
			view.greenTeaButton.setVisible(false);
			view.herbalTeaButton.setVisible(false);
			view.oolongTeaButton.setVisible(false);
			view.chaiButton.setVisible(false);
			view.hotChocalateButton.setVisible(false);
			view.lemonadeButton.setVisible(false);
			view.fruitSmothieButton.setVisible(false);
			view.orangeJuiceButton.setVisible(false);
			view.espressButton.setVisible(false);
			view.macchiatoButton.setVisible(false);
			view.conPannaButton.setVisible(false);
			view.cafeLatteButton.setVisible(false);
			view.classicCapButton.setVisible(false);
			view.cappuccinoButton.setVisible(false);
			view.mochaButton.setVisible(false);
			view.caramelButton.setVisible(false);
			view.vanillaButton.setVisible(false);
			view.mielButton.setVisible(false);
			view.americanoButton.setVisible(false);
			view.muffinButton.setVisible(false);
			view.croissantButton.setVisible(false);
			view.bagelButton.setVisible(false);
			view.cinnamonButton.setVisible(false);
			view.tartButton.setVisible(false);
			view.eclairButton.setVisible(false);
			view.puffButton.setVisible(false);


		});
		
		view.noncoffeeButton.setOnAction(event -> {
			
			view.blackTeaButton.setVisible(true);
			view.greenTeaButton.setVisible(true);
			view.herbalTeaButton.setVisible(true);
			view.oolongTeaButton.setVisible(true);
			view.chaiButton.setVisible(true);
			view.hotChocalateButton.setVisible(true);
			view.lemonadeButton.setVisible(true);
			view.fruitSmothieButton.setVisible(true);
			view.orangeJuiceButton.setVisible(true);
			
			view.brewedButton.setVisible(false);
			view.auLaitButton.setVisible(false);
			view.frenchPressButton.setVisible(false);
			view.icedCoffeeButton.setVisible(false);
			view.espressButton.setVisible(false);
			view.macchiatoButton.setVisible(false);
			view.conPannaButton.setVisible(false);
			view.cafeLatteButton.setVisible(false);
			view.classicCapButton.setVisible(false);
			view.cappuccinoButton.setVisible(false);
			view.mochaButton.setVisible(false);
			view.caramelButton.setVisible(false);
			view.vanillaButton.setVisible(false);
			view.mielButton.setVisible(false);
			view.americanoButton.setVisible(false);
			view.muffinButton.setVisible(false);
			view.croissantButton.setVisible(false);
			view.bagelButton.setVisible(false);
			view.cinnamonButton.setVisible(false);
			view.tartButton.setVisible(false);
			view.eclairButton.setVisible(false);
			view.puffButton.setVisible(false);


		});
		
		view.espressoButton.setOnAction(event -> {
			
			view.espressButton.setVisible(true);
			view.macchiatoButton.setVisible(true);
			view.conPannaButton.setVisible(true);
			view.cafeLatteButton.setVisible(true);
			view.classicCapButton.setVisible(true);
			view.cappuccinoButton.setVisible(true);
			view.mochaButton.setVisible(true);
			view.caramelButton.setVisible(true);
			view.vanillaButton.setVisible(true);
			view.mielButton.setVisible(true);
			view.americanoButton.setVisible(true);
			
			view.brewedButton.setVisible(false);
			view.auLaitButton.setVisible(false);
			view.frenchPressButton.setVisible(false);
			view.icedCoffeeButton.setVisible(false);
			view.blackTeaButton.setVisible(false);
			view.greenTeaButton.setVisible(false);
			view.herbalTeaButton.setVisible(false);
			view.oolongTeaButton.setVisible(false);
			view.chaiButton.setVisible(false);
			view.hotChocalateButton.setVisible(false);
			view.lemonadeButton.setVisible(false);
			view.fruitSmothieButton.setVisible(false);
			view.orangeJuiceButton.setVisible(false);
			view.muffinButton.setVisible(false);
			view.croissantButton.setVisible(false);
			view.bagelButton.setVisible(false);
			view.cinnamonButton.setVisible(false);
			view.tartButton.setVisible(false);
			view.eclairButton.setVisible(false);
			view.puffButton.setVisible(false);

		});
		
		view.pastriesButton.setOnAction(event -> {
			
			view.muffinButton.setVisible(true);
			view.croissantButton.setVisible(true);
			view.bagelButton.setVisible(true);
			view.cinnamonButton.setVisible(true);
			view.tartButton.setVisible(true);
			view.eclairButton.setVisible(true);
			view.puffButton.setVisible(true);
			
			view.brewedButton.setVisible(false);
			view.auLaitButton.setVisible(false);
			view.frenchPressButton.setVisible(false);
			view.icedCoffeeButton.setVisible(false);
			view.blackTeaButton.setVisible(false);
			view.greenTeaButton.setVisible(false);
			view.herbalTeaButton.setVisible(false);
			view.oolongTeaButton.setVisible(false);
			view.chaiButton.setVisible(false);
			view.hotChocalateButton.setVisible(false);
			view.lemonadeButton.setVisible(false);
			view.fruitSmothieButton.setVisible(false);
			view.orangeJuiceButton.setVisible(false);
			view.blackTeaButton.setVisible(false);
			view.greenTeaButton.setVisible(false);
			view.herbalTeaButton.setVisible(false);
			view.oolongTeaButton.setVisible(false);
			view.chaiButton.setVisible(false);
			view.hotChocalateButton.setVisible(false);
			view.lemonadeButton.setVisible(false);
			view.fruitSmothieButton.setVisible(false);
			view.orangeJuiceButton.setVisible(false);
			view.espressButton.setVisible(false);
			view.macchiatoButton.setVisible(false);
			view.conPannaButton.setVisible(false);
			view.cafeLatteButton.setVisible(false);
			view.classicCapButton.setVisible(false);
			view.cappuccinoButton.setVisible(false);
			view.mochaButton.setVisible(false);
			view.caramelButton.setVisible(false);
			view.vanillaButton.setVisible(false);
			view.mielButton.setVisible(false);
			view.americanoButton.setVisible(false);


		});
		
			view.brewedButton.setOnAction(event ->{

			 
            Scene secondScene = new Scene(view.secondaryLayout(), 500, 300);

            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Second Stage");
            newWindow.setScene(secondScene);

            newWindow.show();

		});
		
	}
}
