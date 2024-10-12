package Servicios;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFormato {

    public XSSFFont crearFuente(XSSFWorkbook workbook, String nombreFuente, short tamañoFuente, boolean negrita) {
        XSSFFont font = workbook.createFont();
        font.setFontName(nombreFuente);
        font.setFontHeightInPoints(tamañoFuente);
        font.setBold(negrita);
        return font;
    }

    public XSSFCellStyle alinearContenido(XSSFWorkbook workbook, HorizontalAlignment alineacionHorizontal, VerticalAlignment alineacionVertical) {
        XSSFCellStyle estilo = workbook.createCellStyle();
        estilo.setAlignment(alineacionHorizontal);
        estilo.setVerticalAlignment(alineacionVertical);
        return estilo;
    }

    public XSSFCellStyle bordeSencillo(XSSFWorkbook workbook) {
        XSSFCellStyle estiloborde = workbook.createCellStyle();
        estiloborde.cloneStyleFrom(alinearContenido(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER)); // Copia las propiedades de estilo del método alinear
        estiloborde.setBorderTop(HSSFCellStyle.BORDER_THIN);
        estiloborde.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        estiloborde.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        estiloborde.setBorderRight(HSSFCellStyle.BORDER_THIN);
        return estiloborde;
    }

    public XSSFCellStyle bordeGrueso(XSSFWorkbook workbook) {
        XSSFCellStyle estiloBordeGrueso = workbook.createCellStyle();
        estiloBordeGrueso.cloneStyleFrom(alinearContenido(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER)); // Copia las propiedades de estilo del método alinear
        estiloBordeGrueso.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        estiloBordeGrueso.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        estiloBordeGrueso.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        estiloBordeGrueso.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        return estiloBordeGrueso;
    }

    public XSSFCellStyle estiloEncabezados(XSSFWorkbook workbook, HorizontalAlignment alineacionHorizontal, VerticalAlignment alineacionVertical) {
        // Se crea un nuevo estilo para los encabezados
        XSSFCellStyle estiloEncabezado = workbook.createCellStyle();
        estiloEncabezado.cloneStyleFrom(bordeSencillo(workbook)); // Copia las propiedades de estilo del método alinear
        estiloEncabezado.setFont(crearFuente(workbook, "Arial", (short) 10, true)); // Formato de Fuente
        estiloEncabezado.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex()); // 
        estiloEncabezado.setFillPattern(FillPatternType.SOLID_FOREGROUND); // Color solido para las celdas
        estiloEncabezado.setWrapText(true); // El texto o contenido debe ajustarse automaticamente a las dimensiones de la celda
        return estiloEncabezado;
    }

    public XSSFCellStyle estiloCeldas(XSSFWorkbook workbook, HorizontalAlignment alineacionHorizontal, VerticalAlignment alineacionVertical) {
        // Se crea un nuevo estilo para los encabezados
        XSSFCellStyle estiloCelda = workbook.createCellStyle();
        XSSFColor greyColor = new XSSFColor(new java.awt.Color(242, 243, 244));
        estiloCelda.cloneStyleFrom(alinearContenido(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER)); // Copia las propiedades de estilo del método alinear
        estiloCelda.cloneStyleFrom(bordeSencillo(workbook));
        estiloCelda.setFillForegroundColor(greyColor);
        estiloCelda.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return estiloCelda;
    }

    public XSSFCellStyle estiloEstatus(XSSFWorkbook workbook, HorizontalAlignment alineacionHorizontal, VerticalAlignment alineacionVertical, Color color) {
        // Se crea un estilo para la celda de Estatus
        XSSFCellStyle estiloCeldaEstatus = workbook.createCellStyle();
        XSSFColor xssfColor = new XSSFColor(color);
        estiloCeldaEstatus.cloneStyleFrom(alinearContenido(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER)); // Copia las propiedades de estilo del método alinear
        estiloCeldaEstatus.cloneStyleFrom(bordeSencillo(workbook));
        estiloCeldaEstatus.setFillForegroundColor(xssfColor);
        estiloCeldaEstatus.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return estiloCeldaEstatus;
    }

    public XSSFCellStyle estiloTitulo(XSSFWorkbook workbook, String nombreFuente, short tamañoFuente, boolean negrita) {
        XSSFCellStyle estiloTitulo = workbook.createCellStyle();
        estiloTitulo.cloneStyleFrom(alinearContenido(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
        estiloTitulo.setFont(crearFuente(workbook, nombreFuente, tamañoFuente, negrita));
        return estiloTitulo;
    }

    public XSSFCellStyle estiloTblAnchoLargo(XSSFWorkbook workbook, HorizontalAlignment alineacionHorizontal, VerticalAlignment alineacionVertical) {
        XSSFCellStyle estiloTbl = workbook.createCellStyle();
        estiloTbl.cloneStyleFrom(alinearContenido(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
        estiloTbl.cloneStyleFrom(bordeSencillo(workbook));
        return estiloTbl;
    }

    public void estiloCeldasRD(XSSFWorkbook workbook, int tamFuente, int anguloRotacion, boolean saltoLinea) {
        CellStyle cellStyle = workbook.createCellStyle(); // Crear un estilo con formato de texto que permita saltos de línea

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) tamFuente); // Cambia el tamaño del texto aquí 

        cellStyle.setRotation((short) 90); // Cambia el ángulo de rotación aquí (90 grados en este caso)
        cellStyle.setFont(font);
        cellStyle.setWrapText(saltoLinea); // Permite saltos de línea

        // Crear un margen en la parte superior
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
    }

    public XSSFCellStyle estiloTablaAnchoLargoHI(XSSFWorkbook workbook) {
        XSSFCellStyle estiloCheckmark = (XSSFCellStyle) workbook.createCellStyle(); // Crear estilo de celda con fuente Wingdings
        Font font = workbook.createFont();
        font.setFontName("Wingdings 2"); // Fuente que contiene el ícono de checkmark
        estiloCheckmark.setFont(font);
        estiloCheckmark.setAlignment(HorizontalAlignment.CENTER); // Establecer la alineación horizontal
        estiloCheckmark.setVerticalAlignment(VerticalAlignment.CENTER); // Establecer la alineación vertical
        estiloCheckmark.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        estiloCheckmark.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

        return estiloCheckmark;
    }

    public String formatoFecha(String fechaString) {
        String fechaFormateada = "";
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoSalida = new SimpleDateFormat("d 'de' MMMM 'de' yyyy");

        try {
            Date fecha = formatoEntrada.parse(fechaString);
            fechaFormateada = formatoSalida.format(fecha);
            System.out.println(fechaFormateada);
        } catch (ParseException e) {
        }
        return fechaFormateada;
    }

    public String eliminarSeparadores(String fechaInspeccion) {
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

    public XSSFCellStyle estiloCeldasRD(XSSFWorkbook workbook) {
        XSSFCellStyle estilo = (XSSFCellStyle) workbook.createCellStyle();
        estilo.setWrapText(true);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 6);
        estilo.setFont(font);

        estilo.setBorderTop(HSSFCellStyle.BORDER_THIN);
        estilo.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        estilo.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        estilo.setBorderRight(HSSFCellStyle.BORDER_THIN);
        estilo.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        estilo.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return estilo;
    }

    public XSSFCellStyle formatoMeses(XSSFWorkbook workbook) {
        XSSFCellStyle estilo = workbook.createCellStyle();
        estilo.setVerticalAlignment(VerticalAlignment.CENTER);
        estilo.setAlignment(HorizontalAlignment.CENTER);
        estilo.setRotation((short) 180);

        return estilo;
    }
}
