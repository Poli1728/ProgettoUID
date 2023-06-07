package com.calendly.calendly.Controller;

import com.calendly.calendly.Model.Dipendente;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DipendentiViewController {


    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private TableColumn<Dipendente, String> lastNameCol;

    @FXML
    private TableColumn<Dipendente, String> nameCol;

    @FXML
    private Button removeButton;

    @FXML
    private TableColumn<Dipendente, String> roleCol;

    @FXML
    private TableColumn<Dipendente, Double> salaryCol;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<Dipendente> tableView;

    @FXML
    private Label titleLabel;

    @FXML
    private TableColumn<Dipendente, String> usernameCol;

    @FXML
    void actionAddButton(ActionEvent event) {
        initializePopup();
    }

    @FXML
    void actionEditButton(ActionEvent event) {
        tableView.setEditable(!tableView.isEditable());
        System.out.println(tableView.isEditable());
    }

    @FXML
    void actionRemoveButton(ActionEvent event) {

    }

    @FXML
    void actionSearchButton(ActionEvent event) {

    }

    @FXML
    void onSortTableView(ActionEvent event) {

    }

    @FXML
    void initialize() {
        usernameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        roleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        salaryCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        usernameCol.setCellValueFactory(new PropertyValueFactory<Dipendente, String>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Dipendente, String>("name"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Dipendente, String>("lastName"));
        roleCol.setCellValueFactory(new PropertyValueFactory<Dipendente, String>("role"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<Dipendente, Double>("salary"));


        //inutile
        tableView.setRowFactory(tableView -> {
            TableRow<Dipendente> row = new TableRow<>();
            TableView.TableViewSelectionModel<Dipendente> selectionModel = tableView.getSelectionModel();

            row.pseudoClassStateChanged(PseudoClass.getPseudoClass("highlighted"), selectionModel.getSelectedIndex() == 2);
            return row;
        });

        tableView.setItems(fetchData());
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        TableView.TableViewSelectionModel<Dipendente> selectionModel = tableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<Dipendente> selectedItems = selectionModel.getSelectedItems();
        ObservableList<Integer> selectedIndices = selectionModel.getSelectedIndices();


        usernameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Dipendente, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Dipendente, String> dipendenteStringCellEditEvent) {
                //todo handle changes from the obs list to database
                System.out.println(dipendenteStringCellEditEvent.getNewValue());
            }
        });

    }




    private ObservableList<Dipendente> fetchData() {
        ObservableList<Dipendente> data = FXCollections.observableArrayList(
                new Dipendente("1", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("2", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("3", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("4", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("5", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("6", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("7", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("8", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("9", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("10", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("11", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("12", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("13", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("14", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("15", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("16", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("17", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("18", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("19", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("20", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("21", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("22", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("23", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("24", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("25", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("26", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("27", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("28", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("1", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("2", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("3", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("4", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("5", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("6", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("7", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("8", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("9", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("10", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("11", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("12", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("13", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("14", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("15", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("16", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("17", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("18", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("19", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("20", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("21", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("22", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("23", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("24", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("25", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("26", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("27", "Mario", "Bruno", "Manager", 1005.60),
                new Dipendente("28", "Mario", "Bruno", "Manager", 1005.60)
                );
        return data;
    }



    private void initializePopup() {

    }


}
