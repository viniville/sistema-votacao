create table pauta (
    id serial not null primary key,
    titulo varchar(100) not null CONSTRAINT pauta_titulo_uk UNIQUE,
    descricao text,
    dt_cadastro timestamp,
    dt_modificacao timestamp
);

create table sessao_votacao (
    id serial not null primary key,
    id_pauta bigint not null CONSTRAINT sessao_votacao_pauta_uk UNIQUE,
    dh_abertura timestamp,
    dh_fechamento timestamp,
    dt_cadastro timestamp,
    dt_modificacao timestamp
    FOREIGN KEY (id_pauta) REFERENCES pauta (id)
);

CREATE INDEX sessao_votacao_id_pauta_idx ON sessao_votacao USING btree (id_pauta);

create table voto_associado (
    id serial not null primary key,
    id_sessao_votacao bigint not null,
    cpf_associado varchar(11) not null,
    voto varchar(3) not null,
    dt_cadastro timestamp,
    dt_modificacao timestamp
    CONSTRAINT voto_associado_sessa_cpf_uk UNIQUE (id_sessao_votacao, cpf_associado),
    FOREIGN KEY (id_sessao_votacao) REFERENCES sessao_votacao (id)
);

alter table voto_associado add constraint voto_associado_check_voto CHECK (voto = 'SIM' OR voto = 'NAO');

CREATE INDEX voto_associado_id_sessao_votacao_idx ON voto_associado USING btree (id_sessao_votacao);