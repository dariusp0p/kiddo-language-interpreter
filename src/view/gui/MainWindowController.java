package view.gui;

import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.gui.model.HeapEntry;
import view.gui.model.SymbolTableEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import model.state.ProgramState;
import model.statement.Statement;
import model.value.StringValue;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainWindowController {
    @FXML
    private TextField programCountField;
    @FXML
    private TableView<HeapEntry> heapTableView;
    @FXML
    private TableColumn<HeapEntry, Integer> heapAddressColumn;
    @FXML
    private TableColumn<HeapEntry, String> heapValueColumn;
    @FXML
    private ListView<String> outputListView;
    @FXML
    private ListView<String> fileTableListView;
    @FXML
    private ListView<Integer> programStateListView;
    @FXML
    private TableView<SymbolTableEntry> symbolTableView;
    @FXML
    private TableColumn<SymbolTableEntry, String> variableNameColumn;
    @FXML
    private TableColumn<SymbolTableEntry, String> variableValueColumn;
    @FXML
    private ListView<String> executionStackListView;
    @FXML
    private Button backButton;
    @FXML
    private Button runOneStepButton;

    private Controller controller;

    @FXML
    public void initialize() {
        heapAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        heapValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        variableNameColumn.setCellValueFactory(new PropertyValueFactory<>("variableName"));
        variableValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        programStateListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        updateProgramStateDetails(newValue);
                    }
                }
        );
    }

    public void setController(Controller controller) {
        this.controller = controller;
        updateAllDisplays();
    }



    @FXML
    private void handleBackToSelector() {
        try {
            // Cleanup executor
            if (controller != null) {
                controller.shutdown();
            }

            // Load selector window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/ProgramSelector.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setTitle("Kiddo Interpreter - Program Selector");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            showError("Failed to return to selector: " + e.getMessage());
        }
    }

    @FXML
    private void handleRunOneStep() {
        try {
            List<ProgramState> programStates = controller.removeCompletedPrograms(
                    controller.getProgramListFromRepo()
            );

            if (programStates.isEmpty()) {
                showInfo("Program execution completed!");
                runOneStepButton.setDisable(true);
                return;
            }

            controller.oneStepForAllPrograms(programStates);
            controller.setProgramListFromRepo(programStates);

            updateAllDisplays();

        } catch (Exception e) {
            showError("Execution error: " + e.getMessage());
            runOneStepButton.setDisable(true);
        }
    }

    private void updateAllDisplays() {
        try {
            List<ProgramState> programStates = controller.getProgramListFromRepo();

            if (programStates.isEmpty()) {
                runOneStepButton.setDisable(true);
                return;
            }

            ProgramState currentState = programStates.get(0);
            programCountField.setText(String.valueOf(programStates.size()));

            updateHeapTable(currentState.heapTable().getContent());
            updateOutputList(currentState.output().getAll());
            updateFileTableList(currentState.fileTable().getContent().toMap());
            updateProgramStateList(programStates);

            if (programStateListView.getSelectionModel().getSelectedItem() == null && !programStates.isEmpty()) {
                programStateListView.getSelectionModel().selectFirst();
            }

        } catch (Exception e) {
            showError("Error updating displays: " + e.getMessage());
        }
    }

    private void updateHeapTable(Map<Integer, model.value.Value> heap) {
        ObservableList<HeapEntry> heapEntries = FXCollections.observableArrayList();
        heap.forEach((address, value) ->
                heapEntries.add(new HeapEntry(address, value.toString()))
        );
        heapTableView.setItems(heapEntries);
    }

    private void updateOutputList(List<model.value.Value> output) {
        ObservableList<String> outputStrings = FXCollections.observableArrayList(
                output.stream().map(Object::toString).collect(Collectors.toList())
        );
        outputListView.setItems(outputStrings);
    }

    private void updateFileTableList(Map<StringValue, java.io.BufferedReader> fileTable) {
        ObservableList<String> fileTableStrings = FXCollections.observableArrayList(
                fileTable.keySet().stream()
                        .map(filename -> "File: " + filename.value())
                        .collect(Collectors.toList())
        );
        fileTableListView.setItems(fileTableStrings);
    }

    private void updateProgramStateList(List<ProgramState> programStates) {
        ObservableList<Integer> ids = FXCollections.observableArrayList(
                programStates.stream()
                        .map(ProgramState::getId)
                        .collect(Collectors.toList())
        );
        programStateListView.setItems(ids);
    }

    private void updateProgramStateDetails(Integer programStateId) {
        try {
            List<ProgramState> programStates = controller.getProgramListFromRepo();
            ProgramState selectedState = programStates.stream()
                    .filter(ps -> ps.getId() == programStateId)
                    .findFirst()
                    .orElse(null);

            if (selectedState == null) {
                return;
            }

            updateSymbolTable(selectedState.symbolTable().getContent().toMap());
            updateExecutionStack(selectedState.executionStack().getAll());

        } catch (Exception e) {
            showError("Error updating program state details: " + e.getMessage());
        }
    }

    private void updateSymbolTable(Map<String, model.value.Value> symbolTable) {
        ObservableList<SymbolTableEntry> entries = FXCollections.observableArrayList();
        symbolTable.forEach((name, value) ->
                entries.add(new SymbolTableEntry(name, value.toString()))
        );
        symbolTableView.setItems(entries);
    }

    private void updateExecutionStack(List<Statement> stack) {
        ObservableList<String> stackStrings = FXCollections.observableArrayList(
                stack.stream().map(Object::toString).collect(Collectors.toList())
        );
        executionStackListView.setItems(stackStrings);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
