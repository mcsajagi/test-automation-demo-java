package seleniumTests.data;

public class DropdownOption {
    private int index;
    private String text;

    public DropdownOption(int index, String text) {
        this.index = index;
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public String getText() {
        return text;
    }
}
