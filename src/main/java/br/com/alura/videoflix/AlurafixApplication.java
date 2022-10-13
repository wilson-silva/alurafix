package br.com.alura.videoflix;

import br.com.alura.videoflix.domain.enums.Cor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlurafixApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlurafixApplication.class, args);

		System.out.println(Cor.AMARELO.getDescricao());
		System.out.println(Cor.AMARELO);
		System.out.println(Cor.corPorCorHex("#FFFF00"));

	}

}
