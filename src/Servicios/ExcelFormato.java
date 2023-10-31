/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import java.awt.Color;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author JC
 */
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

    public XSSFCellStyle estiloEstatus(XSSFWorkbook workbook, HorizontalAlignment alineacionHorizontal, VerticalAlignment alineacionVertical,Color color) {
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
}
