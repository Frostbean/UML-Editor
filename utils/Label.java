package utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Label {
    private String text;
    private Color color;
    private LabelShape shape;
    private int fontSize;

    public enum LabelShape {
        RECTANGLE,
        OVAL
    }

    public void setLabel(String text, Color color, LabelShape shape, int fontSize) {
        this.text = text;
        this.color = color;
        this.shape = shape;
        this.fontSize = fontSize;
    }

    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(color);
        Font labelFont = new Font("Arial", Font.PLAIN, fontSize);
        g.setFont(labelFont);
        java.awt.FontMetrics fm = g.getFontMetrics(labelFont);
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int labelX = x + (width - textWidth) / 2;
        int labelY = y + (height - textHeight) / 2 + textHeight;

        if (shape == LabelShape.RECTANGLE) {
            int rectWidth = textWidth + 10;
            int rectHeight = textHeight + 6;
            int rectX = x + (width - rectWidth) / 2;
            int rectY = y + (height - rectHeight) / 2;
            g.drawRect(rectX, rectY, rectWidth, rectHeight);
            g.drawString(text, labelX, labelY);
        }
        else if (shape == LabelShape.OVAL) {
            int ovalWidth = textWidth + 10;
            int ovalHeight = textHeight + 6;
            int ovalX = x + (width - ovalWidth) / 2;
            int ovalY = y + (height - ovalHeight) / 2;
            g.drawOval(ovalX, ovalY, ovalWidth, ovalHeight);
            g.drawString(text, labelX, labelY);
        }
        g.setColor(Color.BLACK);
    }

    public String getLabelText() {
        return text;
    }

    public LabelShape getLabelShape() {
        return shape;
    }

    public Color getLabelColor() {
        return color;
    }

    public int getLabelFontSize() {
        return fontSize;
    }
}
