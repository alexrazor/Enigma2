import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;
import  java.io.*;

public class GUI {
    private JTextArea inputArea;
    private JTextArea outputArea;
    private ArrayList<Data> cardList;
    private JFrame frame;
    private Data card;


    public void go(){
        frame = new JFrame("ENIGMA - coding machine. Ver. 0.0.1");
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font("sanserif", Font.BOLD, 24);
        inputArea = new JTextArea(6,20);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setFont(bigFont);
        inputArea.setText("Эта версия программы работает только с русским алфавитом и только кодирует текст. " +
                "Также она может сохранять текст в файл и читать текст из файла");

        JScrollPane qScroller = new JScrollPane(inputArea);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        outputArea = new JTextArea(6,20);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(bigFont);

        JScrollPane aScroller = new JScrollPane(outputArea);
        aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        JButton nextButton = new JButton("Encode");

        cardList = new ArrayList<>();

        JLabel qLabel = new JLabel("Type text here:");
        JLabel aLabel = new JLabel("Coded text:");
        JLabel author = new JLabel("Created by A.Lyahovich(c)");

        mainPanel.add(qLabel);
        mainPanel.add(qScroller);
        mainPanel.add(aLabel);
        mainPanel.add(aScroller);
        mainPanel.add(nextButton);
        mainPanel.add(author);
        nextButton.addActionListener(new NextCardListener());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("Clear input");
        JMenuItem saveMenuItem = new JMenuItem("Save file");
        JMenuItem loadMenuItem = new JMenuItem("Load file");
        newMenuItem.addActionListener(new NewMenuItemListener());
        saveMenuItem.addActionListener(new SaveMenuListener());
        loadMenuItem.addActionListener(new LoadMenuListener());

        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(500,600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
    public class NextCardListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            CipherAlgorithm cipher = new CipherAlgorithm();
            String userInput = inputArea.getText();
            String codeInput=cipher.encrypt(userInput);
            outputArea.setText(codeInput);

        }
    }
    public class NewMenuItemListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            inputArea.setText("");
            outputArea.setText("");
        }
    }
    public class SaveMenuListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            card = new Data(inputArea.getText());
            cardList.add(card);
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());
        }
    }


    private void saveFile(File file){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            //for (Data card:cardList){
              //  writer.write(card.getInputText()+"/");
                //writer.write(card.getOutputText()+"\n");
            //}
            writer.write(card.getInputText());
            writer.close();
        }catch (IOException ex){
            System.out.println("[error:] couldn't save file");
            ex.printStackTrace();
        }
    }

    public class LoadMenuListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileOpen= new JFileChooser();
            fileOpen.showOpenDialog(frame);
            loadFile(fileOpen.getSelectedFile());
        }
    }
    private  void loadFile(File file){
        cardList = new ArrayList<>();
        StringBuilder text = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) !=null){
                //makeCard(line);
                text.append(line);
            }
            reader.close();
            inputArea.setText(text.toString());

        } catch (IOException ex){
            System.out.println("[error:] couldn't load file");
            ex.printStackTrace();
        }
        //showNextCard();
    }
}
