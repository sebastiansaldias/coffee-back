package cl.ucm.coffee.web.controller;

import cl.ucm.coffee.persitence.entity.CoffeeEntity;
import cl.ucm.coffee.persitence.entity.TestimonialsEntity;
import cl.ucm.coffee.service.Coffee.CoffeeService;
import cl.ucm.coffee.service.Testimonials.TestimonialService;
import cl.ucm.coffee.service.dto.CoffeeDTO;
import cl.ucm.coffee.service.dto.TestimonialDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api/testimonios")
public class TestimonialsController {

    @Autowired
    TestimonialService testimonialService;
    @Autowired
    CoffeeService coffeeService;

    @GetMapping("/")
    public @ResponseBody List<TestimonialDTO> getTestimonials() {

        return testimonialService.findAll();
    }

    @PostMapping("/")
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
