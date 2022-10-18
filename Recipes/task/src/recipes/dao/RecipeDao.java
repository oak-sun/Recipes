package recipes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recipes.models.Recipe;
import java.util.List;

@Repository
public interface RecipeDao extends JpaRepository<Recipe, Long> {
    List<Recipe>
    findByCategoryIgnoreCaseOrderByDateDesc
            (String category);
    List<Recipe>
    findByNameIgnoreCaseContainsOrderByDateDesc
            (String name);
}
