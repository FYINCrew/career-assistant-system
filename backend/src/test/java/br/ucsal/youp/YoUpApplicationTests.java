package br.ucsal.youp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // Adicione esta linha
class YoUpApplicationTests {

    @Test
    void contextLoads() {
        // Teste vazio apenas para verificar se o contexto Spring carrega
    }
}