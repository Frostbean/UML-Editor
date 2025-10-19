package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import shapes.BaseObject;
import utils.Label;

public class LabelDialog extends JDialog {

    private JTextField nameTextField;
    private JComboBox<String> shapeComboBox;
    private JButton colorButton;
    private JSpinner fontSizeSpinner;
    private Color selectedColor = Color.BLACK; // 預設顏色改為黑色更常見
    private int selectedFontSize = 12; // 預設字體大小

    private boolean isOKClicked = false;
    private BaseObject targetObject; // 要設定標籤的目標物件

    public LabelDialog(JFrame owner, BaseObject target) {
        super(owner, "Custom label Style", true); // 模態對話框
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(300, 200));

        this.targetObject = target; // 接收要設定標籤的目標物件

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // 標籤名稱 (Label Name)
        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);

        nameTextField = new JTextField(targetObject.getLabelText() != null ? targetObject.getLabelText() : ""); // 預設顯示現有名稱
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(nameTextField, gbc);

        // 標籤形狀 (Label Shape)
        JLabel shapeLabel = new JLabel("Shape:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(shapeLabel, gbc);

        String[] shapes = {"Oval", "Rectangle"}; // 對應圖片中的 Oval 和 Rect1 (簡化)
        shapeComboBox = new JComboBox<>(shapes);
        if (targetObject.getLabelShape() != null) {
            shapeComboBox.setSelectedItem(targetObject.getLabelShape().toString());
        } else {
            shapeComboBox.setSelectedIndex(0); // 預設選第一個
        }
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(shapeComboBox, gbc);

        // 顏色與樣式 (Color & Font Size) - 顏色
        JLabel colorLabel = new JLabel("Color:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(colorLabel, gbc);

        selectedColor = targetObject.getLabelColor() != null ? targetObject.getLabelColor() : Color.BLACK;
        colorButton = new JButton(getColorName(selectedColor)); // 預設顯示顏色名稱
        colorButton.setBackground(selectedColor);
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(LabelDialog.this, "Choose Label Color", selectedColor);
                if (newColor != null) {
                    selectedColor = newColor;
                    colorButton.setBackground(selectedColor);
                    colorButton.setText(getColorName(selectedColor)); // 顯示顏色名稱 (簡化)
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(colorButton, gbc);

        // 顏色與樣式 (Color & Font Size) - 字體大小
        JLabel fontSizeLabel = new JLabel("Font Size:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(fontSizeLabel, gbc);

        selectedFontSize = targetObject.getLabelFontSize() > 0 ? targetObject.getLabelFontSize() : 12;
        SpinnerNumberModel fontSizeModel = new SpinnerNumberModel(selectedFontSize, 8, 24, 1);
        fontSizeSpinner = new JSpinner(fontSizeModel);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(fontSizeSpinner, gbc);

        // Cancel 和 OK 按鈕
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isOKClicked = true;
                // 將資料傳向 BaseObject 的 setLabel 方法
                if (targetObject != null) {
                    targetObject.setLabel(nameTextField.getText(),
                        selectedColor,
                        Label.LabelShape.valueOf(shapeComboBox.getSelectedItem().toString().toUpperCase()),
                        (int) fontSizeSpinner.getValue()
                        );
                }
                dispose();
            }
        });
        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(buttonPanel, gbc);

        pack();
        setLocationRelativeTo(owner);
    }

    // 一個簡單的方法來獲取顏色的名稱 (可以根據需求擴展)
    private String getColorName(Color color) {
        if (color.equals(Color.YELLOW)) {
            return "yellow";
        } else if (color.equals(Color.BLUE)) {
            return "blue";
        } else if (color.equals(Color.RED)) {
            return "red";
        } else if (color.equals(Color.BLACK)) {
            return "black";
        } else if (color.equals(Color.WHITE)) {
            return "white";
        } else if (color.equals(Color.GREEN)) {
            return "green";
        }
        return "custom";
    }

    // 獲取使用者輸入的標籤名稱
    public String getLabelName() {
        return nameTextField.getText();
    }

    // 獲取使用者選擇的形狀
    public String getSelectedShape() {
        return (String) shapeComboBox.getSelectedItem();
    }

    // 獲取使用者選擇的顏色
    public Color getSelectedColor() {
        return selectedColor;
    }

    // 獲取使用者選擇的字體大小
    public int getSelectedFontSize() {
        return (int) fontSizeSpinner.getValue();
    }

    // 判斷使用者是否點擊了 OK
    public boolean isOKClicked() {
        return isOKClicked;
    }
}