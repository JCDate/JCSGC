/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.AnchoLargoM;
import Modelos.DatosFila;
import Modelos.DatosIRM;
import Modelos.InspeccionReciboM;
import java.io.FileInputStream;
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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author JC
 */
public class ExcelEditor {

    String newFilePath = "";
    ExcelFormato formato = new ExcelFormato();

    Connection conexion;

    public String generarExcel(DatosIRM dirm, InspeccionReciboM irm, JTable tblAL, List<JTable> listTablas, List listaAL) throws IOException, SQLException, ClassNotFoundException {
        this.conexion = Conexion.getInstance().getConnection(); // Obtener la conexión a la base de datos usando el Singleton
        String filePath = "HojaInstruccion.xlsx";
        XSSFWorkbook workbook = null;
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0); // Obtén la primera hoja del libro
            String[] partes = irm.getNoHoja().split("/");
            String numeroStr = partes[1];
            int numero = Integer.parseInt(numeroStr);
            if (numero < 10) {
                // Eliminar ceros a la izquierda
                numeroStr = String.valueOf(numero);
            }

            workbook.setSheetName(workbook.getSheetIndex(sheet), numeroStr); // Cambiar el nombre de la Hoja
            // No. de Hoja
            Row rowNoHoja = sheet.getRow(5); // Obtén la fila
            Cell cellNoHoja = rowNoHoja.getCell(2); // Obtén la celda en la fila
            // Modifica el valor de la celda
            cellNoHoja.setCellValue(numeroStr);

            // Fecha Inspección
            Row rowFechaInspeccion = sheet.getRow(5); // Obtén la fila
            Cell cellFechaInspeccion = rowFechaInspeccion.getCell(6); // Obtén la celda en la fila
            // Modifica el valor de la celda
            cellFechaInspeccion.setCellValue(formatoFecha(dirm.getFechaInspeccion()));

            // Descripción de Materia Prima Recibida
            Row rowDMP = sheet.getRow(9); // Obtén la fila
            Cell cellDMP = rowDMP.getCell(1); // Obtén la celda en la fila
            // Modifica el valor de la celda
            cellDMP.setCellValue(dirm.getDescripcionMP());

            // Proveedor
            Row rowProveedor = sheet.getRow(9); // Obtén la fila
            Cell cellProveedor = rowProveedor.getCell(4); // Obtén la celda en la fila
            // Modifica el valor de la celda
            cellProveedor.setCellValue(irm.getProveedor());

            // No Factura
            Row rowNoFactura = sheet.getRow(9); // Obtén la fila
            Cell cellNoFactura = rowNoFactura.getCell(6); // Obtén la celda en la fila
            // Modifica el valor de la celda
            cellNoFactura.setCellValue(irm.getNoFactura());

            // Fecha Factura
            Row rowFechaFactura = sheet.getRow(9); // Obtén la fila
            Cell cellFechaFactura = rowFechaFactura.getCell(8); // Obtén la celda en la fila
            // Modifica el valor de la celda
            cellFechaFactura.setCellValue(irm.getFechaFactura());

            // Calibre Lamina
            Row rowCalibreLamina = sheet.getRow(13); // Obtén la fila
            Cell cellCalibreLamina = rowCalibreLamina.getCell(1); // Obtén la celda en la fila
            // Modifica el valor de la celda
            cellCalibreLamina.setCellValue(dirm.getCalibreLamina());

            // No. Pedido
            Row rowNoPedido = sheet.getRow(13); // Obtén la fila
            Cell cellNoPedido = rowNoPedido.getCell(3); // Obtén la celda en la fila
            // Modifica el valor de la celda
            cellNoPedido.setCellValue(irm.getNoPedido());

            // No Rollo
            Row rowNoRollo = sheet.getRow(13); // Obtén la fila
            Cell cellNoRollo = rowNoRollo.getCell(5); // Obtén la celda en la fila
            // Modifica el valor de la celda
            cellNoRollo.setCellValue(irm.getNoRollo());

            // No PZKG
            Row rowNoPzKg = sheet.getRow(13); // Obtén la fila
            Cell cellNoPzKg = rowNoPzKg.getCell(7); // Obtén la celda en la fila
            // Modifica el valor de la celda
            cellNoPzKg.setCellValue(irm.getPzKg());

            // Tabla Ancho/Largo
            int rowNum = 20;

