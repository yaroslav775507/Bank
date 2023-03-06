package org.example;
import com.mysql.cj.xdevapi.SelectStatement;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String URL ="jdbc:mysql://localhost/YRtest";
    private static final String USERNAME = "root";
    private static final String PASSWORD ="root1234";

    public static void main(String[] args) throws IOException, SQLException {
        //Подключение к БД
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                System.out.println("Подключение успешно!");
            }
        } catch (Exception ex) {
            System.out.println("Есть проблемки(");
            System.out.println(ex);
        }
        //Создание цифрового рисунка
        String art = """
                 ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄   ▄▄▄▄▄▄▄▄▄▄▄  ▄▄        ▄  ▄    ▄\s
                ▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░▌ ▐░░░░░░░░░░░▌▐░░▌      ▐░▌▐░▌  ▐░▌
                ▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░▌░▌     ▐░▌▐░▌ ▐░▌\s
                ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌▐░▌    ▐░▌▐░▌▐░▌ \s
                ▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░▌ ▐░▌   ▐░▌▐░▌░▌  \s
                ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░▌ ▐░░░░░░░░░░░▌▐░▌  ▐░▌  ▐░▌▐░░▌   \s
                 ▀▀▀▀█░█▀▀▀▀ ▐░█▀▀▀▀█░█▀▀ ▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░▌   ▐░▌ ▐░▌▐░▌░▌  \s
                     ▐░▌     ▐░▌     ▐░▌  ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌    ▐░▌▐░▌▐░▌▐░▌ \s
                     ▐░▌     ▐░▌      ▐░▌ ▐░█▄▄▄▄▄▄▄█░▌▐░▌       ▐░▌▐░▌     ▐░▐░▌▐░▌ ▐░▌\s
                     ▐░▌     ▐░▌       ▐░▌▐░░░░░░░░░░▌ ▐░▌       ▐░▌▐░▌      ▐░░▌▐░▌  ▐░▌
                      ▀       ▀         ▀  ▀▀▀▀▀▀▀▀▀▀   ▀         ▀  ▀        ▀▀  ▀    ▀\s
                                                                                        \s
                """;
        for (int i = 0; i < art.length(); i++) {
            System.out.print(art);
            break;
        }


        //Заполнение БД и работа с командной строкой
        Scanner s = new Scanner(System.in);
        System.out.println("Введите ваше имя");
        String nameUser = s.nextLine();
        String sqlCommand = " INSERT INTO YRtest.users (name,money) value ( ? , ? ) ";


        System.out.println(nameUser + " добро пожаловать в YR банк!");
        //Создание файла
        File file = new File("About.txt");
        if (file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //Заполнение файла
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(nameUser);
        fileWriter.flush();

        FileReader fileReader = new FileReader(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.newLine();
        bufferedWriter.write("Здесь будет вся ваша информация.");
        bufferedWriter.flush();


        System.out.println("Введите желаемый баланс");
        int sum = Integer.parseInt(s.nextLine());
        bufferedWriter.newLine();
        bufferedWriter.write("Ваш баланс равен: " + sum);
        bufferedWriter.flush();
        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = con.prepareStatement(sqlCommand);
            {
                //Добавление данных в БД
                preparedStatement.setString(1, nameUser);
                preparedStatement.setInt(2, sum);
                preparedStatement.executeUpdate(); // выполняем запрос
                System.out.println("Данные успешно добавлены!");
            }
        }



        System.out.println("Выберите услугу:");
       // System.out.println("1-Пополнить счет");
        System.out.println("1-Осуществить перевод средств");
//        System.out.println("3-Пополнить баланс телефона");
//        System.out.println("4-Передать сообщение");
//        System.out.println("5-Осуществить перевод между своими счетами");
        int about = Integer.parseInt(s.nextLine());





            //Перевод между пользователями
            if (about == 1) {
                System.out.println("Выберите счет перевода");
                //Вывод всех пользователей из базы данных.
                try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                    Statement statement = con.createStatement();

                    ResultSet resultSet = statement.executeQuery("SELECT * FROM YRtest.users");
                    while (resultSet.next()) {

                        int id = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        System.out.printf("%d. %s  \n", id, name);

                    }
                //Здесь необходимо реализовать выборку пользователя из списка выше

                    System.out.print("Введите идентификатор пользователя: ");
                    int userId = s.nextInt();
                    String query = "SELECT * FROM YRtest.users WHERE id = ?";
                    PreparedStatement statement2 = con.prepareStatement(query);
                    statement2.setInt(1, userId);
                    ResultSet result = statement2.executeQuery();

                    if (result.next()) {
                        String firstName = result.getString("name");
                        String lastName = result.getString("money");
                        System.out.println("Пользователь: " + firstName + " счет " + lastName);
                    } else {
                        System.out.println("Пользователь с идентификатором " + userId + " не найден");
                    }
                    //Здесь необходимо осуществлять переводы между счетами.


                }
            }




                if (about == 2) {
                    System.out.println("You choose 3");
                }
            }
        }

