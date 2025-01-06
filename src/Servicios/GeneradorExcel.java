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
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GeneradorExcel {

    private Connection conexion;
    private InspeccionReciboServicio irs;

    public GeneradorExcel() {
        try {
            this.conexion = Conexion.getInstance().conectar();
            
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al establecer la conexión: ", ex);
            Logger.getLogger(GeneradorExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public GeneradorExcel(InspeccionReciboServicio irs) {
        this.irs = irs;
    }

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

    private static final int[] FILAS_INFO = {2, 40, 78, 116}; // 
//    private static final int[] FILAS_PROCESOS = {4, 42, 80, 118}; // Celdas donde se imprimen las "x" de los procesos 
    private static final int[] COLUMNAS_COMPONENTE = {13, 20, 26}; // Columnas donde se imprime la información del componente

    public void generarInspeccionReciboXLS() throws SQLException, ParseException {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet hojaExcel = workbook.createSheet("Inspeccion Recibo");

        XSSFCell tituloCelda = hojaExcel.createRow(0).createCell(0);

        tituloCelda.setCellValue("INSPECCIÓN/RECIBO");
        tituloCelda.setCellStyle(formato.estiloTitulo(workbook, "Arial", (short) 15, true));

        CellRangeAddress rangoCeldasCombinadas = new CellRangeAddress(0, 0, 0, 9);
        hojaExcel.addMergedRegion(rangoCeldasCombinadas);

        XSSFRow encabezados = hojaExcel.createRow(1);
        String[] titulosFilas = {"No", "FECHA DE \n FACTURA", "PROVEEDOR", "NO.\n FACTURA", "NO. DE \n PEDIDO", "CALIBRE", "PRESENTACIÓN\n DE LAMINA", "NO. ROLLO", "PZ/KG", "ESTATUS"};

        for (int i = 0; i < titulosFilas.length; i++) {
            XSSFCell cell = encabezados.createCell(i);
            cell.setCellValue(titulosFilas[i]);
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
            hojaExcel.setColumnWidth(i, columnaAncho * 256);
            cell.setCellStyle(formato.estiloEncabezados(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
        }

        int indexFila = 2;

        List<InspeccionReciboM> registrosIR = irs.obtenerTodasInspecciones(conexion);
        List<String> fechas = new ArrayList<>();

        for (InspeccionReciboM registro : registrosIR) {

            Row filaDatos = hojaExcel.createRow(indexFila);
            String noHoja = registro.getNoHoja();

            String numeroHojaStr = noHoja.substring(noHoja.length() - 3);

            filaDatos.createCell(0).setCellValue(numeroHojaStr);
            filaDatos.createCell(1).setCellValue(registro.getFechaFactura());
            filaDatos.createCell(2).setCellValue(registro.getProveedor());
            filaDatos.createCell(3).setCellValue(registro.getNoFactura());
            filaDatos.createCell(4).setCellValue(registro.getNoPedido());
            filaDatos.createCell(5).setCellValue(registro.getCalibre());
            filaDatos.createCell(6).setCellValue(registro.getpLamina());
            filaDatos.createCell(7).setCellValue(registro.getNoRollo());
            filaDatos.createCell(8).setCellValue(registro.getPzKg());
            filaDatos.createCell(9).setCellValue(registro.getEstatus());
            filaDatos.createCell(10);
            fechas.add(registro.getFechaFactura());

            for (int i = 0; i < 11; i++) {
                XSSFCell cell = (XSSFCell) filaDatos.getCell(i);
                switch (cell.getStringCellValue()) {
                    case "LIBERADA":
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
            indexFila++;
        }

        Sheet hoja1 = workbook.getSheetAt(0);

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        String mesActual = null;
        int primeraFilaMes = 2;
        indexFila = 2;

        for (String fechaString : fechas) {
            Date fecha = formatoFecha.parse(fechaString);
            String mes = new SimpleDateFormat("MMMM").format(fecha).toUpperCase();

            if (mesActual == null) {
                mesActual = mes;
            }

            if (!mes.equals(mesActual)) {

                if (primeraFilaMes < indexFila - 1) {
                    CellRangeAddress rango = new CellRangeAddress(primeraFilaMes, indexFila - 1, 10, 10);
                    hoja1.addMergedRegion(rango);
                }
                primeraFilaMes = indexFila;
                mesActual = mes;
            }

            Row fila2 = hoja1.getRow(indexFila) == null ? hoja1.createRow(indexFila) : hoja1.getRow(indexFila);

            XSSFCell celdaFecha = (XSSFCell) fila2.createCell(10);
            celdaFecha.setCellValue(mes);
            celdaFecha.setCellStyle(formato.formatoMeses(workbook));
            indexFila++;
        }

        if (primeraFilaMes < indexFila - 1) {
            CellRangeAddress rango = new CellRangeAddress(primeraFilaMes, indexFila - 1, 10, 10);
            hoja1.addMergedRegion(rango);
        }

        File archivo = new File("InspeccionRecibo.xlsx");
        try (FileOutputStream outputStream = new FileOutputStream(archivo)) {
            workbook.write(outputStream);
            System.out.println("Escribiendo en disco... Listo");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            irs.manejarExcepcion("Error al Generar el Archivo InspeccionRecibo.xlsx", ex);
        }
        System.out.println("Proceso completado.");
    }

    public String getDatosCeldas(Sheet hoja, int fila, int columna) {
        Cell celda = hoja.getRow(fila).getCell(columna);
        return celda.getStringCellValue();
    }

    public void setDatosCeldas(Sheet hoja, int row, int columna, String contenido) {
        Row fila = hoja.getRow(row);
        if (fila == null) {
            fila = hoja.createRow(row);
        }
        Cell celda = fila.getCell(columna);
        if (celda == null) {
            celda = fila.createCell(columna);
        }
        celda.setCellValue(contenido);
    }

    public boolean isAceptacion(Sheet hoja1, int row, int column) {
        Row fila = hoja1.getRow(row);
        Cell celda = fila.getCell(column);
        return celda.getStringCellValue().equalsIgnoreCase("√");
    }

    public String generarHojaInstruccion(Connection conexion, DatosIRM dirm, InspeccionReciboM irm, JTable tblAL, JTable tabla, List listaAL, List listaRD) throws IOException, SQLException, ClassNotFoundException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("jc/doctos/HojaInstruccion.xlsx");

        if (inputStream != null) {
            XSSFWorkbook workbook = null;
            try {
                workbook = new XSSFWorkbook(inputStream);  // Crear el workbook usando el inputStream
                procesarPaginaUnoHJ(workbook, irm, dirm, listaAL, listaRD);
                procesarPaginaDosHJ(workbook, conexion, dirm, tabla);

                // Crear un nuevo archivo de Excel para guardar los cambios
                nuevaRutaArchivo = "HI-" + numeroStr + "-" + formato.eliminarSeparadores(dirm.getFechaInspeccion()) + ".xlsx ";  // Ruta del nuevo archivo de Excel
                FileOutputStream fos = new FileOutputStream(nuevaRutaArchivo);
                workbook.write(fos);

                // Crear un nuevo archivo Excel para guardar los cambios
                String newFilePath = "\\\\" + Utilidades.SERVIDOR + "\\" + "archivos\\InspeccionRecibo\\HojasInstruccion\\" + nuevaRutaArchivo;
//                try (FileOutputStream fos = new FileOutputStream(newFilePath)) {
//                    workbook.write(fos);  // Guardar los cambios en el nuevo archivo Excel
//                }
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
        return nuevaRutaArchivo;
    }

    private void procesarPaginaUnoHJ(XSSFWorkbook workbook, InspeccionReciboM irm, DatosIRM dirm, List listaAL, List listaRD) {
        Sheet sheet = workbook.getSheetAt(0); // Obtén la primera hoja del libro

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
            Row row = sheet.getRow(numeroFila);
            if (row == null) {
                row = sheet.createRow(numeroFila);
            }

            Cell cellAncho = row.createCell(1); // Celda para el ancho
            cellAncho.setCellValue(medida.getAncho());

            Cell cellLargo = row.createCell(2); // Celda para el largo
            cellLargo.setCellValue(medida.getLargo());

            numeroFila++;

            //Aplicación del formato
            cellAncho.setCellStyle(formato.estiloTblAnchoLargo(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
            cellLargo.setCellStyle(formato.estiloTblAnchoLargo(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
        }

        int numColumna = 2;
        for (int i = 0; i < listaRD.size(); i++) {
            RugosidadDurezaM medida = (RugosidadDurezaM) listaRD.get(i);
            Row row = sheet.getRow(43);
            if (row == null) {
                row = sheet.createRow(43);
            }

            Row row2 = sheet.getRow(44);
            if (row == null) {
                row2 = sheet.createRow(44);
            }

            Cell cellAncho = row.createCell(numColumna);
            cellAncho.setCellValue(medida.getRugosidad());

            Cell cellLargo = row2.createCell(numColumna);
            cellLargo.setCellValue(medida.getDureza());

            numColumna++;

            cellAncho.setCellStyle(formato.estiloTblAnchoLargo(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
            cellLargo.setCellStyle(formato.estiloTblAnchoLargo(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
        }

        // Aceptación/Rechazo
        int numCellAR = (dirm.getAceptacion() == 1) ? 2 : 7;
        Row rowAR = sheet.getRow(39);
        Cell celdaAR = rowAR.getCell(numCellAR);

        Row row = sheet.getRow(39);
        Cell celdaAR2 = (numCellAR == 2) ? row.getCell(7) : row.getCell(2);
        celdaAR2.setCellValue("");
        celdaAR.setCellValue("√");

        XSSFFont fuente = formato.crearFuente(workbook, "Calibri", (short) 10, false);
        CellStyle estilo = formato.bordeGrueso(workbook);
        estilo.setFont(fuente);

        celdaAR2.setCellStyle(estilo);

        setValorCeldasHojaInstruccion(sheet, 34, 1, dirm.getObservacionesRD());
        setValorCeldasHojaInstruccion(sheet, 42, 1, dirm.getObsMP());
        setValorCeldasHojaInstruccion(sheet, 60, 1, dirm.getInspector());
    }

    private void procesarPaginaDosHJ(XSSFWorkbook workbook, Connection conexion, DatosIRM dirm, JTable tabla) throws SQLException {
        Sheet sheet2 = workbook.getSheetAt(1);
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
        CellStyle estilo2 = formato.bordeGrueso(workbook);
        estilo2.setFont(fuente2);

        CellStyle estilo3 = workbook.createCellStyle();
        XSSFFont fuente3 = formato.crearFuente(workbook, "Arial", (short) 6, false);
        estilo3.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        estilo3.setFont(fuente3);

        boolean primeraEspecificacion = true;

        for (DatosFila datosFila : datosFilas) {
            String especificacionActual = datosFila.getEspecificacion().toString();
            if (!especificacionActual.equals(especificacionAnterior)) {
                numeroFilaEspecificacion = datosFila.getElemento(especificacionActual);
                primeraEspecificacion = true;
            }

            Row row2 = sheet2.getRow(numeroFilaEspecificacion);
            if (row2 == null) {
                row2 = sheet2.createRow(numeroFilaEspecificacion);
            }

            CellStyle estiloCheckbox = workbook.createCellStyle();
            estiloCheckbox.setLocked(true);

            if (primeraEspecificacion) {
                Cell chkCalibre = row2.createCell(4);
                Cell cellCalibre = row2.createCell(5);
                cellCalibre.setCellValue(datosFila.getCalibre().toString());
                cellCalibre.setCellStyle(estilo3);
                chkCalibre.setCellValue("R");
                chkCalibre.setCellStyle(formato.estiloTablaAnchoLargoHI(workbook));
                primeraEspecificacion = false;
            }

            Cell cellCumplePropiedad = row2.createCell(10);
            if (datosFila.getPropiedad() == null || datosFila.getPropiedad().equals("")) {
                cellCumplePropiedad.setCellValue("");
            } else {
                String cmPm = (datosFila.getCmPm().equals(true)) ? "X" : "√";
                cellCumplePropiedad.setCellValue(cmPm);
            }
            cellCumplePropiedad.setCellStyle(estilo2);

            Cell cellCumpleComposicion = row2.createCell(13);
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
            Sheet sheet = workbook.getSheetAt(0); // Obtén la primera hoja del libro
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

    private void getProceso(TreeMap<String, TreeMap<String, Map<String, String>>> map, int columnaBase, List<String> listVariables, int fila, Sheet sheet2, int rows, XSSFWorkbook workbook, int filaD) {
        int tamanoEntrada2 = calcularTamanoEntrada(map); // Calcula el tamaño del mapa
        int filasNecesarias = Math.max(0, tamanoEntrada2 - 28); // Calcula si se deben agregar más filas al documento

        ajustarFilasNecesarias(sheet2, fila, filasNecesarias, columnaBase, listVariables, filaD, tamanoEntrada2); // Ajusta las filas necesarias en caso de que filasNecesarias sea mayor a 0

        for (Map.Entry<String, TreeMap<String, Map<String, String>>> entrada1 : map.entrySet()) {
            for (Map.Entry<String, Map<String, String>> entrada2 : entrada1.getValue().entrySet()) {
                String noOp = entrada2.getKey(); // Convertir noOp a entero
                Row row = sheet2.getRow(fila);
                fila++; // Incrementar la fila para el próximo valor con noOp igual a 1
                if (row != null) {
                    Cell cellNoOp = validarCeldas(row, 0, noOp); // Número de Operación
                    Cell cellFecha = validarCeldas(row, 1, entrada1.getKey()); // Fecha

                    Row variableRow = sheet2.getRow(rows);
                    if (variableRow == null) {
                        variableRow = sheet2.createRow(rows);
                    }

                    int variableColumna = 9; // Se comienzan a imprimir desde esta columna la información de las variables
                    int celdaBarra = 8; // Se comienzan a imprimir las diagonales que dividen las variables
                    int celdaVar = 9; // Se comienzan a imprimir los valores de las variables

                    for (String variable : listVariables) {
                        Cell variableCelda = variableRow.getCell(variableColumna);
                        if (variableCelda == null) { // Si la celda es nula...
                            variableCelda = variableRow.createCell(variableColumna); // Si la celda es nula, crea una nueva celda en su lugar

                            sheet2.addMergedRegion(new CellRangeAddress(filaD, filaD + 3, celdaBarra, celdaBarra)); // Se combina las celdas donde se muestra el diagonal
                            sheet2.addMergedRegion(new CellRangeAddress(filaD, filaD + 3, celdaVar, celdaVar)); // Se combina las celdas para imprimir las variables y su especificación
                            sheet2.setColumnWidth(celdaBarra, 570); // Establece el ancho de la columna 0
                            sheet2.setColumnWidth(celdaVar, 1450); // Establece el ancho de la columna 0

                            // Crear filas y celdas
                            Row celdaOrigen = sheet2.getRow(filaD);
                            Cell sourceCell = celdaOrigen.getCell(10);

                            Row targetRow = sheet2.getRow(filaD);
                            Cell targetCell = targetRow.createCell(celdaBarra);

                            // Crear filas y celdas
                            Row sourceRow2 = sheet2.getRow(filaD);
                            Cell sourceCell2 = sourceRow2.getCell(9);

                            Row targetRow2 = sheet2.getRow(filaD);
                            Cell targetCell2 = targetRow2.createCell(celdaVar);

                            // Crear un estilo de celda
                            CellStyle cellStyle4 = workbook.createCellStyle();
                            CellStyle cellStyle5 = workbook.createCellStyle();

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
                    CellStyle cellStyle = workbook.createCellStyle();
                    Row sourceRow = sheet2.getRow(9);
                    Cell sourceCell = sourceRow.getCell(columnaBase);
                    cellStyle.cloneStyleFrom(sourceCell.getCellStyle()); // Clonar el estilo de celda de origen

                    for (String variable : listVariables) {
                        Cell cellVariable = row.getCell(columna);
                        Cell cellVariable2 = row.getCell(columna + 1);

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
                    Cell cellNoOrden = validarCeldas(row, 2, "");
                    Cell cellTamLote = validarCeldas(row, 3, "");
                    Cell cellTamMta = validarCeldas(row, 4, "");
                    Cell cellInsp = validarCeldas(row, 5, "");
                    Cell cellTurno = validarCeldas(row, 6, "");
                    Cell cellDisp = validarCeldas(row, 7, "");

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

    private void agregarFilaInformacion(Sheet sheet, int fila, int columna, String valor) {
        Row row = sheet.getRow(fila); // Obtén la fila
        if (row == null) {
            row = sheet.createRow(fila); // Crea la fila si no existe
        }
        Cell cell = row.getCell(columna);
        if (cell == null) {
            cell = row.createCell(columna); // Crea la celda si no existe
        }
        cell.setCellValue(valor); // Modifica el valor de la celda
    }

    private Cell validarCeldas(Row row, int fila, String valor) {
        Cell celda = row.getCell(fila);
        if (celda == null) {
            celda = row.createCell(fila);
        }
        celda.setCellValue(valor);
        return celda;
    }

    private void mostrarProcesos(XSSFSheet sheet, List<AceptacionPc1> ap1m) {
        String noOps = ap1m.get(0).getNoOps();

        for (int i = 1; i <= 5; i++) {

            Row rowNoOps;
            Cell cellNoOps;

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
                Row rowComponente = sheet.getRow(filaInfo);
                Cell cell = rowComponente.getCell(indexColumna);
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

    private void ajustarFilasNecesarias(Sheet sheet2, int fila, int filasNecesarias, int columnaBase, List<String> listVariables, int filaD, int tamanoEntrada2) {
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

            Row filaACopiar = sheet2.getRow(fila); // Obtener la fila que deseas copiar

            for (int i = 0; i < filasNecesarias; i++) {
                int filaNecesaria = fila + i + 1; // Fila actual
                Row nuevaFila = sheet2.createRow(filaNecesaria);

                copiarFilaConCombinacion(sheet2, filaACopiar, nuevaFila, columnaBase, listVariables); // Copia la fila deseada con combinación

                sheet2.setRowBreak(filaNecesaria); // Ajusta el salto de página en cada fila insertada
            }
        }
    }

    private void copiarFilaConCombinacion(Sheet sheet2, Row filaOrigen, Row filaNueva, int columnaBase, List<String> listVariables) {
        filaNueva.setHeight(filaOrigen.getHeight());

        for (Cell viejaCelda : filaOrigen) {
            int columnIndex = viejaCelda.getColumnIndex();
            Cell nuevaCelda = filaNueva.createCell(columnIndex);
            nuevaCelda.setCellStyle(viejaCelda.getCellStyle());

            if (columnIndex >= columnaBase && (columnIndex - columnaBase) % 2 == 0) {
                // Combina las celdas a partir de la columnaBase en incrementos de 2
                int mergedColumnIndex = columnIndex + 1;
                sheet2.addMergedRegion(new CellRangeAddress(filaNueva.getRowNum(), filaNueva.getRowNum(), columnIndex, mergedColumnIndex));
            }
        }
    }

    private void setValorCeldasHojaInstruccion(Sheet sheet, int pocisionFila, int pocisionCelda, String valor) {
        Row fila = sheet.getRow(pocisionFila);
        Cell celda = fila.getCell(pocisionCelda);
        celda.setCellValue(valor);
    }
}