            // Crear estilo de celda con fuente Wingdings
            XSSFCellStyle estiloCheckmark = (XSSFCellStyle) workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("Wingdings 2"); // Fuente que contiene el ícono de checkmark
            estiloCheckmark.setFont(font);
            estiloCheckmark.setAlignment(HorizontalAlignment.CENTER); // Establecer la alineación horizontal
            estiloCheckmark.setVerticalAlignment(VerticalAlignment.CENTER); // Establecer la alineación vertical
            estiloCheckmark.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            estiloCheckmark.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

            for (int i = 0; i < listaAL.size(); i++) {
                AnchoLargoM medida = (AnchoLargoM) listaAL.get(i);
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    row = sheet.createRow(rowNum);
                }

                Cell cellAncho = row.createCell(1); // Celda para el ancho
                cellAncho.setCellValue(medida.getAncho());

                Cell cellLargo = row.createCell(2); // Celda para el largo
                cellLargo.setCellValue(medida.getLargo());

                rowNum++;

                cellAncho.setCellStyle(formato.estiloTblAnchoLargo(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
                cellLargo.setCellStyle(formato.estiloTblAnchoLargo(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
            }
            // Aceptación/Rechazo
            int numCellAR = (dirm.getAceptacion() == 1) ? 2 : 7;

            Row rowAR = sheet.getRow(39); // Obtén la fila
            Cell cellAR = rowAR.getCell(numCellAR); // Obtén la celda en la fila
            Row row = sheet.getRow(39);
            Cell cell = (numCellAR == 2) ? row.getCell(7) : row.getCell(2);
            cell.setCellValue("");
            // Modifica el valor de la celda
            cellAR.setCellValue("√");

            // Observaciones a los resultados dimensionales
            Row rowObsRD = sheet.getRow(34); // Obtén la fila
            Cell cellObsRD = rowObsRD.getCell(1); // Obtén la celda en la fila
            // Modifica el valor de la celda
            cellObsRD.setCellValue(dirm.getObservacionesRD());

            // Observaciones a la disposición de la Materia Prima
            Row rowObsMP = sheet.getRow(42); // Obtén la fila
            Cell cellObsMP = rowObsMP.getCell(1); // Obtén la celda en la fila
            // Modifica el valor de la celda
            cellObsMP.setCellValue(dirm.getObsMP());

            // No. Pedido
            Row rowNombreInspector = sheet.getRow(60); // Obtén la fila
            Cell cellNombreInspector = rowNombreInspector.getCell(1); // Obtén la celda en la fila
            // Modifica el valor de la celda
            cellNombreInspector.setCellValue(dirm.getInspector());

            // HOJA 2
            Sheet sheet2 = workbook.getSheetAt(1);
            workbook.setSheetName(workbook.getSheetIndex(sheet2), eliminarSeparadores(dirm.getFechaInspeccion()));

            // Obtener la lista de tablas
            List<DatosFila> datosFilas = new ArrayList<>();

            for (JTable table : listTablas) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int rowCount = model.getRowCount();
                // Iterar sobre las filas de la JTable
                for (int i = 0; i < rowCount; i++) {
                    Object especificacion = model.getValueAt(0, 0);
                    Object calibres = model.getValueAt(0, 2);
                    Object propiedad = model.getValueAt(i, 3);
                    Object cumplePropiedad = model.getValueAt(i, 4);
                    Object composicion = model.getValueAt(i, 5);
                    Object cumpleComposicion = model.getValueAt(i, 6);
                    int fila = obtenerEspecificacion(conexion, especificacion.toString());
                    DatosFila datosFila = new DatosFila(especificacion, calibres, propiedad, cumplePropiedad, composicion, cumpleComposicion, fila);
                    datosFilas.add(datosFila);
                }
            }

            // Variables para el control de las filas
            int rowNum2 = 6; // Valor inicial de la fila para la primera especificación
            int rowNum3 = 0; // Valor inicial de la fila para las siguientes especificaciones
            String especificacionAnterior = "";

            // Establecer el formato de fuente para el contenido
            XSSFFont font2 = (XSSFFont) workbook.createFont();
            font2.setFontName("Calibri"); // El nombre de la fuente
            font2.setFontHeightInPoints((short) 10); // El tamaño
            // Crear un estilo de celda y establecer el borde grueso
            CellStyle style = workbook.createCellStyle();
            style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            style.setAlignment(CellStyle.ALIGN_CENTER);
            style.setFont(font2);

            // Aplicar el estilo a la celda
            cell.setCellStyle(style);

            CellStyle style2 = workbook.createCellStyle();
            // Establecer el formato de fuente para el contenido
            XSSFFont font3 = (XSSFFont) workbook.createFont();
            font3.setFontName("Arial"); // El nombre de la fuente
            font3.setFontHeightInPoints((short) 6); // El tamaño
            style2.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            style2.setFont(font3);

            // Variable para rastrear la primera aparición de la especificación
            boolean primeraEspecificacion = true;

            // Recorrer los elementos
            for (DatosFila datosFila : datosFilas) {
                // Obtener la especificación actual
                String especificacionActual = datosFila.getEspecificacion().toString();
                // Verificar si es una nueva especificación
                if (!especificacionActual.equals(especificacionAnterior)) {
                    // Cambiar a la siguiente fila específica para la nueva especificación
                    rowNum2 = datosFila.getElemento(especificacionActual);
                    rowNum3 = rowNum2;
                    // Reiniciar la variable para la primera especificación
                    primeraEspecificacion = true;
                }

                // Crear la fila en el archivo de Excel
                Row row2 = sheet2.getRow(rowNum2);
                if (row2 == null) {
                    row2 = sheet2.createRow(rowNum2);
                }

                // Crear estilo de celda con checkbox
                CellStyle estiloCheckbox = workbook.createCellStyle();
                estiloCheckbox.setLocked(true);

                // Imprimir el valor de calibre solo en la primera aparición de la especificación
                if (primeraEspecificacion) {
                    Cell chkCalibre = row2.createCell(4);
                    Cell cellCalibre = row2.createCell(5);
                    cellCalibre.setCellValue(datosFila.getCalibre().toString());
                    cellCalibre.setCellStyle(style2);
                    chkCalibre.setCellValue("R");
                    chkCalibre.setCellStyle(estiloCheckmark);
                    primeraEspecificacion = false; // Marcar que ya se imprimió el calibre
                }
                
                // Escribir los datos en las celdas correspondientes
                Cell cellCumplePropiedad = row2.createCell(10);
                if (datosFila.getPropiedad() == null || datosFila.getPropiedad().equals("")) {
                    cellCumplePropiedad.setCellValue("");
                } else {
                    String cmPm = (datosFila.getCmPm().equals(true)) ? "X" : "√";
                    cellCumplePropiedad.setCellValue(cmPm);
                }
                cellCumplePropiedad.setCellStyle(style);

                Cell cellCumpleComposicion = row2.createCell(13);
                if (datosFila.getComposicion() == null || datosFila.getComposicion().equals("")) {
                    cellCumpleComposicion.setCellValue("");
                } else {
                    String cmCq = (datosFila.getCmCq().toString().equals(true)) ? "X" : "√";
                    cellCumpleComposicion.setCellValue(cmCq);
                }
                cellCumpleComposicion.setCellStyle(style);
                // Incrementar el número de fila correspondiente a la especificación actual
                rowNum2++;
                especificacionAnterior = especificacionActual;
            }

            // Crear un nuevo archivo de Excel para guardar los cambios
            newFilePath = "HI-" + numeroStr + "-" + eliminarSeparadores(dirm.getFechaInspeccion()) + ".xlsx ";  // Ruta del nuevo archivo de Excel
            FileOutputStream fos = new FileOutputStream(newFilePath);
            workbook.write(fos);
        } catch (IOException e) {
        }
        return newFilePath;
    }

