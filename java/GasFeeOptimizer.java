import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GasFeeOptimizer {

    public static class GasRecord {
        LocalDateTime timestamp;
        double gasPriceGwei;

        public GasRecord(LocalDateTime timestamp, double gasPriceGwei) {
            this.timestamp = timestamp;
            this.gasPriceGwei = gasPriceGwei;
        }

        @Override
        public String toString() {
            return String.format("[%s] Gás: %.1f Gwei", timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss")), gasPriceGwei);
        }
    }

    public static class OptimizedTransaction {
        String txDetails;
        double favorableGasThresholdGwei;
        LocalDateTime deadline;
        double initialGasPriceGwei;

        public OptimizedTransaction(String txDetails, double favorableGasThresholdGwei, LocalDateTime deadline, double initialGasPriceGwei) {
            this.txDetails = txDetails;
            this.favorableGasThresholdGwei = favorableGasThresholdGwei;
            this.deadline = deadline;
            this.initialGasPriceGwei = initialGasPriceGwei;
        }
    }

    private final List<GasRecord> gasHistory = new ArrayList<>();

    public void recordGasPrice(double gasGwei) {
        gasHistory.add(new GasRecord(LocalDateTime.now(), gasGwei));
    }

    // Requisito 01: Regista o preço do gás ao longo do tempo e mostra o histórico recente[cite: 15]
    public void showGasHistory() {
        System.out.println("\n--- Histórico Recente de Preços de Gás ---");
        for (GasRecord record : gasHistory) {
            System.out.println(" - " + record);
        }
    }

    // Requisitos 02, 03 & 04: Aguarda até gás favorável ou limite de prazo, reporta custos e nunca falha o prazo[cite: 15]
    public void executeWithOptimization(OptimizedTransaction tx, List<Double> simulatedGasStream) {
        System.out.println("\n=== AVALIAÇÃO DO GAS FEE OPTIMIZER ===");
        System.out.println("Transação: " + tx.txDetails);
        System.out.println("Preço no momento do pedido: " + tx.initialGasPriceGwei + " Gwei");
        System.out.println("Limite favorável desejado: " + tx.favorableGasThresholdGwei + " Gwei");

        int step = 0;
        double finalGasPaid = 0.0;
        String executionStatus = "";

        for (double currentGas : simulatedGasStream) {
            LocalDateTime currentTime = LocalDateTime.now().plusMinutes(step * 5);
            recordGasPrice(currentGas);
            
            System.out.printf("[%s] Gás atual: %.1f Gwei\n", currentTime.format(DateTimeFormatter.ofPattern("HH:mm")), currentGas);

            // Requisito 04: Nunca falha o prazo, submete no limite mesmo se o gás estiver alto[cite: 15]
            if (currentTime.isAfter(tx.deadline) || currentTime.isEqual(tx.deadline)) {
                finalGasPaid = currentGas;
                executionStatus = "SUBMETIDO NO PRAZO LIMITE (Deadline atingido, enviado independentemente do gás)";
                break;
            }

            // Requisito 02: Retém até o gás se tornar favorável ou o prazo ser atingido[cite: 15]
            if (currentGas <= tx.favorableGasThresholdGwei) {
                finalGasPaid = currentGas;
                executionStatus = "SUBMETIDO COM SUCESSO (Preço favorável atingido antes do prazo)";
                break;
            }

            step++;
        }

        // Requisito 03: Reporta o gás efetivamente pago comparado com o preço no momento do pedido[cite: 15]
        System.out.println("\n--- Relatório Final de Execução ---");
        System.out.println("Estado: " + executionStatus);
        System.out.printf("Gás no momento do pedido: %.1f Gwei\n", tx.initialGasPriceGwei);
        System.out.printf("Gás efetivamente pago: %.1f Gwei\n", finalGasPaid);
        
        double savings = tx.initialGasPriceGwei - finalGasPaid;
        if (savings > 0) {
            System.out.printf("✅ Poupança conseguida: %.1f Gwei\n", savings);
        } else {
            System.out.printf("⚠️ Sem poupança adicional (prazo limite atingido com gás elevado).\n");
        }
    }

    public static void main(String[] args) {
        GasFeeOptimizer optimizer = new GasFeeOptimizer();

        LocalDateTime deadlineTime = LocalDateTime.now().plusMinutes(15);
        OptimizedTransaction tx = new OptimizedTransaction("Transfer 500 USDC", 25.0, deadlineTime, 35.0);

        // Simulando a evolução do gás ao longo do tempo
        List<Double> gasStream = new ArrayList<>();
        gasStream.add(35.0); // Pedido inicial
        gasStream.add(30.0);
        gasStream.add(24.0); // Atinge o limiar favorável (<= 25.0)

        optimizer.executeWithOptimization(tx, gasStream);
        optimizer.showGasHistory();
    }
}
