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
                    "ID: " + drone.id()
                            + " | Modelo: " + drone.modelo()
                            + " | Batería: " + drone.bateria()
                            + " | Disponible: " + drone.disponible()
                            + " | Ubicación: " + drone.ubicacion()
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
                null,
                origen,
                destino,
                tipoCarga,
                EstadoMision.PENDIENTE
        );

        misiones.add(mision);

        System.out.println(
                "Solicitud registrada: " + mision.id()
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
            if (drone.disponible()) {
                disponibles.add(drone);

                System.out.println(
                        drone.id()
                                + " | Batería: " + drone.bateria()
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
            if (drone.id().equalsIgnoreCase(idDrone)) {
                seleccionado = drone;
                break;
            }
        }

        if (seleccionado == null) {
            System.out.println("Dron no disponible o ID inválido.");
            return;
        }

        // Reemplazar el drone por una nueva instancia no disponible.
        Drone droneAsignado = new Drone(
                seleccionado.id(),
                seleccionado.modelo(),
                seleccionado.bateria(),
                false,
                seleccionado.ubicacion()
        );

        actualizarDrone(droneAsignado);

        // Actualizar la misión conservando sus demás campos.
        Mision misionAsignada = new Mision(
                mision.id(),
                droneAsignado,
                mision.origen(),
                mision.destino(),
                mision.tipoCarga(),
                mision.estado()
        );

        actualizarMision(misionAsignada);

        System.out.println(
                "Dron " + droneAsignado.id()
                        + " asignado a la misión " + misionAsignada.id()
        );

        ejecutarMision(scanner, misionAsignada);
    }

    private static void ejecutarMision(
            Scanner scanner,
            Mision mision
    ) {
        Mision misionEnVuelo = new Mision(
                mision.id(),
                mision.drone(),
                mision.origen(),
                mision.destino(),
                mision.tipoCarga(),
                EstadoMision.EN_VUELO
        );

        actualizarMision(misionEnVuelo);

        System.out.println("\n=== EJECUCIÓN DE MISIÓN ===");
        System.out.println(
                "Ruta predefinida: "
                        + misionEnVuelo.origen()
                        + " -> "
                        + misionEnVuelo.destino()
        );

        System.out.print("¿La entrega fue confirmada? (S/N): ");
        String respuesta = scanner.nextLine().trim();

        if (respuesta.equalsIgnoreCase("S")) {
            Mision misionEntregada = new Mision(
                    misionEnVuelo.id(),
                    misionEnVuelo.drone(),
                    misionEnVuelo.origen(),
                    misionEnVuelo.destino(),
                    misionEnVuelo.tipoCarga(),
                    EstadoMision.ENTREGADA
            );

            actualizarMision(misionEntregada);

            Drone droneActualizado = new Drone(
                    misionEnVuelo.drone().id(),
                    misionEnVuelo.drone().modelo(),
                    misionEnVuelo.drone().bateria(),
                    true,
                    misionEnVuelo.destino()
            );

            actualizarDrone(droneActualizado);

            System.out.println("Entrega confirmada.");

        } else if (respuesta.equalsIgnoreCase("N")) {
            Mision misionFallida = new Mision(
                    misionEnVuelo.id(),
                    misionEnVuelo.drone(),
                    misionEnVuelo.origen(),
                    misionEnVuelo.destino(),
                    misionEnVuelo.tipoCarga(),
                    EstadoMision.FALLIDA
            );

            actualizarMision(misionFallida);

            Drone droneActualizado = new Drone(
                    misionEnVuelo.drone().id(),
                    misionEnVuelo.drone().modelo(),
                    misionEnVuelo.drone().bateria(),
                    true,
                    misionEnVuelo.drone().ubicacion()
            );

            actualizarDrone(droneActualizado);

            System.out.println("Misión marcada como fallida.");

        } else {
            System.out.println(
                    "Respuesta inválida. La misión permanece en vuelo."
            );
        }
    }

    private static void actualizarDrone(Drone droneActualizado) {
        for (int i = 0; i < drones.size(); i++) {
            if (drones.get(i).id().equals(droneActualizado.id())) {
                drones.set(i, droneActualizado);
                return;
            }
        }
    }

    private static void actualizarMision(Mision misionActualizada) {
        for (int i = 0; i < misiones.size(); i++) {
            if (misiones.get(i).id().equals(misionActualizada.id())) {
                misiones.set(i, misionActualizada);
                return;
            }
        }
    }

    private static void mostrarMisiones() {
        System.out.println("\n=== MISIONES ===");

        if (misiones.isEmpty()) {
            System.out.println("No hay misiones registradas.");
            return;
        }

        for (Mision mision : misiones) {
            String idDrone = mision.drone() == null
                    ? "Sin asignar"
                    : mision.drone().id();

            System.out.println(
                    "ID: " + mision.id()
                            + " | Origen: " + mision.origen()
                            + " | Destino: " + mision.destino()
                            + " | Carga: " + mision.tipoCarga()
                            + " | Dron: " + idDrone
                            + " | Estado: " + mision.estado()
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