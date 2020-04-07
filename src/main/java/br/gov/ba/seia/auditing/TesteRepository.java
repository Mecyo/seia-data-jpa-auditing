package br.gov.ba.seia.auditing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TesteRepository extends JpaRepository<Teste, Integer> {
}