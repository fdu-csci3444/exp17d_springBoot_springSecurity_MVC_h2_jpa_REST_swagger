/**
 * 
 */
package exp17d.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import exp17d.jpa.model.Customer;
import exp17d.jpa.repository.CustomerRepository;

/**
 * @author ilker
 *
 */
@Component
public class InitDbCommandLineRunner4customer implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(InitDbCommandLineRunner4customer.class);

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public void run(String... args) throws Exception {
		logger.info("BEF InitDbCommandLineRunner4customer will make sure 'customer' table has rows");
			
		long countOfCustomers = customerRepository.count();
		if (countOfCustomers == 0) {
			logger.info("InitDbCommandLineRunner4customer will initialize 'customer' table with 10 rows");
			customerRepository.save(new Customer("Kemal", "Sunal"));
			customerRepository.save(new Customer("Inek", "Saban"));
			customerRepository.save(new Customer("Saban oglu", "Saban"));
			customerRepository.save(new Customer("Gerzek", "Saban"));
			customerRepository.save(new Customer("Ortadirek", "Saban"));
			customerRepository.save(new Customer("Sosyete", "Saban"));
			customerRepository.save(new Customer("Gurbetci", "Saban"));
			customerRepository.save(new Customer("Sen Dul", "Saban"));
			customerRepository.save(new Customer("Katma Deger", "Saban"));
			customerRepository.save(new Customer("Umudumuz", "Saban"));
		} else {
			logger.info("InitDbCommandLineRunner4customer will not do anything since 'customer' table already has rows");
		}
	}
}
