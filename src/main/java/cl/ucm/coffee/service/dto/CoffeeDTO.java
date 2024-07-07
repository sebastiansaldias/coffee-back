package cl.ucm.coffee.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeDTO {
    private int idCoffee;
    private String name;
    private String description;
    private int price;
    private String image64;
}
