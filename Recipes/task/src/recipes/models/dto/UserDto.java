package recipes.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Pattern(regexp = ".+@.+\\..+")
    private String email;
    @NotBlank
    @Size(min = 8)
    private String password;
}
