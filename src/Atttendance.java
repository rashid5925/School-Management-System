

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Atttendance extends JFrame{
    private JList studentList;
    private JComboBox classSelection;
    private JButton selectionButton;
    private JButton present;
    private JButton absent;
    private JComboBox day;
    private JLabel attendanceLabel;
    private JLabel dateLabel;
    private JPanel attendancePanel;
    private JTextField date;
    private JTextField month;
    private JTextField year;
    private JButton dateSelect;
    private JButton back;

    String value, fileName, dateFull;
    boolean validator1 = false, validator2 = false;

    public Atttendance() {

        day.addItem("Monday");
        day.addItem("Tuesday");
        day.addItem("Wednesday");
        day.addItem("Thursday");
        day.addItem("Friday");


        classSelection.addItem("Class 1");
        classSelection.addItem("Class 2");
        classSelection.addItem("Class 3");
        classSelection.addItem("Class 4");
        classSelection.addItem("Class 5");
        classSelection.addItem("Class 6");
        classSelection.addItem("Class 7");
        classSelection.addItem("Class 8");


        setContentPane(attendancePanel);
        setTitle("Attendance");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setVisible(true);


        selectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                value = (String) classSelection.getSelectedItem();
                fileName = value.charAt(value.length()-1) + ".txt";
                String[] names = attendanceList(fileName);
                studentList.setModel(new DefaultComboBoxModel(names));

                attendanceFile("Attendance "+fileName);
                validator1 = true;
            }
        });


        present.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validator1 && validator2 && studentList.getSelectedIndex()!=-1){
                    String str = studentList.getSelectedValue().toString();
                    value = (String) classSelection.getSelectedItem();
                    fileName = "Attendance " + value.charAt(value.length() - 1) + ".txt";
                    presentFun(fileName, str);
                }else {
                    JOptionPane.showMessageDialog(null, "Please Select Class and Date");
                }
            }
        });


        absent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validator1 && validator2 && studentList.getSelectedIndex()!=-1){
                    String str = studentList.getSelectedValue().toString();
                    value = (String) classSelection.getSelectedItem();
                    fileName = "Attendance " + value.charAt(value.length() - 1) + ".txt";
                    absentFun(fileName, str);
                }else {
                    JOptionPane.showMessageDialog(null, "Please Select Class and Date");
                }
            }
        });


        dateSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validator1 && !(date.getText().equals("") || month.getText().equals("") || year.getText().equals(""))) {
                    dateFull = day.getSelectedItem() + " " + date.getText() + "-" + month.getText() + "-" + year.getText();
                    value = (String) classSelection.getSelectedItem();
                    fileName = "Attendance " + value.charAt(value.length() - 1) + ".txt";
                    dateWrite(fileName, dateFull);
                    validator2 = true;
                }else {
                    JOptionPane.showMessageDialog(null, "Please Select Class or Enter Date");
                }
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
    }


    public static String[] attendanceList(String fileName){
        int totalLines = lineCounter(fileName);
        int startIndex, endIndex;
        String[] records = new String[totalLines];
        String[] names = new String[totalLines];
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            for (int i=0; i<records.length; i++){
                records[i] = reader.nextLine();
            }

            for (int i=0; i< names.length; i++){
                startIndex = indexOf(records[i], ';', 0);
                endIndex = indexOf(records[i], ';', startIndex+1);
                String name = records[i].substring(startIndex+1, endIndex);
                String rollNo = records[i].substring(0, startIndex);
                names[i] = rollNo + ": " + name;
            }
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        return names;
    }

    public static int lineCounter(String fileName){
        int counter = 0;
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()){
                reader.nextLine();
                counter++;
            }
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return counter;
    }

    public static int indexOf(String str, char c, int start){
        int i;
        for (i=start; i<str.length(); i++){
            if (str.charAt(i) == c){
                break;
            }
        }
        return i;
    }

    public static void attendanceFile(String fileName){
        try {
            File file = new File(fileName);
            file.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void presentFun(String fileName, String studentName){
        try {
            File file = new File(fileName);
            FileWriter fileW = new FileWriter(file, true);
            fileW.write(studentName+": P\n");
            fileW.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void absentFun(String fileName, String studentName){
        try {
            File file = new File(fileName);;
            FileWriter fileW = new FileWriter(file, true);
            fileW.write(studentName+": A\n");
            fileW.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void dateWrite(String fileName, String dateFull){
        try {
            File file = new File(fileName);
            FileWriter fileW = new FileWriter(file, true);
            fileW.write(dateFull+"\n");
            fileW.close();
            System.out.println(fileName);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
