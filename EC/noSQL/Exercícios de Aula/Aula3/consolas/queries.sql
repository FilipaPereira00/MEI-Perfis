/*QUERY A*/
/* QUERY A */
SELECT COUNT (*) AS Total
from TITULO;

/*QUERY B*/
SELECT COUNT (*) AS Total
from MUSICA;

/*QUERY C*/
SELECT COUNT (*) AS Total
from AUTOR;

/*QUERY D*/
SELECT COUNT (DISTINCT "id_editora") AS Total
from EDITORA;

/*QUERY E*/
SELECT * FROM TITULO T
    INNER JOIN AUTOR A ON T."id_autor"  = A."id_autor"
        WHERE A."nome" = 'Max Changmin';

/*QUERY F*/
SELECT * FROM TITULO
    WHERE EXTRACT(YEAR FROM TITULO."data_compra") = 1970;

/*QUERY G*/
SELECT (A."nome") FROM TITULO T
    INNER JOIN AUTOR A ON T."id_autor"  = A."id_autor"
    WHERE T."data_compra" = '2010-02-01' AND T."preco" = '12';

/*QUERY H*/
SELECT E."nome" FROM EDITORA E
    INNER JOIN TITULO T on E."id_editora"  = T."id_editora"
    INNER JOIN AUTOR A ON T."id_autor" = A."id_autor"
        WHERE T."data_compra" = '2010-02-01' AND T."preco" = 12;

/*QUERY I*/
SELECT R."data_review",R."conteudo" FROM REVIEW R
    INNER JOIN TITULO T on T."id_titulo" = R."id_titulo"
    WHERE T."titulo" = 'oh whoa oh';

/*QUERY J*/
SELECT R."data_review",R."conteudo" FROM REVIEW R
    INNER JOIN TITULO T on T."id_titulo" = R."id_titulo"
    WHERE T."titulo" = 'pump'
    ORDER BY "data_review" DESC;

/*QUERY K*/
SELECT (A."nome") FROM TITULO T
    INNER JOIN MUSICA M on T."id_titulo" = M."id_titulo"
    INNER JOIN AUTOR A on A."id_autor" = M."id_autor"
    WHERE T."preco" = 20 AND T."data_compra" = '1970-04-04';

/*QUERY L*/
SELECT SUM (T."preco") FROM TITULO T
    INNER JOIN EDITORA E on T."id_editora" = E."id_editora"
    WHERE E."nome" = 'EMI';

/*QUERY M*/
SELECT T."titulo" , T."data_compra" FROM TITULO T
    WHERE T."preco" = 20
    ORDER BY (T."data_compra") ASC
    FETCH NEXT 1 ROWS ONLY;

/*QUERY N*/
SELECT COUNT (*) FROM TITULO T
    INNER JOIN SUPORTE S on T."id_suporte" = S."id_suporte"
    WHERE S."nome" = 'MP3';

/*QUERY O*/
SELECT T."titulo" FROM TITULO T
    INNER JOIN SUPORTE S on T."id_suporte" = S."id_suporte"
    INNER JOIN GENERO G on T."id_genero" = G."id_genero"
    WHERE S."nome" = 'MP3' AND G."nome" = 'Pop Rock' ;

/*QUERY P*/
SELECT SUM(T."preco") FROM TITULO T
    INNER JOIN SUPORTE S on T."id_suporte" = S."id_suporte"
    WHERE S."nome" = 'Blue-Ray';

/*QUERY Q*/
SELECT SUM(T."preco") FROM TITULO T
    INNER JOIN EDITORA E ON T."id_editora" = E."id_editora"
    INNER JOIN SUPORTE S on T."id_suporte" = S."id_suporte"
    WHERE S."nome" = 'Blue-Ray' AND E."nome" = 'EMI';

/*QUERY R*/
SELECT  SUM(T."preco") FROM TITULO T;

/*QUERY S*/
SELECT E."nome", SUM(T."preco") AS Total FROM TITULO T
    INNER JOIN EDITORA E on E."id_editora" = T."id_editora"
    GROUP BY E."nome"
    ORDER BY Total DESC
    FETCH NEXT 1 ROWS ONLY ;

/*QUERY T*/
SELECT E."nome" , COUNT(*) AS Total FROM TITULO T
    INNER JOIN EDITORA E on E."id_editora" = T."id_editora"
    INNER JOIN GENERO G on T."id_genero" = G."id_genero"
    WHERE G."nome" = 'Heavy Metal'
    GROUP BY E."nome"
    ORDER BY Total DESC
    FETCH NEXT 1 ROWS  ONLY ;


SELECT E."nome", COUNT(*) TOTAL FROM EDITORA E
    JOIN TITULO T on E."id_editora" = T."id_editora"
        JOIN GENERO G on G."id_genero" = T."id_genero"
            WHERE G."nome" = 'Heavy Metal'
                GROUP BY E."nome"
                    ORDER BY TOTAL DESC
                    FETCH NEXT 1 ROWS WITH TIES;