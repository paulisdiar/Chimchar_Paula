package com.chimchar.skycampus;

import java.util.List;
import java.util.stream.Collectors;

public class RetoStreams {

    record Drone(
            String id,
            String modelo,
            int bateria,
            boolean disponible,
            String ubicacion
    ) {}

    public static void main(String[] args) {

        List<Drone> flota = List.of(
                new Drone("D-01", "DJI Mini 3", 85, true, "Bloque A"),
                new Drone("D-02", "DJI Mini 3", 42, false, "Biblioteca"),
                new Drone("D-03", "DJI Mini 3", 91, true, "Bloque C"),
                new Drone("D-04", "DJI Mini 3", 18, true, "Bloque B"),
                new Drone("D-05", "DJI Mini 3", 67, true, "Bloque D")
        );

        // Reto 1: IDs disponibles con batería >= 50%,
        // ordenados de mayor a menor batería.
        List<String> dronesDisponibles = flota.stream()
                .filter(Drone::disponible)
                .filter(drone -> drone.bateria() >= 50)
                .sorted((d1, d2) ->
                        Integer.compare(d2.bateria(), d1.bateria()))
                .map(Drone::id)
                .collect(Collectors.toList());

        System.out.println("1. Drones disponibles con batería >= 50%:");
        System.out.println(dronesDisponibles);

        // Reto 2: ¿Hay algún dron disponible en Bloque C?
        boolean disponibleEnBloqueC = flota.stream()
                .anyMatch(drone ->
                        drone.disponible()
                                && drone.ubicacion().equals("Bloque C"));

        System.out.println("\n2. ¿Hay un dron disponible en Bloque C?");
        System.out.println(disponibleEnBloqueC);

        // Reto 3: Cantidad de drones con batería crítica (< 20%).
        long bateriaCritica = flota.stream()
                .filter(drone -> drone.bateria() < 20)
                .count();

        System.out.println("\n3. Drones con batería crítica:");
        System.out.println(bateriaCritica);

        // Reto 4: ID y batería de todos los drones.
        List<String> estadoBaterias = flota.stream()
                .map(drone ->
                        drone.id() + ": " + drone.bateria() + "%")
                .collect(Collectors.toList());

        System.out.println("\n4. ID y batería de todos los drones:");
        System.out.println(estadoBaterias);
    }
}