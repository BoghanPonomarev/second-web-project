package ua.nure.ponomarev.enity;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Bogdan_Ponamarev.
 */
@Entity
@Table(name = "work_day_hours")
public class WorkHoursOfDay {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "day",nullable = false)
    @NotEmpty
    @NotNull
    private DayOfWeek dayOfWeek;

    @NotNull
    @Column(name = "opening" ,nullable = false)
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "hh:mm")
    private Date opening;

    @NotNull
    @Column(name = "closing" ,nullable = false)
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "hh:mm")
    private Date closing;

    public WorkHoursOfDay(Date opening, Date closing) {
        validation();
        this.opening = opening;
        this.closing = closing;
    }

    private void validation() throws IllegalArgumentException {
        if (opening.after(closing)) {
            throw new IllegalArgumentException("Opening can`t be after closing");
        }
    }
    public enum DayOfWeek {
        MONDAY(1),
        TUESDAY(2),
        WEDNESDAY(3),
        THURSDAY(4),
        FRIDAY(5),
        SATURDAY(6),
        SUNDAY(7);

        private int number;

        DayOfWeek(int numberOfDay) {
            number = numberOfDay;
        }

        public int getDayNumber(){
            return number;
        }
    }
}
