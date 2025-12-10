package vg.Alejandro.SotoCardenas.Hackaton.rest;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CorresponsalDto {
    private Long id;
    private String name;
    private String surnames;
    private String idDoc;
    private String country;
    private String department;
    private String province;
    private String district;
    private String locality;
    private Boolean status;
    private LocalDateTime registerDay;
}