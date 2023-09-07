package com.enviro.assessment.grad001.musawenkosimahlangu.service;

import com.enviro.assessment.grad001.musawenkosimahlangu.model.WithdrawalNotice;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class CsvFileExportService {

    @Value("${user.home}")
    private String userHome;

    public void exportNoticesToCsv(List<WithdrawalNotice> data, String fileName) throws IOException {
        String tempDir = System.getProperty("java.io.tmpdir");
        Path tempFile = Paths.get(tempDir, fileName);

        try (FileWriter writer = new FileWriter(tempFile.toFile());
             CSVWriter csvWriter = new CSVWriter(writer)) {

            String[] header = {"Notice ID", "Withdrawal Date", "Amount"};
            csvWriter.writeNext(header);


            for (WithdrawalNotice notice : data) {
                String[] rowData = {
                        String.valueOf(notice.getId()),
                        notice.getWithdrawalDate().toString(),
                        String.valueOf(notice.getWithdrawalAmount())
                };
                csvWriter.writeNext(rowData);
            }
        }


        String downloadsDir = userHome + "/Downloads";


        Path destination = Paths.get(downloadsDir, fileName);
        Files.move(tempFile, destination, StandardCopyOption.REPLACE_EXISTING);
    }
}
