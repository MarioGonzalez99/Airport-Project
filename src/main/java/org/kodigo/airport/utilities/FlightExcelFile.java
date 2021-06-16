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
        flightCreator.addFlight(
            flightNumber,
            airline,
            aircraft,
            originCountry,
            originCity,
            destinationCountry,
            destinationCity,
            departureDate,
            arrivalDate,
            status);
      }
      System.out.println("Flight(s) added successfully");
    } catch (IOException e) {
      System.out.println("An error occurred when trying to process the excel file");
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
        flightFilter.filterDescriptions(
            FlightManager.getInstance().getCancellationReport(), flights);
    Map<IFlight, String> incidentReport =
        flightFilter.filterDescriptions(FlightManager.getInstance().getIncidentsReport(), flights);
    flights.sort(Comparator.comparing(IFlight::getFlightNumber));

    try (var outputStream = new FileOutputStream("AirportReport.xlsx");
        var workbook = new XSSFWorkbook()) {
      XSSFSheet flightSheet = workbook.createSheet("Flight Report");
      XSSFSheet cancellationSheet = workbook.createSheet("Cancellation Report");
      XSSFSheet incidentSheet = workbook.createSheet("Incident Report");

      // Header style
      final XSSFCellStyle cellStyle = workbook.createCellStyle();
      cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
      cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
      XSSFFont valueCellFont = workbook.createFont();
      valueCellFont.setColor(IndexedColors.WHITE.getIndex());
      valueCellFont.setBold(true);
      cellStyle.setFont(valueCellFont);

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
    var cell = row.createCell(flightNumIndex);
    cell.setCellStyle(cellStyle);
    cell.setCellValue("Flight Number");

    cell = row.createCell(airlineIndex);
    cell.setCellStyle(cellStyle);
    cell.setCellValue("Airline");

    cell = row.createCell(aircraftIndex);
    cell.setCellStyle(cellStyle);
    cell.setCellValue("Aircraft");

    cell = row.createCell(originCountryIndex);
    cell.setCellStyle(cellStyle);
    cell.setCellValue("Origin Country");

    cell = row.createCell(originCityIndex);
    cell.setCellStyle(cellStyle);
    cell.setCellValue("Origin City");

    cell = row.createCell(departureDateIndex);
    cell.setCellStyle(cellStyle);
    cell.setCellValue("Departure Date");

    cell = row.createCell(destinationCountryIndex);
    cell.setCellStyle(cellStyle);
    cell.setCellValue("Destination Country");

    cell = row.createCell(destinationCityIndex);
    cell.setCellStyle(cellStyle);
    cell.setCellValue("Destination City");

    cell = row.createCell(arrivalDateIndex);
    cell.setCellStyle(cellStyle);
    cell.setCellValue("Arrival Date");

    cell = row.createCell(statusIndex);
    cell.setCellStyle(cellStyle);
    cell.setCellValue("Status");
  }

  private void writeFlight(IFlight flight, Row row) {
    var cell = row.createCell(flightNumIndex);
    cell.setCellValue(flight.getFlightNumber());
    cell = row.createCell(airlineIndex);
    cell.setCellValue(flight.getAirline());
    cell = row.createCell(aircraftIndex);
    cell.setCellValue(flight.getAircraft().toString());
    cell = row.createCell(originCountryIndex);
    cell.setCellValue(flight.getCountry().getOriginCountry());
    cell = row.createCell(originCityIndex);
    cell.setCellValue(flight.getCity().getOriginCity());
    cell = row.createCell(departureDateIndex);
    cell.setCellValue(flight.getDateFlight().getDates().get(0));
    cell = row.createCell(destinationCountryIndex);
    cell.setCellValue(flight.getCountry().getDestinationCountry());
    cell = row.createCell(destinationCityIndex);
    cell.setCellValue(flight.getCity().getDestinationCity());
    cell = row.createCell(arrivalDateIndex);
    cell.setCellValue(flight.getDateFlight().getDates().get(1));
    cell = row.createCell(statusIndex);
    cell.setCellValue(flight.getStatus());
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
}
