package cl.ucm.coffee.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestimonialDTO {
    private int idTestimonials;
    private String username;
    private String testimonial;
    private int idCoffee;
}
