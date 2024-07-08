package cl.ucm.coffee.service.Testimonials;


import cl.ucm.coffee.persitence.entity.TestimonialsEntity;
import cl.ucm.coffee.service.dto.TestimonialDTO;

import java.util.List;

public interface ITestimonialService {
    public List<TestimonialDTO> findAll();
    public void save(TestimonialsEntity testimonialsEntity);

}