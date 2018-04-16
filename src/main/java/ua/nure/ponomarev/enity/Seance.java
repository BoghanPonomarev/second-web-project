package ua.nure.ponomarev.enity;

import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

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
public class Seance {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(targetEntity = Film.class)
    private Film film;

    @Min(0)
    @NotNull
    @Column(name = "price")
    private BigDecimal price;

    @Future
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    private Date startTime;

    @Future
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    private Date endTime;

    @NotNull
    @OneToOne(targetEntity = Hall.class)
    private Hall hall;
}
