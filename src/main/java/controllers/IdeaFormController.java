package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Idea;
import models.response.ResponseDto;
import services.OkHttpService;

public class IdeaFormController {

    @FXML
    private TextArea txtAreaIdea;

    @FXML
    private TextField txtName;

    @FXML
    private CheckBox chkBoxFeedback;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSend;

    @FXML
    private TextField txtPhone;

    @FXML
    public void onButtonClicked(ActionEvent event) {
        if (event.getSource().equals(btnSend)) {
            boolean valid = validateFields();
            if (!valid) {
                showAlert("Заполните все поля");
                return;
            }
            Idea idea = new Idea();
            idea.setDescription(txtAreaIdea.getText().trim());
            idea.setName(txtName.getText().trim());
            idea.setPhone(txtPhone.getText().trim());
            idea.setFeedback(chkBoxFeedback.isSelected());

            ResponseDto responseDto = OkHttpService.INSTANCE.sendIdea(idea);
            if (responseDto == null) {
                showAlert("Попробуйте снова");
                return;
            }
            showAlert(responseDto.getMessage());
            System.out.println(OkHttpService.INSTANCE.getAllIdeas(txtPhone.getText().trim()));
        }
    }

    private void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING, text);
        alert.show();
    }

    private boolean validateFields() {
        if (txtAreaIdea.getText().trim().isEmpty()) {
            return false;
        }
        if (txtName.getText().trim().isEmpty()) {
            return false;
        }
        if (txtPhone.getText().trim().isEmpty()) {
            return false;
        }
        return true;
    }

}
