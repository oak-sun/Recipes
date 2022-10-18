package recipes.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import recipes.models.Recipe;
import recipes.models.dto.RecipeDto;
import recipes.services.RecipeService;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
@AllArgsConstructor
public class RecipeController {
    private final RecipeService service;

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable @Min(1) Long id) {
        return  service
                .get(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND));
    }

    @PostMapping("/new")
    public RecipeDto add(@RequestBody
                             @Valid
                             Recipe recipe,
                         @AuthenticationPrincipal
                         UserDetails details) {
        return service
                .add(recipe, details.getUsername());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id,
                                             @AuthenticationPrincipal
                                             UserDetails details) {
        try {
            return service
                    .delete(id, details.getUsername())
                    ?
                    ResponseEntity.noContent().build()
            :
                ResponseEntity.notFound().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRecipe(@PathVariable
                                                 Long id,
                                             @RequestBody
                                             @Valid
                                             Recipe recipe,
                                             @AuthenticationPrincipal
                                                 UserDetails details) {
        try {
            return service.update(id,
                                  recipe,
                                  details.getUsername())
                    ?
                    ResponseEntity
                        .noContent()
                        .build()
                    :
                    ResponseEntity
                            .notFound()
                            .build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> search(@RequestParam(name = "category",
                                                             required = false)
                                                   String category,
                                               @RequestParam(name = "name",
                                                             required = false)
                                               String name) {

        if ((category == null && name == null)
                ||
                (category != null && name != null)) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        return category != null
                ?
                ResponseEntity
                        .ok()
                        .body(service.searchByCategory(category))
                :
                 ResponseEntity
                         .ok()
                         .body(service.searchByName(name));
    }
}
