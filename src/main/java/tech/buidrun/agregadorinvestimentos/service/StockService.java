package tech.buidrun.agregadorinvestimentos.service;

import org.springframework.stereotype.Service;

import tech.buidrun.agregadorinvestimentos.controller.dto.CreateStockDto;
import tech.buidrun.agregadorinvestimentos.entity.Stock;
import tech.buidrun.agregadorinvestimentos.repository.StockRepository;

@Service
public class StockService {
	
	private StockRepository stockRepository;

	public StockService(StockRepository stockRepository) {
	
		this.stockRepository = stockRepository;
	}

	public void createStock(CreateStockDto createStockDto) {
		
		// DTO -> ENTITY
		var stock = new Stock(
			createStockDto.stockId(),
			createStockDto.description()
				
				);
		stockRepository.save(stock);
				
		
	}
	
	
	

}
