package com.jakarta.mail.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Classe que carrega as credenciais do Gmail a partir de um arquivo de propriedades.
 */
public class Credenciais {

    public Credenciais() {
    }

    public static Properties carregar(String caminhoArquivoCredenciais) throws IOException {

        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(caminhoArquivoCredenciais);
        props.load(fis);
        fis.close();

        return props;
    }
}
