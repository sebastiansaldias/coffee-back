package cl.ucm.coffee.service.Coffee;

import cl.ucm.coffee.persitence.entity.CoffeeEntity;
import cl.ucm.coffee.persitence.entity.TestimonialsEntity;
import cl.ucm.coffee.persitence.repository.CoffeeRepository;
import cl.ucm.coffee.service.dto.CoffeeDTO;
import cl.ucm.coffee.service.dto.CoffeeDtoPut;
import cl.ucm.coffee.service.dto.CoffeeTestDto;
import cl.ucm.coffee.service.dto.TestimonialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CoffeeService implements ICoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

    @Override
    public List<CoffeeDTO> findAll() {
        List<CoffeeEntity> coffeeEntities = coffeeRepository.findAll();
        List<CoffeeDTO> coffeeDTOs = new ArrayList<>();

        for (CoffeeEntity coffeeEntity : coffeeEntities) {
            CoffeeDTO coffeeDTO = new CoffeeDTO();
            coffeeDTO.setIdCoffee(coffeeEntity.getIdCoffee());
            coffeeDTO.setName(coffeeEntity.getName());
            coffeeDTO.setDescription(coffeeEntity.getDescription());
            coffeeDTO.setPrice(coffeeEntity.getPrice());
            coffeeDTO.setImage64(coffeeEntity.getImage64());

            // Convertir testimonios si es necesario
            // List<TestimonialDTO> testimonialDTOs =
            // convertTestimonialsToDTOs(coffeeEntity.getTestimonials(),coffeeEntity.getIdCoffee());
            // coffeeDTO.setTestimonials(testimonialDTOs);
            //
            coffeeDTOs.add(coffeeDTO);
        }

        return coffeeDTOs;
    }

    public List<CoffeeTestDto> findAllWithTests() {
        List<CoffeeEntity> coffeeEntities = coffeeRepository.findAll();
        List<CoffeeTestDto> coffeeDTOs = new ArrayList<>();

        for (CoffeeEntity coffeeEntity : coffeeEntities) {
            CoffeeTestDto coffeeTestDTO = new CoffeeTestDto();
            coffeeTestDTO.setIdCoffee(coffeeEntity.getIdCoffee());
            coffeeTestDTO.setName(coffeeEntity.getName());
            coffeeTestDTO.setDescription(coffeeEntity.getDescription());
            coffeeTestDTO.setPrice(coffeeEntity.getPrice());
            coffeeTestDTO.setImage64(coffeeEntity.getImage64());

            // Convertir testimonios si es necesario
            List<TestimonialDTO> testimonialDTOs = convertTestimonialsToDTOs(coffeeEntity.getTestimonials(),
                    coffeeEntity.getIdCoffee());
            coffeeTestDTO.setTestimonials(testimonialDTOs);
            coffeeDTOs.add(coffeeTestDTO);
        }

        return coffeeDTOs;
    }

    @Override
    public CoffeeDTO findById(Integer coffeeId) {
        Optional<CoffeeEntity> coffeeEntity = coffeeRepository.findById(coffeeId);
        CoffeeDTO coffeeDTO = new CoffeeDTO();
        if (coffeeEntity.isPresent()) {
            coffeeDTO.setIdCoffee(coffeeEntity.get().getIdCoffee());
            coffeeDTO.setName(coffeeEntity.get().getName());
            coffeeDTO.setDescription(coffeeEntity.get().getDescription());
            coffeeDTO.setPrice(coffeeEntity.get().getPrice());
            coffeeDTO.setImage64(coffeeEntity.get().getImage64());
            return coffeeDTO;
        } else {
            return null;
        }
    }

    public void saveCoffee(CoffeeDTO coffeeDTO) {
        CoffeeEntity coffeeEntity = new CoffeeEntity();
        coffeeEntity.setName(coffeeDTO.getName());
        coffeeEntity.setDescription(coffeeDTO.getDescription());
        coffeeEntity.setPrice(coffeeDTO.getPrice());
        coffeeEntity.setImage64(coffeeDTO.getImage64());
        coffeeRepository.save(coffeeEntity);
    }

    public void deleteCoffee(Integer idCoffee) {
        coffeeRepository.deleteById(idCoffee);
    }

    public CoffeeEntity updateCoffee(CoffeeDtoPut coffeeDTO, Integer idCoffee) {
        Optional<CoffeeEntity> coffeeEntity = coffeeRepository.findById(idCoffee);
        if (coffeeEntity.isPresent()) {
            CoffeeEntity coffeeEntity1 = coffeeEntity.get();
            coffeeEntity1.setName(coffeeDTO.getName());
            coffeeEntity1.setDescription(coffeeDTO.getDescription());
            coffeeEntity1.setPrice(coffeeDTO.getPrice());
            coffeeEntity1.setImage64(coffeeDTO.getImage64());
            coffeeRepository.save(coffeeEntity1);
            return coffeeEntity1;
        } else {
            return null;
        }

    }

    @Override
    public CoffeeDTO findByName(String name) {
        Optional<CoffeeEntity> coffeeEntityOptional = coffeeRepository.findByName(name);
        
        if (coffeeEntityOptional.isPresent()) {
            CoffeeEntity coffeeEntity = coffeeEntityOptional.get();
            
            // Construir el CoffeeDTO directamente sin un método convertToDTO
            CoffeeDTO coffeeDTO = new CoffeeDTO();
            coffeeDTO.setIdCoffee(coffeeEntity.getIdCoffee());
            coffeeDTO.setName(coffeeEntity.getName());
            coffeeDTO.setDescription(coffeeEntity.getDescription());
            coffeeDTO.setPrice(coffeeEntity.getPrice());
            coffeeDTO.setImage64(coffeeEntity.getImage64());
            
            return coffeeDTO;
        } else {
            throw new RuntimeException("Coffee not found with name: " + name);
        }
    }

    // Método para convertir TestimonialsEntity a TestimonialDTO
    private List<TestimonialDTO> convertTestimonialsToDTOs(List<TestimonialsEntity> testimonials, int coffeeId) {
        List<TestimonialDTO> testimonialDTOs = new ArrayList<>();

        for (TestimonialsEntity testimonialEntity : testimonials) {
            TestimonialDTO testimonialDTO = new TestimonialDTO();
            testimonialDTO.setIdTestimonials(testimonialEntity.getIdTestimonials());
            testimonialDTO.setTestimonial(testimonialEntity.getTestimonial());
            testimonialDTO.setUsername(testimonialEntity.getUsername());
            testimonialDTO.setIdCoffee(coffeeId);

            testimonialDTOs.add(testimonialDTO);
        }

        return testimonialDTOs;
    }
}
