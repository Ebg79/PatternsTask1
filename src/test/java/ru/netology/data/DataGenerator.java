package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    private static final Faker faker = new Faker(new Locale("ru"));

    @Value
    public static class UserInfo {
        String name;
        String phone;
        String city;
    }

    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCityInList() {
        String[] city = {"Москва", "Казань", "Санкт-Петербург", "Рязань", "Ростов"};
        int rnd = new Random().nextInt(city.length);
        return city[rnd];
    }
    public static String generateCityNotInList() {
        String[] city = {"Волжский", "Саров", "Грозный", "Ялта", "Киев", "Горкий"};
        int rnd = new Random().nextInt(city.length);
        return city[rnd];
    }

    public static String generateName() {
        String name = faker.name().fullName();
        return name;
    }

    public static String generatePhone() {
        String phone = faker.phoneNumber().cellPhone();
        return phone;
    }
}

