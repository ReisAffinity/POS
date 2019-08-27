package FinalPOS;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class View {
	ComboBox<Item> itemsComboBox = new ComboBox<>();  //the drop down will show item-names from itemsMasterList
	Label unitValueLabel = new Label();
	Label unitPriceValueLabel = new Label();

	ObservableList<Item> menuList = FXCollections.observableArrayList();
	
	Slider quantitySlider = new Slider(0, 10, 0);  //min, max, current
	Label purchasedUnitsValueLabel = new Label("0");

	
	Button addButton = new Button("Add to Cart");
	Button removeButton = new Button("Remove from Cart");
	Button coffeeButton = new Button("Coffee");
	Button noncoffeeButton = new Button("Non-Coffee");
	Button espressoButton = new Button("Espresso");
	Button pastriesButton = new Button("Pastries");
	Button brewedButton = new Button("Brewed Coffee");
	Button auLaitButton = new Button("Cafe au latte");
	Button frenchPressButton = new Button("French press");
	Button icedCoffeeButton = new Button("Iced coffee");
	Button blackTeaButton = new Button("Black Tea");
	Button greenTeaButton = new Button("Green Tea");
	Button herbalTeaButton = new Button("Herbal Tea");
	Button oolongTeaButton = new Button("Oolong Tea");
	Button chaiButton = new Button("Chai");
	Button hotChocalateButton = new Button("Hor Chocalate");
	Button lemonadeButton = new Button("Lemonade");
	Button fruitSmothieButton = new Button("Fruit Smoothie");
	Button orangeJuiceButton = new Button("Orange Juice");
	Button espressButton = new Button("Espresso");
	Button macchiatoButton = new Button("Macchiato");
	Button conPannaButton = new Button("Con Pana");
	Button cafeLatteButton = new Button("Cafe Latte");
	Button classicCapButton = new Button("Classic Cappuccino");
	Button cappuccinoButton = new Button("Cappuccino");
	Button mochaButton = new Button("Mocha Latte");
	Button caramelButton = new Button("Caramel Latte");
	Button vanillaButton = new Button("Vanilla Latte");
	Button mielButton = new Button("Cafe miel");
	Button americanoButton = new Button("Cafe americano");
	Button muffinButton = new Button("Muffin");
	Button croissantButton = new Button("Croissant");
	Button bagelButton = new Button("Bagel");
	Button cinnamonButton = new Button("Cinnamon Roll");
	Button tartButton = new Button("Fruit Tart");
	Button eclairButton = new Button("Chocalate eclair");
	Button puffButton = new Button("Strawberry cream puff");
	Label totalValueLabel = new Label();
	TableView<ItemInCart> itemsTableView = new TableView<>();
	TableColumn<ItemInCart, Double> rateColumn = new TableColumn<>("Purchase price");
	
	CheckBox checkBox1 = new CheckBox("Green");
	

	@SuppressWarnings("unchecked")
	BorderPane setupScene() {
		BorderPane root = new BorderPane();
		GridPane bottomGrid = new GridPane();
		root.setBottom(bottomGrid);
		
		/**setup topGrid */
		bottomGrid.setVgap(10);
		bottomGrid.setHgap(10);

		//create fixed labels
		Label unitLabel = new Label("Item");
		Label pricePerUnitLabel = new Label("Price/unit");

		//add all controls to topGrid
		bottomGrid.add(unitLabel, 0, 1);
		bottomGrid.add(pricePerUnitLabel, 0, 2);
		bottomGrid.add(itemsComboBox, 0, 0, 2, 1);
		bottomGrid.add(unitValueLabel, 1, 1);
		bottomGrid.add(unitPriceValueLabel, 1, 2);

		// set all columns' width 
		for (int i = 0; i < 5; i++)
			bottomGrid.getColumnConstraints().add(new ColumnConstraints(120)); 

		//set up look and feel, fonts, alignment, etc.
		itemsComboBox.setPromptText("Select item");
		unitValueLabel.setTextFill(Color.TEAL);
		unitPriceValueLabel.setTextFill(Color.TEAL);
		unitLabel.setFont(Font.font(15));
		pricePerUnitLabel.setFont(Font.font(15));
		unitValueLabel.setFont(Font.font(15));
		unitPriceValueLabel.setFont(Font.font(15));

	

		quantitySlider.setMinorTickCount(1);
		quantitySlider.setMajorTickUnit(2); 
		quantitySlider.setPrefWidth(300);
		quantitySlider.setSnapToTicks(true);
		quantitySlider.setShowTickLabels(true);
		quantitySlider.setShowTickMarks(true);

		Label qtySliderLabel = new Label("Select units");
		Label purchasedUnitsLabel = new Label("Quantity");

		bottomGrid.add(qtySliderLabel, 2, 0);
		bottomGrid.add(quantitySlider, 3, 0, 2, 1);
		bottomGrid.add(purchasedUnitsLabel, 2, 1);
		bottomGrid.add(purchasedUnitsValueLabel, 3, 1);

		qtySliderLabel.setFont(Font.font(15));
		purchasedUnitsLabel.setFont(Font.font(15));
		purchasedUnitsValueLabel.setFont(Font.font(15));
		purchasedUnitsValueLabel.setTextFill(Color.TEAL);
		

		//setup bottomGrid
		GridPane topGrid = new GridPane();
		root.setTop(topGrid);
		topGrid.setVgap(10);
		topGrid.setHgap(10);

		//setup Add button and Total amount

		Label totalLabel = new Label("Total amount:");
		
		addButton.setPrefWidth(150);
		addButton.setFont(Font.font(15));
		addButton.setPrefHeight(200);
		
		removeButton.setFont(Font.font(15));
		
		coffeeButton.setFont(Font.font(15));
		coffeeButton.setPrefWidth(150);
		coffeeButton.setPrefHeight(200);
		coffeeButton.setStyle("-fx-background-color: SkyBlue");

		//		coffee sub categories
		brewedButton.setFont(Font.font(15));
		brewedButton.setPrefWidth(150);
		brewedButton.setPrefHeight(200);
		brewedButton.setStyle("-fx-background-color: White");
		brewedButton.setVisible(false);
		
		brewedButton.setOnAction((event) -> {
			Item item = new Item("Brewed Coffee","Cup",1.0,2.0);
			menuList.add(item);
		});
		
		auLaitButton.setFont(Font.font(15));
		auLaitButton.setPrefWidth(150);
		auLaitButton.setPrefHeight(200);
		auLaitButton.setStyle("-fx-background-color: White");
		auLaitButton.setVisible(false);
//		
//		auLaitButton.setOnAction((event) -> {
//
//		});
		
		frenchPressButton.setFont(Font.font(15));
		frenchPressButton.setPrefWidth(150);
		frenchPressButton.setPrefHeight(200);
		frenchPressButton.setStyle("-fx-background-color: White");
		frenchPressButton.setVisible(false);
		

		
		icedCoffeeButton.setFont(Font.font(15));
		icedCoffeeButton.setPrefWidth(150);
		icedCoffeeButton.setPrefHeight(200);
		icedCoffeeButton.setStyle("-fx-background-color: White");
		icedCoffeeButton.setVisible(false);
		
		icedCoffeeButton.setOnAction((event) -> {
			Item item = new Item("Iced Coffee","Cup",1.0,2.0);
			menuList.add(item);
		});
		
		noncoffeeButton.setFont(Font.font(15));
		noncoffeeButton.setPrefWidth(150);
		noncoffeeButton.setPrefHeight(200);
		noncoffeeButton.setStyle("-fx-background-color: SkyBlue");
		

		//		non-coffee sub categories
		blackTeaButton.setFont(Font.font(15));
		blackTeaButton.setPrefWidth(150);
		blackTeaButton.setPrefHeight(200);
		blackTeaButton.setStyle("-fx-background-color: White");
		blackTeaButton.setVisible(false);
		
		greenTeaButton.setFont(Font.font(15));
		greenTeaButton.setPrefWidth(150);
		greenTeaButton.setPrefHeight(200);
		greenTeaButton.setStyle("-fx-background-color: White");
		greenTeaButton.setVisible(false);
		
		herbalTeaButton.setFont(Font.font(15));
		herbalTeaButton.setPrefWidth(150);
		herbalTeaButton.setPrefHeight(200);
		herbalTeaButton.setStyle("-fx-background-color: White");
		herbalTeaButton.setVisible(false);
		
		oolongTeaButton.setFont(Font.font(15));
		oolongTeaButton.setPrefWidth(150);
		oolongTeaButton.setPrefHeight(200);
		oolongTeaButton.setStyle("-fx-background-color: White");
		oolongTeaButton.setVisible(false);
		
		chaiButton.setFont(Font.font(15));
		chaiButton.setPrefWidth(150);
		chaiButton.setPrefHeight(200);
		chaiButton.setStyle("-fx-background-color: White");
		chaiButton.setVisible(false);
		
		hotChocalateButton.setFont(Font.font(15));
		hotChocalateButton.setPrefWidth(150);
		hotChocalateButton.setPrefHeight(200);
		hotChocalateButton.setStyle("-fx-background-color: White");
		hotChocalateButton.setVisible(false);
		
		lemonadeButton.setFont(Font.font(15));
		lemonadeButton.setPrefWidth(150);
		lemonadeButton.setPrefHeight(200);
		lemonadeButton.setStyle("-fx-background-color: White");
		lemonadeButton.setVisible(false);
		
		fruitSmothieButton.setFont(Font.font(15));
		fruitSmothieButton.setPrefWidth(150);
		fruitSmothieButton.setPrefHeight(200);
		fruitSmothieButton.setStyle("-fx-background-color: White");
		fruitSmothieButton.setVisible(false);
		
		orangeJuiceButton.setFont(Font.font(15));
		orangeJuiceButton.setPrefWidth(150);
		orangeJuiceButton.setPrefHeight(200);
		orangeJuiceButton.setStyle("-fx-background-color: White");
		orangeJuiceButton.setVisible(false);
		
		espressoButton.setFont(Font.font(15));
		espressoButton.setPrefWidth(150);
		espressoButton.setPrefHeight(200);
		espressoButton.setStyle("-fx-background-color: SkyBlue");
		
		//		espresso sub categories
		espressButton.setFont(Font.font(15));
		espressButton.setPrefWidth(150);
		espressButton.setPrefHeight(200);
		espressButton.setStyle("-fx-background-color: White");
		espressButton.setVisible(false);
		
		macchiatoButton.setFont(Font.font(15));
		macchiatoButton.setPrefWidth(150);
		macchiatoButton.setPrefHeight(200);
		macchiatoButton.setStyle("-fx-background-color: White");
		macchiatoButton.setVisible(false);
		
		conPannaButton.setFont(Font.font(15));
		conPannaButton.setPrefWidth(150);
		conPannaButton.setPrefHeight(200);
		conPannaButton.setStyle("-fx-background-color: White");
		conPannaButton.setVisible(false);
		
		cafeLatteButton.setFont(Font.font(15));
		cafeLatteButton.setPrefWidth(150);
		cafeLatteButton.setPrefHeight(200);
		cafeLatteButton.setStyle("-fx-background-color: White");
		cafeLatteButton.setVisible(false);
		
		classicCapButton.setFont(Font.font(15));
		classicCapButton.setPrefWidth(150);
		classicCapButton.setPrefHeight(200);
		classicCapButton.setStyle("-fx-background-color: White");
		classicCapButton.setVisible(false);
		
		cappuccinoButton.setFont(Font.font(15));
		cappuccinoButton.setPrefWidth(150);
		cappuccinoButton.setPrefHeight(200);
		cappuccinoButton.setStyle("-fx-background-color: White");
		cappuccinoButton.setVisible(false);
		
		mochaButton.setFont(Font.font(15));
		mochaButton.setPrefWidth(150);
		mochaButton.setPrefHeight(200);
		mochaButton.setStyle("-fx-background-color: White");
		mochaButton.setVisible(false);
		
		caramelButton.setFont(Font.font(15));
		caramelButton.setPrefWidth(150);
		caramelButton.setPrefHeight(200);
		caramelButton.setStyle("-fx-background-color: White");
		caramelButton.setVisible(false);
		
		vanillaButton.setFont(Font.font(15));
		vanillaButton.setPrefWidth(150);
		vanillaButton.setPrefHeight(200);
		vanillaButton.setStyle("-fx-background-color: White");
		vanillaButton.setVisible(false);
		
		mielButton.setFont(Font.font(15));
		mielButton.setPrefWidth(150);
		mielButton.setPrefHeight(200);
		mielButton.setStyle("-fx-background-color: White");
		mielButton.setVisible(false);
		
		americanoButton.setFont(Font.font(15));
		americanoButton.setPrefWidth(150);
		americanoButton.setPrefHeight(200);
		americanoButton.setStyle("-fx-background-color: White");
		americanoButton.setVisible(false);
		
		pastriesButton.setFont(Font.font(15));
		pastriesButton.setPrefWidth(150);
		pastriesButton.setPrefHeight(200);
		pastriesButton.setStyle("-fx-background-color: SkyBlue");
		
		//		pastries sub categories
		muffinButton.setFont(Font.font(15));
		muffinButton.setPrefWidth(150);
		muffinButton.setPrefHeight(200);
		muffinButton.setStyle("-fx-background-color: White");
		muffinButton.setVisible(false);
		
		croissantButton.setFont(Font.font(15));
		croissantButton.setPrefWidth(150);
		croissantButton.setPrefHeight(200);
		croissantButton.setStyle("-fx-background-color: White");
		croissantButton.setVisible(false);
		
		bagelButton.setFont(Font.font(15));
		bagelButton.setPrefWidth(150);
		bagelButton.setPrefHeight(200);
		bagelButton.setStyle("-fx-background-color: White");
		bagelButton.setVisible(false);
		
		cinnamonButton.setFont(Font.font(15));
		cinnamonButton.setPrefWidth(150);
		cinnamonButton.setPrefHeight(200);
		cinnamonButton.setStyle("-fx-background-color: White");
		cinnamonButton.setVisible(false);
		
		tartButton.setFont(Font.font(15));
		tartButton.setPrefWidth(150);
		tartButton.setPrefHeight(200);
		tartButton.setStyle("-fx-background-color: White");
		tartButton.setVisible(false);
		
		eclairButton.setFont(Font.font(15));
		eclairButton.setPrefWidth(150);
		eclairButton.setPrefHeight(200);
		eclairButton.setStyle("-fx-background-color: White");
		eclairButton.setVisible(false);
		
		puffButton.setFont(Font.font(15));
		puffButton.setPrefWidth(150);
		puffButton.setPrefHeight(200);
		puffButton.setStyle("-fx-background-color: White");
		puffButton.setVisible(false);
		
		totalLabel.setFont(Font.font(15));
		totalValueLabel.setFont(Font.font(15));

		//create table view
		TableColumn<ItemInCart, String> nameColumn = new TableColumn<>("Item name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<ItemInCart, String>("name"));
		TableColumn<ItemInCart, Double> qtyColumn = new TableColumn<>("Purchased units");
		qtyColumn.setCellValueFactory(new PropertyValueFactory<ItemInCart, Double>("quantity"));

		itemsTableView.getColumns().setAll(nameColumn, qtyColumn, rateColumn);
		itemsTableView.setPrefWidth(450);
		itemsTableView.setPrefHeight(300);
		itemsTableView.getColumns().get(0).setPrefWidth(100);
		itemsTableView.getColumns().get(1).setPrefWidth(100);
		itemsTableView.getColumns().get(2).setPrefWidth(125);
		
		bottomGrid.add(addButton, 0, 0);
		bottomGrid.add(removeButton, 1, 0);
		bottomGrid.add(itemsTableView, 0, 1, 2, 1);
		bottomGrid.add(totalLabel, 0, 2);
		bottomGrid.add(totalValueLabel, 1, 2);
		
		topGrid.add(addButton, 0, 0);
		topGrid.add(removeButton, 1, 0);
		
		topGrid.add(coffeeButton, 4, 0);
		topGrid.add(brewedButton, 4, 2);
		topGrid.add(auLaitButton, 5, 2);
		topGrid.add(frenchPressButton, 6, 2);
		topGrid.add(icedCoffeeButton, 7, 2);
		
		topGrid.add(noncoffeeButton, 5, 0);
		topGrid.add(blackTeaButton, 4, 2);
		topGrid.add(greenTeaButton, 5, 2);
		topGrid.add(herbalTeaButton, 6, 2);
		topGrid.add(oolongTeaButton, 7, 2);
		topGrid.add(chaiButton, 8, 2);
		topGrid.add(hotChocalateButton, 4, 3);
		topGrid.add(lemonadeButton, 5, 3);
		topGrid.add(fruitSmothieButton, 6, 3);
		topGrid.add(orangeJuiceButton, 7, 3);
		
		topGrid.add(espressoButton, 6, 0);
		topGrid.add(espressButton, 4, 2);
		topGrid.add(macchiatoButton, 5, 2);
		topGrid.add(conPannaButton, 6, 2);
		topGrid.add(cafeLatteButton, 7, 2);
		topGrid.add(classicCapButton, 8, 2);
		topGrid.add(cappuccinoButton, 4, 3);
		topGrid.add(mochaButton, 5, 3);
		topGrid.add(caramelButton, 6, 3);
		topGrid.add(vanillaButton, 7, 3);
		topGrid.add(mielButton, 8, 3);
		topGrid.add(americanoButton, 4, 4);

		
		topGrid.add(pastriesButton, 7, 0);
		topGrid.add(muffinButton, 4, 2);
		topGrid.add(croissantButton, 5, 2);
		topGrid.add(bagelButton, 6, 2);
		topGrid.add(cinnamonButton, 7, 2);
		topGrid.add(tartButton, 8, 2);
		topGrid.add(eclairButton, 4, 3);
		topGrid.add(puffButton, 5, 3);
		
		topGrid.add(itemsTableView, 0, 1, 2, 1);
		topGrid.add(totalLabel, 0, 2);
		topGrid.add(totalValueLabel, 1, 2);
		

		topGrid.setPrefSize(700, 500);
		bottomGrid.setPrefSize(700, 425);
		root.setPrefSize(700, 550);
		BorderPane.setMargin(topGrid, new Insets(10,10,10,10));
		BorderPane.setMargin(bottomGrid, new Insets(10,10,10,10));
		
		return root;
	}
	
	@SuppressWarnings("unchecked")
	BorderPane secondaryLayout() {
		BorderPane root2 = new BorderPane();
		GridPane topGrid = new GridPane();
		root2.setBottom(topGrid);
		
		/**setup topGrid */
		topGrid.setVgap(10);
		topGrid.setHgap(10);
		
		topGrid.add(checkBox1, 0, 0);
		topGrid.setPrefSize(700, 125);
		
		return root2;
	}

}

