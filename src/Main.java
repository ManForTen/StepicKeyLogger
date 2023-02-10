import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class Main {
    static JFrame frame = new JFrame();
    static JLabel l; // Объект с текстом
    static int width = 200, height = 200;
    static File file = new File("test.txt");
    static FileWriter writer;

    static public void print(KeyEvent e) throws IOException {
        if (e.getKeyCode()!=KeyEvent.VK_ESCAPE) {
            l.setText(e.getKeyText(e.getKeyCode())); // Показываем нажатие в метке
            writer.write(e.getKeyText(e.getKeyCode())); // Записываем в файл
        }
        else { // По esc прекращаем запись и выводим информацию, затем можно вновь продолжать ввод
            writer.flush(); // Сохраняем введенное
            Stream log = Files.lines(Paths.get("test.txt")); // Создаем Stream для чтения данных из файла
            String temp = Arrays.deepToString(log.toArray()); // Из Stream делаем массив, массив переводим в строку
            l.setText(temp.substring(1,temp.length()-1)); // Выводим в метку все введенное содержимое
            log.close(); // Закрываем Stream
        }
    }

    public static void main(String[] args) throws IOException {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Запись текста");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(dim.width / 2 - width / 2, dim.height / 2 - height / 2, width, height);
        JPanel panel = new JPanel (new FlowLayout(FlowLayout.CENTER)); // Создаем панель, чтобы ей отлавливать события клавиатуры, ставим ее слева
        panel.setFocusable(true); // Делаем у панели возможность принимать фокус, иначе она не сможет отловить события клавиатуры
        l = new JLabel(); // Создаем объект
        panel.add(l, BorderLayout.CENTER); // Добавляем на панель по центру
        frame.add(panel); // Добавляем панель на форму
        file.createNewFile(); // Создаем файл для записи
        writer = new FileWriter(file);
        panel.addKeyListener(new KeyAdapter() { // Добавляем слушателя на панель
            public void keyReleased(KeyEvent e) {
                try {
                    print(e); // Метод записи
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        frame.setVisible(true);
    }
}