    private int obtenerEspecificacion(Connection conexion, String especificacion) throws SQLException {
        String sqlConsulta = "SELECT fila FROM especificacionesir WHERE especificacion=?";
        PreparedStatement consulta = conexion.prepareStatement(sqlConsulta);
        consulta.setString(1, especificacion);
        ResultSet resultado = consulta.executeQuery();
        int fila = 0;
        if (resultado.next()) {
            fila = resultado.getInt("fila");
        }
        return fila;
    }

    private String eliminarSeparadores(String fechaInspeccion) {
        String fechaSinSeparadores = "";
        String formatoEntrada = "dd/MM/yyyy";
        String formatoSalida = "ddMMyy";
        SimpleDateFormat sdfEntrada = new SimpleDateFormat(formatoEntrada);
        SimpleDateFormat sdfSalida = new SimpleDateFormat(formatoSalida);
        try {
            Date fecha = sdfEntrada.parse(fechaInspeccion);
            fechaSinSeparadores = sdfSalida.format(fecha);
        } catch (ParseException e) {
        }
        return fechaSinSeparadores;
    }

    private String formatoFecha(String fechaString) {
        String fechaFormateada = "";
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoSalida = new SimpleDateFormat("d 'de' MMMM 'de' yyyy");

        try {
            Date fecha = formatoEntrada.parse(fechaString);
            fechaFormateada = formatoSalida.format(fecha);
            System.out.println(fechaFormateada); // Salida: 1 de febrero del 2023
        } catch (ParseException e) {
        }
        return fechaFormateada;
    }

}
