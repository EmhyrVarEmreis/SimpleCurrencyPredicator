package xyz.morecraft.dev.scp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xyz.morecraft.dev.scp.dto.CurrencyHistoryTable;
import xyz.morecraft.dev.scp.neural.NeuralMath;
import xyz.morecraft.dev.scp.neural.SimpleLayeredNeuralNetwork;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
public class CurrencyService {

    public ResponseEntity<CurrencyHistoryTable> getCurrencyHistory(String table, String code, LocalDate startDate, LocalDate endDate) {
        final RestTemplate restTemplate = new RestTemplate();
        final LocalDate endDateTmp = endDate.isAfter(LocalDate.now()) ? LocalDate.now() : endDate;
        final ResponseEntity<CurrencyHistoryTable> entity = restTemplate.getForEntity(
                "http://api.nbp.pl/api/exchangerates/rates/" + table + "/" + code + "/" + startDate + "/" + endDateTmp + "/?format=json",
                CurrencyHistoryTable.class
        );
        final CurrencyHistoryTable currencyHistoryTable = entity.getBody();
        if (!endDateTmp.isEqual(endDate)) {
            final int days = (int) ChronoUnit.DAYS.between(endDateTmp, endDate);
            final ZoneId zone = ZoneId.systemDefault();

            final List<CurrencyHistoryTable.CurrencyHistoryEntry> rates = currencyHistoryTable.getRates();

            final double[][] trainingSetInputs = new double[rates.size()][4];
            final double[][] trainingSetOutputs = new double[1][rates.size()];

            final double outMin = rates.stream().map(CurrencyHistoryTable.CurrencyHistoryEntry::getMid).min(Double::compare).orElse(0.0);
            final double outMax = rates.stream().map(CurrencyHistoryTable.CurrencyHistoryEntry::getMid).max(Double::compare).orElse(0.0);
            final double inMin = startDate.atStartOfDay(zone).toEpochSecond();
            final double inMax = endDate.atStartOfDay(zone).toEpochSecond();

            for (int i = 0; i < rates.size(); i++) {
                final CurrencyHistoryTable.CurrencyHistoryEntry entry = rates.get(i);
                final long epochSecond = entry.getEffectiveDate().atStartOfDay(zone).toEpochSecond();
                trainingSetInputs[i][0] = (1.0 * epochSecond - inMin) / (inMax - inMin);
                trainingSetInputs[i][1] = i > 0 ? trainingSetInputs[i - 1][0] : 0;
                trainingSetInputs[i][2] = i > 1 ? trainingSetInputs[i - 2][0] : 0;
                trainingSetInputs[i][3] = i > 2 ? trainingSetInputs[i - 3][0] : 0;
                trainingSetOutputs[0][i] = (entry.getMid() - outMin) / (outMax - outMin);
            }

            //System.out.println(Arrays.deepToString(trainingSetInputs));
            //System.out.println(Arrays.deepToString(trainingSetOutputs));

            SimpleLayeredNeuralNetwork neuralNetwork = new SimpleLayeredNeuralNetwork();
            neuralNetwork.setTrainFactor(0.9);
            neuralNetwork.addLayer(2, 4);
            neuralNetwork.addLayer(1, 2);
            neuralNetwork.train(trainingSetInputs, NeuralMath.transpose(trainingSetOutputs), 5);
            for (int i = 0; i < days; i++) {
                final LocalDate localDate = endDateTmp.plusDays(i);
                final long epochSecond = localDate.atStartOfDay(zone).toEpochSecond();
                final double mid = neuralNetwork.thinkOutput(new double[][]{{(epochSecond - inMin) / (inMax - inMin)}})[0];
                //System.out.println((epochSecond - inMin) / (inMax - inMin)+" "+mid);
                rates.add(
                        new CurrencyHistoryTable.CurrencyHistoryEntry(
                                "",
                                localDate,
                                (mid * (outMax - outMin) + outMin),
                                true
                        )
                );
            }
        }
        return new ResponseEntity<>(currencyHistoryTable, HttpStatus.OK);
    }

}
