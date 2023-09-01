package br.ufscar.dc.dsw;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufscar.dc.dsw.dao.IRestauranteDAO;
import br.ufscar.dc.dsw.dao.IPratoDAO;
import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Restaurante;
import br.ufscar.dc.dsw.domain.Prato;
import br.ufscar.dc.dsw.domain.Usuario;

@SpringBootApplication
public class EmpregosApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpregosApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(IUsuarioDAO usuarioDAO, BCryptPasswordEncoder encoder, IRestauranteDAO restauranteDAO, IPratoDAO pratoDAO) {
		return (args) -> {
			
			Usuario u1 = new Usuario();
			u1.setUsername("admin");
			u1.setPassword(encoder.encode("admin"));
			u1.setCPF("123.456.789-10");
			u1.setName("Admin");
			u1.setRole("ROLE_ADMIN");
			u1.setEnabled(true);
			usuarioDAO.save(u1);
			
			Usuario u2 = new Usuario();
			u2.setUsername("beltrano");
			u2.setPassword(encoder.encode("123"));
			u2.setCPF("987.654.321-00");
			u2.setName("Lucas");
			u2.setRole("ROLE_USER");
			u2.setEnabled(true);
			usuarioDAO.save(u2);
			
			Usuario u3 = new Usuario();
			u3.setUsername("Delano");
			u3.setPassword(encoder.encode("123"));
			u3.setCPF("147.258.369-10");
			u3.setName("Laura");
			u3.setRole("ROLE_USER");
			u3.setEnabled(true);
			usuarioDAO.save(u3);
			
			Restaurante r1 = new Restaurante();
			r1.setCNPJ("55.789.390/0008-99");
			r1.setNome("Massas da Livia");
			restauranteDAO.save(r1);
			
			Restaurante r2 = new Restaurante();
			r2.setCNPJ("71.150.470/0001-40");
			r2.setNome("Churrascaria Trevo");
			restauranteDAO.save(r2);
			
			Restaurante r3 = new Restaurante();
			r3.setCNPJ("32.106.536/0001-82");
			r3.setNome("Natsumi");
			restauranteDAO.save(r3);
			
			Prato p1 = new Prato();
			p1.setNome("Nhoque a Bolonhesa");
			p1.setDescricao("Nhoque feito de massa de batata com molho bolonhesa");
			p1.setPreco(BigDecimal.valueOf(25.9));
			p1.setRestaurante(r1);
			pratoDAO.save(p1);
			
			Prato p2 = new Prato();
			p2.setNome("Marmitex média - Mista");
			p2.setDescricao("Marmita deliciosa com arroz, feijão e carnes variadas");
			p2.setPreco(BigDecimal.valueOf(30.9));
			p2.setRestaurante(r2);
			pratoDAO.save(p2);
			
			Prato p3 = new Prato();
			p3.setNome("Temaki de Salmão");
			p3.setDescricao("Temaki de salmão cru com arroz, cream cheese e cebolinha");
			p3.setPreco(BigDecimal.valueOf(35.9));
			p3.setRestaurante(r3);
			pratoDAO.save(p3);
		};
	}
}
