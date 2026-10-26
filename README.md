# Pharos Gas Fee Optimizer Agent

Um agente inteligente desenvolvido em **Java** para a Pharos Agent Arena concebido para monitorizar os preços de gás da rede e otimizar o momento de submissão de transações não urgentes para garantir o menor custo possível dentro de um prazo estipulado[cite: 15].

## 📋 Requisitos Cumpridos

1. **Monitorização e Histórico (Req. 01):** Regista e apresenta o histórico recente de preços de gás ao longo do tempo[cite: 15].
2. **Gestão de Prazos e Limiares (Req. 02):** Aceita transações com prazos definidos, retendo-as até que o preço do gás atinja um patamar favorável ou o limite de tempo seja atingido[cite: 15].
3. **Relatório de Custos (Req. 03):** Compara e reporta o gás efetivamente pago em relação ao preço registado no momento do pedido inicial[cite: 15].
4. **Respeito Absoluto por Prazos (Req. 04):** Garante que o prazo nunca é ultrapassado, efetuando a submissão obrigatória no limite de tempo estipulado mesmo se as taxas de gás permanecerem elevadas[cite: 15].

## 🚀 Como Executar

1. Certifique-se de ter o **Java JDK** instalado.
2. Compile o ficheiro:
   ```bash
   javac GasFeeOptimizer.java
