select count(1)
from (select distinct SQ_CANDIDATO from candidatos) as tmp;

select count(1), c.ano_eleicao
from brasil_io.candidatos c
group by c.ano_eleicao;
select count(1)
from brasil_io.bens_candidatos;

select distinct c.sigla_partido from brasil_io.candidatos c;

select c.nome, c.numero_sequencial, floor(bens.total_bens)
from brasil_io.candidatos c
       join (select c.numero_sequencial, sum(bc.valor) as total_bens
             from brasil_io.candidatos c
                    join brasil_io.bens_candidatos bc on bc.numero_sequencial = c.numero_sequencial
             where c.nome_urna like '%MAMAE FALEI%'
             group by c.numero_sequencial) as bens on bens.numero_sequencial = c.numero_sequencial
order by bens.total_bens desc;

select * from brasil_io.bens_candidatos where numero_sequencial=250000605731;



select c.numero_sequencial, c.nome, floor(bens.total_bens)
from brasil_io.candidatos c
       join (select c.numero_sequencial, sum(bc.valor) as total_bens
             from brasil_io.candidatos c
                    join brasil_io.bens_candidatos bc on bc.numero_sequencial = c.numero_sequencial
              where bc.ano_eleicao='2018' and c.ano_eleicao='2018'
             group by c.numero_sequencial) as bens on bens.numero_sequencial = c.numero_sequencial
order by bens.total_bens desc;

select sum(valor) from bens_candidatos where ano_eleicao='2018' and numero_sequencial=250000600423;



select c.numero_sequencial, sum(bc.valor) as total_bens
             from brasil_io.candidatos c
                    join brasil_io.bens_candidatos bc on bc.numero_sequencial = c.numero_sequencial
             group by c.numero_sequencial order by total_bens desc ;
