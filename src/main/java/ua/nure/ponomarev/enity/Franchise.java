package ua.nure.ponomarev.enity;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "franchise")
@Component
public class Franchise {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @Column(name = "address", nullable = false)
    @NotNull
    @NotEmpty
    private String address;

    @Column(name="schedule")
    @OneToMany(targetEntity = WorkHoursOfDay.class,cascade = CascadeType.ALL)
    private List<WorkHoursOfDay> schedule;

    @Column(name = "description")
    @Lazy
    private String description;


}
