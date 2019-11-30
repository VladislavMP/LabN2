package lab2.varb4;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.MathContext;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")

public class MainFrame extends JFrame {
    // Размеры окна приложения
    private static final int WIDTH = 600;
    private static final int HEIGHT = 420;

    // Текстовые поля для считывания значений переменных
    private JTextField textFieldX;
    private JTextField textFieldY;
    private JTextField textFieldZ;

    private JTextField textFieldResult;
    private ButtonGroup radioButtons = new ButtonGroup();
    private ButtonGroup radioMemoryButtons = new ButtonGroup();
    private Box hboxFormulaType = Box.createHorizontalBox();
    private Box hBoxMemoryType = Box.createHorizontalBox();

    private int formulaId = 1;
    private int memoryId= 1;

    private Double mem1 = new Double(0);
    private Double mem2 = new Double(0);
    private Double mem3 = new Double(0);

    // Формула No1 для рассчѐта
    public Double calculate1(Double x, Double y, Double z) {
        return Math.sin(Math.sin(y) + Math.exp(Math.cos(y)) + z * z)
                * Math.pow(x * x + Math.sin(z) + Math.exp(Math.cos(z)), 1/4);
    }
    // Формула No2 для рассчѐта
    public Double calculate2(Double x, Double y, Double z) {
        return Math.pow(x, x)/(Math.sqrt(y*y*y + 1) + Math.log(z));
    }

    private void addRadioButton(String buttonName, final int formulaId) {
        JRadioButton button = new JRadioButton(buttonName);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                MainFrame.this.formulaId = formulaId;
            }
        });
        radioButtons.add(button);
        hboxFormulaType.add(button);
    }

    private void addMemoryRadioButton (String buttonName, final int memoryId)	{
        JRadioButton button = new JRadioButton(buttonName);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)	{
                MainFrame.this.memoryId = memoryId;
            }
        });

        radioMemoryButtons.add(button);
        hBoxMemoryType.add(button);
    }

    public MainFrame() {
        super("Вычисление формулы");

        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH)/2,
                (kit.getScreenSize().height - HEIGHT)/2);

        hboxFormulaType.add(Box.createHorizontalGlue());
        addRadioButton("Формула 1", 1);
        addRadioButton("Формула 2", 2);
        radioButtons.setSelected(
                radioButtons.getElements().nextElement().getModel(), true);
        hboxFormulaType.add(Box.createHorizontalGlue());
        hboxFormulaType.setBorder(
                BorderFactory.createLineBorder(Color.YELLOW));
// Создать область с полями ввода для X и Y
        JLabel labelForX = new JLabel("X:");
        textFieldX = new JTextField("0", 10);
        textFieldX.setMaximumSize(textFieldX.getPreferredSize());
        JLabel labelForY = new JLabel("Y:");
        textFieldY = new JTextField("0", 10);
        textFieldY.setMaximumSize(textFieldY.getPreferredSize());
        JLabel labelForZ = new JLabel("Z:");
        textFieldZ = new JTextField("0", 10);
        textFieldZ.setMaximumSize(textFieldZ.getPreferredSize());

        //Создать контейнер «коробка с горизонтальной укладкой»
        Box hboxVariables = Box.createHorizontalBox();
        hboxVariables.setBorder(
                BorderFactory.createLineBorder(Color.RED));
        hboxVariables.add(Box.createHorizontalGlue());
        hboxVariables.add(labelForX);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldX);
        hboxVariables.add(Box.createHorizontalStrut(50));
        hboxVariables.add(labelForY);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldY);
        hboxVariables.add(Box.createHorizontalStrut(50));
        hboxVariables.add(labelForZ);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldZ);
        hboxVariables.add(Box.createHorizontalGlue());
// Создать область для вывода результата
        JLabel labelForResult = new JLabel("Результат:");
//labelResult = new JLabel("0");
        textFieldResult = new JTextField("0", 10);
        textFieldResult.setMaximumSize(
                textFieldResult.getPreferredSize());
        Box hboxResult = Box.createHorizontalBox();
        hboxResult.add(Box.createHorizontalGlue());
        hboxResult.add(labelForResult);
        hboxResult.add(Box.createHorizontalStrut(10));
        hboxResult.add(textFieldResult);
        hboxResult.add(Box.createHorizontalGlue());
        hboxResult.setBorder(BorderFactory.createLineBorder(Color.BLUE));
