package cl.ucm.coffee.service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeTestDto {
    private int idCoffee;
    private String name;
    private String description;
    private int price;
    private String image64;
    private List<TestimonialDTO> testimonials;
}
