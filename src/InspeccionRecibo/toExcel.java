package InspeccionRecibo;

import Servicios.Conexion;
import Servicios.ExcelFormato;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class toExcel {

    //Direccion para guardar el archivo, con su nombre
    File file = new File("InspeccionRecibo.xlsx");
    final String CONSULTA_INSPECCION_RECIBO_SQL = "SELECT * FROM inspeccionrecibo ORDER BY LPAD(noHoja, 3, '0') ASC";
    Connection conexion; // Objeto de tipo Connection para realizar la conexión a la bd
    ExcelFormato formato = new ExcelFormato();

    public toExcel() {
        this.conexion = Conexion.getInstance().getConnection(); // Obtener la conexión a la base de datos usando el Singleton
    }

    public void WriteExcelIR() throws SQLException, ParseException {

        // Crear un nuevo libro de trabajo en formato XLSX
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Crear una hoja en el libro de trabajo
        XSSFSheet excelSheet = workbook.createSheet("Inspeccion Recibo");

        // Crear las celdas de encabezado
        XSSFRow titleRow = excelSheet.createRow(0);
        XSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("INSPECCIÓN/RECIBO");
        titleCell.setCellStyle(formato.estiloTitulo(workbook, "Arial", (short) 15, true));
        // Combinar celdas
        CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, 9);
        excelSheet.addMergedRegion(mergedRegion);

        // Crear las celdas de encabezado
        XSSFRow headerRow = excelSheet.createRow(1);
        String[] headerLabels = {"No", "FECHA DE \n FACTURA", "PROVEEDOR", "NO.\n FACTURA", "NO. DE \n PEDIDO", "CALIBRE", "PRESENTACIÓN\n DE LAMINA", "NO. ROLLO", "PZ/KG", "ESTATUS"};

        for (int i = 0; i < headerLabels.length; i++) {
            XSSFCell cell = headerRow.createCell(i); // Se crea la celda
            cell.setCellValue(headerLabels[i]); // Se se asigna el encabezado
            int columnaAncho;
            switch (i) {
                case 0: // columna No.
                    columnaAncho = 4;
                    break;
                case 2: // columna Proveedor
                    columnaAncho = 30;
                    break;
                case 6: // columna presentación lamina
                    columnaAncho = 18;
                    break;
                default: // columnas restante
                    columnaAncho = 15;
                    break;
            }
            excelSheet.setColumnWidth(i, columnaAncho * 256); // Se ajusta la dimensión de la columna
            cell.setCellStyle(formato.estiloEncabezados(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER)); // Se aplica el estilo de encabezados
        }

        // Consulta SQL
        int row = 2; // Fila en la que se empieza la inserción de datos
        try (PreparedStatement pstm = conexion.prepareStatement(CONSULTA_INSPECCION_RECIBO_SQL);
                ResultSet res = pstm.executeQuery()) {
            List<String> fechas = new ArrayList<>();
            while (res.next()) { // Mientras haya registros
                Row dataRow = excelSheet.createRow(row); // Se crea una nueva fila
                // Se Captura la información de la BD
                String noHoja = res.getString("noHoja");
                String fechaFactura = res.getString("fechaFactura");
                String proveedor = res.getString("Proveedor");
                String noFactura = res.getString("noFactura");
                String noPedido = res.getString("noPedido");
                String calibre = res.getString("calibre");
                String noRollo = res.getString("noRollo");
                String pLamina = res.getString("pLamina");
                String pzKg = res.getString("pzKg");
                String estatus = res.getString("estatus");
                fechas.add(fechaFactura);

                // Se definen los valores de la celdas
                String numeroStr = noHoja.substring(noHoja.length() - 3); // Extraer el número del código

                dataRow.createCell(0).setCellValue(numeroStr);
                dataRow.createCell(1).setCellValue(fechaFactura);
                dataRow.createCell(2).setCellValue(proveedor);
                dataRow.createCell(3).setCellValue(noFactura);
                dataRow.createCell(4).setCellValue(noPedido);
                dataRow.createCell(5).setCellValue(calibre);
                dataRow.createCell(6).setCellValue(pLamina);
                dataRow.createCell(7).setCellValue(noRollo);
                dataRow.createCell(8).setCellValue(pzKg);
                dataRow.createCell(9).setCellValue(estatus);
                dataRow.createCell(10);

                for (int i = 0; i < dataRow.getLastCellNum(); i++) {
                    XSSFCell cell = (XSSFCell) dataRow.getCell(i);
                    switch (cell.getStringCellValue()) {
                        case "LIBERADA":
                            // Se compara si la celda de Estatus dice liberada...
                            cell.setCellStyle(formato.estiloEstatus(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, new java.awt.Color(102, 204, 0)));
                            break;
                        case "POR LIBERAR":
                            cell.setCellStyle(formato.estiloEstatus(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, new java.awt.Color(255, 151, 0)));
                            break;
                        case "RECHAZADA":
                            cell.setCellStyle(formato.estiloEstatus(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, new java.awt.Color(255, 0, 0)));
                            break;
                        case "CERTIFICADO INCOMPLETO":
                            cell.setCellStyle(formato.estiloEstatus(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, new java.awt.Color(255, 255, 0)));
                            break;
                        default:
                            cell.setCellStyle(formato.estiloCeldas(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
                            break;
                    }
                }
                row++;
            }
            Sheet sheet = workbook.getSheetAt(0); // Suponiendo que trabajas con la primera hoja

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            int rowIndex = 2;
            String mesActual = null;
            int primeraFilaMes = 2;
            
            XSSFCellStyle style = workbook.createCellStyle();
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setRotation((short)180);
            

            for (String fechaString : fechas) {
                Date fecha = dateFormat.parse(fechaString);
                String mes = new SimpleDateFormat("MMMM").format(fecha).toUpperCase();

                if (mesActual == null) {
                    mesActual = mes;
                }

                if (!mes.equals(mesActual)) {
                    // Combinar filas con el mismo mes
                    if (primeraFilaMes < rowIndex - 1) {
                        CellRangeAddress range = new CellRangeAddress(primeraFilaMes, rowIndex - 1, 10, 10);
                        sheet.addMergedRegion(range);
                    }
                    primeraFilaMes = rowIndex;
                    mesActual = mes;
                }

                Row row2 = sheet.getRow(rowIndex);
                XSSFCell fechaCell = (XSSFCell) row2.getCell(10); // Columna A (índice 0)
                fechaCell.setCellValue(mes);
                fechaCell.setCellStyle(style);
                rowIndex++;
            }

            // Combinar la última serie de filas
            if (primeraFilaMes < rowIndex - 1) {
                CellRangeAddress range = new CellRangeAddress(primeraFilaMes, rowIndex - 1, 10, 10);
                sheet.addMergedRegion(range);
            }

            // Se guarda la información y se crea el archivo
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                workbook.write(outputStream);
                System.out.println("Escribiendo en disco... Listo");
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
            System.out.println("Proceso completado.");
        }
    }
}
