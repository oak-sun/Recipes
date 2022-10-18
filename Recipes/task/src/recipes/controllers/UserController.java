package recipes.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recipes.models.dto.UserDto;
import recipes.services.UserService;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/register")
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody
                                             @Valid UserDto dto) {
        return service.add(dto)
                ?
                ResponseEntity
                        .ok().build()
                :
                ResponseEntity
                        .badRequest().build();
        }
    }

