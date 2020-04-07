package br.gov.ba.seia.auditing;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.Resource;


@EnableJpaAuditing
@EnableJpaRepositories
@SpringBootApplication
public class SpringDataJpaAuditingApplication implements CommandLineRunner {

	@Resource FileRepository fileRepository;
	@Resource TesteRepository testeRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaAuditingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    	Teste teste1 = new Teste("teste1", "testando...");
    	testeRepository.save(teste1);
    	
        File file = new File("Java Notes", "Java is awesome", teste1);
        fileRepository.saveAndFlush(file);

        file.setName("First update");
        fileRepository.saveAndFlush(file);
        
        Teste teste2 = new Teste("teste2", "testando123");
    	testeRepository.saveAndFlush(teste2);
    	file.setTeste(teste2);
        
        file.setName("This is the second update");
        fileRepository.saveAndFlush(file);
        
        file.setName("This is the exclusion");
        fileRepository.delete(file);
    }
}











