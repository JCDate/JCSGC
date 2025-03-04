package Servicios;

import Modelos.AceptacionPc1;
import Modelos.AceptacionPc2;
import Modelos.AceptacionPc3;
import Modelos.AnchoLargoM;
import Modelos.DatosFila;
import Modelos.DatosIRM;
import Modelos.InspeccionReciboM;
import Modelos.RugosidadDurezaM;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GeneradorExcel {

    // Atributos
    private Connection conexion;
    private InspeccionReciboServicio irs;

    //INSPECCIÓN RECIBO
    private List<String> fechasRegistrosIR;

    String nuevaRutaArchivo = ""; // Variable para guardar la información de la ruta del archivo de hoja de Instrucción
    String nuevaRutaArchivoRD = ""; // Variable para guardar la información de la ruta del archivo de retención dimensional
    ExcelFormato formato = new ExcelFormato(); // Clase para aplicar formatos de Excel
    AceptacionProductoServicio aps = new AceptacionProductoServicio(); // Instancia de la clase de aceptaciónProducto
    String numeroStr; // Se obtiene el nombre del componente para anexarlo en el nombre del archivo de exce

    // Filas a partir de donde se van a imprimir los datos de los procesos de Retención Dimensional
    int filaBaseNoOp1 = 9;
    int filaBaseNoOp3 = 47;
    int filaBaseNoOp4 = 85;
    int filaBaseNoOp5 = 123;

    int fila1 = 5;
    int fila3 = 43;
    int fila4 = 81;
    int fila5 = 119;

    int columnaBase = 8; // A partir de esta fila se imprimen las variables y sus respectivos valores en el documento de Retención Dimensional

    private static final int[] FILAS_INFO = {2, 40, 78, 116};
    private static final int[] COLUMNAS_COMPONENTE = {13, 20, 26}; // Columnas donde se imprime la información del componente

    public GeneradorExcel() {
        try {
            this.conexion = Conexion.getInstance().conectar();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al establecer la conexión: ", ex);
            Logger.getLogger(GeneradorExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public GeneradorExcel(InspeccionReciboServicio irs) {
        try {
            this.conexion = Conexion.getInstance().conectar();
            this.irs = irs;
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al establecer la conexión: ", ex);
            Logger.getLogger(GeneradorExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // InspecciónRecibo.xlsx
    public void generarInspeccionReciboXLSX() throws SQLException, ParseException {
        this.fechasRegistrosIR = new ArrayList<>();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet hojaExcel = workbook.createSheet("Inspección Recibo");

        crearTituloYEncabezados(workbook, hojaExcel);
        List<InspeccionReciboM> registros = irs.obtenerTodasInspecciones(conexion);

        generarFilasDeDatos(workbook, hojaExcel, registros);
        agregarMeses(workbook, hojaExcel);

        guardarArchivo(workbook, "archivos\\InspeccionRecibo\\InspeccionRecibo.xlsx");
    }

    private void crearTituloYEncabezados(XSSFWorkbook workbook, XSSFSheet hojaExcel) {
        XSSFCell tituloCelda = hojaExcel.createRow(0).createCell(0);
        tituloCelda.setCellValue("INSPECCIÓN/RECIBO");
        tituloCelda.setCellStyle(formato.estiloTitulo(workbook, "Arial", (short) 15, true));
        hojaExcel.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));

        String[] titulosFilas = {"No", "FECHA DE \n FACTURA", "PROVEEDOR", "NO.\n FACTURA", "NO. DE \n PEDIDO", "CALIBRE", "PRESENTACIÓN\n DE LAMINA", "NO. ROLLO", "PZ/KG", "ESTATUS"};
        XSSFRow encabezados = hojaExcel.createRow(1);

        for (int i = 0; i < titulosFilas.length; i++) {
            XSSFCell cell = encabezados.createCell(i);
            cell.setCellValue(titulosFilas[i]);
            cell.setCellStyle(formato.estiloEncabezados(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
            hojaExcel.setColumnWidth(i, obtenerAnchoColumna(i) * 256);
        }
    }

    private int obtenerAnchoColumna(int indice) {
        switch (indice) {
            case 0:
                return 4;
            case 2:
                return 30;
            case 6:
                return 18;
            default:
                return 15;
        }
    }

    private void generarFilasDeDatos(XSSFWorkbook workbook, XSSFSheet hojaExcel, List<InspeccionReciboM> registros) {
        int filaIndex = 2;
        for (InspeccionReciboM registro : registros) {
            XSSFRow fila = hojaExcel.createRow(filaIndex++);
            crearCeldaConEstilo(fila, 0, registro.getNoHoja().substring(registro.getNoHoja().length() - 3), workbook);
            crearCeldaConEstilo(fila, 1, registro.getFechaFactura(), workbook);
            crearCeldaConEstilo(fila, 2, registro.getProveedor(), workbook);
            crearCeldaConEstilo(fila, 3, registro.getNoFactura(), workbook);
            crearCeldaConEstilo(fila, 4, registro.getNoPedido(), workbook);
            crearCeldaConEstilo(fila, 5, registro.getCalibre(), workbook);
            crearCeldaConEstilo(fila, 6, registro.getpLamina(), workbook);
            crearCeldaConEstilo(fila, 7, registro.getNoRollo(), workbook);
            crearCeldaConEstilo(fila, 8, registro.getPzKg(), workbook);
            crearCeldaConEstilo(fila, 9, registro.getEstatus(), workbook);
            fechasRegistrosIR.add(registro.getFechaFactura());
        }
    }

    private void crearCeldaConEstilo(XSSFRow fila, int columna, String valor, XSSFWorkbook workbook) {
        XSSFCell celda = (XSSFCell) fila.createCell(columna);
        celda.setCellValue(valor);
        if (columna == 9) {
            aplicarEstiloSegunEstatus(celda, valor, workbook);
        } else {
            celda.setCellStyle(formato.estiloCeldas(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
        }
    }

    private void aplicarEstiloSegunEstatus(XSSFCell cell, String valor, XSSFWorkbook workbook) {
        if (cell == null || valor == null || valor.isEmpty()) {
            return;
        }

        switch (valor) {
            case "LIBERADA":
                cell.setCellStyle(formato.estiloEstatus(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, new java.awt.Color(102, 204, 0)));
                break;
            case "POR LIBERAR":
                cell.setCellStyle(formato.estiloEstatus(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, new java.awt.Color(255, 151, 0)));
                break;
            case "RECHAZADA":
                cell.setCellStyle(formato.estiloEstatus(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, new java.awt.Color(255, 0, 0)));
                break;
            default:
                cell.setCellStyle(formato.estiloCeldas(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
                break;
        }
    }

    private void agregarMeses(XSSFWorkbook workbook, XSSFSheet hojaExcel) throws ParseException {
        int indexFila = 2;

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        String mesActual = null;
        int primeraFilaMes = 2;

        for (String fechaString : fechasRegistrosIR) {
            Date fecha = formatoFecha.parse(fechaString);
            String mes = new SimpleDateFormat("MMMM").format(fecha).toUpperCase();

            if (mesActual == null) {
                mesActual = mes;
            }

            if (!mes.equals(mesActual)) {

                if (primeraFilaMes < indexFila - 1) {
                    CellRangeAddress rango = new CellRangeAddress(primeraFilaMes, indexFila - 1, 10, 10);
                    hojaExcel.addMergedRegion(rango);
                }
                primeraFilaMes = indexFila;
                mesActual = mes;
            }

            XSSFRow fila2 = hojaExcel.getRow(indexFila) == null ? hojaExcel.createRow(indexFila) : hojaExcel.getRow(indexFila);

            XSSFCell celdaFecha = (XSSFCell) fila2.createCell(10);
            celdaFecha.setCellValue(mes);
            celdaFecha.setCellStyle(formato.formatoMeses(workbook));
            indexFila++;
        }

        if (primeraFilaMes < indexFila - 1) {
            CellRangeAddress rango = new CellRangeAddress(primeraFilaMes, indexFila - 1, 10, 10);
            hojaExcel.addMergedRegion(rango);
        }
    }

    private void guardarArchivo(XSSFWorkbook workbook, String ruta) {
        String rutaCompleta = "\\\\" + Utilidades.SERVIDOR + "\\" + ruta;

        try (FileOutputStream outputStream = new FileOutputStream(new File(rutaCompleta))) {
            workbook.write(outputStream);
            Utilidades.abrirDocumento(ruta);
        } catch (IOException ex) {
            Utilidades.manejarExcepcion("Error al Generar el Archivo InspeccionRecibo.xlsx: ", ex);
        }
    }

    public String getDatosCeldas(XSSFSheet hoja, int fila, int columna) {
        XSSFCell celda = hoja.getRow(fila).getCell(columna);
        return celda.getStringCellValue();
    }

    public void setDatosCeldas(XSSFSheet hoja, int row, int columna, String contenido) {
        XSSFRow fila = hoja.getRow(row);
        if (fila == null) {
            fila = hoja.createRow(row);
        }
        XSSFCell celda = fila.getCell(columna);
        if (celda == null) {
            celda = fila.createCell(columna);
        }
        celda.setCellValue(contenido);
    }

    public boolean isAceptacion(XSSFSheet hoja1, int row, int column) {
        XSSFRow fila = hoja1.getRow(row);
        XSSFCell celda = fila.getCell(column);
        return celda.getStringCellValue().equalsIgnoreCase("√");
    }

    // HojaInstrucción.xlsx
    public String generarHojaInstruccion(Connection conexion, DatosIRM dirm, InspeccionReciboM irm, JTable tblAL, JTable tabla, List listaAL, List listaRD) throws IOException, SQLException, ClassNotFoundException {
        final String rutaPlantilla = "jc/doctos/HojaInstruccion.xlsx";
        final String rutaSalida = "archivos\\InspeccionRecibo\\HojasInstruccion\\";

        InputStream inputStream = null;
        XSSFWorkbook workbook = null;

        try {
            // Cargar la plantilla
            inputStream = getClass().getClassLoader().getResourceAsStream(rutaPlantilla);
            if (inputStream == null) {
                JOptionPane.showMessageDialog(null, "No se pudo encontrar el archivo Excel.");
                return null;
            }

            workbook = new XSSFWorkbook(inputStream);

            // Procesar las hojas de trabajo
            procesarPaginaUnoHJ(workbook, irm, dirm, listaAL, listaRD);
            procesarPaginaDosHJ(workbook, conexion, dirm, tabla);

            // Generar nombre del archivo de salida
            String numeroHoja = extraerNumeroHoja(irm.getNoHoja());
            String fechaFormateada = formato.eliminarSeparadores(dirm.getFechaInspeccion());
            String rutaArchivoSalida =  "HI-" + numeroHoja + "-" + fechaFormateada + ".xlsx";

            // Guardar el archivo generado
            guardarArchivoExcel(workbook, rutaSalida + rutaArchivoSalida);

            return rutaArchivoSalida;
        } finally {
            if (inputStream != null) {
                inputStream.close(); // Cierre manual del InputStream
            }
        }
    }

    private void procesarPaginaUnoHJ(XSSFWorkbook workbook, InspeccionReciboM irm, DatosIRM dirm, List listaAL, List listaRD) {
        XSSFSheet sheet = workbook.getSheetAt(0); // Obtén la primera hoja del libro

        String[] partes = irm.getNoHoja().split("/"); // Se divide el valor de NoHoja para solo obtener el número
        numeroStr = partes[1];
        int numero = Integer.parseInt(numeroStr);
        if (numero < 10) { // Eliminar ceros a la izquierda
            numeroStr = String.valueOf(numero);
        }

        workbook.setSheetName(workbook.getSheetIndex(sheet), numeroStr); // Cambiar el nombre de la Hoja con el número previamente obtenido

        // Se imprime la información referente
        setValorCeldasHojaInstruccion(sheet, 5, 2, numeroStr);  // No Hoja
        setValorCeldasHojaInstruccion(sheet, 5, 6, formato.formatoFecha(dirm.getFechaInspeccion()));  // Fecha Inspección
        setValorCeldasHojaInstruccion(sheet, 9, 1, dirm.getDescripcionMP());  // Descripcion de Materia Prima Recibida
        setValorCeldasHojaInstruccion(sheet, 9, 4, irm.getProveedor());  // Proveedor
        setValorCeldasHojaInstruccion(sheet, 9, 6, irm.getNoFactura());  // No. Factura
        setValorCeldasHojaInstruccion(sheet, 9, 8, irm.getFechaFactura());  // Fecha Factura
        setValorCeldasHojaInstruccion(sheet, 13, 1, dirm.getCalibreLamina());  // Calibre
        setValorCeldasHojaInstruccion(sheet, 13, 3, irm.getNoPedido());  // No. Pedido
        setValorCeldasHojaInstruccion(sheet, 13, 5, irm.getNoRollo());  // No. Rollo
        setValorCeldasHojaInstruccion(sheet, 13, 7, irm.getPzKg());  // No. PzKg

        int numeroFila = 20; // Tabla Ancho/Largo

        for (int i = 0; i < listaAL.size(); i++) { // Se imprimen los valores de ancho y largo del componente
            AnchoLargoM medida = (AnchoLargoM) listaAL.get(i);
            XSSFRow row = sheet.getRow(numeroFila);
            if (row == null) {
                row = sheet.createRow(numeroFila);
            }

            XSSFCell cellAncho = row.createCell(1); // Celda para el ancho
            cellAncho.setCellValue(medida.getAncho());

            XSSFCell cellLargo = row.createCell(2); // Celda para el largo
            cellLargo.setCellValue(medida.getLargo());

            numeroFila++;

            //Aplicación del formato
            cellAncho.setCellStyle(formato.estiloTblAnchoLargo(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
            cellLargo.setCellStyle(formato.estiloTblAnchoLargo(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
        }

        int numColumna = 2;
        for (int i = 0; i < listaRD.size(); i++) {
            RugosidadDurezaM medida = (RugosidadDurezaM) listaRD.get(i);
            XSSFRow row = sheet.getRow(43);
            if (row == null) {
                row = sheet.createRow(43);
            }

            XSSFRow row2 = sheet.getRow(44);
            if (row2 == null) {
                row2 = sheet.createRow(44);
            }

            XSSFCell cellAncho = row.createCell(numColumna);
            cellAncho.setCellValue(medida.getRugosidad());

            XSSFCell cellLargo = row2.createCell(numColumna);
            cellLargo.setCellValue(medida.getDureza());

            // Definir colores usando new XSSFColor(color, null)
            XSSFColor colorRojo = new XSSFColor(new java.awt.Color(255, 207, 207), null);
            XSSFColor colorVerde = new XSSFColor(new java.awt.Color(142, 255, 131), null);

            // Crear un estilo para rugosidad
            XSSFCellStyle estiloRugosidad = workbook.createCellStyle();
            estiloRugosidad.setAlignment(HorizontalAlignment.CENTER);
            estiloRugosidad.setVerticalAlignment(VerticalAlignment.CENTER);
            estiloRugosidad.setBorderLeft(BorderStyle.THIN);
            estiloRugosidad.setBorderBottom(BorderStyle.THIN);
            estiloRugosidad.setBorderRight(BorderStyle.THIN);
            estiloRugosidad.setBorderTop(BorderStyle.THIN);
            estiloRugosidad.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Aplicar color basado en la condición de rugosidad
            if (Double.parseDouble(medida.getRugosidad().trim()) > 58) {
                System.out.println("Aplicando rojo a Rugosidad: " + medida.getRugosidad());
                estiloRugosidad.setFillForegroundColor(colorRojo);
            } else {
                System.out.println("Aplicando verde a Rugosidad: " + medida.getRugosidad());
                estiloRugosidad.setFillForegroundColor(colorVerde);
            }
            cellAncho.setCellStyle(estiloRugosidad);

            // Crear un estilo independiente para dureza
            XSSFCellStyle estiloDureza = workbook.createCellStyle();
            estiloDureza.setAlignment(HorizontalAlignment.CENTER);
            estiloDureza.setVerticalAlignment(VerticalAlignment.CENTER);
            estiloDureza.setBorderLeft(BorderStyle.THIN);
            estiloDureza.setBorderBottom(BorderStyle.THIN);
            estiloDureza.setBorderRight(BorderStyle.THIN);
            estiloDureza.setBorderTop(BorderStyle.THIN);
            estiloDureza.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Aplicar color basado en la condición de dureza
            if (Double.parseDouble(medida.getDureza()) < 35 || Double.parseDouble(medida.getDureza()) > 60) {
                System.out.println("Aplicando rojo a Dureza: " + medida.getDureza());
                estiloDureza.setFillForegroundColor(colorRojo);
            } else {
                System.out.println("Aplicando verde a Dureza: " + medida.getDureza());
                estiloDureza.setFillForegroundColor(colorVerde);
            }
            cellLargo.setCellStyle(estiloDureza);

            numColumna++;
        }

        // Aceptación/Rechazo
        int numCellAR = (dirm.getAceptacion() == 1) ? 2 : 7;
        XSSFRow rowAR = sheet.getRow(39);
        XSSFCell celdaAR = rowAR.getCell(numCellAR);

        XSSFRow row = sheet.getRow(39);
        XSSFCell celdaAR2 = (numCellAR == 2) ? row.getCell(7) : row.getCell(2);
        celdaAR2.setCellValue("");
        celdaAR.setCellValue("√");

        XSSFFont fuente = formato.crearFuente(workbook, "Calibri", (short) 10, false);
        XSSFCellStyle estilo = formato.bordeGrueso(workbook);
        estilo.setFont(fuente);

        celdaAR2.setCellStyle(estilo);

        setValorCeldasHojaInstruccion(sheet, 34, 1, dirm.getObservacionesRD());
        setValorCeldasHojaInstruccion(sheet, 42, 1, dirm.getObsMP());
        setValorCeldasHojaInstruccion(sheet, 60, 1, dirm.getInspector());
    }

    private void procesarPaginaDosHJ(XSSFWorkbook workbook, Connection conexion, DatosIRM dirm, JTable tabla) throws SQLException {
        XSSFSheet sheet2 = workbook.getSheetAt(1);
        workbook.setSheetName(workbook.getSheetIndex(sheet2), formato.eliminarSeparadores(dirm.getFechaInspeccion()));

        List<DatosFila> datosFilas = new ArrayList<>();

        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            Object especificacion = model.getValueAt(0, 0);
            Object calibres = model.getValueAt(0, 2);
            Object propiedad = model.getValueAt(i, 3);
            Object cumplePropiedad = model.getValueAt(i, 4);
            Object composicion = model.getValueAt(i, 5);
            Object cumpleComposicion = model.getValueAt(i, 6);
            int fila = aps.obtenerEspecificacion(conexion, especificacion.toString());
            DatosFila datosFila = new DatosFila(especificacion, calibres, propiedad, cumplePropiedad, composicion, cumpleComposicion, fila);
            datosFilas.add(datosFila);
        }

        int numeroFilaEspecificacion = 6;
        String especificacionAnterior = "";

        XSSFFont fuente2 = formato.crearFuente(workbook, "Calibri", (short) 10, false);
        XSSFCellStyle estilo2 = formato.bordeGrueso(workbook);
        estilo2.setFont(fuente2);

        XSSFCellStyle estilo3 = workbook.createCellStyle();
        XSSFFont fuente3 = formato.crearFuente(workbook, "Arial", (short) 6, false);
        estilo3.setBorderTop(BorderStyle.MEDIUM);
        estilo3.setFont(fuente3);

        boolean primeraEspecificacion = true;

        for (DatosFila datosFila : datosFilas) {
            String especificacionActual = datosFila.getEspecificacion().toString();
            if (!especificacionActual.equals(especificacionAnterior)) {
                numeroFilaEspecificacion = datosFila.getElemento(especificacionActual);
                primeraEspecificacion = true;
            }

            XSSFRow row2 = sheet2.getRow(numeroFilaEspecificacion);
            if (row2 == null) {
                row2 = sheet2.createRow(numeroFilaEspecificacion);
            }

            XSSFCellStyle estiloCheckbox = workbook.createCellStyle();
            estiloCheckbox.setLocked(true);

            if (primeraEspecificacion) {
                XSSFCell chkCalibre = row2.createCell(4);
                XSSFCell cellCalibre = row2.createCell(5);
                cellCalibre.setCellValue(datosFila.getCalibre().toString());
                cellCalibre.setCellStyle(estilo3);
                chkCalibre.setCellValue("R");
                chkCalibre.setCellStyle(formato.estiloTablaAnchoLargoHI(workbook));
                primeraEspecificacion = false;
            }

            XSSFCell cellCumplePropiedad = row2.createCell(10);
            if (datosFila.getPropiedad() == null || datosFila.getPropiedad().equals("")) {
                cellCumplePropiedad.setCellValue("");
            } else {
                String cmPm = (datosFila.getCmPm().equals(true)) ? "X" : "√";
                cellCumplePropiedad.setCellValue(cmPm);
            }
            cellCumplePropiedad.setCellStyle(estilo2);

            XSSFCell cellCumpleComposicion = row2.createCell(13);
            if (datosFila.getComposicion() == null || datosFila.getComposicion().equals("")) {
                cellCumpleComposicion.setCellValue("");
            } else {
                String cmCq = (datosFila.getCmCq().toString().equals(true)) ? "X" : "√";
                cellCumpleComposicion.setCellValue(cmCq);
            }

            cellCumpleComposicion.setCellStyle(estilo2);
            numeroFilaEspecificacion++;
            especificacionAnterior = especificacionActual;
        }
    }

    private String extraerNumeroHoja(String noHoja) {
        String[] partes = noHoja.split("/");
        return partes.length > 1 ? partes[1] : noHoja;
    }

    private void guardarArchivoExcel(XSSFWorkbook workbook, String rutaArchivoSalida) throws IOException {
        try (FileOutputStream fos = new FileOutputStream("\\\\" + Utilidades.SERVIDOR + "\\" + rutaArchivoSalida)) {
            workbook.write(fos);
        }
    }

    public String editarHojaInstruccion(String rutaHojaInstruccion, InspeccionReciboM irm) {
        FileInputStream fis = null;
        String nuevoArchivo = "";
        try {
            fis = new FileInputStream("\\\\" + Utilidades.SERVIDOR + "/" + rutaHojaInstruccion);
            XSSFWorkbook workbook;
            String numeroStr;
            try {
                workbook = new XSSFWorkbook(fis);
                XSSFSheet hoja1 = workbook.getSheetAt(0);

                // Número de Hoja
                String[] partes = irm.getNoHoja().split("/"); // Se divide el valor de NoHoja para solo obtener el número
                numeroStr = partes[1];
                int numero = Integer.parseInt(numeroStr);
                if (numero < 10) { // Eliminar ceros a la izquierda
                    numeroStr = String.valueOf(numero);
                }
                workbook.setSheetName(workbook.getSheetIndex(hoja1), numeroStr); // Cambiar el nombre de la Hoja con el número previamente obtenido

                // Modifica los campos con la información del nuevo registro
                setDatosCeldas(hoja1, 5, 2, numeroStr); // Número de Hoja
                setDatosCeldas(hoja1, 9, 8, irm.getFechaFactura()); // Fecha Factura
                setDatosCeldas(hoja1, 9, 6, irm.getNoFactura()); // No. Factura
                setDatosCeldas(hoja1, 13, 7, irm.getPzKg()); // Pz/Kg

                // Descripción
                XSSFRow fila3 = hoja1.getRow(9); // Obtén la fila
                XSSFCell celda3 = fila3.getCell(1); // Obtén la celda en la fila
                String txtTexto = celda3.getStringCellValue();
                if (txtTexto.contains("HOJA") && irm.getpLamina().equals("CINTA")) {
                    txtTexto = txtTexto.replace("HOJA", "CINTA");
                } else if (txtTexto.contains("CINTA") && irm.getpLamina().equals("HOJA")) {
                    txtTexto = txtTexto.replace("CINTA", "HOJA");
                }
                celda3.setCellValue(txtTexto);

                nuevoArchivo = "archivos\\InspeccionRecibo\\HojasInstruccion\\HI-" + numeroStr + "-" + formato.eliminarSeparadores(irm.getFechaFactura()) + ".xlsx";

                // Crear un nuevo archivo con los cambios
                try (FileOutputStream fos = new FileOutputStream("\\\\" + Utilidades.SERVIDOR + "\\" + nuevoArchivo)) {
                    workbook.write(fos); // Escribir los cambios en el nuevo archivo
                }

            } catch (IOException ex) {
                Logger.getLogger(InspeccionReciboServicio.class.getName()).log(Level.SEVERE, null, ex);
            }
            return nuevoArchivo;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(InspeccionReciboServicio.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(InspeccionReciboServicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return nuevoArchivo;
    }

    public String generarArchivoRetencionDimensional(Connection conexion, List<AceptacionPc3> ap3m, AceptacionPc2 apc2) throws IOException, SQLException, ClassNotFoundException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("jc/doctos/RETENCION-DIMENSIONAL.xlsx");

        if (inputStream != null) {
            XSSFWorkbook workbook = null;
            try {
                workbook = new XSSFWorkbook(inputStream);  // Crear el workbook usando el inputStream

                // Recuperar la información relacionada
                List<AceptacionPc1> ap1m = aps.obtenerAceptacionPc1(conexion, ap3m.get(0).getComponente());

                if (ap1m == null || ap1m.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No se encontró información sobre los componentes");
                } else {
                    // Procesar el contenido del archivo Excel
                    procesarPaginaUnoRD(workbook, ap1m);
                    procesarPaginaDosRD(conexion, workbook, ap1m, ap3m, aps, apc2);
                }

                // Crear un nuevo archivo Excel para guardar los cambios
                String newFilePath = "\\\\" + Utilidades.SERVIDOR + "\\" + "archivos\\AceptacionProducto\\" + "-" + ap1m.get(0).getComponente() + ".xlsx";
                try (FileOutputStream fos = new FileOutputStream(newFilePath)) {
                    workbook.write(fos);  // Guardar los cambios en el nuevo archivo Excel
                    nuevaRutaArchivoRD = "archivos\\AceptacionProducto\\" + "-" + ap1m.get(0).getComponente() + ".xlsx";
                }

            } catch (IOException e) {
                Utilidades.manejarExcepcion("Error al procesar el archivo Excel: ", e);
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Utilidades.manejarExcepcion("Error al generar el Archivo de Retención Dimensional: ", e);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo encontrar el archivo Excel.");
        }

        return nuevaRutaArchivoRD;

    }

    private void procesarPaginaUnoRD(XSSFWorkbook workbook, List<AceptacionPc1> ap1m) {
        int fila = 6;
        Set<String> valoresUnicos = new HashSet<>(); // Mapa para guardar datos únicos 

        for (int i = 0; i < ap1m.size(); i++) {
            XSSFSheet sheet = workbook.getSheetAt(0); // Obtén la primera hoja del libro
            AceptacionPc1 aceptacionPc1 = ap1m.get(i);

            // Verifica si los valores ya han sido impresos
            String claveUnica = aceptacionPc1.getFecha() + aceptacionPc1.getNoRollo() + aceptacionPc1.getInspVisual() + aceptacionPc1.getObservacion();

            if (valoresUnicos.contains(claveUnica)) { // Si los valores ya han sido impresos, pasa al siguiente registro
                continue;
            }

            // Se agrega la información en la hoja 1
            agregarFilaInformacion(sheet, fila, 3, aceptacionPc1.getFecha()); // Fecha
            agregarFilaInformacion(sheet, fila, 8, aceptacionPc1.getNoRollo()); // NoRollo
            agregarFilaInformacion(sheet, fila, 11, aceptacionPc1.getInspVisual()); // Inspección Visual
            agregarFilaInformacion(sheet, fila, 16, aceptacionPc1.getObservacion()); // Observación

            valoresUnicos.add(claveUnica);

            if (fila == 34) {
                fila = 41;
            }
            fila++;
        }
    }

    private void procesarPaginaDosRD(Connection conexion, XSSFWorkbook workbook, List<AceptacionPc1> ap1m, List<AceptacionPc3> ap3m, AceptacionProductoServicio aps, AceptacionPc2 apc2) {
        XSSFSheet hoja2 = workbook.getSheetAt(1);

        mostrarComponentes(hoja2, ap1m, ap3m);
        mostrarProcesos(hoja2, ap1m);

        Comparator<String> dateComparator = (fecha1, fecha2) -> { // Expresión lambda para comparar fechas
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date date1 = sdf.parse(fecha1);
                Date date2 = sdf.parse(fecha2);
                return date1.compareTo(date2);
            } catch (ParseException e) {
                return 0;
            }
        };

        // Se crean los mapas para almacenar la información según su número de Operación
        TreeMap<String, TreeMap<String, Map<String, String>>> mapVariablesNoOp12 = crearTreeMap(dateComparator);
        TreeMap<String, TreeMap<String, Map<String, String>>> mapVariablesNoOp3 = crearTreeMap(dateComparator);
        TreeMap<String, TreeMap<String, Map<String, String>>> mapVariablesNoOp4 = crearTreeMap(dateComparator);
        TreeMap<String, TreeMap<String, Map<String, String>>> mapVariablesNoOp5 = crearTreeMap(dateComparator);

        formato.estiloCeldasRD(workbook, 7, 90, true); // Se aplica el estilo a las celdas en el documento de excel

        // Listas para almacenar las variables en orden
        List<String> listVariables1 = new ArrayList<>();
        List<String> listVariables3 = new ArrayList<>();
        List<String> listVariables4 = new ArrayList<>();
        List<String> listVariables5 = new ArrayList<>();

        TreeMap<String, TreeMap<String, Map<String, String>>> selectedMap; // Variable para seleccionar el mapa y guardar los datos en el mapa elegido
        for (AceptacionPc3 dataVariable : ap3m) {
            String variable = dataVariable.getVariable() + "\n" + dataVariable.getEspecificacion();
            String valor = dataVariable.getValor();

            switch (dataVariable.getNoOp()) { // Seleccionar el mapa según el número de operación
                case "1":
                case "2":
                    selectedMap = mapVariablesNoOp12;
                    if (!listVariables1.contains(variable)) {
                        listVariables1.add(variable);
                    }
                    break;
                case "3":
                    selectedMap = mapVariablesNoOp3;
                    if (!listVariables3.contains(variable)) {
                        listVariables3.add(variable);
                    }
                    break;
                case "4":
                    selectedMap = mapVariablesNoOp4;
                    if (!listVariables4.contains(variable)) {
                        listVariables4.add(variable);
                    }
                    break;
                case "5":
                case "IF":
                    selectedMap = mapVariablesNoOp5;
                    if (!listVariables5.contains(variable)) {
                        listVariables5.add(variable);
                    }
                    break;
                default:
                    selectedMap = null; // Manejar valores de noOp desconocidos
                    break;
            }

            if (selectedMap != null) {

                try {
                    String id = dataVariable.getFecha();
                    String comp = dataVariable.getComponente();
                    List<AceptacionPc2> objetoAp2m = aps.buscarObjetoEnAp2mPorFecha(conexion, id, comp, apc2);

                    // Actualizar el mapa seleccionado con los datos
                    selectedMap.computeIfAbsent(dataVariable.getFecha(), k -> new TreeMap<>())
                            .computeIfAbsent(dataVariable.getNoOp(), k -> new LinkedHashMap<>())
                            .put(variable, valor);

                    // Agregar campos de ap2m al mapa
                    int numO = 0;
                    for (AceptacionPc2 dataVariable3 : objetoAp2m) {
                        Map<String, String> variablesMap = selectedMap.get(dataVariable.getFecha()).get(dataVariable.getNoOp());
                        variablesMap.put("noOrden" + numO, dataVariable3.getNoOrden());
                        variablesMap.put("tamLote" + numO, dataVariable3.getTamLote());
                        variablesMap.put("tamMta" + numO, dataVariable3.getTamMta());
                        variablesMap.put("insp" + numO, dataVariable3.getInspector());
                        variablesMap.put("turno" + numO, dataVariable3.getTurno());
                        variablesMap.put("disp" + numO, dataVariable3.getDisp());
                        numO++;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GeneradorExcel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        getProceso(mapVariablesNoOp12, columnaBase, listVariables1, filaBaseNoOp1, hoja2, fila1, workbook, 5);
        getProceso(mapVariablesNoOp3, columnaBase, listVariables3, filaBaseNoOp3, hoja2, fila3, workbook, 43);
        getProceso(mapVariablesNoOp4, columnaBase, listVariables4, filaBaseNoOp4, hoja2, fila4, workbook, 81);
        getProceso(mapVariablesNoOp5, columnaBase, listVariables5, filaBaseNoOp5, hoja2, fila5, workbook, 119);
    }

    private void getProceso(TreeMap<String, TreeMap<String, Map<String, String>>> map, int columnaBase, List<String> listVariables, int fila, XSSFSheet sheet2, int rows, XSSFWorkbook workbook, int filaD) {
        int tamanoEntrada2 = calcularTamanoEntrada(map); // Calcula el tamaño del mapa
        int filasNecesarias = Math.max(0, tamanoEntrada2 - 28); // Calcula si se deben agregar más filas al documento

        ajustarFilasNecesarias(sheet2, fila, filasNecesarias, columnaBase, listVariables, filaD, tamanoEntrada2); // Ajusta las filas necesarias en caso de que filasNecesarias sea mayor a 0

        for (Map.Entry<String, TreeMap<String, Map<String, String>>> entrada1 : map.entrySet()) {
            for (Map.Entry<String, Map<String, String>> entrada2 : entrada1.getValue().entrySet()) {
                String noOp = entrada2.getKey(); // Convertir noOp a entero
                XSSFRow row = sheet2.getRow(fila);
                fila++; // Incrementar la fila para el próximo valor con noOp igual a 1
                if (row != null) {
                    XSSFCell cellNoOp = validarCeldas(row, 0, noOp); // Número de Operación
                    XSSFCell cellFecha = validarCeldas(row, 1, entrada1.getKey()); // Fecha

                    XSSFRow variableRow = sheet2.getRow(rows);
                    if (variableRow == null) {
                        variableRow = sheet2.createRow(rows);
                    }

                    int variableColumna = 9; // Se comienzan a imprimir desde esta columna la información de las variables
                    int celdaBarra = 8; // Se comienzan a imprimir las diagonales que dividen las variables
                    int celdaVar = 9; // Se comienzan a imprimir los valores de las variables

                    for (String variable : listVariables) {
                        XSSFCell variableCelda = variableRow.getCell(variableColumna);
                        if (variableCelda == null) { // Si la celda es nula...
                            variableCelda = variableRow.createCell(variableColumna); // Si la celda es nula, crea una nueva celda en su lugar

                            sheet2.addMergedRegion(new CellRangeAddress(filaD, filaD + 3, celdaBarra, celdaBarra)); // Se combina las celdas donde se muestra el diagonal
                            sheet2.addMergedRegion(new CellRangeAddress(filaD, filaD + 3, celdaVar, celdaVar)); // Se combina las celdas para imprimir las variables y su especificación
                            sheet2.setColumnWidth(celdaBarra, 570); // Establece el ancho de la columna 0
                            sheet2.setColumnWidth(celdaVar, 1450); // Establece el ancho de la columna 0

                            // Crear filas y celdas
                            XSSFRow celdaOrigen = sheet2.getRow(filaD);
                            XSSFCell sourceCell = celdaOrigen.getCell(10);

                            XSSFRow targetRow = sheet2.getRow(filaD);
                            XSSFCell targetCell = targetRow.createCell(celdaBarra);

                            // Crear filas y celdas
                            XSSFRow sourceRow2 = sheet2.getRow(filaD);
                            XSSFCell sourceCell2 = sourceRow2.getCell(9);

                            XSSFRow targetRow2 = sheet2.getRow(filaD);
                            XSSFCell targetCell2 = targetRow2.createCell(celdaVar);

                            // Crear un estilo de celda
                            XSSFCellStyle cellStyle4 = workbook.createCellStyle();
                            XSSFCellStyle cellStyle5 = workbook.createCellStyle();

                            cellStyle4.cloneStyleFrom(sourceCell.getCellStyle()); // Copia el estilo de la celda de origen
                            cellStyle5.cloneStyleFrom(sourceCell2.getCellStyle()); // Copia el estilo de la celda de origen
                            sheet2.setColumnWidth(celdaBarra, 600);

                            // Aplicar el estilo a la celda de destino
                            targetCell.setCellStyle(cellStyle4);
                            targetCell2.setCellStyle(cellStyle5);

                        }

                        variableCelda.setCellValue(variable);
                        celdaBarra += 2;
                        celdaVar += 2;
                        variableColumna += 2;

                    }

                    Map<String, String> variablesMap = entrada2.getValue();
                    int columna = columnaBase; // Inicializar la columna en función de columnaBase

                    // Crear el estilo de celda fuera del bucle
                    XSSFCellStyle cellStyle = workbook.createCellStyle();
                    XSSFRow sourceRow = sheet2.getRow(9);
                    XSSFCell sourceCell = sourceRow.getCell(columnaBase);
                    cellStyle.cloneStyleFrom(sourceCell.getCellStyle()); // Clonar el estilo de celda de origen

                    for (String variable : listVariables) {
                        XSSFCell cellVariable = row.getCell(columna);
                        XSSFCell cellVariable2 = row.getCell(columna + 1);

                        // Si la celda es nula, crea una nueva celda en su lugar
                        if (cellVariable == null) {
                            cellVariable = row.createCell(columna);
                        }
                        if (cellVariable2 == null) {
                            cellVariable2 = row.createCell(columna + 1);
                        }

                        // Configurar el valor de la celda
                        if (variablesMap.containsKey(variable)) {
                            cellVariable.setCellValue(variablesMap.get(variable));
                        } else {
                            cellVariable.setCellValue("N/A");
                        }

                        // Configurar el estilo de celda clonado para la nueva celda
                        cellVariable.setCellStyle(cellStyle);
                        cellVariable2.setCellStyle(cellStyle);

                        // Combinar las celdas en pares
                        CellRangeAddress region = new CellRangeAddress(
                                row.getRowNum(), // Fila inicial
                                row.getRowNum(), // Fila final (misma fila)
                                columna, // Columna inicial
                                columna + 1 // Columna final (celda siguiente)
                        );

                        // Verificar si la región ya está combinada
                        boolean combinada = false;
                        for (int i = 0; i < sheet2.getNumMergedRegions(); i++) {
                            CellRangeAddress mergedRegion = sheet2.getMergedRegion(i);
                            if (mergedRegion.getFirstRow() == region.getFirstRow()
                                    && mergedRegion.getLastRow() == region.getLastRow()
                                    && mergedRegion.getFirstColumn() == region.getFirstColumn()
                                    && mergedRegion.getLastColumn() == region.getLastColumn()) {
                                combinada = true;
                                break;
                            }
                        }

                        if (!combinada) { // Agregar la región combinada solo si no está combinada previamente
                            sheet2.addMergedRegion(region);
                        }

                        // Incrementar la columna para la siguiente iteración
                        columna += 2;
                    }

                    ////////////////////////////////////////////////////////////
                    // Concatenar valores de 'noOrden'
                    Set<String> valoresUnicosNoOrden = new HashSet<>();
                    Set<String> valoresUnicosTamLote = new HashSet<>();
                    Set<String> valoresUnicosTamMta = new HashSet<>();
                    Set<String> valoresUnicosInsp = new HashSet<>();
                    Set<String> valoresUnicosTurno = new HashSet<>();
                    Set<String> valoresUnicosDisp = new HashSet<>();

                    // Iterar sobre los valores y agregarlos a los conjuntos correspondientes
                    variablesMap.entrySet().forEach((variableEntry) -> {
                        String variableKey = variableEntry.getKey();
                        String valor = variableEntry.getValue();

                        if (variableKey.startsWith("noOrden")) {
                            if (!valor.isEmpty()) {
                                valoresUnicosNoOrden.add(valor);
                            }
                        } else if (variableKey.startsWith("tamLote")) {
                            if (!valor.isEmpty()) {
                                valoresUnicosTamLote.add(valor);
                            }
                        } else if (variableKey.startsWith("tamMta")) {
                            if (!valor.isEmpty()) {
                                valoresUnicosTamMta.add(valor);
                            }
                        } else if (variableKey.startsWith("insp")) {
                            if (!valor.isEmpty()) {
                                valoresUnicosInsp.add(valor);
                            }
                        } else if (variableKey.startsWith("turno")) {
                            if (!valor.isEmpty()) {
                                valoresUnicosTurno.add(valor);
                            }
                        } else if (variableKey.startsWith("disp")) {
                            if (!valor.isEmpty()) {
                                valoresUnicosDisp.add(valor);
                            }
                        }
                    });

                    // Crear cadenas de concatenación a partir de los valores únicos en los conjuntos
                    String noOrdenConcatenado = String.join("\n", valoresUnicosNoOrden);
                    String tamLoteConcatenado = String.join("\n", valoresUnicosTamLote);
                    String tamMtaConcatenado = String.join("\n", valoresUnicosTamMta);
                    String inspConcatenado = String.join("\n", valoresUnicosInsp);
                    String turnoConcatenado = String.join("\n", valoresUnicosTurno);
                    String dispConcatenado = String.join("\n", valoresUnicosDisp);

                    // Obtener el valor actual en la celda 
                    XSSFCell cellNoOrden = validarCeldas(row, 2, "");
                    XSSFCell cellTamLote = validarCeldas(row, 3, "");
                    XSSFCell cellTamMta = validarCeldas(row, 4, "");
                    XSSFCell cellInsp = validarCeldas(row, 5, "");
                    XSSFCell cellTurno = validarCeldas(row, 6, "");
                    XSSFCell cellDisp = validarCeldas(row, 7, "");

                    String valorActual = cellNoOrden.getStringCellValue();
                    String valorActual2 = cellTamLote.getStringCellValue();
                    String valorActual3 = cellTamMta.getStringCellValue();
                    String valorActual4 = cellInsp.getStringCellValue();
                    String valorActual5 = cellTurno.getStringCellValue();
                    String valorActual6 = cellDisp.getStringCellValue();

                    // Concatenar el valor actual con el nuevo valor de 'noOrden' (manteniendo los saltos de línea)
                    if (!valorActual.isEmpty()) {
                        noOrdenConcatenado = valorActual + "\n" + noOrdenConcatenado;
                    }

                    if (!valorActual2.isEmpty()) {
                        tamLoteConcatenado = valorActual2 + "\n" + tamLoteConcatenado;
                    }

                    if (!valorActual3.isEmpty()) {
                        tamMtaConcatenado = valorActual3 + "\n" + tamMtaConcatenado;
                    }

                    if (!valorActual4.isEmpty()) {
                        inspConcatenado = valorActual4 + "\n" + inspConcatenado;
                    }

                    if (!valorActual5.isEmpty()) {
                        turnoConcatenado = valorActual5 + "\n" + turnoConcatenado;
                    }

                    if (!valorActual6.isEmpty()) {
                        dispConcatenado = valorActual6 + "\n" + dispConcatenado;
                    }

                    // Se aplica el estilo a las celdas
                    cellNoOp.setCellStyle(formato.estiloCeldasRD(workbook));
                    cellFecha.setCellStyle(formato.estiloCeldasRD(workbook));
                    cellNoOrden.setCellStyle(formato.estiloCeldasRD(workbook));
                    cellTamLote.setCellStyle(formato.estiloCeldasRD(workbook));
                    cellTamMta.setCellStyle(formato.estiloCeldasRD(workbook));
                    cellInsp.setCellStyle(formato.estiloCeldasRD(workbook));
                    cellTurno.setCellStyle(formato.estiloCeldasRD(workbook));
                    cellDisp.setCellStyle(formato.estiloCeldasRD(workbook));

                    // Colocar el valor concatenado en la celda correspondiente
                    cellNoOrden.setCellValue(noOrdenConcatenado);
                    cellTamLote.setCellValue(tamLoteConcatenado);
                    cellTamMta.setCellValue(tamMtaConcatenado);
                    cellInsp.setCellValue(inspConcatenado);
                    cellTurno.setCellValue(turnoConcatenado);
                    cellDisp.setCellValue(dispConcatenado);
                }
            }
        }
    }

    private void agregarFilaInformacion(XSSFSheet sheet, int fila, int columna, String valor) {
        XSSFRow row = sheet.getRow(fila); // Obtén la fila
        if (row == null) {
            row = sheet.createRow(fila); // Crea la fila si no existe
        }
        XSSFCell cell = row.getCell(columna);
        if (cell == null) {
            cell = row.createCell(columna); // Crea la celda si no existe
        }
        cell.setCellValue(valor); // Modifica el valor de la celda
    }

    private XSSFCell validarCeldas(XSSFRow row, int fila, String valor) {
        XSSFCell celda = row.getCell(fila);
        if (celda == null) {
            celda = row.createCell(fila);
        }
        celda.setCellValue(valor);
        return celda;
    }

    private void mostrarProcesos(XSSFSheet sheet, List<AceptacionPc1> ap1m) {
        String noOps = ap1m.get(0).getNoOps();

        for (int i = 1; i <= 5; i++) {

            XSSFRow rowNoOps;
            XSSFCell cellNoOps;

            if (noOps.equalsIgnoreCase("IF")) {
                rowNoOps = sheet.getRow(118);
                if (rowNoOps == null) {
                    rowNoOps = sheet.createRow(118);
                }
                cellNoOps = rowNoOps.getCell(28);
                if (cellNoOps == null) {
                    cellNoOps = rowNoOps.createCell(28);
                }
                cellNoOps.setCellValue("X");
                break;
            }

            switch (i) {
                case 1:
                    rowNoOps = sheet.getRow(4);
                    if (rowNoOps == null) {
                        rowNoOps = sheet.createRow(4);
                    }
                    cellNoOps = rowNoOps.getCell(12);
                    if (cellNoOps == null) {
                        cellNoOps = rowNoOps.createCell(12);
                    }
                    cellNoOps.setCellValue("X");
                    break;
                case 2:
                    rowNoOps = sheet.getRow(4);
                    if (rowNoOps == null) {
                        rowNoOps = sheet.createRow(4);
                    }
                    cellNoOps = rowNoOps.getCell(16);
                    if (cellNoOps == null) {
                        cellNoOps = rowNoOps.createCell(16);
                    }
                    cellNoOps.setCellValue("X");
                    break;
                case 3:
                    rowNoOps = sheet.getRow(42);
                    if (rowNoOps == null) {
                        rowNoOps = sheet.createRow(42);
                    }
                    cellNoOps = rowNoOps.getCell(20);
                    if (cellNoOps == null) {
                        cellNoOps = rowNoOps.createCell(20);
                    }
                    cellNoOps.setCellValue("X");
                    break;
                case 4:

                    rowNoOps = sheet.getRow(80);
                    if (rowNoOps == null) {
                        rowNoOps = sheet.createRow(80);
                    }
                    cellNoOps = rowNoOps.getCell(24);
                    if (cellNoOps == null) {
                        cellNoOps = rowNoOps.createCell(24);
                    }
                    cellNoOps.setCellValue("X");
                    break;
                case 5:
                    rowNoOps = sheet.getRow(118);
                    if (rowNoOps == null) {
                        rowNoOps = sheet.createRow(118);
                    }
                    cellNoOps = rowNoOps.getCell(28);
                    if (cellNoOps == null) {
                        cellNoOps = rowNoOps.createCell(28);
                    }
                    cellNoOps.setCellValue("X");
                    break;
            }
        }

    }

    private void mostrarComponentes(XSSFSheet sheet, List<AceptacionPc1> ap1m, List<AceptacionPc3> ap3m) {
        for (int filaInfo : FILAS_INFO) {
            for (int indexColumna : COLUMNAS_COMPONENTE) {
                XSSFRow rowComponente = sheet.getRow(filaInfo);
                XSSFCell cell = rowComponente.getCell(indexColumna);
                switch (indexColumna) {
                    case 13:
                        cell.setCellValue(ap1m.get(0).getComponente());
                        break;
                    case 20:
                        cell.setCellValue(ap1m.get(0).getNoParte());
                        break;
                    case 26:
                        ap3m.forEach((ap3) -> {
                            String noOp = ap3.getNoOp();
                            int filaCorrespondiente = obtenerFilaCorrespondiente(noOp);
                            if (filaCorrespondiente == filaInfo) {
                                cell.setCellValue(ap3.getNoTroquel());
                            }
                        });
                        break;
                }
            }
        }
    }

    private int obtenerFilaCorrespondiente(String noOp) {
        switch (noOp) {
            case "1":
            case "2":
                return 2;
            case "3":
                return 40;
            case "4":
                return 78;
            case "5":
            case "IF":
                return 116;
            default:
                throw new IllegalArgumentException("Número de operación no válido: " + noOp);
        }
    }

    private TreeMap<String, TreeMap<String, Map<String, String>>> crearTreeMap(Comparator<String> comparator) {
        return new TreeMap<>(comparator);
    }

    private int calcularTamanoEntrada(TreeMap<String, TreeMap<String, Map<String, String>>> map) {
        return map.values().stream().mapToInt(TreeMap::size).sum();
    }

    private void ajustarFilasNecesarias(XSSFSheet sheet2, int fila, int filasNecesarias, int columnaBase, List<String> listVariables, int filaD, int tamanoEntrada2) {
        if (filasNecesarias > 0) { // Inserta las filas necesarias en la hoja

            sheet2.shiftRows(fila + 1, sheet2.getLastRowNum() + 1, filasNecesarias, true, false);

            // Ajusta las filas base para reflejar las inserciones
            filaBaseNoOp1 += filasNecesarias;
            filaBaseNoOp3 += filasNecesarias;
            filaBaseNoOp4 += filasNecesarias;
            filaBaseNoOp5 += filasNecesarias;

            fila1 += filasNecesarias;
            fila3 += filasNecesarias;
            fila4 += filasNecesarias;
            fila5 += filasNecesarias;

            XSSFRow filaACopiar = sheet2.getRow(fila); // Obtener la fila que deseas copiar

            for (int i = 0; i < filasNecesarias; i++) {
                int filaNecesaria = fila + i + 1; // Fila actual
                XSSFRow nuevaFila = sheet2.createRow(filaNecesaria);

                copiarFilaConCombinacion(sheet2, filaACopiar, nuevaFila, columnaBase, listVariables); // Copia la fila deseada con combinación

                sheet2.setRowBreak(filaNecesaria); // Ajusta el salto de página en cada fila insertada
            }
        }
    }

    private void copiarFilaConCombinacion(XSSFSheet sheet2, XSSFRow filaOrigen, XSSFRow filaNueva, int columnaBase, List<String> listVariables) {
        filaNueva.setHeight(filaOrigen.getHeight());

        for (Cell viejaCelda : filaOrigen) {
            int columnIndex = viejaCelda.getColumnIndex();
            XSSFCell nuevaCelda = filaNueva.createCell(columnIndex);
            nuevaCelda.setCellStyle(viejaCelda.getCellStyle());

            if (columnIndex >= columnaBase && (columnIndex - columnaBase) % 2 == 0) {
                // Combina las celdas a partir de la columnaBase en incrementos de 2
                int mergedColumnIndex = columnIndex + 1;
                sheet2.addMergedRegion(new CellRangeAddress(filaNueva.getRowNum(), filaNueva.getRowNum(), columnIndex, mergedColumnIndex));
            }
        }
    }

    private void setValorCeldasHojaInstruccion(XSSFSheet sheet, int pocisionFila, int pocisionCelda, String valor) {
        XSSFRow fila = sheet.getRow(pocisionFila);
        XSSFCell celda = fila.getCell(pocisionCelda);
        celda.setCellValue(valor);
    }
}
