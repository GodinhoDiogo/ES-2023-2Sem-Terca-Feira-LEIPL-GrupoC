


import modules.Schedule;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ScheduleCSVFileReader {

	 public static List<Schedule> readFromFile(String arquivo) throws IOException {
		 List<Schedule> aulas = new ArrayList<Schedule>();
	        FileReader reader = new FileReader(arquivo);
	        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';'));
	        List<CSVRecord> linhas = csvParser.getRecords();
	        for (CSVRecord linha : linhas) {
	            Schedule aula = new Schedule();
	            aula.setCurso(linha.get(0));
	            aula.setUnidadeCurricular(linha.get(1));
	            aula.setTurno(linha.get(2));
	            aula.setTurma(linha.get(3));
	            aula.setInscritosNoTurno(linha.get(4));
	            aula.setDiaSemana(linha.get(5));
	            aula.setHorarioInicioAula(linha.get(6));
	            aula.setHorarioFimAula(linha.get(7));
	            aula.setDataAula(linha.get(8));
	            aula.setSalaAtribuida(linha.get(9));
	            aula.setLotacaoSala(linha.get(10));
	        }
	        reader.close();
	        return aulas;
	    }

}
