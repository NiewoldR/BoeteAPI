package nl.fuchsia.it.persoonController;

import com.consol.citrus.actions.ExecuteSQLQueryAction;
import com.consol.citrus.annotations.CitrusEndpoint;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.config.CitrusSpringConfig;
import com.consol.citrus.dsl.runner.TestRunner;
import com.consol.citrus.http.client.HttpClient;
import cucumber.api.java.nl.Als;
import cucumber.api.java.nl.Dan;
import cucumber.api.java.nl.Gegeven;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;
import java.util.Random;

@ContextConfiguration(classes = CitrusSpringConfig.class)
public class GetPersoonById {
	/**
	 * Test runner filled with actions by step definitions
	 */
	@CitrusResource
	private TestRunner runner;

	@CitrusEndpoint
	private HttpClient boeteApiClient;

	@Autowired
	private DataSource dataSource;

	private String persoonnr;

	@Gegeven("er zitten {int} personen in de database.")
	public void generateRandomPeople(int nrOfPeople) {
		runner.plsql(sqlBuilder -> sqlBuilder
			.dataSource(dataSource)
			.statement("DELETE FROM persoon")
		);
		createRandomPersonen(nrOfPeople);
		ExecuteSQLQueryAction query = runner.query(action -> action
			.dataSource(dataSource)
			.statement("SELECT PERSOONNR FROM public.persoon LIMIT 1")
			.extract("PERSOONNR", "selectPersoonnr")
		);
		persoonnr = runner.variable("selectPersoonnr", "${selectPersoonnr}");
	}

	@Als("de client een GETbyID request maakt naar {string} met persoonnr")
	public void callUrl(String url) {
		String urlPersoonnr =url.concat(persoonnr);
		runner.http(
			httpActionBuilder -> httpActionBuilder
				.client(boeteApiClient)
				.send()
				.get(urlPersoonnr)
		);
		System.out.println("Hallo");
	}

	@Dan("moet de HTTP status code {int} zijn en huisnummer moet {int} zijn in de response")
	public void verifyResponse(int httpStatusCode, int huisnummer) {
		runner.http(httpActionBuilder -> httpActionBuilder
			.client(boeteApiClient)
			.receive()
			.response(HttpStatus.valueOf(httpStatusCode))
			.validate("$.huisnummer", String.valueOf(huisnummer))
		);
	}

	private void createRandomPersonen(int nrOfPeople) {
		Random random = new Random();
		for (int i = 0; i < nrOfPeople; i++) {
			String bsn = String.valueOf(random.nextInt(899999999) + 100000000);
			int huisnummer = 50 + i;
			runner.plsql(sqlBuilder -> sqlBuilder
				.dataSource(dataSource)
				.statement("INSERT INTO persoon (voornaam, achternaam, straat, huisnummer, postcode, woonplaats, bsn, geboortedatum) " +
					"VALUES ('Gerard', 'Derf', 'Fredstraat', " + huisnummer + ", '1234 KL', 'Groningen', " + bsn + ", '01-01-2000')"));
		}
	}
}
