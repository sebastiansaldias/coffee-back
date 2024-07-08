package cl.ucm.coffee.service.Coffee;


import cl.ucm.coffee.persitence.entity.CoffeeEntity;
import cl.ucm.coffee.service.dto.CoffeeDTO;
import cl.ucm.coffee.service.dto.CoffeeDtoPut;
import cl.ucm.coffee.service.dto.CoffeeTestDto;

import java.util.List;

public interface ICoffeeService {
    public List<CoffeeDTO> findAll();
    public List<CoffeeTestDto> findAllWithTests();
    public void saveCoffee(CoffeeDTO coffeeDTO);
    public void deleteCoffee(Integer coffeeId);
    public CoffeeDTO findById(Integer coffeeId);
    public CoffeeEntity updateCoffee(CoffeeDtoPut coffeeDTO, Integer coffeeId);

    public CoffeeDTO findByName(String name);

}
