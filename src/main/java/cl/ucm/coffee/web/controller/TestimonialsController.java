package cl.ucm.coffee.web.controller;

import cl.ucm.coffee.persitence.entity.TestimonialsEntity;
import cl.ucm.coffee.service.Coffee.CoffeeService;
import cl.ucm.coffee.service.Testimonials.TestimonialService;
import cl.ucm.coffee.service.dto.TestimonialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




@Controller
@RestController
@RequestMapping("/api/testimonials")
public class TestimonialsController {

    @Autowired
    TestimonialService testimonialService;
    @Autowired
    CoffeeService coffeeService;

    @GetMapping("/obtener")
    public @ResponseBody List<TestimonialDTO> getTestimonials() {

        return testimonialService.findAll();
    }

    @PostMapping("/ingresar")
    public ResponseEntity<Map> saveTestimonial(@RequestBody TestimonialDTO testimonialDTO) {
        TestimonialsEntity testimonialsEntity = new TestimonialsEntity();

        // testimonialsEntity.setIdTestimonials(testimonialDTO.getIdTestimonials());
        testimonialsEntity.setTestimonial(testimonialDTO.getTestimonial());
        testimonialsEntity.setUsername(testimonialDTO.getUsername());
        testimonialsEntity.setIdCoffee(testimonialDTO.getIdCoffee());
        // testimonialsEntity.setCoffee(coffeeEntity);
        try {
            testimonialService.save(testimonialsEntity);
            Map<String, String> response = new HashMap<>();
            response.put("message", "testimonio creado correctamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al crear el testimonio: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }

    }
}
