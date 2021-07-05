package org.kodigo.airport.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kodigo.airport.Airport;
import org.kodigo.airport.entities.*;
import org.kodigo.airport.flight.*;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class FlightExcelFile implements IFileManager {

  private static final BufferedReader INPUT = Airport.INPUT;

  // Indices
  static int flightNumIndex = 0;
  static int airlineIndex = 1;
  static int aircraftIndex = 2;
  static int originCountryIndex = 3;
  static int originCityIndex = 4;
  static int departureDateIndex = 5;
  static int destinationCountryIndex = 6;
  static int destinationCityIndex = 7;
  static int arrivalDateIndex = 8;
  static int statusIndex = 9;

  @Override
  public void readFile() {
    System.out.println("Enter the path of the excel file: ");
    try (var file = new FileInputStream(INPUT.readLine())) {

      // Get the workbook instance for XLS file
      Workbook workbook = new XSSFWorkbook(file);

      // Get first sheet from the workbook
      var sheet = workbook.getSheetAt(0);

      getFlightsFromFile(sheet);

      System.out.println("Flight(s) added successfully");
    } catch (IOException e) {
      System.out.println("An error occurred when trying to process the excel file");
    }
  }

  private void getFlightsFromFile(Sheet sheet) throws IOException {
    // Iterate through each rows from first sheet
    for (Row row : sheet) {
      if (row.getRowNum() == 0) continue;
      // For each row, iterate through each columns
      int flightNumber = (int) row.getCell(flightNumIndex).getNumericCellValue();
      var airline = row.getCell(airlineIndex).getStringCellValue();
      var aircraft =
          AircraftCatalogue.getInstance()
              .getAircraftFromCatalogue(row.getCell(aircraftIndex).getStringCellValue());
      var originCountry = row.getCell(originCountryIndex).getStringCellValue();
      var originCity = row.getCell(originCityIndex).getStringCellValue();
      var departureDate =
          row.getCell(departureDateIndex)
              .getLocalDateTimeCellValue()
              .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
      var destinationCountry = row.getCell(destinationCountryIndex).getStringCellValue();
      var destinationCity = row.getCell(destinationCityIndex).getStringCellValue();
      var arrivalDate =
          row.getCell(arrivalDateIndex)
              .getLocalDateTimeCellValue()
              .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
      var status = Status.ON_TIME.toString();

      var flightCreator = new FlightCreator();

      IFlightDetails flightDetails = new FlightDetails(status, airline, aircraft);
      ICountry country = new Country(originCountry, destinationCountry);
      ICity city = new City(originCity, destinationCity);
      var date = new Date(departureDate, arrivalDate);

      IFlight flight = new Flight(flightNumber, flightDetails, country, city, date);

      flightCreator.addFlight(flight);
    }
  }

  @Override
  public void writeFile() {

    IFlightFilter flightFilter = new FlightFilter();
    List<IFlight> flights = flightFilter.filterFlight();
    if (flights.isEmpty()) {
      System.out.println("No flights found");
    }
    Map<IFlight, String> cancellationReport =
        getReport(FlightManager.getInstance().getCancellationReport(), flights, flightFilter);
    Map<IFlight, String> incidentReport =
            getReport(FlightManager.getInstance().getIncidentsReport(), flights, flightFilter);
    flights.sort(Comparator.comparing(IFlight::getFlightNumber));

    try (var outputStream = new FileOutputStream("AirportReport.xlsx");
        var workbook = new XSSFWorkbook()) {
      XSSFSheet flightSheet = workbook.createSheet("Flight Report");
      XSSFSheet cancellationSheet = workbook.createSheet("Cancellation Report");
      XSSFSheet incidentSheet = workbook.createSheet("Incident Report");

      final XSSFCellStyle cellStyle = getHeaderCellStyle(workbook);

      createFlightSheet(flights, flightSheet, cellStyle);
      createDescriptionSheet(cancellationReport, cancellationSheet, cellStyle);
      createDescriptionSheet(incidentReport, incidentSheet, cellStyle);

      System.out.println("Report created successfully");
      workbook.write(outputStream);
    } catch (FileNotFoundException e) {
      System.out.println("Path not found");
    } catch (IOException e) {
      System.out.println("An error occurred when creating excel file");
    }
  }

  private Map<IFlight, String> getReport(
      Map<IFlight, String> report, List<IFlight> flights, IFlightFilter filter) {
    return filter.filterDescriptions(report, flights);
  }

  private XSSFCellStyle getHeaderCellStyle(XSSFWorkbook workbook) {
    final XSSFCellStyle cellStyle = workbook.createCellStyle();
    cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    XSSFFont valueCellFont = workbook.createFont();
    valueCellFont.setColor(IndexedColors.WHITE.getIndex());
    valueCellFont.setBold(true);
    cellStyle.setFont(valueCellFont);
    return cellStyle;
  }

  private void createFlightSheet(
      List<IFlight> flights, XSSFSheet flightSheet, XSSFCellStyle cellStyle) {

    Row row = flightSheet.createRow(0);
    writeFlightHeader(row, cellStyle);

    var rowCount = 1;
    for (IFlight IFlight : flights) {
      row = flightSheet.createRow(rowCount);
      writeFlight(IFlight, row);
      rowCount++;
    }

    writeWeather(rowCount, flightSheet, cellStyle);
  }

  private void writeWeather(int rowCount, XSSFSheet flightSheet, XSSFCellStyle cellStyle) {
    IWeatherAPI weatherAPI = new WeatherAPI();
    Row row = flightSheet.createRow(++rowCount);
    var cell = row.createCell(0);
    cell.setCellStyle(cellStyle);
    cell.setCellValue("Weather Conditions");
    row = flightSheet.createRow(++rowCount);
    cell = row.createCell(0);
    cell.setCellValue(weatherAPI.getWeather());
  }

  private void writeFlightHeader(Row row, XSSFCellStyle cellStyle) {
    generateHeader(row, flightNumIndex, "Flight Number", cellStyle);
    generateHeader(row, airlineIndex, "Airline", cellStyle);
    generateHeader(row, aircraftIndex, "Aircraft", cellStyle);
    generateHeader(row, originCountryIndex, "Origin Country", cellStyle);
    generateHeader(row, originCityIndex, "Origin City", cellStyle);
    generateHeader(row, departureDateIndex, "Departure Date", cellStyle);
    generateHeader(row, destinationCountryIndex, "Destination Country", cellStyle);
    generateHeader(row, destinationCityIndex, "Destination City", cellStyle);
    generateHeader(row, arrivalDateIndex, "Arrival Date", cellStyle);
    generateHeader(row, statusIndex, "Status", cellStyle);
  }

  private void writeFlight(IFlight flight, Row row) {
    generateCell(row, flightNumIndex, String.valueOf(flight.getFlightNumber()));
    generateCell(row, airlineIndex, flight.getFlightDetails().getAirline());
    generateCell(row, aircraftIndex, flight.getFlightDetails().getAircraft().toString());
    generateCell(row, originCountryIndex, flight.getCountry().getOriginCountry());
    generateCell(row, originCityIndex, flight.getCity().getOriginCity());
    generateCell(row, departureDateIndex, flight.getDateFlight().getDates().get(0));
    generateCell(row, destinationCountryIndex, flight.getCountry().getDestinationCountry());
    generateCell(row, destinationCityIndex, flight.getCity().getDestinationCity());
    generateCell(row, arrivalDateIndex, flight.getDateFlight().getDates().get(1));
    generateCell(row, statusIndex, flight.getFlightDetails().getStatus());
  }

  private void createDescriptionSheet(
      Map<IFlight, String> descriptionReport, XSSFSheet descriptionSheet, XSSFCellStyle cellStyle) {

    Row row = descriptionSheet.createRow(0);
    if (descriptionSheet.getSheetName().equals("Cancellation Report")) {
      writeCancellationHeader(row, cellStyle);
    } else if (descriptionSheet.getSheetName().equals("Incident Report")) {
      writeIncidentHeader(row, cellStyle);
    } else {
      System.out.println("No valid sheet was provided");
      return;
    }
    var rowCount = 1;
    for (Map.Entry<IFlight, String> flightEntry : descriptionReport.entrySet()) {
      row = descriptionSheet.createRow(rowCount);
      writeDescription(descriptionReport, flightEntry.getKey(), row);
      rowCount++;
    }
  }

  private void writeCancellationHeader(Row row, XSSFCellStyle cellStyle) {
    var cell = row.createCell(0);
    cell.setCellStyle(cellStyle);
    cell.setCellValue("Flight Number");

    cell = row.createCell(1);
    cell.setCellStyle(cellStyle);
    cell.setCellValue("Cancellation Reason");
  }

  private void writeIncidentHeader(Row row, XSSFCellStyle cellStyle) {
    var cell = row.createCell(0);
    cell.setCellStyle(cellStyle);
    cell.setCellValue("Flight Number");

    cell = row.createCell(1);
    cell.setCellStyle(cellStyle);
    cell.setCellValue("Incidents during flight");
  }

  private void writeDescription(Map<IFlight, String> cancellation, IFlight flight, Row row) {
    var cell = row.createCell(0);
    cell.setCellValue(flight.getFlightNumber());
    cell = row.createCell(1);
    cell.setCellValue(cancellation.get(flight));
  }

  private void generateHeader(Row row, int index, String header, XSSFCellStyle cellStyle) {
    var cell = row.createCell(index);
    cell.setCellStyle(cellStyle);
    cell.setCellValue(header);
  }

  private void generateCell(Row row, int index, String value) {
    var cell = row.createCell(index);
    cell.setCellValue(value);
  }
}
