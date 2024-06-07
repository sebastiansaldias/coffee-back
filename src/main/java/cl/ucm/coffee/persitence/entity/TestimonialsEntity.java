package cl.ucm.coffee.persitence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "testimonials")
@Getter
@Setter
@NoArgsConstructor
public class TestimonialsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_testimonial", nullable = false)
    private int idTestimonials;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false, length = 30)
    private String testimonial;

    @Column(name="id_coffee", nullable = false, length = 30)
    private int idCoffee;




    @ManyToOne
    @JoinColumn(name = "id_coffee", referencedColumnName = "id_coffee", insertable = false, updatable = false)
    private CoffeeEntity coffee;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username",  insertable = false, updatable = false)
    private UserEntity user;


}