// Создать область для кнопок
        JButton buttonCalc = new JButton("Вычислить");
        buttonCalc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {


                    Double x = Double.parseDouble(textFieldX.getText());
                    Double y = Double.parseDouble(textFieldY.getText());
                    Double z = Double.parseDouble(textFieldZ.getText());
                    Double result;
                    if (formulaId==1)
                        result = calculate1(x, y, z);
                    else
                        result = calculate2(x, y, z);
                    textFieldResult.setText(result.toString());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        hBoxMemoryType.add(Box.createHorizontalGlue());
        addMemoryRadioButton("Переменная 1", 1);
        addMemoryRadioButton("Переменная 2", 2);
        addMemoryRadioButton("Переменная 3", 3);
        // по умолчанию на первую память
        radioMemoryButtons.setSelected(radioMemoryButtons.getElements().nextElement().getModel(), true);
        //Добавить «клей» C1-H2 с правой стороны
        hBoxMemoryType.add(Box.createHorizontalGlue());
        //Задать рамку для коробки с помощью класса BorderFactory
        hBoxMemoryType.setBorder(BorderFactory.createLineBorder(Color.YELLOW));

        JButton buttonReset = new JButton("Очистить поля");
        buttonReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                textFieldX.setText("0");
                textFieldY.setText("0");
                textFieldZ.setText("0");
                textFieldResult.setText("0");
            }
        });

        JButton buttonMC = new JButton("MC");
        buttonMC.addActionListener(new ActionListener()	{
            public void actionPerformed(ActionEvent event) {

                if (memoryId == 1)	mem1 = (double) 0;
                if (memoryId == 2)	mem2 = (double) 0;
                if (memoryId == 3)	mem3 = (double) 0;
                textFieldResult.setText("0");
            }
        });


        Box hBoxMemoryField = Box.createHorizontalBox();
        hBoxMemoryField.add(Box.createHorizontalGlue());
        hBoxMemoryField.add(Box.createHorizontalGlue());


        JButton buttonMp = new JButton("M+");
        buttonMp.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                try{
                    Double result = Double.parseDouble(textFieldResult.getText());

                    if (memoryId == 1) 	{mem1 += result;textFieldResult.setText(mem1.toString());}
                    if (memoryId == 2)	{mem2 += result;textFieldResult.setText(mem2.toString());}
                    if (memoryId == 3)	{mem3 += result;textFieldResult.setText(mem3.toString());}

                }catch (NumberFormatException ex)
                { JOptionPane.showMessageDialog(MainFrame.this,
                        "Ошибка в формате записи числа с плавающей точкой", "" +
                                "Ошибочный формат числа", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        Box hBoxControlButtons = Box.createHorizontalBox();
        hBoxControlButtons.add(Box.createHorizontalGlue());
        hBoxControlButtons.add(buttonMC);
        hBoxControlButtons.add(Box.createHorizontalStrut(30));
        hBoxControlButtons.add(buttonMp);
        hBoxControlButtons.add(Box.createHorizontalGlue());

        Box hboxButtons = Box.createHorizontalBox();
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(buttonCalc);
        hboxButtons.add(Box.createHorizontalStrut(30));
        hboxButtons.add(buttonReset);
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.setBorder(
                BorderFactory.createLineBorder(Color.GREEN));
// Связать области воедино в компоновке BoxLayout
        Box contentBox = Box.createVerticalBox();
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(hboxFormulaType);
        contentBox.add(hboxVariables);
        contentBox.add(hboxResult);
        contentBox.add(hboxButtons);
        contentBox.add(hBoxMemoryType);
        contentBox.add(hBoxControlButtons);
        contentBox.add(hBoxMemoryField);
        contentBox.add(Box.createVerticalGlue());
        getContentPane().add(contentBox, BorderLayout.CENTER);
    }

    // Главный метод класса
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
