package ua.nure.ponomarev.enity;

import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
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
@Table(name = "hall")
@Component
@Scope("prototype")
public class Hall {

    @Id
    @GeneratedValue
    private int id;
    @Min(0)
    @Column(name = "hall_number")
    private int hallNumber;
    @Column(name = "color")
    private String color;
    @Min(1)
    @Column(name = "place_quantity")
    private int placeQuantity;
    @Column(name = "places")
    @OneToMany(targetEntity = PlaceStatus.class, cascade = CascadeType.ALL)
    @Convert(converter = ua.nure.ponomarev.converter.PlaceConverter.class)
    private List<PlaceStatus> placeStatuses;

    public Hall(int placeQuantity) {
        this.placeQuantity = placeQuantity;
        placeStatuses = new ArrayList<>(placeQuantity);
        for (int i = 0; i < placeQuantity; i++) {
            placeStatuses.add(PlaceStatus.FREE);
        }
    }

}
