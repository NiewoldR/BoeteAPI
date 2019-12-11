package nl.fuchsia.configuration;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.vendor.Database;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("nl.fuchsia.repository")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TestDatabaseConfig extends AbstractDatabaseConfig {

    @Override
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Override
    public Database getDatabaseType() {
        return Database.H2;
    }

    @Override
    protected Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_ONLY);
        return properties;
    }
}