package cl.ucm.coffee.service.Testimonials;

import cl.ucm.coffee.persitence.entity.TestimonialsEntity;
import cl.ucm.coffee.persitence.repository.TestimonialRepository;
import cl.ucm.coffee.service.dto.TestimonialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestimonialService implements ITestimonialService{

    @Autowired
    private TestimonialRepository testimonialRepository;
    public List<TestimonialDTO> findAll() {
        List<TestimonialsEntity> testimonials = testimonialRepository.findAll();
        List<TestimonialDTO> testimonialDTOS = new ArrayList<>();
        for (TestimonialsEntity testimonialsEntity : testimonials) {
            TestimonialDTO testimonialDTO = new TestimonialDTO();
            testimonialDTO.setIdTestimonials(testimonialsEntity.getIdTestimonials());
            testimonialDTO.setTestimonial(testimonialsEntity.getTestimonial());
            testimonialDTO.setUsername(testimonialsEntity.getUsername());
            testimonialDTO.setIdCoffee(testimonialsEntity.getIdCoffee());
            testimonialDTOS.add(testimonialDTO);
        }
        return testimonialDTOS;
    }

    public void save(TestimonialsEntity testimonialsEntity) {
        System.out.println("TestimonialsEntity: " + testimonialsEntity);
        testimonialRepository.save(testimonialsEntity);
    }
}
