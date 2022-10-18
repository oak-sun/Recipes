package recipes.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import recipes.dao.RecipeDao;
import recipes.models.Recipe;
import recipes.models.dto.RecipeDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RecipeService {
    private final RecipeDao dao;

    public RecipeDto add(Recipe recipe, String username) {
        recipe.setDate(LocalDateTime.now());
        recipe.setCreator(username);
        return new RecipeDto(
                dao.save(recipe).getId());
    }
    public Optional<Recipe> get(Long id) {
        return dao.findById(id);
    }
    public Boolean delete(Long id, String username) {
        if (dao.findById(id).isPresent()) {
            if (username.equals(dao
                                    .findById(id)
                                    .get()
                                    .getCreator())) {
                dao.deleteById(id);
                return true;
            } else {
                throw new IllegalArgumentException();
            }
        }
        return false;
    }

    public Boolean update(Long id,
                          Recipe recipe,
                          String username)
            throws IllegalArgumentException {

        if (dao.findById(id).isPresent()) {
            if (username.equals(dao
                            .findById(id)
                            .get()
                            .getCreator())) {
                recipe.setId(id);
                recipe.setDate(LocalDateTime.now());
                recipe.setCreator(dao
                        .findById(id)
                        .get()
                        .getCreator());
                dao.save(recipe);
                return true;
            } else {
                throw new IllegalArgumentException();
            }
        }
        return false;
    }

    public List<Recipe> searchByName(String term) {
        return dao
                .findByNameIgnoreCaseContainsOrderByDateDesc(
                        term);
    }

    public List<Recipe> searchByCategory(String term) {
        return dao
                .findByCategoryIgnoreCaseOrderByDateDesc(
                        term);
    }
}