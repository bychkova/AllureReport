package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateCity() {
        String [] validCities = new String[]{
                "Белгород", "Брянск", "Владимир", "Воронеж", "Иваново", "Калуга", "Кострома",
                "Курск", "Липецк", "Москва", "Орел", "Рязань", "Смоленск", "Тамбов", "Тверь",
                "Тула", "Ярославль", "Архангельск", "Вологда", "Калининград", "Петрозаводск",
                "Сыктывкар", "Санкт-Петербург", "Мурманск", "Салехард", "Великий Новгород",
                "Псков", "Санкт-Петербург", "Уфа", "Киров", "Йошкар-Ола", "Саранск",
                "Нижний Новгород", "Оренбург", "Пенза", "Пермь", "Самара", "Саратов", "Казань",
                "Ижевск", "Ульяновск", "Чебоксары", "Курган", "Екатеринбург", "Тюмень",
                "Ханты-мансийск", "Челябиннск", "Салехард", "Горно-Алтайск", "Барнаул", "Улан-Удэ",
                "Чита", "Иркутск", "Кемерово", "Красноярск", "Новосибирск", "Омск",
                "Томск", "Кызыл", "Абакан", "Благовещенск", "Биробиджан", "Петропавловск-Камчатский",
                "Магадан", "Владивосток", "Якутск", "Южно-Сахалинск", "Хабаровск", "Анадырь",
                "Майкоп", "Астрахань", "Волгоград", "Элиста", "Краснодар", "Ростов",
                "Махачкала", "Магас", "Нальчик", "Черкесск", "Владивкавказ", "Ставрополь",
                "Грозный"
        };
        return validCities[new Random().nextInt(validCities.length)];
    }

    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String name = faker.name().lastName() + " " + faker.name().firstName();
        return name;
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateValidUser(String locale) {
            UserInfo user = new UserInfo(generateCity(), generateName(locale), generatePhone(locale));
            return user;
        }
    }
    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}