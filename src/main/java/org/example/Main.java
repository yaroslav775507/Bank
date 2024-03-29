package org.example;
import com.mysql.cj.xdevapi.SelectStatement;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

import static org.example.DataBase.*;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        //Подключение к БД
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                System.out.println("Добро пожаловать, подключение успешно установленно!");
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

        Scanner s = new Scanner(System.in);

        System.out.println("Выберите вход:");
        System.out.println("1-Войти");
        System.out.println("2-Зарегистрироваться");
        System.out.println("3-Перевести средства");
        int about = Integer.parseInt(s.nextLine());


        //Вход
        if(about==1){
            int a = 0;
            do {
                try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                    System.out.println("Введите имя");
                    String username = s.nextLine();
                    System.out.println("Введите пароль");
                    String password = s.nextLine();
                    Statement statement = con.createStatement();
                    String query = "SELECT * FROM YRtest.users WHERE name = '" + username + "' AND password = '" + password + "'";
                    ResultSet resultSet = statement.executeQuery(query);


                    // Проверка результатов запроса
                    if(resultSet.next()){
                        System.out.println("Доступ предоставлен");
                        a++;
                    } else {
                        System.out.println("Введите данные еще раз");
                    }
                }
            }while (a==0);
        }

        //Регистрация
        //Заполнение БД и работа с командной строкой
        if(about==2){
            System.out.println("Введите ваше имя");
            String nameUser = s.nextLine();
            System.out.println("Введите пароль");
            String password = s.nextLine();
            System.out.println("Введите ваш пол");
            String gender = s.nextLine();
            String sqlCommand = " INSERT INTO YRtest.users (name,money,password,gender) value ( ? , ?, ?, ? ) ";


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
            bufferedWriter.newLine();
            bufferedWriter.write(gender);
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
                    preparedStatement.setString(3,password);
                    preparedStatement.setString(4,gender);
                    preparedStatement.executeUpdate(); // выполняем запрос
                    System.out.println("Данные успешно добавлены!");
                }
            }

        }

            //Перевод между пользователями
            if (about == 3) {
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
                //Здесь реализована выборка пользователя из списка выше и осуществляется перевод между счетами
                    System.out.print("Введите идентификатор пользователя: ");
                    int userId = s.nextInt();
                    String query = "SELECT * FROM YRtest.users WHERE id = ?";
                    PreparedStatement statement2 = con.prepareStatement(query);
                    statement2.setInt(1, userId);
                    ResultSet result = statement2.executeQuery();
                    if (result.next()) {
                        int x = 0;
                        String firstName = result.getString("name");
                        int money = result.getInt("money");
                        System.out.println("Пользователь: " + firstName + " счет " + money);
                        System.out.println("Введите сумму перевода");
                        int suma = s.nextInt();
                        x = money + suma;
                        System.out.println("Пользователь: " + firstName + " счет " + x);
                    } else {
                        System.out.println("Пользователь с идентификатором " + userId + " не найден");
                    }
                }
            }

                if (about == 4) {
                    System.out.println("You choose 4");
                }
            }
}

