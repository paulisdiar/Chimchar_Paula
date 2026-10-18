package com.chimchar.skycampus.v3.dominio;

import java.util.List;

public interface RepositorioDrones {

    List<Drone> porSede(String sede);

    List<Drone> todos();

    void guardar(Drone drone);
}
