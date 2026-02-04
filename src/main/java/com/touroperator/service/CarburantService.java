package com.touroperator.service;

import com.touroperator.model.Carburant;
import com.touroperator.repository.CarburantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CarburantService {

    @Autowired
    private CarburantRepository carburantRepository;

    public List<Carburant> getAllCarburants() {
        return carburantRepository.findAll();
    }

    public Carburant getCarburantById(Integer id) {
        return carburantRepository.findById(id);
    }

    public Carburant getCarburantByName(String name) {
        return carburantRepository.findByName(name);
    }

    public Carburant saveCarburant(Carburant carburant) {
        return carburantRepository.save(carburant);
    }

    public void deleteCarburant(Carburant carburant) {
        carburantRepository.delete(carburant);
    }

    public void deleteCarburantById(Integer id) {
        carburantRepository.deleteById(id);
    }
}
