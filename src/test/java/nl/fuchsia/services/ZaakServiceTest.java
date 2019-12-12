package nl.fuchsia.services;

import nl.fuchsia.model.Zaak;
import nl.fuchsia.repository.ListZaakReposistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ZaakServiceTest {

    @Mock
    private ListZaakReposistory listZaakReposistory;

    @InjectMocks
    private ZaakService zaakService;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void testAddZaak() {
        Zaak zaak = new Zaak();

        zaakService.addZaak(zaak);

        verify(listZaakReposistory).addZaak(zaak);
    }

    @Test
    public void testGetZaken() {
        zaakService.getZaken();

        verify(listZaakReposistory).getZaken();
    }
}