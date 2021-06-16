package org.kodigo.airport.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.kodigo.airport.utilities.Email;
import org.kodigo.airport.utilities.IFileManager;
import org.kodigo.airport.utilities.FlightExcelFile;
import org.kodigo.airport.utilities.IMessageSender;

@Data
@AllArgsConstructor
public class GenerateAndSendReportOption implements IOption {
  private String description;

  @Override
  public void execute() {
    IFileManager flightExcel = new FlightExcelFile();
    flightExcel.writeFile();
    IMessageSender messageSender = new Email();
    messageSender.sendMessage();
  }
}
