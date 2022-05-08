import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements Initializable {

    @FXML
    private TableView tableView;


    @Override
    public void initialize(URL url, ResourceBundle rb){
        TableColumn score = new TableColumn("Score");
        tableView.getColumns().addAll(score);
        final ObservableList<Long> data = FXCollections.observableArrayList((long)1094,(long)109);
        score.setCellValueFactory( new PropertyValueFactory<Long,Long>("Score"));
        tableView.setItems(data);

    }
}
