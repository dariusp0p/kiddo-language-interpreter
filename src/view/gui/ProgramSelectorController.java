package view.gui;

import controller.Controller;
import examples.ProgramExamples;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.state.*;
import model.statement.Statement;
import repository.MainRepository;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class ProgramSelectorController {
    @FXML
    private ListView<String> programListView;

    @FXML
    private Button selectButton;
    private final List<Statement> programs = new ArrayList<>();
    private final List<String> descriptions = new ArrayList<>();

    @FXML
    public void initialize() {
        loadPrograms();
        programListView.setItems(FXCollections.observableArrayList(descriptions));
    }

    private void loadPrograms() {
        String[] descs = {
                "int v; v = 2; Print(v)",
                "int a; int b; a = 2 + 3 * 5; b = a + 1; Print(b)",
                "bool a; int v; a = true; if a then v = 2 else v = 3; Print(v)",
                "string varf; varf = \"./data/test.in\"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)",
                "int a; int b; a = 10; b = 5; if (a > b) then Print(a) else Print(b); int c = a - b; if (c >= 5) then Print(100) else Print(200)",
                "Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v) + 5)",
                "Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a); print(rH(v)); print(rH(rH(a)) + 5)",
                "Ref int v; new(v,0); while (rH(v) < 3) { print(rH(v)); wH(v, rH(v) + 1); } print(rH(v))",
                "Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))",
                "Ref Ref Ref int r; int v; v = 99; Ref int p; new(p,v); Ref Ref int q; new(q,p); new(r,q); print(rH(rH(rH(r))))",
                "SAFE GC test: Ref int v; new(v,1); Ref Ref int a; new(a,v); loop new(v,x) twice; print(rH(rH(a)))",
                "int v; Ref int a; v = 10; new(a,22); fork( wH(a,30); v = 32; print(v); print(rH(a)) ); print(v); print(rH(a));",
                "int v; v = 1; fork( v = v + 10; print(v) ); print(v);",
                "Ref int a; new(a,0); fork( wH(a, rH(a) + 1); print(rH(a)) ); fork( wH(a, rH(a) + 1); print(rH(a)) ); print(rH(a));",
                "TYPE ERROR: bool a; a = 5;",
                "TYPE ERROR: int a; a = 5; if a then print(1) else print(2);"
        };

        try {
            for (int i = 1; i <= 16; i++) {
                Method method = ProgramExamples.class.getMethod("example" + i);
                Statement program = (Statement) method.invoke(null);
                programs.add(program);
                descriptions.add((i) + ": " + descs[i - 1]);
            }
        } catch (Exception e) {
            showError("Error loading programs: " + e.getMessage());
        }
    }

    @FXML
    private void handleSelectProgram() {
        int selectedIndex = programListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            showError("Please select a program first!");
            return;
        }

        Statement selectedProgram = programs.get(selectedIndex);

        try {
            selectedProgram.typecheck(new MapSymbolTable());

            ProgramState programState = new ProgramState(
                    new DequeExecutionStack(),
                    new MapSymbolTable(),
                    new ListOutput(),
                    new MapFileTable(),
                    new MapHeapTable()
            );
            programState.executionStack().push(selectedProgram);

            Controller controller = new Controller(
                    new MainRepository(programState, "logs/gui_log.txt")
            );

            controller.setConcurrentExecutor(Executors.newFixedThreadPool(2));


            openMainWindow(controller);

        } catch (Exception e) {
            showError("Type check failed: " + e.getMessage());
        }
    }

    private void openMainWindow(Controller controller) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/MainWindow.fxml"));
            Scene scene = new Scene(loader.load());

            MainWindowController mainController = loader.getController();
            mainController.setController(controller);

            Stage stage = new Stage();
            stage.setTitle("Kiddo Interpreter - Execution");
            stage.setScene(scene);
            stage.show();

            ((Stage) selectButton.getScene().getWindow()).close();

        } catch (IOException e) {
            showError("Failed to open main window: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
