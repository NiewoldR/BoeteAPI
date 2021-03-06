package nl.fuchsia.repository.historie;

import nl.fuchsia.model.Persoon;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PersoonRepositoryOrm {

    private static final String GET_PERSONEN = "SELECT persoon FROM Persoon persoon";
    /**
     * maakt een {@link EntityManager} t.b.v. de {@link PersistenceContext}.
     */
    //unitName verwijst naar de naam van de bean in DatabaseConfig.java, entityManagerFactory.
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    /**
     * Haalt een lijst van alle personen uit de database m.b.v. ornm.
     *
     * @return Lijst van personen.
     */
    @Transactional
    public List<Persoon> getPersonen() {
        TypedQuery<Persoon> getAllPersonen = entityManager.createQuery(GET_PERSONEN, Persoon.class);
        return getAllPersonen.getResultList();
    }

    /**
     * Haalt de persoon op op basis van het persoonnr.
     *
     * @param persoonnr het persoonnr van de op te halen persoon.
     * @return de opgehaalde persoon.
     */
    @Transactional
    public Persoon getPersoonById(Integer persoonnr) {
        return entityManager.find(Persoon.class, persoonnr);
    }

    /**
     * Voegt een nieuwe persoon toe.
     *
     * @param persoon de toe te voegen persoon.
     */
    @Transactional
    public Persoon addPersoon(Persoon persoon) {
        entityManager.persist(persoon);
        return persoon;
    }

    /**
     * wijzigt een bestaande persoon.
     *
     * @param persoon de te wijzigen persoon.
     */
    @Transactional
    public Persoon updatePersoonById(Persoon persoon) {
        entityManager.merge(persoon);
        return persoon;
    }
}
