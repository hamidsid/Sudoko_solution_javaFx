package master_sudoku.graphics;

import javafx.scene.control.TextField;

public class OneLetterTextField extends TextField {

    @Override
    public void replaceText(int start, int end, String text) {
        set(text);
    }

    @Override
    public void replaceSelection(String text) {
        set(text);
    }

    private void set(String text) {
        if (matches(text)) {
            if (text.length() > 1) {
                text = text.substring(1);
            }
            setText(text);
            positionCaret(text.length());
        }
    }

    private boolean matches(String text) {
        if (text.isEmpty()) {
            return true;
        }
        if (text.matches("[1-9]")) {
            return true;
        }
        return false;
    }
}