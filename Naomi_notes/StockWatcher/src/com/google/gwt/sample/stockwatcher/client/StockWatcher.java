package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;

import com.google.gwt.user.client.Window;
import java.util.ArrayList;

import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;



public class StockWatcher implements EntryPoint{
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private FlexTable stocksFlexTable = new FlexTable();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private TextBox newSymbolTextBox = new TextBox();
	private Button addStockButton = new Button("Add");
	private Label lastUpdateLabel = new Label();
	private ArrayList<String> stocks = new ArrayList<String>();
	private static final int REFRESH_INTERVAL = 5000; // ms

	
	public void onModuleLoad() {
		
		//TODO create table for stock data.
		stocksFlexTable.setText(0, 0, "Symbol");
		stocksFlexTable.setText(0, 1, "Price");
		stocksFlexTable.setText(0, 2, "Change");
		stocksFlexTable.setText(0, 3, "Remove");
		
		//TODO Assemble Add stock panel.
		addPanel.add(newSymbolTextBox);
		addPanel.add(addStockButton);
		
		//TODO Assemble Main panel.
		mainPanel.add(stocksFlexTable);
		mainPanel.add(addPanel);
		mainPanel.add(lastUpdateLabel);
		
		//TODO Associate the Main Panel with the HTML host page.
		RootPanel.get("stockList").add(mainPanel);
		
		//TODO Move cursor focus to the input box.
		newSymbolTextBox.setFocus(true);

	      // Setup timer to refresh list automatically.
	      Timer refreshTimer = new Timer() {
	        @Override
	        public void run() {
	          refreshWatchList();
	        }
	      };
	      refreshTimer.scheduleRepeating(REFRESH_INTERVAL);
		
		
		// Listen for mouse events on the Add button.
	      addStockButton.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	          addStock();
	        }
	      });

	      // Listen for keyboard events in the input box.
	      newSymbolTextBox.addKeyDownHandler(new KeyDownHandler() {
	        public void onKeyDown(KeyDownEvent event) {
	          if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
	            addStock();
	          }
	        }
	      });
	    }

	    private void addStock() {
	      // TODO Auto-generated method stub
	    	
	    	final String symbol = newSymbolTextBox.getText().toUpperCase().trim();
	    	newSymbolTextBox.setFocus(true);
	    	
	    	//Stock code must be between 1 and 10 chars that are numbers, Letters, or dots.
	    	if (!symbol.matches("^[0-9A-Z\\\\.]{1,10}$")) {
	    		Window.alert("'" + symbol + "' is not a valid symbol.");
	    		newSymbolTextBox.selectAll();
	    		return;
	    	}
	    	newSymbolTextBox.setText("");
	    	
	    	//TODO Don't add the stock of it's already in the table.
	    	if (stocks.contains(symbol))
	    		return;
	    	
	    	//TODO Add the stock too the table
	    	int row = stocksFlexTable.getRowCount();
	    	stocks.add(symbol);
	    	stocksFlexTable.setText(row, 0, symbol);
	    	
	    	//TODO Add a button to remove this stock from the table.
	    	Button removeStockButton = new Button("x");
	    	removeStockButton.addClickHandler(new ClickHandler() {
	    		public void onClick(ClickEvent event) {
	    			int removeIndex = stocks.indexOf(symbol);
	    			stocks.remove(removeIndex);
	    			stocksFlexTable.removeRow(removeIndex + 1);
	    		}
	    	});
	    	stocksFlexTable.setWidget(row, 3, removeStockButton);
	    	
	    	//TODO Get the stock price
	    	 refreshWatchList();
	    	 
	    	 private void refreshWatchList() {
	    	     // TODO Auto-generated method stub
	    		 final double MAX_PRICE = 100.0; //$100.00
	    		 final double MAX_PRICE_CHANGE = 0.02; // +/- 2%
	    		 
	    		 StockPrice[] prices = new StockPrice[stocks.size()];
	    		 for(int i = 0; i < stocks.size(); i++)
	    		 {
	    			double price = Random.nextDouble() * MAX_PRICE;
	    			double change = price * MAX_PRICE_CHANGE 
	    					* (Random.nextDouble() * 2.0 - 1.0);
	    			prices[i] = new StockPrice(stocks.get(i),price,change);
	    		 }
	    		 updateTable(prices);
	    	    }
	    	 
	    	 private void updateTable(StockPrice[] prices) {
	    		  // TODO Auto-generated method stub
	    		 for (int i = 0; i < prices.length; i++) {
	    		        updateTable(prices[i]);
	    		      }
	    		}
	    	 
	    }				
		
	}
