CREATE TABLE processos (
	id BIGINT NOT NULL AUTO_INCREMENT,
	eh_oficio TINY INT NOT NULL,
        numero VARCHAR(100) NOT NULL,
        interessado_id BIGINT NOT NULL,
	assunto INT NOT NULL,
	situacao INT NOT NULL,
	orgao_origem INT NOT NULL,
        observacao VARCHAR(255),
	data_entrada DATE,
	orgao_origem INT NOT NULL,
	data_saida DATE,
	orgao_saida INT,

        PRIMARY KEY (id),
	INDEX (interessado_id),
	FOREIGN KEY (interessado_id)
        	REFERENCES interessados(id)
	        ON DELETE CASCADE	
) ENGINE = InnoDB;
