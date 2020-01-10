package nl.fuchsia.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import nl.fuchsia.model.Feit;
import org.springframework.stereotype.Repository;

@Repository
public class FeitRepository {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    private static final String GET_FEITEN ="SELECT feit FROM Feit feit";

    /**
     * Voegt een Feit toe aan de database
     * @param feit is het toe te voegen feit.
     */
    @Transactional
    public Feit addFeit(Feit feit) {
        entityManager.persist(feit);
        return feit;
    }

	/**
	 * Haalt het strafbare feit op basis van het feitnr.
	 *
	 * @param feitnr het feitnr van de op te halen feiten.
	 * @return het opgehaalde feit.
	 */
	@Transactional
    public Feit getFeitById(Integer feitnr){
    	return entityManager.find(Feit.class, feitnr);
	}

    /**
     * Haalt alle Feiten uit de database
     * @return een lijst met alle feiten
     */
    @Transactional
    public List<Feit> getFeiten() {
        TypedQuery<Feit> getAllFeiten = entityManager.createQuery(GET_FEITEN, Feit.class);
        return getAllFeiten.getResultList();
    }

    /**
     * wijzigt een bestaand feit.
     *
     * @param feit de te wijzigen feit.
     */
    @Transactional
    public Feit updateFeitById(Feit feit) {
        entityManager.merge(feit);
        return feit;
    }
}