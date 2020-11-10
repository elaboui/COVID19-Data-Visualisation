package me.nassimi.covid19_data_visualisation.models;

public class AdviceItem {
    int adviceImage;
    String adviceText;

    public AdviceItem(int adviceImage, String adviceText) {
        this.adviceImage = adviceImage;
        this.adviceText = adviceText;
    }

    public int getAdviceImage() {
        return adviceImage;
    }

    public String getAdviceText() {
        return adviceText;
    }
}
