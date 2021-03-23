
    create table Immagine (
        id int8 not null,
        bufferedImage bytea,
        nome varchar(255),
        primary key (id)
    );

    create table Posizione (
        id int8 not null,
        maxi int4 not null,
        maxj int4 not null,
        mini int4 not null,
        minj int4 not null,
        primary key (id)
    );

    create table Struttura (
        id int8 not null,
        anno int4 not null,
        area float8 not null,
        contrasto float8 not null,
        falseDetectionProbability float8 not null,
        lunghezzaMedia float8 not null,
        immagine_id int8,
        posizione_id int8,
        primary key (id)
    );

    alter table Struttura 
        add constraint FK80FEDCE0BB9F089E 
        foreign key (immagine_id) 
        references Immagine;

    alter table Struttura 
        add constraint FK80FEDCE05568FA76 
        foreign key (posizione_id) 
        references Posizione;

    create table hibernate_sequences (
         sequence_name varchar(255),
         sequence_next_hi_value int4 
    ) ;
