package recipes.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RECIPES")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String category;

    private LocalDateTime date;

    @Column(name = "created_by")
    @JsonIgnore
    private String creator;

    @NotNull
    @Size(min = 1)
    @ElementCollection
    @CollectionTable(name = "ingredients",
                     joinColumns =
                     @JoinColumn(name = "recipe_id"))
    @Column(name = "ingredients")
    private List<String> ingredients;

    @NotNull
    @Size(min = 1)
    @ElementCollection
    @CollectionTable(name = "directions",
                     joinColumns =
                     @JoinColumn(name = "recipe_id"))
    @Column(name = "directions")
    private List<String> directions;
}
