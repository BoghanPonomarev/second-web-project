package ua.nure.ponomarev.enity;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
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
@Scope("prototype")
public class Franchise {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @Column(name = "address", nullable = false)
    @NotNull
    @NotEmpty
    private String address;

    @Column(name = "open_close_schedule")
    @OneToMany(targetEntity = WorkHoursOfDay.class, cascade = CascadeType.ALL)
    private List<WorkHoursOfDay> openCloseSchedule;

    @Column(name = "description")
    @Lazy
    private String description;


}
