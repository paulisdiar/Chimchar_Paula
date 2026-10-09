package com.chimchar.skycampus;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SkyCampus {

    private static final List<Drone> drones = new ArrayList<>();
    private static final List<Mision> misiones = new ArrayList<>();

    public static void main(String[] args) {
        inicializarDrones();
        ejecutarMenu();
    }

    private static void inicializarDrones() {
        for (int i = 1; i <= 5; i++) {
            drones.add(new Drone(
                    "D" + i,
                    "DJI Mini 3",
                    100,
                    true,
                    "Biblioteca"
            ));
        }
    }

    private static void ejecutarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== SKYCAMPUS MVP ===");
            System.out.println("1. Consultar drones");
            System.out.println("2. Registrar solicitud de reparto");
            System.out.println("3. Consultar misiones");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero(scanner);

            switch (opcion) {
                case 1:
                    mostrarDrones();
                    break;
                case 2:
                    registrarSolicitud(scanner);
                    break;
                case 3:
                    mostrarMisiones();
                    break;
                case 4:
                    System.out.println("Programa finalizado.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 4);

        scanner.close();
    }

    private static void mostrarDrones() {
        System.out.println("\n=== DRONES ===");

        for (Drone drone : drones) {
            System.out.println(
                    "ID: " + drone.getId()
                            + " | Modelo: " + drone.getModelo()
                            + " | Batería: " + drone.getBateria()
                            + " | Disponible: " + drone.isDisponible()
                            + " | Ubicación: " + drone.getUbicacion()
            );
        }
    }

    private static void registrarSolicitud(Scanner scanner) {
        System.out.println("\n=== REGISTRAR SOLICITUD ===");

        System.out.print("Origen: ");
        String origen = scanner.nextLine().trim();

        System.out.println("Destinos disponibles:");
        System.out.println("1. Bloque A");
        System.out.println("2. Bloque B");
        System.out.println("3. Bloque C");
        System.out.println("4. Bloque D");
        System.out.println("5. Biblioteca");
        System.out.print("Seleccione el destino: ");

        int opcionDestino = leerEntero(scanner);
        String destino = obtenerDestino(opcionDestino);

        if (destino == null || origen.isBlank()) {
            System.out.println("Origen o destino inválido.");
            return;
        }

        System.out.println("Tipos de carga:");
        System.out.println("1. Sobre");
        System.out.println("2. Carpeta");
        System.out.println("3. Libro");
        System.out.print("Seleccione el tipo de carga: ");

        int opcionCarga = leerEntero(scanner);
        TipoCarga tipoCarga = obtenerTipoCarga(opcionCarga);

        if (tipoCarga == null) {
            System.out.println("Tipo de carga inválido.");
            return;
        }

        Mision mision = new Mision(
                "M" + (misiones.size() + 1),
                origen,
                destino,
                tipoCarga
        );

        misiones.add(mision);

        System.out.println(
                "Solicitud registrada: " + mision.getId()
        );

        asignarDrone(scanner, mision);
    }

    private static String obtenerDestino(int opcion) {
        switch (opcion) {
            case 1:
                return "Bloque A";
            case 2:
                return "Bloque B";
            case 3:
                return "Bloque C";
            case 4:
                return "Bloque D";
            case 5:
                return "Biblioteca";
            default:
                return null;
        }
    }

    private static TipoCarga obtenerTipoCarga(int opcion) {
        switch (opcion) {
            case 1:
                return TipoCarga.SOBRE;
            case 2:
                return TipoCarga.CARPETA;
            case 3:
                return TipoCarga.LIBRO;
            default:
                return null;
        }
    }

    private static void asignarDrone(
            Scanner scanner,
            Mision mision
    ) {
        System.out.println("\n=== DRONES DISPONIBLES ===");

        List<Drone> disponibles = new ArrayList<>();

        for (Drone drone : drones) {
            if (drone.isDisponible()) {
                disponibles.add(drone);

                System.out.println(
                        drone.getId()
                                + " | Batería: " + drone.getBateria()
                );
            }
        }

        if (disponibles.isEmpty()) {
            System.out.println("No hay drones disponibles.");
            return;
        }

        System.out.print("Ingrese el ID del dron que desea asignar: ");
        String idDrone = scanner.nextLine().trim();

        Drone seleccionado = null;

        for (Drone drone : disponibles) {
            if (drone.getId().equalsIgnoreCase(idDrone)) {
                seleccionado = drone;
                break;
            }
        }

        if (seleccionado == null) {
            System.out.println("Dron no disponible o ID inválido.");
            return;
        }

        seleccionado.setDisponible(false);
        mision.asignarDrone(seleccionado);

        System.out.println(
                "Dron " + seleccionado.getId()
                        + " asignado a la misión " + mision.getId()
        );

        ejecutarMision(scanner, mision);
    }

    private static void ejecutarMision(
            Scanner scanner,
            Mision mision
    ) {
        mision.setEstado(EstadoMision.EN_VUELO);

        System.out.println("\n=== EJECUCIÓN DE MISIÓN ===");
        System.out.println(
                "Ruta predefinida: "
                        + mision.getOrigen() + " -> " + mision.getDestino()
        );

        System.out.print("¿La entrega fue confirmada? (S/N): ");
        String respuesta = scanner.nextLine().trim();

        if (respuesta.equalsIgnoreCase("S")) {
            mision.setEstado(EstadoMision.ENTREGADA);
            mision.getDrone().setUbicacion(mision.getDestino());
            mision.getDrone().setDisponible(true);

            System.out.println("Entrega confirmada.");
        } else if (respuesta.equalsIgnoreCase("N")) {
            mision.setEstado(EstadoMision.FALLIDA);
            mision.getDrone().setDisponible(true);

            System.out.println("Misión marcada como fallida.");
        } else {
            System.out.println(
                    "Respuesta inválida. La misión permanece en vuelo."
            );
        }
    }

    private static void mostrarMisiones() {
        System.out.println("\n=== MISIONES ===");

        if (misiones.isEmpty()) {
            System.out.println("No hay misiones registradas.");
            return;
        }

        for (Mision mision : misiones) {
            String idDrone = mision.getDrone() == null
                    ? "Sin asignar"
                    : mision.getDrone().getId();

            System.out.println(
                    "ID: " + mision.getId()
                            + " | Origen: " + mision.getOrigen()
                            + " | Destino: " + mision.getDestino()
                            + " | Carga: " + mision.getTipoCarga()
                            + " | Dron: " + idDrone
                            + " | Estado: " + mision.getEstado()
            );
        }
    }

    private static int leerEntero(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}