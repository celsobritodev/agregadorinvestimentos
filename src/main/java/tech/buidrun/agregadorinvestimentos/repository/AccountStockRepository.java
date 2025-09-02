package tech.buidrun.agregadorinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.buidrun.agregadorinvestimentos.entity.AccountStock;
import tech.buidrun.agregadorinvestimentos.entity.AccountStockId;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock,AccountStockId>{

}
