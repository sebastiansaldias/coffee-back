package cl.ucm.coffee.persitence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "coffee")
@Getter
@Setter
@NoArgsConstructor
public class CoffeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coffee", nullable = false)
    private int idCoffee;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 30)
    private String description;

    @Column(nullable = false)
    private int price;

    @Lob
    private  String image64;

    @OneToMany(mappedBy = "coffee", fetch = FetchType.EAGER)
    private List<TestimonialsEntity> testimonials;


}
