package com.sadikov.wallet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sadikov.wallet.DTOs.WalletOperationDTO;
import com.sadikov.wallet.model.OperationType;
import com.sadikov.wallet.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testMultipleBalanceOperationsWithConcurrency() throws Exception {

		String walletId = "abd94c59-17c6-49e8-8962-c164990c8b20";
		int initialBalance = walletRepository.findById(UUID.fromString(walletId)).get().getBalance();

		ExecutorService executor = Executors.newFixedThreadPool(4);

		for (int i = 0; i < 1000; i++) {
			int j = i;
			executor.execute(() -> {
				try {
					WalletOperationDTO dto = new WalletOperationDTO();
					dto.setWalletId(walletId);
					dto.setAmount(j % 2 == 0 ? 10 : 5);
					dto.setOperationType(j % 2 == 0 ? OperationType.DEPOSIT : OperationType.WITHDRAW);

					String jsonRequest = objectMapper.writeValueAsString(dto);

					mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wallet")
									.contentType(MediaType.APPLICATION_JSON)
									.content(jsonRequest))
							.andExpect(status().isOk());

				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}

		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);

		int finalBalance = walletRepository.findById(walletId).get().getBalance();

		if((finalBalance - 2500) == initialBalance){
			System.out.println("nice");
		}
	}
}

