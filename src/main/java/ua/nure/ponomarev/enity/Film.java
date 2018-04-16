package ua.nure.ponomarev.enity;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Bogdan_Ponamarev.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Component
@Entity
@Table(name = "film")
@Scope("prototype")
@NamedQueries({
        @NamedQuery(name = "Film.getAll", query = "FROM Film"),
})
public class Film {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 15, message = "Name of film must be between 2 or 15 symbols")
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "duration")
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "hh:mm")
    private Date duration;

    @Column(name = "rating")
    @Min(0)
    @Max(10)
    private int rating;

    @Lazy
    @Column(name = "description")
    private String description;
}
